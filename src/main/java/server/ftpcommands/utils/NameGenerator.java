package server.ftpcommands.utils;

import java.util.function.Predicate;

public class NameGenerator {

    private Predicate<String> fileExists;
    private String fileName;
    private String extension;

    public NameGenerator(Predicate<String> fileExists) {
        this.fileExists = fileExists;
    }

    public String generateUnique(String name) {
        setDetails(name);

        return isUnique(name)
                ? name
                : generateWithIndex(1);
    }

    private String generateWithIndex(int index) {
        String newName = renameFile(index);

        return isUnique(newName)
                ? newName
                : generateWithIndex(index + 1);
    }

    private String renameFile(int index) {
        return fileName.isEmpty()
                ? String.format(".%s-%d", extension, index)
                : String.format("%s-%d.%s", fileName, index, extension);
    }

    private void setDetails(String name) {
        int i = lastDotIndex(name);
        fileName = name.substring(0, i);
        extension = name.substring(i + 1);
    }

    private int lastDotIndex(String name) {
        return name.lastIndexOf(".");
    }

    private boolean isUnique(String name) {
        return !fileExists.test(name);
    }

}
