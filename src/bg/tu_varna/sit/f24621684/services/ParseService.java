package bg.tu_varna.sit.f24621684.services;

import bg.tu_varna.sit.f24621684.models.song.*;
import bg.tu_varna.sit.f24621684.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Сервиз за парсване и обработка на данни от текстов формат към системни обекти.
 * @author Margarita Proykova
 */
public class ParseService {
    /** Форматер за дати във формат година-месец-ден */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** Форматер за дата и час във формат година-месец-ден час:минути */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Преобразува текстов низ в дата (LocalDate).
     * @param dateStr низ, съдържащ дата.
     * @return обект LocalDate.
     */
    public static LocalDate parseUserDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * Преобразува низове за дата и час в обект LocalDateTime.
     * @param dateStr низ за дата.
     * @param timeStr низ за час.
     * @return обект LocalDateTime.
     */
    public static LocalDateTime parseUserDateTime(String dateStr, String timeStr) {
        return LocalDateTime.parse(dateStr + " " + timeStr, DATE_TIME_FORMATTER);
    }

    /**
     * Парсва ред от файл в обект от тип Song.
     * @param line редът от файла със специфичен разделител.
     * @return обект Song със заредени данни.
     */
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

    /**
     * Парсва ред от файл в обект от тип Playlist.
     * @param line редът от файла с данни за плейлиста.
     * @param library библиотеката, от която се извличат песните по ID.
     * @return обект Playlist.
     */
    protected static Playlist parsePlaylist(String line, MusicLibrary library){
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid playlist data: " + line);
        }

        ArrayList<Song> songs = new ArrayList<>();
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

    /**
     * Парсва ред от файл в обект за история на слушане.
     * @param line редът с данни от файла.
     * @param library библиотеката за търсене на песента по ID.
     * @return обект PlayHistoryEntry или null при невалидни данни.
     */
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

    /**
     * Превръща времетраене във формат mm:ss в общ брой секунди.
     * @param durationStr низ във формат "минути:секунди".
     * @return общия брой секунди.
     */
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

    /**
     * Форматира секунди в текстов низ с формат mm:ss.
     * @param durationScnd общ брой секунди.
     * @return форматиран низ (напр. 03:45).
     */
    public static String formatSecondsToDuration(int durationScnd){
        int minutes = durationScnd / 60;
        int seconds = durationScnd % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}