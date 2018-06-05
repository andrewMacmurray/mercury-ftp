package doubles;

import server.handlers.connection.SocketFactory;

import java.io.*;

public class MockSocketFactory extends SocketFactory {

    public String host;
    public int port;
    private InputStream socketIn;

    public MockSocketFactory(InputStream socketIn) {
        this.socketIn = socketIn;
    }

    @Override
    public SocketStub create(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        return new SocketStub(socketIn);
    }

}
