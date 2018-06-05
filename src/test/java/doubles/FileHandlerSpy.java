package doubles;

import filesystem.NativeFileSystem;
import server.handlers.FileHandler;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;

public class FileHandlerSpy extends FileHandler {

    public String requestedFile;
    public String storedFile;

    public FileHandlerSpy(NativeFileSystem fileSystem, SocketExecutor socketExecutor) {
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

}
