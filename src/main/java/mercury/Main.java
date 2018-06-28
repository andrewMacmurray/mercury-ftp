package mercury;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.PassivePorts;

import java.io.IOException;
import java.net.ServerSocket;

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

        ServerSocket serverSocket = new ServerSocket(2021);
        NativeFileSystem fs = new NativeFileSystem(userRootDirectory);
        PassivePorts passivePorts = new PassivePorts(publicIp, 2022, 2026);

        FtpServer ftpServer = new FtpServer(serverSocket, passivePorts, fs);
        ftpServer.start();
    }

}
