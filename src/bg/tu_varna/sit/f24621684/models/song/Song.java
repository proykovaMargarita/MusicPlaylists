package bg.tu_varna.sit.f24621684.models.song;

import java.util.Objects;

public class Song {
    private final int ID;
    private final String title;
    private final String artist;
    private final int durationSeconds;
    private final String album;
    private final Integer year;
    private final Genre genre;

    protected Song(SongBuilder builder){
        this.ID = builder.getID();
        this.title = builder.getTitle();
        this.artist = builder.getArtist();
        this.durationSeconds = builder.getDurationSeconds();
        this.album = builder.getAlbum();
        this.year = builder.getYear();
        this.genre = builder.getGenre();
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;

        return this.title.equalsIgnoreCase(song.title) && this.artist.equalsIgnoreCase(song.artist);
    }

    @Override
    public int hashCode(){
        return Objects.hash(title.toLowerCase(), artist.toLowerCase());
    }

    public int getID() { return ID;}
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDurationSeconds() { return durationSeconds; }
    public Integer getYear() { return year; }
    public Genre getGenre() { return genre; }
}
