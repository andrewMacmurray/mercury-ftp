package server.ftpcommands;

import server.connections.CommandConnection;
import server.connections.CommandResponses;
import server.connections.FileConnection;
import server.ftpcommands.actions.CommandExecutor;
import server.ftpcommands.actions.CommandReader;

import java.io.IOException;

public class CommandInterpreter {

    private CommandReader commandReader;
    private CommandResponses commandResponses;
    private Commands commands;

    public CommandInterpreter(CommandConnection commandConnection, FileConnection fileConnection) {
        this.commandReader = commandConnection::readCommand;
        this.commandResponses = new CommandResponses(commandConnection::writeResponse);
        this.commands = new CommandsFactory(commandResponses, fileConnection).build();
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
        String rawCommand = commandReader.readLine();
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
