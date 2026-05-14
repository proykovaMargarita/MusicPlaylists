package bg.tu_varna.sit.f24621684.commands.library.playlist;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.Playlist;

import java.util.Collections;
import java.util.Random;

public class ShuffleCommand implements Command {
    private final StateManager stateManager;
    private final MusicLibrary library;

    public ShuffleCommand(StateManager stateManager) {
        this.stateManager = stateManager;
        this.library = stateManager.getLibrary();
    }

    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 1) return "Error: Usage - shuffle <playlistName> [seed=<n>]";

        String playlistName = args[0];
        Playlist playlist = library.getPlaylistByName(playlistName);
        if (playlist == null) return "Error: Playlist not found.";

        if (args.length > 1 && args[1].startsWith("seed=")) {
            try {
                long seed = Long.parseLong(args[1].substring(5));
                Collections.shuffle(playlist.getSongs(), new Random(seed));
            } catch (NumberFormatException e) {
                return "Error: Invalid seed format.";
            }
        } else {
            Collections.shuffle(playlist.getSongs());
        }

        return "Playlist has been shuffled.";
    }

    @Override
    public String getDescription() {
        return "(shuffle <playlistName> [seed=<n>]) shuffles the songs in the playlist";
    }
}