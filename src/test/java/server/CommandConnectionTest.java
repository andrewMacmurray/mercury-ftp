package server;

import org.junit.Before;
import org.junit.Test;
import server.connections.CommandConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandConnectionTest {

    private ByteArrayInputStream in = null;
    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        out = new ByteArrayOutputStream();
    }

    @Test
    public void readCommand() throws IOException {
        in = new ByteArrayInputStream("RETR hello.txt".getBytes());

        CommandConnection connection = new CommandConnection(in, out);
        String result = connection.readCommand();
        assertEquals("RETR hello.txt", result);
    }

    @Test
    public void writeResponse() {
        in = new ByteArrayInputStream("".getBytes());
        CommandConnection connection = new CommandConnection(in, out);

        connection.writeResponse(150, "Retrieving File");
        assertEquals("150 Retrieving File", out.toString().trim());
    }

    @Test
    public void signalConnected() {
        in = new ByteArrayInputStream("".getBytes());
        CommandConnection connection = new CommandConnection(in, out);

        connection.signalConnected();
        assertEquals("200 Connected to Mercury", out.toString().trim());
    }

    @Test
    public void signalDisconnect() {
        in = new ByteArrayInputStream("".getBytes());
        CommandConnection connection = new CommandConnection(in, out);

        connection.signalDisconnect();
        assertEquals("421 Disconnected from Mercury", out.toString().trim());
    }

    @Test
    public void disconnectCommand() {
        CommandConnection connection = new CommandConnection(new ByteArrayInputStream("".getBytes()), out);
        assertFalse(connection.isDisconnectCommand("RETR hello.txt"));
        assertTrue(connection.isDisconnectCommand("QUIT"));
        assertTrue(connection.isDisconnectCommand(null));
    }

}
