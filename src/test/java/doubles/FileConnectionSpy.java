package doubles;

import filesystem.FtpFileSystem;
import server.connections.FileConnection;
import server.connections.socket.SocketExecutor;

import java.io.IOException;

public class FileConnectionSpy extends FileConnection {

    public String requestedFile;
    public String storedFile;
    public String requestedDirectoryList;

    public FileConnectionSpy(FtpFileSystem fileSystem, SocketExecutor socketExecutor) {
        super(fileSystem, socketExecutor);
    }

    @Override
    public void retrieve(String path) throws IOException {
        this.requestedFile = path;
    }

    @Override
    public void store(String path) throws IOException {
        this.storedFile = path;
    }

    @Override
    public void sendFileList(String path) throws IOException {
        this.requestedDirectoryList = path;
    }

}
