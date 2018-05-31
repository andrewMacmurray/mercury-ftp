package server;

import doubles.CommandHandlerSpy;
import doubles.FakeFileSystem;
import doubles.FileHandlerSpy;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CommandRegistryTest {

    private CommandRegistry commandRegistry;
    private FileHandlerSpy fileHandlerSpy;
    private CommandHandlerSpy commandHandlerSpy;

    @Before
    public void setup() {
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        ByteArrayOutputStream commandOut = new ByteArrayOutputStream();
        ByteArrayInputStream fileIn = new ByteArrayInputStream("".getBytes());
        ByteArrayInputStream commandIn = new ByteArrayInputStream("".getBytes());
        FakeFileSystem fakeFileSystem = new FakeFileSystem();

        commandHandlerSpy = new CommandHandlerSpy(commandIn, commandOut);
        fileHandlerSpy = new FileHandlerSpy(fileIn, fileOut, fakeFileSystem);
        commandRegistry = new CommandRegistry(fileHandlerSpy, commandHandlerSpy);
    }

    @Test
    public void retrieveFile() throws IOException {
        commandRegistry.executeCommand("RETR", "hello.txt");

        assertEquals("hello.txt", fileHandlerSpy.requestedFile);
        assertEquals(250, commandHandlerSpy.code);
        assertEquals("OK File sent", commandHandlerSpy.message);
    }

    @Test
    public void storeFile() throws IOException {
        commandRegistry.executeCommand("STOR", "my-file.txt");

        assertEquals("my-file.txt", fileHandlerSpy.storedFile);
        assertEquals(250, commandHandlerSpy.code);
        assertEquals("OK File stored", commandHandlerSpy.message);
    }

    @Test
    public void unrecognised() throws IOException {
        commandRegistry.executeCommand("LOL", "wut?");

        assertEquals(500, commandHandlerSpy.code);
        assertEquals("Unrecognized", commandHandlerSpy.message);
    }

}
