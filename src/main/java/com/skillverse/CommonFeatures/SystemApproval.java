package com.skillverse.CommonFeatures;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import java.util.Date;

@IgnoreExtraProperties
public class SystemApproval {
    private String id;
    private String approvalId;
    private String title;
    private String applicantName;
    private String applicantEmail;
    private String requesterEmail;
    private String requestType; // "Skill Verification", "Department Change", "Role Promotion", "Profile Update"
    private String category;
    private String department;
    private String description;
    private String status = "PENDING"; // "PENDING", "APPROVED", "REJECTED"
    private Object submittedAt;
    private Object reviewedAt;
    private Object requestedAt;

    public SystemApproval() {}

    public SystemApproval(String approvalId, String title, String requesterEmail, String requestType, String status, Date requestedAt) {
        this.id = approvalId;
        this.approvalId = approvalId;
        this.title = title;
        this.applicantName = requesterEmail;
        this.applicantEmail = requesterEmail;
        this.requesterEmail = requesterEmail;
        this.requestType = requestType;
        this.category = requestType;
        this.status = status;
        this.submittedAt = requestedAt;
        this.requestedAt = requestedAt;
    }

    // Getters and Setters
    public String getId() { return id != null ? id : approvalId; }
    public void setId(String id) { this.id = id; if (this.approvalId == null) this.approvalId = id; }

    public String getApprovalId() { return approvalId != null ? approvalId : id; }
    public void setApprovalId(String approvalId) { this.approvalId = approvalId; if (this.id == null) this.id = approvalId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getApplicantName() { return applicantName != null ? applicantName : requesterEmail; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public String getApplicantEmail() { return applicantEmail != null ? applicantEmail : requesterEmail; }
    public void setApplicantEmail(String applicantEmail) { this.applicantEmail = applicantEmail; }

    public String getRequesterEmail() { return requesterEmail != null ? requesterEmail : applicantEmail; }
    public void setRequesterEmail(String requesterEmail) { this.requesterEmail = requesterEmail; }

    public String getRequestType() { return requestType != null ? requestType : category; }
    public void setRequestType(String requestType) { this.requestType = requestType; if (this.category == null) this.category = requestType; }

    public String getCategory() { return category != null ? category : requestType; }
    public void setCategory(String category) { this.category = category; if (this.requestType == null) this.requestType = category; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Object getSubmittedAt() { return submittedAt != null ? submittedAt : requestedAt; }
    public void setSubmittedAt(Object submittedAt) { this.submittedAt = submittedAt; }

    public Object getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(Object reviewedAt) { this.reviewedAt = reviewedAt; }

    public Object getRequestedAt() { return requestedAt != null ? requestedAt : submittedAt; }
    public void setRequestedAt(Object requestedAt) { this.requestedAt = requestedAt; }
}
