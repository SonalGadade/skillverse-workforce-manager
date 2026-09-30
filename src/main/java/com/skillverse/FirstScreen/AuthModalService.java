package com.skillverse.FirstScreen;

import com.skillverse.CommonFeatures.UserRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.prefs.Preferences;

public class AuthModalService {

    private static final String ROYAL_BLUE = "#2563EB";
    private static final String DARK_NAVY = "#0F172A";
    private static final String TEXT_MUTED = "#64748B";
    private static final String BORDER_COLOR = "#CBD5E1";

    private static final Preferences prefs = Preferences.userRoot().node("com.skillverse.auth");

    public static void showCreateAccountModal(String roleName, TextField loginEmailFieldToPrefill) {
        CreateAccountModal.show(roleName, loginEmailFieldToPrefill);
    }

    public static void showForgotPasswordModal(String initialEmail) {
        ForgotPasswordModal.show(initialEmail);
    }

    public static void saveRememberMePreference(String roleName, String email, boolean remember) {
        String keyEmail = "remember_email_" + (roleName != null ? roleName.toLowerCase() : "default");
        String keyFlag = "remember_flag_" + (roleName != null ? roleName.toLowerCase() : "default");

        if (remember && email != null && !email.trim().isEmpty()) {
            prefs.put(keyEmail, email.trim());
            prefs.putBoolean(keyFlag, true);
        } else {
            prefs.remove(keyEmail);
            prefs.putBoolean(keyFlag, false);
        }
    }

    public static String loadRememberedEmail(String roleName) {
        String keyEmail = "remember_email_" + (roleName != null ? roleName.toLowerCase() : "default");
        String keyFlag = "remember_flag_" + (roleName != null ? roleName.toLowerCase() : "default");

        if (prefs.getBoolean(keyFlag, false)) {
            return prefs.get(keyEmail, null);
        }
        return null;
    }

    public static boolean isRemembered(String roleName) {
        String keyFlag = "remember_flag_" + (roleName != null ? roleName.toLowerCase() : "default");
        return prefs.getBoolean(keyFlag, false);
    }

    private static VBox createFieldGroup(String labelText, String placeholder) {
        VBox box = new VBox(6);
        box.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        label.setTextFill(Color.web(TEXT_MUTED));

        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setPrefHeight(40);
        field.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";"
                + "-fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 8px 12px; -fx-font-size: 13px;");

        box.getChildren().addAll(label, field);
        return box;
    }

    private static VBox createPasswordFieldGroup(String labelText, String placeholder) {
        VBox box = new VBox(6);
        box.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        label.setTextFill(Color.web(TEXT_MUTED));

        PasswordField passField = new PasswordField();
        passField.setPromptText(placeholder);
        passField.setPrefHeight(40);
        passField.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";"
                + "-fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 8px 36px 8px 12px; -fx-font-size: 13px;");

        TextField visiblePassField = new TextField();
        visiblePassField.setPromptText(placeholder);
        visiblePassField.setPrefHeight(40);
        visiblePassField.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";"
                + "-fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 8px 36px 8px 12px; -fx-font-size: 13px;");
        visiblePassField.setManaged(false);
        visiblePassField.setVisible(false);

        visiblePassField.textProperty().bindBidirectional(passField.textProperty());

        Button eyeBtn = new Button("👁");
        eyeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-size: 14px; -fx-cursor: hand;");

        eyeBtn.setOnAction(e -> {
            boolean show = !visiblePassField.isVisible();
            visiblePassField.setVisible(show);
            visiblePassField.setManaged(show);
            passField.setVisible(!show);
            passField.setManaged(!show);
            eyeBtn.setText(show ? "🙈" : "👁");
        });

        StackPane stack = new StackPane(passField, visiblePassField, eyeBtn);
        StackPane.setAlignment(eyeBtn, Pos.CENTER_RIGHT);
        StackPane.setMargin(eyeBtn, new Insets(0, 8, 0, 0));

        box.getChildren().addAll(label, stack);
        return box;
    }

    private static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("SkillVerse Auth");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
