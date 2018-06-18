package mercury.server.ftpcommands;

import mercury.server.connections.CommandResponses;
import mercury.server.connections.FileConnection;
import mercury.server.ftpcommands.utils.Address;
import mercury.server.ftpcommands.utils.NameGenerator;

import java.io.IOException;

public class CommandRegistry {

    private CommandResponses commandResponses;
    private FileConnection fileConnection;
    private NameGenerator nameGenerator;

    public CommandRegistry(
            CommandResponses commandResponses,
            FileConnection fileConnection,
            NameGenerator nameGenerator
    ) {
        this.commandResponses = commandResponses;
        this.fileConnection = fileConnection;
        this.nameGenerator = nameGenerator;
    }

    public void STOR(String fileName) {
        commandResponses.gettingResource("OK receiving File");

        try {
            fileConnection.store(fileName);
            commandResponses.fileStored(fileName);
        } catch (IOException e) {
            commandResponses.fileError("Error storing File");
        }
    }

    public void STOU(String fileName) {
        String uniqueName = nameGenerator.generateUnique(fileName);
        STOR(uniqueName);
    }

    public void RETR(String fileName) {
        commandResponses.gettingResource("OK getting File");

        try {
            fileConnection.retrieve(fileName);
            commandResponses.fileSent(fileName);
        } catch (IOException e) {
            commandResponses.fileError("Error retrieving File");
        }
    }

    public void APPE(String fileName) {
        commandResponses.gettingResource("OK receiving data");

        try {
            if (fileConnection.fileExists(fileName)) {
                fileConnection.append(fileName);
                commandResponses.fileAppended(fileName);
            } else {
                fileConnection.store(fileName);
                commandResponses.fileStored(fileName);
            }
        } catch (IOException e) {
            commandResponses.fileError("Error appending file");
        }
    }

    public void PORT(String rawIpAddress) {
        try {
            int port = Address.getIpv4Port(rawIpAddress);
            String host = Address.getIpv4Host(rawIpAddress);
            fileConnection.activeMode(host, port);
            commandResponses.commandSuccess("OK I got the Port");
        } catch (Exception e) {
            commandResponses.commandFailure("Invalid Port");
        }
    }

    public void PASV() {
        try {
            fileConnection.passiveMode();
            commandResponses.passiveConnectionSuccess(fileConnection.getPassiveAddress());
        } catch (IOException e) {
            commandResponses.commandFailure("Error setting passive mode");
        }
    }

    public void USER(String userName) {
        commandResponses.enterPassword(userName);
    }

    public void PASS(String password) {
        boolean validPassword = password.equalsIgnoreCase("HERMES");

        if (validPassword) {
            commandResponses.welcome();
        } else {
            commandResponses.badPassword();
        }
    }

    public void PWD() {
        commandResponses.directorySuccess(fileConnection.currentDirectory());
    }

    public void CWD(String directory) {
        boolean isValidDirectory = fileConnection.isDirectory(directory);

        if (isValidDirectory) {
            fileConnection.changeWorkingDirectory(directory);
            commandResponses.directorySuccess(fileConnection.currentDirectory());
        } else {
            commandResponses.invalidDirectory();
        }
    }

    public void CDUP() {
        fileConnection.cdUp();
        commandResponses.directorySuccess(fileConnection.currentDirectory());
    }

    public void LIST(String path) {
        commandResponses.gettingResource("Getting a file list");

        try {
            fileConnection.sendFileList(path);
            commandResponses.listingSuccess();
        } catch (IOException e) {
            commandResponses.fileError("Could not get listing");
        }
    }

    public void NLST(String path) {
        commandResponses.gettingResource("Getting a list of file names");

        try {
            fileConnection.sendNameList(path);
            commandResponses.listingSuccess();
        } catch (IOException e) {
            commandResponses.fileError("Could not get listing");
        }
    }

    public void unrecognized() {
        commandResponses.unrecognized();
    }

}
