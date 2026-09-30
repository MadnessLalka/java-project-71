package hexlet.code;

import java.nio.file.Path;
import lombok.NoArgsConstructor;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.yaml.YAMLMapper;

@NoArgsConstructor
public class Parser {
    public static ObjectMapper getObjectMapper(Path path) {
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
}
