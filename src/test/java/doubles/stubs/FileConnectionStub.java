package doubles.stubs;

import server.connections.FileConnection;

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
    public boolean cdUpCalled;
    private boolean isDirectory;

    public FileConnectionStub() {
        super(null, null);
        isDirectory = true;
    }

    public FileConnectionStub(boolean isDirectory) {
        super(null, null);
        this.isDirectory = isDirectory;
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
