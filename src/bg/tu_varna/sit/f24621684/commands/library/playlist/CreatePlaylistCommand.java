package bg.tu_varna.sit.f24621684.commands.library.playlist;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Song;

import java.util.ArrayList;

/**
 * Команда за създаване на нов празен плейлист с име и описание.
 * @author Margarita Proykova
 */
public class CreatePlaylistCommand implements Command {
    /** Обект за управление на състоянието на библиотеката */
    private final StateManager stateManager;

    /**
     * Конструктор за създаване на командата CreatePlaylist.
     * @param stateManager мениджър на състоянието.
     */
    public CreatePlaylistCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Проверява за уникалност на името и създава нов плейлист в библиотеката.
     * @param args очаква име на плейлиста и опционално описание.
     * @return статус на операцията.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 1) return "Error: Please provide a name for the playlist.";

        String name = args[0];

        for (Playlist p : stateManager.getLibrary().getPlaylists()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return "Error: This playlist already exists.";
            }
        }

        String description = "";
        if (args.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            description = sb.toString().trim();
        }

        Playlist newPlaylist = new Playlist(name, new ArrayList<Song>(), description);
        stateManager.getLibrary().getPlaylists().add(newPlaylist);

        return "Successfully created playlist.";
    }

    /**
     * Връща описание на синтаксиса на командата.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() {
        return "(createplaylist <name> [<description>]) creates a new playlist";
    }
}