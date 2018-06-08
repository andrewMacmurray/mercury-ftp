package server;

import filesystem.NativeFileSystem;
import server.connections.socket.SocketExecutor;

import java.io.IOException;
import java.net.Socket;

public class FtpConnectionThread extends Thread {

    private Socket commandSocket;
    private SocketExecutor socketExecutor;
    private NativeFileSystem fs;

    public FtpConnectionThread(Socket commandSocket, SocketExecutor socketExecutor, NativeFileSystem fs) {
        this.commandSocket = commandSocket;
        this.socketExecutor = socketExecutor;
        this.fs = fs;
    }

    @Override
    public void run() {
        try (FtpConnection ftpConnection = new FtpConnection(commandSocket, socketExecutor, fs)) {
            ftpConnection.processCommands();
        } catch (IOException e) {
            System.out.println("Error on thread " + Thread.currentThread().getName());
            System.out.println(e.getMessage());
        }
    }

}
