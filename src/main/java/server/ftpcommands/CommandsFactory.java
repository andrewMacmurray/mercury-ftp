package server.ftpcommands;

import server.connections.FileConnection;
import server.ftpcommands.actions.CommandAction;
import server.ftpcommands.actions.CommandResponder;

public class CommandsFactory {

    private CommandRegistry commandRegistry;

    public CommandsFactory(CommandResponder commandResponder, FileConnection fileConnection) {
        this.commandRegistry = new CommandRegistry(commandResponder, fileConnection);
    }

    public Commands build() {
        return new Commands(commandRegistry::unrecognized).register(
                new Command("RETR", commandRegistry::RETR),
                new Command("STOR", commandRegistry::STOR),
                new Command("PORT", commandRegistry::PORT),
                new Command("USER", commandRegistry::USER),
                new Command("PASS", commandRegistry::PASS),
                new Command("CWD",  commandRegistry::CWD),
                new Command("LIST", commandRegistry::LIST),
                new Command("NLST", commandRegistry::NLST),
                new Command("PWD",  noArg(commandRegistry::PWD)),
                new Command("CDUP", noArg(commandRegistry::CDUP))
        );
    }

    private CommandAction noArg(Runnable action) {
        return ignoredArg -> action.run();
    }

}
