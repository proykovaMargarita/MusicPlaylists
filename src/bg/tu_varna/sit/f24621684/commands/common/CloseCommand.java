package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;

public class CloseCommand implements Command {
    private final StateManager stateManager;

    public CloseCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) {
            return "Error: No file is currently open to close.";
        }

        String fileName = stateManager.getCurrentFilePath();

        stateManager.setLibrary(new MusicLibrary());
        stateManager.setCurrentFilePath(null);
        stateManager.setFileOpen(false);

        return "Successfully closed " + fileName;
    }

    @Override
    public String getDescription() {
        return "closes the currently opened file";
    }
}
