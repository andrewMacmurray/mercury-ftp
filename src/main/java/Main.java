import filesystem.NativeFileSystem;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2021);
        NativeFileSystem fs = new NativeFileSystem("tmp");

        FtpServer ftpServer = new FtpServer(serverSocket, fs);
        ftpServer.start();
    }

}
