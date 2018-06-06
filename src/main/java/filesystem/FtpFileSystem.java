package filesystem;

import server.connections.socket.InputStreamAction;
import server.connections.socket.OutputStreamAction;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;

public class FtpFileSystem {

    private NativeFileSystem nativeFileSystem;
    private FileListingFormatter fileListingFormatter;
    private WorkingDirectory workingDirectory;

    public FtpFileSystem(
            NativeFileSystem nativeFileSystem,
            FileListingFormatter fileListingFormatter,
            WorkingDirectory workingDirectory
    ) {
        this.nativeFileSystem = nativeFileSystem;
        this.fileListingFormatter = fileListingFormatter;
        this.workingDirectory = workingDirectory;
    }

    public void changeWorkingDirectory(String path) {
        if (isDirectory(path)) {
            workingDirectory.changeDirectory(removeLeadingSlash(path));
        }
    }

    public void cdUp() {
        workingDirectory.cdUp();
    }

    public boolean isDirectory(String path) {
        try {
            return nativeFileSystem.isValidDirectory(resolve(path));
        } catch (IOException e) {
            return false;
        }
    }

    public String getCurrentWorkingDirectory() {
        return "/" + workingDirectory.getCurrentDirectory().toString();
    }

    public InputStreamAction store(String path) {
        return inputStream -> nativeFileSystem.writeFile(resolve(path), inputStream);
    }

    public OutputStreamAction retrieve(String path) {
        return outputStream -> nativeFileSystem.copyFromLocal(resolve(path), outputStream);
    }

    public OutputStreamAction list(String path) {
        return outputStream -> getFilesList(path, outputStream, fileListingFormatter::format);
    }

    public OutputStreamAction nameList(String path) {
        return outputStream -> getFilesList(path, outputStream, fileListingFormatter::name);
    }

    private void getFilesList(String path, OutputStream outputStream, FormatRunner formatRunner) throws IOException {
        PrintWriter printWriter = createPrintWriter(outputStream);
        Path currentDirectory = workingDirectory.getCurrentDirectory();

        if (path.isEmpty()) {
            printFilesList(currentDirectory, printWriter, formatRunner);
        } else {
            printFilesList(resolve(path), printWriter, formatRunner);
        }
    }

    private void printFilesList(Path path, PrintWriter printWriter, FormatRunner formatRunner) throws IOException {
        nativeFileSystem
                .list(path)
                .map(formatRunner::run)
                .forEach(printWriter::println);
    }

    private PrintWriter createPrintWriter(OutputStream outputStream) {
        return new PrintWriter(outputStream, true);
    }

    private Path resolve(String path) {
        return workingDirectory.getCurrentDirectory().resolve(removeLeadingSlash(path));
    }

    private String removeLeadingSlash(String path) {
        return path.charAt(0) == '/' ? path.substring(1) : path;
    }

}
