package doubles.stubs;

import mercury.server.connections.FileConnection;

public class FileConnectionStub extends FileConnection {

    public String retrieveCalledWith;
    public String storeCalledWith;
    public String activeModeFirstArg;
    public int activeModeSecondArg;
    public boolean passiveModeCalled;
    public String changeWorkingDirectoryCalledWith;
    public String isDirectoryCalledWith;
    public String sendFileListCalledWith;
    public String sendNameListCalledWith;
    public String fileExistsCalledWith;
    public String appendCalledWith;
    public boolean cdUpCalled;
    private boolean isDirectory;
    private boolean fileExists;

    public FileConnectionStub() {
        super(null, null);
        isDirectory = true;
        fileExists = true;
    }

    public FileConnectionStub(boolean shouldFilesExist) {
        super(null, null);
        this.isDirectory = shouldFilesExist;
        this.fileExists = shouldFilesExist;
    }

    @Override
    public void activeMode(String host, int port) {
        activeModeFirstArg = host;
        activeModeSecondArg = port;
    }

    @Override
    public void passiveMode() {
        passiveModeCalled = true;
    }

    @Override
    public String getPassiveAddress() {
        return "(0,0,0,0,12,12)";
    }

    @Override
    public void retrieve(String fileName) {
        retrieveCalledWith = fileName;
    }

    @Override
    public void store(String fileName) {
        storeCalledWith = fileName;
    }

    @Override
    public void append(String fileName) {
        appendCalledWith = fileName;
    }

    @Override
    public String currentDirectory() {
        return "/";
    }

    @Override
    public void changeWorkingDirectory(String path) {
        changeWorkingDirectoryCalledWith = path;
    }

    @Override
    public void cdUp() {
        cdUpCalled = true;
    }

    @Override
    public boolean fileExists(String fileName) {
        fileExistsCalledWith = fileName;
        return fileExists;
    }

    @Override
    public boolean isDirectory(String path) {
        isDirectoryCalledWith = path;
        return isDirectory;
    }

    @Override
    public void sendFileList(String path) {
        sendFileListCalledWith = path;
    }

    @Override
    public void sendNameList(String path) {
        sendNameListCalledWith = path;
    }

}
