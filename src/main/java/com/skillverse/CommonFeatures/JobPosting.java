package com.skillverse.CommonFeatures;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@IgnoreExtraProperties
public class JobPosting {
    private String id;
    private String jobId;
    private String title;
    private String department;
    private String location;
    private String jobType;
    private String experience;
    private String salaryRange;
    private String description;
    private String skillsRequired;
    private String postedByEmail;
    private List<String> requiredSkills = new ArrayList<>();
    private String status = "ACTIVE";
    private long applicantsCount = 0;
    private Object createdAt; 

    public JobPosting() {}

    public JobPosting(String jobId, String title, String department, String experience, String location, String jobType, String salaryRange, String description, String skillsRequired, String postedByEmail, String status, String createdAt) {
        this.id = jobId;
        this.jobId = jobId;
        this.title = title;
        this.department = department;
        this.experience = experience;
        this.location = location;
        this.jobType = jobType;
        this.salaryRange = salaryRange;
        this.description = description;
        this.skillsRequired = skillsRequired;
        this.postedByEmail = postedByEmail;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() { return id != null ? id : jobId; }
    public void setId(String id) { this.id = id; if (this.jobId == null) this.jobId = id; }

    public String getJobId() { return jobId != null ? jobId : id; }
    public void setJobId(String jobId) { this.jobId = jobId; if (this.id == null) this.id = jobId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSkillsRequired() { return skillsRequired; }
    public void setSkillsRequired(String skillsRequired) { this.skillsRequired = skillsRequired; }

    public String getPostedByEmail() { return postedByEmail; }
    public void setPostedByEmail(String postedByEmail) { this.postedByEmail = postedByEmail; }

    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getApplicantsCount() { return (int) applicantsCount; }
    public void setApplicantsCount(int applicantsCount) { this.applicantsCount = applicantsCount; }
    public void setApplicantsCount(long applicantsCount) { this.applicantsCount = applicantsCount; }

    public Object getCreatedAt() { return createdAt; }
    public void setCreatedAt(Object createdAt) { this.createdAt = createdAt; }
}
