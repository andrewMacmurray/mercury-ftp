package mercury;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.PassivePortManager;
import mercury.server.connections.socket.SocketFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FtpServerBuilder {

    private String publicIp;
    private String userRootDir;
    private Integer commandSocketPort;
    private Integer passiveFromPort;
    private Integer passiveToPort;

    public FtpServerBuilder(
            String publicIp,
            String userRootDir,
            Integer commandSocketPort,
            Integer passiveFromPort,
            Integer passiveToPort
    ) {
        this.publicIp = publicIp;
        this.userRootDir = userRootDir;
        this.commandSocketPort = commandSocketPort;
        this.passiveFromPort = passiveFromPort;
        this.passiveToPort = passiveToPort;
    }

    public FtpServer build() throws IOException {
        ServerSocket commandServerSocket = new ServerSocket(commandSocketPort);
        NativeFileSystem fs = new NativeFileSystem(userRootDir);
        PassivePortManager passivePortManager = new PassivePortManager(publicIp, passiveFromPort, passiveToPort);
        SocketFactory socketFactory = new SocketFactory();
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads());

        return new FtpServer(
                commandServerSocket,
                passivePortManager,
                socketFactory,
                fs,
                threadPool
        );
    }

    private Integer numberOfThreads() {
        return passiveToPort - passiveFromPort + 1;
    }

}
