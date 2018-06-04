package server.handlers.connection;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface InputStreamAction {

    void run(InputStream in) throws IOException;

}
