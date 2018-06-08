package server.ftpcommands.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NameGenerator {

    private Predicate<String> fileExists;
    private List<String> fileParts;
    private String fileName;
    private String extension;

    public NameGenerator(Predicate<String> fileExists) {
        this.fileExists = fileExists;
    }

    public String generateUnique(String name) {
        setFileDetails(name);

        return isUnique(name)
                ? name
                : generateWithIndex(1);
    }

    private void setFileDetails(String name) {
        fileParts = getFileParts(name);
        fileName = getFileName();
        extension = getExt();
    }

    private String generateWithIndex(int index) {
        String newName = renameFile(index);

        return isUnique(newName)
                ? newName
                : generateWithIndex(index + 1);
    }

    private String renameFile(int index) {
        return String.format("%s-%d.%s", fileName, index, extension);
    }

    private String getExt() {
        return fileParts.get(fileParts.size() - 1);
    }

    private String getFileName() {
        return fileParts
                .subList(0, fileParts.size() - 1)
                .stream()
                .collect(Collectors.joining("."));
    }

    private List<String> getFileParts(String name) {
        return Arrays.asList(name.split("\\."));
    }

    private boolean isUnique(String name) {
        return !fileExists.test(name);
    }

}
