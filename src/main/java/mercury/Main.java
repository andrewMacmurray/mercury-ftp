package mercury;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.socket.SocketExecutor;
import mercury.server.connections.socket.SocketFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        String publicIp = args[0];
        String userRootDirectory = args[1];

        if (publicIp == null || userRootDirectory == null) {
            throw new RuntimeException("Please pass public_ip and user_root_directory as arguments");
        }

        int passivePort = 2022;

        ServerSocket serverSocket = new ServerSocket(21);
        ServerSocket passiveServerSocket = new ServerSocket(passivePort);

        SocketExecutor socketExecutor = new SocketExecutor(
                new SocketFactory(),
                passiveServerSocket,
                passivePort,
                publicIp
        );
        NativeFileSystem fs = new NativeFileSystem(userRootDirectory);

        FtpServer ftpServer = new FtpServer(serverSocket, socketExecutor, fs);
        ftpServer.start();
    }

}
