package com.skillverse.manager.model;

public class InterviewModel {

    private String candidate;
    private String position;
    private String time;
    private String round;

    public InterviewModel(String candidate, String position, String time, String round) {
        this.candidate = candidate;
        this.position = position;
        this.time = time;
        this.round = round;
    }

    public String getCandidate() {
        return candidate;
    }

    public String getPosition() {
        return position;
    }

    public String getTime() {
        return time;
    }

    public String getRound() {
        return round;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRound(String round) {
        this.round = round;
    }
}