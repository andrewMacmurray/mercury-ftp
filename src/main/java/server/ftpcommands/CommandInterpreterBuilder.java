package server.ftpcommands;

import filesystem.FileListingFormatter;
import filesystem.FtpFileSystem;
import filesystem.NativeFileSystem;
import filesystem.WorkingDirectory;
import server.connections.CommandConnection;
import server.connections.FileConnection;
import server.connections.socket.SocketExecutor;
import server.ftpcommands.CommandInterpreter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CommandInterpreterBuilder {

    public static CommandInterpreter build(
            Socket commandSocket,
            SocketExecutor socketExecutor,
            NativeFileSystem fs
    ) throws IOException {
        return new CommandInterpreter(
                createCommandConnection(commandSocket),
                createFileConnection(fs, socketExecutor)
        );
    }

    private static FileConnection createFileConnection(NativeFileSystem fs, SocketExecutor socketExecutor) {
        return new FileConnection(createFileSystem(fs), socketExecutor);
    }

    private static CommandConnection createCommandConnection(Socket commandSocket) throws IOException {
        InputStream socketIn = commandSocket.getInputStream();
        OutputStream socketOut = commandSocket.getOutputStream();
        return new CommandConnection(socketIn, socketOut);
    }

    private static FtpFileSystem createFileSystem(NativeFileSystem fs) {
        return new FtpFileSystem(
                fs,
                new FileListingFormatter(),
                new WorkingDirectory()
        );
    }

}
