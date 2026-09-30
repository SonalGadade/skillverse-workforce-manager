package com.skillverse.CommonFeatures;

public class TrainingProgram {
    private String programId;
    private String title;
    private String description;
    private String assignedTrainerEmail;
    private String assignedTrainerName;
    private String department;
    private String startDate;
    private String endDate;
    private String durationWeeks;
    private String status; 
    private String createdAt;

    public TrainingProgram() {}

    public TrainingProgram(String programId, String title, String description, String assignedTrainerEmail, String assignedTrainerName, String department, String startDate, String endDate, String durationWeeks, String status, String createdAt) {
        this.programId = programId;
        this.title = title;
        this.description = description;
        this.assignedTrainerEmail = assignedTrainerEmail;
        this.assignedTrainerName = assignedTrainerName;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationWeeks = durationWeeks;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getProgramId() { return programId; }
    public void setProgramId(String programId) { this.programId = programId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAssignedTrainerEmail() { return assignedTrainerEmail; }
    public void setAssignedTrainerEmail(String assignedTrainerEmail) { this.assignedTrainerEmail = assignedTrainerEmail; }

    public String getAssignedTrainerName() { return assignedTrainerName; }
    public void setAssignedTrainerName(String assignedTrainerName) { this.assignedTrainerName = assignedTrainerName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getDurationWeeks() { return durationWeeks; }
    public void setDurationWeeks(String durationWeeks) { this.durationWeeks = durationWeeks; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
