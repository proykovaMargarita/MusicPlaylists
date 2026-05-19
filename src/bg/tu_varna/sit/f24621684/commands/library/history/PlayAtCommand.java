package bg.tu_varna.sit.f24621684.commands.library.history;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.PlayHistoryEntry;
import bg.tu_varna.sit.f24621684.models.song.Song;
import bg.tu_varna.sit.f24621684.services.ParseService;
import java.time.LocalDateTime;

/**
 * Команда за добавяне на запис в историята за изслушване в минал или специфичен момент.
 * @author Margarita Proykova
 */
public class PlayAtCommand implements Command {
    /** Мениджър на състоянието на приложението */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата PlayAt.
     * @param stateManager обект за управление на състоянието.
     */
    public PlayAtCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Записва изслушване в историята със задна дата и час.
     * @param args очаква ID на песен, дата (yyyy-MM-dd), час (HH:mm) и опционално плейлист.
     * @return статус на операцията по записване.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 3) return "Error: Usage - playat <songId> <yyyy-MM-dd> <HH:mm> [playlist=<name>]";

        try {
            int songId = Integer.parseInt(args[0]);
            Song song = stateManager.getLibrary().getSongById(songId);
            if (song == null) return "Error: Song not found.";

            LocalDateTime dateTime = ParseService.parseUserDateTime(args[1], args[2]);

            String playlistName = null;
            if (args.length > 3 && args[3].startsWith("playlist=")) {
                playlistName = args[3].substring(9);
            }

            PlayHistoryEntry entry = (playlistName != null)
                    ? PlayHistoryEntry.playAt(song, dateTime, playlistName)
                    : PlayHistoryEntry.playAt(song, dateTime);

            stateManager.getLibrary().getHistory().add(entry);
            return "Successfully recorded historical play: " + song.getTitle() + " at " + dateTime;

        } catch (Exception e) {
            return "Error: Invalid input. Use format: <songId> <yyyy-MM-dd> <HH:mm>";
        }
    }

    /**
     * Връща описание на синтаксиса за регистриране на историческо изслушване.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() {
        return "(playat <songId> <date> <time> [playlist=<name>]) adds historical play entry";
    }
}