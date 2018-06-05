package server.handlers;

import filesystem.NativeFileSystem;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;

public class FileHandler {

    private int portNumber;
    private SocketExecutor dataSocketExecutor;
    private FtpFileSystem ftpFileSystem;

    public FileHandler(NativeFileSystem fileSystem, SocketExecutor dataSocketExecutor) {
        this.ftpFileSystem = new FtpFileSystem(fileSystem, new WorkingDirectory());
        this.dataSocketExecutor = dataSocketExecutor;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void retrieve(String path) throws IOException {
        dataSocketExecutor.outputStream("localhost", portNumber, socketOut -> {
            ftpFileSystem.retrieve(path).runWithStream(socketOut);
        });
    }

    public void store(String path) throws IOException {
        dataSocketExecutor.inputStream("localhost", portNumber, socketIn -> {
            ftpFileSystem.store(path).runWithStream(socketIn);
        });
    }

}
