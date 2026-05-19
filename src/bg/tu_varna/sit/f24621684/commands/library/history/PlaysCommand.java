package bg.tu_varna.sit.f24621684.commands.library.history;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.PlayHistoryEntry;
import bg.tu_varna.sit.f24621684.services.ParseService;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Команда за извеждане на справка за историята на слушане с филтриране по различни критерии.
 * @author Margarita Proykova
 */
public class PlaysCommand implements Command {
    /** Мениджър на състоянието на системата */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата Plays.
     * @param stateManager обект за управление на състоянието.
     */
    public PlaysCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Генерира списък с изслушвания въз основа на зададени филтри за време, плейлист или песен.
     * @param args опционални филтри (from, to, playlist, song).
     * @return форматиран отчет за историята на слушане.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is open.";

        LocalDate from = null, to = null;
        String playlist = null;
        Integer songId = null;

        for (String arg : args) {
            if (arg.startsWith("from=")) from = ParseService.parseUserDate(arg.substring(5));
            else if (arg.startsWith("to=")) to = ParseService.parseUserDate(arg.substring(3));
            else if (arg.startsWith("playlist=")) playlist = arg.substring(9);
            else if (arg.startsWith("song=")) songId = Integer.parseInt(arg.substring(5));
        }

        LocalDateTime start = (from != null) ? from.atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime end = (to != null) ? to.atTime(23, 59, 59) : LocalDateTime.MAX;

        StringBuilder sb = new StringBuilder("Play history report:\n");
        for (PlayHistoryEntry entry : stateManager.getLibrary().getHistory()) {
            boolean matches = !entry.getTime().isBefore(start) && !entry.getTime().isAfter(end);
            if (playlist != null && !playlist.equalsIgnoreCase(entry.getPlaylistName())) matches = false;
            if (songId != null && entry.getSong().getID() != songId) matches = false;

            if (matches) {
                sb.append(entry.getTime()).append(" | Song: ").append(entry.getSong().getTitle())
                        .append(" | Playlist: ").append(entry.getPlaylistName().isEmpty() ? "N/A" : entry.getPlaylistName())
                        .append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Извежда описание на възможните филтри за историята.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() { return "(plays [from=<date>] [to=<date>] [playlist=<name>] [song=<id>])"; }
}