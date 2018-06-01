package server;

import org.junit.Before;
import org.junit.Test;
import server.commands.Command;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CommandInterpreterTest {

    private String executedCommand;
    private String executedCommandArg;
    private CommandInterpreter commandInterpreter;

    @Before
    public void setup() {
       commandInterpreter = new CommandInterpreter(this::dummyCommandExecutor);
    }

    @Test
    public void parseCommand() throws IOException {
       commandInterpreter.execute("RETR hello.txt");

       assertEquals("RETR", executedCommand);
       assertEquals("hello.txt", executedCommandArg);
    }

    @Test
    public void parseSingleArg() throws IOException {
        commandInterpreter.execute("QUIT");

        assertEquals("QUIT", executedCommand);
        assertEquals("", executedCommandArg);
    }

    private void dummyCommandExecutor(String commandName, String arg) {
        this.executedCommand = commandName;
        this.executedCommandArg = arg;
    }

}
