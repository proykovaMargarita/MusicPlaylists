package bg.tu_varna.sit.f24621684.models;
import bg.tu_varna.sit.f24621684.models.song.Song;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private ArrayList<Song> songs;
    private String description;

    public Playlist(String name, ArrayList<Song> songs) {
        this.name = name;
        this.songs = songs;
    }
    public Playlist(String name, ArrayList<Song> songs, String description) {
        this.name = name;
        this.songs = songs;
        this.description = description;
    }

    public void addSong(Song song){
        this.songs.add(song);
    }

    public void addSongAt(int index, Song song) {
        if (index < 0) {
            this.songs.addFirst(song);
        } else if (index >= this.songs.size()) {
            this.songs.add(song);
        } else {
            this.songs.add(index, song);
        }
    }

    public String getName() { return name; }
    public ArrayList<Song> getSongs() { return songs; }
    public String getDescription() { return description; }
}
