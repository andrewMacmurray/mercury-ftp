package doubles;

import server.handlers.connection.InputStreamAction;
import server.handlers.connection.OutputStreamAction;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;

public class DummySocketExecutor implements SocketExecutor {

    @Override
    public void inputStream(String host, int port, InputStreamAction action) throws IOException {

    }

    @Override
    public void outputStream(String host, int port, OutputStreamAction action) throws IOException {

    }

}
