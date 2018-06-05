package doubles;

import filesystem.NativeFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class FileSystemSpy extends NativeFileSystem {

    public String checkedFile;
    public String storedFile;
    public String retrievedFile;

    public FileSystemSpy() {
        super("tmp");
    }

    @Override
    public boolean fileExists(Path path) {
        this.checkedFile = path.toString();
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

}
