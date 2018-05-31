package filesystem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.file.Path;
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
        assertFalse(nativeFileSystem.fileExists("hello.txt"));

        Path path = tempFolder.newFile("hello.txt").toPath();
        assertTrue(nativeFileSystem.fileExists("hello.txt"));
    }

    @Test
    public void writeFile() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("hello".getBytes());

        nativeFileSystem.writeFile("hello.txt", in);
        assertTrue(nativeFileSystem.fileExists("hello.txt"));
    }

    @Test
    public void readFile() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("hello".getBytes());
        nativeFileSystem.writeFile("hello.txt", in);

        InputStream retrievedFile = nativeFileSystem.readFile("hello.txt");
        assertEquals(inputStreamToString(retrievedFile), "hello");
    }

    @Test
    public void copyToOutput() throws IOException {
        ByteArrayInputStream source = new ByteArrayInputStream("hello".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        nativeFileSystem.writeFile("hello.txt", source);
        nativeFileSystem.copyFromLocal("hello.txt", out);

        assertEquals("hello", out.toString());
    }

    private String inputStreamToString(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        return br.lines().collect(Collectors.joining("\n"));
    }

}
