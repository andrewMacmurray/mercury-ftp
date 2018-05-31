package server.handlers;

import filesystem.FileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHandler {

    private FileSystem fileSystem;
    private InputStream socketIn;
    private OutputStream socketOut;

    public FileHandler(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void connectStreams(InputStream in, OutputStream out) {
        socketIn = in;
        socketOut = out;
    }

    public void retrieve(String path) throws IOException {
        fileSystem.copyFromLocal(path, socketOut);
    }

    public void store(String path) throws IOException {
        fileSystem.writeFile(path, socketIn);
    }

}
