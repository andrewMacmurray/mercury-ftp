package server;

import doubles.FakeSocketExecutor;
import doubles.FileSystemSpy;
import org.junit.Before;
import org.junit.Test;
import server.connections.FileConnection;
import server.connections.socket.SocketExecutor;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileConnectionTest {

    private FileSystemSpy fileSystemSpy;
    private FileConnection fileConnection;

    @Before
    public void setup() {
        fileSystemSpy = new FileSystemSpy();
        SocketExecutor socketExecutor = new FakeSocketExecutor();
        fileConnection = new FileConnection(fileSystemSpy, socketExecutor);
    }

    @Test
    public void retrieve() throws IOException {
        fileConnection.retrieve("hello.txt");
        assertEquals("hello.txt", fileSystemSpy.retrievedFile);
    }

    @Test
    public void store() throws IOException {
        fileConnection.store("socket.txt");
        assertEquals("socket.txt", fileSystemSpy.storedFile);
    }

}
