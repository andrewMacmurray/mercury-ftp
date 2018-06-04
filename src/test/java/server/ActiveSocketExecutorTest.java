package server;

import doubles.FakeSocketFactory;
import doubles.StreamHelper;
import org.junit.Before;
import org.junit.Test;
import server.handlers.connection.ActiveSocketExecutor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ActiveSocketExecutorTest {

    private InputStream socketIn;
    private FakeSocketFactory fakeSocketFactory;
    private ActiveSocketExecutor activeSocketExecutor;

    @Before
    public void setup() {
        socketIn = new ByteArrayInputStream("hello".getBytes());
        fakeSocketFactory = new FakeSocketFactory(socketIn);
        activeSocketExecutor = new ActiveSocketExecutor(fakeSocketFactory);
    }

    @Test
    public void testInputStream() throws IOException {
        activeSocketExecutor.inputStream("localhost", 2021, in -> {
            assertEquals(2021, fakeSocketFactory.port);
            assertEquals("localhost", fakeSocketFactory.host);
            assertEquals("hello", StreamHelper.inputStreamToString(socketIn));
        });
    }

    @Test
    public void testOutputStream() throws IOException {
        activeSocketExecutor.outputStream("localhost", 2022, out -> {
            assertEquals(2022, fakeSocketFactory.port);
            assertEquals("localhost", fakeSocketFactory.host);
            assertEquals("", out.toString().trim());
        });
    }

}
