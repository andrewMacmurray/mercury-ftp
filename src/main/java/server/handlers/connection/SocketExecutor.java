package server.handlers.connection;

import java.io.IOException;

public interface SocketExecutor {

    void inputStream(String host, int port, InputStreamAction action) throws IOException;

    void outputStream(String host, int port, OutputStreamAction action) throws IOException;

}
