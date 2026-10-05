package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

public class JSON implements Format {
    private final JsonMapper jsonMapper = new JsonMapper();

    @Override
    public String getFormat(
            Map<String, Object> dataFirstFile,
            Map<String, Object> dataSecondFile,
            List<String> keys) {

        var data =
                keys.stream()
                        .map(
                                key -> {
                                    var firstVal = Optional.ofNullable(dataFirstFile.get(key));
                                    var secondVal = Optional.ofNullable(dataSecondFile.get(key));
                                    var isKeyContains =
                                            dataFirstFile.containsKey(key)
                                                    && dataSecondFile.containsKey(key);

                                    if (isKeyContains && !(firstVal.equals(secondVal))) {
                                        return Map.of(
                                                "key",
                                                key,
                                                "type",
                                                "changed",
                                                "value",
                                                Map.of("old", firstVal, "new", secondVal));
                                    } else if (isKeyContains && firstVal.equals(secondVal)) {
                                        return Map.of(
                                                "key", key, "type", "unchanged", "value", firstVal);
                                    } else if (firstVal.isEmpty()
                                            && dataSecondFile.containsKey(key)) {
                                        return Map.of(
                                                "key", key, "type", "added", "value", secondVal);
                                    } else {
                                        return Map.of(
                                                "key", key, "type", "removed", "value", firstVal);
                                    }
                                })
                        .toList();

        return jsonMapper
                .writerWithDefaultPrettyPrinter()
                .with(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                .writeValueAsString(data);
    }
}
