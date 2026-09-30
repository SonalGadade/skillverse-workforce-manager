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

public class CreateAccountModal {

    private static final String ROYAL_BLUE = "#2563EB";
    private static final String DARK_NAVY = "#0F172A";
    private static final String TEXT_MUTED = "#64748B";
    private static final String BORDER_COLOR = "#CBD5E1";
    private static final long OTP_EXPIRATION_MILLIS = 5 * 60 * 1000;

    private String fullName = "";
    private String email = "";
    private String password = "";
    private String role = "";
    private String department = "";
    private String generatedOtp = "";
    private long otpCreatedAtMillis = 0L;

    private Dialog<Void> dialog;
    private VBox modalContainer;
    private TextField loginEmailFieldToPrefill;

    private Timeline countdownTimeline;
    private int secondsRemaining = 60;
    private Label timerLabel;
    private Hyperlink resendLink;

    public static void show(String roleName, TextField loginEmailFieldToPrefill) {
        CreateAccountModal modal = new CreateAccountModal();
        modal.role = roleName != null && !roleName.isEmpty() ? roleName : "Employee";
        modal.loginEmailFieldToPrefill = loginEmailFieldToPrefill;
        modal.openModal();
    }

    private void openModal() {
        dialog = new Dialog<>();
        dialog.setTitle("SkillVerse - Account Registration");

        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().add(ButtonType.CLOSE);
        pane.lookupButton(ButtonType.CLOSE).setVisible(false);
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 0;");

        modalContainer = new VBox(16);
        modalContainer.setPadding(new Insets(28, 32, 28, 32));
        modalContainer.setPrefWidth(460);
        modalContainer.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 16px;");

        dialog.setOnCloseRequest(e -> stopTimer());

        renderStage1Form();

        pane.setContent(modalContainer);
        dialog.showAndWait();
    }

