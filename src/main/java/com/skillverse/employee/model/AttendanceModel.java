package com.skillverse.employee.model;

public class AttendanceModel {

    private String email;
    private int presentDays;
    private int absentDays;
    private int leaveDays;
    private int totalWorkingDays;
    private double attendancePercentage;

    public AttendanceModel(
            String email,
            int presentDays,
            int absentDays,
            int leaveDays,
            int totalWorkingDays,
            double attendancePercentage
    ) {
        this.email = email;
        this.presentDays = presentDays;
        this.absentDays = absentDays;
        this.leaveDays = leaveDays;
        this.totalWorkingDays = totalWorkingDays;
        this.attendancePercentage = attendancePercentage;
    }

    public String getEmail() {
        return email;
    }

    public int getPresentDays() {
        return presentDays;
    }

    public int getAbsentDays() {
        return absentDays;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public int getTotalWorkingDays() {
        return totalWorkingDays;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPresentDays(int presentDays) {
        this.presentDays = presentDays;
    }

    public void setAbsentDays(int absentDays) {
        this.absentDays = absentDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    public void setTotalWorkingDays(int totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }
}