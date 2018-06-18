package mercury.server;

import org.junit.Before;
import org.junit.Test;
import mercury.server.connections.CommandConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

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

}
