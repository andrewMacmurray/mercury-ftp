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
        if (args.length != 2) {
            throw new RuntimeException("Please pass public_ip and user_root_directory as arguments");
        }

        String publicIp = args[0];
        String userRootDirectory = args[1];
        Integer commandSocketPort = 21;
        Integer passiveFromPort = 2022;
        Integer passiveToPort = 2026;

        FtpServer ftpServer = new FtpServerBuilder(
                publicIp,
                userRootDirectory,
                commandSocketPort,
                passiveFromPort,
                passiveToPort
        ).build();

        ftpServer.start();
    }

}
