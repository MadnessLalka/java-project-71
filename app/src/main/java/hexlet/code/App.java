package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "diff 0.1",
        description = """
                Compares two configuration files and shows a difference.
                      filepath1         path to first file
                      filepath2         path to second file
                """
)
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

    /**
     * Переменная для выбора выходного формата
     */
    @Option(names = {"-f", "--format"},
            description = "output format [default: stylish]")
    private String formate;

    /**
     * Метод для вызова каких-то значений
     */
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
