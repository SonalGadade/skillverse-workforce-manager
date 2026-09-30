package com.skillverse.CommonFeatures;

public class LeaveRequest {
    private String leaveId;
    private String employeeEmail;
    private String employeeName;
    private String leaveType;
    private String startDate;
    private String endDate;
    private int durationDays;
    private String reason;
    private String status; 
    private String appliedAt;
    private String managerEmail;
    private String managerComment;

    public LeaveRequest() {}

    public LeaveRequest(String leaveId, String employeeEmail, String employeeName, String leaveType,
                        String startDate, String endDate, int durationDays, String reason,
                        String status, String appliedAt, String managerEmail, String managerComment) {
        this.leaveId = leaveId;
        this.employeeEmail = employeeEmail;
        this.employeeName = employeeName;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationDays = durationDays;
        this.reason = reason;
        this.status = status;
        this.appliedAt = appliedAt;
        this.managerEmail = managerEmail;
        this.managerComment = managerComment;
    }

    public String getLeaveId() { return leaveId; }
    public void setLeaveId(String leaveId) { this.leaveId = leaveId; }

    public String getEmployeeEmail() { return employeeEmail; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public int getDurationDays() { return durationDays; }
    public void setDurationDays(int durationDays) { this.durationDays = durationDays; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAppliedAt() { return appliedAt; }
    public void setAppliedAt(String appliedAt) { this.appliedAt = appliedAt; }

    public String getManagerEmail() { return managerEmail; }
    public void setManagerEmail(String managerEmail) { this.managerEmail = managerEmail; }

    public String getManagerComment() { return managerComment; }
    public void setManagerComment(String managerComment) { this.managerComment = managerComment; }
}
