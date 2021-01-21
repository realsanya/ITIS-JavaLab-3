import com.beust.jcommander.JCommander;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] argv) throws IOException {
        Args args = new Args();
        JCommander.newBuilder().addObject(args).build().parse(argv);
        new Server(args.port);
    }
}
