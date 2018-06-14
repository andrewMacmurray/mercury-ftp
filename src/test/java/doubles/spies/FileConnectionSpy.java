package doubles.spies;

import server.connections.FileConnection;

import java.io.IOException;

public class FileConnectionSpy extends FileConnection {

    public String requestedFile;
    public String storedFile;
    public String requestedDirectoryList;

    public FileConnectionSpy() {
        super(null, null);
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

    @Override
    public boolean fileExists(String path) {
        return false;
    }

}
