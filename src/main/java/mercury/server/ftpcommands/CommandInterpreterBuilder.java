package mercury.server.ftpcommands;

import mercury.filesystem.FileListingFormatter;
import mercury.filesystem.FtpFileSystem;
import mercury.filesystem.NativeFileSystem;
import mercury.filesystem.WorkingDirectory;
import mercury.server.connections.CommandConnection;
import mercury.server.connections.CommandResponses;
import mercury.server.connections.FileConnection;
import mercury.server.connections.socket.SocketExecutor;

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
                commandConnection,
                commandResponses,
                createCommands(commandResponses, fs, socketExecutor)
        );
    }

    private static CommandResponses createResponses(CommandConnection commandConnection) {
        return new CommandResponses(commandConnection);
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
