package com.skillverse.hr.view;

public class Reports {
    private int totalEmployees;
    private int activeRecruitments;
    private double avgPerformanceScore;
    private double trainingCompletionRate;

    public Reports(int totalEmployees, int activeRecruitments, double avgPerformanceScore, double trainingCompletionRate) {
        this.totalEmployees = totalEmployees;
        this.activeRecruitments = activeRecruitments;
        this.avgPerformanceScore = avgPerformanceScore;
        this.trainingCompletionRate = trainingCompletionRate;
    }

    public int getTotalEmployees() { return totalEmployees; }
    public int getActiveRecruitments() { return activeRecruitments; }
    public double getAvgPerformanceScore() { return avgPerformanceScore; }
    public double getTrainingCompletionRate() { return trainingCompletionRate; }
}
