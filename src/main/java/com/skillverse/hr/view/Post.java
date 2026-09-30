package com.skillverse.hr.view;

import java.util.ArrayList;
import java.util.List;

public class Post {

    private String userName;
    private String role;
    private String headlineTitle;
    private String jobTag;
    private String content;
    private String imagePath;
    private String timestamp;

    private int likes;
    private boolean liked;

    private List<String> comments;

    public Post(String userName, String role, String content, String imagePath, int likes) {
        this(userName, role, null, "General", content, imagePath, "Just now", likes);
    }

    public Post(String userName, String role, String headlineTitle, String jobTag, String content, String imagePath, String timestamp, int likes) {
        this.userName = userName;
        this.role = role;
        this.headlineTitle = headlineTitle;
        this.jobTag = (jobTag != null && !jobTag.isBlank()) ? jobTag : "Hiring";
        this.content = content;
        this.imagePath = imagePath;
        this.timestamp = (timestamp != null && !timestamp.isBlank()) ? timestamp : "Just now";
        this.likes = likes;
        this.comments = new ArrayList<>();
    }

    public String getUserName() { return userName; }
    public String getRole() { return role; }
    public String getHeadlineTitle() { return headlineTitle; }
    public String getJobTag() { return jobTag; }
    public String getContent() { return content; }
    public String getImagePath() { return imagePath; }
    public String getTimestamp() { return timestamp; }
    public int getLikes() { return likes; }
    public boolean isLiked() { return liked; }

    public void toggleLike() {
        liked = !liked;
        if (liked) {
            likes++;
        } else {
            likes--;
        }
    }

    public List<String> getComments() { return comments; }

    public void addComment(String comment) {
        if (comment != null && !comment.trim().isEmpty()) {
            comments.add(comment.trim());
        }
    }
}