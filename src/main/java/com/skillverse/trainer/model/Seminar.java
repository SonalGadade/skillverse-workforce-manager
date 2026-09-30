package com.skillverse.trainer.model;

public class Seminar {

    private int id;
    private String title;
    private String date;
    private String time;
    private String description;
    private int attendeeCount;
    private String status;

    public Seminar(
            int id,
            String title,
            String date,
            String time,
            String description,
            int attendeeCount,
            String status) {

        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.attendeeCount = attendeeCount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Seminar{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", attendeeCount=" + attendeeCount +
                ", status='" + status + '\'' +
                '}';
    }
}
