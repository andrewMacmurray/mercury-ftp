package server;

import server.commands.Command;
import server.commands.CommandAction;
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
                new Command("PORT", this::PORT),
                new Command("USER", this::USER),
                new Command("PASS", this::PASS),
                new Command("CWD", this::CWD),
                new Command("PWD", noArg(this::PWD))
        );
    }

    private CommandAction noArg(Runnable action) {
        return ignoredArg -> action.run();
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

    private void PORT(String rawIpAddress) {
        try {
            int port = PortParser.parse(rawIpAddress);
            fileHandler.setPortNumber(port);
            statusResponder.respond(200, "OK I got the port");
        } catch (Exception e) {
            statusResponder.respond(500, "Invalid Port");
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

    private void PWD() {
        statusResponder.respond(257, fileHandler.currentDirectory());
    }

    private void CWD(String directory) {
        boolean isValidDirectory = fileHandler.isDirectory(directory);
        if (isValidDirectory) {
            fileHandler.changeWorkingDirectory(directory);
            statusResponder.respond(257, fileHandler.currentDirectory());
        } else {
            statusResponder.respond(550, "Not a valid directory");
        }
    }

    private void unrecognized() {
        statusResponder.respond(500, "Unrecognized");
    }

}
