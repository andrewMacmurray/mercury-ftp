package mercury.filesystem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class NativeFileSystemTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private NativeFileSystem nativeFileSystem;

    @Before
    public void setup() {
        nativeFileSystem = new NativeFileSystem(tempFolder.getRoot().toString());
    }

    @Test
    public void exists() throws IOException {
        assertFalse(nativeFileSystem.fileExists(Paths.get("hello.txt")));

        Path path = tempFolder.newFile("hello.txt").toPath();
        assertTrue(nativeFileSystem.fileExists(Paths.get("hello.txt")));
    }

    @Test
    public void isValidDirectory() throws IOException {
        assertFalse(nativeFileSystem.isValidDirectory(Paths.get("hello.txt")));

        tempFolder.newFolder("hello");

        assertFalse(nativeFileSystem.isValidDirectory(Paths.get("hello/hello.txt")));
        assertTrue(nativeFileSystem.isValidDirectory(Paths.get("hello")));
    }

    @Test
    public void rejectDirectoryDots() throws IOException {
        assertFalse(nativeFileSystem.isValidDirectory(Paths.get("..")));
        assertFalse(nativeFileSystem.isValidDirectory(Paths.get(".")));

        tempFolder.newFolder("hello");

        assertFalse(nativeFileSystem.isValidDirectory(Paths.get("hello/..")));
        assertFalse(nativeFileSystem.isValidDirectory(Paths.get("hello/.")));
    }

    @Test
    public void writeFile() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("hello".getBytes());

        nativeFileSystem.writeFile(Paths.get("hello.txt"), in);
        assertTrue(nativeFileSystem.fileExists(Paths.get("hello.txt")));
    }

    @Test
    public void copyToOutput() throws IOException {
        ByteArrayInputStream source = new ByteArrayInputStream("hello".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        nativeFileSystem.writeFile(Paths.get("hello.txt"), source);
        nativeFileSystem.copyFromLocal(Paths.get("hello.txt"), out);

        assertEquals("hello", out.toString());
    }

    @Test
    public void listFiles() throws IOException {
        tempFolder.newFile("foo.txt");
        tempFolder.newFile("bar.txt");
        tempFolder.newFile("baz.txt");

        Path path = tempFolder.getRoot().toPath();

        String result = nativeFileSystem.list(path)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.joining(" "));

        assertTrue(result.contains("foo.txt"));
        assertTrue(result.contains("baz.txt"));
        assertTrue(result.contains("bar.txt"));
    }

    @Test
    public void fileExists() throws IOException {
        assertFalse(nativeFileSystem.fileExists(Paths.get("hello.txt")));
        Path file = tempFolder.newFile("hello.txt").toPath();
        assertTrue(nativeFileSystem.fileExists(file));
    }

    @Test
    public void appendToFile() throws IOException {
        Path file = tempFolder.newFile("hello.txt").toPath();
        InputStream in = new ByteArrayInputStream("hello".getBytes());

        nativeFileSystem.append(file, in);

        String contents = Files.lines(file).collect(Collectors.joining(""));
        assertTrue(contents.contains("hello"));
    }

}
