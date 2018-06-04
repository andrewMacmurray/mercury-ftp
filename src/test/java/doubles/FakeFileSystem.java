package doubles;

import filesystem.NativeFileSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeFileSystem extends NativeFileSystem {

    private List<String> storedFileLines;
    private List<String> output = new ArrayList<>();

    public FakeFileSystem(String... storedFileLines) {
        super("tmp");
        this.storedFileLines = Arrays.asList(storedFileLines);
    }

    public FakeFileSystem() {
        super("tmp");
        storedFileLines = new ArrayList<>();
    }

    public List<String> getOutput() {
        return output;
    }

    @Override
    public void writeFile(String destination, InputStream source) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(source));
        br.lines().forEach(line -> output.add(line));
    }

    @Override
    public void copyFromLocal(String path, OutputStream destination) throws IOException {
        PrintWriter pr = new PrintWriter(destination, true);
        for (String line : storedFileLines) {
            pr.println(line);
        }
    }

}
