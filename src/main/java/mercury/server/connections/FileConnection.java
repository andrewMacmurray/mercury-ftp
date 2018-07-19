package mercury.server.connections;

import mercury.filesystem.FtpFileSystem;
import mercury.server.connections.socket.DataSocketExecutor;
import mercury.server.connections.socket.InputStreamAction;
import mercury.server.connections.socket.OutputStreamAction;
import mercury.server.ftpcommands.utils.Address;

import java.io.IOException;

public class FileConnection {

    private DataSocketExecutor dataSocketExecutor;
    private FtpFileSystem ftpFileSystem;

    public FileConnection(FtpFileSystem ftpFileSystem, DataSocketExecutor dataSocketExecutor) {
        this.ftpFileSystem = ftpFileSystem;
        this.dataSocketExecutor = dataSocketExecutor;
    }

    public void activeMode(String host, int port) {
        dataSocketExecutor.setActiveMode(host, port);
    }

    public void passiveMode() {
        dataSocketExecutor.setPassiveMode();
    }

    public String getPassiveAddress() {
        return Address.formatIpAddress(
                dataSocketExecutor.getPassiveHost(),
                dataSocketExecutor.getPassivePort()
        );
    }

    public boolean isDirectory(String path) {
        return ftpFileSystem.isDirectory(path);
    }

    public boolean fileExists(String path) {
        return ftpFileSystem.fileExists(path);
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

    public void append(String path) throws IOException {
        runInputSocket(ftpFileSystem.append(path));
    }

    public void sendFileList(String path) throws IOException {
        runOutputSocket(ftpFileSystem.list(path));
    }

    public void sendNameList(String path) throws IOException {
        runOutputSocket(ftpFileSystem.nameList(path));
    }

    private void runOutputSocket(OutputStreamAction outputStreamAction) throws IOException {
        dataSocketExecutor.runOutputStream(outputStreamAction);
    }

    private void runInputSocket(InputStreamAction inputStreamAction) throws IOException {
        dataSocketExecutor.runInputStream(inputStreamAction);
    }

}
