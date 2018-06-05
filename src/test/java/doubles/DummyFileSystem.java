package doubles;

import filesystem.NativeFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class DummyFileSystem extends NativeFileSystem {

    public DummyFileSystem() {
        super("tmp");
    }

    @Override
    public boolean fileExists(Path path) {
        return true;
    }

    @Override
    public void writeFile(Path destinationPath, InputStream source) throws IOException {

    }

    @Override
    public void copyFromLocal(Path path, OutputStream destination) throws IOException {

    }

}
