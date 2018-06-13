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
        this.nameGenerator = new NameGenerator(fileConnection::fileExists);
    }

    public void STOR(String fileName) {
        try {
            gettingResource("OK receiving File");
            fileConnection.store(fileName);
            fileSuccessResponse("OK %s stored", fileName);
        } catch (IOException e) {
            fileError("Error storing File");
        }
    }

    public void STOU(String fileName) {
        String uniqueName = nameGenerator.generateUnique(fileName);
        STOR(uniqueName);
    }

    public void RETR(String fileName) {
        try {
            gettingResource("OK getting File");
            fileConnection.retrieve(fileName);
            fileSuccessResponse("OK %s sent", fileName);
        } catch (IOException e) {
            fileError("Error retrieving File");
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
        directorySuccessResponse(fileConnection.currentDirectory());
    }

    public void CWD(String directory) {
        boolean isValidDirectory = fileConnection.isDirectory(directory);
        if (isValidDirectory) {
            fileConnection.changeWorkingDirectory(directory);
            directorySuccessResponse(fileConnection.currentDirectory());
        } else {
            sendResponse(550, "Not a valid directory");
        }
    }

    public void CDUP() {
        fileConnection.cdUp();
        directorySuccessResponse(fileConnection.currentDirectory());
    }

    public void LIST(String path) {
        try {
            gettingResource("Getting a file list");
            fileConnection.sendFileList(path);
            listingSuccess();
        } catch (IOException e) {
            fileError("Could not get listing");
        }
    }

    public void NLST(String path) {
        try {
            gettingResource("Getting a list of file names");
            fileConnection.sendNameList(path);
            listingSuccess();
        } catch (IOException e) {
            fileError("Could not get listing");
        }
    }

    public void unrecognized() {
        sendResponse(500, "Unrecognized");
    }

    private void gettingResource(String message) {
        sendResponse(150, message);
    }

    private void listingSuccess() {
        sendResponse(227, "Retrieved the listing");
    }

    private void directorySuccessResponse(String message) {
        sendResponse(257, message);
    }

    private void fileError(String message) {
        sendResponse(450, message);
    }

    private void fileSuccessResponse(String message, String fileName) {
        sendFormattedResponse(250, message, fileName);
    }

    private void sendFormattedResponse(int code, String message, String fileName) {
        sendResponse(code, String.format(message, fileName));
    }

    private void sendResponse(int code, String message) {
        commandResponder.respond(code, message);
    }

}
