package com.skillverse.CommonFeatures;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MasterSkill {
    private String skillId;
    private String skillName;
    private String category;
    private String description;
    private String createdAt;

    public MasterSkill() {}

    public MasterSkill(String skillId, String skillName, String category, String description, String createdAt) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.category = category;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getSkillId() { return skillId; }
    public void setSkillId(String skillId) { this.skillId = skillId; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
