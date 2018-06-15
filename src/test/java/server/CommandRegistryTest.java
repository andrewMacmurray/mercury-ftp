package server;

import doubles.spies.NameGeneratorSpy;
import doubles.stubs.ErroringFileConnectionStub;
import doubles.stubs.FileConnectionStub;
import org.junit.Before;
import org.junit.Test;
import server.connections.CommandResponses;
import server.ftpcommands.CommandRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandRegistryTest {

    private List<Integer> responseCodes;
    private List<String> responseMessages;
    private CommandRegistry commandRegistry;
    private FileConnectionStub fileConnectionStub;
    private CommandResponses responder;
    private ErroringFileConnectionStub erroringFileConnectionStub;

    @Before
    public void setup() {
        responseCodes = new ArrayList<>();
        responseMessages = new ArrayList<>();
        fileConnectionStub = new FileConnectionStub();
        erroringFileConnectionStub = new ErroringFileConnectionStub();
        responder = new CommandResponses(this::dummyResponder);
        commandRegistry = new CommandRegistry(responder, fileConnectionStub, null);
    }

    @Test
    public void triggersFileRetrievalAndSendsResponses() {
        commandRegistry.RETR("hello.txt");

        assertEquals("hello.txt", fileConnectionStub.retrieveCalledWith);

        assertFirstResponse(150, "OK getting File");
        assertSecondResponse(250, "OK hello.txt sent");
    }

    @Test
    public void reportsFileRetrievalError() {
        commandRegistry = new CommandRegistry(responder, erroringFileConnectionStub, null);

        commandRegistry.RETR("hello.txt");

        assertFirstResponse(150, "OK getting File");
        assertSecondResponse(450, "Error retrieving File");
    }

    @Test
    public void triggersFileStorageAndSendsResponses() {
        commandRegistry.STOR("hello.txt");

        assertFirstResponse(150, "OK receiving File");
        assertSecondResponse(250, "OK hello.txt stored");
    }

    @Test
    public void reportsFileStorageError() {
        commandRegistry = new CommandRegistry(responder, erroringFileConnectionStub, null);

        commandRegistry.STOR("hello.txt");

        assertFirstResponse(150, "OK receiving File");
        assertSecondResponse(450, "Error storing File");
    }

    @Test
    public void triggersStoreFileWithUniqueName() {
        NameGeneratorSpy nameGeneratorSpy = new NameGeneratorSpy();
        commandRegistry = new CommandRegistry(responder, fileConnectionStub, nameGeneratorSpy);

        commandRegistry.STOU("hello.txt");

        assertFirstResponse(150, "OK receiving File");
        assertSecondResponse(250, "OK unique-name.txt stored");
    }

    @Test
    public void triggersAppendingOfFileIfFileExists() {
        commandRegistry.APPE("hello.txt");

        assertEquals("hello.txt", fileConnectionStub.fileExistsCalledWith);
        assertEquals("hello.txt", fileConnectionStub.appendCalledWith);
        assertFirstResponse(150, "OK receiving data");
        assertSecondResponse(250, "Appended data to hello.txt");
    }

    @Test
    public void triggersStoreIfAppendedFileDoesNotExist() {
        fileConnectionStub = new FileConnectionStub(false);
        commandRegistry = new CommandRegistry(responder, fileConnectionStub, null);

        commandRegistry.APPE("hello.txt");

        assertEquals("hello.txt", fileConnectionStub.fileExistsCalledWith);
        assertEquals("hello.txt", fileConnectionStub.storeCalledWith);
        assertFirstResponse(150, "OK receiving data");
        assertSecondResponse(250, "OK hello.txt stored");
    }

    @Test
    public void sendsErrorIfAppendFails() {
        commandRegistry = new CommandRegistry(responder, erroringFileConnectionStub, null);

        commandRegistry.APPE("hello.txt");

        assertFirstResponse(150, "OK receiving data");
        assertSecondResponse(450, "Error appending file");
    }

    @Test
    public void enteringUsernamePromptsUserForPassword() {
        commandRegistry.USER("andrew");

        assertFirstResponse(331, "Hey andrew, Please enter your password");
    }

    @Test
    public void setsActiveModeWithPortSentFromClient() {
        commandRegistry.PORT("127,0,0,1,211,127");

        assertEquals("localhost", fileConnectionStub.activeModeFirstArg);
        assertEquals(54143, fileConnectionStub.activeModeSecondArg);
        assertFirstResponse(200, "OK I got the Port");
    }

    @Test
    public void sendsErrorMessageForFailedPort() {
        commandRegistry.PORT("127,1,1");

        assertFirstResponse(500, "Invalid Port");
    }

    @Test
    public void triggersPassiveModeAndSendsResponse() {
        commandRegistry.PASV();

        assertTrue(fileConnectionStub.passiveModeCalled);
        assertFirstResponse(227, "Passive connection made (0,0,0,0,12,12)");
    }

    @Test
    public void sendsErrorIfPassiveModeError() {
        commandRegistry = new CommandRegistry(responder, erroringFileConnectionStub, null);

        commandRegistry.PASV();

        assertFirstResponse(500, "Error setting passive mode");
    }

    @Test
    public void sendsErrorMessageForInvalidPassword() {
        commandRegistry.PASS("hello");

        assertFirstResponse(430, "Bad password, please try again");
    }

    @Test
    public void sendsSuccessMessageForCorrectPassword() {
        commandRegistry.PASS("hermes");

        assertFirstResponse(230, "Welcome to Mercury");
    }

    @Test
    public void sendsCurrentWorkingDirectory() {
        commandRegistry.PWD();

        assertFirstResponse(257, "/");
    }

    @Test
    public void changesCurrentWorkingDirectoryAndSendsInResponse() {
        commandRegistry.CWD("hello");

        assertEquals("hello", fileConnectionStub.isDirectoryCalledWith);
        assertEquals("hello", fileConnectionStub.changeWorkingDirectoryCalledWith);
        assertFirstResponse(257, "/");
    }

    @Test
    public void sendsErrorIfInvalidDirectory() {
        fileConnectionStub = new FileConnectionStub(false);
        commandRegistry = new CommandRegistry(responder, fileConnectionStub, null);

        commandRegistry.CWD("hello");

        assertFirstResponse(550, "Not a valid directory");
    }

    @Test
    public void movesUpOneDirectoryAndSendsInResponse() {
        commandRegistry.CDUP();

        assertTrue(fileConnectionStub.cdUpCalled);
        assertFirstResponse(257, "/");
    }

    @Test
    public void triggersSendingOfFileListAndResponse() {
        commandRegistry.LIST("hello");

        assertEquals("hello", fileConnectionStub.sendFileListCalledWith);
        assertFirstResponse(150, "Getting a file list");
        assertSecondResponse(227, "Retrieved the listing");
    }

    @Test
    public void sendsErrorMessageForFailedListing() {
        commandRegistry = new CommandRegistry(responder, erroringFileConnectionStub, null);

        commandRegistry.LIST("hello");

        assertFirstResponse(150, "Getting a file list");
        assertSecondResponse(450, "Could not get listing");
    }

    @Test
    public void triggersSendingOfNamedListAndResponse() {
        commandRegistry.NLST("hello");

        assertEquals("hello", fileConnectionStub.sendNameListCalledWith);
        assertFirstResponse(150, "Getting a list of file names");
        assertSecondResponse(227, "Retrieved the listing");
    }

    @Test
    public void sendsErrorMessageForFailedNameListing() {
        commandRegistry = new CommandRegistry(responder, erroringFileConnectionStub, null);

        commandRegistry.NLST("hello");

        assertFirstResponse(150, "Getting a list of file names");
        assertSecondResponse(450, "Could not get listing");
    }
    @Test
    public void sendsUnrecognisedResponse() {
        commandRegistry.unrecognized();
        assertFirstResponse(500, "Unrecognized");
    }

    private void assertFirstResponse(Integer code, String message) {
        assertEquals(code, responseCodes.get(0));
        assertEquals(message, responseMessages.get(0));
    }

    private void assertSecondResponse(Integer code, String message) {
        assertEquals(code, responseCodes.get(1));
        assertEquals(message, responseMessages.get(1));
    }

    private void dummyResponder(int code, String message) {
        responseCodes.add(code);
        responseMessages.add(message);
    }

}
