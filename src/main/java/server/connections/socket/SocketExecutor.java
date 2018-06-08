package server.connections.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketExecutor {

    private SocketFactory socketFactory;

    public SocketExecutor(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }

    public void inputStream(String host, int port, InputStreamAction inputStreamAction) throws IOException {
        try (
                Socket socket = socketFactory.create(host, port);
                InputStream inputStream = socket.getInputStream();
        ) {
            inputStreamAction.runWithStream(inputStream);
        }
    }

    public void outputStream(String host, int port, OutputStreamAction outputStreamAction) throws IOException {
        try (
                Socket socket = socketFactory.create(host, port);
                OutputStream outputStream = socket.getOutputStream();
        ) {
            outputStreamAction.runWithStream(outputStream);
        }
    }

}
