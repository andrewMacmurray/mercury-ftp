package server;

import server.handlers.FileHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FileConnection {

    private FileHandler fileHandler;
    private CommandInterpreter commandInterpreter;

    public FileConnection(FileHandler fileHandler, CommandInterpreter commandInterpreter) {
        this.fileHandler = fileHandler;
        this.commandInterpreter = commandInterpreter;
    }

    public void processCommand(String rawCommand, Socket fileSocket) throws IOException {
        connectSocketStreams(fileSocket);
        commandInterpreter.execute(rawCommand);
        fileSocket.close();
    }

    private void connectSocketStreams(Socket socket) throws IOException {
        InputStream fileIn = socket.getInputStream();
        OutputStream fileOut = socket.getOutputStream();
        fileHandler.connectStreams(fileIn, fileOut);
    }

}
