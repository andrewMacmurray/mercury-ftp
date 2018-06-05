package server.connections;

import filesystem.FtpFileSystem;
import filesystem.NativeFileSystem;
import filesystem.WorkingDirectory;
import server.connections.socket.SocketExecutor;

import java.io.IOException;

public class FileConnection {

    private int portNumber;
    private SocketExecutor socketExecutor;
    private FtpFileSystem ftpFileSystem;

    public FileConnection(NativeFileSystem fileSystem, SocketExecutor socketExecutor) {
        this.ftpFileSystem = new FtpFileSystem(fileSystem, new WorkingDirectory());
        this.socketExecutor = socketExecutor;
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
        socketExecutor.outputStream("localhost", portNumber, socketOut -> {
            ftpFileSystem.retrieve(path).runWithStream(socketOut);
        });
    }

    public void store(String path) throws IOException {
        socketExecutor.inputStream("localhost", portNumber, socketIn -> {
            ftpFileSystem.store(path).runWithStream(socketIn);
        });
    }

}
