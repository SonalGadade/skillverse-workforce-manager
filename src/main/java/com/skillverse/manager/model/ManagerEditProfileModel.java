package com.skillverse.manager.model;

import javafx.scene.image.Image;

public class ManagerEditProfileModel {

    public static String profileName = "Manager Name";
    public static String username = "manager";
    public static String bio = "Manager profile";
    public static String email = "manager@skillverse.com";
    public static Image profileImage = null;

    public static String getProfileName() {
        return profileName;
    }

    public static void setProfileName(String profileName) {
        ManagerEditProfileModel.profileName = profileName;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ManagerEditProfileModel.username = username;
    }

    public static String getBio() {
        return bio;
    }

    public static void setBio(String bio) {
        ManagerEditProfileModel.bio = bio;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        ManagerEditProfileModel.email = email;
    }

    public static Image getProfileImage() {
        return profileImage;
    }

    public static void setProfileImage(Image profileImage) {
        ManagerEditProfileModel.profileImage = profileImage;
    }
}