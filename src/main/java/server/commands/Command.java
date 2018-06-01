package server.commands;

import java.io.IOException;

public class Command {

    private String name;
    private CommandAction action;

    public Command(String name, CommandAction action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void run(String argument) throws IOException {
        action.run(argument);
    }

}
