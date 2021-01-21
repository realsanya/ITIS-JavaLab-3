import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.List;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = {"--mode"})
    public String mode;

    @Parameter(names = {"--count"})
    public Integer count;

    @Parameter(names = {"--files"}, listConverter = UrlConverter.class)
    public List<String>files;

    @Parameter(names = {"--folder"})
    public String folder;

}

//$ java -jar app.jar --mode=one-thread --files=https://sun9-2.userapi.com/c855620/v855620872/185d10/9Kb1Hhf98pA.jpg --folder=C:/images
