package server;

import org.junit.Test;
import server.ftpcommands.utils.NameGenerator;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NameGeneratorTest {

    private List<String> existingNames = Arrays.asList("hello.txt", "hello-1.txt", "hello-2.txt", "hello.world.txt");
    private NameGenerator nameGenerator = new NameGenerator(this::isUniqueName);

    @Test
    public void generate() {
        String name = nameGenerator.generateUnique("foo.txt");
        assertEquals("foo.txt", name);
    }

    @Test
    public void generateUnique() {
        String name = nameGenerator.generateUnique("hello.txt");
        assertEquals("hello-3.txt", name);
    }

    @Test
    public void dottedUnique() {
        String name = nameGenerator.generateUnique("hello.world.txt");
        assertEquals("hello.world-1.txt", name);
    }

    private boolean isUniqueName(String name) {
        return !existingNames.contains(name);
    }

}
