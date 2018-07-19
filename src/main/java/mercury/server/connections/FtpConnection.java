package mercury.server.connections;

import mercury.server.ftpcommands.CommandInterpreter;

import java.io.Closeable;
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
            Integer provisionedPort,
            ServerSocket passiveServerSocket,
            CommandInterpreter commandInterpreter
    ) throws IOException {
        this.commandSocket = commandSocket;
        this.passivePortManager = passivePortManager;
        this.provisionedPort = provisionedPort;
        this.passiveServerSocket = passiveServerSocket;
        this.commandInterpreter = commandInterpreter;
    }

    public void processCommands() throws IOException {
        commandInterpreter.processCommands();
    }

    @Override
    public void close() {
        passivePortManager.releasePort(provisionedPort);
        closeQuietly(passiveServerSocket);
        closeQuietly(commandSocket);
    }

    private void closeQuietly(Closeable socket) {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
