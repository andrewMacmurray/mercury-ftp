package doubles;

import filesystem.NativeFileSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeFileSystem extends NativeFileSystem {

    private List<String> input;
    private List<String> output = new ArrayList<>();

    public FakeFileSystem(String... input) {
        super("tmp");
        this.input = Arrays.asList(input);
    }

    public FakeFileSystem() {
        super("tmp");
        input = new ArrayList<>();
    }

    public List<String> getOutput() {
        return output;
    }

    @Override
    public boolean fileExists(String path) {
        return false;
    }

    @Override
    public InputStream readFile(String path) throws IOException {
        return null;
    }

    @Override
    public void writeFile(String destination, InputStream source) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(source));
        br.lines().forEach(line -> output.add(line));
    }

    @Override
    public void copyFromLocal(String path, OutputStream destination) throws IOException {
        PrintWriter pr = new PrintWriter(destination, true);
        for (String line : input) {
            pr.println(line);
        }
    }

}
