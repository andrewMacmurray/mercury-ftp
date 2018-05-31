package server;

import server.commands.Command;
import server.commands.Commands;
import server.handlers.CommandHandler;
import server.handlers.FileHandler;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class CommandRegistry {

    private FileHandler fileHandler;
    private BiConsumer<Integer, String> responseHandler;
    private Commands commands;

    public CommandRegistry(FileHandler fileHandler, BiConsumer<Integer, String> responseHandler) {
        this.fileHandler = fileHandler;
        this.responseHandler = responseHandler;
        this.commands = new Commands(this::unrecognized);
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
            responseHandler.accept(150, "OK receiving file");
            fileHandler.store(fileName);
            responseHandler.accept(250, "OK File stored");
        } catch (IOException e) {
            responseHandler.accept(450, "Something went wrong");
        }
    }

    private void RETR(String fileName) {
        try {
            responseHandler.accept(150, "OK getting file");
            fileHandler.retrieve(fileName);
            responseHandler.accept(250, "OK File sent");
        } catch (IOException e) {
            responseHandler.accept(450, "Something went wrong");
        }
    }

    private void unrecognized() {
        responseHandler.accept(500, "Unrecognized");
    }

}
