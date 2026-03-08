package bg.tu_varna.sit.f24621684.models;

import java.time.LocalDateTime;

public class PlayHistoryEntry {
    private final int songId;
    private final LocalDateTime time;
    private String playlistName;

    public PlayHistoryEntry(int songId, LocalDateTime time) {
        this.songId = songId;
        this.time = time;
    }
    public PlayHistoryEntry(int songId, LocalDateTime time, String playlistName) {
        this.songId = songId;
        this.time = time;
        this.playlistName = playlistName;
    }

    public int getSongId() { return songId; }
    public LocalDateTime getTime() { return time; }
    public String getPlaylistName() { return playlistName; }


    public static PlayHistoryEntry play(int songID){
        return new PlayHistoryEntry(songID, LocalDateTime.now());
    }
    public static PlayHistoryEntry play(int songID, String playlistName){
        return new PlayHistoryEntry(songID, LocalDateTime.now(), playlistName);
    }

    public static PlayHistoryEntry playAt(int songID, LocalDateTime time){
        return new PlayHistoryEntry(songID, time);
    }
    public static PlayHistoryEntry playAt(int songID, LocalDateTime time, String playlistName){
        return new PlayHistoryEntry(songID, time, playlistName);
    }

}
