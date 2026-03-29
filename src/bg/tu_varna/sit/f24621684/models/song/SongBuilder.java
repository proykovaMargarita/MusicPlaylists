package bg.tu_varna.sit.f24621684.models.song;

public class SongBuilder {
    private final int ID;
    private final String title;
    private final String artist;
    private final int durationSeconds;
    private String album;
    private Integer year;
    private Genre genre;

    public SongBuilder(int ID, String title, String artist, int durationSeconds) {
        this.ID = ID;
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
    }

    public SongBuilder setAlbum(String album){
        this.album = album;
        return this;
    }
    public SongBuilder setYear(Integer year){
        this.year = year;
        return this;
    }
    public SongBuilder setGenre(Genre genre){
        this.genre = genre;
        return this;
    }

    public Song build(){
        return new Song(this);
    }


    protected int getID() { return ID;}

    protected String getTitle() { return title; }

    protected String getArtist() { return artist; }

    protected String getAlbum() { return album; }

    protected int getDurationSeconds() { return durationSeconds; }

    protected Integer getYear() { return year; }

    protected Genre getGenre() { return genre; }
}
