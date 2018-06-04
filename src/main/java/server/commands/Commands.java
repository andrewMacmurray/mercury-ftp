package server.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Commands {

    private Map<String, Command> registeredCommands = new HashMap<>();
    private Runnable unrecognizedCallback;

    public Commands(Runnable unrecognizedCallback) {
        this.unrecognizedCallback = unrecognizedCallback;
    }

    public void register(Command... commands) {
        for (Command command : commands) {
            registeredCommands.put(command.getName(), command);
        }
    }

    public void execute(String commandName, String argument) {
        Command command = registeredCommands.get(commandName);
        if (command != null) {
            command.run(argument);
        } else {
            unrecognizedCallback.run();
        }
    }

}
