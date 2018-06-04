package server.handlers.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ActiveSocketExecutor implements SocketExecutor {

    private SocketFactory socketFactory;

    public ActiveSocketExecutor(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }

    @Override
    public void inputStream(String host, int port, InputStreamAction inputStreamAction) throws IOException {
        try (
                Socket socket = socketFactory.create(host, port);
                InputStream inputStream = socket.getInputStream();
        ) {
            inputStreamAction.run(inputStream);
        }
    }

    @Override
    public void outputStream(String host, int port, OutputStreamAction outputStreamAction) throws IOException {
        try (
                Socket socket = socketFactory.create(host, port);
                OutputStream outputStream = socket.getOutputStream();
        ) {
            outputStreamAction.run(outputStream);
        }
    }

}
