package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

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
        var args = new String[] {"-h"};

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultHelp.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppVersion() {
        var args = new String[] {"-V"};

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultVersion.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppDifferJSON() {
        var args =
                new String[] {
                    getFixturePath("file1.json").toString(), getFixturePath("file2.json").toString()
                };

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultDiffer.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppDifferYml() {
        var args =
                new String[] {
                    getFixturePath("file1.yml").toString(), getFixturePath("file2.yml").toString()
                };

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultDiffer.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppDifferYaml() {
        var args =
                new String[] {
                    getFixturePath("file1.yaml").toString(), getFixturePath("file2.yaml").toString()
                };

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultDiffer.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppDifferWithoutFiles() {
        var args = new String[] {};

        commandLine.setErr(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultDifferWithoutFiles.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppDifferFileNotFound() {
        var args =
                new String[] {
                    getFixturePath("fileTest1.json").toString(),
                    getFixturePath("fileTest2.json").toString()
                };

        commandLine.setErr(new PrintWriter(buffer));
        int exitCode = commandLine.execute(args);

        Assertions.assertNotEquals(0, exitCode);
        assertTrue(buffer.toString().trim().contains("NoSuchFileException"));
    }

    @SneakyThrows
    @Test
    public void testAppHardDifferJSON() {
        var args =
                new String[] {
                    getFixturePath("hardFile1.json").toString(),
                    getFixturePath("hardFile2.json").toString()
                };

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultHardDiffer.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppHardDifferYML() {
        var args =
                new String[] {
                    getFixturePath("hardFile1.yml").toString(),
                    getFixturePath("hardFile2.yml").toString()
                };

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultHardDiffer.txt").trim();

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    public void testAppHardDifferPlainFormate() {
        var args =
                new String[] {
                    "-f",
                    "plain",
                    getFixturePath("hardFile1.json").toString(),
                    getFixturePath("hardFile2.json").toString()
                };

        commandLine.setOut(new PrintWriter(buffer));
        commandLine.execute(args);

        var actual = buffer.toString().trim();
        var expected = readFixture("resultPlainFormate.txt").trim();

        assertEquals(expected, actual);
    }
}
