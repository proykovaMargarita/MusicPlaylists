package bg.tu_varna.sit.f24621684.models;

import bg.tu_varna.sit.f24621684.models.song.Song;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас, представляващ цялата музикална библиотека, съдържаща песни, плейлисти и история.
 * @author Margarita Proykova
 */
public class MusicLibrary {
    /** Списък с всички налични песни */
    private List<Song> songs;
    /** Списък с всички създадени плейлисти */
    private List<Playlist> playlists;
    /** Списък със записи от историята на слушане */
    private List<PlayHistoryEntry> history;

    /**
     * Конструктор по подразбиране, инициализиращ празни списъци.
     */
    public MusicLibrary() {
        this.songs = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    /**
     * Конструктор за инициализиране на библиотеката с готови списъци.
     * @param songs списък с песни.
     * @param playlists списък с плейлисти.
     * @param history история на слушане.
     */
    public MusicLibrary(List<Song> songs, List<Playlist> playlists, List<PlayHistoryEntry> history) {
        this.songs = (songs != null) ? songs : new ArrayList<>();
        this.playlists = (playlists != null) ? playlists : new ArrayList<>();
        this.history = (history != null) ? history : new ArrayList<>();
    }

    /**
     * Добавя нова песен в библиотеката, ако тя вече не съществува.
     * @param song песента за добавяне.
     * @throws IllegalArgumentException ако песента вече е в списъка.
     */
    public void addSong(Song song) throws IllegalArgumentException {
        for (Song s : songs){
            if (s.equals(song)){
                throw new IllegalArgumentException("Song already exists.");
            }
        }
        songs.add(song);
    }

    /**
     * Търси песен по нейния уникален идентификатор.
     * @param id идентификатор на песента.
     * @return обекта Song или null, ако не е намерен.
     */
    public Song getSongById(int id) {
        for (Song song : songs) {
            if (song.getID() == id) {
                return song;
            }
        }
        return null;
    }

    /**
     * Търси плейлист по неговото име.
     * @param name името на плейлиста.
     * @return обекта Playlist или null, ако не е намерен.
     */
    public Playlist getPlaylistByName(String name) {
        for (Playlist p : playlists) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Премахва плейлист от библиотеката по име.
     * @param name име на плейлиста за изтриване.
     * @return true, ако плейлистът е успешно премахнат.
     */
    public boolean removePlaylist(String name) {
        Playlist p = getPlaylistByName(name);
        if (p != null) {
            return playlists.remove(p);
        }
        return false;
    }

    /** @return Списък с всички песни */
    public List<Song> getSongs() { return songs; }
    /** @param songs задава списък с песни */
    public void setSongs(List<Song> songs) { this.songs = songs; }

    /** @return Списък с всички плейлисти */
    public List<Playlist> getPlaylists() { return playlists; }
    /** @param playlists задава списък с плейлисти */
    public void setPlaylists(List<Playlist> playlists) { this.playlists = playlists; }

    /** @return Списък с историята на слушане */
    public List<PlayHistoryEntry> getHistory() { return history; }
    /** @param history задава списък с история на слушане */
    public void setHistory(List<PlayHistoryEntry> history) { this.history = history; }
}