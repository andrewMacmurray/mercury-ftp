package server;

import doubles.fakes.FakeSocketExecutor;
import doubles.spies.FileSystemSpy;
import doubles.stubs.SocketStub;
import org.junit.Test;
import server.connections.CommandResponses;
import server.ftpcommands.CommandInterpreter;
import server.ftpcommands.CommandInterpreterBuilder;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CommandInterpreterBuilderTest {

    @Test
    public void buildsInterpreterCorrectly() throws IOException {
        InputStream socketIn = new ByteArrayInputStream("RETR hello.txt".getBytes());
        OutputStream socketOut = new ByteArrayOutputStream();
        SocketStub socketStub = new SocketStub(socketIn, socketOut);
        FakeSocketExecutor fakeSocketExecutor = new FakeSocketExecutor();
        FileSystemSpy fileSystemSpy = new FileSystemSpy();

        CommandInterpreter commandInterpreter = CommandInterpreterBuilder.build(socketStub, fakeSocketExecutor, fileSystemSpy);

        commandInterpreter.processCommands();

        String result = Stream.of(
                "200 Connected to Mercury",
                "150 OK getting File",
                "250 OK hello.txt sent",
                "421 Disconnected from Mercury"
        ).collect(Collectors.joining("\n"));

        assertEquals(result, socketOut.toString().trim());
    }


}
