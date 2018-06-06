package server;

import server.commands.CommandExecutor;
import server.connections.CommandConnection;
import server.connections.FileConnection;

import java.io.IOException;

public class CommandInterpreter {

    private CommandRegistry commandRegistry;
    private CommandConnection commandConnection;

    public CommandInterpreter(CommandConnection commandConnection, FileConnection fileConnection) {
        this.commandConnection = commandConnection;
        this.commandRegistry = new CommandRegistry(fileConnection, commandConnection::writeResponse);
    }

    public void processCommands() throws IOException {
        clientConnectedResponse();
        processNextCommand();
        disconnectedResponse();
    }

    private void clientConnectedResponse() {
        commandConnection.writeResponse(200, "Connected to Mercury");
    }

    private void disconnectedResponse() {
        commandConnection.writeResponse(421, "Disconnected from Mercury");
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
        return rawCommand != null && !"QUIT".equalsIgnoreCase(rawCommand);
    }

    private void execute(String rawCommand) {
        CommandExecutor.run(rawCommand, commandRegistry::executeCommand);
    }

}
