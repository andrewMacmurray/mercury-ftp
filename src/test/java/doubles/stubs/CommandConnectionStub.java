package doubles.stubs;

import mercury.server.connections.CommandConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CommandConnectionStub extends CommandConnection {

    public List<Integer> codes = new ArrayList<>();
    public List<String> messages = new ArrayList<>();

    public CommandConnectionStub() {
        super(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());
    }

    @Override
    public void writeResponse(int code, String message) {
        codes.add(code);
        messages.add(message);
    }

    @Override
    public String readCommand() {
        return null;
    }

}
