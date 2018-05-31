package server;

import doubles.FakeFileSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DataConnectionTest {

    private ByteArrayOutputStream socketOut;
    private FakeFileSystem fakeFileSystem;
    private DataConnection connection;

    @Before
    public void setup() {
        ByteArrayInputStream socketIn = new ByteArrayInputStream("socket\ninput".getBytes());
        socketOut = new ByteArrayOutputStream();
        fakeFileSystem = new FakeFileSystem("hello", "world");
        connection = new DataConnection(socketIn, socketOut, fakeFileSystem);
    }

    @Test
    public void retrieve() throws IOException {
        connection.retrieve("hello.txt");
        assertEquals("hello\nworld\n", socketOut.toString());
    }

    @Test
    public void store() throws IOException {
        connection.store("socket.txt");
        assertEquals(Arrays.asList("socket", "input"), fakeFileSystem.getOutput());
    }

}
