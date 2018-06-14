package server;

import doubles.stubs.SocketFactoryStub;
import doubles.StreamHelper;
import org.junit.Before;
import org.junit.Test;
import server.connections.socket.SocketExecutor;

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
        socketExecutor = new SocketExecutor(socketFactoryStub);

        socketExecutor.setActiveMode("localhost", 2021);
        socketExecutor.setPassivePort(2022);
    }

    @Test
    public void inputStreamActive() throws IOException {
        socketExecutor.inputStream(in -> {
            assertEquals(2021, socketFactoryStub.port);
            assertEquals("localhost", socketFactoryStub.host);
            assertEquals("hello", StreamHelper.inputStreamToString(in));
        });
    }

    @Test
    public void outputStreamActive() throws IOException {
        socketExecutor.outputStream(out -> {
            assertEquals(2021, socketFactoryStub.port);
            assertEquals("localhost", socketFactoryStub.host);
            assertEquals("", out.toString().trim());
        });
    }

    @Test
    public void inputStreamPassive() throws IOException {
        socketExecutor.setPassiveMode();
        socketExecutor.inputStream(in -> {
            assertEquals(2022, socketFactoryStub.port);
            assertEquals("hello", StreamHelper.inputStreamToString(in));
        });
    }

    @Test
    public void resetToActiveMode() throws IOException {
        socketExecutor.setPassiveMode();
        socketExecutor.setActiveMode("localhost", 2021);

        socketExecutor.inputStream(in -> {
            assertEquals(2021, socketFactoryStub.port);
        });
    }

}