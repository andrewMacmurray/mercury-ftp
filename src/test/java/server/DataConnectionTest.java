package server;

import doubles.FileSystemSpy;
import filesystem.FileSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class DataConnectionTest {

    private ByteArrayInputStream socketIn;
    private ByteArrayOutputStream socketOut;
    private FileSystemSpy fsSpy;
    private DataConnection connection;

    @Before
    public void setup() {
       socketIn = new ByteArrayInputStream("".getBytes());
       socketOut = new ByteArrayOutputStream();
       fsSpy = new FileSystemSpy();
       connection = new DataConnection(socketIn, socketOut, fsSpy);
    }

    @Test
    public void retrieve() throws IOException {
        connection.retrieve("hello.txt");
        assertTrue(fsSpy.copyFromLocalCalled);
    }

    @Test
    public void store() throws IOException {
        connection.store("hello.txt");
        assertTrue(fsSpy.writeFileCalled);
    }

}
