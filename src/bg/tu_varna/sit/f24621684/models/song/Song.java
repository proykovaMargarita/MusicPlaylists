package bg.tu_varna.sit.f24621684.models.song;

import bg.tu_varna.sit.f24621684.services.ParseService;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("ID: %d | Song: %s by %s [%s]",
                ID, artist, title, ParseService.formatSecondsToDuration(durationSeconds)));

        if (album != null && !album.isEmpty()) {
            sb.append(" | Album: ").append(album);
        }

        if (year != null && year > 0) {
            sb.append(" | Year: ").append(year);
        }

        if (genre != null) {
            sb.append(" | Genre: ").append(genre);
        }

        return sb.toString();
    }

    public int getID() { return ID;}
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDurationSeconds() { return durationSeconds; }
    public Integer getYear() { return year; }
    public Genre getGenre() { return genre; }
}
