package server;

import filesystem.NativeFileSystem;
import server.handlers.CommandHandler;
import server.handlers.FileHandler;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;
import java.net.Socket;

public class InterpreterFactory {

    public static CommandInterpreter create(Socket commandSocket, SocketExecutor dataSocketExecutor, NativeFileSystem fs) throws IOException {
        FileHandler fileHandler = new FileHandler(fs, dataSocketExecutor);
        CommandHandler commandHandler = new CommandHandler(commandSocket.getInputStream(), commandSocket.getOutputStream());
        return new CommandInterpreter(commandHandler, fileHandler);
    }

}
