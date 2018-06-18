package mercury.server.ftpcommands.actions;

@FunctionalInterface
public interface CommandAction {

    void run(String argument);

}
