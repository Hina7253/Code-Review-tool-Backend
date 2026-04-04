package com.codereview.dto;

public class MetricsResponse {
    private int totalLines;
    private int codeLines;
    private int commentLines;
    private int blankLines;
    private int classCount;
    private int methodCount;
    private int publicMethodCount;
    private int privateMethodCount;
    private double commentPercentage;
    private int qualityScore;
    private String qualityGrade;

    // Constructor
    public MetricsResponse() {
        this.totalLines = 0;
        this.codeLines = 0;
        this.commentLines = 0;
        this.blankLines = 0;
        this.classCount = 0;
        this.methodCount = 0;
        this.publicMethodCount = 0;
        this.privateMethodCount = 0;
        this.commentPercentage = 0.0;
        this.qualityScore = 0;
        this.qualityGrade = "F";
    }

    // Getters and Setters
    public int getTotalLines() { return totalLines; }
    public void setTotalLines(int totalLines) { this.totalLines = totalLines; }

    public int getCodeLines() { return codeLines; }
    public void setCodeLines(int codeLines) { this.codeLines = codeLines; }

    public int getCommentLines() { return commentLines; }
    public void setCommentLines(int commentLines) { this.commentLines = commentLines; }

    public int getBlankLines() { return blankLines; }
    public void setBlankLines(int blankLines) { this.blankLines = blankLines; }

    public int getClassCount() { return classCount; }
    public void setClassCount(int classCount) { this.classCount = classCount; }

    public int getMethodCount() { return methodCount; }
    public void setMethodCount(int methodCount) { this.methodCount = methodCount; }

    public int getPublicMethodCount() { return publicMethodCount; }
    public void setPublicMethodCount(int publicMethodCount) { this.publicMethodCount = publicMethodCount; }

    public int getPrivateMethodCount() { return privateMethodCount; }
    public void setPrivateMethodCount(int privateMethodCount) { this.privateMethodCount = privateMethodCount; }

    public double getCommentPercentage() { return commentPercentage; }
    public void setCommentPercentage(double commentPercentage) { this.commentPercentage = commentPercentage; }

    public int getQualityScore() { return qualityScore; }
    public void setQualityScore(int qualityScore) { this.qualityScore = qualityScore; }

    public String getQualityGrade() { return qualityGrade; }
    public void setQualityGrade(String qualityGrade) { this.qualityGrade = qualityGrade; }
}