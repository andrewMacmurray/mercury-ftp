package server.connections;

import java.io.*;

public class CommandConnection {

    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public CommandConnection(InputStream in, OutputStream out) {
        this.socketIn = new BufferedReader(new InputStreamReader(in));
        this.socketOut = new PrintWriter(out, true);
    }

    public String readCommand() throws IOException {
        return socketIn.readLine();
    }

    public void writeResponse(int code, String message) {
        socketOut.printf("%d %s\n", code, message);
    }

    public void signalConnected() {
        writeResponse(200, "Connected to Mercury");
    }

    public void signalDisconnect() {
        writeResponse(421, "Disconnected from Mercury");
    }

    public boolean isDisconnectCommand(String rawCommand) {
        return rawCommand == null || rawCommand.equalsIgnoreCase("QUIT");
    }

}
