package com.codereview.model;

import java.util.List;

public class AnalysisResponse {
    private List<Issue> critical;
    private List<Issue> warning;
    private List<Issue> info;
    private int totalIssues;
    private String error;

    // Getters and Setters
    public List<Issue> getCritical() {
        return critical;
    }

    public void setCritical(List<Issue> critical) {
        this.critical = critical;
    }

    public List<Issue> getWarning() {
        return warning;
    }

    public void setWarning(List<Issue> warning) {
        this.warning = warning;
    }

    public List<Issue> getInfo() {
        return info;
    }

    public void setInfo(List<Issue> info) {
        this.info = info;
    }

    public int getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(int totalIssues) {
        this.totalIssues = totalIssues;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}