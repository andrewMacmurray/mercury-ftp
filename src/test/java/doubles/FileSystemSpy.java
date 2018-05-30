package doubles;

import filesystem.FileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileSystemSpy implements FileSystem {

    public boolean copyFromLocalCalled = false;
    public boolean writeFileCalled = false;

    @Override
    public boolean fileExists(String path) {
        return false;
    }

    @Override
    public InputStream readFile(String path) throws IOException {
        return null;
    }

    @Override
    public void writeFile(String destination, InputStream source) throws IOException {
        writeFileCalled = true;
    }

    @Override
    public void copyFromLocal(String path, OutputStream destination) throws IOException {
       copyFromLocalCalled = true;
    }

}
