import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = {"--server-ip"}, required = true)
    public String host;

    @Parameter(names = {"--server-port"})
    public Integer port;
}
