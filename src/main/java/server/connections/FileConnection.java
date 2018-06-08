package server.connections;

import filesystem.FtpFileSystem;
import server.connections.socket.InputStreamAction;
import server.connections.socket.OutputStreamAction;
import server.connections.socket.SocketExecutor;

import java.io.IOException;

public class FileConnection {

    private int portNumber;
    private String host = "localhost";
    private SocketExecutor socketExecutor;
    private FtpFileSystem ftpFileSystem;

    public FileConnection(FtpFileSystem ftpFileSystem, SocketExecutor socketExecutor) {
        this.ftpFileSystem = ftpFileSystem;
        this.socketExecutor = socketExecutor;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public boolean isDirectory(String path) {
        return ftpFileSystem.isDirectory(path);
    }

    public boolean isUniqueFileName(String path) {
        return !ftpFileSystem.fileExists(path);
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
        runOutputSocket(ftpFileSystem.retrieve(path));
    }

    public void store(String path) throws IOException {
        runInputSocket(ftpFileSystem.store(path));
    }

    public void sendFileList(String path) throws IOException {
        runOutputSocket(ftpFileSystem.list(path));
    }

    public void sendNameList(String path) throws IOException {
        runOutputSocket(ftpFileSystem.nameList(path));
    }

    private void runOutputSocket(OutputStreamAction outputStreamAction) throws IOException {
        socketExecutor.outputStream(host, portNumber, outputStreamAction);
    }

    private void runInputSocket(InputStreamAction inputStreamAction) throws IOException {
        socketExecutor.inputStream(host, portNumber, inputStreamAction);
    }

}
