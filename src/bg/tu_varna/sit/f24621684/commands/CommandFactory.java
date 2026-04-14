package bg.tu_varna.sit.f24621684.commands;

import bg.tu_varna.sit.f24621684.commands.common.*;
import bg.tu_varna.sit.f24621684.commands.library.*;
import bg.tu_varna.sit.f24621684.engine.Engine;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;


import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
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

        helpCommand.setCommands(commands);

        return commands;
    }
}
