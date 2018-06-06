package doubles;

import filesystem.NativeFileSystem;

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
    private Stream<Path> subDirectories;

    public FileSystemSpy() {
        super("tmp");
        this.subDirectories = Stream.empty();
    }

    public FileSystemSpy(Stream<Path> subDirectories) {
        super("tmp");
        this.subDirectories = subDirectories;
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
    public void writeFile(Path path, InputStream source) throws IOException {
        storedFile = path.toString();
    }

    @Override
    public void copyFromLocal(Path path, OutputStream destination) throws IOException {
        retrievedFile = path.toString();
    }

    @Override
    public Stream<Path> list(Path path) throws IOException {
        listedDirectory = path.toString();
        return subDirectories;
    }

}
