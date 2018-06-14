package doubles.stubs;

import filesystem.FileListingFormatter;

import java.nio.file.Path;

public class FileListingStub extends FileListingFormatter {

    @Override
    public String format(Path path) {
        return path.toString();
    }

    @Override
    public String name(Path path) {
        return path.getFileName().toString();
    }

}
