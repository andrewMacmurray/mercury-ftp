package server;

import server.commands.CommandExecutor;
import server.handlers.CommandHandler;
import server.handlers.FileHandler;

import java.io.IOException;

public class CommandInterpreter {

    private CommandRegistry commandRegistry;
    private CommandHandler commandHandler;

    public CommandInterpreter(CommandHandler commandHandler, FileHandler fileHandler) {
        this.commandHandler = commandHandler;
        this.commandRegistry = new CommandRegistry(fileHandler, commandHandler::writeResponse);
        clientConnectedResponse();
    }

    public void processCommands() throws IOException {
        processCommand();
        commandHandler.writeResponse(421, "Terminating Connection");
    }

    private void clientConnectedResponse() {
        commandHandler.writeResponse(200, "Connected to Mercury");
    }

    private void processCommand() throws IOException {
        String rawCommand = commandHandler.readCommand();
        if (shouldExecuteCommand(rawCommand)) {
            execute(rawCommand);
            processCommand();
        }
    }

    private boolean shouldExecuteCommand(String rawCommand) {
        return rawCommand != null && !"QUIT".equalsIgnoreCase(rawCommand);
    }

    private void execute(String rawCommand) {
        CommandExecutor.run(rawCommand, commandRegistry::executeCommand);
    }

}
