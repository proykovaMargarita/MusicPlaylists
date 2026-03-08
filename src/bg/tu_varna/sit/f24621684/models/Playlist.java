package bg.tu_varna.sit.f24621684.models;
import bg.tu_varna.sit.f24621684.models.song.Song;

import java.util.List;

public class Playlist {
    private String name;
    private List<Song> songs;
    private String description;

    public Playlist(String name, List<Song> songs) {
        this.name = name;
        this.songs = songs;
    }
    public Playlist(String name, List<Song> songs, String description) {
        this.name = name;
        this.songs = songs;
        this.description = description;
    }

    public String getName() { return name; }
    public List<Song> getSongs() { return songs; }
    public String getDescription() { return description; }
}
