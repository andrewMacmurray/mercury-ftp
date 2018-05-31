package server;

import server.commands.Command;
import server.commands.Commands;
import server.handlers.CommandHandler;
import server.handlers.FileHandler;

import java.io.IOException;

public class CommandRegistry {

    private FileHandler fileHandler;
    private CommandHandler commandHandler;
    private Commands commands;

    public CommandRegistry(FileHandler fileHandler, CommandHandler commandHandler) {
        this.fileHandler = fileHandler;
        this.commandHandler = commandHandler;
        this.commands = new Commands(commandHandler::unrecognized);
        registerCommands();
    }

    public void executeCommand(String commandName, String argument) throws IOException {
        commands.execute(commandName, argument);
    }

    private void registerCommands() {
        commands.register(
                new Command("RETR", this::RETR),
                new Command("STOR", this::STOR)
        );
    }

    private void STOR(String fileName) {
        try {
            fileHandler.store(fileName);
            commandHandler.writeResponse(250, "OK File stored");
        } catch (IOException e) {
            commandHandler.writeResponse(450, "Something went wrong");
        }
    }

    private void RETR(String fileName) {
        try {
            fileHandler.retrieve(fileName);
            commandHandler.writeResponse(250, "OK File sent");
        } catch (IOException e) {
            commandHandler.writeResponse(450, "Something went wrong");
        }
    }

}
