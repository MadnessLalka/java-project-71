package hexlet.code;

import hexlet.code.formatters.Formatter;
import java.io.IOException;
import java.nio.file.Path;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Differ {

    public static Formatter formatter;

    /** Метод генерирующий разницу между двумя файлами */
    public static String generate(Path filePath1, Path filePath2, String format)
            throws IOException {

        var parser = new Parser(filePath1, filePath2);
        var formatter =
                new Formatter(
                        parser.getOriginalFileMap(),
                        parser.getTargetFileMap(),
                        parser.getSortedListKey(),
                        format);

        return formatter.getFormatedDiff();
    }
}
