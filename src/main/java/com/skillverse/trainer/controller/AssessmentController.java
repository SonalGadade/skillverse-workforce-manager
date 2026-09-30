package com.skillverse.trainer.controller;

import java.util.List;

import com.skillverse.trainer.model.Assessment;
import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.Trainer;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AssessmentController {

    private final Stage stage;
    private final Trainer trainer;
    private final DataStore dataStore;
    private final NavigationController navigation;

    public AssessmentController(
            Stage stage,
            Trainer trainer) {

        this.stage = stage;
        this.trainer = trainer;
        this.dataStore = DataStore.getInstance();
        this.navigation =
                new NavigationController(stage, trainer);
    }

    public List<Assessment> getAssessments() {
        return dataStore.getAssessments();
    }
    public boolean createAssessment(
            String title,
            String courseName,
            int totalMarks,
            String description) {

        if (title == null || title.isBlank()) {
            showError("Assessment title is required.");
            return false;
        }

        if (courseName == null || courseName.isBlank()) {
            showError("Course name is required.");
            return false;
        }

        if (totalMarks <= 0) {
            showError("Total marks must be greater than zero.");
            return false;
        }

        Assessment assessment =
                new Assessment(
                        getNextId(),
                        title.trim(),
                        courseName.trim(),
                        0,
                        totalMarks,
                        description == null
                                ? ""
                                : description.trim(),
                        "Active"
                );

        dataStore.addAssessment(assessment);

        return true;
    }

 

    public void goToDashboard() {
        navigation.goToDashboard();
    }

    private int getNextId() {

        int maxId = 0;

        for (Assessment assessment :
                dataStore.getAssessments()) {

            if (assessment.getId() > maxId) {
                maxId = assessment.getId();
            }
        }

        return maxId + 1;
    }

    private void showError(String message) {

        new Alert(
                Alert.AlertType.WARNING,
                message
        ).showAndWait();
    }
}
