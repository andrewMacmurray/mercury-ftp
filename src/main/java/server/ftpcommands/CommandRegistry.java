package server.ftpcommands;

import server.connections.FileConnection;
import server.ftpcommands.actions.CommandResponder;
import server.ftpcommands.utils.NameGenerator;
import server.ftpcommands.utils.PortParser;

import java.io.IOException;

public class CommandRegistry {

    private CommandResponder commandResponder;
    private FileConnection fileConnection;
    private NameGenerator nameGenerator;

    public CommandRegistry(CommandResponder commandResponder, FileConnection fileConnection) {
        this.commandResponder = commandResponder;
        this.fileConnection = fileConnection;
        this.nameGenerator = new NameGenerator(fileConnection::isUniqueFileName);
    }

    public void STOR(String fileName) {
        try {
            sendResponse(150, "OK receiving File");
            fileConnection.store(fileName);
            sendFormattedResponse(250, "OK %s stored" , fileName);
        } catch (IOException e) {
            sendResponse(450, "Error storing File");
        }
    }

    public void STOU(String fileName) {
        String uniqueName = nameGenerator.generateUnique(fileName);
        STOR(uniqueName);
    }

    public void RETR(String fileName) {
        try {
            sendResponse(150, "OK getting File");
            fileConnection.retrieve(fileName);
            sendFormattedResponse(250, "OK %s sent" , fileName);
        } catch (IOException e) {
            sendResponse(450, "Error retrieving File");
        }
    }

    public void PORT(String rawIpAddress) {
        try {
            int port = PortParser.parseIpv4(rawIpAddress);
            fileConnection.setPortNumber(port);
            sendResponse(200, "OK I got the Port");
        } catch (Exception e) {
            sendResponse(500, "Invalid Port");
        }
    }

    public void USER(String userName) {
        sendFormattedResponse(331, "Hey %s, Please enter your password", userName);
    }

    public void PASS(String password) {
        boolean validPassword = password.equalsIgnoreCase("HERMES");
        if (validPassword) {
            sendResponse(230, "Welcome to Mercury");
        } else {
            sendResponse(430, "Bad password, please try again");
        }
    }

    public void PWD() {
        sendResponse(257, fileConnection.currentDirectory());
    }

    public void CWD(String directory) {
        boolean isValidDirectory = fileConnection.isDirectory(directory);
        if (isValidDirectory) {
            fileConnection.changeWorkingDirectory(directory);
            sendResponse(257, fileConnection.currentDirectory());
        } else {
            sendResponse(550, "Not a valid directory");
        }
    }

    public void CDUP() {
        fileConnection.cdUp();
        sendResponse(257, fileConnection.currentDirectory());
    }

    public void LIST(String path) {
        try {
            sendResponse(150, "Getting a file list");
            fileConnection.sendFileList(path);
            sendResponse(227, "Retrieved the listing");
        } catch (IOException e) {
            sendResponse(450, "Could not get listing");
        }
    }

    public void NLST(String path) {
        try {
            sendResponse(150, "Getting a list of file names");
            fileConnection.sendNameList(path);
            sendResponse(227, "Retrieved the listing");
        } catch (IOException e) {
            sendResponse(450, "Could not get listing");
        }
    }

    public void unrecognized() {
        sendResponse(500, "Unrecognized");
    }

    private void sendFormattedResponse(int code, String message, String fileName) {
        sendResponse(code, String.format(message, fileName));
    }
    
    private void sendResponse(int code, String message) {
        commandResponder.respond(code, message);
    }

}
