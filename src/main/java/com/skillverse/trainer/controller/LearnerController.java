package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.Learner;
import com.skillverse.trainer.model.Trainer;

import java.util.List;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LearnerController {

    private final Stage stage;
    private final Trainer trainer;
    private final DataStore dataStore;
    private final NavigationController navigation;

    public LearnerController(
            Stage stage,
            Trainer trainer) {

        this.stage = stage;
        this.trainer = trainer;
        this.dataStore = DataStore.getInstance();
        this.navigation =
                new NavigationController(stage, trainer);
    }

    public List<Learner> getLearners() {
        return dataStore.getLearners();
    }

    public boolean addLearner(
            String fullName,
            String email,
            String courseName,
            int completion,
            int assessmentScore,
            String status) {

        if (fullName == null || fullName.isBlank()) {
            showError("Learner name is required.");
            return false;
        }

        if (email == null || email.isBlank()) {
            showError("Learner email is required.");
            return false;
        }

        if (completion < 0 || completion > 100) {
            showError("Course completion must be between 0 and 100.");
            return false;
        }

        if (assessmentScore < 0 || assessmentScore > 100) {
            showError("Assessment score must be between 0 and 100.");
            return false;
        }

        Learner learner =
                new Learner(
                        getNextId(),
                        fullName.trim(),
                        email.trim(),
                        courseName == null ? "" : courseName.trim(),
                        completion,
                        assessmentScore,
                        status == null ? "On Track" : status
                );

        dataStore.addLearner(learner);

        return true;
    }

    public void goToDashboard() {
        navigation.goToDashboard();
    }

    private int getNextId() {

        int maxId = 0;

        for (Learner learner : dataStore.getLearners()) {
            if (learner.getId() > maxId) {
                maxId = learner.getId();
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
