package server.commands;

import java.io.IOException;

public class Command {

    private String name;
    private IOConsumer action;

    public Command(String name, IOConsumer action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void run(String argument) throws IOException {
        action.accept(argument);
    }

}
