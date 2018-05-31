package doubles;

import filesystem.FileSystem;
import server.handlers.FileHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHandlerSpy extends FileHandler {

    public String requestedFile;
    public String storedFile;

    public FileHandlerSpy(InputStream socketIn, OutputStream socketOut, FileSystem fileSystem) {
        super(socketIn, socketOut, fileSystem);
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
