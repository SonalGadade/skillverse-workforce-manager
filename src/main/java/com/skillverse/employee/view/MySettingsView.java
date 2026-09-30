package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MySettingsView {

    private static final String BLUE = "#1648C8";
    private static final String PURPLE = "#7C3AED";
    private static final String DARK = "#111827";
    private static final String TEXT = "#374151";
    private static final String MUTED = "#6B7280";
    private static final String BG = "#F8F8FD";
    private static final String BORDER = "#E7E8F0";

    public VBox createSettingsContent(String email) {

        VBox page = new VBox(25);

        page.setPadding(
                new Insets(38, 42, 45, 42)
        );

        page.setStyle(
                "-fx-background-color: " + BG + ";"
        );

        HBox header = new HBox();

        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(5);

        Label small = new Label("ACCOUNT SETTINGS");

        small.setStyle(
                "-fx-text-fill: " + PURPLE + ";"
                + "-fx-font-size: 12px;"
                + "-fx-font-weight: bold;"
        );

        Label title = new Label("Settings");

        title.setStyle(
                "-fx-text-fill: " + DARK + ";"
                + "-fx-font-size: 32px;"
                + "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Manage your account preferences and application settings."
        );

        subtitle.setStyle(
                "-fx-text-fill: " + TEXT + ";"
                + "-fx-font-size: 15px;"
        );

        titleBox.getChildren().addAll(
                small,
                title,
                subtitle
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label emailLabel = new Label(
                email == null ? "Employee" : email
        );

        emailLabel.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: bold;"
        );

        HBox userBox = new HBox(emailLabel);

        userBox.setPadding(
                new Insets(10, 15, 10, 15)
        );

        userBox.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 12;"
        );

        header.getChildren().addAll(
                titleBox,
                spacer,
                userBox
        );

        VBox accountCard = createCard();

        Label accountTitle = new Label(
                "Account Settings"
        );

        accountTitle.setStyle(
                "-fx-text-fill: " + DARK + ";"
                + "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
        );

        Label accountSub = new Label(
                "Manage your personal account information."
        );

        accountSub.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 13px;"
        );

        VBox accountHeading = new VBox(4);

        accountHeading.getChildren().addAll(
                accountTitle,
                accountSub
        );

        // EMAIL
        Label emailText = new Label("Email Address");

        emailText.setStyle(
                "-fx-text-fill: " + TEXT + ";"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: bold;"
        );

        TextField emailField = new TextField(
                email == null ? "" : email
        );

        emailField.setPrefHeight(42);

        emailField.setEditable(false);

        emailField.setStyle(
                "-fx-background-color: #F4F5FA;"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 10;"
                + "-fx-padding: 0 12;"
        );

        VBox emailBox = new VBox(7);

        emailBox.getChildren().addAll(
                emailText,
                emailField
        );

        // PASSWORD
        Label passwordText = new Label(
                "Password"
        );

        passwordText.setStyle(
                "-fx-text-fill: " + TEXT + ";"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: bold;"
        );

        PasswordField passwordField
                = new PasswordField();

        passwordField.setPromptText(
                "••••••••"
        );

        passwordField.setPrefHeight(42);

        passwordField.setStyle(
                "-fx-background-color: #F9FAFC;"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 10;"
        );

        Button changePassword
                = new Button("Change Password");

        changePassword.setPrefHeight(40);

        changePassword.setStyle(
                "-fx-background-color: #EEF3FF;"
                + "-fx-text-fill: " + BLUE + ";"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 9;"
        );

        HBox passwordRow = new HBox(12);

        passwordRow.setAlignment(
                Pos.CENTER_LEFT
        );

        HBox.setHgrow(
                passwordField,
                Priority.ALWAYS
        );

        passwordRow.getChildren().addAll(
                passwordField,
                changePassword
        );

        VBox passwordBox = new VBox(7);

        passwordBox.getChildren().addAll(
                passwordText,
                passwordRow
        );

        accountCard.getChildren().addAll(
                accountHeading,
                emailBox,
                passwordBox
        );

        VBox notificationCard = createCard();

        Label notificationTitle
                = new Label("Notifications");

        notificationTitle.setStyle(
                "-fx-text-fill: " + DARK + ";"
                + "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
        );

        Label notificationSub
                = new Label(
                        "Choose how you want to receive notifications."
                );

        notificationSub.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 13px;"
        );

        VBox notificationHeading
                = new VBox(4);

        notificationHeading.getChildren().addAll(
                notificationTitle,
                notificationSub
        );

        CheckBox taskNotification
                = new CheckBox(
                        "Task notifications"
                );

        taskNotification.setSelected(true);

        CheckBox leaveNotification
                = new CheckBox(
                        "Leave request updates"
                );

        leaveNotification.setSelected(true);

        CheckBox announcementNotification
                = new CheckBox(
                        "Announcements"
                );

        announcementNotification.setSelected(true);

        CheckBox learningNotification
                = new CheckBox(
                        "Learning and course updates"
                );

        learningNotification.setSelected(false);

        VBox notificationOptions
                = new VBox(13);

        notificationOptions.getChildren().addAll(
                taskNotification,
                leaveNotification,
                announcementNotification,
                learningNotification
        );

        notificationCard.getChildren().addAll(
                notificationHeading,
                notificationOptions
        );

        VBox appearanceCard = createCard();

        Label appearanceTitle
                = new Label("Appearance");

        appearanceTitle.setStyle(
                "-fx-text-fill: " + DARK + ";"
                + "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
        );

        Label appearanceSub
                = new Label(
                        "Customize how SkillVerse looks for you."
                );

        appearanceSub.setStyle(
                "-fx-text-fill: " + MUTED + ";"
                + "-fx-font-size: 13px;"
        );

        VBox appearanceHeading
                = new VBox(4);

        appearanceHeading.getChildren().addAll(
                appearanceTitle,
                appearanceSub
        );

        Label themeLabel
                = new Label("Theme");

        themeLabel.setStyle(
                "-fx-text-fill: " + TEXT + ";"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: bold;"
        );

        ComboBox<String> theme
                = new ComboBox<>();

        theme.getItems().addAll(
                "Light",
                "System Default"
        );

        theme.setValue("Light");

        theme.setPrefWidth(230);

        theme.setPrefHeight(42);

        theme.setStyle(
                "-fx-background-color: #F9FAFC;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 10;"
                + "-fx-background-radius: 10;"
        );

        HBox themeRow
                = new HBox(25);

        themeRow.setAlignment(
                Pos.CENTER_LEFT
        );

        themeRow.getChildren().addAll(
                themeLabel,
                theme
        );

        appearanceCard.getChildren().addAll(
                appearanceHeading,
                themeRow
        );

        HBox bottom = new HBox();

        bottom.setAlignment(
                Pos.CENTER_RIGHT
        );

        Button save = new Button(
                "Save Changes"
        );

        save.setPrefHeight(45);

        save.setPrefWidth(150);

        save.setStyle(
                "-fx-background-color: linear-gradient("
                + "to right, "
                + BLUE
                + ", "
                + PURPLE
                + ");"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 10;"
        );

        save.setOnAction(e -> {

            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION
            );

            alert.setTitle("Settings");

            alert.setHeaderText(
                    "Settings Saved"
            );

            alert.setContentText(
                    "Your settings have been saved successfully."
            );

            alert.showAndWait();

        });

        bottom.getChildren().add(save);

        page.getChildren().addAll(
                header,
                accountCard,
                notificationCard,
                appearanceCard,
                bottom
        );

        return page;
    }

    private VBox createCard() {

        VBox card = new VBox(18);

        card.setPadding(
                new Insets(24)
        );

        card.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 17;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 17;"
        );

        return card;
    }
}
