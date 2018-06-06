package filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;

public class FileInformation {

    private Path path;

    public FileInformation(Path path) {
        this.path = path;
    }

    public String getInfo() {
        try {
            return String.format(
                    "%s %d %s %s %d %s %s",
                    getPermissions(),
                    numberOfLinks(),
                    getOwner(),
                    getGroup(),
                    getSize(),
                    getLastModified(),
                    getName()
            );
        } catch (IOException e) {
            return getName();
        }
    }

    private String getPermissions() throws IOException {
        return isDirectory() ? "d" + filePermissions() : "-" + filePermissions();
    }

    private int numberOfLinks() {
        return isDirectory() ? 3 : 1;
    }

    private String getOwner() throws IOException {
        return Files.getOwner(path).toString();
    }

    private String getGroup() throws IOException {
        return Files
                .readAttributes(path, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS)
                .group()
                .toString();
    }

    private long getSize() throws IOException {
        return Files.size(path);
    }

    private String getLastModified() throws IOException {
        return Files.getLastModifiedTime(path).toString();
    }

    private String getName() {
        return path.getFileName().toString();
    }

    private String filePermissions() throws IOException {
        return PosixFilePermissions.toString(Files.getPosixFilePermissions(path));
    }

    private boolean isDirectory() {
        return Files.isDirectory(path);
    }

}
