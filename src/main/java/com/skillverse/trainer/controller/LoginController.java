package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.Trainer;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginController {

    private final Stage stage;
    private final DataStore dataStore;

    public LoginController(Stage stage) {
        this.stage = stage;
        this.dataStore = DataStore.getInstance();
    }


    public Trainer login(String email, String password) {

        if (email == null || email.isBlank()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Please enter your email."
            );
            return null;
        }

        if (password == null || password.isBlank()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Please enter your password."
            );
            return null;
        }

        Trainer trainer =
                dataStore.authenticate(
                        email.trim(),
                        password
                );

        if (trainer == null) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid email or password."
            );

            return null;
        }

        return trainer;
    }

    public void forgotPassword() {

        showAlert(
                Alert.AlertType.INFORMATION,
                "Password reset / OTP verification can be connected here."
        );
    }

    public void googleLogin() {

        showAlert(
                Alert.AlertType.INFORMATION,
                "Google login UI is ready. Connect Google authentication here."
        );
    }

    private void showAlert(
            Alert.AlertType type,
            String message) {

        new Alert(type, message).showAndWait();
    }
}
