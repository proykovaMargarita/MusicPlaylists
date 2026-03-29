package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;

public class OpenCommand implements Command {
    private final StateManager stateManager;
    private final FileService fileService;

    public OpenCommand(StateManager stateManager, FileService fileService) {
        this.stateManager = stateManager;
        this.fileService = fileService;
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 1) return "Error: Usage: open <file_path>";

        String path = args[0];
        try {
            stateManager.setLibrary(fileService.load(path));
            stateManager.setCurrentFilePath(path);
            stateManager.setFileOpen(true);
            return "Successfully opened " + path;
        } catch (Exception e) {
            return "Error: Could not read TXT file: " + e.getMessage();
        }
    }

    @Override
    public String getDescription() {
        return "(open <file>) opens <file>>";
    }
}
