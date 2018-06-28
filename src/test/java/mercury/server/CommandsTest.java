package mercury.server;

import mercury.server.ftpcommands.Command;
import mercury.server.ftpcommands.Commands;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandsTest {

    private boolean commandOneExecuted = false;
    private boolean commandTwoExecuted = false;
    private boolean unrecognizedCalled = false;
    private Commands commands = new Commands(this::unrecognized);

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
