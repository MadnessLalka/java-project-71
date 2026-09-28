package hexlet.code;

import lombok.NoArgsConstructor;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@NoArgsConstructor
public class Differ {

    /**
     * Метод генерирующий разницу между двумя файлами
     */
    public static String generate(Path filePath1, Path filePath2) throws IOException {

        filePath1 = filePath1.toAbsolutePath().normalize();
        filePath2 = filePath2.toAbsolutePath().normalize();

        if (Files.notExists(filePath1)) {
            throw new IOException("File " + filePath1.getFileName() + " not found");
        }

        if (Files.notExists(filePath2)) {
            throw new IOException("File " + filePath2.getFileName() + " not found");
        }

        var dataFirstFile = convertJsonToMap(filePath1);
        var dataSecondFile = convertJsonToMap(filePath2);

        return dataFirstFile + "\n" + dataSecondFile;

    }

    private static Map convertJsonToMap(Path json) {
        return new ObjectMapper().readValue(json,
                new TypeReference<Map<String, Object>>() {
                }
        );
    }
}
