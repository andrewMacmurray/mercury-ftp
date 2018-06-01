package server.commands;

@FunctionalInterface
public interface StatusResponder {

    void respond(int code, String message);

}
