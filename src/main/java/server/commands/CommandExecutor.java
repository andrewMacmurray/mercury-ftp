package server.commands;

import java.io.IOException;

@FunctionalInterface
public interface CommandExecutor {

    void run(String name, String argument) throws IOException;

}
