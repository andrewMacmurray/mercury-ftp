package server;

import server.handlers.CommandHandler;
import server.handlers.FileHandler;

import java.io.IOException;

public class InterpreterFactory {

    public static CommandInterpreter create(FileHandler fileHandler, CommandHandler commandHandler) throws IOException {
       CommandRegistry commandRegistry = new CommandRegistry(fileHandler, commandHandler::writeResponse);
       return new CommandInterpreter(commandRegistry::executeCommand);
    }

}
