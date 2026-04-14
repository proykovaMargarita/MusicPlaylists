package bg.tu_varna.sit.f24621684.commands.common;

import bg.tu_varna.sit.f24621684.commands.Command;
import bg.tu_varna.sit.f24621684.engine.Engine;

/**
 * Команда за прекратяване на изпълнението на програмата
 * @author Margarita Proykova
 */
public class ExitCommand implements Command {
    /** Двигателят на системата, който управлява цикъла на изпълнение */
    private final Engine engine;

    /**
     * Конструктор за създаване на командата Exit.
     * @param engine Двигателят на системата
     */
    public ExitCommand(Engine engine) {
        this.engine = engine;
    }

    /**
     * Прекратява изпълнението на програмата.
     * @param args не се очакват параметри за тази команда.
     * @return съобщение за излизане от програмата.
     */
    @Override
    public String execute(String[] args) {
        engine.stop();
        return "Exiting program.";
    }

    /**
     * Извежда описанието на командата.
     * @return Описание на командата
     */
    @Override
    public String getDescription() {
        return "exists the program";
    }
}
