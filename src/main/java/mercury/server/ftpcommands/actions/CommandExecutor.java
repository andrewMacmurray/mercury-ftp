package mercury.server.ftpcommands.actions;

public class CommandExecutor {

    public static void run(String rawCommand, ParsedCommandRunner action) {
        String[] args = rawCommand.split(" ");
        if (args.length > 1) {
            action.run(args[0], args[1]);
        } else {
            action.run(args[0], "");
        }
    }

    @FunctionalInterface
    public interface ParsedCommandRunner {
        void run(String command, String arg);
    }

}
