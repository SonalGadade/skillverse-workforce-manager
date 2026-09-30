package com.skillverse.hr.model;

import java.util.ArrayList;
import java.util.List;

public class PostModel {

    private String userName;
    private String role;
    private String content;
    private String imagePath;

    private int likes;
    private boolean liked;

    private List<String> comments;

    public PostModel(
            String userName,
            String role,
            String content,
            String imagePath,
            int likes
    ) {

        this.userName = userName;
        this.role = role;
        this.content = content;
        this.imagePath = imagePath;
        this.likes = likes;

        this.comments = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getLikes() {
        return likes;
    }

    public boolean isLiked() {
        return liked;
    }

    public void toggleLike() {

        liked = !liked;

        if (liked) {
            likes++;
        } else {
            likes--;
        }
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment) {

        if (comment != null && !comment.trim().isEmpty()) {
            comments.add(comment.trim());
        }
    }
}