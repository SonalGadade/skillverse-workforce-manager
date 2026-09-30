package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.Announcement;
import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.PerformanceSummary;
import com.skillverse.trainer.model.Trainer;

import java.util.List;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class DashboardController {

    private final Stage stage;
    private final Trainer trainer;
    private final DataStore dataStore;
    private final NavigationController navigation;

    public DashboardController(
            Stage stage,
            Trainer trainer) {

        this.stage = stage;
        this.trainer = trainer;
        this.dataStore = DataStore.getInstance();
        this.navigation =
                new NavigationController(stage, trainer);
    }

    public int getActiveCourseCount() {
        return dataStore.getActiveCourseCount();
    }

    public int getLearnerCount() {
        return dataStore.getLearnerCount();
    }

    public int getAssessmentCount() {
        return dataStore.getAssessmentCount();
    }

    public int getSeminarCount() {
        return dataStore.getSeminarCount();
    }

    public PerformanceSummary getPerformanceSummary() {
        return dataStore.getPerformanceSummary();
    }

    public List<Announcement> getAnnouncements() {
        return dataStore.getAnnouncements();
    }

    public void openCourses() {
        navigation.goToCourses();
    }

    public void openLearners() {
        navigation.goToLearners();
    }

    public void openSeminars() {
        navigation.goToSeminars();
    }

    public void openAssessments() {
        navigation.goToAssessments();
    }

    public void openPerformance() {
        navigation.goToPerformance();
    }

    public void openSkills() {
        navigation.goToLearnerSkills();
    }

    public void showNotifications() {

        List<Announcement> announcements =
                dataStore.getAnnouncements();

        StringBuilder text =
                new StringBuilder();

        for (Announcement announcement : announcements) {

            text.append("• ")
                    .append(announcement.getMessage())
                    .append("\n");
        }

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle("Notifications");
        alert.setHeaderText("Trainer Notifications");
        alert.setContentText(text.toString());

        alert.showAndWait();
    }
}
