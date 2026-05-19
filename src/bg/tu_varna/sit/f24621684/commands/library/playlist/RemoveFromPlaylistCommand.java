package bg.tu_varna.sit.f24621684.commands.library.playlist;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Song;

/**
 * Команда за премахване на определена песен от съществуващ плейлист.
 * @author Margarita Proykova
 */
public class RemoveFromPlaylistCommand implements Command {
    /** Мениджър на състоянието на приложението */
    private final StateManager stateManager;

    /**
     * Конструктор за инициализиране на командата за премахване.
     * @param stateManager обект за управление на състоянието.
     */
    public RemoveFromPlaylistCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Намира песента и плейлиста и изтрива връзката между тях.
     * @param args очаква име на плейлиста и ID на песента.
     * @return статус съобщение за успеха на операцията.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 2) return "Error: Usage - removefromplaylist <playlistName> <songId>";

        String playlistName = args[0];
        int songId;

        try {
            songId = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return "Error: Invalid song ID. Must be a number.";
        }

        Playlist playlist = stateManager.getLibrary().getPlaylistByName(playlistName);
        if (playlist == null) return "Error: Playlist not found.";

        Song song = stateManager.getLibrary().getSongById(songId);
        if (song == null) return "Error: Song not found.";

        boolean removed = playlist.getSongs().remove(song);

        if (removed) {
            return "Successfully removed song from playlist.";
        } else {
            return "Error: The song was not found in this playlist.";
        }
    }

    /**
     * Описание на синтаксиса на командата.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() {
        return "(removefromplaylist <playlistName> <songId>) removes a song from a playlist";
    }
}