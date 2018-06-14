package doubles.stubs;

import filesystem.FtpFileSystem;
import server.connections.FileConnection;
import server.connections.socket.SocketExecutor;

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
