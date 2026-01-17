package com.codereview.service;

import com.codereview.model.CodeMetrics;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import org.springframework.stereotype.Service;

@Service
public class CodeMetricsService {

    private final JavaParser javaParser = new JavaParser();

    public CodeMetrics calculateMetrics(String code) {
        CodeMetrics metrics = new CodeMetrics();

        try {
            // Parse the code
            CompilationUnit cu = javaParser.parse(code).getResult().orElseThrow();

            // Calculate line counts
            calculateLineMetrics(code, metrics);

            // Count classes
            int classCount = cu.findAll(ClassOrInterfaceDeclaration.class).size();
            metrics.setClassCount(classCount);

            // Count methods
            var methods = cu.findAll(MethodDeclaration.class);
            metrics.setMethodCount(methods.size());

            // Count public vs private methods
            int publicMethods = 0;
            int privateMethods = 0;
            for (MethodDeclaration method : methods) {
                if (method.isPublic()) {
                    publicMethods++;
                } else if (method.isPrivate()) {
                    privateMethods++;
                }
            }
            metrics.setPublicMethodCount(publicMethods);
            metrics.setPrivateMethodCount(privateMethods);

            // Calculate comment percentage
            double commentPercentage = 0;
            if (metrics.getTotalLines() > 0) {
                commentPercentage = (metrics.getCommentLines() * 100.0) / metrics.getTotalLines();
            }
            metrics.setCommentPercentage((int) (Math.round(commentPercentage * 100.0) / 100.0));

            // Calculate quality score
            int qualityScore = calculateQualityScore(metrics, cu);
            metrics.setQualityScore(qualityScore);

            // Determine quality grade
            String grade = getQualityGrade(qualityScore);
            metrics.setQualityGrade(Integer.parseInt(grade));

        } catch (Exception e) {
            // If parsing fails, return empty metrics
            System.err.println("Error calculating metrics: " + e.getMessage());
        }

        return metrics;
    }

    private void calculateLineMetrics(String code, CodeMetrics metrics) {
        String[] lines = code.split("\n");
        int totalLines = lines.length;
        int blankLines = 0;
        int commentLines = 0;

        boolean inBlockComment = false;

        for (String line : lines) {
            String trimmed = line.trim();

            // Check for blank lines
            if (trimmed.isEmpty()) {
                blankLines++;
                continue;
            }

            // Check for block comments
            if (trimmed.startsWith("/*")) {
                inBlockComment = true;
                commentLines++;
                if (trimmed.contains("*/")) {
                    inBlockComment = false;
                }
                continue;
            }

            if (inBlockComment) {
                commentLines++;
                if (trimmed.contains("*/")) {
                    inBlockComment = false;
                }
                continue;
            }

            // Check for single line comments
            if (trimmed.startsWith("//")) {
                commentLines++;
                continue;
            }
        }

        int codeLines = totalLines - blankLines - commentLines;

        metrics.setTotalLines(totalLines);
        metrics.setCodeLines(codeLines);
        metrics.setBlankLines(blankLines);
        metrics.setCommentLines(commentLines);
    }

    private int calculateQualityScore(CodeMetrics metrics, CompilationUnit cu) {
        int score = 100; // Start with perfect score

        // Deduct points for poor comment coverage
        if (metrics.getCommentPercentage() < 10) {
            score -= 20; // Very low comments
        } else if (metrics.getCommentPercentage() < 20) {
            score -= 10; // Low comments
        }

        // Deduct points for too many methods (complexity)
        if (metrics.getMethodCount() > 20) {
            score -= 15;
        } else if (metrics.getMethodCount() > 10) {
            score -= 5;
        }

        // Deduct points for no private methods (encapsulation)
        if (metrics.getPrivateMethodCount() == 0 && metrics.getMethodCount() > 0) {
            score -= 10;
        }

        // Bonus for good comment percentage
        if (metrics.getCommentPercentage() >= 25) {
            score += 5;
        }

        // Check for JavaDoc comments
        int javadocCount = 0;
        for (Comment comment : cu.getAllComments()) {
            if (comment.isJavadocComment()) {
                javadocCount++;
            }
        }
        if (javadocCount > 0) {
            score += 5; // Bonus for documentation
        }

        // Ensure score stays within 0-100
        if (score > 100) score = 100;
        if (score < 0) score = 0;

        return score;
    }

    private String getQualityGrade(int score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
}