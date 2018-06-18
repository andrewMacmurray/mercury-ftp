package doubles.fakes;

import mercury.server.connections.socket.InputStreamAction;
import mercury.server.connections.socket.OutputStreamAction;
import mercury.server.connections.socket.SocketExecutor;

import java.io.*;

public class FakeSocketExecutor extends SocketExecutor {

    private InputStream inputStream;
    private OutputStream outputStream;

    public FakeSocketExecutor() {
        super(null, 0);
        this.inputStream = new ByteArrayInputStream("".getBytes());
        this.outputStream = new ByteArrayOutputStream();
    }

    @Override
    public void inputStream(InputStreamAction action) throws IOException {
        action.runWithStream(inputStream);
    }

    @Override
    public void outputStream(OutputStreamAction action) throws IOException {
        action.runWithStream(outputStream);
    }

}
