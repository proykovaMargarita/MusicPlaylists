package bg.tu_varna.sit.f24621684.models;
import bg.tu_varna.sit.f24621684.models.song.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Представя плейлист, съдържащ списък от песни.
 * @author Margarita Proykova
 */
public class Playlist {
    /** Име на плейлиста */
    private final String name;
    /** Списък с песни в плейлиста */
    private ArrayList<Song> songs;
    /** Кратко описание на плейлиста */
    private String description;

    /**
     * Конструктор за създаване на плейлист без описание.
     * @param name име на плейлиста.
     * @param songs списък с песни.
     */
    public Playlist(String name, ArrayList<Song> songs) {
        this.name = name;
        this.songs = songs;
    }

    /**
     * Конструктор за създаване на плейлист с описание.
     * @param name име на плейлиста.
     * @param songs списък с песни.
     * @param description описание.
     */
    public Playlist(String name, ArrayList<Song> songs, String description) {
        this.name = name;
        this.songs = songs;
        this.description = description;
    }

    /**
     * Добавя песен в края на плейлиста.
     * @param song песен за добавяне.
     */
    public void addSong(Song song){
        this.songs.add(song);
    }

    /**
     * Добавя песен на специфична позиция в плейлиста.
     * @param index позиция, на която да се добави песента.
     * @param song песен за добавяне.
     */
    public void addSongAt(int index, Song song) {
        if (index < 0) {
            this.songs.addFirst(song);
        } else if (index >= this.songs.size()) {
            this.songs.add(song);
        } else {
            this.songs.add(index, song);
        }
    }

    /** @return Име на плейлиста */
    public String getName() { return name; }
    /** @return Списък с песните в плейлиста */
    public ArrayList<Song> getSongs() { return songs; }
    /** @return Описание на плейлиста */
    public String getDescription() { return description; }
}