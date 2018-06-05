package doubles;

import filesystem.NativeFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DummyFileSystem extends NativeFileSystem {

    public DummyFileSystem() {
        super("tmp");
    }

    @Override
    public boolean fileExists(String path) {
        return true;
    }

    @Override
    public void writeFile(String destinationPath, InputStream source) throws IOException {

    }

    @Override
    public void copyFromLocal(String path, OutputStream destination) throws IOException {

    }

}
