package com.skillverse.trainer.controller;

import com.skillverse.trainer.model.DataStore;
import com.skillverse.trainer.model.Trainer;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class RegisterController {

    private final Stage stage;
    private final DataStore dataStore;

    public RegisterController(Stage stage) {
        this.stage = stage;
        this.dataStore = DataStore.getInstance();
    }

    public Trainer register(
            String fullName,
            String email,
            String password,
            String confirmPassword,
            String phone,
            String address,
            String gender) {

       
        if (isBlank(fullName)
                || isBlank(email)
                || isBlank(password)
                || isBlank(confirmPassword)) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Please fill all required fields."
            );

            return null;
        }

        if (!password.equals(confirmPassword)) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Passwords do not match."
            );

            return null;
        }

     
        if (!isValidEmail(email)) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Please enter a valid email address."
            );

            return null;
        }

  
        if (dataStore.emailExists(email.trim())) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "An account with this email already exists."
            );

            return null;
        }

        Trainer trainer =
                new Trainer(
                        fullName.trim(),
                        email.trim(),
                        password,
                        phone == null ? "" : phone.trim(),
                        address == null ? "" : address.trim(),
                        gender == null ? "" : gender
                );

        dataStore.addTrainer(trainer);

        showAlert(
                Alert.AlertType.INFORMATION,
                "Trainer account created successfully."
        );

        return trainer;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private boolean isValidEmail(String email) {

        return email != null
                && email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }

    private void showAlert(
            Alert.AlertType type,
            String message) {

        new Alert(type, message).showAndWait();
    }
}
