package mercury.server.connections.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketExecutor {

    private SocketFactory socketFactory;
    private ServerSocket passiveServerSocket;
    private boolean passiveMode;
    private int passivePort;
    private String passiveHost;
    private int activePort;
    private String activeHost;

    public SocketExecutor(
            SocketFactory socketFactory,
            ServerSocket passiveServerSocket,
            int passivePort,
            String passiveHost
    ) {
        this.socketFactory = socketFactory;
        this.passiveServerSocket = passiveServerSocket;
        this.passivePort = passivePort;
        this.passiveHost = passiveHost;
        this.passiveMode = false;
    }

    public void runInputStream(InputStreamAction inputStreamAction) throws IOException {
        try (
                Socket socket = createSocket();
                InputStream inputStream = socket.getInputStream();
        ) {
            inputStreamAction.runWithStream(inputStream);
        }
    }

    public void runOutputStream(OutputStreamAction outputStreamAction) throws IOException {
        try (
                Socket socket = createSocket();
                OutputStream outputStream = socket.getOutputStream();
        ) {
            outputStreamAction.runWithStream(outputStream);
        }
    }

    private Socket createSocket() throws IOException {
        return passiveMode
                ? passiveServerSocket.accept()
                : socketFactory.createSocket(activeHost, activePort);
    }

    public void setPassiveMode() {
        passiveMode = true;
    }

    public void setActiveMode(String host, int port) {
        passiveMode = false;
        activeHost = host;
        activePort = port;
    }

    public int getPassivePort() {
        return passivePort;
    }

    public String getPassiveHost() {
        return passiveHost;
    }

}
