package server.handlers;

import filesystem.FtpFileSystem;
import filesystem.NativeFileSystem;
import filesystem.WorkingDirectory;
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

    public boolean isDirectory(String path) {
        return ftpFileSystem.isDirectory(path);
    }

    public void cdUp() {
        ftpFileSystem.cdUp();
    }

    public void changeWorkingDirectory(String path) {
        ftpFileSystem.changeWorkingDirectory(path);
    }

    public String currentDirectory() {
        return ftpFileSystem.getCurrentWorkingDirectory();
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
