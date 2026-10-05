package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public interface Format {
    String getFormat(
            Map<String, Object> dataFirstFile,
            Map<String, Object> dataSecondFile,
            List<String> keys);

    String wrapIfNeeded(String data);
}
