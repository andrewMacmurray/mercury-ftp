package server;

import filesystem.NativeFileSystem;
import server.connections.CommandConnection;
import server.connections.FileConnection;
import server.connections.socket.SocketExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class InterpreterFactory {

    public static CommandInterpreter create(Socket commandSocket, SocketExecutor socketExecutor, NativeFileSystem fs) throws IOException {
        FileConnection fileConnection = new FileConnection(fs, socketExecutor);
        CommandConnection commandConnection = createCommandConnection(commandSocket);
        return new CommandInterpreter(commandConnection, fileConnection);
    }

    private static CommandConnection createCommandConnection(Socket commandSocket) throws IOException {
        InputStream socketIn = commandSocket.getInputStream();
        OutputStream socketOut = commandSocket.getOutputStream();
        return new CommandConnection(socketIn, socketOut);
    }

}
