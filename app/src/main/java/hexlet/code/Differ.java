package hexlet.code;

import lombok.NoArgsConstructor;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;

@NoArgsConstructor
public class Differ {

    /**
     * Метод генерирующий разницу между двумя файлами
     */
    public static String generate(Path filePath1, Path filePath2) throws IOException {

        var dataFirstFile = convertJsonToMap(
                validateAndNormalize(filePath1)
        );
        var dataSecondFile = convertJsonToMap(
                validateAndNormalize(filePath2)
        );

        return dataFirstFile + "\n" + dataSecondFile;

    }

    private static Path validateAndNormalize(Path path) throws NoSuchFileException {
        Path normalizedPath = path.toAbsolutePath().normalize();

        if (Files.notExists(normalizedPath)) {
            throw new NoSuchFileException("File " + normalizedPath.getFileName() + " not found");
        }

        return normalizedPath;
    }

    private static Map convertJsonToMap(Path json) {
        return new ObjectMapper().readValue(json,
                new TypeReference<Map<String, Object>>() {
                }
        );
    }
}
