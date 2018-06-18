package mercury.server.ftpcommands;

import mercury.server.connections.CommandConnection;
import mercury.server.connections.CommandResponses;
import mercury.server.ftpcommands.actions.CommandExecutor;

import java.io.IOException;

public class CommandInterpreter {

    private CommandConnection commandConnection;
    private CommandResponses commandResponses;
    private Commands commands;

    public CommandInterpreter(
            CommandConnection commandConnection,
            CommandResponses commandResponses,
            Commands commands
    ) {
        this.commandConnection = commandConnection;
        this.commandResponses = commandResponses;
        this.commands = commands;
    }

    public void processCommands() throws IOException {
        clientConnectedResponse();
        processNextCommand();
        disconnectedResponse();
    }

    private void clientConnectedResponse() {
        commandResponses.signalConnected();
    }

    private void disconnectedResponse() {
        commandResponses.signalDisconnect();
    }

    private void processNextCommand() throws IOException {
        String rawCommand = commandConnection.readCommand();
        System.out.println(rawCommand);
        if (shouldExecuteCommand(rawCommand)) {
            execute(rawCommand);
            processNextCommand();
        }
    }

    private boolean shouldExecuteCommand(String rawCommand) {
        return !commandResponses.isDisconnectCommand(rawCommand);
    }

    private void execute(String rawCommand) {
        CommandExecutor.run(rawCommand, commands::execute);
    }

}
