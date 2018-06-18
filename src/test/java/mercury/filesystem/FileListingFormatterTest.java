package mercury.filesystem;

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

        assertTrue("correct permissions and links", info.contains("drwxr"));
        assertTrue("correct number of links", info.contains(" 3"));
        assertTrue("correct folder name", info.contains("my-folder"));
    }

    @Test
    public void getFileInfo() throws IOException {
        FileListingFormatter fileListingFormatter = new FileListingFormatter();
        String info = fileListingFormatter.format(tempFile);

        assertTrue("correct permissions and links", info.contains("-rw-r"));
        assertTrue("correct file name", info.contains("hello.txt"));
        assertTrue("correct number of links", info.contains(" 1"));
        assertTrue("correct file size", info.contains("0"));
    }

}
