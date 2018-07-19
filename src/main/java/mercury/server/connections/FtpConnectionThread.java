package mercury.server.connections;

import mercury.filesystem.NativeFileSystem;
import mercury.server.connections.socket.SocketFactory;

import java.io.IOException;
import java.net.Socket;

public class FtpConnectionThread extends Thread {

    private Socket commandSocket;
    private PassivePortManager passivePortManager;
    private SocketFactory socketFactory;
    private NativeFileSystem fs;

    public FtpConnectionThread(
            Socket commandSocket,
            PassivePortManager passivePortManager,
            SocketFactory socketFactory,
            NativeFileSystem fs
    ) {
        this.commandSocket = commandSocket;
        this.passivePortManager = passivePortManager;
        this.socketFactory = socketFactory;
        this.fs = fs;
    }

    @Override
    public void run() {
        try (FtpConnection ftpConnection = new FtpConnectionBuilder(
                commandSocket,
                passivePortManager,
                socketFactory,
                fs).build()
        ) {
            ftpConnection.processCommands();
        } catch (IOException e) {
            System.out.println("Error on thread " + Thread.currentThread().getName());
            System.out.println(e.getMessage());
        }
    }

}
