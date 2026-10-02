package hexlet.code.formatter;

import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Stylish implements Formatter {

    @Override
    public String getFormatter(
            Map<String, Object> dataFirstFile, Map<String, Object> dataSecondFile, String key) {

        var firstVal = dataFirstFile.get(key) == null ? "null" : dataFirstFile.get(key);
        var secondVal = dataSecondFile.get(key) == null ? "null" : dataSecondFile.get(key);
        var isKeyContains = dataFirstFile.containsKey(key) && dataSecondFile.containsKey(key);

        var result = "";

        if (dataFirstFile.containsKey(key) && !dataSecondFile.containsKey(key)) {
            result += "  - " + key + ": " + firstVal;
        } else if (isKeyContains && !(firstVal.equals(secondVal))) {
            result += "  - " + key + ": " + firstVal + "\n" + "  + " + key + ": " + secondVal;
        } else if (isKeyContains && firstVal.equals(secondVal)) {
            result += "    " + key + ": " + firstVal;
        } else if (!dataFirstFile.containsKey(key) && dataSecondFile.containsKey(key)) {
            result += "  + " + key + ": " + secondVal;
        }

        return result;
    }

    @Override
    public String wrapIfNeeded(String data) {
        return "{\n" + data + "\n}";
    }
}
