package com.skillverse.CommonFeatures;

import java.util.Date;

public class TrainerAssessment {
    private String assessmentId;
    private String title;
    private String courseName;
    private String trainerEmail;
    private String durationMinutes;
    private int totalMarks;
    private String targetDepartment;
    private String deadlineDate;
    private Date createdAt;

    public TrainerAssessment() {}

    public TrainerAssessment(String assessmentId, String title, String courseName, String trainerEmail, String durationMinutes, int totalMarks, String targetDepartment, String deadlineDate, Date createdAt) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.courseName = courseName;
        this.trainerEmail = trainerEmail;
        this.durationMinutes = durationMinutes;
        this.totalMarks = totalMarks;
        this.targetDepartment = targetDepartment;
        this.deadlineDate = deadlineDate;
        this.createdAt = createdAt != null ? createdAt : new Date();
    }

    public String getAssessmentId() { return assessmentId; }
    public void setAssessmentId(String assessmentId) { this.assessmentId = assessmentId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getTrainerEmail() { return trainerEmail; }
    public void setTrainerEmail(String trainerEmail) { this.trainerEmail = trainerEmail; }

    public String getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(String durationMinutes) { this.durationMinutes = durationMinutes; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public String getTargetDepartment() { return targetDepartment; }
    public void setTargetDepartment(String targetDepartment) { this.targetDepartment = targetDepartment; }

    public String getDeadlineDate() { return deadlineDate; }
    public void setDeadlineDate(String deadlineDate) { this.deadlineDate = deadlineDate; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
