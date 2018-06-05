package server.connections.socket;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface InputStreamAction {

    void runWithStream(InputStream inputStream) throws IOException;

}
