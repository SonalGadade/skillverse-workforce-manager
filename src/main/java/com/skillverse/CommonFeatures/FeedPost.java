package com.skillverse.CommonFeatures;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedPost {
    private String id;
    private String authorName;
    private String authorEmail;
    private String authorRole;
    private String authorDepartment;
    private String content;
    private String postType; 
    private String imageUrl;
    private int likesCount;
    private int commentsCount;
    private List<String> likedBy = new ArrayList<>();
    private Date createdAt;

    public FeedPost() {}

    public FeedPost(String id, String authorName, String authorEmail, String authorRole, String authorDepartment, String content, String postType, String imageUrl) {
        this.id = id;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authorRole = authorRole;
        this.authorDepartment = authorDepartment;
        this.content = content;
        this.postType = postType != null ? postType : "GENERAL";
        this.imageUrl = imageUrl;
        this.likesCount = 0;
        this.commentsCount = 0;
        this.createdAt = new Date();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public String getAuthorRole() { return authorRole; }
    public void setAuthorRole(String authorRole) { this.authorRole = authorRole; }

    public String getAuthorDepartment() { return authorDepartment; }
    public void setAuthorDepartment(String authorDepartment) { this.authorDepartment = authorDepartment; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }

    public int getCommentsCount() { return commentsCount; }
    public void setCommentsCount(int commentsCount) { this.commentsCount = commentsCount; }

    public List<String> getLikedBy() { return likedBy; }
    public void setLikedBy(List<String> likedBy) { this.likedBy = likedBy; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
