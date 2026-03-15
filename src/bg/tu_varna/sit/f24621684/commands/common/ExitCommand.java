package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.Engine;

public class ExitCommand implements Command {
    private final Engine engine;

    public ExitCommand(Engine engine) {
        this.engine = engine;
    }

    @Override
    public String execute(String[] args) {
        engine.stop();
        return "Exiting program.";
    }

    @Override
    public String getDescription() {
        return "exists the program";
    }
}
