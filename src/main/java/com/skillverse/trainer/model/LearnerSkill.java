package com.skillverse.trainer.model;

public class LearnerSkill {

    private int id;
    private String skillName;
    private String proficiencyLevel;
    private int learnerCount;
    private String description;

    public LearnerSkill(
            int id,
            String skillName,
            String proficiencyLevel,
            int learnerCount,
            String description) {

        this.id = id;
        this.skillName = skillName;
        this.proficiencyLevel = proficiencyLevel;
        this.learnerCount = learnerCount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public int getLearnerCount() {
        return learnerCount;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public void setLearnerCount(int learnerCount) {
        this.learnerCount = learnerCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LearnerSkill{" +
                "id=" + id +
                ", skillName='" + skillName + '\'' +
                ", proficiencyLevel='" + proficiencyLevel + '\'' +
                ", learnerCount=" + learnerCount +
                '}';
    }
}
