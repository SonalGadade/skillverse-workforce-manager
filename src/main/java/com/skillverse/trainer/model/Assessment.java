package com.skillverse.trainer.model;

public class Assessment {

    private int id;
    private String title;
    private String courseName;
    private int submissionCount;
    private int totalMarks;
    private String description;
    private String status;

    public Assessment(
            int id,
            String title,
            String courseName,
            int submissionCount,
            int totalMarks,
            String description,
            String status) {

        this.id = id;
        this.title = title;
        this.courseName = courseName;
        this.submissionCount = submissionCount;
        this.totalMarks = totalMarks;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getSubmissionCount() {
        return submissionCount;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public String getDescription() {
        return description;
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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setSubmissionCount(int submissionCount) {
        this.submissionCount = submissionCount;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", courseName='" + courseName + '\'' +
                ", submissionCount=" + submissionCount +
                ", totalMarks=" + totalMarks +
                ", status='" + status + '\'' +
                '}';
    }
}
