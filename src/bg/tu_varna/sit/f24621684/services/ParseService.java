package bg.tu_varna.sit.f24621684.services;

import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.PlayHistoryEntry;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Genre;
import bg.tu_varna.sit.f24621684.models.song.Song;
import bg.tu_varna.sit.f24621684.models.song.SongBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ParseService {
    protected static Song parseSong(String line) {
        String[] parts = line.split("\\|", -1);

        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid song data: " + line);
        }

        SongBuilder builder = new SongBuilder(
                Integer.parseInt(parts[0]),     //ID
                parts[1],                       //Title
                parts[2],                       //Artist
                parseDurationToSeconds(parts[3])      //Duration
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

    protected static Playlist parsePlaylist(String line, MusicLibrary library){
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

    protected static PlayHistoryEntry parseHistory(String line, MusicLibrary library) {
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

    public static int parseDurationToSeconds(String durationStr){
        String[] parts = durationStr.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid duration format, use: (mm:ss).");
        }
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);

        if (seconds < 0 || seconds > 59) {
            throw new IllegalArgumentException("Seconds must be between 00 and 59.");
        }

        return (minutes * 60) + seconds;
    }

    protected static String formatSecondsToDuration(int durationScnd){
        int minutes = durationScnd / 60;
        int seconds = durationScnd % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
