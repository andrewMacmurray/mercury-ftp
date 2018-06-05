package server.commands;

@FunctionalInterface
public interface CommandResponder {

    void respond(int code, String message);

}
