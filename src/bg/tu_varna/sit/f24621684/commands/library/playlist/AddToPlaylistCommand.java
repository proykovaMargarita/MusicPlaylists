package bg.tu_varna.sit.f24621684.commands.library.playlist;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Song;

/**
 * Команда за добавяне на песен в плейлист.
 * @author Margarita Proykova
 */
public class AddToPlaylistCommand implements Command {
    private final StateManager stateManager;
    private final MusicLibrary library;

    public AddToPlaylistCommand(StateManager stateManager) {
        this.stateManager = stateManager;
        this.library = stateManager.getLibrary();
    }

    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 2) return "Error: Usage - addtoplaylist <playlistName> <songId> [pos=<n>]";

        String playlistName = args[0];
        int songId;
        int position = -1;

        try {
            songId = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return "Error: Invalid song ID. Must be a number.";
        }

        if (args.length > 2) {
            String posArg = args[2];
            if (posArg.startsWith("pos=")) {
                try {
                    position = Integer.parseInt(posArg.substring(4));
                } catch (NumberFormatException e) {
                    return "Error: Invalid position format. Use pos=<number>";
                }
            }
        }

        Playlist playlist = library.getPlaylistByName(playlistName);

        if (playlist == null) return "Error: Playlist not found.";

        Song song = library.getSongById(songId);
        if (song == null) return "Error: Song not found.";

        if (position >= 0 && position < playlist.getSongs().size()) {
            playlist.addSongAt(position, song);
        } else {
            playlist.addSong(song);
        }

        return "Successfully added song to playlist.";
    }

    @Override
    public String getDescription() {
        return "(addtoplaylist <playlistName> <songId> [pos=<n>]) adds a song to a playlist at an optional position";
    }
}