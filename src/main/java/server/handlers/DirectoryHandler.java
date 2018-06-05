package server.handlers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryHandler {

    private Path rootDirectory;

    public DirectoryHandler() {
        this.rootDirectory = Paths.get("/");
    }

    public String getCurrentDirectory() {
        return removeLeadingSlash(rootDirectory.toString());
    }

    public String getCurrentDirectoryWithPath(String path) {
        return removeLeadingSlash(rootDirectory.resolve(path).toString());
    }

    public void changeDirectory(String path) {
        rootDirectory = rootDirectory.resolve(removeLeadingSlash(path));
    }

    public void cdUp() {
        if (rootDirectory.getParent() != null) {
            rootDirectory = rootDirectory.getParent();
        }
    }

    private String removeLeadingSlash(String path) {
        return hasLeadingSlash(path) ? path.substring(1) : path;
    }

    private boolean hasLeadingSlash(String path) {
        return path.charAt(0) == '/';
    }

}
