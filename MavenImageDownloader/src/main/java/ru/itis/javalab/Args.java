package ru.itis.javalab;

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
