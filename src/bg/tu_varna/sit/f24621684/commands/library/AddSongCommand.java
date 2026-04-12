package bg.tu_varna.sit.f24621684.commands.library;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.song.Genre;
import bg.tu_varna.sit.f24621684.models.song.Song;
import bg.tu_varna.sit.f24621684.models.song.SongBuilder;
import bg.tu_varna.sit.f24621684.services.ParseService;

public class AddSongCommand implements Command {
    private final StateManager stateManager;
    private int lastID = 0;

    public AddSongCommand(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";

        if (args.length < 3){
            throw new IllegalArgumentException("Invalid song data.");
        }
        if (!stateManager.getLibrary().getSongs().isEmpty()){
            lastID = stateManager.getLibrary().getSongs().getLast().getID();
        }

        SongBuilder builder = new SongBuilder(
                lastID == 0 ? lastID : (lastID + 1),
                args[0].trim(),
                args[1].trim(),
                ParseService.parseDurationToSeconds(args[2])
        );

        if (args.length > 3 && !args[3].isEmpty()) {
            builder.setAlbum(args[3]);
        }

        if (args.length > 4 && !args[4].isEmpty()) {
            builder.setYear(Integer.parseInt(args[4]));
        }

        if (args.length > 5 && !args[5].isEmpty()) {
            try {
                builder.setGenre(Genre.valueOf(args[5].toUpperCase()));
            } catch (IllegalArgumentException e) {
                builder.setGenre(null);
            }
        }
        Song song = builder.build();
        try {
            stateManager.getLibrary().addSong(song);
        } catch (IllegalArgumentException e){
            return "Error: " + e.getMessage();
        }

        return "Successfully added song.";
    }

    @Override
    public String getDescription() {
        return "(<title> <artist> <duration> [<album>] [<year>] [<genre>]) Adds a song to the library";
    }
}
