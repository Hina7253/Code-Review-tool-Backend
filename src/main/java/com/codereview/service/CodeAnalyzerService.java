package com.codereview.service;

import com.codereview.model.AnalysisResponse;
import com.codereview.model.Issue;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeAnalyzerService {

    private final JavaParser javaParser = new JavaParser();

    public AnalysisResponse analyzeCode(String code) {
        AnalysisResponse response = new AnalysisResponse();
        List<Issue> critical = new ArrayList<>();
        List<Issue> warning = new ArrayList<>();
        List<Issue> info = new ArrayList<>();

        try {
            CompilationUnit cu = javaParser.parse(code).getResult().orElseThrow();

            // Check for null pointer risks
            cu.findAll(NullLiteralExpr.class).forEach(nullExpr -> {
                int line = nullExpr.getRange().map(r -> r.begin.line).orElse(0);
                critical.add(new Issue(
                        "critical",
                        line,
                        "Potential NullPointerException: null literal found",
                        "Avoid using null directly. Consider Optional or null checks."
                ));
            });

            // Check for empty catch blocks
            cu.findAll(CatchClause.class).forEach(catchClause -> {
                if (catchClause.getBody().getStatements().isEmpty()) {
                    int line = catchClause.getRange().map(r -> r.begin.line).orElse(0);
                    critical.add(new Issue(
                            "critical",
                            line,
                            "Empty catch block - exceptions are being silently ignored",
                            "Always handle exceptions properly or at least log them."
                    ));
                }
            });

            // Check for System.out.println
            cu.findAll(MethodCallExpr.class).forEach(methodCall -> {
                if (methodCall.getName().asString().equals("println") &&
                        methodCall.getScope().isPresent() &&
                        methodCall.getScope().get().toString().contains("System.out")) {
                    int line = methodCall.getRange().map(r -> r.begin.line).orElse(0);
                    info.add(new Issue(
                            "info",
                            line,
                            "Using System.out.println for logging",
                            "Consider using a proper logging framework like SLF4J or Log4j."
                    ));
                }
            });

            // Check class naming conventions
            cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDecl -> {
                String className = classDecl.getNameAsString();
                if (!Character.isUpperCase(className.charAt(0))) {
                    int line = classDecl.getRange().map(r -> r.begin.line).orElse(0);
                    warning.add(new Issue(
                            "warning",
                            line,
                            "Class name should start with uppercase: " + className,
                            "Follow Java naming conventions (PascalCase for classes)."
                    ));
                }
            });

            // Check method complexity
            cu.findAll(MethodDeclaration.class).forEach(method -> {
                int statementCount = method.getBody()
                        .map(body -> body.getStatements().size())
                        .orElse(0);

                if (statementCount > 20) {
                    int line = method.getRange().map(r -> r.begin.line).orElse(0);
                    warning.add(new Issue(
                            "warning",
                            line,
                            "Method " + method.getNameAsString() + " is too complex (" + statementCount + " statements)",
                            "Consider breaking down into smaller methods."
                    ));
                }

                // Check for missing JavaDoc
                if (method.isPublic() && !method.getJavadocComment().isPresent()) {
                    int line = method.getRange().map(r -> r.begin.line).orElse(0);
                    info.add(new Issue(
                            "info",
                            line,
                            "Public method missing JavaDoc: " + method.getNameAsString(),
                            "Add JavaDoc comments for public APIs."
                    ));
                }
            });

            // If no issues found
            if (critical.isEmpty() && warning.isEmpty() && info.isEmpty()) {
                info.add(new Issue(
                        "info",
                        0,
                        "No issues detected!",
                        "Your code looks clean. Great job!"
                ));
            }

        } catch (Exception e) {
            critical.add(new Issue(
                    "critical",
                    0,
                    "Failed to parse code: " + e.getMessage(),
                    "Make sure your code is valid Java syntax."
            ));
        }

        response.setCritical(critical);
        response.setWarning(warning);
        response.setInfo(info);
        response.setTotalIssues(critical.size() + warning.size() + info.size());

        return response;
    }
}