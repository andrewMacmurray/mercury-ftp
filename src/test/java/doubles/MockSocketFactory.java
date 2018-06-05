package doubles;

import server.connections.socket.SocketFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MockSocketFactory extends SocketFactory {

    public String host;
    public int port;
    private InputStream socketIn;

    public MockSocketFactory(InputStream socketIn) {
        this.socketIn = socketIn;
    }

    public MockSocketFactory() {
        this.socketIn = new ByteArrayInputStream("".getBytes());
    }

    @Override
    public SocketStub create(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        return new SocketStub(socketIn);
    }

}
