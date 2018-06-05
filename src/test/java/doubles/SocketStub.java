package doubles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStub extends Socket {

    public boolean closed = false;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SocketStub(InputStream inputStream) {
        this.inputStream = inputStream;
        this.outputStream =  new ByteArrayOutputStream();
    }

    public SocketStub(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void close() {
        closed = true;
    }

}
