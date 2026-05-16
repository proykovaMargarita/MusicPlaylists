package bg.tu_varna.sit.f24621684.commands.library.playlist;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Song;
import bg.tu_varna.sit.f24621684.services.ParseService;

/**
 * Команда за визуализиране на съдържанието на даден плейлист и неговата обща продължителност.
 * @author Margarita Proykova
 */
public class ShowPlaylistCommand implements Command {
    /** Мениджър на състоянието */
    private final StateManager stateManager;
    /** Текущата музикална библиотека */
    private final MusicLibrary library;

    /**
     * Конструктор за инициализиране на командата за показване на плейлист.
     * @param stateManager обект за управление на състоянието.
     */
    public ShowPlaylistCommand(StateManager stateManager) {
        this.stateManager = stateManager;
        this.library = stateManager.getLibrary();
    }

    /**
     * Форматира и връща списък с всички песни в плейлиста и сумарното им време.
     * @param args очаква име на плейлиста.
     * @return детайлна информация за плейлиста под формата на низ.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is open.";
        if (args.length < 1) return "Error: Usage - showplaylist <name>";

        Playlist playlist = library.getPlaylistByName(args[0]);
        if (playlist == null) return "Error: Playlist not found.";

        int totalSeconds = 0;
        StringBuilder sb = new StringBuilder("Playlist: " + playlist.getName() + "\n");
        sb.append("Description: ").append(playlist.getDescription()).append("\n");
        sb.append("Songs:\n");

        for (Song s : playlist.getSongs()) {
            sb.append(s.toString()).append("\n");
            totalSeconds += s.getDurationSeconds();
        }

        sb.append("Total duration: ").append(ParseService.formatSecondsToDuration(totalSeconds));
        return sb.toString();
    }

    /**
     * Описание на функционалността на командата.
     * @return Описание на командата.
     */
    @Override
    public String getDescription() { return "(showplaylist <name>) shows playlist contents and duration"; }
}