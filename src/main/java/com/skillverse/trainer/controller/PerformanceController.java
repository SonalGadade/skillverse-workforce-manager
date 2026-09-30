package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.PerformanceSummary;
import com.skillverse.trainer.model.Trainer;

import javafx.stage.Stage;

public class PerformanceController {

    private final Stage stage;
    private final Trainer trainer;
    private final DataStore dataStore;
    private final NavigationController navigation;

    public PerformanceController(
            Stage stage,
            Trainer trainer) {

        this.stage = stage;
        this.trainer = trainer;
        this.dataStore = DataStore.getInstance();
        this.navigation =
                new NavigationController(stage, trainer);
    }


    public PerformanceSummary getPerformanceSummary() {
        return dataStore.getPerformanceSummary();
    }

    public double getAverageCourseCompletion() {

        return dataStore
                .getPerformanceSummary()
                .getAverageCourseCompletion();
    }

    public double getAverageAssessmentScore() {

        return dataStore
                .getPerformanceSummary()
                .getAverageAssessmentScore();
    }

    public int getLearnersNeedingSupport() {

        return dataStore
                .getPerformanceSummary()
                .getLearnersNeedingSupport();
    }

    public int getAdvancedModuleLearners() {

        return dataStore
                .getPerformanceSummary()
                .getAdvancedModuleLearners();
    }

    public void goToDashboard() {
        navigation.goToDashboard();
    }
}
