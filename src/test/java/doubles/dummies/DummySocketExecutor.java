package doubles.dummies;

import doubles.mocks.MockSocketFactory;
import server.connections.socket.InputStreamAction;
import server.connections.socket.OutputStreamAction;
import server.connections.socket.SocketExecutor;

import java.io.IOException;

public class DummySocketExecutor extends SocketExecutor {

    public DummySocketExecutor() {
        super(new MockSocketFactory());
    }

    @Override
    public void inputStream(InputStreamAction action) throws IOException {

    }

    @Override
    public void outputStream(OutputStreamAction action) throws IOException {

    }

}
