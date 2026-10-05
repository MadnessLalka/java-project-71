package hexlet.code;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.Data;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.yaml.YAMLMapper;

@Data
public class Parser {
    private final Map<String, Object> originalFileMap;
    private final Map<String, Object> targetFileMap;
    private final List<String> sortedListKey;

    Parser(Path filePath1, Path filePath2) throws NoSuchFileException {
        originalFileMap = convertObjectToMap(validateAndNormalize(filePath1));
        targetFileMap = convertObjectToMap(validateAndNormalize(filePath2));
        sortedListKey = getListSortedKey();
    }

    private Map<String, Object> convertObjectToMap(Path file) {
        return getObjectMapper(file).readValue(file, new TypeReference<>() {});
    }

    private ObjectMapper getObjectMapper(Path path) {
        String format = path.getFileName().toString().toLowerCase().split("\\.")[1];

        switch (format) {
            case "yaml", "yml" -> {
                return new YAMLMapper();
            }
            case "json" -> {
                return new JsonMapper();
            }
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    private Path validateAndNormalize(Path path) throws NoSuchFileException {
        Path normalizedPath = path.toAbsolutePath().normalize();

        if (Files.notExists(normalizedPath)) {
            throw new NoSuchFileException("File " + normalizedPath.getFileName() + " not found");
        }

        return normalizedPath;
    }

    private List<String> getListSortedKey() {
        return Stream.concat(originalFileMap.keySet().stream(), targetFileMap.keySet().stream())
                .distinct()
                .sorted()
                .toList();
    }
}
