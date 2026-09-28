package hexlet.code;

import lombok.SneakyThrows;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Path;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "diff 0.1",
        showDefaultValues = true,
        description = "Compares two configuration files and shows a difference."
)
public class App implements Runnable {
    /**
     * Переменная пути для первого файла
     *
     * @param filePath1 пути до файла 1
     */
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private Path filePath1;

    /**
     * Переменная пути для второго файла
     *
     * @param filePath2 пути до файла 2
     */
    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private Path filePath2;

    /**
     *
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
            paramLabel = "format",
            description = "output format [default: stylish]")
    private String formate;

    /**
     * Метод для вызова каких-то значений
     */
    @SneakyThrows
    @Override
    public void run() {
        var diff = Differ.generate(filePath1, filePath2);
        System.out.println(diff);

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
