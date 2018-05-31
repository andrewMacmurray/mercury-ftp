package doubles;

import server.handlers.CommandHandler;

import java.io.InputStream;
import java.io.OutputStream;

public class CommandHandlerSpy extends CommandHandler {

    public int code;
    public String message;

    public CommandHandlerSpy(InputStream in, OutputStream out) {
        super(in, out);
    }

    @Override
    public void writeResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
