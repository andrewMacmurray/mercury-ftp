package mercury;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.FtpConnectionThread;
import mercury.server.connections.PassivePorts;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FtpServer {

    private ServerSocket serverSocket;
    private PassivePorts passivePorts;
    private NativeFileSystem fs;
    private ExecutorService threadPool;

    public FtpServer(ServerSocket serverSocket, PassivePorts passivePorts, NativeFileSystem fs) {
        this.serverSocket = serverSocket;
        this.fs = fs;
        this.passivePorts = passivePorts;
        this.threadPool = Executors.newFixedThreadPool(5);
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
        threadPool.execute(new FtpConnectionThread(commandSocket, passivePorts, fs));
    }

    private void logConnectionError(IOException e) {
        System.out.println("Error processing connection");
        System.out.println(e.getMessage());
    }

}
