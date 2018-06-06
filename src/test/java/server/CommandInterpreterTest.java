package server;

import doubles.*;
import filesystem.FtpFileSystem;
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

        FileConnectionSpy fileHandlerSpy = new FileConnectionSpy(new DummyFtpFileSystem(), new DummySocketExecutor());
        CommandConnectionSpy commandHandlerSpy = new CommandConnectionSpy(socketIn, socketOut);

        CommandInterpreter commandInterpreter = new CommandInterpreter(commandHandlerSpy, fileHandlerSpy);
        commandInterpreter.processCommands();

        assertEquals(421, commandHandlerSpy.code);
        assertEquals("Disconnected from Mercury", commandHandlerSpy.message);
        assertEquals("hello.txt", fileHandlerSpy.requestedFile);
    }

}
