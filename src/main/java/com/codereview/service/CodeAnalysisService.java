package com.codereview.service;

import com.codereview.dto.AnalysisResponse;
import com.codereview.dto.AnalysisResponse.Issue;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CodeAnalysisService {

    public AnalysisResponse analyzeCode(String code) {
        List<Issue> criticalIssues = new ArrayList<>();
        List<Issue> warningIssues = new ArrayList<>();
        List<Issue> infoIssues = new ArrayList<>();

        if (code == null || code.trim().isEmpty()) {
            return new AnalysisResponse(criticalIssues, warningIssues, infoIssues);
        }

        String[] lines = code.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int lineNumber = i + 1;
            String trimmedLine = line.trim();

            // Skip empty lines
            if (trimmedLine.isEmpty()) {
                continue;
            }

            // Check for null pointer dereference
            if (trimmedLine.contains(".length()") && !trimmedLine.contains("if") && !trimmedLine.contains("null")) {
                criticalIssues.add(new Issue(
                        lineNumber,
                        "Potential NullPointerException: Calling .length() on object that might be null",
                        "Add null check: if (object != null) { object.length(); }"
                ));
            }

            // Check for null pointer on toString()
            if (trimmedLine.contains(".toString()") && !trimmedLine.contains("if") && !trimmedLine.contains("null")) {
                criticalIssues.add(new Issue(
                        lineNumber,
                        "Potential NullPointerException: Calling .toString() on object that might be null",
                        "Add null check or use String.valueOf() instead"
                ));
            }

            // Check for System.out.println
            if (trimmedLine.contains("System.out.println") || trimmedLine.contains("System.out.print")) {
                infoIssues.add(new Issue(
                        lineNumber,
                        "System.out.println found in code",
                        "Consider using a logging framework like SLF4J or Log4j"
                ));
            }

            // Check for printStackTrace
            if (trimmedLine.contains("printStackTrace()")) {
                warningIssues.add(new Issue(
                        lineNumber,
                        "printStackTrace() should not be used in production",
                        "Use logger.error('message', e) instead"
                ));
            }

            // Check for empty catch blocks
            if (trimmedLine.contains("catch") && i + 1 < lines.length) {
                String nextLine = lines[i + 1].trim();
                if (nextLine.equals("{}") || nextLine.equals("{ }") ||
                        (nextLine.contains("{") && nextLine.contains("}") && nextLine.length() < 5)) {
                    warningIssues.add(new Issue(
                            lineNumber,
                            "Empty catch block detected",
                            "Either handle the exception properly or log it: logger.error('Error', e);"
                    ));
                }
            }

            // Check for magic numbers
            Pattern magicNumberPattern = Pattern.compile("\\b\\d+\\b");
            java.util.regex.Matcher matcher = magicNumberPattern.matcher(trimmedLine);
            if (matcher.find() &&
                    !trimmedLine.contains("//") &&
                    !trimmedLine.contains("return") &&
                    !trimmedLine.contains("for") &&
                    !trimmedLine.contains("if") &&
                    !trimmedLine.contains("case") &&
                    !trimmedLine.contains("switch") &&
                    !trimmedLine.contains("catch") &&
                    !trimmedLine.contains("while")) {

                // Check if it's not 0, 1, or -1 (common valid numbers)
                String number = matcher.group();
                if (!number.equals("0") && !number.equals("1") && !number.equals("-1")) {
                    infoIssues.add(new Issue(
                            lineNumber,
                            "Magic number '" + number + "' detected",
                            "Extract numeric literals into named constants: private static final int CONSTANT_NAME = " + number + ";"
                    ));
                }
            }

            // Check for TODO comments
            if (trimmedLine.contains("TODO") || trimmedLine.contains("FIXME")) {
                infoIssues.add(new Issue(
                        lineNumber,
                        "TODO/FIXME comment found",
                        "Address this TODO before production deployment"
                ));
            }

            // Check for long method (simplified)
            if ((trimmedLine.contains("public") || trimmedLine.contains("private") || trimmedLine.contains("protected")) &&
                    (trimmedLine.contains("void") || trimmedLine.contains("String") || trimmedLine.contains("int") ||
                            trimmedLine.contains("boolean") || trimmedLine.contains("List")) &&
                    trimmedLine.contains("(") && trimmedLine.contains(")") &&
                    !trimmedLine.contains("//") && !trimmedLine.contains("class")) {

                int methodLength = 0;
                int braceCount = 0;
                boolean methodStarted = false;

                for (int j = i; j < lines.length && j < i + 50; j++) {
                    String methodLine = lines[j];
                    if (methodLine.contains("{")) {
                        methodStarted = true;
                        braceCount++;
                    }
                    if (methodStarted) {
                        methodLength++;
                        if (methodLine.contains("}")) {
                            braceCount--;
                            if (braceCount == 0) {
                                break;
                            }
                        }
                    }
                }

                if (methodLength > 30) {
                    warningIssues.add(new Issue(
                            lineNumber,
                            "Method is too long (" + methodLength + " lines)",
                            "Break down the method into smaller, more focused methods (Single Responsibility Principle)"
                    ));
                }
            }
        }

        // Check for missing Javadoc on public class
        if (code.contains("public class") && !code.contains("/**") && !code.contains("/*")) {
            warningIssues.add(new Issue(
                    1,
                    "Missing Javadoc for public class",
                    "Add Javadoc comment explaining the purpose of the class"
            ));
        }

        return new AnalysisResponse(criticalIssues, warningIssues, infoIssues);
    }
}