package doubles.spies;

import mercury.filesystem.NativeFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileSystemSpy extends NativeFileSystem {

    public String checkedFile;
    public String checkedDirectory;
    public String storedFile;
    public String retrievedFile;
    public String listedDirectory;
    public String appendedFile;
    private Stream<Path> subPaths;

    public FileSystemSpy() {
        super("tmp");
        this.subPaths = Stream.empty();
    }

    public FileSystemSpy(Stream<Path> subPaths) {
        super("tmp");
        this.subPaths = subPaths;
    }

    @Override
    public boolean fileExists(Path path) {
        this.checkedFile = path.toString();
        return true;
    }

    @Override
    public boolean isValidDirectory(Path path) {
        this.checkedDirectory = path.toString();
        return true;
    }

    @Override
    public void writeFile(Path path, InputStream source) {
        storedFile = path.toString();
    }

    @Override
    public void copyFromLocal(Path path, OutputStream destination) {
        retrievedFile = path.toString();
    }

    @Override
    public Stream<Path> list(Path path) throws IOException {
        listedDirectory = path.toString();
        return subPaths;
    }

    @Override
    public void append(Path path, InputStream in) {
        appendedFile = path.toString();
    }

}
