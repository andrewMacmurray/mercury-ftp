package server.connections;

public class CommandResponses {

    private CommandConnection commandConnection;

    public CommandResponses(CommandConnection commandConnection) {
        this.commandConnection = commandConnection;
    }

    public void signalConnected() {
        writeResponse(200, "Connected to Mercury");
    }

    public void welcome() {
        writeResponse(230, "Welcome to Mercury");
    }

    public void enterPassword(String userName) {
        sendFormattedResponse(331, "Hey %s, Please enter your password", userName);
    }

    public void signalDisconnect() {
        writeResponse(421, "Disconnected from Mercury");
    }

    public void badPassword() {
        writeResponse(430, "Bad password, please try again");
    }

    public void passiveConnectionSuccess(String ipAddress) {
        sendFormattedResponse(227, "Passive connection made %s", ipAddress);
    }

    public void unrecognized() {
        writeResponse(500, "Unrecognized");
    }

    public void invalidDirectory() {
        writeResponse(550, "Not a valid directory");
    }

    public void commandFailure(String message) {
        writeResponse(500, message);
    }

    public void gettingResource(String message) {
        writeResponse(150, message);
    }

    public void listingSuccess() {
        writeResponse(227, "Retrieved the listing");
    }

    public void directorySuccess(String message) {
        writeResponse(257, message);
    }

    public void fileError(String message) {
        writeResponse(450, message);
    }

    public void fileAppended(String fileName) {
        fileSuccess("Appended data to %s", fileName);
    }

    public void fileSent(String fileName) {
        fileSuccess("OK %s sent", fileName);
    }

    public void fileStored(String fileName) {
        fileSuccess("OK %s stored", fileName);
    }

    public void fileSuccess(String message, String fileName) {
        sendFormattedResponse(250, message, fileName);
    }

    public void commandSuccess(String message) {
        writeResponse(200, message);
    }

    public boolean isDisconnectCommand(String rawCommand) {
        return rawCommand == null || rawCommand.equalsIgnoreCase("QUIT");
    }

    private void sendFormattedResponse(int code, String message, String fileName) {
        writeResponse(code, String.format(message, fileName));
    }

    private void writeResponse(int code, String message) {
        commandConnection.writeResponse(code, message);
    }

}
