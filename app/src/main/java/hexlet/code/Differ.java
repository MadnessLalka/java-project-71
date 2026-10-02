package hexlet.code;

import hexlet.code.formatter.Formatter;
import hexlet.code.formatter.Plain;
import hexlet.code.formatter.Stylish;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NoArgsConstructor;
import tools.jackson.core.type.TypeReference;

@NoArgsConstructor
public class Differ {

    public static Formatter formatter;

    /** Метод генерирующий разницу между двумя файлами */
    public static String generate(Path filePath1, Path filePath2, String formate)
            throws IOException {

        var dataFirstFile = convertObjectToMap(validateAndNormalize(filePath1));
        var dataSecondFile = convertObjectToMap(validateAndNormalize(filePath2));

        var differSortedListKey =
                Stream.concat(dataFirstFile.keySet().stream(), dataSecondFile.keySet().stream())
                        .distinct()
                        .sorted()
                        .toList();

        var diff =
                differSortedListKey.stream()
                        .map(
                                key -> {
                                    formatter =
                                            switch (formate) {
                                                case "plain" -> new Plain();
                                                case "stylish" -> new Stylish();
                                                default ->
                                                        throw new IllegalStateException(
                                                                "Unexpected value: " + formate);
                                            };
                                    return formatter.getFormatter(
                                            dataFirstFile, dataSecondFile, key);
                                })
                        .filter(line -> !line.isBlank())
                        .collect(Collectors.joining("\n"));

        return formatter.wrapIfNeeded(diff);
    }

    private static Path validateAndNormalize(Path path) throws NoSuchFileException {
        Path normalizedPath = path.toAbsolutePath().normalize();

        if (Files.notExists(normalizedPath)) {
            throw new NoSuchFileException("File " + normalizedPath.getFileName() + " not found");
        }

        return normalizedPath;
    }

    private static Map<String, Object> convertObjectToMap(Path file) {
        return Parser.getObjectMapper(file).readValue(file, new TypeReference<>() {});
    }
}
