package server;

import doubles.mocks.MockSocketFactory;
import doubles.StreamHelper;
import org.junit.Before;
import org.junit.Test;
import server.connections.socket.SocketExecutor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class DataSocketExecutorTest {

    private InputStream socketIn;
    private MockSocketFactory mockSocketFactory;
    private SocketExecutor socketExecutor;

    @Before
    public void setup() {
        socketIn = new ByteArrayInputStream("hello".getBytes());
        mockSocketFactory = new MockSocketFactory(socketIn);
        socketExecutor = new SocketExecutor(mockSocketFactory);
    }

    @Test
    public void testInputStream() throws IOException {
        socketExecutor.inputStream("localhost", 2021, in -> {
            assertEquals(2021, mockSocketFactory.port);
            assertEquals("localhost", mockSocketFactory.host);
            assertEquals("hello", StreamHelper.inputStreamToString(socketIn));
        });
    }

    @Test
    public void testOutputStream() throws IOException {
        socketExecutor.outputStream("localhost", 2022, out -> {
            assertEquals(2022, mockSocketFactory.port);
            assertEquals("localhost", mockSocketFactory.host);
            assertEquals("", out.toString().trim());
        });
    }

}
