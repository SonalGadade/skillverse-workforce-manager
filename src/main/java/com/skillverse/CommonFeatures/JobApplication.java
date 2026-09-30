package com.skillverse.CommonFeatures;

public class JobApplication {
    private String applicationId;
    private String jobId;
    private String jobTitle;
    private String applicantName;
    private String applicantEmail;
    private String applicantRole;
    private String applicantDepartment;
    private String experienceYears;
    private String coverNote;
    private String status;
    private String appliedAt;

    public JobApplication() {}

    public JobApplication(String applicationId, String jobId, String jobTitle, String applicantName, String applicantEmail, String applicantRole, String applicantDepartment, String experienceYears, String coverNote, String status, String appliedAt) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.applicantRole = applicantRole;
        this.applicantDepartment = applicantDepartment;
        this.experienceYears = experienceYears;
        this.coverNote = coverNote;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public String getApplicantEmail() { return applicantEmail; }
    public void setApplicantEmail(String applicantEmail) { this.applicantEmail = applicantEmail; }

    public String getApplicantRole() { return applicantRole; }
    public void setApplicantRole(String applicantRole) { this.applicantRole = applicantRole; }

    public String getApplicantDepartment() { return applicantDepartment; }
    public void setApplicantDepartment(String applicantDepartment) { this.applicantDepartment = applicantDepartment; }

    public String getExperienceYears() { return experienceYears; }
    public void setExperienceYears(String experienceYears) { this.experienceYears = experienceYears; }

    public String getCoverNote() { return coverNote; }
    public void setCoverNote(String coverNote) { this.coverNote = coverNote; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAppliedAt() { return appliedAt; }
    public void setAppliedAt(String appliedAt) { this.appliedAt = appliedAt; }
}
