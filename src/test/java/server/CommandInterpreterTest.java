package server;

import doubles.spies.CommandConnectionSpy;
import doubles.stubs.FileConnectionStub;
import org.junit.Test;
import server.ftpcommands.CommandInterpreter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CommandInterpreterTest {

    @Test
    public void processCommands() throws IOException {
        ByteArrayInputStream socketIn = new ByteArrayInputStream("RETR hello.txt".getBytes());
        ByteArrayOutputStream socketOut = new ByteArrayOutputStream();

        FileConnectionStub fileConnectionStub = new FileConnectionStub();
        CommandConnectionSpy commandHandlerSpy = new CommandConnectionSpy(socketIn, socketOut);

        CommandInterpreter commandInterpreter = new CommandInterpreter(commandHandlerSpy, fileConnectionStub);
        commandInterpreter.processCommands();

        assertEquals(421, commandHandlerSpy.code);
        assertEquals("Disconnected from Mercury", commandHandlerSpy.message);
        assertEquals("hello.txt", fileConnectionStub.retrieveCalledWith);
    }

}
