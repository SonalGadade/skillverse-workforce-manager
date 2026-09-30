package com.skillverse.CommonFeatures;

import java.util.Date;

public class EmployeeFeedback {
    private String feedbackId;
    private String employeeEmail;
    private String reviewerEmail;
    private String reviewerName;
    private String reviewerRole; 
    private String categoryTag;
    private String feedbackText;
    private double rating;          
    private String quarterOrDate;
    private boolean acknowledged;
    private Date createdAt;

    public EmployeeFeedback() {}

    public EmployeeFeedback(String feedbackId, String employeeEmail, String reviewerName, String reviewerRole, String feedbackText, double rating, String quarterOrDate, Date createdAt) {
        this.feedbackId = feedbackId;
        this.employeeEmail = employeeEmail;
        this.reviewerName = reviewerName;
        this.reviewerRole = reviewerRole;
        this.feedbackText = feedbackText;
        this.rating = rating;
        this.quarterOrDate = quarterOrDate;
        this.createdAt = createdAt;
    }

    public String getFeedbackId() { return feedbackId; }
    public void setFeedbackId(String feedbackId) { this.feedbackId = feedbackId; }

    public String getEmployeeEmail() { return employeeEmail; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }

    public String getReviewerEmail() { return reviewerEmail; }
    public void setReviewerEmail(String reviewerEmail) { this.reviewerEmail = reviewerEmail; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public String getReviewerRole() { return reviewerRole; }
    public void setReviewerRole(String reviewerRole) { this.reviewerRole = reviewerRole; }

    public String getCategoryTag() { return categoryTag != null ? categoryTag : "PERFORMANCE"; }
    public void setCategoryTag(String categoryTag) { this.categoryTag = categoryTag; }

    public String getFeedbackText() { return feedbackText; }
    public void setFeedbackText(String feedbackText) { this.feedbackText = feedbackText; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getQuarterOrDate() { return quarterOrDate; }
    public void setQuarterOrDate(String quarterOrDate) { this.quarterOrDate = quarterOrDate; }

    public boolean isAcknowledged() { return acknowledged; }
    public void setAcknowledged(boolean acknowledged) { this.acknowledged = acknowledged; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
