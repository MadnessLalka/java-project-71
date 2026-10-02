package hexlet.code.formatters;

import java.util.Map;

public interface Formatter {
    String getFormatter(
            Map<String, Object> dataFirstFile, Map<String, Object> dataSecondFile, String key);

    String wrapIfNeeded(String data);
}
