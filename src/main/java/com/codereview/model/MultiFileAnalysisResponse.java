package com.codereview.model;

import java.util.ArrayList;
import java.util.List;

public class MultiFileAnalysisResponse {
    private int totalFiles;
    private int totalIssues;
    private int totalCritical;
    private int totalWarnings;
    private int totalInfo;
    private double averageQualityScore;
    private List<FileAnalysisResult> fileResults;
    private String error;

    public MultiFileAnalysisResponse() {
        this.fileResults = new ArrayList<>();
    }

    // Getters and Setters
    public int getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(int totalFiles) {
        this.totalFiles = totalFiles;
    }

    public int getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(int totalIssues) {
        this.totalIssues = totalIssues;
    }

    public int getTotalCritical() {
        return totalCritical;
    }

    public void setTotalCritical(int totalCritical) {
        this.totalCritical = totalCritical;
    }

    public int getTotalWarnings() {
        return totalWarnings;
    }

    public void setTotalWarnings(int totalWarnings) {
        this.totalWarnings = totalWarnings;
    }

    public int getTotalInfo() {
        return totalInfo;
    }

    public void setTotalInfo(int totalInfo) {
        this.totalInfo = totalInfo;
    }

    public double getAverageQualityScore() {
        return averageQualityScore;
    }

    public void setAverageQualityScore(double averageQualityScore) {
        this.averageQualityScore = averageQualityScore;
    }

    public List<FileAnalysisResult> getFileResults() {
        return fileResults;
    }

    public void setFileResults(List<FileAnalysisResult> fileResults) {
        this.fileResults = fileResults;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}