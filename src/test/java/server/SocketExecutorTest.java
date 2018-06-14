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

public class SocketExecutorTest {

    private MockSocketFactory mockSocketFactory;
    private SocketExecutor socketExecutor;

    @Before
    public void setup() throws IOException {
        InputStream socketIn = new ByteArrayInputStream("hello".getBytes());
        mockSocketFactory = new MockSocketFactory(socketIn);
        socketExecutor = new SocketExecutor(mockSocketFactory);

        socketExecutor.setActiveMode("localhost", 2021);
        socketExecutor.setPassivePort(2022);
    }

    @Test
    public void inputStreamActive() throws IOException {
        socketExecutor.inputStream(in -> {
            assertEquals(2021, mockSocketFactory.port);
            assertEquals("localhost", mockSocketFactory.host);
            assertEquals("hello", StreamHelper.inputStreamToString(in));
        });
    }

    @Test
    public void outputStreamActive() throws IOException {
        socketExecutor.outputStream(out -> {
            assertEquals(2021, mockSocketFactory.port);
            assertEquals("localhost", mockSocketFactory.host);
            assertEquals("", out.toString().trim());
        });
    }

    @Test
    public void inputStreamPassive() throws IOException {
        socketExecutor.setPassiveMode();
        socketExecutor.inputStream(in -> {
            assertEquals(2022, mockSocketFactory.port);
            assertEquals("hello", StreamHelper.inputStreamToString(in));
        });
    }

    @Test
    public void resetToActiveMode() throws IOException {
        socketExecutor.setPassiveMode();
        socketExecutor.setActiveMode("localhost", 2021);

        socketExecutor.inputStream(in -> {
            assertEquals(2021, mockSocketFactory.port);
        });
    }

}
