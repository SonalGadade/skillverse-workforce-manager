package com.skillverse.CommonFeatures;

public class SpeakUpTicket {

    private String ticketId;
    private String senderRole;
    private String senderName;
    private String senderEmail;
    private String category;
    private String priority; 
    private String subject;
    private String description;
    private String status; 
    private String managerResolutionNote;
    private String timestamp;
    private boolean isAnonymous;

    public SpeakUpTicket() {}

    public SpeakUpTicket(String ticketId, String senderRole, String senderName, String senderEmail, 
                         String category, String priority, String subject, String description, 
                         String status, String managerResolutionNote, String timestamp, boolean isAnonymous) {
        this.ticketId = ticketId;
        this.senderRole = senderRole;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.category = category;
        this.priority = priority;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.managerResolutionNote = managerResolutionNote;
        this.timestamp = timestamp;
        this.isAnonymous = isAnonymous;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
    }

    public String getSenderName() {
        return isAnonymous ? "Anonymous Employee" : senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManagerResolutionNote() {
        return managerResolutionNote;
    }

    public void setManagerResolutionNote(String managerResolutionNote) {
        this.managerResolutionNote = managerResolutionNote;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
}
