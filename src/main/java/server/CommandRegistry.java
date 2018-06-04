package server;

import server.commands.Command;
import server.commands.Commands;
import server.commands.StatusResponder;
import server.handlers.FileHandler;

import java.io.IOException;

public class CommandRegistry {

    private FileHandler fileHandler;
    private StatusResponder statusResponder;
    private Commands commands;

    public CommandRegistry(FileHandler fileHandler, StatusResponder statusResponder) {
        this.fileHandler = fileHandler;
        this.statusResponder = statusResponder;
        this.commands = new Commands(this::unrecognized);
        registerCommands();
    }

    public void executeCommand(String commandName, String argument) {
        commands.execute(commandName, argument);
    }

    private void registerCommands() {
        commands.register(
                new Command("RETR", this::RETR),
                new Command("STOR", this::STOR),
                new Command("USER", this::USER),
                new Command("PASS", this::PASS)
        );
    }

    private void STOR(String fileName) {
        try {
            statusResponder.respond(150, "OK receiving File");
            fileHandler.store(fileName);
            statusResponder.respond(250, "OK File stored");
        } catch (IOException e) {
            statusResponder.respond(450, "Something went wrong");
        }
    }

    private void RETR(String fileName) {
        try {
            statusResponder.respond(150, "OK getting File");
            fileHandler.retrieve(fileName);
            statusResponder.respond(250, "OK File sent");
        } catch (IOException e) {
            statusResponder.respond(450, "Something went wrong");
        }
    }

    private void USER(String userName) {
        statusResponder.respond(331, String.format("Hey %s, Please enter your password", userName));
    }

    private void PASS(String password) {
        boolean validPassword = password.equalsIgnoreCase("HERMES");
        if (validPassword) {
            statusResponder.respond(230, "Welcome to Mercury");
        } else {
            statusResponder.respond(430, "Bad password, please try again");
        }
    }

    private void unrecognized() {
        statusResponder.respond(500, "Unrecognized");
    }

}
