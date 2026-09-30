package com.skillverse.admin.controller;

import java.util.Optional;

import com.skillverse.admin.view.SettingsView;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class SettingsController {

    private final Stage stage;
    private final SettingsView view;

    public SettingsController(
        Stage stage,
        SettingsView view
    ) {

        this.stage = stage;
        this.view = view;
    }

   

        public ScrollPane initialize() {

            ScrollPane scrollPane = view.createScrollPane();

            loadSettings();

            setupActions();

            return scrollPane;
}
  

    private void loadSettings() {

        view.getAdminNameField()
            .setText("Admin");

        view.getOrganizationField()
            .setText("Employee Management System");

        view.getEmailField()
            .setText("admin@gmail.com");

        view.getTimezoneBox()
            .setValue("Asia/Kolkata");

        view.getLanguageBox()
            .setValue("English");

        view.getThemeBox()
            .setValue("Light");

        view.getItemsPerPageBox()
            .setValue("20");
    }

   

    private void setupActions() {

        
        view.getSaveButton()
            .setOnAction(
                e -> saveSettings()
            );

        view.getChangePasswordButton()
            .setOnAction(
                e -> changePassword()
            );

    
        view.getLoginActivityButton()
            .setOnAction(
                e -> showLoginActivity()
            );

        view.getLogoutDevicesButton()
            .setOnAction(
                e -> logoutAllDevices()
            );

        view.getResetButton()
            .setOnAction(
                e -> resetSettings()
            );
    }


    private void saveSettings() {

        String name =
            view.getAdminNameField()
                .getText();

        String organization =
            view.getOrganizationField()
                .getText();

        String email =
            view.getEmailField()
                .getText();

        if (
            name == null ||
            name.trim().isEmpty()
        ) {

            showAlert(
                "Validation",
                "Please enter Admin Name."
            );

            return;
        }

        if (
            email == null ||
            email.trim().isEmpty()
        ) {

            showAlert(
                "Validation",
                "Please enter Email Address."
            );

            return;
        }

       

        view.getStatusLabel()
            .setText(
                "✓ Settings saved successfully."
            );
    }

    private void changePassword() {

        TextInputDialog dialog =
            new TextInputDialog();

        dialog.setTitle(
            "Change Password"
        );

        dialog.setHeaderText(
            "Enter your new password"
        );

        dialog.setContentText(
            "New Password:"
        );

        Optional<String> result =
            dialog.showAndWait();

        if (
            result.isPresent() &&
            !result.get()
                .trim()
                .isEmpty()
        ) {

            showAlert(
                "Password",
                "Password changed successfully."
            );
        }
    }

   

    private void showLoginActivity() {

        showAlert(
            "Login Activity",
            "Recent Login\n\n" +
            "Admin • Today • 10:32 AM\n" +
            "Windows • Chrome\n\n" +
            "Admin • Yesterday • 08:45 PM"
        );
    }

    

    private void logoutAllDevices() {

        Alert alert =
            new Alert(
                Alert.AlertType.CONFIRMATION
            );

        alert.setTitle(
            "Logout From All Devices"
        );

        alert.setHeaderText(
            "Are you sure?"
        );

        alert.setContentText(
            "All other active sessions will be logged out."
        );

        Optional<javafx.scene.control.ButtonType>
            result =
            alert.showAndWait();

        if (
            result.isPresent() &&
            result.get()
                == javafx.scene.control.ButtonType.OK
        ) {

            showAlert(
                "Security",
                "All other devices have been logged out."
            );
        }
    }


    private void resetSettings() {

        Alert alert =
            new Alert(
                Alert.AlertType.CONFIRMATION
            );

        alert.setTitle(
            "Reset Settings"
        );

        alert.setHeaderText(
            "Reset all settings?"
        );

        alert.setContentText(
            "Your settings will return to default values."
        );

        Optional<javafx.scene.control.ButtonType>
            result =
            alert.showAndWait();

        if (
            result.isPresent() &&
            result.get()
                == javafx.scene.control.ButtonType.OK
        ) {

            loadSettings();

            view.getThemeBox()
                .setValue("Light");

            view.getCompactDashboard()
                .setSelected(false);

            view.getEmailNotification()
                .setSelected(true);

            view.getEmployeeAlert()
                .setSelected(true);

            view.getJobAlert()
                .setSelected(true);

            view.getApprovalAlert()
                .setSelected(true);

            view.getSystemAlert()
                .setSelected(true);

            view.getTwoFactor()
                .setSelected(true);

            view.getAutoRefresh()
                .setSelected(true);

            view.getStatusLabel()
                .setText(
                    "✓ Settings restored to default."
                );
        }
    }

  

    private void showAlert(
        String title,
        String message
    ) {

        Alert alert =
            new Alert(
                Alert.AlertType.INFORMATION
            );

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }
}
