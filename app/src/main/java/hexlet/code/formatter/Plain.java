package hexlet.code.formatter;

import java.util.List;
import java.util.Map;

public class Plain {
    public static String formatterPlain(
            Map<String, Object> dataFirstFile, Map<String, Object> dataSecondFile, String key) {

        var firstVal = dataFirstFile.get(key) == null ? "null" : dataFirstFile.get(key);
        var secondVal = dataSecondFile.get(key) == null ? "null" : dataSecondFile.get(key);
        var isKeyContains = dataFirstFile.containsKey(key) && dataSecondFile.containsKey(key);

        var result = "";

        if (dataFirstFile.containsKey(key) && !dataSecondFile.containsKey(key)) {
            result += "Property '" + key + "' was removed";
        } else if (isKeyContains && !(firstVal.equals(secondVal))) {
            result += "Property '" + key + "' was updated. From " +
                    convertValueToFormatter(firstVal) + " to " + convertValueToFormatter(secondVal);
        } else if (!dataFirstFile.containsKey(key) && dataSecondFile.containsKey(key)) {
            result += "Property '" + key + "' was added with value: " + convertValueToFormatter(secondVal);
        }

        return result;
    }

    private static String convertValueToFormatter(Object value) {
        return switch (value) {
            case String str -> "'" + value + "'";
            case Integer inter -> value.toString();
            case List<?> list -> "[complex value]";
            case Object[] arr -> "[complex value]";
            case Map<?, ?> map -> "[complex value]";
            case Boolean bool -> value.toString();
            case null -> "null";
            default -> "[complex value]";

        };
    }
}
