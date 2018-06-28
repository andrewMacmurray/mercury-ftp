package doubles.spies;

import mercury.server.connections.PassivePortManager;

public class PassivePortManagerSpy extends PassivePortManager {

    public Integer releasePortCalledWith;

    public PassivePortManagerSpy(String host, Integer fromPort, Integer toPort) {
        super(host, fromPort, toPort);
    }

    @Override
    public void releasePort(Integer port) {
        releasePortCalledWith = port;
        super.releasePort(port);
    }

}
