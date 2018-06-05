package filesystem;

import server.connections.socket.InputStreamAction;
import server.connections.socket.OutputStreamAction;

import java.io.IOException;
import java.nio.file.Path;

public class FtpFileSystem {

    private NativeFileSystem nativeFileSystem;
    private WorkingDirectory workingDirectory;

    public FtpFileSystem(NativeFileSystem nativeFileSystem, WorkingDirectory workingDirectory) {
        this.nativeFileSystem = nativeFileSystem;
        this.workingDirectory = workingDirectory;
    }

    public void changeWorkingDirectory(String path) {
        if (isDirectory(path)) {
            workingDirectory.changeDirectory(removeLeadingSlash(path));
        }
    }

    public void cdUp() {
        workingDirectory.cdUp();
    }

    public boolean isDirectory(String path) {
        try {
            return nativeFileSystem.isValidDirectory(resolve(path));
        } catch (IOException e) {
            return false;
        }
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
        return workingDirectory.getCurrentDirectory().resolve(removeLeadingSlash(path));
    }

    private String removeLeadingSlash(String path) {
        return hasLeadingSlash(path) ? path.substring(1) : path;
    }

    private boolean hasLeadingSlash(String path) {
        return path.charAt(0) == '/';
    }

}
