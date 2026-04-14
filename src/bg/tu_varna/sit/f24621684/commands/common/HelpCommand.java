package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;

import java.util.Map;

/**
 * Команда за извеждане на всички възможни команди
 * @author Margarita Proykova
 */
public class HelpCommand implements Command {
    /** Map, който пази командите в системата */
    private Map<String, Command> commands;

    /**
     * Конструктор за създаване на командата Help.
     * @param commands Map с команди в системата
     */
    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * Извежда командите с тяхното описание.
     * @param args не се очакват параметри за тази команда.
     * @return описание на всички команди, вписани в системата.
     */
    @Override
    public String execute(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("The following commands are supported:\n");

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            sb.append(String.format("%-15s %s%n", entry.getKey(), entry.getValue().getDescription()));
        }

        return sb.toString();
    }

    /**
     * Извежда описанието на командата.
     * @return Описание на командата
     */
    @Override
    public String getDescription() {
        return "prints this information";
    }
}
