package doubles;

import server.connections.CommandConnection;

import java.io.InputStream;
import java.io.OutputStream;

public class CommandConnectionSpy extends CommandConnection {

    public int code;
    public String message;

    public CommandConnectionSpy(InputStream in, OutputStream out) {
        super(in, out);
    }

    @Override
    public void writeResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
