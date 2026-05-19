package bg.tu_varna.sit.f24621684.commands.library.stats;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.PlayHistoryEntry;
import bg.tu_varna.sit.f24621684.services.ParseService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Команда за извеждане на плейлисти с ниска активност за определен период.
 * @author Margarita Proykova
 */
public class LowActivityCommand implements Command {
    /** Мениджър на състоянието на приложението */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата LowActivity.
     * @param stateManager обект за управление на състоянието.
     */
    public LowActivityCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Изчислява активността на плейлистите и извежда тези под зададения процентен праг.
     * @param args очаква начална дата, крайна дата и процентен праг.
     * @return списък с плейлистите с ниска активност или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 3) return "Error: Usage - lowactivity <from(yyyy-MM-dd)> <to(yyyy-MM-dd)> <threshold%>";

        try {
            LocalDate fromDate = ParseService.parseUserDate(args[0]);
            LocalDate toDate = ParseService.parseUserDate(args[1]);
            double threshold = Double.parseDouble(args[2]);

            LocalDateTime start = fromDate.atStartOfDay();
            LocalDateTime end = toDate.atTime(23, 59, 59);

            Map<String, Integer> counts = new HashMap<>();
            int totalPlays = 0;

            for (PlayHistoryEntry entry : stateManager.getLibrary().getHistory()) {
                String name = entry.getPlaylistName();
                counts.put(name, 0);
                if (!entry.getTime().isBefore(start) && !entry.getTime().isAfter(end)) {

                    if (name != null && !name.isEmpty()) {
                        counts.put(name, counts.getOrDefault(name, 0) + 1);
                        totalPlays++;
                    }
                }
            }

            if (totalPlays == 0) return "No activity found in specified period.";

            List<String> toDelete = new ArrayList<>();

            StringBuilder sb = new StringBuilder("Playlists below " + threshold + "% activity:\n");
            for (String name : counts.keySet()) {
                double percent = ((double) counts.get(name) / totalPlays) * 100;
                if (percent < threshold) {
                    sb.append("- ").append(name).append(" (").append(String.format("%.2f", percent)).append("%)\n");
                }
                if (percent < 10) {
                    toDelete.add(name);
                }
            }

            if (!toDelete.isEmpty()) {
                stateManager.setPendingDeletions(toDelete);
                sb.append("\nFound ").append(toDelete.size()).append(" playlist(s) below 10% activity.");
                sb.append(" Type 'confirmdelete' to remove them permanently.");
            }

            return sb.toString();

        } catch (Exception e) {
            return "Error: Invalid date format or threshold. Use: yyyy-MM-dd";
        }
    }

    /**
     * Извежда описание на командата.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() {
        return "(lowactivity <from> <to> <threshold>) lists underactive playlists";
    }
}