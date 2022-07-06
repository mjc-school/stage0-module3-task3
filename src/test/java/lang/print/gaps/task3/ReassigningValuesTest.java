package lang.print.gaps.task3;

import base.BaseIOTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReassigningValuesTest extends BaseIOTest {

    private static final String RAW_NUMBER_SOUT = "^.+(System\\.out\\.println\\D+\\d+\\D+);";

    @Test
    void mainTestPrintExpected() {
        ReassigningValues.main(null);

        assertEquals("1\n10\n100\n15\n6\n4\n1\n10\n100\n", updateLineSpliterators(outContent.toString()));
    }


    @Test
    void containsNoRawNumbersPrinting() throws IOException {
        Path path = Paths.get("src/main/java/lang/print/gaps/task3/ReassigningValues.java");
        List<String> strings = Files.readAllLines(path);
        List<String> rowNumbersOutput = strings.stream()
                .filter(line ->
                        line.matches(RAW_NUMBER_SOUT))
                .collect(Collectors.toList());
        assertEquals(0, rowNumbersOutput.size());
    }
}