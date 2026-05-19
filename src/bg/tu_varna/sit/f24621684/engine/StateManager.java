package bg.tu_varna.sit.f24621684.engine;

import bg.tu_varna.sit.f24621684.models.MusicLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас за управление на текущото състояние на приложението, включително заредената библиотека и файлови пътища.
 * @author Margarita Proykova
 */
public class StateManager {
    /** Текущо заредената музикална библиотека */
    private MusicLibrary library;
    /** Път до файла, от който са заредени данните */
    private String currentFilePath;
    /** Флаг, индикиращ дали в момента има зареден файл */
    private boolean isFileOpen;
    /** Списък от плейлисти с нисък процент слушания, които могат да бъдат изтрити*/
    private List<String> pendingDeletions = new ArrayList<>();

    /**
     * Конструктор за създаване на нов мениджър на състоянието с начални стойности.
     */
    public StateManager() {
        this.isFileOpen = false;
        this.library = new MusicLibrary();
        this.currentFilePath = null;
    }

    /** @return Текущата музикална библиотека */
    public MusicLibrary getLibrary() { return library; }
    /** @param library обектът на библиотеката за задаване */
    public void setLibrary(MusicLibrary library) { this.library = library; }

    /** @return Пътят до текущо отворения файл */
    public String getCurrentFilePath() { return currentFilePath; }
    /** @param currentFilePath път до файла */
    public void setCurrentFilePath(String currentFilePath) { this.currentFilePath = currentFilePath; }

    /** @return true, ако има отворен файл */
    public boolean isFileOpen() { return isFileOpen; }
    /** @param fileOpen статус на отворения файл */
    public void setFileOpen(boolean fileOpen) { isFileOpen = fileOpen; }

    /** @return Списъкът с плейлисти, които могат да бъдат изтрити */
    public List<String> getPendingDeletions() { return pendingDeletions; }
    /** @param list плейлисти, които могат да бъдат изтрити */
    public void setPendingDeletions(List<String> list) { this.pendingDeletions = list; }

    /**
     * Нулира състоянието на приложението, премахвайки библиотеката и файловия път.
     */
    public void reset() {
        this.library = new MusicLibrary();
        this.currentFilePath = null;
        this.isFileOpen = false;
    }
}