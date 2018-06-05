package server.commands;

@FunctionalInterface
public interface CommandAction {

    void run(String argument);

}
