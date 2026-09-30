package com.skillverse.hr.view;

public class Performance {
    private String employeeName;
    private double rating;
    private double goalCompletionPct;

    public Performance(String employeeName, double rating, double goalCompletionPct) {
        this.employeeName = employeeName;
        this.rating = rating;
        this.goalCompletionPct = goalCompletionPct;
    }

    public String getEmployeeName() { return employeeName; }
    public double getRating() { return rating; }
    public double getGoalCompletionPct() { return goalCompletionPct; }
}
