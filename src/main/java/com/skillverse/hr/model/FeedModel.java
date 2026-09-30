package com.skillverse.hr.model;

import java.util.ArrayList;
import java.util.List;

public class FeedModel {

    private String userName;
    private String role;
    private String content;
    private String imagePath;

    private int likes;
    private boolean liked;

    private List<String> comments;

    public FeedModel(
            String userName,
            String role,
            String content,
            String imagePath
    ) {
        this.userName = userName;
        this.role = role;
        this.content = content;
        this.imagePath = imagePath;

        this.likes = 0;
        this.liked = false;

        this.comments = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getLikes() {
        return likes;
    }

    public boolean isLiked() {
        return liked;
    }

    public List<String> getComments() {
        return comments;
    }

    public void toggleLike() {

        if (liked) {
            likes--;
            liked = false;
        } else {
            likes++;
            liked = true;
        }
    }

    public void addComment(String comment) {

        if (comment != null && !comment.trim().isEmpty()) {
            comments.add(comment.trim());
        }
    }
}