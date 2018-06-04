import filesystem.NativeFileSystem;
import server.FtpConnectionThread;
import server.handlers.connection.ActiveSocketExecutor;
import server.handlers.connection.SocketExecutor;
import server.handlers.connection.SocketFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FtpServer {

    private ServerSocket serverSocket;
    private NativeFileSystem fs;
    private ExecutorService threadPool;

    public FtpServer(ServerSocket serverSocket, NativeFileSystem fs) {
        this.serverSocket = serverSocket;
        this.threadPool = Executors.newFixedThreadPool(5);
        this.fs = fs;
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
        SocketExecutor dataSocketExecutor = new ActiveSocketExecutor(new SocketFactory());
        threadPool.execute(new FtpConnectionThread(commandSocket, dataSocketExecutor, fs));
    }

    private void logConnectionError(IOException e) {
        System.out.println("Error processing connection");
        System.out.println(e.getMessage());
    }

}
