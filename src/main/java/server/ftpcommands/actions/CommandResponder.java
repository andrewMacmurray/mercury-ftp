package server.ftpcommands.actions;

@FunctionalInterface
public interface CommandResponder {

    void respond(int code, String message);

}
