package hexlet.code.formatters;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Data
public class JSON implements Formatter {

    @Override
    public String getFormatter(
            Map<String, Object> dataFirstFile, Map<String, Object> dataSecondFile, List<String> key) {

        var firstVal = dataFirstFile.get(key) == null ? "null" : dataFirstFile.get(key);
        var secondVal = dataSecondFile.get(key) == null ? "null" : dataSecondFile.get(key);
        var isKeyContains = dataFirstFile.containsKey(key) && dataSecondFile.containsKey(key);

        List<Map<String, Object>> result = new java.util.ArrayList<>(List.of());

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

        return result.toString();
    }



    @Override
    public String wrapIfNeeded(String data) {
        return new JsonMapper().writeValueAsString(data);
    }
}
