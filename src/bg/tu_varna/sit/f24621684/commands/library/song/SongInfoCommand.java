package bg.tu_varna.sit.f24621684.commands.library.song;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.song.Song;

public class SongInfoCommand implements Command {
    private final StateManager stateManager;

    public SongInfoCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

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

    @Override
    public String getDescription() {
        return "(<songId>) Shows detailed information for a song";
    }
}
