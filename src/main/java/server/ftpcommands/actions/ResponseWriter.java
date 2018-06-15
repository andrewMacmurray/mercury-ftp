package server.ftpcommands.actions;

@FunctionalInterface
public interface ResponseWriter {

    void write(int code, String message);

}
