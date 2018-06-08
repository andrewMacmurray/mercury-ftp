import filesystem.NativeFileSystem;
import server.connections.socket.SocketExecutor;
import server.connections.socket.SocketFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2021);
        SocketExecutor socketExecutor = new SocketExecutor(new SocketFactory());
        NativeFileSystem fs = new NativeFileSystem("tmp");

        FtpServer ftpServer = new FtpServer(serverSocket, socketExecutor, fs);
        ftpServer.start();
    }

}
