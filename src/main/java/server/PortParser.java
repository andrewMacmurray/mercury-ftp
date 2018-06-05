package server;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PortParser {

    public static int parse(String rawIpAddress) {
        List<Integer> ipList = Arrays
                .stream(rawIpAddress.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        return ipList.get(4) * 256 + ipList.get(5);
    }

}
