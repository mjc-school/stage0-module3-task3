package school.mjc.stage0.module3.task3;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.PrimitiveType;
import org.junit.jupiter.api.Test;
import school.mjc.test.BaseIOTest;
import school.mjc.test.JavaFileSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static school.mjc.parser.Asserts.assertVariablesPrinted;
import static school.mjc.parser.predicate.Dsl.declaration;

@JavaFileSource("src/main/java/school/mjc/stage0/module3/task3/BasicVariablesInitialization.java")
class BasicVariablesInitializationTest extends BaseIOTest {

    @Test
    void mainPrintsValues() {
        BasicVariablesInitialization.main(null);

        assertOutput("1", "10", "100");
    }

    @Test
    void containsInlineDeclarationAndInitialization(CompilationUnit parsed) {
        boolean firstExists = intVarExists(parsed, "first");
        boolean secondExists = intVarExists(parsed, "second");
        boolean thirdExists = intVarExists(parsed, "third");

        assertTrue(firstExists && secondExists && thirdExists,
                "Not all required variables exist");

        assertVariablesPrinted(parsed, "first", "second", "third");
    }

    private boolean intVarExists(CompilationUnit parsed, String name) {
        return parsed.findAll(VariableDeclarator.class,
                declaration(name).ofPrimitiveType(PrimitiveType.Primitive.INT)).size() == 1;
    }
}
