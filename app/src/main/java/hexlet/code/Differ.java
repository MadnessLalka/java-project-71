package hexlet.code;

import hexlet.code.formatters.Formatter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Path;

@NoArgsConstructor
public class Differ {

    public static Formatter formatter;

    /**
     * Метод генерирующий разницу между двумя файлами
     */
    public static String generate(Path filePath1, Path filePath2, String formate)
            throws IOException {

        var parser = new Parser(filePath1, filePath2);

        return formatter.getFormatter(parser.getFileMapper1(),
                parser.getFileMapper1(),
                parser.getSortedListKey()
        );


//        Stream<String> diff =
//                differSortedListKey.stream()
//                        .map(
//                                key -> {
//                                    formatter =
//                                            switch (formate) {
//                                                case "plain" -> new Plain();
//                                                case "stylish" -> new Stylish();
//                                                case "json" -> new JSON();
//                                                default -> throw new IllegalStateException(
//                                                        "Unexpected value: " + formate);
//                                            };
//                                    return formatter.getFormatter(
//                                            dataFirstFile, dataSecondFile, key);
//                                })
//                        .filter(line -> !line.isBlank())


    }


}
