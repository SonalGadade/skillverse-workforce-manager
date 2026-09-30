package com.skillverse.CommonFeatures;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
public class User {
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String department;
    private String profileImageUrl;
    private String phone;
    private String status;
    private String joiningDate;
    private String managerEmail;
    private double performanceRating;
    private List<String> skills;
    private Object createdAt;

    public User() {}

    public User(String fullName, String email, String password, String role, String department) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.department = department;
    }

    public String getFullName() {
        return fullName;
    }

    public String getName() {
        return fullName != null ? fullName : email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setName(String name) {
        this.fullName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProfileImageUrl() {
        if (profileImageUrl != null && (profileImageUrl.trim().isEmpty() || "null".equalsIgnoreCase(profileImageUrl.trim()))) {
            return null;
        }
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        if (profileImageUrl != null && (profileImageUrl.trim().isEmpty() || "null".equalsIgnoreCase(profileImageUrl.trim()))) {
            this.profileImageUrl = null;
        } else {
            this.profileImageUrl = profileImageUrl;
        }
    }

    public String getProfilePicUrl() {
        return getProfileImageUrl();
    }

    public void setProfilePicUrl(String profilePicUrl) {
        setProfileImageUrl(profilePicUrl);
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public double getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(double performanceRating) {
        this.performanceRating = performanceRating;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
