package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;

import java.io.File;
import java.io.IOException;

/**
 * Команда за отваряне на файл
 * @author Margarita Proykova
 */
public class OpenCommand implements Command {
    /** Текущо състояние на програмата */
    private final StateManager stateManager;
    /** Обект за работа с файловата система*/
    private final FileService fileService;

    /**
     * Конструктор за създаване на командата Open.
     * @param stateManager Текущо състояние на програмата
     * @param fileService Обект за работа с файловата система
     */
    public OpenCommand(StateManager stateManager, FileService fileService) {
        this.stateManager = stateManager;
        this.fileService = fileService;
    }

    /**
     * Изпълнява командата за отваряне на файл.
     * Проверява дали е въведен път до файла и дали съществува такъв файл.
     * В противен случай, създава нов файл с празно съдържание.
     * @param args Масив от низове, съдържащ път до файла.
     * @return String съобщение, което се извежда на потребителя в конзолата.
     */
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

    /**
     * Извежда описанието на командата.
     * @return Описание на командата и параметър, който тя очаква.
     */
    @Override
    public String getDescription() {
        return "(open <file>) opens <file>>";
    }
}
