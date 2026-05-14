package bg.tu_varna.sit.f24621684.commands.library.playlist;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Song;

import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements Command {
    private final StateManager stateManager;
    private final MusicLibrary library;

    public MoveCommand(StateManager stateManager) {
        this.stateManager = stateManager;
        this.library = stateManager.getLibrary();
    }

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

        Playlist playlist = library.getPlaylistByName(playlistName);
        if (playlist == null) return "Error: Playlist not found.";

        ArrayList<Song> songs = playlist.getSongs();
        if (fromPos < 0 || fromPos >= songs.size() || toPos < 0 || toPos >= songs.size()) {
            return "Error: Position out of bounds.";
        }

        Song songToMove = songs.remove(fromPos);
        songs.add(toPos, songToMove);

        return "Successfully moved song.";
    }

    @Override
    public String getDescription() {
        return "(move <playlistName> <fromPos> <toPos>) moves a song to a new position";
    }
}