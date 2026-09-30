package com.skillverse.FirstScreen;

import com.skillverse.CommonFeatures.EmailService;
import com.skillverse.CommonFeatures.EmailService1;
import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserRepository;
import com.skillverse.Dao.FirebaseDAO;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.security.SecureRandom;

public class ForgotPasswordModal {

    private static final String ROYAL_BLUE = "#2563EB";
    private static final String DARK_NAVY = "#0F172A";
    private static final String TEXT_MUTED = "#64748B";
    private static final String BORDER_COLOR = "#CBD5E1";
    private static final long OTP_EXPIRATION_MILLIS = 5 * 60 * 1000;

    private String email = "";
    private String generatedOtp = "";
    private long otpCreatedAtMillis = 0L;

    private Dialog<Void> dialog;
    private VBox modalContainer;

    private Timeline countdownTimeline;
    private int secondsRemaining = 60;
    private Label timerLabel;
    private Hyperlink resendLink;

    public static void show(String initialEmail) {
        ForgotPasswordModal modal = new ForgotPasswordModal();
        modal.email = initialEmail != null ? initialEmail.trim() : "";
        modal.openModal();
    }

    private void openModal() {
        dialog = new Dialog<>();
        dialog.setTitle("SkillVerse - Reset Password");

        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().add(ButtonType.CLOSE);
        pane.lookupButton(ButtonType.CLOSE).setVisible(false);
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 0;");

        modalContainer = new VBox(16);
        modalContainer.setPadding(new Insets(28, 32, 28, 32));
        modalContainer.setPrefWidth(440);
        modalContainer.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 16px;");

        dialog.setOnCloseRequest(e -> stopTimer());

        renderStage1EmailView();

        pane.setContent(modalContainer);
        dialog.showAndWait();
    }

    private void renderStage1EmailView() {
        stopTimer();
        modalContainer.getChildren().clear();

        VBox header = new VBox(4);
        Text titleText = new Text("Reset Password");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleText.setFill(Color.web(DARK_NAVY));

        Text subText = new Text("Enter your registered email address to receive password reset OTP.");
        subText.setFont(Font.font("Arial", 12));
        subText.setFill(Color.web(TEXT_MUTED));
        subText.setWrappingWidth(370);
        header.getChildren().addAll(titleText, subText);

        HBox banner = new HBox(10);
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setPadding(new Insets(10, 14, 10, 14));
        banner.setStyle("-fx-background-color: #FEF2F2; -fx-border-color: #EF4444; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        banner.setVisible(false);
        banner.setManaged(false);

        Label bannerIcon = new Label("⚠️");
        bannerIcon.setFont(Font.font("Arial", 16));

        VBox textContent = new VBox(2);
        Label bannerTitle = new Label("Account Not Found");
        bannerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        bannerTitle.setTextFill(Color.web("#991B1B"));

        Label bannerSubtitle = new Label("No account exists with this email address. Please check or register.");
        bannerSubtitle.setFont(Font.font("Arial", 11.5));
        bannerSubtitle.setTextFill(Color.web("#DC2626"));
        bannerSubtitle.setWrapText(true);

        textContent.getChildren().addAll(bannerTitle, bannerSubtitle);
        banner.getChildren().addAll(bannerIcon, textContent);

        // Form Group
        VBox emailBox = createFieldGroup("REGISTERED EMAIL ADDRESS", "e.g. user@skillverse.com", email);
        TextField emailField = (TextField) emailBox.getChildren().get(1);

        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (banner.isVisible()) {
                banner.setVisible(false);
                banner.setManaged(false);
            }
        });

        Button sendBtn = new Button("Send Reset Code →");
        sendBtn.setMaxWidth(Double.MAX_VALUE);
        sendBtn.setPrefHeight(44);
        sendBtn.setStyle("-fx-background-color: " + ROYAL_BLUE + "; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10px; -fx-cursor: hand;");

