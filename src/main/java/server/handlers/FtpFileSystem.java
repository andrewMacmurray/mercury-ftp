package server.handlers;

import filesystem.NativeFileSystem;
import server.handlers.connection.InputStreamAction;
import server.handlers.connection.OutputStreamAction;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FtpFileSystem {

    private NativeFileSystem nativeFileSystem;
    private WorkingDirectory workingDirectory;

    public FtpFileSystem(NativeFileSystem nativeFileSystem, WorkingDirectory workingDirectory) {
        this.nativeFileSystem = nativeFileSystem;
        this.workingDirectory = workingDirectory;
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
