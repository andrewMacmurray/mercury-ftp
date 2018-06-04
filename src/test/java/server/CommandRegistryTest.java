package server;

import doubles.FakeFileSystem;
import doubles.FakeSocketExecutor;
import doubles.FakeSocketFactory;
import doubles.FileHandlerSpy;
import org.junit.Before;
import org.junit.Test;
import server.handlers.connection.ActiveSocketExecutor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CommandRegistryTest {

    private CommandRegistry commandRegistry;
    private FileHandlerSpy fileHandlerSpy;
    private int responseCode;
    private String responseMessage;

    @Before
    public void setup() {
        fileHandlerSpy = new FileHandlerSpy(new FakeFileSystem(), new FakeSocketExecutor());
        commandRegistry = new CommandRegistry(fileHandlerSpy, this::dummyResponseHandler);
    }

    @Test
    public void retrieveFile() throws IOException {
        commandRegistry.executeCommand("RETR", "hello.txt");

        assertEquals("hello.txt", fileHandlerSpy.requestedFile);
        assertEquals(250, responseCode);
        assertEquals("OK File sent", responseMessage);
    }

    @Test
    public void storeFile() throws IOException {
        commandRegistry.executeCommand("STOR", "my-file.txt");

        assertEquals("my-file.txt", fileHandlerSpy.storedFile);
        assertEquals(250, responseCode);
        assertEquals("OK File stored", responseMessage);
    }

    @Test
    public void unrecognised() throws IOException {
        commandRegistry.executeCommand("LOL", "wut?");

        assertEquals(500, responseCode);
        assertEquals("Unrecognized", responseMessage);
    }
    
    private void dummyResponseHandler(Integer code, String message) {
        this.responseCode = code;
        this.responseMessage = message;
    }

}
