package filesystem;

import java.nio.file.Path;

@FunctionalInterface
public interface FormatRunner {

    String run(Path path);

}
