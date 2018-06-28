package mercury.server.connections;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PassivePorts {

    private Queue<Integer> availablePorts;
    private String hostAddress;

    public PassivePorts(String hostAddress, Integer fromPort, Integer toPort) {
        availablePorts = IntStream
                .range(fromPort, toPort)
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));
        this.hostAddress = hostAddress;
    }

    public Integer getAvailablePort() {
        return availablePorts.poll();
    }

    public void releasePort(Integer port) {
        availablePorts.add(port);
    }

    public String getHostAddress() {
        return hostAddress;
    }

}
