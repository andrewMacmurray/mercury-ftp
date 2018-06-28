package mercury.server;

import mercury.server.ftpcommands.utils.NameGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NameGeneratorTest {

    private List<String> existingNames;
    private NameGenerator nameGenerator = new NameGenerator(this::fileExists);

    @Before
    public void setup() {
        existingNames = new ArrayList<>();
    }

    @Test
    public void generate() {
        String name = nameGenerator.generateUnique("foo.txt");
        assertEquals("foo.txt", name);
    }

    @Test
    public void generateUnique() {
        existingNames.add("hello.txt");
        existingNames.add("hello-1.txt");
        existingNames.add("hello-2.txt");

        String name = nameGenerator.generateUnique("hello.txt");
        assertEquals("hello-3.txt", name);
    }

    @Test
    public void dottedUnique() {
        existingNames.add("hello.world.txt");

        String name = nameGenerator.generateUnique("hello.world.txt");
        assertEquals("hello.world-1.txt", name);
    }

    @Test
    public void dotFiles() {
        existingNames.add(".config");
        String name = nameGenerator.generateUnique(".config");

        assertEquals(".config-1", name);
    }

    private boolean fileExists(String name) {
        return existingNames.contains(name);
    }

}
