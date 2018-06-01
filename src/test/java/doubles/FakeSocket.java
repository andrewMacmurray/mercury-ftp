package doubles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FakeSocket extends Socket {

    public boolean closed = false;

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }

    @Override
    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }

    @Override
    public void close() {
        closed = true;
    }

}
