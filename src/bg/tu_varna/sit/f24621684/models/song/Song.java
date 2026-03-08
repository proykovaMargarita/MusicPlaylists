package bg.tu_varna.sit.f24621684.models.song;

public class Song {
    private final int ID;
    private final String title;
    private final String artist;
    private final String album;
    private final int durationSeconds;
    private final Integer year;
    private final Genre genre;

    protected Song(SongBuilder builder){
        this.ID = builder.getID();
        this.title = builder.getTitle();
        this.artist = builder.getArtist();
        this.album = builder.getAlbum();
        this.durationSeconds = builder.getDurationSeconds();
        this.year = builder.getYear();
        this.genre = builder.getGenre();
    }

    public int getID() { return ID;}
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDurationSeconds() { return durationSeconds; }
    public Integer getYear() { return year; }
    public Genre getGenre() { return genre; }
}
