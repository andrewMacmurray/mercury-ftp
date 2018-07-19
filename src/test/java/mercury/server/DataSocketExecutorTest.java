package mercury.server;

import doubles.StreamHelper;
import doubles.stubs.ServerSocketStub;
import doubles.stubs.SocketFactoryStub;
import mercury.server.connections.socket.DataSocketExecutor;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class DataSocketExecutorTest {

    private SocketFactoryStub socketFactoryStub;
    private DataSocketExecutor dataSocketExecutor;

    @Before
    public void setup() throws IOException {
        InputStream socketIn = new ByteArrayInputStream("hello".getBytes());
        socketFactoryStub = new SocketFactoryStub(socketIn);
        dataSocketExecutor = new DataSocketExecutor(
                socketFactoryStub,
                new ServerSocketStub(socketIn),
                2022,
                "127.0.0.1"
        );
    }

    @Test
    public void getsCorrectPassiveHostAndPort() {
        assertEquals(2022, dataSocketExecutor.getPassivePort());
        assertEquals("127.0.0.1", dataSocketExecutor.getPassiveHost());
    }

    @Test
    public void inputStreamActive() throws IOException {
        dataSocketExecutor.setActiveMode("localhost", 2021);
        dataSocketExecutor.runInputStream(in -> {
            assertEquals(2021, socketFactoryStub.port);
            assertEquals("localhost", socketFactoryStub.host);
            assertEquals("hello", StreamHelper.inputStreamToString(in));
        });
    }

    @Test
    public void outputStreamActive() throws IOException {
        dataSocketExecutor.setActiveMode("127.0.0.1", 8080);
        dataSocketExecutor.runOutputStream(out -> {
            assertEquals(8080, socketFactoryStub.port);
            assertEquals("127.0.0.1", socketFactoryStub.host);
            assertEquals("", out.toString().trim());
        });
    }

    @Test
    public void inputStreamPassive() throws IOException {
        dataSocketExecutor.setPassiveMode();
        dataSocketExecutor.runInputStream(in -> {
            assertEquals("hello", StreamHelper.inputStreamToString(in));
        });
    }

    @Test
    public void resetToActiveMode() throws IOException {
        dataSocketExecutor.setPassiveMode();
        dataSocketExecutor.setActiveMode("localhost", 2021);

        dataSocketExecutor.runInputStream(in -> {
            assertEquals("localhost", socketFactoryStub.host);
            assertEquals(2021, socketFactoryStub.port);
        });
    }

}
