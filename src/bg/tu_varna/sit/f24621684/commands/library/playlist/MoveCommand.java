package bg.tu_varna.sit.f24621684.commands.library.playlist;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Song;

import java.util.ArrayList;

/**
 * Команда за промяна на позицията на песен в рамките на плейлист.
 * @author Margarita Proykova
 */
public class MoveCommand implements Command {
    /** Мениджър на състоянието за достъп до данните */
    private final StateManager stateManager;

    /**
     * Конструктор за инициализиране на командата за местене на песен.
     * @param stateManager обект за управление на състоянието.
     */
    public MoveCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Премества песен от един индекс на друг в списъка на плейлиста.
     * @param args очаква име на плейлист, начална позиция и крайна позиция.
     * @return съобщение за успешно преместване или грешка при невалидни индекси.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 3) return "Error: Usage - move <playlistName> <fromPos> <toPos>";

        String playlistName = args[0];
        int fromPos, toPos;

        try {
            fromPos = Integer.parseInt(args[1]);
            toPos = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            return "Error: Positions must be numbers.";
        }

        Playlist playlist = stateManager.getLibrary().getPlaylistByName(playlistName);
        if (playlist == null) return "Error: Playlist not found.";

        ArrayList<Song> songs = playlist.getSongs();
        if (fromPos < 0 || fromPos >= songs.size() || toPos < 0 || toPos >= songs.size()) {
            return "Error: Position out of bounds.";
        }

        Song songToMove = songs.remove(fromPos);
        songs.add(toPos, songToMove);

        return "Successfully moved song.";
    }

    /**
     * Описание на предназначението на командата.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() {
        return "(move <playlistName> <fromPos> <toPos>) moves a song to a new position";
    }
}