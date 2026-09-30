package com.skillverse.trainer.model;

public class Learner {

    private int id;
    private String fullName;
    private String email;
    private String courseName;
    private int courseCompletion;
    private int assessmentScore;
    private String status;

    public Learner(
            int id,
            String fullName,
            String email,
            String courseName,
            int courseCompletion,
            int assessmentScore,
            String status) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.courseName = courseName;
        this.courseCompletion = courseCompletion;
        this.assessmentScore = assessmentScore;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseCompletion() {
        return courseCompletion;
    }

    public int getAssessmentScore() {
        return assessmentScore;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseCompletion(int courseCompletion) {
        this.courseCompletion = courseCompletion;
    }

    public void setAssessmentScore(int assessmentScore) {
        this.assessmentScore = assessmentScore;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Learner{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseCompletion=" + courseCompletion +
                ", assessmentScore=" + assessmentScore +
                ", status='" + status + '\'' +
                '}';
    }
}
