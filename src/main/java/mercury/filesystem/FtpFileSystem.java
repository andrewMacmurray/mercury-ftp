package mercury.filesystem;

import mercury.server.connections.socket.InputStreamAction;
import mercury.server.connections.socket.OutputStreamAction;

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

    public boolean fileExists(String path) {
        return nativeFileSystem.fileExists(resolve(path));
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

    public InputStreamAction append(String path) {
        return inputStream -> nativeFileSystem.append(resolve(path), inputStream);
    }

    public OutputStreamAction list(String path) {
        return outputStream -> printFilesList(path, outputStream, fileListingFormatter::format);
    }

    public OutputStreamAction nameList(String path) {
        return outputStream -> printFilesList(path, outputStream, fileListingFormatter::name);
    }

    private void printFilesList(String path, OutputStream outputStream, FormatRunner formatRunner) throws IOException {
        Path dir = path.isEmpty() ? workingDirectory.getCurrentDirectory() : resolve(path);
        printDirectory(dir, createPrintWriter(outputStream), formatRunner);
    }

    private void printDirectory(Path path, PrintWriter pr, FormatRunner formatRunner) throws IOException {
        nativeFileSystem
                .list(path)
                .map(formatRunner::run)
                .forEach(x -> pr.printf("%s\r\n", x));
    }

    private PrintWriter createPrintWriter(OutputStream outputStream) {
        return new PrintWriter(outputStream, true);
    }

    private Path resolve(String path) {
        return workingDirectory
                .getCurrentDirectory()
                .resolve(removeLeadingSlash(path));
    }

    private String removeLeadingSlash(String path) {
        return path.charAt(0) == '/'
                ? path.substring(1)
                : path;
    }

}
