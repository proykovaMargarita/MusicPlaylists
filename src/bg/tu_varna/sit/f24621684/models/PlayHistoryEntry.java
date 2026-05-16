package bg.tu_varna.sit.f24621684.models;

import bg.tu_varna.sit.f24621684.models.song.Song;
import java.time.LocalDateTime;

/**
 * Представя запис в историята на слушане на песни.
 * @author Margarita Proykova
 */
public class PlayHistoryEntry {
    /** Слушаната песен */
    private final Song song;
    /** Време на слушане */
    private final LocalDateTime time;
    /** Име на плейлиста, от който е пусната песента (ако има такъв) */
    private final String playlistName;

    /**
     * Конструктор за запис без информация за плейлист.
     * @param song слушаната песен.
     * @param time кога е слушана.
     */
    public PlayHistoryEntry(Song song, LocalDateTime time) {
        this.song = song;
        this.time = time;
        this.playlistName = "";
    }

    /**
     * Конструктор за запис с информация за плейлист.
     * @param song слушаната песен.
     * @param time кога е слушана.
     * @param playlistName име на плейлиста.
     */
    public PlayHistoryEntry(Song song, LocalDateTime time, String playlistName) {
        this.song = song;
        this.time = time;
        this.playlistName = playlistName;
    }

    /** @return Слушаната песен */
    public Song getSong() { return song; }
    /** @return Времето на слушане */
    public LocalDateTime getTime() { return time; }
    /** @return Име на плейлиста */
    public String getPlaylistName() { return playlistName; }

    /**
     * Създава запис за песен, пусната в момента.
     * @param song песента за запис.
     * @return нов обект PlayHistoryEntry с текущото време.
     */
    public static PlayHistoryEntry play(Song song){
        return new PlayHistoryEntry(song, LocalDateTime.now());
    }

    /**
     * Създава запис за песен от плейлист, пусната в момента.
     * @param song песента за запис.
     * @param playlistName име на плейлиста.
     * @return нов обект PlayHistoryEntry с текущото време.
     */
    public static PlayHistoryEntry play(Song song, String playlistName){
        return new PlayHistoryEntry(song, LocalDateTime.now(), playlistName);
    }

    /**
     * Създава запис за песен в специфичен минал момент.
     * @param song песента за запис.
     * @param time време на слушане.
     * @return нов обект PlayHistoryEntry.
     */
    public static PlayHistoryEntry playAt(Song song, LocalDateTime time){
        return new PlayHistoryEntry(song, time);
    }

    /**
     * Създава запис за песен от плейлист в специфичен минал момент.
     * @param song песента за запис.
     * @param time време на слушане.
     * @param playlistName име на плейлиста.
     * @return нов обект PlayHistoryEntry.
     */
    public static PlayHistoryEntry playAt(Song song, LocalDateTime time, String playlistName){
        return new PlayHistoryEntry(song, time, playlistName);
    }
}