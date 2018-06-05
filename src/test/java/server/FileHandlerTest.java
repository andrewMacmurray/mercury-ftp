package server;

import doubles.FakeSocketExecutor;
import doubles.FileSystemSpy;
import org.junit.Before;
import org.junit.Test;
import server.handlers.FileHandler;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileHandlerTest {

    private FileSystemSpy fileSystemSpy;
    private FileHandler fileHandler;

    @Before
    public void setup() {
        fileSystemSpy = new FileSystemSpy();
        SocketExecutor socketExecutor = new FakeSocketExecutor();
        fileHandler = new FileHandler(fileSystemSpy, socketExecutor);
    }

    @Test
    public void retrieve() throws IOException {
        fileHandler.retrieve("hello.txt");
        assertEquals("hello.txt", fileSystemSpy.retrievedFile);
    }

    @Test
    public void store() throws IOException {
        fileHandler.store("socket.txt");
        assertEquals("socket.txt", fileSystemSpy.storedFile);
    }

}
