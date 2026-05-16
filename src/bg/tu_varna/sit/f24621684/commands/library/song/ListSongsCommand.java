package bg.tu_varna.sit.f24621684.commands.library.song;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.song.Song;
import java.util.ArrayList;
import java.util.List;

/**
 * Команда за извеждане на списък с песни с поддръжка на филтри.
 * @author Margarita Proykova
 */
public class ListSongsCommand implements Command {
    /** Мениджър на състоянието за достъп до песните */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата ListSongs.
     * @param stateManager обект за управление на състоянието.
     */
    public ListSongsCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Извежда всички песни или филтриран подмножество въз основа на аргументите.
     * @param args опционални филтри във формат key=value (artist, genre, year).
     * @return списък с намерените песни или съобщение при липса на резултати.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) {
            return "Error: No file is currently open.";
        }

        List<Song> allSongs = stateManager.getLibrary().getSongs();
        if (allSongs.isEmpty()) {
            return "The library is empty.";
        }

        List<Song> filteredSongs = new ArrayList<>(allSongs);

        for (String arg : args) {
            if (arg.contains("=")) {
                String[] parts = arg.split("=", 2);
                String key = parts[0].toLowerCase();
                String value = parts[1].trim();

                List<Song> tempResult = new ArrayList<>();

                for (Song song : filteredSongs) {
                    boolean matches = false;

                    switch (key) {
                        case "artist":
                            if (song.getArtist().equalsIgnoreCase(value)) matches = true;
                            break;
                        case "genre":
                            if (song.getGenre() != null && song.getGenre().toString().equalsIgnoreCase(value)) {
                                matches = true;
                            }
                            break;
                        case "year":
                            try {
                                int year = Integer.parseInt(value);
                                if (song.getYear() != null && song.getYear() == year) {
                                    matches = true;
                                }
                            } catch (NumberFormatException e) {
                                return "Error: Invalid year format.";
                            }
                            break;
                    }

                    if (matches) {
                        tempResult.add(song);
                    }
                }
                filteredSongs = tempResult;
            }
        }

        if (filteredSongs.isEmpty()) {
            return "No songs found matching the criteria.";
        }

        StringBuilder sb = new StringBuilder("Matching songs:\n");
        for (Song s : filteredSongs) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Връща описание на командата и нейния синтаксис.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() {
        return "listsongs [artist=<artist>] [genre=<genre>] [year=<year>] - Lists and filters songs.";
    }
}