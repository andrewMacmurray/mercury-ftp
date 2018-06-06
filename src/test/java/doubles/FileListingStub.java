package doubles;

import filesystem.FileListingFormatter;

import java.nio.file.Path;

public class FileListingStub extends FileListingFormatter {

    @Override
    public String format(Path path) {
        return path.toString();
    }

}
