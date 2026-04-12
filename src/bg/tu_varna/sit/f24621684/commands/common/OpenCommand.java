package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;

import java.io.File;
import java.io.IOException;

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
            File file = new File(path);
            boolean isNewFile = false;

            if (!file.exists()) {
                isNewFile = file.createNewFile();
            }

            stateManager.setLibrary(fileService.load(path));
            stateManager.setCurrentFilePath(path);
            stateManager.setFileOpen(true);

            return isNewFile ? "Created new empty file and opened " + path
                    : "Successfully opened " + path;
        } catch (IOException e) {
            return "System I/O error: " + e.getMessage();
        } catch (Exception e) {
            return "Could not process file: " + e.getMessage();
        }

    }

    @Override
    public String getDescription() {
        return "(open <file>) opens <file>>";
    }
}