        Hyperlink backLink = new Hyperlink("← Back to Sign In");
        backLink.setStyle("-fx-text-fill: " + ROYAL_BLUE + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        backLink.setOnAction(e -> {
            stopTimer();
            dialog.close();
        });

        HBox backBox = new HBox(backLink);
        backBox.setAlignment(Pos.CENTER);

        sendBtn.setOnAction(e -> {
            String typedEmail = emailField.getText().trim();

            if (typedEmail.isEmpty() || !typedEmail.contains("@") || !typedEmail.contains(".")) {
                showAlert(Alert.AlertType.WARNING, "Invalid Email", "Please enter a valid email address.");
                return;
            }

            banner.setVisible(false);
            banner.setManaged(false);

            sendBtn.setDisable(true);
            sendBtn.setText("Checking account status...");

            FirebaseDAO.getInstance().isEmailRegistered(typedEmail.toLowerCase()).thenAccept(exists -> {
                Platform.runLater(() -> {
                    boolean userFound = Boolean.TRUE.equals(exists) || UserRepository.getInstance().userExists(typedEmail);

                    if (!userFound) {
                        sendBtn.setDisable(false);
                        sendBtn.setText("Send Reset Code →");

                        banner.setVisible(true);
                        banner.setManaged(true);

                        FadeTransition ft = new FadeTransition(Duration.millis(300), banner);
                        ft.setFromValue(0.0);
                        ft.setToValue(1.0);
                        ft.play();
                        return;
                    }

                    SecureRandom random = new SecureRandom();
                    String newOtp = String.format("%06d", random.nextInt(1000000));
                    this.email = typedEmail;
                    this.generatedOtp = newOtp;
                    this.otpCreatedAtMillis = System.currentTimeMillis();

                    sendBtn.setText("Sending OTP ✉️...");

                    EmailService1.sendOtpEmail(typedEmail, newOtp, "SkillVerse User").thenAccept(sent -> {
                        Platform.runLater(() -> {
                            sendBtn.setDisable(false);
                            sendBtn.setText("Send Reset Code →");

                            showAlert(Alert.AlertType.INFORMATION, "Reset Code Dispatched ✉️",
                                    "Password reset verification code sent to " + this.email + "\n\nPlease check your email inbox.");

                            renderStage2OtpView();
                        });
                    });
                });
            });
        });

        modalContainer.getChildren().addAll(header, banner, emailBox, sendBtn, backBox);
    }

    private void renderStage2OtpView() {
        modalContainer.getChildren().clear();

        VBox header = new VBox(4);
        Text titleText = new Text("Verify Reset Code ✉️");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleText.setFill(Color.web(DARK_NAVY));

        Text subText = new Text("Enter the 6-digit code sent to:\n" + email);
        subText.setFont(Font.font("Arial", 12.5));
        subText.setFill(Color.web(TEXT_MUTED));
        subText.setWrappingWidth(360);
        header.getChildren().addAll(titleText, subText);

        HBox otpBox = new HBox(8);
        otpBox.setAlignment(Pos.CENTER);
        otpBox.setPadding(new Insets(10, 0, 10, 0));

        TextField[] digitFields = new TextField[6];
        for (int i = 0; i < 6; i++) {
            TextField tf = new TextField("");
            tf.setPrefSize(48, 52);
            tf.setAlignment(Pos.CENTER);
            tf.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            tf.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";"
                    + "-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: " + DARK_NAVY + ";");

            final int index = i;

            tf.textProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == null) {
                    return;
                }

                if (newVal.length() >= 6 && newVal.matches("\\d+")) {
                    String pasted = newVal.substring(0, 6);
                    for (int k = 0; k < 6; k++) {
                        digitFields[k].setText(String.valueOf(pasted.charAt(k)));
                    }
                    digitFields[5].requestFocus();
                    return;
                }

                if (newVal.length() > 1) {
                    tf.setText(newVal.substring(newVal.length() - 1));
                }

