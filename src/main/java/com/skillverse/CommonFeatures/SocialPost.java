package com.skillverse.CommonFeatures;

import java.util.ArrayList;
import java.util.List;

public class SocialPost {
    private String postId;
    private String authorName;
    private String authorEmail;
    private String authorRole;
    private String authorAvatarUrl;
    private String contentText;
    private String imageUrl;
    private long timestamp;
    private List<String> likedByEmails = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    public SocialPost() {}

    public SocialPost(String postId, String authorName, String authorEmail, String authorRole, String authorAvatarUrl, String contentText, String imageUrl) {
        this.postId = postId;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authorRole = authorRole;
        this.authorAvatarUrl = authorAvatarUrl;
        this.contentText = contentText;
        this.imageUrl = imageUrl;
        this.timestamp = System.currentTimeMillis();
    }

    public static class Comment {
        private String authorName;
        private String authorEmail;
        private String commentText;
        private long timestamp;

        public Comment() {}

        public Comment(String authorName, String authorEmail, String commentText) {
            this.authorName = authorName;
            this.authorEmail = authorEmail;
            this.commentText = commentText;
            this.timestamp = System.currentTimeMillis();
        }

        public String getAuthorName() { return authorName; }
        public void setAuthorName(String authorName) { this.authorName = authorName; }
        public String getAuthorEmail() { return authorEmail; }
        public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }
        public String getCommentText() { return commentText; }
        public void setCommentText(String commentText) { this.commentText = commentText; }
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }
    public String getAuthorRole() { return authorRole; }
    public void setAuthorRole(String authorRole) { this.authorRole = authorRole; }
    public String getAuthorAvatarUrl() { return authorAvatarUrl; }
    public void setAuthorAvatarUrl(String authorAvatarUrl) { this.authorAvatarUrl = authorAvatarUrl; }
    public String getContentText() { return contentText; }
    public void setContentText(String contentText) { this.contentText = contentText; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public List<String> getLikedByEmails() { return likedByEmails; }
    public void setLikedByEmails(List<String> likedByEmails) { this.likedByEmails = likedByEmails; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}
