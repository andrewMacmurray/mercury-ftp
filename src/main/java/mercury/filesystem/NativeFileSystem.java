package mercury.filesystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Stream<Path> list(Path path) throws IOException {
        return Files.list(resolveRoot(path));
    }

    public void append(Path path, InputStream source) throws IOException {
        Files.write(
                resolveRoot(path),
                getLines(source),
                StandardOpenOption.APPEND
        );
    }

    private List<String> getLines(InputStream source) throws IOException {
        return new BufferedReader(new InputStreamReader(source))
                .lines()
                .collect(Collectors.toList());
    }

    private Path resolveRoot(Path path) {
        return rootDir.resolve(path);
    }

}
