package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.Course;
import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.Trainer;

import java.util.List;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class CourseController {

    private final Stage stage;
    private final Trainer trainer;
    private final DataStore dataStore;
    private final NavigationController navigation;

    public CourseController(
            Stage stage,
            Trainer trainer) {

        this.stage = stage;
        this.trainer = trainer;
        this.dataStore = DataStore.getInstance();
        this.navigation =
                new NavigationController(stage, trainer);
    }

    public List<Course> getCourses() {
        return dataStore.getCourses();
    }

    public boolean createCourse(
            String title,
            String description,
            String level,
            int learnerCount,
            String duration) {

        if (title == null || title.isBlank()) {
            showError("Course title is required.");
            return false;
        }

        if (level == null || level.isBlank()) {
            showError("Course level is required.");
            return false;
        }

        if (learnerCount < 0) {
            showError("Learner count cannot be negative.");
            return false;
        }

        Course course =
                new Course(
                        getNextId(),
                        title.trim(),
                        description == null ? "" : description.trim(),
                        level.trim(),
                        learnerCount,
                        duration == null ? "" : duration.trim(),
                        "Active"
                );

        dataStore.addCourse(course);

        return true;
    }

    public void goToDashboard() {
        navigation.goToDashboard();
    }

    private int getNextId() {

        int maxId = 0;

        for (Course course : dataStore.getCourses()) {
            if (course.getId() > maxId) {
                maxId = course.getId();
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
