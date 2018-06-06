package filesystem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

public class FileListingFormatterTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private Path tempFile;
    private Path tempFolder;

    @Before
    public void setup() throws IOException {
        tempFile = temporaryFolder.newFile("hello.txt").toPath();
        tempFolder = temporaryFolder.newFolder("my-folder").toPath();
    }

    @Test
    public void getFolderInfo() throws IOException {
        FileListingFormatter fileListingFormatter = new FileListingFormatter();
        String info = fileListingFormatter.format(tempFolder);

        assertTrue("correct permissions and links", info.contains("drwxr-xr-x 3"));
        assertTrue("correct folder name", info.contains("my-folder"));
        assertTrue("correct file size", info.contains("64"));
    }

    @Test
    public void getFileInfo() throws IOException {
        FileListingFormatter fileListingFormatter = new FileListingFormatter();
        String info = fileListingFormatter.format(tempFile);

        assertTrue("correct permissions and links", info.contains("-rw-r--r-- 1"));
        assertTrue("correct file name", info.contains("hello.txt"));
        assertTrue("correct file size", info.contains("0"));
    }

}
