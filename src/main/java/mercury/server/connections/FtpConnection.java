package mercury.server.connections;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.socket.SocketExecutor;
import mercury.server.connections.socket.SocketFactory;
import mercury.server.ftpcommands.CommandInterpreter;
import mercury.server.ftpcommands.CommandInterpreterBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpConnection implements AutoCloseable {

    private Socket commandSocket;
    private PassivePortManager passivePortManager;
    private Integer provisionedPort;
    private ServerSocket passiveServerSocket;
    private CommandInterpreter commandInterpreter;

    public FtpConnection(
            Socket commandSocket,
            PassivePortManager passivePortManager,
            SocketFactory socketFactory,
            NativeFileSystem fs
    ) throws IOException {
        this.commandSocket = commandSocket;
        this.passivePortManager = passivePortManager;
        this.provisionedPort = provisionPort();
        this.passiveServerSocket = createPassiveServer(socketFactory);
        this.commandInterpreter = createInterpreter(socketFactory, fs);
    }

    public void processCommands() throws IOException {
        commandInterpreter.processCommands();
    }

    private CommandInterpreter createInterpreter(SocketFactory socketFactory, NativeFileSystem fs) throws IOException {
        return new CommandInterpreterBuilder(commandSocket, createSocketExecutor(socketFactory), fs).build();
    }

    private Integer provisionPort() {
        return passivePortManager.getAvailablePort();
    }

    private ServerSocket createPassiveServer(SocketFactory socketFactory) throws IOException {
        return socketFactory.createServerSocket(provisionedPort);
    }

    private SocketExecutor createSocketExecutor(SocketFactory socketFactory) {
        return new SocketExecutor(
                socketFactory,
                passiveServerSocket,
                provisionedPort,
                passivePortManager.getHost()
        );
    }

    @Override
    public void close() throws IOException {
        passivePortManager.releasePort(provisionedPort);
        passiveServerSocket.close();
        commandSocket.close();
    }

}
