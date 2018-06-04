package server;

import doubles.FakeFileSystem;
import doubles.FakeSocket;
import doubles.FakeSocketExecutor;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionIOTest {

    private ByteArrayOutputStream socketOut;
    private FakeSocket fakeSocket;
    private ConnectionIO connectionIO;

    @Before
    public void setup() throws IOException {
        ByteArrayInputStream socketIn = new ByteArrayInputStream("RETR hello.txt\n".getBytes());
        socketOut = new ByteArrayOutputStream();
        fakeSocket = new FakeSocket(socketIn, socketOut);
        connectionIO = new ConnectionIO(
                fakeSocket,
                new FakeFileSystem(),
                new FakeSocketExecutor()
        );
    }

    @Test
    public void clientConnectedMessage() throws IOException {
        connectionIO.clientConnected();
        assertEquals("200 Connected to Mercury", socketOut.toString().trim());
    }

    @Test
    public void closeConnection() throws IOException {
        connectionIO.clientDisconnect();
        assertEquals("421 Terminating Connection", socketOut.toString().trim());
        assertTrue(fakeSocket.closed);
    }

    @Test
    public void readCommand() throws IOException {
        String command = connectionIO.readCommand();
        assertEquals("RETR hello.txt", command);
    }

    @Test
    public void executeCommand() throws IOException {
        connectionIO.executeCommand("RETR hello.txt");
        assertTrue(socketOut.toString().contains("150 OK getting File"));
        assertTrue(socketOut.toString().contains("250 OK File sent"));
    }

}
