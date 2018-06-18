package mercury.server;

import doubles.stubs.CommandConnectionStub;
import doubles.stubs.FileConnectionStub;
import org.junit.Test;
import mercury.server.connections.CommandResponses;
import mercury.server.ftpcommands.CommandInterpreter;
import mercury.server.ftpcommands.Commands;
import mercury.server.ftpcommands.CommandsFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommandInterpreterTest {

    private CommandConnectionStub commandConnectionStub;

    @Test
    public void processCommands() throws IOException {
        commandConnectionStub = new CommandConnectionStub();
        CommandResponses responses = new CommandResponses(commandConnectionStub);
        FileConnectionStub fileConnectionStub = new FileConnectionStub();
        Commands commands = new CommandsFactory(responses, fileConnectionStub).build();
        CommandInterpreter commandInterpreter = new CommandInterpreter(
                commandConnectionStub,
                responses,
                commands
        );

        commandInterpreter.processCommands();

        assertNthResponse(1, 200, "Connected to Mercury");
        assertNthResponse(2, 421, "Disconnected from Mercury");
    }

    private void assertNthResponse(Integer responseNumber, Integer code, String message) {
        assertEquals(code, commandConnectionStub.codes.get(responseNumber - 1));
        assertEquals(message, commandConnectionStub.messages.get(responseNumber - 1));
    }

}
