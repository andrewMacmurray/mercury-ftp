package doubles;

import filesystem.NativeFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileSystemSpy extends NativeFileSystem {

    public String checkedFile;
    public String storedFile;
    public String retrievedFile;

    public FileSystemSpy() {
        super("tmp");
    }

    @Override
    public boolean fileExists(String path) {
        this.checkedFile = path;
        return true;
    }

    @Override
    public void writeFile(String path, InputStream source) throws IOException {
        storedFile = path;
    }

    @Override
    public void copyFromLocal(String path, OutputStream destination) throws IOException {
        retrievedFile = path;
    }

}
