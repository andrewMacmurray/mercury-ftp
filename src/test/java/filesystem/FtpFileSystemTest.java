package filesystem;

import doubles.FileSystemSpy;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FtpFileSystemTest {

    private FileSystemSpy fileSystemSpy;
    private FtpFileSystem ftpFileSystem;

    @Before
    public void setup() {
        fileSystemSpy = new FileSystemSpy();
        ftpFileSystem = new FtpFileSystem(fileSystemSpy, new WorkingDirectory());
    }

    @Test
    public void getCurrentWorkingDirectory() {
        assertEquals(ftpFileSystem.getCurrentWorkingDirectory(), "/");
    }

    @Test
    public void changeWorkingDirectory() {
        ftpFileSystem.changeWorkingDirectory("/hello");
        assertEquals("/hello", ftpFileSystem.getCurrentWorkingDirectory());
        assertEquals("/hello", fileSystemSpy.checkedDirectory);
    }

    @Test
    public void changeMultipleDirectories() {
        ftpFileSystem.changeWorkingDirectory("/hello/world");
        assertEquals("/hello/world", ftpFileSystem.getCurrentWorkingDirectory());
        assertEquals("/hello/world", fileSystemSpy.checkedDirectory);
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

}
