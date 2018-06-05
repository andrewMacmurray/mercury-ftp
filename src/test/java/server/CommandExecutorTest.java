package server;

import org.junit.Test;
import server.commands.CommandExecutor;

import static org.junit.Assert.assertEquals;

public class CommandExecutorTest {

    private String command;
    private String argument;

    @Test
    public void executorOneArg() {
        String rawCommand = "RETR hello.txt";
        CommandExecutor.run(rawCommand, this::dummyParsedCommandRunner);

        assertEquals("RETR", command);
        assertEquals("hello.txt", argument);
    }

    @Test
    public void executorNoArgs() {
        String rawCommand = "QUIT";
        CommandExecutor.run(rawCommand, this::dummyParsedCommandRunner);

        assertEquals("QUIT", command);
        assertEquals("", argument);
    }

    private void dummyParsedCommandRunner(String command, String argument) {
        this.command = command;
        this.argument = argument;
    }

}
