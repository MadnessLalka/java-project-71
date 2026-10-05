package hexlet.code;

import hexlet.code.formatters.Formatter;
import java.io.IOException;
import java.nio.file.Path;
import lombok.NoArgsConstructor;

@NoArgsConstructor()
public class Differ {

    public static Formatter formatter;

    /** Метод генерирующий разницу между двумя файлами */
    public static String generate(String filePath1, String filePath2, String format)
            throws IOException {

        var parser = new Parser(Path.of(filePath1), Path.of(filePath2));
        var formatter =
                new Formatter(
                        parser.getOriginalFileMap(),
                        parser.getTargetFileMap(),
                        parser.getSortedListKey(),
                        format);

        return formatter.getFormatedDiff();
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }
}
