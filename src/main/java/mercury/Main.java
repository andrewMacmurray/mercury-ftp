package mercury;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.socket.SocketExecutor;
import mercury.server.connections.socket.SocketFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2021);
        SocketExecutor socketExecutor = new SocketExecutor(new SocketFactory(), 2022);
        NativeFileSystem fs = new NativeFileSystem("tmp");

        FtpServer ftpServer = new FtpServer(serverSocket, socketExecutor, fs);
        ftpServer.start();
    }

}
