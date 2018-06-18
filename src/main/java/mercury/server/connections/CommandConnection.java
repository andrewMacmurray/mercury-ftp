package mercury.server.connections;

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

}
