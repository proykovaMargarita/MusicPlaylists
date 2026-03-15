package bg.tu_varna.sit.f24621684.engine;

import bg.tu_varna.sit.f24621684.commands.Command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Engine {
    private final Map<String, Command> commands = new HashMap<>();
    private final StateManager stateManager;
    private boolean isRunning = true;

    public Engine(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void registerAll(Map<String, Command> commandMap) {
        this.commands.putAll(commandMap);
    }

    public void stop() {
        this.isRunning = false;
    }

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
                    // Изпълнение и принтиране на резултата
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
