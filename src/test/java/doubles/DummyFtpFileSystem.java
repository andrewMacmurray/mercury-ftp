package doubles;

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
