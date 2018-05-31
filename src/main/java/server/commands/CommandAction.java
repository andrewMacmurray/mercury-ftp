package server.commands;

import java.io.IOException;

@FunctionalInterface
public interface CommandAction {

    void run(String argument) throws IOException;

}
