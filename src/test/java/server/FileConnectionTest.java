package server;

import doubles.FakeFileSystem;
import doubles.FakeSocket;
import doubles.FileHandlerSpy;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class FileConnectionTest {

    private boolean interpreterCalled = false;

    @Test
    public void initConnection() throws IOException  {
        FileHandlerSpy fileHandlerSpy = new FileHandlerSpy(new FakeFileSystem());
        FileConnection connection = new FileConnection(fileHandlerSpy, new CommandInterpreter(this::interpreterCallback));

        FakeSocket fakeSocket = new FakeSocket();
        connection.processCommand("RETR hello.txt", fakeSocket);

        assertTrue(interpreterCalled);
        assertTrue(fileHandlerSpy.streamsConnected);
        assertTrue(fakeSocket.closed);
    }

    private void interpreterCallback(String arg1, String arg2) {
        interpreterCalled = true;
    }

}
