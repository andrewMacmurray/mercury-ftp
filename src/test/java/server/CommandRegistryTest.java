package server;

import doubles.DummyFtpFileSystem;
import doubles.DummySocketExecutor;
import doubles.FileConnectionSpy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandRegistryTest {

    private CommandRegistry commandRegistry;
    private FileConnectionSpy fileHandlerSpy;
    private int responseCode;
    private String responseMessage;

    @Before
    public void setup() {
        fileHandlerSpy = new FileConnectionSpy(new DummyFtpFileSystem(), new DummySocketExecutor());
        commandRegistry = new CommandRegistry(fileHandlerSpy, this::dummyResponseHandler);
    }

    @Test
    public void retrieveFile() {
        commandRegistry.executeCommand("RETR", "hello.txt");
        assertEquals("hello.txt", fileHandlerSpy.requestedFile);
        assertResponse(250, "OK File sent");
    }

    @Test
    public void storeFile() {
        commandRegistry.executeCommand("STOR", "my-file.txt");
        assertEquals("my-file.txt", fileHandlerSpy.storedFile);
        assertResponse(250, "OK File stored");
    }

    @Test
    public void userName() {
        commandRegistry.executeCommand("USER", "andrew");
        assertResponse(331, "Hey andrew, Please enter your password");
    }

    @Test
    public void port() {
        commandRegistry.executeCommand("PORT", "127,0,0,1,211,127");
        assertResponse(200, "OK I got the Port");
    }

    @Test
    public void badPort() {
        commandRegistry.executeCommand("PORT", "127,1,1");
        assertResponse(500, "Invalid Port");
    }

    @Test
    public void badPassword() {
        commandRegistry.executeCommand("PASS", "hello");
        assertResponse(430, "Bad password, please try again");
    }

    @Test
    public void correctPassword() {
        commandRegistry.executeCommand("PASS", "hermes");
        assertResponse(230, "Welcome to Mercury");
    }

    @Test
    public void pwd() {
        commandRegistry.executeCommand("PWD", "");
        assertResponse(257, "/");
    }

    @Test
    public void cwd() {
        commandRegistry.executeCommand("CWD", "hello");
        assertResponse(257, "/hello");
    }

    @Test
    public void cdup() {
        commandRegistry.executeCommand("CWD", "hello");
        commandRegistry.executeCommand("CDUP", "");
        assertResponse(257, "/");
    }

    @Test
    public void list() {
        commandRegistry.executeCommand("LIST", "");
        assertResponse(227, "Retrieved the listing");
    }

    @Test
    public void unrecognised() {
        commandRegistry.executeCommand("LOL", "wut?");
        assertResponse(500, "Unrecognized");
    }

    private void assertResponse(int code, String message) {
        assertEquals(code, responseCode);
        assertEquals(message, responseMessage);
    }

    private void dummyResponseHandler(Integer code, String message) {
        this.responseCode = code;
        this.responseMessage = message;
    }

}
