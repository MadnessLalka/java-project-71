package hexlet.code;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppTest {

    private static Path getFixturePath(String fixtureName) {
        return Paths.get("src", "test", "resources", "fixtures", fixtureName)
                .toAbsolutePath()
                .normalize();
    }

    @Test
    public void testApp() {
        CommandLine commandLine = new CommandLine(new App());


    }
}
