package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.Trainer;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ProfileController {

    private final Stage stage;
    private final Trainer trainer;
    private final NavigationController navigation;

    public ProfileController(
            Stage stage,
            Trainer trainer) {

        this.stage = stage;
        this.trainer = trainer;
        this.navigation =
                new NavigationController(stage, trainer);
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public String getFullName() {
        return trainer.getFullName();
    }

    public String getEmail() {
        return trainer.getEmail();
    }

    public String getPhone() {
        return trainer.getPhone();
    }

    public String getAddress() {
        return trainer.getAddress();
    }

    public String getGender() {
        return trainer.getGender();
    }

    public boolean saveProfile(
            String fullName,
            String email,
            String phone,
            String address) {

        if (fullName == null || fullName.isBlank()) {

            showError("Full name is required.");
            return false;
        }

        if (email == null || email.isBlank()) {

            showError("Email is required.");
            return false;
        }

        if (!isValidEmail(email)) {

            showError("Please enter a valid email.");
            return false;
        }

        if (phone == null || phone.isBlank()) {

            showError("Phone number is required.");
            return false;
        }

        trainer.setFullName(fullName.trim());
        trainer.setEmail(email.trim());
        trainer.setPhone(phone.trim());
        trainer.setAddress(
                address == null ? "" : address.trim()
        );

        showSuccess(
                "Profile updated successfully."
        );

        return true;
    }

    public void goToDashboard() {
        navigation.goToDashboard();
    }

    private boolean isValidEmail(String email) {

        return email != null
                && email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }

    private void showError(String message) {

        new Alert(
                Alert.AlertType.WARNING,
                message
        ).showAndWait();
    }

    private void showSuccess(String message) {

        new Alert(
                Alert.AlertType.INFORMATION,
                message
        ).showAndWait();
    }
}

