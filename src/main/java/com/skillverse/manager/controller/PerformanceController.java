package com.skillverse.manager.controller;

import com.skillverse.manager.model.PerformanceModel;
import com.skillverse.manager.view.Performance;
import javafx.scene.Scene;

public class PerformanceController {

    private PerformanceModel performanceModel;

    private Performance performanceView;

    public PerformanceController() {

        performanceModel = new PerformanceModel();

        performanceView = new Performance();
    }

    public Scene getPerformanceScene(Runnable callBackActionDashboard) {

        Scene scene = performanceView.getPerformanceScene();

        performanceView.getBackButton().setOnAction(event -> {
            callBackActionDashboard.run();
        });

        return scene;
    }

    public PerformanceModel getPerformanceModel() {

        return performanceModel;
    }

    public void updatePerformanceStatistics(String averagePerformance, String aboveTarget, String needsAttention) {

        performanceModel.updatePerformanceStatistics(averagePerformance, aboveTarget, needsAttention);
    }

    public void updateEmployeePerformance(String employeeName, int performance) {

        performanceModel.updateEmployeePerformance(employeeName, performance);
    }

    public Performance getPerformanceView() {

        return performanceView;
    }
}