package doubles;

import server.handlers.connection.InputStreamAction;
import server.handlers.connection.OutputStreamAction;
import server.handlers.connection.SocketExecutor;

import java.io.*;

public class FakeSocketExecutor implements SocketExecutor {

    private InputStream inputStream;
    private OutputStream outputStream;

    public FakeSocketExecutor() {
        inputStream = new ByteArrayInputStream("".getBytes());
        outputStream = new ByteArrayOutputStream();
    }

    public FakeSocketExecutor(InputStream inputStream, OutputStream outputStream) {
       this.inputStream = inputStream;
       this.outputStream = outputStream;
    }

    @Override
    public void inputStream(String host, int port, InputStreamAction action) throws IOException {
        action.run(inputStream);
    }

    @Override
    public void outputStream(String host, int port, OutputStreamAction action) throws IOException {
        action.run(outputStream);
    }

}
