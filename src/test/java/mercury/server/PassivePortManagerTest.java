package mercury.server;

import mercury.server.connections.PassivePortManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PassivePortManagerTest {

    private PassivePortManager passivePortManager;

    @Before
    public void setup() {
        passivePortManager = new PassivePortManager("127.0.0.1", 2022, 2026);
    }

    @Test
    public void getsCorrectHost() {
        assertEquals("127.0.0.1", passivePortManager.getHost());
    }

    @Test
    public void provisionsPortCorrectly() {
        assertEquals(new Integer(2022), passivePortManager.provisionPort());
    }

    @Test
    public void provisionsSequentialPorts() {
        passivePortManager.provisionPort();
        passivePortManager.provisionPort();
        Integer availablePort = passivePortManager.provisionPort();

        assertEquals(new Integer(2024), availablePort);
    }

    @Test
    public void releasesPortCorrectly() {
        Integer availablePort = passivePortManager.provisionPort();
        passivePortManager.releasePort(availablePort);

        for (int i = 0; i < 4; i++) {
            passivePortManager.provisionPort();
        }

        assertEquals(new Integer(2022), passivePortManager.provisionPort());
    }
}
