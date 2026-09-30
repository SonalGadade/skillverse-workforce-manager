package com.skillverse.manager.model;

public class GoalsKPIModel {

    private String goal;
    private String employee;
    private double progress;
    private String percentage;

    public GoalsKPIModel(String goal, String employee, double progress, String percentage) {
        this.goal = goal;
        this.employee = employee;
        this.progress = progress;
        this.percentage = percentage;
    }

    public String getGoal() {
        return goal;
    }

    public String getEmployee() {
        return employee;
    }

    public double getProgress() {
        return progress;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}