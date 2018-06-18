package doubles.spies;

import mercury.server.ftpcommands.utils.NameGenerator;

public class NameGeneratorSpy extends NameGenerator {

    public String generateUniqueCalledWith;

    public NameGeneratorSpy() {
        super(null);
    }

    @Override
    public String generateUnique(String name) {
        generateUniqueCalledWith = name;
        return "unique-name.txt";
    }

}
