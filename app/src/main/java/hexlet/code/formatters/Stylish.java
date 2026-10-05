package hexlet.code.formatters;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
public class Stylish implements Format {

    @Override
    public String getFormat(
            Map<String, Object> dataFirstFile, Map<String, Object> dataSecondFile, List<String> keys) {

        var data = keys.stream()
                .map(key -> {
                    var firstVal = dataFirstFile.get(key) == null ? "null" : dataFirstFile.get(key);
                    var secondVal = dataSecondFile.get(key) == null ? "null" : dataSecondFile.get(key);
                    var isKeyContains = dataFirstFile.containsKey(key) && dataSecondFile.containsKey(key);

                    var result = "";

                    if (dataFirstFile.containsKey(key) && !dataSecondFile.containsKey(key)) {
                        result += "  - " + key + ": " + firstVal;
                    } else if (isKeyContains && !(firstVal.equals(secondVal))) {
                        result += "  - " + key + ": " + firstVal + "\n  + " + key + ": " + secondVal;
                    } else if (isKeyContains && firstVal.equals(secondVal)) {
                        result += "    " + key + ": " + firstVal;
                    } else if (!dataFirstFile.containsKey(key) && dataSecondFile.containsKey(key)) {
                        result += "  + " + key + ": " + secondVal;
                    }

                    return result;
                }).collect(Collectors.joining("\n"));

        return wrapIfNeeded(data);
    }

    @Override
    public String wrapIfNeeded(String data) {
        return "{\n" + data + "\n}";
    }
}
