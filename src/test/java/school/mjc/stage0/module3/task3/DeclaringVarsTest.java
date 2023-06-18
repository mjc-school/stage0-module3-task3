package school.mjc.stage0.module3.task3;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import org.junit.jupiter.api.Test;
import school.mjc.test.BaseIOTest;
import school.mjc.test.JavaFileSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static school.mjc.parser.Asserts.assertVariablesPrinted;

@JavaFileSource("src/main/java/school/mjc/stage0/module3/task3/DeclaringVars.java")
class DeclaringVarsTest extends BaseIOTest {

    @Test
    void mainWritesToConsoleExpected() {
        DeclaringVars.main(null);

        assertOutput("10");
    }

    @Test
    void containsInlineDeclarationAndInitialization(CompilationUnit parsed) {
        boolean declaredVariablesExist = parsed.findAll(VariableDeclarationExpr.class, vde ->
                vde.getVariables().stream()
                        .map(VariableDeclarator::getName)
                        .map(SimpleName::getIdentifier)
                        .toList()
                        .containsAll(List.of("a", "b", "c"))).size() == 1;
        boolean initializedVariablesExist = parsed.findAll(AssignExpr.class, ae -> {
            if (!targetIsVarWithName(ae, "a") || !ae.getValue().isAssignExpr()) {
                return false;
            }
            AssignExpr inner = ae.getValue().asAssignExpr();
            if (!targetIsVarWithName(inner, "b") || !inner.getValue().isAssignExpr()) {
                return false;
            }
            AssignExpr inner2 = inner.getValue().asAssignExpr();
            return targetIsVarWithName(inner2, "c")
                    && inner2.getValue().isIntegerLiteralExpr()
                    && inner2.getValue().asIntegerLiteralExpr().getValue().equals("10");
        }).size() == 1;

        assertTrue(declaredVariablesExist, "Variables not declared as expected");
        assertTrue(initializedVariablesExist, "Variables not initialized as expected");
        assertVariablesPrinted(parsed, "c");
    }

    private boolean targetIsVarWithName(AssignExpr assignExpr, String name) {
        return assignExpr.getTarget().isNameExpr()
                && assignExpr.getTarget().asNameExpr().getName().getIdentifier().equals(name);
    }
}
