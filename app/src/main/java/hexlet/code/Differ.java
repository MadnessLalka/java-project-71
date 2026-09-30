package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import tools.jackson.core.type.TypeReference;

@NoArgsConstructor
public class Differ {
    /** Метод генерирующий разницу между двумя файлами */
    public static String generate(Path filePath1, Path filePath2) throws IOException {

        var dataFirstFile = convertObjectToMap(validateAndNormalize(filePath1));

        var dataSecondFile = convertObjectToMap(validateAndNormalize(filePath2));

        var differ =
                dataFirstFile.keySet().stream()
                        .sorted()
                        .map(key -> differBody(dataFirstFile, dataSecondFile, key))
                        .collect(Collectors.joining("\n"));

        var differEnd =
                dataSecondFile.keySet().stream()
                        .map(key -> differEnd(dataFirstFile, dataSecondFile, key))
                        .collect(Collectors.joining("\n"));

        if (!differEnd.isEmpty()) {
            differ += differEnd;
        }

        return "{\n" + differ + "}";
    }

    private static String differBody(
            Map<String, Object> dataFirstFile, Map<String, Object> dataSecondFile, String key) {
        var firstVal = dataFirstFile.get(key);
        var secondVal = dataSecondFile.get(key);
        var result = "";

        if (dataFirstFile.containsKey(key) && !dataSecondFile.containsKey(key)) {
            result += "  - " + key + ": " + firstVal;
        } else if (dataSecondFile.containsKey(key) && (firstVal.equals(secondVal))) {
            result += "    " + key + ": " + firstVal;
        } else if (dataSecondFile.containsKey(key)) {
            result += "  - " + key + ": " + firstVal + "\n" + "  + " + key + ": " + secondVal;
        }

        return result;
    }

    private static String differEnd(
            Map<String, Object> dataFirstFile, Map<String, Object> dataSecondFile, String key) {
        var result = "";

        if (!dataFirstFile.containsKey(key)) {
            result += "  + " + key + ": " + dataSecondFile.get(key);
        }

        return result;
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
