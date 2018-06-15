package server.ftpcommands.actions;

import java.io.IOException;

@FunctionalInterface
public interface CommandReader {

    String readLine() throws IOException;

}
