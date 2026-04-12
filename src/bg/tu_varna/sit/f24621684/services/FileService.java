package bg.tu_varna.sit.f24621684.services;

import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.PlayHistoryEntry;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Song;

import java.io.*;
import java.util.List;

public class FileService {
    public MusicLibrary load(String path) throws IOException {
        MusicLibrary library = new MusicLibrary();
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + path);
        }

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

                try{
                    switch (currentSection){
                        case "[SONGS]":
                            library.getSongs().add(ParseService.parseSong(line));
                            break;
                        case "[PLAYLISTS]":
                            library.getPlaylists().add(ParseService.parsePlaylist(line, library));
                            break;
                        case "[HISTORY]":
                            library.getHistory().add(ParseService.parseHistory(line, library));
                            break;
                        default:
                            break;
                    }
                } catch (Exception e){
                    System.out.println("Invalid file format at line: " + line);
                    System.exit(1);
                }
            }
        }
        return library;
    }

    public void save(String path, MusicLibrary library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            writer.println("[SONGS]");
            for (Song song : library.getSongs()) {
                writer.println(String.format("%d|%s|%s|%s|%s|%s|%s",
                        song.getID(),
                        song.getTitle(),
                        song.getArtist(),
                        ParseService.formatSecondsToDuration(song.getDurationSeconds()),
                        (song.getAlbum() != null ? song.getAlbum() : ""),
                        (song.getYear() != null ? song.getYear().toString() : ""),
                        (song.getGenre() != null ? song.getGenre().name() : "")
                ));
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
                        (playlist.getDescription() != null ? playlist.getDescription() : "")
                ));
            }

            writer.println("[HISTORY]");
            for (PlayHistoryEntry entry : library.getHistory()) {
                writer.println(String.format("%d|%s|%s",
                        entry.getSong().getID(),
                        entry.getTime().toString(),
                        entry.getPlaylistName()
                ));
            }
        }
    }




}
