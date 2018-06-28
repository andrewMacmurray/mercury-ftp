package mercury.server;

import doubles.StreamHelper;
import doubles.stubs.ServerSocketStub;
import doubles.stubs.SocketFactoryStub;
import mercury.server.connections.socket.SocketExecutor;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class SocketExecutorTest {

    private SocketFactoryStub socketFactoryStub;
    private SocketExecutor socketExecutor;

    @Before
    public void setup() throws IOException {
        InputStream socketIn = new ByteArrayInputStream("hello".getBytes());
        socketFactoryStub = new SocketFactoryStub(socketIn);
        socketExecutor = new SocketExecutor(
                socketFactoryStub,
                new ServerSocketStub(socketIn),
                2022,
                "127.0.0.1"
        );
    }

    @Test
    public void getsCorrectPassiveHostAndPort() {
        assertEquals(2022, socketExecutor.getPassivePort());
        assertEquals("127.0.0.1", socketExecutor.getPassiveHost());
    }

    @Test
    public void inputStreamActive() throws IOException {
        socketExecutor.setActiveMode("localhost", 2021);
        socketExecutor.runInputStream(in -> {
            assertEquals(2021, socketFactoryStub.port);
            assertEquals("localhost", socketFactoryStub.host);
            assertEquals("hello", StreamHelper.inputStreamToString(in));
        });
    }

    @Test
    public void outputStreamActive() throws IOException {
        socketExecutor.setActiveMode("127.0.0.1", 8080);
        socketExecutor.runOutputStream(out -> {
            assertEquals(8080, socketFactoryStub.port);
            assertEquals("127.0.0.1", socketFactoryStub.host);
            assertEquals("", out.toString().trim());
        });
    }

    @Test
    public void inputStreamPassive() throws IOException {
        socketExecutor.setPassiveMode();
        socketExecutor.runInputStream(in -> {
            assertEquals("hello", StreamHelper.inputStreamToString(in));
        });
    }

    @Test
    public void resetToActiveMode() throws IOException {
        socketExecutor.setPassiveMode();
        socketExecutor.setActiveMode("localhost", 2021);

        socketExecutor.runInputStream(in -> {
            assertEquals("localhost", socketFactoryStub.host);
            assertEquals(2021, socketFactoryStub.port);
        });
    }

}
