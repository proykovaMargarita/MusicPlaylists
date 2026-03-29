package bg.tu_varna.sit.f24621684.services;

import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.PlayHistoryEntry;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Genre;
import bg.tu_varna.sit.f24621684.models.song.Song;
import bg.tu_varna.sit.f24621684.models.song.SongBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class FileService {
    public MusicLibrary load(String path) throws IOException {
        MusicLibrary library = new MusicLibrary();
        File file = new File(path);
        if (!file.exists()) return library;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String currentSection = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("[")) {
                    currentSection = line;
                    continue;
                }

                switch (currentSection){
                    case "[SONGS]":
                        library.getSongs().add(parseSong(line));
                        break;
                    case "[PLAYLISTS]":
                        library.getPlaylists().add(parsePlaylist(line, library));
                        break;
                    case "[HISTORY]":
                        library.getHistory().add(parseHistory(line, library));
                        break;
                    default:
                        break;
                }
            }
        }
        return library;
    }

    public void save(String path, MusicLibrary library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            writer.println("[SONGS]");
            for (Song song : library.getSongs()) {
                writer.println(String.format("%d|%s|%s|%s|%d|%d|%s",
                        song.getID(),
                        song.getTitle(),
                        song.getArtist(),
                        song.getAlbum(),
                        song.getDurationSeconds(),
                        song.getYear(),
                        song.getGenre()));
            }

            writer.println("[PLAYLISTS]");
            for (Playlist playlist : library.getPlaylists()) {
                StringBuilder ids = new StringBuilder();
                List<Song> playlistSongs = playlist.getSongs();

                for (int i = 0; i < playlistSongs.size(); i++) {
                    ids.append(playlistSongs.get(i).getID());
                    if (i < playlistSongs.size() - 1) {
                        ids.append(",");
                    }
                }

                writer.println(String.format("%s|%s|%s",
                        playlist.getName(),
                        ids,
                        playlist.getDescription()));
            }

            writer.println("[HISTORY]");
            for (PlayHistoryEntry entry : library.getHistory()) {
                writer.println(String.format("%d|%s|%s",
                        entry.getSong().getID(),
                        entry.getTime().toString(),
                        entry.getPlaylistName()));
            }
        }
    }

    private Song parseSong(String line) {
        String[] parts = line.split("\\|");

        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid song data: " + line);
        }

        SongBuilder builder = new SongBuilder(
                Integer.parseInt(parts[0]),     //ID
                parts[1],                       //Title
                parts[2],                       //Artist
                Integer.parseInt(parts[3])      //Duration
        );

        if (parts.length > 4 && !parts[4].isEmpty()) {
            builder.setAlbum(parts[4]);
        }

        if (parts.length > 5 && !parts[5].isEmpty()) {
            builder.setYear(Integer.parseInt(parts[5]));
        }

        if (parts.length > 6 && !parts[6].isEmpty()) {
            try {
                builder.setGenre(Genre.valueOf(parts[6].toUpperCase()));
            } catch (IllegalArgumentException e) {
               builder.setGenre(null);
            }
        }

        return builder.build();
    }

    private Playlist parsePlaylist(String line, MusicLibrary library){
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid playlist data: " + line);
        }

        List<Song> songs = new ArrayList<>();
        if (!parts[1].isEmpty()) {
            String[] songIds = parts[1].split(",");
            for (String idStr : songIds) {
                try {
                    int id = Integer.parseInt(idStr.trim());
                    Song song = library.getSongById(id);
                    if (song != null) {
                        songs.add(song);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Song with ID " + idStr + " not found. Skipping.");
                }
            }
        }

        if (parts.length == 2) {
            return new Playlist(
                    parts[0],
                    songs
            );
        } else {
            return new Playlist(
                    parts[0],
                    songs,
                    parts[2]
            );
        }
    }

    private PlayHistoryEntry parseHistory(String line, MusicLibrary library) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid history data: " + line);
        }

        try {
            int songId = Integer.parseInt(parts[0]);
            Song song = library.getSongById(songId);
            if (song == null) {
                System.out.println("Warning: History record for missing song ID " + songId + ". Skipping.");
                return null;
            }

            LocalDateTime time = LocalDateTime.parse(parts[1]);

            if (parts.length == 2){
                return new PlayHistoryEntry(song, time);
            } else {
                return new PlayHistoryEntry(song, time, parts[2]);
            }

        } catch (NumberFormatException e) {
            System.out.println("Warning: Invalid song ID in history: " + parts[0]);
        } catch (DateTimeParseException e) {
            System.out.println("Warning: Invalid date format in history: " + parts[1] + ". Expected ISO format.");
        }

        return null;
    }


}
