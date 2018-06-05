package server.connections.socket;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface OutputStreamAction {

    void runWithStream(OutputStream outputStream) throws IOException;

}
