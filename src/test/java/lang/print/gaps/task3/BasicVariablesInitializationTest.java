package lang.print.gaps.task3;

import base.BaseIOTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BasicVariablesInitializationTest extends BaseIOTest {

    @Test
    void mainPrintsValues() {
        BasicVariablesInitialization.main(null);

        assertEquals("1\n10\n100\n", updateLineSpliterators(outContent.toString()));
    }

    @Test
    void containsInlineDeclarationAndInitialization() throws IOException {
        Path path = Paths.get("src/main/java/lang/print/gaps/task3/BasicVariablesInitialization.java");
        List<String> strings = Files.readAllLines(path);
        List<String> declarationResult = strings.stream()
                .filter(line ->
                        line.contains("System.out.println") ||
                                line.contains("int first") ||
                                line.contains("int second") ||
                                line.contains("int third"))
                .collect(Collectors.toList());

        assertEquals(6, declarationResult.size());
    }
}