                if (!tf.getText().isEmpty()) {
                    tf.setStyle("-fx-background-color: #EFF6FF; -fx-border-color: " + ROYAL_BLUE + ";"
                            + "-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: " + ROYAL_BLUE + ";");
                    if (index < 5) {
                        digitFields[index + 1].requestFocus();
                    }
                } else {
                    tf.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";"
                            + "-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: " + DARK_NAVY + ";");
                }
            });

            tf.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    if (tf.getText().isEmpty() && index > 0) {
                        digitFields[index - 1].requestFocus();
                        digitFields[index - 1].selectAll();
                    }
                }
            });

            digitFields[i] = tf;
            otpBox.getChildren().add(tf);
        }

        Label errorLabel = new Label();
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        errorLabel.setTextFill(Color.web("#DC2626"));
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        HBox timerRow = new HBox(8);
        timerRow.setAlignment(Pos.CENTER);

        timerLabel = new Label("Resend code in 01:00");
        timerLabel.setFont(Font.font("Arial", 12));
        timerLabel.setTextFill(Color.web(TEXT_MUTED));

        resendLink = new Hyperlink("Resend Code");
        resendLink.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        resendLink.setStyle("-fx-text-fill: #94A3B8; -fx-border-color: transparent;");
        resendLink.setDisable(true);

        resendLink.setOnAction(e -> {
            if (resendLink.isDisable()) {
                return;
            }
            SecureRandom random = new SecureRandom();
            String freshOtp = String.format("%06d", random.nextInt(1000000));
            this.generatedOtp = freshOtp;
            this.otpCreatedAtMillis = System.currentTimeMillis();

            resendLink.setDisable(true);
            EmailService1.sendOtpEmail(email, freshOtp, "SkillVerse User").thenAccept(sent -> {
                Platform.runLater(() -> {
                    showAlert(Alert.AlertType.INFORMATION, "New OTP Dispatched",
                            "Verification code sent to " + email + "\n\nPlease check your email inbox.");
                    startTimer();
                });
            });
        });

        timerRow.getChildren().addAll(timerLabel, resendLink);

        Button verifyBtn = new Button("Verify Code →");
        verifyBtn.setMaxWidth(Double.MAX_VALUE);
        verifyBtn.setPrefHeight(44);
        verifyBtn.setStyle("-fx-background-color: " + ROYAL_BLUE + "; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10px; -fx-cursor: hand;");

        Hyperlink backToStage1Link = new Hyperlink("← Back / Change Email");
        backToStage1Link.setStyle("-fx-text-fill: " + ROYAL_BLUE + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        backToStage1Link.setOnAction(e -> {
            stopTimer();
            renderStage1EmailView();
        });

        HBox backBox = new HBox(backToStage1Link);
        backBox.setAlignment(Pos.CENTER);

        verifyBtn.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            for (TextField tf : digitFields) {
                sb.append(tf.getText().trim());
            }
            String enteredCode = sb.toString();

            if (enteredCode.length() < 6) {
                errorLabel.setText("⚠️ Please enter all 6 digits of the reset code.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
                return;
            }

            long elapsedMillis = System.currentTimeMillis() - otpCreatedAtMillis;
            boolean isNotExpired = elapsedMillis <= OTP_EXPIRATION_MILLIS;
            boolean isCodeValid = !generatedOtp.isEmpty() && enteredCode.equals(generatedOtp);

            if (isCodeValid && isNotExpired) {
                stopTimer();
                renderStage3NewPasswordView();
            } else {
                errorLabel.setText("Invalid or expired reset code. Please try again.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);

                for (TextField tf : digitFields) {
                    tf.setStyle("-fx-background-color: #FEF2F2; -fx-border-color: #EF4444;"
                            + "-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: #DC2626;");
                }
            }
        });

        modalContainer.getChildren().addAll(header, otpBox, errorLabel, timerRow, verifyBtn, backBox);
        digitFields[0].requestFocus();
        startTimer();
    }

    private void renderStage3NewPasswordView() {
        modalContainer.getChildren().clear();

        VBox header = new VBox(4);
        Text titleText = new Text("Set New Password 🔒");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleText.setFill(Color.web(DARK_NAVY));

        Text subText = new Text("Enter your new password below for account:\n" + email);
        subText.setFont(Font.font("Arial", 12.5));
        subText.setFill(Color.web(TEXT_MUTED));
        subText.setWrappingWidth(360);
        header.getChildren().addAll(titleText, subText);

        VBox passBox = createPasswordFieldGroup("NEW PASSWORD", "At least 4 characters");
        StackPane passStack = (StackPane) passBox.getChildren().get(1);
        PasswordField passField = (PasswordField) passStack.getChildren().get(0);

        VBox confirmBox = createPasswordFieldGroup("CONFIRM NEW PASSWORD", "Re-enter new password");
        StackPane confirmStack = (StackPane) confirmBox.getChildren().get(1);
        PasswordField confirmPassField = (PasswordField) confirmStack.getChildren().get(0);

        Label errorLabel = new Label();
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        errorLabel.setTextFill(Color.web("#DC2626"));
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Button resetBtn = new Button("Update Password & Save →");
        resetBtn.setMaxWidth(Double.MAX_VALUE);
        resetBtn.setPrefHeight(44);
        resetBtn.setStyle("-fx-background-color: " + ROYAL_BLUE + "; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10px; -fx-cursor: hand;");

        resetBtn.setOnAction(e -> {
            String newPass = passField.getText();
            String confirmPass = confirmPassField.getText();

            if (newPass == null || newPass.trim().length() < 4) {
                errorLabel.setText("⚠️ Password must be at least 4 characters long.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
                return;
            }

            if (!newPass.equals(confirmPass)) {
                errorLabel.setText("⚠️ Passwords do not match. Please re-enter.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
                return;
            }

            errorLabel.setVisible(false);
            errorLabel.setManaged(false);

            resetBtn.setDisable(true);
            resetBtn.setText("Updating Password 🔒...");

            FirebaseDAO.getInstance().updateUserPassword(email.toLowerCase(), newPass).thenAccept(success -> {
                Platform.runLater(() -> {
                    resetBtn.setDisable(false);
                    resetBtn.setText("Update Password & Save →");

                    if (Boolean.TRUE.equals(success)) {
                        // Update cached user in UserRepository if present
                        User cached = UserRepository.getInstance().findUser(email);
                        if (cached != null) {
                            cached.setPassword(newPass);
                        }

                        showAlert(Alert.AlertType.INFORMATION, "Password Reset Successful 🎉",
                                "Your password has been successfully updated!\n\nYou can now log in using your new password.");
                        dialog.close();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Update Failed",
                                "Failed to update password in Firestore. Please try again.");
                    }
                });
            });
        });

        modalContainer.getChildren().addAll(header, passBox, confirmBox, errorLabel, resetBtn);
    }

    private void startTimer() {
        stopTimer();
        secondsRemaining = 60;
        resendLink.setDisable(true);
        resendLink.setStyle("-fx-text-fill: #94A3B8; -fx-border-color: transparent;");

        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsRemaining--;
            if (secondsRemaining > 0) {
                int mins = secondsRemaining / 60;
                int secs = secondsRemaining % 60;
                timerLabel.setText(String.format("Resend code in %02d:%02d", mins, secs));
            } else {
                timerLabel.setText("Didn't receive the code?");
                resendLink.setDisable(false);
                resendLink.setStyle("-fx-text-fill: " + ROYAL_BLUE + "; -fx-border-color: transparent; -fx-cursor: hand;");
                stopTimer();
            }
        }));
        countdownTimeline.setCycleCount(60);
        countdownTimeline.play();
    }

    private void stopTimer() {
        if (countdownTimeline != null) {
            countdownTimeline.stop();
            countdownTimeline = null;
        }
    }

    private static VBox createFieldGroup(String labelText, String placeholder, String initialValue) {
        VBox box = new VBox(6);
        box.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        label.setTextFill(Color.web(TEXT_MUTED));

        TextField field = new TextField(initialValue != null ? initialValue : "");
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
