package hexlet.code;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    private static StringWriter buffer;
    private static CommandLine commandLine;


    private static Path getFixturePath(String fixtureName) {
        return Paths.get("src", "test", "resources", "fixtures", fixtureName)
                .toAbsolutePath()
                .normalize();
    }

    private static String readFixture(String fixtureName) throws Exception {
        var path = getFixturePath(fixtureName);
        return Files.readString(path).trim();
    }

    @BeforeEach
    void beforeEach() {
        buffer = new StringWriter();
        commandLine = new CommandLine(new App());
    }

    @SneakyThrows
    @Test
    public void testAppHelp() {
        var args = new String[]{"-h"};

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultHelp.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppVersion() {
        var args = new String[]{"-V"};

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultVersion.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppDiffer() {
        var args = new String[]{" ",readFixture("file1.json"), " ", readFixture("file2.json")};

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultDiffer.txt").trim();

        assertEquals(expected, actual);
    }
}
