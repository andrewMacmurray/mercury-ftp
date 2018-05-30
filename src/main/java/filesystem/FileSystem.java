package filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileSystem {

    boolean fileExists(String path);

    InputStream readFile(String path) throws IOException;

    void writeFile(String destination, InputStream source) throws IOException;

    void copyFromLocal(String path, OutputStream destination) throws IOException;

}
