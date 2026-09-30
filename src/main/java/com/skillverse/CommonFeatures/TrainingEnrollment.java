package com.skillverse.CommonFeatures;

public class TrainingEnrollment {
    private String enrollmentId;
    private String programId;
    private String programTitle;
    private String employeeEmail;
    private String employeeName;
    private String employeeDepartment;
    private double attendancePercentage;
    private double assessmentScore;
    private String completionStatus; 
    private String completionDate;
    private String enrolledAt;

    public TrainingEnrollment() {}

    public TrainingEnrollment(String enrollmentId, String programId, String programTitle, String employeeEmail, String employeeName, String employeeDepartment, double attendancePercentage, double assessmentScore, String completionStatus, String completionDate, String enrolledAt) {
        this.enrollmentId = enrollmentId;
        this.programId = programId;
        this.programTitle = programTitle;
        this.employeeEmail = employeeEmail;
        this.employeeName = employeeName;
        this.employeeDepartment = employeeDepartment;
        this.attendancePercentage = attendancePercentage;
        this.assessmentScore = assessmentScore;
        this.completionStatus = completionStatus;
        this.completionDate = completionDate;
        this.enrolledAt = enrolledAt;
    }

    public String getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(String enrollmentId) { this.enrollmentId = enrollmentId; }

    public String getProgramId() { return programId; }
    public void setProgramId(String programId) { this.programId = programId; }

    public String getProgramTitle() { return programTitle; }
    public void setProgramTitle(String programTitle) { this.programTitle = programTitle; }

    public String getEmployeeEmail() { return employeeEmail; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getEmployeeDepartment() { return employeeDepartment; }
    public void setEmployeeDepartment(String employeeDepartment) { this.employeeDepartment = employeeDepartment; }

    public double getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(double attendancePercentage) { this.attendancePercentage = attendancePercentage; }

    public double getAssessmentScore() { return assessmentScore; }
    public void setAssessmentScore(double assessmentScore) { this.assessmentScore = assessmentScore; }

    public String getCompletionStatus() { return completionStatus; }
    public void setCompletionStatus(String completionStatus) { this.completionStatus = completionStatus; }

    public String getCompletionDate() { return completionDate; }
    public void setCompletionDate(String completionDate) { this.completionDate = completionDate; }

    public String getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(String enrolledAt) { this.enrolledAt = enrolledAt; }
}
