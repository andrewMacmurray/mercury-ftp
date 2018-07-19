package mercury.server;

import doubles.fakes.FakeDataSocketExecutor;
import doubles.spies.PassivePortManagerSpy;
import doubles.stubs.SocketFactoryStub;
import doubles.stubs.SocketStub;
import mercury.server.connections.FtpConnection;
import mercury.server.connections.FtpConnectionBuilder;
import mercury.server.connections.PassivePortManager;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FtpConnectionTest {

    @Test
    public void processesMultipleCommands() throws IOException {
        ByteArrayInputStream socketIn = new ByteArrayInputStream("USER andrew".getBytes());
        ByteArrayOutputStream socketOut = new ByteArrayOutputStream();
        SocketStub socketStub = new SocketStub(socketIn, socketOut);
        FakeDataSocketExecutor fakeSocketExecutor = new FakeDataSocketExecutor();
        PassivePortManager passivePortManager = new PassivePortManager("127.0.0.1", 2022, 2026);
        SocketFactoryStub socketFactoryStub = new SocketFactoryStub();

        FtpConnection ftpConnection = new FtpConnectionBuilder(
                socketStub,
                passivePortManager,
                socketFactoryStub,
                null
        ).build();
        ftpConnection.processCommands();

        String output = socketOut.toString();
        assertTrue(output.contains("200 Connected to Mercury"));
        assertTrue(output.contains("331 Hey andrew, Please enter your password"));
        assertTrue(output.contains("421 Disconnected from Mercury"));
    }

    @Test
    public void closesResourcesAppropriately() throws IOException {
        ByteArrayInputStream socketIn = new ByteArrayInputStream("USER andrew".getBytes());
        ByteArrayOutputStream socketOut = new ByteArrayOutputStream();
        SocketStub socketStub = new SocketStub(socketIn, socketOut);
        FakeDataSocketExecutor fakeSocketExecutor = new FakeDataSocketExecutor();
        PassivePortManagerSpy passivePortsSpy = new PassivePortManagerSpy("127.0.0.1", 2022, 2026);
        SocketFactoryStub socketFactoryStub = new SocketFactoryStub();

        FtpConnection ftpConnection = new FtpConnectionBuilder(
                socketStub,
                passivePortsSpy,
                socketFactoryStub,
                null
        ).build();

        ftpConnection.close();

        assertTrue(socketStub.closed);
        assertTrue(socketFactoryStub.createdServerSocketStub.closed);
        assertEquals(new Integer(2022), passivePortsSpy.releasePortCalledWith);
    }

}
