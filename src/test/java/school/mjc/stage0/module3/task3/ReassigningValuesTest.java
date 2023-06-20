package school.mjc.stage0.module3.task3;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.type.PrimitiveType;
import org.junit.jupiter.api.Test;
import school.mjc.test.BaseIOTest;
import school.mjc.test.JavaFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static school.mjc.parser.Asserts.assertVariablesPrinted;
import static school.mjc.parser.predicate.Dsl.declaration;
import static school.mjc.parser.predicate.Dsl.sout;

@JavaFileSource("src/main/java/school/mjc/stage0/module3/task3/ReassigningValues.java")
class ReassigningValuesTest extends BaseIOTest {

    @Test
    void mainTestPrintExpected() {
        ReassigningValues.main(null);

        assertOutput("1", "10", "100", "15", "6", "4", "1", "10", "100");
    }

    @Test
    void containsNoRawNumbersPrinting(CompilationUnit parsed) {
        assertIntExists(parsed, "first");
        assertIntExists(parsed, "second");
        assertIntExists(parsed, "third");
        assertIntExists(parsed, "linkToFirst");
        assertIntExists(parsed, "linkToSecond");
        assertIntExists(parsed, "linkToThird");

        boolean hasRawNumberPrinting = parsed.findAll(MethodCallExpr.class, sout())
                .stream()
                .flatMap(mce -> mce.getArguments().stream())
                .anyMatch(Expression::isLiteralExpr);

        assertFalse(hasRawNumberPrinting, "Please use variables, don't print numbers directly");
        assertVariablesPrinted(parsed, "first", "second", "third", "linkToFirst", "linkToSecond", "linkToThird");
    }

    private void assertIntExists(CompilationUnit parsed, String name) {
        assertTrue(isIntVariableWithNameExists(parsed, name), "Could not find variable " + name);
    }

    private boolean isIntVariableWithNameExists(CompilationUnit parsed, String name) {
        return parsed.findAll(VariableDeclarator.class,
            declaration(name).ofPrimitiveType(PrimitiveType.Primitive.INT)).size() == 1;
    }
}
