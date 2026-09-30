package com.skillverse.CommonFeatures;

import java.util.Date;

public class PostComment {
    private String commentId;
    private String postId;
    private String authorName;
    private String authorEmail;
    private String authorRole;
    private String text;
    private Date createdAt;

    public PostComment() {}

    public PostComment(String commentId, String postId, String authorName, String authorEmail, String authorRole, String text) {
        this.commentId = commentId;
        this.postId = postId;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authorRole = authorRole != null ? authorRole : "Employee";
        this.text = text;
        this.createdAt = new Date();
    }

    public String getCommentId() { return commentId; }
    public void setCommentId(String commentId) { this.commentId = commentId; }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public String getAuthorRole() { return authorRole; }
    public void setAuthorRole(String authorRole) { this.authorRole = authorRole; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
