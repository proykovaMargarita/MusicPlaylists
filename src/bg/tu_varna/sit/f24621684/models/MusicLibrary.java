package bg.tu_varna.sit.f24621684.models;

import bg.tu_varna.sit.f24621684.models.song.Song;
import java.util.ArrayList;
import java.util.List;

public class MusicLibrary {
    private List<Song> songs;
    private List<Playlist> playlists;
    private List<PlayHistoryEntry> history;

    public MusicLibrary() {
        this.songs = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    public MusicLibrary(List<Song> songs, List<Playlist> playlists, List<PlayHistoryEntry> history) {
        this.songs = (songs != null) ? songs : new ArrayList<>();
        this.playlists = (playlists != null) ? playlists : new ArrayList<>();
        this.history = (history != null) ? history : new ArrayList<>();
    }

    public void addSong(Song song){
        for (Song s : songs){
            if (s.equals(song)){
                throw new IllegalArgumentException("Song already exists.");
            }
        }
        songs.add(song);
    }

    public Song getSongById(int id) {
        for (Song song : songs) {
            if (song.getID() == id) {
                return song;
            }
        }
        return null;
    }

    public List<Song> getSongs() { return songs; }
    public void setSongs(List<Song> songs) { this.songs = songs; }

    public List<Playlist> getPlaylists() { return playlists; }
    public void setPlaylists(List<Playlist> playlists) { this.playlists = playlists; }

    public List<PlayHistoryEntry> getHistory() { return history; }
    public void setHistory(List<PlayHistoryEntry> history) { this.history = history; }
}
