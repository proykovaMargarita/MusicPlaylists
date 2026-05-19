package bg.tu_varna.sit.f24621684.commands.library.stats;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.PlayHistoryEntry;
import bg.tu_varna.sit.f24621684.services.ParseService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Команда за извеждане на най-използваните плейлисти в системата.
 * @author Margarita Proykova
 */
public class TopPlaylistsCommand implements Command {
    /** Мениджър на състоянието на приложението */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата TopPlaylists.
     * @param stateManager обект за управление на състоянието.
     */
    public TopPlaylistsCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Извежда класация на плейлистите според броя пускания на песни от тях.
     * @param args очаква брой плейлисти (n) и опционален времеви интервал.
     * @return списък с най-активните плейлисти.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 1) return "Error: Usage - topplaylists <n>";

        int n = Integer.parseInt(args[0]);
        LocalDate from = null, to = null;

        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("from=")) from = ParseService.parseUserDate(args[i].substring(5));
            else if (args[i].startsWith("to=")) to = ParseService.parseUserDate(args[i].substring(3));
        }
        LocalDateTime start = (from != null) ? from.atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime end = (to != null) ? to.atTime(23, 59, 59) : LocalDateTime.MAX;

        Map<String, Integer> counts = new HashMap<>();
        for (PlayHistoryEntry entry : stateManager.getLibrary().getHistory()) {
            if (entry.getTime().isBefore(start) || entry.getTime().isAfter(end)) {
                continue;
            }
            if (entry.getPlaylistName() != null && !entry.getPlaylistName().isEmpty()) {
                String name = entry.getPlaylistName();
                counts.put(name, counts.getOrDefault(name, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(counts.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        StringBuilder sb = new StringBuilder("Top " + n + " playlists:\n");
        for (int i = 0; i < Math.min(n, list.size()); i++) {
            sb.append(i + 1).append(". ").append(list.get(i).getKey())
                    .append(" - ").append(list.get(i).getValue()).append(" plays\n");
        }
        return sb.toString();
    }

    /**
     * Извежда описание на командата.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() {
        return "(topplaylists <n> [from=<date>] [to=<date>]) shows the top n most active playlists";
    }
}