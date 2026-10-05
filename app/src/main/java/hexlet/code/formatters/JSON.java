package hexlet.code.formatters;

import lombok.NoArgsConstructor;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class JSON implements Format {

    @Override
    public String getFormat(
            Map<String, Object> dataFirstFile, Map<String, Object> dataSecondFile, List<String> keys) {

        List<Map<String, Object>> data = keys.stream()
                .map(key -> {
                    var firstVal = dataFirstFile.get(key) == null ? "null" : dataFirstFile.get(key);
                    var secondVal = dataSecondFile.get(key) == null ? "null" : dataSecondFile.get(key);
                    var isKeyContains = dataFirstFile.containsKey(key) && dataSecondFile.containsKey(key);

                    var result = new java.util.ArrayList<>(List.of());

                    if (dataFirstFile.containsKey(key) && !dataSecondFile.containsKey(key)) {
                        result.add(Map.of("key", key, "type", "remove", "value", firstVal));
                    } else if (isKeyContains && !(firstVal.equals(secondVal))) {
                        result.add(
                                Map.of(
                                        "key",
                                        key,
                                        "type",
                                        "changed",
                                        "value",
                                        Map.of("old", firstVal, "new", secondVal)));
                    } else if (isKeyContains && firstVal.equals(secondVal)) {
                        result.add(Map.of("key", key, "type", "unchanged", "value", firstVal));
                    } else if (!dataFirstFile.containsKey(key) && dataSecondFile.containsKey(key)) {
                        result.add(Map.of("key", key, "type", "added", "value", secondVal));
                    }

                    return result;
                })=


        return new JsonMapper(). writeValueAsString(data);
    }


    @Override
    public String wrapIfNeeded(String data) {
        return data;
    }
}
