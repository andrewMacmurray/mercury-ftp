package server.connections.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketExecutor {

    private SocketFactory socketFactory;
    private boolean passiveMode;
    private ServerSocket passiveServerSocket;
    private int passivePort;
    private int activePort;
    private String activeHost;

    public SocketExecutor(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
        this.passiveMode = false;
    }

    public void inputStream(InputStreamAction inputStreamAction) throws IOException {
        try (
                Socket socket = createSocket();
                InputStream inputStream = socket.getInputStream();
        ) {
            inputStreamAction.runWithStream(inputStream);
        }
    }

    public void outputStream(OutputStreamAction outputStreamAction) throws IOException {
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

    public void setPassiveMode() throws IOException {
        if (passiveServerSocket == null) {
            passiveServerSocket = socketFactory.createServerSocket(passivePort);
        }
        passiveMode = true;
    }

    public void setActiveMode(String host, int port) throws IOException {
        if (passiveServerSocket != null) {
            passiveServerSocket.close();
            passiveServerSocket = null;
        }
        passiveMode = false;
        activeHost = host;
        activePort = port;
    }

    public void setPassivePort(int port) {
        passivePort = port;
    }

    public int getPassivePort() {
        return passivePort;
    }

    public String getPassiveHost() throws IOException {
        return passiveServerSocket.getInetAddress().getHostAddress();
    }

}
