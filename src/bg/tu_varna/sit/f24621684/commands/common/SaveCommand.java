package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;

/**
 * Команда за записване на текущите данни в оригиналния файл.
 * @author Margarita Proykova
 */
public class SaveCommand implements Command {
    /** Текущо състояние на програмата */
    private final StateManager stateManager;
    /** Обект за работа с файловата система*/
    private final FileService fileService;

    /**
     * Конструктор за създаване на командата Save.
     * @param stateManager Текущо състояние на програмата
     * @param fileService Обект за работа с файловата система
     */
    public SaveCommand(StateManager stateManager, FileService fileService) {
        this.stateManager = stateManager;
        this.fileService = fileService;
    }

    /**
     * Изпълнява командата за записване на данните във файл.
     * Проверява дали е отворен файл.
     * @param args не се очакват параметри за тази команда.
     * @return String съобщение, което се извежда на потребителя в конзолата.
     */
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

    /**
     * Извежда описанието на командата.
     * @return Описание на командата
     */
    @Override
    public String getDescription() {
        return "saves the currently open file";
    }
}
