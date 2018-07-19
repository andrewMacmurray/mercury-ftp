package doubles.fakes;

import mercury.server.connections.socket.DataSocketExecutor;
import mercury.server.connections.socket.InputStreamAction;
import mercury.server.connections.socket.OutputStreamAction;

import java.io.*;

public class FakeDataSocketExecutor extends DataSocketExecutor {

    private InputStream inputStream;
    private OutputStream outputStream;

    public FakeDataSocketExecutor() {
        super(null, null, 0, "");
        this.inputStream = new ByteArrayInputStream("".getBytes());
        this.outputStream = new ByteArrayOutputStream();
    }

    @Override
    public void runInputStream(InputStreamAction action) throws IOException {
        action.runWithStream(inputStream);
    }

    @Override
    public void runOutputStream(OutputStreamAction action) throws IOException {
        action.runWithStream(outputStream);
    }

}
