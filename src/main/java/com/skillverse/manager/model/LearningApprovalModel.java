package com.skillverse.manager.model;

public class LearningApprovalModel {

    private String employeeName;
    private String courseName;
    private String date;
    private String status;

    public LearningApprovalModel(String employeeName, String courseName, String date, String status) {
        this.employeeName = employeeName;
        this.courseName = courseName;
        this.date = date;
        this.status = status;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}