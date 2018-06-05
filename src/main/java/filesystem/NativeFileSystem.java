package filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NativeFileSystem {

    private final Path rootDir;

    public NativeFileSystem(String rootDir) {
        this.rootDir = Paths.get(rootDir);
    }

    public boolean fileExists(Path path) {
        return Files.exists(resolveRoot(path));
    }

    public boolean isValidDirectory(Path path) throws IOException {
        Path resolvedPath = resolveRoot(path);
        return Files.isDirectory(resolvedPath) && !Files.isHidden(resolvedPath);
    }

    public void writeFile(Path destinationPath, InputStream source) throws IOException {
        Files.copy(source, resolveRoot(destinationPath));
    }

    public void copyFromLocal(Path path, OutputStream destination) throws IOException {
        Files.copy(resolveRoot(path), destination);
    }

    private Path resolveRoot(Path path) {
        return rootDir.resolve(path);
    }

}
