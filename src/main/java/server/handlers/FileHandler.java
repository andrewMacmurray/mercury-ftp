package server.handlers;

import filesystem.FileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHandler {

    private FileSystem fileSystem;
    private InputStream socketIn;
    private OutputStream socketOut;

    public FileHandler(InputStream socketIn, OutputStream socketOut, FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        this.socketIn = socketIn;
        this.socketOut = socketOut;
    }

    public void retrieve(String path) throws IOException {
        fileSystem.copyFromLocal(path, socketOut);
    }

    public void store(String path) throws IOException {
        fileSystem.writeFile(path, socketIn);
    }

}
