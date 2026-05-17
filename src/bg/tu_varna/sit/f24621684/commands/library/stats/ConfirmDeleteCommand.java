package bg.tu_varna.sit.f24621684.commands.library.stats;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import java.util.ArrayList;
import java.util.List;

/**
 * Команда за потвърждаване на изтриването на плейлисти с ниска активност.
 * @author Margarita Proykova
 */
public class ConfirmDeleteCommand implements Command {
    /** Мениджър на състоянието на приложението */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата ConfirmDelete.
     * @param stateManager обект за управление на състоянието.
     */
    public ConfirmDeleteCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Изпълнява окончателното изтриване на плейлистите, съхранени в списъка за изчакване.
     * @param args не се очакват допълнителни аргументи.
     * @return съобщение за резултата от операцията.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";

        List<String> pending = stateManager.getPendingDeletions();

        if (pending == null || pending.isEmpty()) {
            return "No pending deletions found.";
        }

        MusicLibrary library = stateManager.getLibrary();
        int deletedCount = 0;

        for (String playlistName : pending) {
            if (library.removePlaylist(playlistName)) {
                deletedCount++;
            }
        }

        stateManager.setPendingDeletions(new ArrayList<>());

        return "Successfully deleted " + deletedCount + " playlist(s) with low activity.";
    }

    /**
     * Извежда описание на командата.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() {
        return "(confirmdelete) confirms the permanent deletion of underactive playlists";
    }
}