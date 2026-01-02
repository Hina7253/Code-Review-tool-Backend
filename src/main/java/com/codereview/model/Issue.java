package com.codereview.model;

public class Issue {
    private String severity;
    private int lineNumber;
    private String description;
    private String suggestion;

    public Issue() {}

    public Issue(String severity, int lineNumber, String description, String suggestion) {
        this.severity = severity;
        this.lineNumber = lineNumber;
        this.description = description;
        this.suggestion = suggestion;
    }

    // Getters and Setters
    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

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