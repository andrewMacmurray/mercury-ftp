package server;

import org.junit.Before;
import org.junit.Test;
import server.handlers.DirectoryHandler;

import static org.junit.Assert.assertEquals;

public class DirectoryHandlerTest {

    private DirectoryHandler directoryHandler;

    @Before
    public void setup() {
        directoryHandler = new DirectoryHandler();
    }

    @Test
    public void currentWorkingDirectory() {
        assertEquals("", directoryHandler.getCurrentDirectory());
    }

    @Test
    public void changeDirectory() {
        directoryHandler.changeDirectory("hello");
        assertEquals("hello", directoryHandler.getCurrentDirectory());
    }

    @Test
    public void moveMultipleDirectories() {
        directoryHandler.changeDirectory("hello/");
        directoryHandler.changeDirectory("world");
        assertEquals("hello/world", directoryHandler.getCurrentDirectory());
    }

    @Test
    public void multipleDirectoriesInOnePath() {
        directoryHandler.changeDirectory("/hello/world/");
        assertEquals("hello/world", directoryHandler.getCurrentDirectory());
    }

    @Test
    public void currentWorkingDirectoryWithPath() {
        directoryHandler.changeDirectory("hello");
        assertEquals("hello/world", directoryHandler.getCurrentDirectoryWithPath("world"));
    }

    @Test
    public void currentWorkingDirectoryWithNestedPath() {
        directoryHandler.changeDirectory("hello");
        assertEquals("hello/world/thing.txt", directoryHandler.getCurrentDirectoryWithPath("world/thing.txt"));
    }

    @Test
    public void cdUp() {
        directoryHandler.changeDirectory("hello/world");
        directoryHandler.cdUp();
        assertEquals("hello", directoryHandler.getCurrentDirectory());
    }

    @Test
    public void cdUpTooFar() {
        directoryHandler.changeDirectory("hello/world");
        directoryHandler.cdUp();
        directoryHandler.cdUp();
        directoryHandler.cdUp();
        assertEquals("", directoryHandler.getCurrentDirectory());
    }

}
