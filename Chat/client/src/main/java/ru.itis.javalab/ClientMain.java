import com.beust.jcommander.JCommander;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientMain {
    public static void main(String[] argv) throws UnknownHostException {
        Args args = new Args();
        JCommander.newBuilder().addObject(args).build().parse(argv);
        new Client(InetAddress.getByName(args.host), args.port);
    }
}
