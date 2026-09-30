package com.skillverse.manager.model;

public class AnonymousModel {

    private String category;
    private String message;
    private String time;
    private boolean acknowledged;

    public AnonymousModel(String category, String message, String time) {
        this.category = category;
        this.message = message;
        this.time = time;
        this.acknowledged = false;
    }

    public String getCategory() {
        return category;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }
}