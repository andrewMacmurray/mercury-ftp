package server;

import org.junit.Before;
import org.junit.Test;
import server.commands.Command;
import server.commands.Commands;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandsTest {

    private Commands commands = new Commands(this::unrecognized);
    private boolean commandOneExecuted = false;
    private boolean commandTwoExecuted = false;
    private boolean unrecognizedCalled = false;

    @Before
    public void setup() {
        commands.register(
                new Command("ONE", this::one),
                new Command("TWO", this::two)
        );
    }

    @Test
    public void runRecognized() {
        commands.execute("ONE", "arg");
        commands.execute("TWO", "arg");
        assertTrue(commandOneExecuted);
        assertTrue(commandTwoExecuted);
        assertFalse(unrecognizedCalled);
    }

    @Test
    public void runUnrecognized() {
        commands.execute("BLAH", "wut");
        assertFalse(commandOneExecuted);
        assertFalse(commandTwoExecuted);
        assertTrue(unrecognizedCalled);
    }

    private void two(String arg) {
        commandTwoExecuted = true;
    }

    private void one(String arg) {
        commandOneExecuted = true;
    }

    private void unrecognized() {
        unrecognizedCalled = true;
    }

}
