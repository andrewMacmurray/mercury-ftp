package server.commands;

import java.io.IOException;

@FunctionalInterface
public interface IOBiConsumer<T, R> {

    void accept(T arg1, R arg2) throws IOException;

}
