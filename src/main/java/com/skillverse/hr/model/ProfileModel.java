package com.skillverse.hr.model;

public class ProfileModel {

    private String userName;
    private String role;
    private String email;
    private String about;

    private int followers = 248;
    private int following = 126;
    private int posts = 18;

    public ProfileModel(
            String userName,
            String role,
            String email,
            String about
    ) {

        this.userName = userName;
        this.role = role;
        this.email = email;
        this.about = about;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getAbout() {
        return about;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public int getPosts() {
        return posts;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void updateProfile(
            String userName,
            String email,
            String role,
            String about
    ) {

        this.userName = userName;
        this.email = email;
        this.role = role;
        this.about = about;
    }
}
