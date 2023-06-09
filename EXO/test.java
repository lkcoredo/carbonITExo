import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TreasureTrackerTest {
    private static final String INPUT_FILE_PATH = "input.txt";
    private static final String EXPECTED_OUTPUT_FILE_PATH = "expected_output.txt";

    @Test
    public void testTreasureTrackerSimulation() throws IOException {
        // Load input and expected output from files
        String input = loadFileContent(INPUT_FILE_PATH);
        String expectedOutput = loadFileContent(EXPECTED_OUTPUT_FILE_PATH);

        // Redirect System.out to capture program output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run the program
        TreasureTracker.main(new String[]{});

        // Restore System.out
        System.setOut(System.out);

        // Get the program output
        String actualOutput = outputStream.toString(StandardCharsets.UTF_8);

        // Assert the output matches the expected output
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    private String loadFileContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Test
    public void testTreasureTrackerSimulation() throws IOException {
        String input = loadFileContent(INPUT_FILE_PATH);
        String expectedOutput = loadFileContent(EXPECTED_OUTPUT_FILE_PATH);
        String actualOutput = runTreasureTrackerSimulation(input);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testEmptyMap() throws IOException {
        String input = "C - 0 - 0\n";
        String expectedOutput = "C - 0 - 0\n";
        String actualOutput = runTreasureTrackerSimulation(input);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testNoTreasures() throws IOException {
        String input = "C - 3 - 3\nA - Indiana - 0 - 0 - E - AAA\n";
        String expectedOutput = "C - 3 - 3\nA - Indiana - 3 - 0 - E - 0\n";
        String actualOutput = runTreasureTrackerSimulation(input);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testMultipleAdventurers() throws IOException {
        String input = "C - 4 - 4\nM - 1 - 1\nM - 2 - 2\nT - 1 - 2 - 3\n"
                + "A - Indiana - 1 - 1 - S - AADADA\n"
                + "A - Lara - 2 - 2 - E - DADAA\n";
        String expectedOutput = "C - 4 - 4\nM - 1 - 1\nM - 2 - 2\nT - 1 - 2 - 2\n"
                + "A - Indiana - 0 - 3 - S - 2\n"
                + "A - Lara - 4 - 2 - S - 1\n";
        String actualOutput = runTreasureTrackerSimulation(input);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    private String runTreasureTrackerSimulation(String input) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TreasureTracker.main(new String[]{});
        System.setOut(System.out);
        System.setIn(System.in);
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    private String loadFileContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
