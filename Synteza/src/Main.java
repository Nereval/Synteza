
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;

import javax.tools.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
/*
@Author Piotr Czyżak
Zadanie 23 - Synteza
 */

public class Main {
    public static void main(String[] args) throws IOException {
        final String fileName = "src\\Class.java";
        final String alteredFileName = "src\\ClassAltered.java";
        CompilationUnit cu;
        try(FileInputStream in = new FileInputStream(fileName)){
            cu = JavaParser.parse(in);
        }

        cu.getChildNodesByType(ReturnStmt.class)
                .forEach(Main::getReturnStatement);

        cu.getClassByName("Class").get().setName("ClassAltered");

        try(FileWriter output = new FileWriter(new File(alteredFileName), false)) {
            output.write(cu.toString());
        }

        File[] files = {new File(alteredFileName)};
        String[] options = { "-d", "out//production//Synteza" };

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits =
                    fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files));
            compiler.getTask(
                    null,
                    fileManager,
                    diagnostics,
                    Arrays.asList(options),
                    null,
                    compilationUnits).call();

            diagnostics.getDiagnostics().forEach(d -> System.out.println(d.getMessage(null)));
        }

    }

    private static void getReturnStatement(ReturnStmt returnStmt) {
        BlockStmt parentBlock = (BlockStmt) returnStmt.getParentNode().get();
        MethodDeclaration method = returnStmt.getAncestorOfType(MethodDeclaration.class).get();
        MethodCallExpr call = new MethodCallExpr(null, "log");
        call.addArgument(new StringLiteralExpr("Nazwa metody: " + method.getNameAsString() + ", Return: " + returnStmt.getExpression().get().toString()));
        int indexInParent = parentBlock.getChildNodes().indexOf(returnStmt);
        parentBlock.addStatement(indexInParent, call);

    }
}
