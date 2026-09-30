package com.skillverse.trainer.model;

public class Course {

    private int id;
    private String title;
    private String description;
    private String level;
    private int learnerCount;
    private String duration;
    private String status;

    public Course(
            int id,
            String title,
            String description,
            String level,
            int learnerCount,
            String duration,
            String status) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.level = level;
        this.learnerCount = learnerCount;
        this.duration = duration;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLevel() {
        return level;
    }

    public int getLearnerCount() {
        return learnerCount;
    }

    public String getDuration() {
        return duration;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setLearnerCount(int learnerCount) {
        this.learnerCount = learnerCount;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", level='" + level + '\'' +
                ", learnerCount=" + learnerCount +
                ", duration='" + duration + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
