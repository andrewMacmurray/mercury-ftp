package server.handlers;

import filesystem.NativeFileSystem;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;

public class FileHandler {

    private NativeFileSystem fileSystem;
    private SocketExecutor dataSocketExecutor;
    private int portNumber;

    public FileHandler(NativeFileSystem fileSystem, SocketExecutor dataSocketExecutor) {
        this.fileSystem = fileSystem;
        this.dataSocketExecutor = dataSocketExecutor;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void retrieve(String path) throws IOException {
        dataSocketExecutor.outputStream("localhost", portNumber, socketOut -> {
            fileSystem.copyFromLocal(path, socketOut);
        });
    }

    public void store(String path) throws IOException {
        dataSocketExecutor.inputStream("localhost", portNumber, socketIn -> {
            fileSystem.writeFile(path, socketIn);
        });
    }

}
