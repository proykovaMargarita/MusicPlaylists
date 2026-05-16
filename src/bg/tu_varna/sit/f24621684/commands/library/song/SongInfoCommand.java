package bg.tu_varna.sit.f24621684.commands.library.song;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.song.Song;

/**
 * Команда за извеждане на детайлна информация за конкретна песен.
 * @author Margarita Proykova
 */
public class SongInfoCommand implements Command {
    /** Мениджър на състоянието за достъп до библиотеката */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата SongInfo.
     * @param stateManager обект за управление на състоянието.
     */
    public SongInfoCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Извлича и показва информация за песен по зададено ID.
     * @param args очаква един параметър - ID на песента.
     * @return низово представяне на информацията за песента или съобщение за грешка.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 1) return "Error: Please provide a song ID.";

        try {
            int id = Integer.parseInt(args[0]);

            Song song = stateManager.getLibrary().getSongById(id);
            if (song != null){
                return song.toString();
            }

            return "Error: Song with ID " + id + " not found.";
        } catch (NumberFormatException e) {
            return "Error: Invalid ID format.";
        }
    }

    /**
     * Връща описание на командата.
     * @return Описание на командата и нейните параметри.
     */
    @Override
    public String getDescription() {
        return "(<songId>) Shows detailed information for a song";
    }
}