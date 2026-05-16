package bg.tu_varna.sit.f24621684.commands.library.stats;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.PlayHistoryEntry;
import bg.tu_varna.sit.f24621684.services.ParseService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Команда за извеждане на класация на най-слушаните изпълнители.
 * @author Margarita Proykova
 */
public class TopArtistsCommand implements Command {
    /** Мениджър на състоянието за достъп до историята */
    private final StateManager stateManager;
    /** Текущата музикална библиотека */
    private final MusicLibrary library;

    /**
     * Конструктор за създаване на командата TopArtists.
     * @param stateManager обект за управление на състоянието.
     */
    public TopArtistsCommand(StateManager stateManager) {
        this.stateManager = stateManager;
        this.library = stateManager.getLibrary();
    }

    /**
     * Генерира списък с топ N изпълнители въз основа на броя изслушвания.
     * @param args очаква брой изпълнители (n) и опционални филтри за период (from/to).
     * @return форматиран списък с най-слушаните изпълнители.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 1) return "Error: Usage - toptracks <n>";

        int n = Integer.parseInt(args[0]);
        LocalDate from = null, to = null;

        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("from=")) from = ParseService.parseUserDate(args[i].substring(5));
            else if (args[i].startsWith("to=")) to = ParseService.parseUserDate(args[i].substring(3));
        }
        LocalDateTime start = (from != null) ? from.atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime end = (to != null) ? to.atTime(23, 59, 59) : LocalDateTime.MAX;

        Map<String, Integer> counts = new HashMap<>();
        for (PlayHistoryEntry entry : library.getHistory()) {
            if (!entry.getTime().isBefore(start) && !entry.getTime().isAfter(end)) {
                String artist = entry.getSong().getArtist();
                counts.put(artist, counts.getOrDefault(artist, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(counts.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        StringBuilder sb = new StringBuilder("Top " + n + " artists:\n");
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
        return "(topartists <n> [from=<date>] [to=<date>]) shows the top n most played artists";
    }
}