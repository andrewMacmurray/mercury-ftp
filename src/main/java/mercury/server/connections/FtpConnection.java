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
    private PassivePorts passivePorts;
    private Integer provisionedPort;
    private ServerSocket passiveServerSocket;
    private CommandInterpreter commandInterpreter;

    public FtpConnection(Socket commandSocket, PassivePorts passivePorts, NativeFileSystem fs) throws IOException {
        this.commandSocket = commandSocket;
        this.passivePorts = passivePorts;
        this.provisionedPort = passivePorts.getAvailablePort();
        System.out.println("provisioned port " + provisionedPort);
        this.commandInterpreter = new CommandInterpreterBuilder(
                commandSocket,
                createSocketExecutor(),
                fs).build();
    }

    public void processCommands() throws IOException {
        commandInterpreter.processCommands();
    }

    private SocketExecutor createSocketExecutor() throws IOException {
        SocketFactory socketFactory = new SocketFactory();
        passiveServerSocket = socketFactory.createServerSocket(provisionedPort);
        return new SocketExecutor(socketFactory, passiveServerSocket, provisionedPort, passivePorts.getHostAddress());
    }

    @Override
    public void close() throws IOException {
        System.out.println("releasing port " + provisionedPort);
        passivePorts.releasePort(provisionedPort);
        passiveServerSocket.close();
        commandSocket.close();
    }

}
