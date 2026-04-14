package bg.tu_varna.sit.f24621684.commands.library;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.song.Genre;
import bg.tu_varna.sit.f24621684.models.song.Song;
import bg.tu_varna.sit.f24621684.models.song.SongBuilder;
import bg.tu_varna.sit.f24621684.services.ParseService;

/**
 * Команда за добавяне на песен
 * @author Margarita Proykova
 */
public class AddSongCommand implements Command {
    /** Текущо състояние на програмата */
    private final StateManager stateManager;
    /** Текуща музикална библиотека */
    private final MusicLibrary library;

    /**
     * Конструктор за създаване на командата Add song.
     * Извлича се текущо заредената библиотека
     * @param stateManager Текущо състояние на програмата
     */
    public AddSongCommand(StateManager stateManager) {
        this.stateManager = stateManager;
        this.library = stateManager.getLibrary();
    }

    /**
     * Изпълнява командата за добавяне на песен.
     * Проверява дали файлът е отворен и дали данните са валидни.
     * @param args Масив от низове, съдържащ заглавие, изпълнител, продължителност
     * и опционални параметри (album=..., year=..., genre=...)
     * @return String съобщение, което се извежда на потребителя в конзолата.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 3) return "Invalid song data.";

        try{
            int newId = library.getSongs().isEmpty() ? 0 : library.getSongs().getLast().getID() + 1;

            SongBuilder builder = new SongBuilder(
                    newId,
                    args[0].trim(),
                    args[1].trim(),
                    ParseService.parseDurationToSeconds(args[2])
            );

            for (int i = 3; i < args.length; i++) {
                String arg = args[i];
                if (arg.contains("=")) {
                    String[] parts = arg.split("=", 2);
                    String key = parts[0].toLowerCase();
                    String value = parts[1];

                    switch (key) {
                        case "album":
                            builder.setAlbum(value);
                            break;
                        case "year":
                            builder.setYear(Integer.parseInt(value));
                            break;
                        case "genre":
                            try {
                                builder.setGenre(Genre.valueOf(value.toUpperCase()));
                            } catch (IllegalArgumentException e) {
                                builder.setGenre(null);
                            }
                            break;
                    }
                }
            }

            Song song = builder.build();
            library.addSong(song);
        } catch (Exception e){
            return "Error: " + e.getMessage();
        }

        return "Successfully added song.";
    }

    /**
     * Извежда описанието на командата.
     * @return Описание и параметри, които командата изисква
     */
    @Override
    public String getDescription() {
        return "(<title> <artist> <duration> [album=<album>] [year=<year>] [genre=<genre>]) Adds a song to the library";
    }
}
