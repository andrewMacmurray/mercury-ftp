package filesystem;

import server.handlers.connection.InputStreamAction;
import server.handlers.connection.OutputStreamAction;

import java.nio.file.Path;

public class FtpFileSystem {

    private NativeFileSystem nativeFileSystem;
    private WorkingDirectory workingDirectory;

    public FtpFileSystem(NativeFileSystem nativeFileSystem, WorkingDirectory workingDirectory) {
        this.nativeFileSystem = nativeFileSystem;
        this.workingDirectory = workingDirectory;
    }

    public void changeWorkingDirectory(String path) {
        if (nativeFileSystem.isDirectory(resolve(path))) {
            workingDirectory.changeDirectory(path);
        }
    }

    public boolean isDirectory(String path) {
        return nativeFileSystem.isDirectory(resolve(path));
    }

    public String getCurrentWorkingDirectory() {
        return "/" + workingDirectory.getCurrentDirectory().toString();
    }

    public InputStreamAction store(String path) {
        return inputStream -> nativeFileSystem.writeFile(resolve(path), inputStream);
    }

    public OutputStreamAction retrieve(String path) {
        return outputStream -> nativeFileSystem.copyFromLocal(resolve(path), outputStream);
    }

    private Path resolve(String path) {
        return workingDirectory.getCurrentDirectory().resolve(path);
    }

}
