package com.skillverse.manager.controller;

import java.util.ArrayList;
import java.util.List;
import com.skillverse.manager.model.GoalsKPIModel;
import com.skillverse.manager.view.GoalsKPIs;
import javafx.scene.Scene;

public class GoalsKPIController {

    private GoalsKPIs goalsView;

    private List<GoalsKPIModel> goalList;

    private Runnable callBackActionDashboard;

    public GoalsKPIController() {

        goalsView = new GoalsKPIs();

        goalList = new ArrayList<>();

        loadGoals();
    }

    public GoalsKPIController(List<GoalsKPIModel> goalList) {

        goalsView = new GoalsKPIs();

        this.goalList = goalList;
    }

    private void loadGoals() {

        goalList.add(new GoalsKPIModel("Complete Payment Module", "Priya Sharma", 0.85, "85%"));

        goalList.add(new GoalsKPIModel("Improve API Performance", "Rahul Verma", 0.70, "70%"));

        goalList.add(new GoalsKPIModel("Launch New Product Design", "Sneha Joshi", 0.92, "92%"));

        goalList.add(new GoalsKPIModel("Complete Security Audit", "Amit Kumar", 0.55, "55%"));
    }

    public Scene showGoalsKPIs(Runnable callBackActionDashboard) {

        this.callBackActionDashboard = callBackActionDashboard;

        return goalsView.getGoalsKPIsScene(goalList, () -> navigateToDashboard());
    }

    private void navigateToDashboard() {

        if (callBackActionDashboard != null) {

            callBackActionDashboard.run();
        }
    }

    public List<GoalsKPIModel> getGoalList() {

        return goalList;
    }

    public void addGoal(GoalsKPIModel goal) {

        if (goal != null) {

            goalList.add(goal);
        }
    }

    public void reviewGoal(GoalsKPIModel goal) {

        if (goal == null) {

            return;
        }

        System.out.println("Reviewing Goal: " + goal.getGoal());

        System.out.println("Assigned to: " + goal.getEmployee());

        System.out.println("Progress: " + goal.getPercentage());
    }
}