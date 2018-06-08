package server.ftpcommands;

import server.connections.FileConnection;
import server.ftpcommands.actions.CommandResponder;
import server.ftpcommands.utils.PortParser;

import java.io.IOException;

public class CommandRegistry {

    private CommandResponder commandResponder;
    private FileConnection fileConnection;

    public CommandRegistry(CommandResponder commandResponder, FileConnection fileConnection) {
        this.commandResponder = commandResponder;
        this.fileConnection = fileConnection;
    }

    public void STOR(String fileName) {
        try {
            commandResponder.respond(150, "OK receiving File");
            fileConnection.store(fileName);
            commandResponder.respond(250, "OK File stored");
        } catch (IOException e) {
            commandResponder.respond(450, "Error storing File");
        }
    }

    public void RETR(String fileName) {
        try {
            commandResponder.respond(150, "OK getting File");
            fileConnection.retrieve(fileName);
            commandResponder.respond(250, "OK File sent");
        } catch (IOException e) {
            commandResponder.respond(450, "Error retrieving File");
        }
    }

    public void PORT(String rawIpAddress) {
        try {
            int port = PortParser.parseIpv4(rawIpAddress);
            fileConnection.setPortNumber(port);
            commandResponder.respond(200, "OK I got the Port");
        } catch (Exception e) {
            commandResponder.respond(500, "Invalid Port");
        }
    }

    public void USER(String userName) {
        commandResponder.respond(331, String.format("Hey %s, Please enter your password", userName));
    }

    public void PASS(String password) {
        boolean validPassword = password.equalsIgnoreCase("HERMES");
        if (validPassword) {
            commandResponder.respond(230, "Welcome to Mercury");
        } else {
            commandResponder.respond(430, "Bad password, please try again");
        }
    }

    public void PWD() {
        commandResponder.respond(257, fileConnection.currentDirectory());
    }

    public void CWD(String directory) {
        boolean isValidDirectory = fileConnection.isDirectory(directory);
        if (isValidDirectory) {
            fileConnection.changeWorkingDirectory(directory);
            commandResponder.respond(257, fileConnection.currentDirectory());
        } else {
            commandResponder.respond(550, "Not a valid directory");
        }
    }

    public void CDUP() {
        fileConnection.cdUp();
        commandResponder.respond(257, fileConnection.currentDirectory());
    }

    public void LIST(String path) {
        try {
            commandResponder.respond(150, "Getting a file list");
            fileConnection.sendFileList(path);
            commandResponder.respond(227, "Retrieved the listing");
        } catch (IOException e) {
            commandResponder.respond(450, "Could not get listing");
        }
    }

    public void NLST(String path) {
        try {
            commandResponder.respond(150, "Getting a list of file names");
            fileConnection.sendNameList(path);
            commandResponder.respond(227, "Retrieved the listing");
        } catch (IOException e) {
            commandResponder.respond(450, "Could not get listing");
        }
    }

    public void unrecognized() {
        commandResponder.respond(500, "Unrecognized");
    }

}
