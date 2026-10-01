package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NoArgsConstructor;
import tools.jackson.core.type.TypeReference;

import static hexlet.code.formatter.Stylish.formatterStylish;

@NoArgsConstructor
public class Differ {
    /** Метод генерирующий разницу между двумя файлами */
    public static String generate(Path filePath1, Path filePath2) throws IOException {

        var dataFirstFile = convertObjectToMap(validateAndNormalize(filePath1));
        var dataSecondFile = convertObjectToMap(validateAndNormalize(filePath2));

        var differSortedListKey =
                Stream.concat(dataFirstFile.keySet().stream(), dataSecondFile.keySet().stream())
                        .distinct()
                        .sorted()
                        .toList();

        var differ =
                differSortedListKey.stream()
                        .map(key -> formatterStylish(dataFirstFile, dataSecondFile, key))
                        .collect(Collectors.joining("\n"));

        return "{\n" + differ + "\n}";
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
