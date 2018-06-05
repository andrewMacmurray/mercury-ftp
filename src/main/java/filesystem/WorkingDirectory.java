package filesystem;

import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkingDirectory {

    private Path root;

    public WorkingDirectory() {
        this.root = Paths.get("");
    }

    public Path getCurrentDirectory() {
        return root;
    }

    public void changeDirectory(String path) {
        root = root.resolve(removeLeadingSlash(path));
    }

    public void cdUp() {
        if (root.getParent() != null) {
            root = root.getParent();
        } else {
            root = Paths.get("");
        }
    }

    private String removeLeadingSlash(String path) {
        return hasLeadingSlash(path) ? path.substring(1) : path;
    }

    private boolean hasLeadingSlash(String path) {
        return path.charAt(0) == '/';
    }

}
