package server;

import filesystem.NativeFileSystem;
import server.handlers.CommandHandler;
import server.handlers.FileHandler;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class InterpreterFactory {

    public static CommandInterpreter create(Socket commandSocket, SocketExecutor dataSocketExecutor, NativeFileSystem fs) throws IOException {
        FileHandler fileHandler = new FileHandler(fs, dataSocketExecutor);
        CommandHandler commandHandler = createCommandHandler(commandSocket);
        return new CommandInterpreter(commandHandler, fileHandler);
    }

    private static CommandHandler createCommandHandler(Socket commandSocket) throws IOException {
        InputStream socketIn = commandSocket.getInputStream();
        OutputStream socketOut = commandSocket.getOutputStream();
        return new CommandHandler(socketIn, socketOut);
    }

}
