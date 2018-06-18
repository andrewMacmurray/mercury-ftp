package mercury.server.ftpcommands;

import mercury.server.connections.CommandResponses;
import mercury.server.connections.FileConnection;
import mercury.server.ftpcommands.actions.CommandAction;
import mercury.server.ftpcommands.utils.NameGenerator;

public class CommandsFactory {

    private CommandRegistry commandRegistry;

    public CommandsFactory(CommandResponses commandResponses, FileConnection fileConnection) {
        this.commandRegistry = new CommandRegistry(
                commandResponses,
                fileConnection,
                new NameGenerator(fileConnection::fileExists)
        );
    }

    public Commands build() {
        return new Commands(commandRegistry::unrecognized).register(
                new Command("RETR", commandRegistry::RETR),
                new Command("STOR", commandRegistry::STOR),
                new Command("STOU", commandRegistry::STOU),
                new Command("APPE", commandRegistry::APPE),
                new Command("PORT", commandRegistry::PORT),
                new Command("USER", commandRegistry::USER),
                new Command("PASS", commandRegistry::PASS),
                new Command("CWD", commandRegistry::CWD),
                new Command("LIST", commandRegistry::LIST),
                new Command("NLST", commandRegistry::NLST),
                new Command("PWD", noArg(commandRegistry::PWD)),
                new Command("CDUP", noArg(commandRegistry::CDUP)),
                new Command("PASV", noArg(commandRegistry::PASV))
        );
    }

    private CommandAction noArg(Runnable action) {
        return ignoredArg -> action.run();
    }

}
