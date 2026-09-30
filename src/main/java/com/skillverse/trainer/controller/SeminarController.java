package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.Seminar;
import com.skillverse.trainer.model.Trainer;

import java.util.List;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SeminarController {

    private final Stage stage;
    private final Trainer trainer;
    private final DataStore dataStore;
    private final NavigationController navigation;

    public SeminarController(
            Stage stage,
            Trainer trainer) {

        this.stage = stage;
        this.trainer = trainer;
        this.dataStore = DataStore.getInstance();
        this.navigation =
                new NavigationController(stage, trainer);
    }


    public List<Seminar> getSeminars() {
        return dataStore.getSeminars();
    }

    public boolean scheduleSeminar(
            String title,
            String date,
            String time,
            String description,
            int attendeeCount) {

        if (title == null || title.isBlank()) {
            showError("Seminar title is required.");
            return false;
        }

        if (date == null || date.isBlank()) {
            showError("Seminar date is required.");
            return false;
        }

        if (time == null || time.isBlank()) {
            showError("Seminar time is required.");
            return false;
        }

        if (attendeeCount < 0) {
            showError("Attendee count cannot be negative.");
            return false;
        }

        Seminar seminar =
                new Seminar(
                        getNextId(),
                        title.trim(),
                        date.trim(),
                        time.trim(),
                        description == null
                                ? ""
                                : description.trim(),
                        attendeeCount,
                        "Scheduled"
                );

        dataStore.addSeminar(seminar);

        return true;
    }

    public void goToDashboard() {
        navigation.goToDashboard();
    }

    private int getNextId() {

        int maxId = 0;

        for (Seminar seminar : dataStore.getSeminars()) {
            if (seminar.getId() > maxId) {
                maxId = seminar.getId();
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
