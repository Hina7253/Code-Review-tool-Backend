package com.codereview.model;

import java.util.List;

public class FileAnalysisResult {
    private String fileName;
    private AnalysisResponse analysis;
    private CodeMetrics metrics;
    private int totalIssue;

    public FileAnalysisResult() {}

    public FileAnalysisResult(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public AnalysisResponse getAnalysis() {
        return analysis;
    }

    public void setAnalysis(AnalysisResponse analysis) {
        this.analysis = analysis;
    }

    public CodeMetrics getMetrics() {
        return metrics;
    }

    public void setMetrics(CodeMetrics metrics) {
        this.metrics = metrics;
    }

    public int getTotalIssue() {
        return totalIssue;
    }

    public void setTotalIssue(int totalIssue) {
        this.totalIssue = totalIssue;
    }
}
