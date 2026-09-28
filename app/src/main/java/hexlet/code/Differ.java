package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NoArgsConstructor;

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

        var dataFirstFile = Files.readString(filePath1);
        var dataSecondFile = Files.readString(filePath2);

        System.out.println("Data first file: " + dataFirstFile);
        System.out.println("Data second file: " + dataSecondFile);

        return dataFirstFile + "\n" + dataSecondFile;

    }

    private static Map convertJsonToMap(String stringJson){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(stringJson, new TypeReference<Map<String, Object>>(){});
        return

    }

}
