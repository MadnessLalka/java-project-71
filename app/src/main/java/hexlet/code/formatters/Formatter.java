package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Formatter {
    private final Map<String, Object> originalFileMap;
    private final Map<String, Object> targetFileMap;
    private final List<String> sortedListKey;
    private final String format;

    public String getFormatedDiff() {

        var formatStyle = getFormatObject(format);
        return formatStyle.getFormat(originalFileMap, targetFileMap, sortedListKey);
    }

    private Format getFormatObject(String format) {
        return switch (format) {
            case "plain" -> new Plain();
            case "stylish" -> new Stylish();
            case "json" -> new JSON();
            default -> throw new IllegalStateException("Unexpected value: " + format);
        };
    }
}
