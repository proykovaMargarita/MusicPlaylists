package bg.tu_varna.sit.f24621684.commands.library.history;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.PlayHistoryEntry;
import bg.tu_varna.sit.f24621684.models.song.Song;

/**
 * Команда за регистриране на изслушване на песен в текущия момент.
 * @author Margarita Proykova
 */
public class PlayCommand implements Command {
    /** Мениджър на състоянието на приложението */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата Play.
     * @param stateManager обект за управление на състоянието.
     */
    public PlayCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Регистрира ново изслушване в историята с текущата дата и час.
     * @param args очаква ID на песен и опционално име на плейлист (playlist=name).
     * @return съобщение за успешно записано изслушване или грешка.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 1) return "Error: Usage - play <songId> [playlist=<name>]";

        try {
            int songId = Integer.parseInt(args[0]);
            Song song = stateManager.getLibrary().getSongById(songId);
            if (song == null) return "Error: Song not found.";

            String playlistName = null;
            if (args.length > 1 && args[1].startsWith("playlist=")) {
                playlistName = args[1].substring(9);
            }

            PlayHistoryEntry entry;
            if (playlistName != null) {
                entry = PlayHistoryEntry.play(song, playlistName);
            } else {
                entry = PlayHistoryEntry.play(song);
            }

            stateManager.getLibrary().getHistory().add(entry);
            return "Successfully recorded play: " + song.getTitle();

        } catch (NumberFormatException e) {
            return "Error: Invalid song ID.";
        }
    }

    /**
     * Извежда описание на командата.
     * @return Описание на функционалността и параметрите.
     */
    @Override
    public String getDescription() {
        return "(play <songId> [playlist=<name>]) plays a song and records history";
    }
}