package bg.tu_varna.sit.f24621684.engine;

import bg.tu_varna.sit.f24621684.commands.Command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Основен двигател на приложението, който управлява жизнения цикъл и изпълнението на командите.
 * @author Margarita Proykova
 */
public class Engine {
    /** Списък с регистрираните команди в системата */
    private final Map<String, Command> commands = new HashMap<>();
    /** Флаг за управление на главния цикъл на програмата */
    private boolean isRunning = true;

    /**
     * Регистрира набор от команди в системата.
     * @param commandMap Map, съдържащ имената на командите и техните реализации.
     */
    public void registerAll(Map<String, Command> commandMap) {
        this.commands.putAll(commandMap);
    }

    /**
     * Спира изпълнението на главния цикъл на приложението.
     */
    public void stop() {
        this.isRunning = false;
    }

    /**
     * Стартира главния цикъл на програмата, чете потребителски вход и изпълнява съответните команди.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Music Playlist Management System started. Type 'help' for commands.");

        while (isRunning) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            String commandName = parts[0].toLowerCase();
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            if (commands.containsKey(commandName)) {
                try {
                    String result = commands.get(commandName).execute(args);
                    System.out.println(result);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Unknown command. Type 'help' to see available commands.");
            }
        }
    }
}