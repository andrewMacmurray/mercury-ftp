package filesystem;

import doubles.FileListingStub;
import doubles.FileSystemSpy;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FtpFileSystemTest {

    private FileSystemSpy fileSystemSpy;
    private WorkingDirectory workingDirectory;
    private FtpFileSystem ftpFileSystem;

    @Before
    public void setup() {
        Stream<Path> subPaths = Stream.of("sub-1/file.txt", "sub-2/file.txt").map(Paths::get);
        fileSystemSpy = new FileSystemSpy(subPaths);
        workingDirectory = new WorkingDirectory();
        ftpFileSystem = new FtpFileSystem(fileSystemSpy, new FileListingStub(), workingDirectory);
    }

    @Test
    public void getCurrentWorkingDirectory() {
        assertEquals(ftpFileSystem.getCurrentWorkingDirectory(), "/");
    }

    @Test
    public void changeWorkingDirectory() {
        ftpFileSystem.changeWorkingDirectory("/hello");
        assertEquals("/hello", ftpFileSystem.getCurrentWorkingDirectory());
        assertEquals("hello", fileSystemSpy.checkedDirectory);
    }

    @Test
    public void changeMultipleDirectories() {
        ftpFileSystem.changeWorkingDirectory("/hello/world");
        assertEquals("/hello/world", ftpFileSystem.getCurrentWorkingDirectory());
        assertEquals("hello/world", fileSystemSpy.checkedDirectory);
    }

    @Test
    public void store() throws IOException {
        ftpFileSystem.store("hello.txt").runWithStream(new ByteArrayInputStream("".getBytes()));
        assertEquals("hello.txt", fileSystemSpy.storedFile);
    }

    @Test
    public void retrieve() throws IOException {
        ftpFileSystem.retrieve("hello.txt").runWithStream(new ByteArrayOutputStream());
        assertEquals("hello.txt", fileSystemSpy.retrievedFile);
    }

    @Test
    public void listWithNoArg() throws IOException {
        workingDirectory.changeDirectory("hello");
        ftpFileSystem.list("").runWithStream(new ByteArrayOutputStream());
        assertEquals("if empty arg checks the current working directory", "hello", fileSystemSpy.listedDirectory);
    }

    @Test
    public void listWithArg() throws IOException {
        workingDirectory.changeDirectory("hello");
        ftpFileSystem.list("world").runWithStream(new ByteArrayOutputStream());
        assertEquals("resolves asked for dir against current working dir", "hello/world", fileSystemSpy.listedDirectory);
    }

    @Test
    public void returnList() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ftpFileSystem.list("").runWithStream(out);

        assertTrue(out.toString().contains("sub-1"));
        assertTrue(out.toString().contains("sub-2"));
    }

    @Test
    public void nameList() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ftpFileSystem.nameList("").runWithStream(out);

        assertTrue(out.toString().contains("file.txt"));
    }

}
