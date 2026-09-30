package com.skillverse.manager.model;

import javafx.scene.image.Image;

public class ManagerProfileModel {

    private String profileName;
    private String username;
    private String bio;
    private String email;
    private Image profileImage;

    public ManagerProfileModel() {
        profileName = "Manager";
        username = "manager";
        bio = "manager";
        email = "manager@skillverse.com";
        profileImage = null;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }
}