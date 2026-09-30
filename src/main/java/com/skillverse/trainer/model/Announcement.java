package com.skillverse.trainer.model;

public class Announcement {

    private int id;
    private String title;
    private String message;
    private String date;
    private boolean read;

    public Announcement(
            int id,
            String title,
            String message,
            String date,
            boolean read) {

        this.id = id;
        this.title = title;
        this.message = message;
        this.date = date;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public boolean isRead() {
        return read;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", read=" + read +
                '}';
    }
}
