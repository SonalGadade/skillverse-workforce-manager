package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.Announcement;
import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.Trainer;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class CommunicationController {

    private final Stage stage;
    private final Trainer trainer;
    private final DataStore dataStore;
    private final NavigationController navigation;

    public CommunicationController(
            Stage stage,
            Trainer trainer) {

        this.stage = stage;
        this.trainer = trainer;
        this.dataStore = DataStore.getInstance();
        this.navigation =
                new NavigationController(stage, trainer);
    }

    public List<Announcement> getAnnouncements() {
        return dataStore.getAnnouncements();
    }

    public boolean createAnnouncement(
            String title,
            String message) {

        if (title == null || title.isBlank()) {
            showError("Announcement title is required.");
            return false;
        }

        if (message == null || message.isBlank()) {
            showError("Announcement message is required.");
            return false;
        }

        Announcement announcement =
                new Announcement(
                        getNextId(),
                        title.trim(),
                        message.trim(),
                        LocalDate.now().toString(),
                        false
                );

        dataStore.addAnnouncement(announcement);

        return true;
    }

    public void markAsRead(Announcement announcement) {

        if (announcement != null) {
            announcement.setRead(true);
        }
    }

    public void goToDashboard() {
        navigation.goToDashboard();
    }

    private int getNextId() {

        int maxId = 0;

        for (Announcement announcement :
                dataStore.getAnnouncements()) {

            if (announcement.getId() > maxId) {
                maxId = announcement.getId();
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
