package server.commands;

import java.io.IOException;

@FunctionalInterface
public interface IOConsumer<T> {

    void accept(T argument) throws IOException;

}
