package com.skillverse.hr.view;

public class Training {
    private String courseName;
    private String department;
    private int enrolledCount;
    private double completionRate;

    public Training(String courseName, String department, int enrolledCount, double completionRate) {
        this.courseName = courseName;
        this.department = department;
        this.enrolledCount = enrolledCount;
        this.completionRate = completionRate;
    }

    public String getCourseName() { return courseName; }
    public String getDepartment() { return department; }
    public int getEnrolledCount() { return enrolledCount; }
    public double getCompletionRate() { return completionRate; }
}
