package doubles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FakeSocket extends Socket {

    public boolean closed = false;
    private InputStream inputStream;
    private OutputStream outputStream;

    public FakeSocket(InputStream inputStream) {
        this.inputStream = inputStream;
        this.outputStream =  new ByteArrayOutputStream();
    }

    public FakeSocket(InputStream inputStream, OutputStream outputStream) {
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
