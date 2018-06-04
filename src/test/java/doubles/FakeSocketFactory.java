package doubles;

import server.handlers.connection.SocketFactory;

import java.io.*;

public class FakeSocketFactory extends SocketFactory {

    public String host;
    public int port;
    private InputStream socketIn;

    public FakeSocketFactory(InputStream socketIn) {
        this.socketIn = socketIn;
    }

    @Override
    public FakeSocket create(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        return new FakeSocket(socketIn);
    }

}
