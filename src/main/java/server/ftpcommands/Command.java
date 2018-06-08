package server.ftpcommands;

import server.ftpcommands.actions.CommandAction;

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

    public void run(String argument) {
        action.run(argument);
    }

}
