package mercury.server.connections;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.socket.SocketExecutor;
import mercury.server.ftpcommands.CommandInterpreter;
import mercury.server.ftpcommands.CommandInterpreterBuilder;

import java.io.IOException;
import java.net.Socket;

public class FtpConnection implements AutoCloseable {

    private Socket commandSocket;
    private CommandInterpreter commandInterpreter;

    public FtpConnection(Socket commandSocket, SocketExecutor socketExecutor, NativeFileSystem fs) throws IOException {
        this.commandSocket = commandSocket;
        this.commandInterpreter = new CommandInterpreterBuilder(commandSocket, socketExecutor, fs).build();
    }

    public void processCommands() throws IOException {
        commandInterpreter.processCommands();
    }

    @Override
    public void close() throws IOException {
        commandSocket.close();
    }

}
