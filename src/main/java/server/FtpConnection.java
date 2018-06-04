package server;

import filesystem.NativeFileSystem;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;
import java.net.Socket;

public class FtpConnection implements AutoCloseable {

    private ConnectionIO connectionIO;

    public FtpConnection(Socket commandSocket, NativeFileSystem fs, SocketExecutor socketExecutor) throws IOException {
        connectionIO = new ConnectionIO(commandSocket, fs, socketExecutor);
        connectionIO.clientConnected();
    }

    public void processCommands() throws IOException {
        processNextCommand();
    }

    private void processNextCommand() throws IOException {
        String rawCommand = connectionIO.readCommand();
        if (shouldExecuteCommand(rawCommand)) {
            connectionIO.executeCommand(rawCommand);
            processNextCommand();
        }
    }

    private boolean shouldExecuteCommand(String rawCommand) {
        return rawCommand != null && !"QUIT".equalsIgnoreCase(rawCommand);
    }

    @Override
    public void close() throws Exception {
        connectionIO.clientDisconnect();
    }

}
