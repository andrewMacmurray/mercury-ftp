package mercury.server;

import doubles.fakes.FakeDataSocketExecutor;
import doubles.spies.FileSystemSpy;
import doubles.stubs.FileListingStub;
import mercury.filesystem.FtpFileSystem;
import mercury.filesystem.WorkingDirectory;
import mercury.server.connections.FileConnection;
import mercury.server.connections.socket.DataSocketExecutor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileConnectionTest {

    private FileSystemSpy fileSystemSpy;
    private FileConnection fileConnection;

    @Before
    public void setup() {
        fileSystemSpy = new FileSystemSpy();
        DataSocketExecutor dataSocketExecutor = new FakeDataSocketExecutor();
        FtpFileSystem ftpFileSystem = new FtpFileSystem(
                fileSystemSpy,
                new FileListingStub(),
                new WorkingDirectory()
        );
        fileConnection = new FileConnection(ftpFileSystem, dataSocketExecutor);
    }

    @Test
    public void retrieve() throws IOException {
        fileConnection.retrieve("hello.txt");
        assertEquals("hello.txt", fileSystemSpy.retrievedFile);
    }

    @Test
    public void store() throws IOException {
        fileConnection.store("socket.txt");
        assertEquals("socket.txt", fileSystemSpy.storedFile);
    }

    @Test
    public void sendFileList() throws IOException {
        fileConnection.sendFileList("dir");
        assertEquals("dir", fileSystemSpy.listedDirectory);
    }

    @Test
    public void sendNameList() throws IOException {
        fileConnection.sendNameList("dir");
        assertEquals("dir", fileSystemSpy.listedDirectory);
    }

    @Test
    public void isUniqueFileName() {
        fileConnection.fileExists("hello.txt");
        assertEquals("hello.txt", fileSystemSpy.checkedFile);
    }
}
