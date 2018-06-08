package server;

import doubles.spies.FileConnectionSpy;
import org.junit.Before;
import org.junit.Test;
import server.ftpcommands.CommandRegistry;

import static org.junit.Assert.assertEquals;

public class CommandRegistryTest {

    private int responseCode;
    private String responseMessage;
    private FileConnectionSpy fileConnectionSpy;
    private CommandRegistry commandRegistry;

    @Before
    public void setup() {
        fileConnectionSpy = new FileConnectionSpy();
        commandRegistry = new CommandRegistry(this::dummyResponder, fileConnectionSpy);
    }

    @Test
    public void retrieveFile() {
        commandRegistry.RETR("hello.txt");
        assertEquals("hello.txt", fileConnectionSpy.requestedFile);
        assertResponse(250, "OK File sent");
    }

    @Test
    public void storeFile() {
        commandRegistry.STOR("my-file.txt");
        assertEquals("my-file.txt", fileConnectionSpy.storedFile);
        assertResponse(250, "OK File stored");
    }

    @Test
    public void userName() {
        commandRegistry.USER("andrew");
        assertResponse(331, "Hey andrew, Please enter your password");
    }

    @Test
    public void port() {
        commandRegistry.PORT("127,0,0,1,211,127");
        assertResponse(200, "OK I got the Port");
    }

    @Test
    public void badPort() {
        commandRegistry.PORT("127,1,1");
        assertResponse(500, "Invalid Port");
    }

    @Test
    public void badPassword() {
        commandRegistry.PASS("hello");
        assertResponse(430, "Bad password, please try again");
    }

    @Test
    public void correctPassword() {
        commandRegistry.PASS("hermes");
        assertResponse(230, "Welcome to Mercury");
    }

    @Test
    public void pwd() {
        commandRegistry.PWD();
        assertResponse(257, "/");
    }

    @Test
    public void cwd() {
        commandRegistry.CWD("hello");
        assertResponse(257, "/hello");
    }

    @Test
    public void cdup() {
        commandRegistry.CWD("hello");
        commandRegistry.CDUP();
        assertResponse(257, "/");
    }

    @Test
    public void list() {
        commandRegistry.LIST("");
        assertResponse(227, "Retrieved the listing");
    }

    @Test
    public void unrecognised() {
        commandRegistry.unrecognized();
        assertResponse(500, "Unrecognized");
    }

    private void assertResponse(int code, String message) {
        assertEquals(code, responseCode);
        assertEquals(message, responseMessage);
    }

    private void dummyResponder(int code, String message) {
        responseCode = code;
        responseMessage = message;
    }

}
