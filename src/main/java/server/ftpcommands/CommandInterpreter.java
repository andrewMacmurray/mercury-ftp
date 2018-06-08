package server.ftpcommands;

import server.connections.CommandConnection;
import server.connections.FileConnection;
import server.ftpcommands.actions.CommandExecutor;

import java.io.IOException;

public class CommandInterpreter {

    private CommandConnection commandConnection;
    private Commands commands;

    public CommandInterpreter(CommandConnection commandConnection, FileConnection fileConnection) {
        this.commandConnection = commandConnection;
        this.commands = new CommandsFactory(commandConnection::writeResponse, fileConnection).build();
    }

    public void processCommands() throws IOException {
        clientConnectedResponse();
        processNextCommand();
        disconnectedResponse();
    }

    private void clientConnectedResponse() {
        commandConnection.signalConnected();
    }

    private void disconnectedResponse() {
        commandConnection.signalDisconnect();
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
        return !commandConnection.isDisconnectCommand(rawCommand);
    }

    private void execute(String rawCommand) {
        CommandExecutor.run(rawCommand, commands::execute);
    }

}
