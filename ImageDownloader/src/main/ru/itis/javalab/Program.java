package main.ru.itis.javalab;

import com.beust.jcommander.JCommander;


public class Program {
    public static void main(String[] argv) {
        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);

        ThreadPool threadPool = new ThreadPool(args.mode.equals("one-thread") ? 1 : args.count);

        for (String name : args.files) {
            threadPool.submit(new DownloadTask(name, args.folder));
        }
    }
}