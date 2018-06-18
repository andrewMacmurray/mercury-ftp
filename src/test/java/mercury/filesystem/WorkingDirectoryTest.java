package mercury.filesystem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorkingDirectoryTest {

    private WorkingDirectory workingDirectory;

    @Before
    public void setup() {
        workingDirectory = new WorkingDirectory();
    }

    @Test
    public void currentWorkingDirectory() {
        assertEquals("", workingDirectory.getCurrentDirectory().toString());
    }

    @Test
    public void changeDirectory() {
        workingDirectory.changeDirectory("hello");
        assertEquals("hello", workingDirectory.getCurrentDirectory().toString());
    }

    @Test
    public void moveMultipleDirectories() {
        workingDirectory.changeDirectory("hello/");
        workingDirectory.changeDirectory("world");
        assertEquals("hello/world", workingDirectory.getCurrentDirectory().toString());
    }

    @Test
    public void multipleDirectoriesInOnePath() {
        workingDirectory.changeDirectory("hello/world/");
        assertEquals("hello/world", workingDirectory.getCurrentDirectory().toString());
    }

    @Test
    public void cdUp() {
        workingDirectory.changeDirectory("hello/world");
        workingDirectory.cdUp();
        assertEquals("hello", workingDirectory.getCurrentDirectory().toString());
    }

    @Test
    public void cdUpTooFar() {
        workingDirectory.changeDirectory("hello/world");
        workingDirectory.cdUp();
        workingDirectory.cdUp();
        workingDirectory.cdUp();
        assertEquals("", workingDirectory.getCurrentDirectory().toString());
    }

}
