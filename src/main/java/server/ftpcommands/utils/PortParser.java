package server.ftpcommands.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PortParser {

    public static int parseIpv4(String rawIpv4Address) throws Exception {
        List<Integer> ipList = Arrays
                .stream(rawIpv4Address.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        return ipList.get(4) * 256 + ipList.get(5);
    }

}
