package bg.tu_varna.sit.f24621684.commands;

import bg.tu_varna.sit.f24621684.commands.common.*;
import bg.tu_varna.sit.f24621684.commands.library.history.*;
import bg.tu_varna.sit.f24621684.commands.library.playlist.*;
import bg.tu_varna.sit.f24621684.commands.library.song.*;
import bg.tu_varna.sit.f24621684.commands.library.stats.*;
import bg.tu_varna.sit.f24621684.engine.Engine;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика за създаване и конфигурация на всички команди в системата.
 * @author Margarita Proykova
 */
public class CommandFactory {

    /**
     * Статичен метод за инициализиране и регистриране на всички команди.
     * @param engine инстанция на двигателя на програмата.
     * @param stateManager мениджър на състоянието за достъп до данните.
     * @param fileService сервиз за работа с файлове.
     * @return Map, съдържащ имената на командите и съответните им обекти.
     */
    public static Map<String, Command> createCommands(Engine engine, StateManager stateManager, FileService fileService) {
        Map<String, Command> commands = new HashMap<>();
        HelpCommand helpCommand = new HelpCommand();

        commands.put("exit", new ExitCommand(engine));
        commands.put("help", helpCommand);
        commands.put("open", new OpenCommand(stateManager, fileService));
        commands.put("save", new SaveCommand(stateManager, fileService));
        commands.put("saveas", new SaveAsCommand(stateManager, fileService));
        commands.put("close", new CloseCommand(stateManager));

        commands.put("addsong", new AddSongCommand(stateManager));
        commands.put("removesong", new RemoveSongCommand(stateManager));
        commands.put("listsongs", new ListSongsCommand(stateManager));
        commands.put("songinfo", new SongInfoCommand(stateManager));

        commands.put("createplaylist", new CreatePlaylistCommand(stateManager));
        commands.put("addtoplaylist", new AddToPlaylistCommand(stateManager));
        commands.put("deleteplaylist", new DeletePlaylistCommand(stateManager));
        commands.put("dropplaylist", new DropPlaylistCommand(stateManager));
        commands.put("removefromplaylist", new RemoveFromPlaylistCommand(stateManager));
        commands.put("move", new MoveCommand(stateManager));
        commands.put("shuffle", new ShuffleCommand(stateManager));
        commands.put("showplaylist", new ShowPlaylistCommand(stateManager));

        commands.put("play", new PlayCommand(stateManager));
        commands.put("playat", new PlayAtCommand(stateManager));
        commands.put("plays", new PlaysCommand(stateManager));

        commands.put("toptracks", new TopTracksCommand(stateManager));
        commands.put("topplaylists", new TopPlaylistsCommand(stateManager));
        commands.put("topartists", new TopArtistsCommand(stateManager));
        commands.put("lowactivity", new LowActivityCommand(stateManager));
        commands.put("confirmdelete", new ConfirmDeleteCommand(stateManager));

        helpCommand.setCommands(commands);

        return commands;
    }
}