package bg.tu_varna.sit.f24621684;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.commands.CommandFactory;
import bg.tu_varna.sit.f24621684.engine.Engine;
import bg.tu_varna.sit.f24621684.engine.StateManager;
import bg.tu_varna.sit.f24621684.services.FileService;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        StateManager stateManager = new StateManager();
        FileService fileService = new FileService();

        Engine engine = new Engine(stateManager);

        Map<String, Command> commands = CommandFactory.createCommands(engine, stateManager, fileService);

        engine.registerAll(commands);
        engine.run();
    }
}
