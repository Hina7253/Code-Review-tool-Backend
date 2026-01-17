package com.codereview.model;

public class CodeMetrics {
    private int totalLines;
    private int countLines;
    private int commentLines;
    private int blankLines;
    private int classCount;
    private int methodCount;
    private int publicMethodCount;
    private int privateMethodCount;
    private int commentPercentage;
    private int qualityScore;
    private int qualityGrade;

    public CodeMetrics() {}

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getCountLines() {
        return countLines;
    }

    public void setCountLines(int countLines) {
        this.countLines = countLines;
    }

    public int getCommentLines() {
        return commentLines;
    }

    public void setCommentLines(int commentLines) {
        this.commentLines = commentLines;
    }

    public int getBlankLines() {
        return blankLines;
    }

    public void setBlankLines(int blankLines) {
        this.blankLines = blankLines;
    }

    public int getClassCount() {
        return classCount;
    }

    public void setClassCount(int classCount) {
        this.classCount = classCount;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public void setMethodCount(int methodCount) {
        this.methodCount = methodCount;
    }

    public int getPublicMethodCount() {
        return publicMethodCount;
    }

    public void setPublicMethodCount(int publicMethodCount) {
        this.publicMethodCount = publicMethodCount;
    }

    public int getPrivateMethodCount() {
        return privateMethodCount;
    }

    public void setPrivateMethodCount(int privateMethodCount) {
        this.privateMethodCount = privateMethodCount;
    }

    public int getCommentPercentage() {
        return commentPercentage;
    }

    public void setCommentPercentage(int commentPercentage) {
        this.commentPercentage = commentPercentage;
    }

    public int getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(int qualityScore) {
        this.qualityScore = qualityScore;
    }

    public int getQualityGrade() {
        return qualityGrade;
    }

    public void setQualityGrade(int qualityGrade) {
        this.qualityGrade = qualityGrade;
    }

    public void setCodeLines(int codeLines) {
    }
}
