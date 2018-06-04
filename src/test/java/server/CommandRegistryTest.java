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
    public void retrieveFile() {
        commandRegistry.executeCommand("RETR", "hello.txt");
        assertEquals("hello.txt", fileHandlerSpy.requestedFile);
        assertResponse(250, "OK File sent");
    }

    @Test
    public void storeFile() {
        commandRegistry.executeCommand("STOR", "my-file.txt");
        assertEquals("my-file.txt", fileHandlerSpy.storedFile);
        assertResponse(250, "OK File stored");
    }

    @Test
    public void userName() {
       commandRegistry.executeCommand("USER", "andrew");
       assertResponse(331, "Hey andrew, Please enter your password");
    }

    @Test
    public void unrecognised() {
        commandRegistry.executeCommand("LOL", "wut?");
        assertResponse(500, "Unrecognized");
    }

    private void assertResponse(int code, String message) {
        assertEquals(code, responseCode);
        assertEquals(message, responseMessage);
    }
    
    private void dummyResponseHandler(Integer code, String message) {
        this.responseCode = code;
        this.responseMessage = message;
    }

}
