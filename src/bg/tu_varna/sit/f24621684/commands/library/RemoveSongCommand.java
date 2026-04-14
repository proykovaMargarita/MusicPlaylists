package bg.tu_varna.sit.f24621684.commands.library;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.models.MusicLibrary;
import bg.tu_varna.sit.f24621684.models.Playlist;
import bg.tu_varna.sit.f24621684.models.song.Song;

/**
 * Команда за премахване на песен
 * @author Margarita Proykova
 */
public class RemoveSongCommand implements Command {
    /** Текущо състояние на програмата */
    private final StateManager stateManager;
    /** Текуща музикална библиотека */
    private final MusicLibrary library;

    /**
     * Конструктор за създаване на командата Remove song.
     * Извлича се текущо заредената библиотека
     * @param stateManager Текущо състояние на програмата
     */
    public RemoveSongCommand(StateManager stateManager) {
        this.stateManager = stateManager;
        this.library = stateManager.getLibrary();
    }

    /**
     * Изпълнява командата за премахване на песен.
     * Проверява дали файлът е отворен и дали данните са валидни.
     * @param args Масив от низове, съдържащ ID на песен
     * @return String съобщение, което се извежда на потребителя в конзолата.
     */
    @Override
    public String execute(String[] args) {
        if (!stateManager.isFileOpen()) return "Error: No file is currently open.";
        if (args.length < 1) return "Enter song ID.";

        try {
            Song song = library.getSongById(Integer.parseInt(args[0]));
            if (song != null){
                for (Playlist playlist : library.getPlaylists()){
                    playlist.getSongs().remove(song);
                }
                library.getSongs().remove(song);
            } else {
                return "No song found with specified ID. No changes were made.";
            }

            return "Successfully removed song from all playlists and library.";
        } catch (NumberFormatException e){
            return "Invalid ID. Must be a number: " + e.getMessage();
        }
    }

    /**
     * Извежда описанието на командата.
     * @return Описание и параметри, които командата изисква
     */
    @Override
    public String getDescription() {
        return "(removesong <songID>) removes song";
    }
}
