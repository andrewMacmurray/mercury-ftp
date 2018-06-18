package mercury.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FileListingFormatter {

    private Path path;

    public String format(Path path) {
        this.path = path;
        try {
            return getAllInfo();
        } catch (IOException e) {
            return "";
        }
    }

    public String name(Path path) {
        this.path = path;
        return getName();
    }

    private String getAllInfo() throws IOException {
        return String.format(
                "%s %d %s %s %8d %s %s",
                getPermissions(),
                numberOfLinks(),
                getOwner(),
                getGroup(),
                getSize(),
                getLastModified(),
                getName()
        );
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
        DateFormat format = new SimpleDateFormat("MMM dd yyyy");
        return format.format(Files.getLastModifiedTime(path).toMillis());
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
