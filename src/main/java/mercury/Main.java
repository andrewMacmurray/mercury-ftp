package mercury;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.socket.SocketExecutor;
import mercury.server.connections.socket.SocketFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        String publicIp = args[0];

        ServerSocket serverSocket = new ServerSocket(21);
        ServerSocket passiveServerSocket = new ServerSocket(2022);

        SocketExecutor socketExecutor = new SocketExecutor(
                new SocketFactory(),
                passiveServerSocket,
                2022,
                publicIp
        );
        NativeFileSystem fs = new NativeFileSystem("ftp");

        FtpServer ftpServer = new FtpServer(serverSocket, socketExecutor, fs);
        ftpServer.start();
    }

}
