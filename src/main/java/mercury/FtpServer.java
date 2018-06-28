package mercury;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.FtpConnectionThread;
import mercury.server.connections.PassivePortManager;
import mercury.server.connections.socket.SocketFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class FtpServer {

    private ServerSocket serverSocket;
    private PassivePortManager passivePortManager;
    private SocketFactory socketFactory;
    private NativeFileSystem fs;
    private ExecutorService threadPool;

    public FtpServer(
            ServerSocket serverSocket,
            PassivePortManager passivePortManager,
            SocketFactory socketFactory,
            NativeFileSystem fs,
            ExecutorService threadPool
    ) {
        this.serverSocket = serverSocket;
        this.socketFactory = socketFactory;
        this.fs = fs;
        this.passivePortManager = passivePortManager;
        this.threadPool = threadPool;
    }

    public void start() throws IOException {
        System.out.println("Starting Server");
        while (true) {
            System.out.println("Waiting for new connection");
            try {
                acceptConnection();
            } catch (IOException e) {
                logConnectionError(e);
            }
        }
    }

    private void acceptConnection() throws IOException {
        Socket commandSocket = serverSocket.accept();
        threadPool.execute(new FtpConnectionThread(commandSocket, passivePortManager, socketFactory, fs));
    }

    private void logConnectionError(IOException e) {
        System.out.println("Error processing connection");
        System.out.println(e.getMessage());
    }

}
