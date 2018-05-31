package server;

import server.commands.IOBiConsumer;

import java.io.IOException;

public class CommandInterpreter {

    private IOBiConsumer<String, String> commandExecutor;

    public CommandInterpreter(IOBiConsumer<String, String> commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public void execute(String rawCommand) throws IOException {
        String[] args = rawCommand.split(" ");
        if (args.length > 1) {
            commandExecutor.accept(args[0], args[1]);
        } else {
            commandExecutor.accept(args[0], "");
        }
    }

}
