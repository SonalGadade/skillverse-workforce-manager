package com.skillverse.manager.model;

public class AnalyticsModel {

    private String teamPerformance;
    private String trainingCompletion;
    private String goalCompletion;
    private int promotionReady;

    private String productivity;
    private String learning;
    private String goalAchievement;
    private String engagement;

    private double javaSkill;
    private double cloudSkill;
    private double aiMlSkill;
    private double otherSkill;

    public AnalyticsModel() {
        teamPerformance = "88%";
        trainingCompletion = "76%";
        goalCompletion = "79%";
        promotionReady = 5;

        productivity = "92%";
        learning = "76%";
        goalAchievement = "88%";
        engagement = "84%";

        javaSkill = 35;
        cloudSkill = 25;
        aiMlSkill = 20;
        otherSkill = 20;
    }

    public String getTeamPerformance() {
        return teamPerformance;
    }

    public void setTeamPerformance(String teamPerformance) {
        this.teamPerformance = teamPerformance;
    }

    public String getTrainingCompletion() {
        return trainingCompletion;
    }

    public void setTrainingCompletion(String trainingCompletion) {
        this.trainingCompletion = trainingCompletion;
    }

    public String getGoalCompletion() {
        return goalCompletion;
    }

    public void setGoalCompletion(String goalCompletion) {
        this.goalCompletion = goalCompletion;
    }

    public int getPromotionReady() {
        return promotionReady;
    }

    public void setPromotionReady(int promotionReady) {
        this.promotionReady = promotionReady;
    }

    public String getProductivity() {
        return productivity;
    }

    public void setProductivity(String productivity) {
        this.productivity = productivity;
    }

    public String getLearning() {
        return learning;
    }

    public void setLearning(String learning) {
        this.learning = learning;
    }

    public String getGoalAchievement() {
        return goalAchievement;
    }

    public void setGoalAchievement(String goalAchievement) {
        this.goalAchievement = goalAchievement;
    }

    public String getEngagement() {
        return engagement;
    }

    public void setEngagement(String engagement) {
        this.engagement = engagement;
    }

    public double getJavaSkill() {
        return javaSkill;
    }

    public void setJavaSkill(double javaSkill) {
        this.javaSkill = javaSkill;
    }

    public double getCloudSkill() {
        return cloudSkill;
    }

    public void setCloudSkill(double cloudSkill) {
        this.cloudSkill = cloudSkill;
    }

    public double getAiMlSkill() {
        return aiMlSkill;
    }

    public void setAiMlSkill(double aiMlSkill) {
        this.aiMlSkill = aiMlSkill;
    }

    public double getOtherSkill() {
        return otherSkill;
    }

    public void setOtherSkill(double otherSkill) {
        this.otherSkill = otherSkill;
    }
}