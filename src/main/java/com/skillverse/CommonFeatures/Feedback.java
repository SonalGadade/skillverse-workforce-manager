package com.skillverse.CommonFeatures;

public class Feedback {
    private String feedbackId;
    private String fromUser;
    private String toUserRole; 
    private String targetUserId;
    private String targetUserName;
    private String category;
    private double rating;
    private String message;
    private String timestamp;
    private boolean acknowledged;

    public Feedback() {}

    public Feedback(String feedbackId, String fromUser, String toUserRole, String targetUserId,
                    String targetUserName, String category, double rating, String message,
                    String timestamp, boolean acknowledged) {
        this.feedbackId = feedbackId;
        this.fromUser = fromUser;
        this.toUserRole = toUserRole;
        this.targetUserId = targetUserId;
        this.targetUserName = targetUserName;
        this.category = category;
        this.rating = rating;
        this.message = message;
        this.timestamp = timestamp;
        this.acknowledged = acknowledged;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUserRole() {
        return toUserRole;
    }

    public void setToUserRole(String toUserRole) {
        this.toUserRole = toUserRole;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }
}
