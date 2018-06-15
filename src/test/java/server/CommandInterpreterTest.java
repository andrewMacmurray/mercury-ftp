package server;

import doubles.stubs.FileConnectionStub;
import org.junit.Test;
import server.connections.CommandResponses;
import server.ftpcommands.CommandInterpreter;
import server.ftpcommands.Commands;
import server.ftpcommands.CommandsFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommandInterpreterTest {

    private List<Integer> codes = new ArrayList<>();
    private List<String> messages = new ArrayList<>();
    private List<String> commandsToSend = new ArrayList<>(
            Arrays.asList(
                    "RETR hello.txt"
            )
    );

    @Test
    public void processCommands() throws IOException {
        CommandResponses responses = new CommandResponses(this::dummyResponder);
        FileConnectionStub fileConnectionStub = new FileConnectionStub();
        Commands commands = new CommandsFactory(responses, fileConnectionStub).build();
        CommandInterpreter commandInterpreter = new CommandInterpreter(
                this::dummyCommandReader,
                responses,
                commands
        );

        commandInterpreter.processCommands();

        assertNthResponse(1, 200, "Connected to Mercury");
        assertNthResponse(2, 150, "OK getting File");
        assertNthResponse(3, 250, "OK hello.txt sent");
        assertNthResponse(4, 421, "Disconnected from Mercury");
    }

    private String dummyCommandReader() {
        try {
            return commandsToSend.remove(0);
        } catch (Exception e) {
            return null;
        }
    }

    private void assertNthResponse(Integer responseNumber, Integer code, String message) {
        assertEquals(code, codes.get(responseNumber - 1));
        assertEquals(message, messages.get(responseNumber - 1));
    }

    private void dummyResponder(int code, String message) {
        codes.add(code);
        messages.add(message);
    }

}
