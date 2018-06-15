package doubles.stubs;

import server.connections.FileConnection;

import java.io.IOException;

public class ErroringFileConnectionStub extends FileConnection {

    public ErroringFileConnectionStub() {
        super(null, null);
    }

    @Override
    public void retrieve(String fileName) throws IOException {
        throw new IOException();
    }

    @Override
    public void store(String fileName) throws IOException {
        throw new IOException();
    }

    @Override
    public void append(String fileName) throws IOException {
        throw new IOException();
    }

    @Override
    public boolean fileExists(String fileName) {
        return true;
    }

    @Override
    public void sendFileList(String path) throws IOException {
        throw new IOException();
    }

    @Override
    public void sendNameList(String path) throws IOException {
        throw new IOException();
    }

    @Override
    public void passiveMode() throws IOException {
        throw new IOException();
    }

}
