package bg.tu_varna.sit.f24621684.engine;

import bg.tu_varna.sit.f24621684.models.MusicLibrary;

public class StateManager {
    private MusicLibrary library;
    private String currentFilePath;
    private boolean isFileOpen = false;

    public StateManager() {
        this.isFileOpen = false;
        this.library = null;
        this.currentFilePath = null;
    }

    public MusicLibrary getLibrary() { return library; }
    public void setLibrary(MusicLibrary library) { this.library = library; }

    public String getCurrentFilePath() { return currentFilePath; }
    public void setCurrentFilePath(String currentFilePath) { this.currentFilePath = currentFilePath; }

    public boolean isFileOpen() { return isFileOpen; }
    public void setFileOpen(boolean fileOpen) { isFileOpen = fileOpen; }

    public void reset() {
        this.library = null;
        this.currentFilePath = null;
        this.isFileOpen = false;
    }
}
