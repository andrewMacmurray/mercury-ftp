package server;

import server.commands.CommandExecutor;

import java.io.IOException;

public class CommandInterpreter {

    private CommandExecutor commandExecutor;

    public CommandInterpreter(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public void execute(String rawCommand) {
        String[] args = rawCommand.split(" ");
        if (args.length > 1) {
            commandExecutor.run(args[0], args[1]);
        } else {
            commandExecutor.run(args[0], "");
        }
    }

}
