package mercury.server.connections;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PassivePortManager {

    private Queue<Integer> availablePorts;
    private String host;

    public PassivePortManager(String host, Integer fromPort, Integer toPort) {
        availablePorts = IntStream
                .rangeClosed(fromPort, toPort)
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));
        this.host = host;
    }

    public Integer provisionPort() {
        return availablePorts.poll();
    }

    public void releasePort(Integer port) {
        availablePorts.add(port);
    }

    public String getHost() {
        return host;
    }

}
