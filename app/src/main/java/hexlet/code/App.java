package hexlet.code;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import lombok.SneakyThrows;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

@Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "diff 0.1",
        showDefaultValues = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Spec CommandSpec spec;

    /**
     * Переменная пути для первого файла
     *
     * @param filePath1 пути до файла 1
     */
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private String filePath1;

    /**
     * Переменная пути для второго файла
     *
     * @param filePath2 пути до файла 2
     */
    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private String filePath2;

    /** Переменная для отображения help */
    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Show this help message and exit.")
    private Boolean usageHelpRequested;

    /** Переменная для отображения version */
    @Option(
            names = {"-V", "--version"},
            versionHelp = true,
            description = "Print version information and exit.")
    private Boolean versionInfoRequested;

    /** Переменная для выбора выходного формата */
    @Option(
            names = {"-f", "--format"},
            paramLabel = "format",
            defaultValue = "stylish",
            showDefaultValue = CommandLine.Help.Visibility.NEVER,
            description = "output format [default: ${DEFAULT-VALUE}]")
    private String formate;

    /** Метод для вызова каких-то значений */
    @SneakyThrows
    @Override
    public Integer call() {
        var out = spec.commandLine().getOut();
        out.println(Differ.generate(Path.of(filePath1), Path.of(filePath2), formate));
        return 0;
    }

    /**
     * Главный метод приложения. Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(final String[] args) {
        System.exit(new CommandLine(new App()).execute(args));
    }
}
