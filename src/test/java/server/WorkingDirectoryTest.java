package server;

import org.junit.Before;
import org.junit.Test;
import server.handlers.WorkingDirectory;

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
        workingDirectory.changeDirectory("/hello/world/");
        assertEquals("hello/world", workingDirectory.getCurrentDirectory().toString());
    }

    @Test
    public void currentWorkingDirectoryWithPath() {
        workingDirectory.changeDirectory("hello");
        assertEquals("hello/world", workingDirectory.getCurrentDirectoryWithPath("world").toString());
    }

    @Test
    public void currentWorkingDirectoryWithNestedPath() {
        workingDirectory.changeDirectory("hello");
        assertEquals("hello/world/thing.txt", workingDirectory.getCurrentDirectoryWithPath("world/thing.txt").toString());
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
