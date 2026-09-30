package com.skillverse.CommonFeatures;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import java.util.Date;

@IgnoreExtraProperties
public class AppNotification {
    private String id;
    private String title;
    private String message;
    private String category; 
    private boolean read = false;
    private Object createdAt;

    public AppNotification() {}

    public AppNotification(String title, String message, String category) {
        this.title = title;
        this.message = message;
        this.category = category;
        this.read = false;
        this.createdAt = new Date();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public Object getCreatedAt() { return createdAt; }
    public void setCreatedAt(Object createdAt) { this.createdAt = createdAt; }
}
