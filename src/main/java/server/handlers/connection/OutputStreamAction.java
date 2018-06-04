package server.handlers.connection;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface OutputStreamAction {

    void run(OutputStream outputStream) throws IOException;

}
