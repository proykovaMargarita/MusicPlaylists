package bg.tu_varna.sit.f24621684.models;

import bg.tu_varna.sit.f24621684.models.song.Song;

import java.time.LocalDateTime;

public class PlayHistoryEntry {
    private final Song song;
    private final LocalDateTime time;
    private final String playlistName;

    public PlayHistoryEntry(Song song, LocalDateTime time) {
        this.song = song;
        this.time = time;
        this.playlistName = "";
    }
    public PlayHistoryEntry(Song song, LocalDateTime time, String playlistName) {
        this.song = song;
        this.time = time;
        this.playlistName = playlistName;
    }

    public Song getSong() { return song; }
    public LocalDateTime getTime() { return time; }
    public String getPlaylistName() { return playlistName; }


    public static PlayHistoryEntry play(Song song){
        return new PlayHistoryEntry(song, LocalDateTime.now());
    }
    public static PlayHistoryEntry play(Song song, String playlistName){
        return new PlayHistoryEntry(song, LocalDateTime.now(), playlistName);
    }

    public static PlayHistoryEntry playAt(Song song, LocalDateTime time){
        return new PlayHistoryEntry(song, time);
    }
    public static PlayHistoryEntry playAt(Song song, LocalDateTime time, String playlistName){
        return new PlayHistoryEntry(song, time, playlistName);
    }

}
