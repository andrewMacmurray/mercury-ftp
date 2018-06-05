package server;

import doubles.CommandHandlerSpy;
import doubles.DummyFileSystem;
import doubles.DummySocketExecutor;
import doubles.FileHandlerSpy;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CommandInterpreterTest {

    @Test
    public void processCommands() throws IOException {
        ByteArrayInputStream socketIn = new ByteArrayInputStream("RETR hello.txt".getBytes());
        ByteArrayOutputStream socketOut = new ByteArrayOutputStream();

        FileHandlerSpy fileHandlerSpy = new FileHandlerSpy(new DummyFileSystem(), new DummySocketExecutor());
        CommandHandlerSpy commandHandlerSpy = new CommandHandlerSpy(socketIn, socketOut);

        CommandInterpreter commandInterpreter = new CommandInterpreter(commandHandlerSpy, fileHandlerSpy);
        commandInterpreter.processCommands();

        assertEquals(421, commandHandlerSpy.code);
        assertEquals("Disconnected from Mercury", commandHandlerSpy.message);
        assertEquals("hello.txt", fileHandlerSpy.requestedFile);
    }

}
