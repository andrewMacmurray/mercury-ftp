package mercury.server;

import doubles.fakes.FakeSocketExecutor;
import doubles.stubs.SocketStub;
import mercury.server.connections.FtpConnectionThread;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class FtpConnectionThreadTest {

    @Test
    public void startThread() throws IOException {
        ByteArrayInputStream socketIn = new ByteArrayInputStream("USER andrew".getBytes());
        ByteArrayOutputStream socketOut = new ByteArrayOutputStream();
        SocketStub socketStub = new SocketStub(socketIn, socketOut);
        FakeSocketExecutor fakeSocketExecutor = new FakeSocketExecutor();

        FtpConnectionThread ftpConnectionThread = new FtpConnectionThread(socketStub, fakeSocketExecutor, null);
        ftpConnectionThread.run();

        String output = socketOut.toString();
        assertTrue(output.contains("200 Connected to Mercury"));
        assertTrue(output.contains("331 Hey andrew, Please enter your password"));
        assertTrue(output.contains("421 Disconnected from Mercury"));
    }

}
