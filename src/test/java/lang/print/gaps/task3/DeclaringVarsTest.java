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

class DeclaringVarsTest extends BaseIOTest {

    @Test
    void mainWritesToConsoleExpected() {
        DeclaringVars.main(null);

        assertEquals("10\n", updateLineSpliterators(outContent.toString()));
    }

    @Test
    void containsInlineDeclarationAndInitialization() throws IOException {
        Path path = Paths.get("src/main/java/lang/print/gaps/task3/DeclaringVars.java");
        List<String> strings = Files.readAllLines(path);
        List<String> declarationResult = strings.stream()
                .filter(line ->
                        line.contains("int a,b,c;") ||
                                line.contains("int a, b, c;"))
                .collect(Collectors.toList());

        List<String> initialisationResult = strings.stream()
                .filter(line ->
                        line.contains("a = b = c = 10;") ||
                                line.contains("a=b=c=10;"))
                .collect(Collectors.toList());

        assertEquals(1, declarationResult.size());
        assertEquals(1, initialisationResult.size());
    }
}