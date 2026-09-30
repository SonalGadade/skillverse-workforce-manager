package com.skillverse.manager.model;

public class FeedModel {

    private String name;
    private String role;
    private String post;
    private String time;
    private int likes;
    private int comments;
    private boolean liked;
    private boolean saved;

    public FeedModel(String name, String role, String post, String time, int likes, int comments) {
        this.name = name;
        this.role = role;
        this.post = post;
        this.time = time;
        this.likes = likes;
        this.comments = comments;
        this.liked = false;
        this.saved = false;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPost() {
        return post;
    }

    public String getTime() {
        return time;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void addLike() {
        if (!liked) {
            likes++;
            liked = true;
        }
    }

    public void removeLike() {
        if (liked) {
            likes--;
            liked = false;
        }
    }

    public void addComment() {
        comments++;
    }
}