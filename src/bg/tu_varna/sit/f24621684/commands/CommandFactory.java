package bg.tu_varna.sit.f24621684.commands;

import bg.tu_varna.sit.f24621684.commands.common.*;
import bg.tu_varna.sit.f24621684.engine.Engine;
import bg.tu_varna.sit.f24621684.engine.StateManager;


import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    public static Map<String, Command> createCommands(Engine engine, StateManager stateManager) {
        Map<String, Command> commands = new HashMap<>();

        HelpCommand helpCommand = new HelpCommand();

        commands.put("exit", new ExitCommand(engine));
        commands.put("help", helpCommand);

        helpCommand.setCommands(commands);

        return commands;
    }
}
