package doubles;

import filesystem.NativeFileSystem;
import server.handlers.FileHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHandlerSpy extends FileHandler {

    public String requestedFile;
    public String storedFile;
    public boolean streamsConnected = false;

    public FileHandlerSpy(NativeFileSystem fileSystem) {
        super(fileSystem);
    }

    @Override
    public void connectStreams(InputStream in, OutputStream out) {
        this.streamsConnected = true;
    }

    @Override
    public void retrieve(String path) throws IOException {
        this.requestedFile = path;
    }

    @Override
    public void store(String path) throws IOException {
        this.storedFile = path;
    }

}
