package doubles.dummies;

import doubles.FileListingStub;
import doubles.dummies.DummyFileSystem;
import filesystem.FtpFileSystem;
import filesystem.WorkingDirectory;

public class DummyFtpFileSystem extends FtpFileSystem {

    public DummyFtpFileSystem() {
        super(
                new DummyFileSystem(),
                new FileListingStub(),
                new WorkingDirectory()
        );
    }

}
