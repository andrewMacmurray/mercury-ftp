package server;

import doubles.FakeFileSystem;
import doubles.FakeSocketExecutor;
import org.junit.Before;
import org.junit.Test;
import server.handlers.FileHandler;
import server.handlers.connection.SocketExecutor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class FileHandlerTest {

    private ByteArrayOutputStream socketOut;
    private FakeFileSystem fakeFileSystem;
    private FileHandler fileHandler;

    @Before
    public void setup() {
        ByteArrayInputStream socketIn = new ByteArrayInputStream("socket\ninput".getBytes());
        socketOut = new ByteArrayOutputStream();
        fakeFileSystem = new FakeFileSystem("hello", "world");
        SocketExecutor socketExecutor = new FakeSocketExecutor(socketIn, socketOut);
        fileHandler = new FileHandler(fakeFileSystem, socketExecutor);
    }

    @Test
    public void retrieve() throws IOException {
        fileHandler.retrieve("hello.txt");
        assertEquals("hello\nworld\n", socketOut.toString());
    }

    @Test
    public void store() throws IOException {
        fileHandler.store("socket.txt");
        assertEquals(Arrays.asList("socket", "input"), fakeFileSystem.getOutput());
    }

}
