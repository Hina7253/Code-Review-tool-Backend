package com.codereview.service;

import com.codereview.dto.MetricsResponse;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    public MetricsResponse calculateMetrics(String code) {
        MetricsResponse metrics = new MetricsResponse();

        if (code == null || code.trim().isEmpty()) {
            return metrics;
        }

        String[] lines = code.split("\n");
        int totalLines = lines.length;
        int codeLines = 0;
        int commentLines = 0;
        int blankLines = 0;
        int classCount = 0;
        int methodCount = 0;
        int publicMethodCount = 0;
        int privateMethodCount = 0;

        boolean inMultiLineComment = false;

        for (String line : lines) {
            String trimmedLine = line.trim();

            // Count blank lines
            if (trimmedLine.isEmpty()) {
                blankLines++;
                continue;
            }

            // Count single line comments
            if (trimmedLine.startsWith("//")) {
                commentLines++;
                continue;
            }

            // Count multi-line comments
            if (trimmedLine.contains("/*") && !inMultiLineComment) {
                inMultiLineComment = true;
                commentLines++;
                continue;
            }

            if (inMultiLineComment) {
                commentLines++;
                if (trimmedLine.contains("*/")) {
                    inMultiLineComment = false;
                }
                continue;
            }

            // Count code lines
            codeLines++;

            // Count classes
            if (trimmedLine.contains("class ") && !trimmedLine.contains("//")) {
                classCount++;
            }

            // Count methods
            if ((trimmedLine.contains("public") || trimmedLine.contains("private") || trimmedLine.contains("protected")) &&
                    (trimmedLine.contains("void") || trimmedLine.contains("String") || trimmedLine.contains("int") ||
                            trimmedLine.contains("boolean") || trimmedLine.contains("List") || trimmedLine.contains("Map")) &&
                    trimmedLine.contains("(") && trimmedLine.contains(")") &&
                    !trimmedLine.contains("//") && !trimmedLine.contains("class")) {
                methodCount++;

                if (trimmedLine.contains("public")) {
                    publicMethodCount++;
                } else if (trimmedLine.contains("private")) {
                    privateMethodCount++;
                }
            }
        }

        metrics.setTotalLines(totalLines);
        metrics.setCodeLines(codeLines);
        metrics.setCommentLines(commentLines);
        metrics.setBlankLines(blankLines);
        metrics.setClassCount(classCount);
        metrics.setMethodCount(methodCount);
        metrics.setPublicMethodCount(publicMethodCount);
        metrics.setPrivateMethodCount(privateMethodCount);

        // Calculate comment percentage
        double commentPercentage = totalLines > 0 ? (commentLines * 100.0) / totalLines : 0;
        metrics.setCommentPercentage(Math.round(commentPercentage * 10) / 10.0);

        // Calculate quality score
        int qualityScore = calculateQualityScore(metrics);
        metrics.setQualityScore(qualityScore);
        metrics.setQualityGrade(getQualityGrade(qualityScore));

        return metrics;
    }

    private int calculateQualityScore(MetricsResponse metrics) {
        int score = 100;

        // Comment coverage (max 20 points)
        if (metrics.getCommentPercentage() < 10) score -= 15;
        else if (metrics.getCommentPercentage() < 20) score -= 10;
        else if (metrics.getCommentPercentage() < 30) score -= 5;

        // Method count (max 15 points)
        if (metrics.getMethodCount() == 0) score -= 15;
        else if (metrics.getMethodCount() < 2) score -= 10;
        else if (metrics.getMethodCount() < 5) score -= 5;

        // Class structure (max 10 points)
        if (metrics.getClassCount() == 0) score -= 10;

        // Documentation (max 15 points)
        if (metrics.getCommentLines() == 0) score -= 15;

        // Penalty for too many blank lines (max 10 points)
        if (metrics.getBlankLines() > metrics.getTotalLines() * 0.3) score -= 10;

        return Math.max(0, Math.min(100, score));
    }

    private String getQualityGrade(int score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
}