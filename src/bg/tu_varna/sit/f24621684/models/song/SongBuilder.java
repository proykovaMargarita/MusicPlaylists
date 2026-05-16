package bg.tu_varna.sit.f24621684.models.song;

/**
 * Строител (Builder) за улеснено създаване на обекти от тип Song.
 * @author Margarita Proykova
 */
public class SongBuilder {
    /** Уникален идентификатор */
    private final int ID;
    /** Заглавие */
    private final String title;
    /** Изпълнител */
    private final String artist;
    /** Продължителност в секунди */
    private final int durationSeconds;
    /** Албум (опционално) */
    private String album;
    /** Година (опционално) */
    private Integer year;
    /** Жанр (опционално) */
    private Genre genre;

    /**
     * Инициализира строителя със задължителните параметри за песен.
     * @param ID уникален номер.
     * @param title заглавие.
     * @param artist изпълнител.
     * @param durationSeconds продължителност.
     */
    public SongBuilder(int ID, String title, String artist, int durationSeconds) {
        this.ID = ID;
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
    }

    /**
     * Задава албум на песента.
     * @param album име на албума.
     * @return текущия строител.
     */
    public SongBuilder setAlbum(String album){
        this.album = album;
        return this;
    }

    /**
     * Задава година на издаване.
     * @param year година.
     * @return текущия строител.
     */
    public SongBuilder setYear(Integer year){
        this.year = year;
        return this;
    }

    /**
     * Задава жанр на песента.
     * @param genre обект от тип Genre.
     * @return текущия строител.
     */
    public SongBuilder setGenre(Genre genre){
        this.genre = genre;
        return this;
    }

    /**
     * Създава финалния обект Song.
     * @return нов обект от тип Song.
     */
    public Song build(){
        return new Song(this);
    }

    /** @return Идентификатор */
    protected int getID() { return ID;}
    /** @return Заглавие */
    protected String getTitle() { return title; }
    /** @return Изпълнител */
    protected String getArtist() { return artist; }
    /** @return Албум */
    protected String getAlbum() { return album; }
    /** @return Продължителност в секунди */
    protected int getDurationSeconds() { return durationSeconds; }
    /** @return Година */
    protected Integer getYear() { return year; }
    /** @return Жанр */
    protected Genre getGenre() { return genre; }
}