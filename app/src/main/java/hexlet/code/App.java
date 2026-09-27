package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "diff 0.1")
public class App implements Callable<Integer> {

    /**
     * Переменная для отображения help
     */
    @Option(names = {"-h", "--help"},
            usageHelp = true,
            description = "Show this help message and exit.")
    private Boolean usageHelpRequested;

    /**
     * Переменная для отображения version
     */
    @Option(names = {"-V", "--version"},
            versionHelp = true,
            description = "Print version information and exit.")
    private Boolean versionInfoRequested;

    @Override
    public Integer call() throws Exception {
        return 1;
    }

    /**
     * Главный метод приложения. Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(final String[] args) {
        CommandLine commandLine = new CommandLine(new App());

        commandLine.parseArgs(args);

        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
        } else if (commandLine.isVersionHelpRequested()) {
            commandLine.printVersionHelp(System.out);
        }
    }


}