    private void renderStage1Form() {
        stopTimer();
        modalContainer.getChildren().clear();

        // Header Title
        VBox header = new VBox(4);
        Text titleText = new Text("Create " + role + " Account");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleText.setFill(Color.web(DARK_NAVY));

        Text subText = new Text("Fill out your details to register for the " + role + " workspace.");
        subText.setFont(Font.font("Arial", 12));
        subText.setFill(Color.web(TEXT_MUTED));
        header.getChildren().addAll(titleText, subText);

        // Modern Inline Notification Banner / Toast Card
        HBox banner = new HBox(10);
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setPadding(new Insets(10, 14, 10, 14));
        banner.setStyle(
                "-fx-background-color: #EFF6FF; "
                + "-fx-border-color: #3B82F6; "
                + "-fx-border-width: 1px; "
                + "-fx-border-radius: 10px; "
                + "-fx-background-radius: 10px;"
        );
        banner.setVisible(false);
        banner.setManaged(false);

        Label iconLabel = new Label("ℹ️");
        iconLabel.setFont(Font.font("Arial", 16));

        VBox textContent = new VBox(2);
        Label bannerTitle = new Label("Account Already Exists");
        bannerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        bannerTitle.setTextFill(Color.web("#1E40AF"));

        Label bannerSubtitle = new Label("This email is already registered. Please sign in instead.");
        bannerSubtitle.setFont(Font.font("Arial", 11.5));
        bannerSubtitle.setTextFill(Color.web("#2563EB"));
        bannerSubtitle.setWrapText(true);

        textContent.getChildren().addAll(bannerTitle, bannerSubtitle);

        Region bannerSpacer = new Region();
        HBox.setHgrow(bannerSpacer, Priority.ALWAYS);

        Hyperlink bannerSignInLink = new Hyperlink("Sign In →");
        bannerSignInLink.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        bannerSignInLink.setStyle("-fx-text-fill: #1E40AF; -fx-border-color: transparent; -fx-padding: 0;");
        bannerSignInLink.setOnAction(e -> {
            stopTimer();
            dialog.close();
        });

        banner.getChildren().addAll(iconLabel, textContent, bannerSpacer, bannerSignInLink);

        VBox form = new VBox(12);

        VBox nameBox = createFieldGroup("FULL NAME", "e.g. Alex Morgan", fullName);
        TextField nameField = (TextField) nameBox.getChildren().get(1);

        VBox emailBox = createFieldGroup("OFFICIAL EMAIL ADDRESS", "e.g. " + role.toLowerCase() + "@skillverse.com", email);
        TextField emailField = (TextField) emailBox.getChildren().get(1);

        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (banner.isVisible()) {
                banner.setVisible(false);
                banner.setManaged(false);
            }
        });

        VBox passBox = createPasswordFieldGroup("PASSWORD", "At least 4 characters", password);
        StackPane passStack = (StackPane) passBox.getChildren().get(1);
        PasswordField passField = (PasswordField) passStack.getChildren().get(0);

        VBox confirmPassBox = createPasswordFieldGroup("CONFIRM PASSWORD", "Re-enter your password", password);
        StackPane confirmPassStack = (StackPane) confirmPassBox.getChildren().get(1);
        PasswordField confirmPassField = (PasswordField) confirmPassStack.getChildren().get(0);

        Label deptLabel = new Label("DEPARTMENT / WORKSPACE ROLE");
        deptLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        deptLabel.setTextFill(Color.web(TEXT_MUTED));

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Admin", "HR", "Manager", "Employee", "Trainer");
        roleCombo.setValue(role);
        roleCombo.setMaxWidth(Double.MAX_VALUE);
        roleCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";"
                + "-fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 4px;");

        form.getChildren().addAll(nameBox, emailBox, passBox, confirmPassBox, deptLabel, roleCombo);

        Button sendCodeBtn = new Button("Send Verification Code →");
        sendCodeBtn.setMaxWidth(Double.MAX_VALUE);
        sendCodeBtn.setPrefHeight(44);
        sendCodeBtn.setStyle("-fx-background-color: " + ROYAL_BLUE + "; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10px; -fx-cursor: hand;");

        Hyperlink backLink = new Hyperlink("Already have an account? Sign In");
        backLink.setStyle("-fx-text-fill: " + ROYAL_BLUE + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        backLink.setOnAction(e -> {
            stopTimer();
            dialog.close();
        });

        HBox backBox = new HBox(backLink);
        backBox.setAlignment(Pos.CENTER);

        sendCodeBtn.setOnAction(e -> {
            String typedName = nameField.getText().trim();
            String typedEmail = emailField.getText().trim();
            String typedPass = passField.getText();
            String typedConfirmPass = confirmPassField.getText();
            String selectedRole = roleCombo.getValue();

            if (typedName.isEmpty() || typedEmail.isEmpty() || typedPass.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Registration Error", "Please fill in all required fields.");
                return;
            }

            if (!typedEmail.contains("@") || !typedEmail.contains(".")) {
                showAlert(Alert.AlertType.WARNING, "Invalid Email", "Please enter a valid email address.");
                return;
            }

            if (!typedPass.equals(typedConfirmPass)) {
                showAlert(Alert.AlertType.WARNING, "Password Mismatch", "Passwords do not match. Please re-type.");
                return;
            }

            if (typedPass.length() < 4) {
                showAlert(Alert.AlertType.WARNING, "Weak Password", "Password must be at least 4 characters.");
                return;
            }

            banner.setVisible(false);
            banner.setManaged(false);

            sendCodeBtn.setDisable(true);
            sendCodeBtn.setText("Checking account status...");

            FirebaseDAO.getInstance().isEmailRegistered(typedEmail.toLowerCase()).thenAccept(exists -> {
                Platform.runLater(() -> {
                    if (Boolean.TRUE.equals(exists) || UserRepository.getInstance().userExists(typedEmail)) {
                        sendCodeBtn.setDisable(false);
                        sendCodeBtn.setText("Send Verification Code →");

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
                    this.fullName = typedName;
                    this.email = typedEmail;
                    this.password = typedPass;
                    this.role = selectedRole;
                    this.department = selectedRole + " Dept";
                    this.generatedOtp = newOtp;
                    this.otpCreatedAtMillis = System.currentTimeMillis();

                    sendCodeBtn.setText("Sending OTP ✉️...");

                    EmailService1.sendOtpEmail(typedEmail, newOtp, typedName).thenAccept(sent -> {
                        Platform.runLater(() -> {
                            sendCodeBtn.setDisable(false);
                            sendCodeBtn.setText("Send Verification Code →");

                            showAlert(Alert.AlertType.INFORMATION, "Verification Code Sent ✉️",
                                    "Verification code sent to " + this.email + "\n\nPlease check your email inbox.");

                            renderStage2OtpView();
                        });
                    });
                });
            });
        });

        modalContainer.getChildren().addAll(header, banner, form, sendCodeBtn, backBox);
    }

    // =========================================================================
    // STAGE 2: 6-DIGIT OTP VERIFICATION SCREEN
    // =========================================================================
    private void renderStage2OtpView() {
        modalContainer.getChildren().clear();

        // Header Title
        VBox header = new VBox(4);
        Text titleText = new Text("Verify Your Email ✉️");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleText.setFill(Color.web(DARK_NAVY));

        Text subText = new Text("Enter the 6-digit verification code sent to:\n" + email);
        subText.setFont(Font.font("Arial", 12.5));
        subText.setFill(Color.web(TEXT_MUTED));
        subText.setWrappingWidth(380);
        header.getChildren().addAll(titleText, subText);

        // 6-Digit OTP Input Boxes (Start Completely Empty)
        HBox otpBox = new HBox(8);
        otpBox.setAlignment(Pos.CENTER);
        otpBox.setPadding(new Insets(10, 0, 10, 0));

        TextField[] digitFields = new TextField[6];
        for (int i = 0; i < 6; i++) {
            TextField tf = new TextField(""); // Empty initial value
            tf.setPrefSize(48, 52);
            tf.setAlignment(Pos.CENTER);
            tf.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            tf.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";"
                    + "-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: " + DARK_NAVY + ";");

            final int index = i;

            // Handle Paste & Input
            tf.textProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == null) {
                    return;
                }

                // If user pasted a 6-digit code
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

            // Handle Backspace Navigation
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

        // Inline Error Message Label
        Label errorLabel = new Label();
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        errorLabel.setTextFill(Color.web("#DC2626"));
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        // Timer & Resend Section
        HBox timerRow = new HBox(8);
        timerRow.setAlignment(Pos.CENTER);

        timerLabel = new Label("Resend code in 01:00");
        timerLabel.setFont(Font.font("Arial", 12));
        timerLabel.setTextFill(Color.web(TEXT_MUTED));

        resendLink = new Hyperlink("Resend OTP");
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
            EmailService1.sendOtpEmail(email, freshOtp, fullName).thenAccept(sent -> {
                Platform.runLater(() -> {
                    showAlert(Alert.AlertType.INFORMATION, "New OTP Dispatched",
                            "Verification code sent to " + email + "\n\nPlease check your email inbox.");
                    startTimer();
                });
            });
        });

        timerRow.getChildren().addAll(timerLabel, resendLink);

        // Action Buttons
        Button verifyBtn = new Button("Verify & Create Account →");
        verifyBtn.setMaxWidth(Double.MAX_VALUE);
        verifyBtn.setPrefHeight(44);
        verifyBtn.setStyle("-fx-background-color: " + ROYAL_BLUE + "; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10px; -fx-cursor: hand;");

        Hyperlink backToStage1Link = new Hyperlink("← Back / Change Email");
        backToStage1Link.setStyle("-fx-text-fill: " + ROYAL_BLUE + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        backToStage1Link.setOnAction(e -> {
            stopTimer();
            renderStage1Form();
        });

        HBox backBox = new HBox(backToStage1Link);
        backBox.setAlignment(Pos.CENTER);

        // Verification Logic (Strict Matching & 5-minute Expiration Check)
        verifyBtn.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            for (TextField tf : digitFields) {
                sb.append(tf.getText().trim());
            }
            String enteredCode = sb.toString();

            if (enteredCode.length() < 6) {
                errorLabel.setText("⚠️ Please enter all 6 digits of the verification code.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
                return;
            }

            long elapsedMillis = System.currentTimeMillis() - otpCreatedAtMillis;
            boolean isNotExpired = elapsedMillis <= OTP_EXPIRATION_MILLIS;
            boolean isCodeValid = !generatedOtp.isEmpty() && enteredCode.equals(generatedOtp);

            if (isCodeValid && isNotExpired) {
                // STAGE 3: SUCCESS & WRITE DIRECTLY TO CLOUD FIRESTORE
                stopTimer();
                User newUser = new User();
                newUser.setName(fullName.trim());
                newUser.setEmail(email.toLowerCase().trim());
                newUser.setPassword(password);
                newUser.setRole(role);
                newUser.setDepartment(department);
                newUser.setCreatedAt(new java.util.Date());
                newUser.setProfilePicUrl(null);
                FirebaseDAO.getInstance().saveUser(newUser);
                UserRepository.getInstance().registerUser(fullName, email, password, role, department);

                if (loginEmailFieldToPrefill != null) {
                    loginEmailFieldToPrefill.setText(email);
                }

                showAlert(Alert.AlertType.INFORMATION, "Account Created 🎉",
                        "Account created successfully!\n\nYou can now log into your " + role + " workspace.");
                dialog.close();

            } else {
                errorLabel.setText("Invalid or expired OTP code. Please check your email inbox and try again.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);

                // Highlight fields in red
                for (TextField tf : digitFields) {
                    tf.setStyle("-fx-background-color: #FEF2F2; -fx-border-color: #EF4444;"
                            + "-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: #DC2626;");
                }
            }
        });

        modalContainer.getChildren().addAll(header, otpBox, errorLabel, timerRow, verifyBtn, backBox);

        // Auto-focus first digit field
        digitFields[0].requestFocus();

        // Start Countdown Timer
        startTimer();
    }

    // =========================================================================
    // TIMER UTILITIES
    // =========================================================================
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

    // =========================================================================
    // UI FACTORIES
    // =========================================================================
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

    private static VBox createPasswordFieldGroup(String labelText, String placeholder, String initialValue) {
        VBox box = new VBox(6);
        box.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        label.setTextFill(Color.web(TEXT_MUTED));

        PasswordField passField = new PasswordField();
        passField.setText(initialValue != null ? initialValue : "");
        passField.setPromptText(placeholder);
        passField.setPrefHeight(40);
        passField.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + ";"
                + "-fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 8px 36px 8px 12px; -fx-font-size: 13px;");

        TextField visiblePassField = new TextField();
        visiblePassField.setText(initialValue != null ? initialValue : "");
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
