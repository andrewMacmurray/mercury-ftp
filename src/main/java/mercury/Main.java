package mercury;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.PassivePortManager;
import mercury.server.connections.socket.SocketFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
//        String publicIp = args[0];
//        String userRootDirectory = args[1];
//
//        if (publicIp == null || userRootDirectory == null) {
//            throw new RuntimeException("Please pass public_ip and user_root_directory as arguments");
//        }

        String userRootDirectory = "ftp";
        String publicIp = "127.0.0.1";

        Integer fromPort = 2022;
        Integer toPort = 2026;

        ServerSocket serverSocket = new ServerSocket(2021);
        NativeFileSystem fs = new NativeFileSystem(userRootDirectory);
        PassivePortManager passivePortManager = new PassivePortManager(publicIp, fromPort, toPort);
        SocketFactory socketFactory = new SocketFactory();
        ExecutorService threadPool = Executors.newFixedThreadPool(toPort - fromPort + 1);

        FtpServer ftpServer = new FtpServer(serverSocket, passivePortManager, socketFactory, fs, threadPool);
        ftpServer.start();
    }

}
