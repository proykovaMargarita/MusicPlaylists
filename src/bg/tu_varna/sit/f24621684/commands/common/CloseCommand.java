package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;

/**
 * Команда за затваряне на файл
 * @author Margarita Proykova
 */
public class CloseCommand implements Command {
    /** Текущо състояние на програмата */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата Close.
     * @param stateManager Текущо състояние на програмата
     */
    public CloseCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Изпълнява командата за затваряне на файл.
     * Проверява дали файлът е отворен.
     * @param args не се очакват параметри за тази команда.
     * @return String съобщение, което се извежда на потребителя в конзолата.
     */
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

    /**
     * Извежда описанието на командата.
     * @return Описание на командата
     */
    @Override
    public String getDescription() {
        return "closes the currently opened file";
    }
}
