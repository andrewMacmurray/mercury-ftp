package filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class NativeFileSystem implements FileSystem {

    private Path rootDir;

    public NativeFileSystem(Path rootDir) {
        this.rootDir = rootDir;
    }

    public boolean fileExists(String path) {
        return Files.exists(rootDir.resolve(path));
    }

    public InputStream readFile(String path) throws IOException {
        return Files.newInputStream(rootDir.resolve(path));
    }

    public void writeFile(String destinationPath, InputStream source) throws IOException {
        Files.copy(source, rootDir.resolve(destinationPath));
    }

    public void copyFromLocal(String path, OutputStream destination) throws IOException {
        Files.copy(rootDir.resolve(path), destination);
    }

}
