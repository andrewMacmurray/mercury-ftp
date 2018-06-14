package doubles.stubs;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketStub extends ServerSocket {

    private SocketStub socketStub;
    private boolean closed;

    public ServerSocketStub(InputStream in) throws IOException {
        this.socketStub = new SocketStub(in);
        this.closed = false;
    }

    @Override
    public Socket accept() {
        return socketStub;
    }

    @Override
    public void close() {
        closed = true;
    }

}
