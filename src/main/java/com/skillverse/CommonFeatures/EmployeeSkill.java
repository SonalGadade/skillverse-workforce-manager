package com.skillverse.CommonFeatures;

public class EmployeeSkill {
    private String skillId;
    private String employeeEmail;
    private String skillName;
    private String currentLevel;       
    private int proficiencyPercentage; 
    private String requiredLevel;      
    private int gapPercentage;          
    private String recommendedCourseId;
    private String recommendedCourseName;

    public EmployeeSkill() {}

    public EmployeeSkill(String skillId, String employeeEmail, String skillName, String currentLevel, int proficiencyPercentage, String requiredLevel, int gapPercentage, String recommendedCourseId, String recommendedCourseName) {
        this.skillId = skillId;
        this.employeeEmail = employeeEmail;
        this.skillName = skillName;
        this.currentLevel = currentLevel;
        this.proficiencyPercentage = proficiencyPercentage;
        this.requiredLevel = requiredLevel;
        this.gapPercentage = gapPercentage;
        this.recommendedCourseId = recommendedCourseId;
        this.recommendedCourseName = recommendedCourseName;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getProficiencyPercentage() {
        return proficiencyPercentage;
    }

    public void setProficiencyPercentage(int proficiencyPercentage) {
        this.proficiencyPercentage = proficiencyPercentage;
    }

    public String getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(String requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public int getGapPercentage() {
        return gapPercentage;
    }

    public void setGapPercentage(int gapPercentage) {
        this.gapPercentage = gapPercentage;
    }

    public String getRecommendedCourseId() {
        return recommendedCourseId;
    }

    public void setRecommendedCourseId(String recommendedCourseId) {
        this.recommendedCourseId = recommendedCourseId;
    }

    public String getRecommendedCourseName() {
        return recommendedCourseName;
    }

    public void setRecommendedCourseName(String recommendedCourseName) {
        this.recommendedCourseName = recommendedCourseName;
    }
}
