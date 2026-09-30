package com.skillverse.CommonFeatures;

import java.util.Date;

public class TrainerAnnouncement {
    private String announcementId;
    private String title;
    private String message;
    private String trainerName;
    private String trainerEmail;
    private String priority; 
    private String targetAudience;
    private Date createdAt;

    public TrainerAnnouncement() {}

    public TrainerAnnouncement(String announcementId, String title, String message, String trainerName, String trainerEmail, String priority, String targetAudience, Date createdAt) {
        this.announcementId = announcementId;
        this.title = title;
        this.message = message;
        this.trainerName = trainerName;
        this.trainerEmail = trainerEmail;
        this.priority = priority != null ? priority : "NORMAL";
        this.targetAudience = targetAudience != null ? targetAudience : "ALL";
        this.createdAt = createdAt != null ? createdAt : new Date();
    }

    public String getAnnouncementId() { return announcementId; }
    public void setAnnouncementId(String announcementId) { this.announcementId = announcementId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    public String getTrainerEmail() { return trainerEmail; }
    public void setTrainerEmail(String trainerEmail) { this.trainerEmail = trainerEmail; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getTargetAudience() { return targetAudience; }
    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
