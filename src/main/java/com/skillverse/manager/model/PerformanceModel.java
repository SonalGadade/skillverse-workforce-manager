package com.skillverse.manager.model;

public class PerformanceModel {

    private String averagePerformance;
    private String aboveTarget;
    private String needsAttention;

    public PerformanceModel() {
        averagePerformance = "88%";
        aboveTarget = "9";
        needsAttention = "3";
    }

    public String getAveragePerformance() {
        return averagePerformance;
    }

    public String getAboveTarget() {
        return aboveTarget;
    }

    public String getNeedsAttention() {
        return needsAttention;
    }

    public void updatePerformanceStatistics(String averagePerformance, String aboveTarget, String needsAttention) {
        this.averagePerformance = averagePerformance;
        this.aboveTarget = aboveTarget;
        this.needsAttention = needsAttention;
    }

    public boolean isValidPerformance(int performance) {
        return performance >= 0 && performance <= 100;
    }

    public void updateEmployeePerformance(String employeeName, int performance) {
        if (employeeName != null && !employeeName.trim().isEmpty() && isValidPerformance(performance)) {
            System.out.println(employeeName + " performance updated to " + performance + "%");
        }
    }
}