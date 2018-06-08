package doubles.fakes;

import doubles.mocks.MockSocketFactory;
import server.connections.socket.InputStreamAction;
import server.connections.socket.OutputStreamAction;
import server.connections.socket.SocketExecutor;

import java.io.*;

public class FakeSocketExecutor extends SocketExecutor {

    private InputStream inputStream;
    private OutputStream outputStream;

    public FakeSocketExecutor() {
        super(new MockSocketFactory());
        this.inputStream = new ByteArrayInputStream("".getBytes());
        this.outputStream = new ByteArrayOutputStream();
    }

    @Override
    public void inputStream(String host, int port, InputStreamAction action) throws IOException {
        action.runWithStream(inputStream);
    }

    @Override
    public void outputStream(String host, int port, OutputStreamAction action) throws IOException {
        action.runWithStream(outputStream);
    }

}