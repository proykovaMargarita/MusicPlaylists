package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;

public class SaveCommand implements Command {
    private final StateManager stateManager;
    private final FileService fileService;

    public SaveCommand(StateManager stateManager, FileService fileService) {
        this.stateManager = stateManager;
        this.fileService = fileService;
    }

    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";

        try {
            fileService.save(stateManager.getCurrentFilePath(), stateManager.getLibrary());
            return "Successfully saved to " + stateManager.getCurrentFilePath();
        } catch (Exception e) {
            return "Error: Could not save file: " + e.getMessage();
        }
    }

    @Override
    public String getDescription() {
        return "saves the currently open file";
    }
}
