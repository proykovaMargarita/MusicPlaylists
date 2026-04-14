package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;

/**
 * Команда за записване на текущите данни в нов файл.
 * Позволява на потребителя да укаже нов път за съхранение.
 * @author Margarita Proykova
 */
public class SaveAsCommand implements Command {
    /** Текущо състояние на програмата */
    private final StateManager stateManager;
    /** Обект за работа с файловата система*/
    private final FileService fileService;

    /**
     * Конструктор за създаване на командата Save as.
     * @param stateManager Текущо състояние на програмата
     * @param fileService Обект за работа с файловата система
     */
    public SaveAsCommand(StateManager stateManager, FileService fileService) {
        this.stateManager = stateManager;
        this.fileService = fileService;
    }

    /**
     * Изпълнява командата за записване на данните в нов файл.
     * Проверява дали е въведен нов път за съхранение и дали е отворен файл.
     * @param args Масив от низове, съдържащ новия път.
     * @return String съобщение, което се извежда на потребителя в конзолата.
     */
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

    /**
     * Извежда описанието на командата.
     * @return Описание на командата и параметър, който тя очаква.
     */
    @Override
    public String getDescription() {
        return "(saveas <file>) saves the currently open file in <file>";
    }
}
