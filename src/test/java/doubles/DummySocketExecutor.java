package doubles;

import server.connections.socket.InputStreamAction;
import server.connections.socket.OutputStreamAction;
import server.connections.socket.SocketExecutor;

import java.io.IOException;

public class DummySocketExecutor extends SocketExecutor {

    public DummySocketExecutor() {
        super(new MockSocketFactory());
    }

    @Override
    public void inputStream(String host, int port, InputStreamAction action) throws IOException {

    }

    @Override
    public void outputStream(String host, int port, OutputStreamAction action) throws IOException {

    }

}
