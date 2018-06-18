package mercury.server.ftpcommands.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Address {

    public static int getIpv4Port(String rawIpv4Address) {
        List<Integer> ipList = Arrays
                .stream(rawIpv4Address.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        return ipList.get(4) * 256 + ipList.get(5);
    }

    public static String getIpv4Host(String rawIpv4Address) {
        return Arrays
                .stream(rawIpv4Address.split(","))
                .limit(4)
                .collect(Collectors.joining("."));
    }

    public static String formatIpAddress(String host, int port) {
        String address = Arrays.stream(host.split("\\."))
                .limit(4)
                .collect(Collectors.joining(","));
        int p1 = port / 256;
        int p2 = port % 256;
        return String.format("(%s,%d,%d)", address, p1, p2);
    }

}
