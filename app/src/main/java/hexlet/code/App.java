package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "diff 0.1")
public class App implements Runnable {

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
    public void run() {

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
            return;
        } else if (commandLine.isVersionHelpRequested()) {
            commandLine.printVersionHelp(System.out);
            return;
        }
    }


}
