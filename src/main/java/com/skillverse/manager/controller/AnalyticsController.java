package com.skillverse.manager.controller;

import com.skillverse.manager.model.AnalyticsModel;
import com.skillverse.manager.view.Analytics;

import javafx.scene.Scene;

public class AnalyticsController {

    private Analytics analyticsView;
    private AnalyticsModel analyticsModel;

    public AnalyticsController() {
        analyticsView = new Analytics();
        analyticsModel = new AnalyticsModel();
    }

    public AnalyticsController(AnalyticsModel analyticsModel) {
        analyticsView = new Analytics();
        this.analyticsModel = analyticsModel;
    }

    public Scene getAnalyticsScene(Runnable callBackActionDashboard) {
        return analyticsView.getAnalyticsScene(() -> navigateToDashboard(callBackActionDashboard));
    }

    private void navigateToDashboard(Runnable callBackActionDashboard) {
        if (callBackActionDashboard != null) {
            callBackActionDashboard.run();
        }
    }

    public AnalyticsModel getAnalyticsModel() {
        return analyticsModel;
    }

    public void setTeamPerformance(String teamPerformance) {
        analyticsModel.setTeamPerformance(teamPerformance);
    }

    public void setTrainingCompletion(String trainingCompletion) {
        analyticsModel.setTrainingCompletion(trainingCompletion);
    }

    public void setGoalCompletion(String goalCompletion) {
        analyticsModel.setGoalCompletion(goalCompletion);
    }

    public void setPromotionReady(int promotionReady) {
        analyticsModel.setPromotionReady(promotionReady);
    }

    public void setProductivity(String productivity) {
        analyticsModel.setProductivity(productivity);
    }

    public void setLearning(String learning) {
        analyticsModel.setLearning(learning);
    }

    public void setGoalAchievement(String goalAchievement) {
        analyticsModel.setGoalAchievement(goalAchievement);
    }

    public void setEngagement(String engagement) {
        analyticsModel.setEngagement(engagement);
    }

    public void setJavaSkill(double javaSkill) {
        analyticsModel.setJavaSkill(javaSkill);
    }

    public void setCloudSkill(double cloudSkill) {
        analyticsModel.setCloudSkill(cloudSkill);
    }

    public void setAiMlSkill(double aiMlSkill) {
        analyticsModel.setAiMlSkill(aiMlSkill);
    }

    public void setOtherSkill(double otherSkill) {
        analyticsModel.setOtherSkill(otherSkill);
    }
}