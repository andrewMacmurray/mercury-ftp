package server.connections.socket;

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

    public SocketExecutor(SocketFactory socketFactory, int passivePort) {
        this.socketFactory = socketFactory;
        this.passivePort = passivePort;
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
            setPassiveHost();
        }
        passiveMode = true;
    }

    private void setPassiveHost() {
        passiveHost = passiveServerSocket.getInetAddress().getHostAddress();
    }

    public void setActiveMode(String host, int port) {
        closePassiveServerQuietly();
        passiveMode = false;
        activeHost = host;
        activePort = port;
    }

    private void closePassiveServerQuietly() {
        try {
            closePassiveServer();
        } catch (IOException e) {
            passiveServerSocket = null;
        }
    }

    private void closePassiveServer() throws IOException {
        if (passiveServerSocket != null) {
            passiveServerSocket.close();
            passiveServerSocket = null;
        }
    }

    public int getPassivePort() {
        return passivePort;
    }

    public String getPassiveHost() {
        return passiveHost;
    }

}
