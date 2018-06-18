package doubles.stubs;

import mercury.server.connections.socket.SocketFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SocketFactoryStub extends SocketFactory {

    public String host;
    public int port;
    private InputStream socketIn;

    public SocketFactoryStub(InputStream socketIn) {
        this.socketIn = socketIn;
    }

    public SocketFactoryStub() {
        this.socketIn = new ByteArrayInputStream("".getBytes());
    }

    @Override
    public SocketStub createSocket(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        return new SocketStub(socketIn);
    }

    @Override
    public ServerSocketStub createServerSocket(int port) throws IOException {
        this.port = port;
        return new ServerSocketStub(socketIn);
    }

}
