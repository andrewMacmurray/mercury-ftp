package server.connections.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketFactory {

    public Socket create(String host, int port) throws IOException {
        return new Socket(host, port);
    }

    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }

}
