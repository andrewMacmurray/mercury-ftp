package mercury.server.ftpcommands;

import mercury.filesystem.FileListingFormatter;
import mercury.filesystem.FtpFileSystem;
import mercury.filesystem.NativeFileSystem;
import mercury.filesystem.WorkingDirectory;
import mercury.server.connections.CommandConnection;
import mercury.server.connections.CommandResponses;
import mercury.server.connections.FileConnection;
import mercury.server.connections.socket.DataSocketExecutor;

import java.io.IOException;
import java.net.Socket;

public class CommandInterpreterBuilder {

    private Socket commandSocket;
    private DataSocketExecutor dataSocketExecutor;
    private NativeFileSystem fs;

    public CommandInterpreterBuilder(
            Socket commandSocket,
            DataSocketExecutor dataSocketExecutor,
            NativeFileSystem fs
    ) {
        this.commandSocket = commandSocket;
        this.dataSocketExecutor = dataSocketExecutor;
        this.fs = fs;
    }

    public CommandInterpreter build() throws IOException {
        CommandConnection commandConnection = createCommandConnection();
        CommandResponses commandResponses = createResponses(commandConnection);

        return new CommandInterpreter(
                commandConnection,
                commandResponses,
                createCommands(commandResponses, dataSocketExecutor)
        );
    }

    private CommandResponses createResponses(CommandConnection commandConnection) {
        return new CommandResponses(commandConnection);
    }

    private Commands createCommands(CommandResponses commandResponses, DataSocketExecutor dataSocketExecutor) {
        return new CommandsFactory(commandResponses, createFileConnection(fs, dataSocketExecutor)).build();
    }

    private FileConnection createFileConnection(NativeFileSystem fs, DataSocketExecutor dataSocketExecutor) {
        return new FileConnection(createFileSystem(), dataSocketExecutor);
    }

    private CommandConnection createCommandConnection() throws IOException {
        return new CommandConnection(
                commandSocket.getInputStream(),
                commandSocket.getOutputStream()
        );
    }

    private FtpFileSystem createFileSystem() {
        return new FtpFileSystem(fs, new FileListingFormatter(), new WorkingDirectory());
    }

}
