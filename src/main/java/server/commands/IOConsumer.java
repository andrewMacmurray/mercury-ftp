package server.commands;

import java.io.IOException;

@FunctionalInterface
public interface IOConsumer {

    void accept(String argument) throws IOException;

}
