package com.skillverse.FirstScreen;

import java.util.Optional;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean validateLoginInput(String emailOrUser, String password) {
        if (!isNotEmpty(emailOrUser) || !isNotEmpty(password)) {
            showAlert(
                Alert.AlertType.WARNING,
                "Required Fields Missing",
                "Please fill in all required fields.",
                "Both Email/Username and Password fields are required to log in."
            );
            return false;
        }

        String input = emailOrUser.trim();
        if (input.contains("@") && !isValidEmail(input)) {
            showAlert(
                Alert.AlertType.WARNING,
                "Invalid Email Format",
                "Invalid Email Address",
                "Please enter a valid email address format (e.g., user@skillverse.com)."
            );
            return false;
        }

        return true;
    }

    public static boolean confirmLogout(String roleName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Logout from " + (roleName != null ? roleName : "Account"));
        alert.setContentText("Are you sure you want to log out and return to the main role portal?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
