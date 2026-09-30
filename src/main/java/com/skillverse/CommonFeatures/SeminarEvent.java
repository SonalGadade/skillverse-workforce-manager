package com.skillverse.CommonFeatures;

import java.util.Date;

public class SeminarEvent {
    private String eventId;
    private String title;
    private String topic;
    private String trainerEmail;
    private String trainerName;
    private String eventDate;
    private String eventTime;
    private String meetingLinkOrVenue;
    private String targetDepartment;
    private Date createdAt;

    public SeminarEvent() {}

    public SeminarEvent(String eventId, String title, String topic, String trainerEmail, String trainerName, String eventDate, String eventTime, String meetingLinkOrVenue, String targetDepartment, Date createdAt) {
        this.eventId = eventId;
        this.title = title;
        this.topic = topic;
        this.trainerEmail = trainerEmail;
        this.trainerName = trainerName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.meetingLinkOrVenue = meetingLinkOrVenue;
        this.targetDepartment = targetDepartment;
        this.createdAt = createdAt != null ? createdAt : new Date();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getTrainerEmail() { return trainerEmail; }
    public void setTrainerEmail(String trainerEmail) { this.trainerEmail = trainerEmail; }

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public String getEventTime() { return eventTime; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime; }

    public String getMeetingLinkOrVenue() { return meetingLinkOrVenue; }
    public void setMeetingLinkOrVenue(String meetingLinkOrVenue) { this.meetingLinkOrVenue = meetingLinkOrVenue; }

    public String getTargetDepartment() { return targetDepartment; }
    public void setTargetDepartment(String targetDepartment) { this.targetDepartment = targetDepartment; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
