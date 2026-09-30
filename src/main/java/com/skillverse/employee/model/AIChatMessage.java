package com.skillverse.employee.model;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import java.util.Date;

@IgnoreExtraProperties
public class AIChatMessage {
    private String messageId;
    private String employeeEmail;
    private String sender; 
    private String messageText;
    private String category; 
    private Object timestamp;

    public AIChatMessage() {}

    public AIChatMessage(String messageId, String employeeEmail, String sender, String messageText, String category) {
        this.messageId = messageId;
        this.employeeEmail = employeeEmail;
        this.sender = sender;
        this.messageText = messageText;
        this.category = category;
        this.timestamp = new Date();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isUser() {
        return "User".equalsIgnoreCase(sender);
    }
}
