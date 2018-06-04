package server;

import filesystem.NativeFileSystem;
import server.handlers.connection.SocketExecutor;

import java.io.IOException;
import java.net.Socket;

public class FtpConnectionThread extends Thread {

    private Socket commandSocket;
    private SocketExecutor dataSocketExecutor;
    private NativeFileSystem fs;

    public FtpConnectionThread(Socket commandSocket, SocketExecutor dataSocketExecutor, NativeFileSystem fs) {
        this.commandSocket = commandSocket;
        this.dataSocketExecutor = dataSocketExecutor;
        this.fs = fs;
    }

    @Override
    public void run() {
        try (FtpConnection ftpConnection = new FtpConnection(commandSocket, dataSocketExecutor, fs)) {
            ftpConnection.processCommands();
        } catch (IOException e) {
            System.out.println("Error on thread " + Thread.currentThread().getName());
            System.out.println(e.getMessage());
        }
    }

}
