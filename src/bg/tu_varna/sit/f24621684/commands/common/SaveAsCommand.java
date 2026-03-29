package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;

public class SaveAsCommand implements Command {
    private final StateManager stateManager;
    private final FileService fileService;

    public SaveAsCommand(StateManager stateManager, FileService fileService) {
        this.stateManager = stateManager;
        this.fileService = fileService;
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 1) return "Error: Usage: saveas <file_path>";
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";

        String newPath = args[0];
        try {
            fileService.save(newPath, stateManager.getLibrary());
            stateManager.setCurrentFilePath(newPath);
            return "Successfully saved as " + newPath;
        } catch (Exception e) {
            return "Error: Could not save to new path: " + e.getMessage();
        }
    }

    @Override
    public String getDescription() {
        return "(saveas <file>) saves the currently open file in <file>";
    }
}
