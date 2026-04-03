// CodeAnalysisService.java
package com.codeanalyzer.service;

import com.codeanalyzer.dto.AnalysisResponse;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CodeAnalysisService {

    public AnalysisResponse analyzeCode(String code) {
        List<AnalysisResponse.Issue> criticalIssues = new ArrayList<>();
        List<AnalysisResponse.Issue> warningIssues = new ArrayList<>();
        List<AnalysisResponse.Issue> infoIssues = new ArrayList<>();

        String[] lines = code.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int lineNumber = i + 1;

            // Check for null pointer dereference
            if (line.contains(".length()") && !line.contains("if") && !line.contains("null")) {
                criticalIssues.add(new AnalysisResponse.Issue(
                        lineNumber,
                        "Potential NullPointerException: Calling .length() on object that might be null",
                        "Add null check before accessing the object: if (object != null) { object.length(); }"
                ));
            }

            // Check for System.out.println
            if (line.contains("System.out.println")) {
                infoIssues.add(new AnalysisResponse.Issue(
                        lineNumber,
                        "System.out.println found in code",
                        "Consider using a logging framework like SLF4J or Log4j for better control"
                ));
            }

            // Check for empty catch blocks
            if (line.contains("catch") && lines.length > i + 1 && lines[i + 1].contains("{}")) {
                warningIssues.add(new AnalysisResponse.Issue(
                        lineNumber,
                        "Empty catch block detected",
                        "Either handle the exception properly or log it: logger.error('Error occurred', e);"
                ));
            }

            // Check for magic numbers
            Pattern magicNumberPattern = Pattern.compile("\\b\\d+\\b");
            if (magicNumberPattern.matcher(line).find() &&
                    !line.contains("//") && !line.contains("return") &&
                    !line.contains("for") && !line.contains("if") &&
                    !line.contains("case") && !line.contains("switch")) {
                infoIssues.add(new AnalysisResponse.Issue(
                        lineNumber,
                        "Magic number detected",
                        "Extract numeric literals into named constants: private static final int CONSTANT_NAME = value;"
                ));
            }

            // Check for long methods
            if (line.contains("public") || line.contains("private") || line.contains("protected")) {
                if (line.contains("void") || line.contains("String") || line.contains("int") || line.contains("boolean")) {
                    int methodLength = 0;
                    for (int j = i; j < lines.length && j < i + 50; j++) {
                        if (lines[j].contains("}")) break;
                        methodLength++;
                    }
                    if (methodLength > 30) {
                        warningIssues.add(new AnalysisResponse.Issue(
                                lineNumber,
                                "Method is too long (" + methodLength + " lines)",
                                "Break down the method into smaller, more focused methods"
                        ));
                    }
                }
            }

            // Check for commented code
            if (line.contains("//") && (line.contains("System.out") || line.contains("if") || line.contains("for"))) {
                infoIssues.add(new AnalysisResponse.Issue(
                        lineNumber,
                        "Commented code detected",
                        "Remove commented code or add explanation why it's kept"
                ));
            }
        }

        // Check for missing Javadoc on public classes
        if (!code.contains("/**") && code.contains("public class")) {
            warningIssues.add(new AnalysisResponse.Issue(
                    1,
                    "Missing Javadoc for public class",
                    "Add Javadoc comment explaining the purpose of the class"
            ));
        }

        return new AnalysisResponse(criticalIssues, warningIssues, infoIssues);
    }
}