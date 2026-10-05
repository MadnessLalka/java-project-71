package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Plain implements Format {

    @Override
    public String getFormat(
            Map<String, Object> dataFirstFile,
            Map<String, Object> dataSecondFile,
            List<String> keys) {

        var data =
                keys.stream()
                        .map(
                                key -> {
                                    var firstVal =
                                            dataFirstFile.get(key) == null
                                                    ? "null"
                                                    : dataFirstFile.get(key);
                                    var secondVal =
                                            dataSecondFile.get(key) == null
                                                    ? "null"
                                                    : dataSecondFile.get(key);

                                    var isKeyContains =
                                            dataFirstFile.containsKey(key)
                                                    && dataSecondFile.containsKey(key);

                                    var result = "";

                                    if (dataFirstFile.containsKey(key)
                                            && !dataSecondFile.containsKey(key)) {
                                        result += "Property '" + key + "' was removed";
                                    } else if (isKeyContains && !(firstVal.equals(secondVal))) {
                                        result +=
                                                "Property '"
                                                        + key
                                                        + "' was updated. From "
                                                        + convertValueToFormatter(firstVal)
                                                        + " to "
                                                        + convertValueToFormatter(secondVal);
                                    } else if (!dataFirstFile.containsKey(key)
                                            && dataSecondFile.containsKey(key)) {
                                        result +=
                                                "Property '"
                                                        + key
                                                        + "' was added with value: "
                                                        + convertValueToFormatter(secondVal);
                                    }

                                    return result;
                                })
                        .filter(line -> !line.isBlank())
                        .collect(Collectors.joining("\n"));

        return wrapIfNeeded(data);
    }

    @Override
    public String wrapIfNeeded(String data) {
        return data;
    }

    private String convertValueToFormatter(Object value) {
        return switch (value) {
            case null -> "null";
            case String str -> value.equals("null") ? value.toString() : "'" + value + "'";
            case Integer inter -> value.toString();
            case List<?> list -> "[complex value]";
            case Object[] arr -> "[complex value]";
            case Map<?, ?> map -> "[complex value]";
            case Boolean bool -> value.toString();
            default -> "[complex value]";
        };
    }
}
