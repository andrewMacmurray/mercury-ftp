package server;

import server.commands.Command;
import server.commands.CommandAction;
import server.commands.Commands;
import server.commands.CommandResponder;
import server.connections.FileConnection;

import java.io.IOException;

public class CommandRegistry {

    private FileConnection fileConnection;
    private CommandResponder commandResponder;
    private Commands commands;

    public CommandRegistry(FileConnection fileConnection, CommandResponder commandResponder) {
        this.fileConnection = fileConnection;
        this.commandResponder = commandResponder;
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
                new Command("PWD", noArg(this::PWD)),
                new Command("CDUP", noArg(this::CDUP))
        );
    }

    private CommandAction noArg(Runnable action) {
        return ignoredArg -> action.run();
    }

    private void STOR(String fileName) {
        try {
            commandResponder.respond(150, "OK receiving File");
            fileConnection.store(fileName);
            commandResponder.respond(250, "OK File stored");
        } catch (IOException e) {
            commandResponder.respond(450, "Something went wrong");
        }
    }

    private void RETR(String fileName) {
        try {
            commandResponder.respond(150, "OK getting File");
            fileConnection.retrieve(fileName);
            commandResponder.respond(250, "OK File sent");
        } catch (IOException e) {
            commandResponder.respond(450, "Something went wrong");
        }
    }

    private void PORT(String rawIpAddress) {
        try {
            int port = PortParser.parseIpv4(rawIpAddress);
            fileConnection.setPortNumber(port);
            commandResponder.respond(200, "OK I got the port");
        } catch (Exception e) {
            commandResponder.respond(500, "Invalid Port");
        }
    }

    private void USER(String userName) {
        commandResponder.respond(331, String.format("Hey %s, Please enter your password", userName));
    }

    private void PASS(String password) {
        boolean validPassword = password.equalsIgnoreCase("HERMES");
        if (validPassword) {
            commandResponder.respond(230, "Welcome to Mercury");
        } else {
            commandResponder.respond(430, "Bad password, please try again");
        }
    }

    private void PWD() {
        commandResponder.respond(257, fileConnection.currentDirectory());
    }

    private void CWD(String directory) {
        boolean isValidDirectory = fileConnection.isDirectory(directory);
        if (isValidDirectory) {
            fileConnection.changeWorkingDirectory(directory);
            commandResponder.respond(257, fileConnection.currentDirectory());
        } else {
            commandResponder.respond(550, "Not a valid directory");
        }
    }

    private void CDUP() {
        fileConnection.cdUp();
        commandResponder.respond(257, fileConnection.currentDirectory());
    }

    private void unrecognized() {
        commandResponder.respond(500, "Unrecognized");
    }

}
