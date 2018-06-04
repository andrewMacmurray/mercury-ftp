package server.handlers;

import filesystem.NativeFileSystem;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHandler {

    private NativeFileSystem fileSystem;
    private SocketExecutor socketExecutor;
    private int portNumber;

    public FileHandler(NativeFileSystem fileSystem, SocketExecutor socketExecutor) {
        this.fileSystem = fileSystem;
        this.socketExecutor = socketExecutor;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void retrieve(String path) throws IOException {
        socketExecutor.outputStream("localhost", portNumber, socketOut -> {
            fileSystem.copyFromLocal(path, socketOut);
        });
    }

    public void store(String path) throws IOException {
        socketExecutor.inputStream("localhost", portNumber, socketIn -> {
            fileSystem.writeFile(path, socketIn);
        });
    }

}
