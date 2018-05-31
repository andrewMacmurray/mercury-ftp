package server.handlers;

import java.io.*;

public class CommandHandler {

    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public CommandHandler(InputStream in, OutputStream out) {
        this.socketIn = new BufferedReader(new InputStreamReader(in));
        this.socketOut = new PrintWriter(out, true);
    }

    public String readCommand() throws IOException {
        return socketIn.readLine();
    }

    public void writeResponse(int code, String message) {
        socketOut.printf("%d %s\n", code, message);
    }

}
