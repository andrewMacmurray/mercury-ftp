package server;

import filesystem.NativeFileSystem;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;
import java.net.Socket;

public class FtpConnection implements AutoCloseable {

    private Socket commandSocket;
    private CommandInterpreter commandInterpreter;

    public FtpConnection(Socket commandSocket, SocketExecutor dataSocketExecutor, NativeFileSystem fs) throws IOException {
        this.commandSocket = commandSocket;
        this.commandInterpreter = InterpreterFactory.create(commandSocket, dataSocketExecutor, fs);
    }

    public void processCommands() throws IOException {
        commandInterpreter.processCommands();
    }

    @Override
    public void close() throws IOException {
        commandSocket.close();
    }

}
