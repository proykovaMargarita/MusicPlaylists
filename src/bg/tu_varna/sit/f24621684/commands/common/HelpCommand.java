package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;

import java.util.Map;

public class HelpCommand implements Command {

    private Map<String, Command> commands;

    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public String execute(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("The following commands are supported:\n");

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            sb.append(String.format("%-15s %s%n", entry.getKey(), entry.getValue().getDescription()));
        }

        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "prints this information";
    }
}
