package bg.tu_varna.sit.f24621684.commands.library.playlist;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;

/**
 * Команда за премахване на плейлист от музикалната библиотека.
 * @author Margarita Proykova
 */
public class DropPlaylistCommand implements Command {
    /** Обект за управление на състоянието */
    private final StateManager stateManager;
    /** Текущата музикална библиотека */
    private final MusicLibrary library;

    /**
     * Конструктор за инициализиране на командата за изтриване.
     * @param stateManager мениджър на състоянието.
     */
    public DropPlaylistCommand(StateManager stateManager) {
        this.stateManager = stateManager;
        this.library = stateManager.getLibrary();
    }

    /**
     * Премахва плейлиста по име, ако такъв съществува.
     * @param args очаква един аргумент - име на плейлиста.
     * @return съобщение за резултата от премахването.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is open.";
        if (args.length < 1) return "Error: Usage - dropplaylist <name>";

        boolean removed = library.removePlaylist(args[0]);
        return removed ? "Playlist deleted." : "Error: Playlist not found.";
    }

    /**
     * Извежда кратко описание на командата.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() { return "(dropplaylist <name>) deletes a playlist"; }
}
