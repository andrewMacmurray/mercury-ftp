package mercury.server.connections;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.socket.DataSocketExecutor;
import mercury.server.connections.socket.SocketFactory;
import mercury.server.ftpcommands.CommandInterpreter;
import mercury.server.ftpcommands.CommandInterpreterBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpConnectionBuilder {

    private Socket commandSocket;
    private PassivePortManager passivePortManager;
    private Integer provisionedPort;
    private SocketFactory socketFactory;
    private NativeFileSystem fs;

    public FtpConnectionBuilder(
            Socket commandSocket,
            PassivePortManager passivePortManager,
            SocketFactory socketFactory,
            NativeFileSystem fs
    ) {
        this.commandSocket = commandSocket;
        this.passivePortManager = passivePortManager;
        this.provisionedPort = passivePortManager.provisionPort();
        this.socketFactory = socketFactory;
        this.fs = fs;
    }

    public FtpConnection build() throws IOException {
        ServerSocket passiveServer = createPassiveServer();
        DataSocketExecutor dataSocketExecutor = createDataSocketExecutor(passiveServer);
        return new FtpConnection(
                commandSocket,
                passivePortManager,
                provisionedPort,
                passiveServer,
                createInterpreter(dataSocketExecutor)
        );
    }

    private ServerSocket createPassiveServer() throws IOException {
        return socketFactory.createServerSocket(provisionedPort);
    }

    private DataSocketExecutor createDataSocketExecutor(ServerSocket passiveServer) {
        return new DataSocketExecutor(socketFactory, passiveServer, provisionedPort, passivePortManager.getHost());
    }

    private CommandInterpreter createInterpreter(DataSocketExecutor dataSocketExecutor) throws IOException {
        return new CommandInterpreterBuilder(commandSocket, dataSocketExecutor, fs).build();
    }

}
