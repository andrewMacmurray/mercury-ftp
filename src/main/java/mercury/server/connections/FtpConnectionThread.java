package mercury.server.connections;

import mercury.filesystem.NativeFileSystem;

import java.io.IOException;
import java.net.Socket;

public class FtpConnectionThread extends Thread {

    private Socket commandSocket;
    private PassivePorts passivePorts;
    private NativeFileSystem fs;

    public FtpConnectionThread(Socket commandSocket, PassivePorts passivePorts, NativeFileSystem fs) {
        this.commandSocket = commandSocket;
        this.passivePorts = passivePorts;
        this.fs = fs;
    }

    @Override
    public void run() {
        try (FtpConnection ftpConnection = new FtpConnection(commandSocket, passivePorts, fs)) {
            ftpConnection.processCommands();
        } catch (IOException e) {
            System.out.println("Error on thread " + Thread.currentThread().getName());
            System.out.println(e.getMessage());
        }
    }

}
