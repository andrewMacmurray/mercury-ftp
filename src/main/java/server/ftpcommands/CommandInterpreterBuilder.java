package server.ftpcommands;

import filesystem.FileListingFormatter;
import filesystem.FtpFileSystem;
import filesystem.NativeFileSystem;
import filesystem.WorkingDirectory;
import server.connections.CommandConnection;
import server.connections.CommandResponses;
import server.connections.FileConnection;
import server.connections.socket.SocketExecutor;

import java.io.IOException;
import java.net.Socket;

public class CommandInterpreterBuilder {

    public static CommandInterpreter build(
            Socket commandSocket,
            SocketExecutor socketExecutor,
            NativeFileSystem fs
    ) throws IOException {
        CommandConnection commandConnection = createCommandConnection(commandSocket);
        CommandResponses commandResponses   = createResponses(commandConnection);
        return new CommandInterpreter(
                commandConnection::readCommand,
                commandResponses,
                createCommands(commandResponses, fs, socketExecutor)
        );
    }

    private static CommandResponses createResponses(CommandConnection commandConnection) {
        return new CommandResponses(commandConnection::writeResponse);
    }

    private static Commands createCommands(
            CommandResponses commandResponses,
            NativeFileSystem fs,
            SocketExecutor socketExecutor
    ) {
        return new CommandsFactory(
                commandResponses,
                createFileConnection(fs, socketExecutor)
        ).build();
    }

    private static FileConnection createFileConnection(NativeFileSystem fs, SocketExecutor socketExecutor) {
        return new FileConnection(
                createFileSystem(fs),
                socketExecutor
        );
    }

    private static CommandConnection createCommandConnection(Socket commandSocket) throws IOException {
        return new CommandConnection(
                commandSocket.getInputStream(),
                commandSocket.getOutputStream()
        );
    }

    private static FtpFileSystem createFileSystem(NativeFileSystem fs) {
        return new FtpFileSystem(
                fs,
                new FileListingFormatter(),
                new WorkingDirectory()
        );
    }

}
