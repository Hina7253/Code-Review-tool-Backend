package com.codereview.dto;

import java.util.List;

public class AnalysisResponse {
    private List<Issue> critical;
    private List<Issue> warning;
    private List<Issue> info;
    private int totalIssues;

    public AnalysisResponse() {
    }

    public AnalysisResponse(List<Issue> critical, List<Issue> warning, List<Issue> info) {
        this.critical = critical;
        this.warning = warning;
        this.info = info;
        this.totalIssues = (critical != null ? critical.size() : 0) +
                (warning != null ? warning.size() : 0) +
                (info != null ? info.size() : 0);
    }

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

    // Inner Issue class
    public static class Issue {
        private int lineNumber;
        private String description;
        private String suggestion;

        public Issue() {
        }

        public Issue(int lineNumber, String description, String suggestion) {
            this.lineNumber = lineNumber;
            this.description = description;
            this.suggestion = suggestion;
        }

        // Getters and Setters
        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }
    }
}