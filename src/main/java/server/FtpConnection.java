package server;

import filesystem.NativeFileSystem;
import server.ftpcommands.CommandInterpreter;
import server.connections.socket.SocketExecutor;
import server.ftpcommands.CommandInterpreterBuilder;

import java.io.IOException;
import java.net.Socket;

public class FtpConnection implements AutoCloseable {

    private Socket commandSocket;
    private CommandInterpreter commandInterpreter;

    public FtpConnection(Socket commandSocket, SocketExecutor socketExecutor, NativeFileSystem fs) throws IOException {
        this.commandSocket = commandSocket;
        this.commandInterpreter = CommandInterpreterBuilder.build(commandSocket, socketExecutor, fs);
        setPassivePort(socketExecutor);
    }

    private void setPassivePort(SocketExecutor socketExecutor) {
        socketExecutor.setPassivePort(2022);
    }

    public void processCommands() throws IOException {
        commandInterpreter.processCommands();
    }

    @Override
    public void close() throws IOException {
        commandSocket.close();
    }

}
