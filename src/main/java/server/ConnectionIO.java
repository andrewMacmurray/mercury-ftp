package server;

import filesystem.NativeFileSystem;
import server.handlers.CommandHandler;
import server.handlers.FileHandler;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;
import java.net.Socket;

public class ConnectionIO {

    private Socket commandSocket;
    private CommandHandler commandHandler;
    private CommandInterpreter commandInterpreter;

    public ConnectionIO(Socket commandSocket, NativeFileSystem fs, SocketExecutor socketExecutor) throws IOException {
        initConnection(commandSocket, fs, socketExecutor);
    }

    public void executeCommand(String rawCommand) throws IOException {
        commandInterpreter.execute(rawCommand);
    }

    public String readCommand() throws IOException {
        return commandHandler.readCommand();
    }

    public void clientConnected() {
        commandHandler.writeResponse(200, "Connected to Mercury");
    }

    public void clientDisconnect() throws IOException {
        commandHandler.writeResponse(421, "Terminating Connection");
        commandSocket.close();
    }

    private void initConnection(Socket commandSocket, NativeFileSystem fs, SocketExecutor socketExecutor) throws IOException {
        FileHandler fileHandler = new FileHandler(fs, socketExecutor);
        this.commandSocket = commandSocket;
        this.commandHandler = new CommandHandler(commandSocket.getInputStream(), commandSocket.getOutputStream());
        this.commandInterpreter = InterpreterFactory.create(fileHandler, commandHandler);
    }

}
