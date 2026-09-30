package com.skillverse.manager.model;

public class FeedbackModel {

    private String employee;
    private String message;
    private String type;
    private boolean responded;

    public FeedbackModel(String employee, String message, String type) {
        this.employee = employee;
        this.message = message;
        this.type = type;
        this.responded = false;
    }

    public String getEmployee() {
        return employee;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public boolean isResponded() {
        return responded;
    }

    public void setResponded(boolean responded) {
        this.responded = responded;
    }
}