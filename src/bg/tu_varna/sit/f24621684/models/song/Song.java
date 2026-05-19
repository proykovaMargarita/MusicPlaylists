package bg.tu_varna.sit.f24621684.models.song;

import bg.tu_varna.sit.f24621684.services.ParseService;
import java.util.Objects;

/**
 * Представя музикална песен с нейните характеристики.
 * @author Margarita Proykova
 */
public class Song {
    /** Уникален идентификатор на песента */
    private final int ID;
    /** Заглавие на песента */
    private final String title;
    /** Изпълнител на песента */
    private final String artist;
    /** Продължителност на песента в секунди */
    private final int durationSeconds;
    /** Албум, към който принадлежи песента */
    private final String album;
    /** Година на издаване */
    private final Integer year;
    /** Жанр на песента */
    private final Genre genre;

    /**
     * Конструктор, който създава песен чрез обект от тип SongBuilder.
     * @param builder обектът строител, съдържащ данните за песента.
     */
    protected Song(SongBuilder builder){
        this.ID = builder.getID();
        this.title = builder.getTitle();
        this.artist = builder.getArtist();
        this.durationSeconds = builder.getDurationSeconds();
        this.album = builder.getAlbum();
        this.year = builder.getYear();
        this.genre = builder.getGenre();
    }

    /**
     * Проверява дали две песни са еднакви въз основа на заглавие и изпълнител.
     * @param o обектът за сравнение.
     * @return true, ако заглавието и изпълнителят съвпадат (без значение от регистъра).
     */
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;

        return this.title.equalsIgnoreCase(song.title) && this.artist.equalsIgnoreCase(song.artist);
    }

    /**
     * Генерира хеш код за песента.
     * @return хеш код базиран на заглавие и изпълнител.
     */
    @Override
    public int hashCode(){
        return Objects.hash(title.toLowerCase(), artist.toLowerCase());
    }

    /**
     * Форматира данните за песента в четим текстов вид.
     * @return низово представяне на песента.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("ID: %d | Song: %s by %s [%s]",
                ID, title, artist, ParseService.formatSecondsToDuration(durationSeconds)));

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

    /** @return Идентификатор на песента */
    public int getID() { return ID;}
    /** @return Заглавие на песента */
    public String getTitle() { return title; }
    /** @return Изпълнител на песента */
    public String getArtist() { return artist; }
    /** @return Албум на песента */
    public String getAlbum() { return album; }
    /** @return Продължителност в секунди */
    public int getDurationSeconds() { return durationSeconds; }
    /** @return Година на издаване */
    public Integer getYear() { return year; }
    /** @return Жанр на песента */
    public Genre getGenre() { return genre; }
}