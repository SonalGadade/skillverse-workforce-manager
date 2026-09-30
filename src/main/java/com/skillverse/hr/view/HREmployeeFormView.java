package com.skillverse.hr.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.User;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class HREmployeeFormView extends VBox {

    private final TextField nameField;
    private final TextField emailField;
    private final TextField phoneField;
    private final ComboBox<String> roleCombo;
    private final ComboBox<String> deptCombo;
    private final DatePicker joinDatePicker;
    private final Button saveBtn;
    private final Label statusLbl;
    private final boolean isEditMode;
    private final User targetUser;

    // Constructor for ADD NEW EMPLOYEE
    public HREmployeeFormView() {
        this(null);
    }

    // Constructor for EDIT EXISTING EMPLOYEE
    public HREmployeeFormView(User existingUser) {
        this.targetUser = existingUser;
        this.isEditMode = (existingUser != null);

        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        // 1. Header with Back Button
        HBox headerBar = new HBox(14);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("← Back to Directory");
        backBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
        backBtn.setOnAction(e -> navigateBack());

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label(isEditMode ? "Edit Employee Details ✏️" : "Add New Employee / Team Member 👤");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label(isEditMode ? "Update employee role, department and credentials in Firestore." : "Register a new staff member into enterprise directory and synchronize with Firestore.");
        subtitleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        headerBar.getChildren().addAll(backBtn, titleBox);

        // 2. Centered Clean Card Form (No Popups)
        VBox card = new VBox(18);
        card.setMaxWidth(720);
        card.setPadding(new Insets(28));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        nameField = createFormField(isEditMode ? targetUser.getName() : "", "e.g. Rahul Patil");
        emailField = createFormField(isEditMode ? targetUser.getEmail() : "", "e.g. rahul.patil@skillverse.ai");
        if (isEditMode) {
            emailField.setDisable(true); // Primary Key
        }

        phoneField = createFormField(isEditMode && targetUser.getPhone() != null ? targetUser.getPhone() : "", "e.g. +91 98765 43210");

        roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("EMPLOYEE", "MANAGER", "TRAINER", "HR", "ADMIN");
        roleCombo.setValue(isEditMode && targetUser.getRole() != null ? targetUser.getRole() : "EMPLOYEE");
        roleCombo.setMaxWidth(Double.MAX_VALUE);
        roleCombo.setPrefHeight(40);
        roleCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6;");

        deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("Engineering & Tech", "Product & Design", "Human Resources", "Sales & Marketing", "Training & Enablement", "Operations");
        deptCombo.setValue(isEditMode && targetUser.getDepartment() != null ? targetUser.getDepartment() : "Engineering & Tech");
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setPrefHeight(40);
        deptCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6;");

        joinDatePicker = new DatePicker();
        joinDatePicker.setValue(java.time.LocalDate.now());
        joinDatePicker.setMaxWidth(Double.MAX_VALUE);
        joinDatePicker.setPrefHeight(40);

        statusLbl = new Label();
        statusLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        HBox actionRow = new HBox(12);
        actionRow.setAlignment(Pos.CENTER_LEFT);

        saveBtn = new Button(isEditMode ? "Update Changes →" : "Register & Save Employee →");
        saveBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");
        saveBtn.setOnAction(e -> handleSaveOrUpdate());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> navigateBack());

        actionRow.getChildren().addAll(saveBtn, cancelBtn);

        card.getChildren().addAll(
                createFieldContainer("FULL NAME *", nameField),
                createFieldContainer("WORK EMAIL ADDRESS *", emailField),
                createFieldContainer("PHONE NUMBER", phoneField),
                createFieldContainer("ASSIGNED ROLE *", roleCombo),
                createFieldContainer("DEPARTMENT *", deptCombo),
                createFieldContainer("JOINING DATE", joinDatePicker),
                statusLbl,
                actionRow
        );

        ScrollPane scroll = new ScrollPane(new VBox(20, headerBar, card));
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(scroll);
    }

    private void handleSaveOrUpdate() {
        String name = nameField.getText().trim();
        String email = emailField.getText().toLowerCase().trim();
        String phone = phoneField.getText().trim();
        String role = roleCombo.getValue();
        String dept = deptCombo.getValue();

        if (name.isEmpty() || email.isEmpty()) {
            statusLbl.setText("⚠️ Please fill in Full Name and Email Address.");
            statusLbl.setStyle("-fx-text-fill: #DC2626;");
            return;
        }

        saveBtn.setDisable(true);
        saveBtn.setText(isEditMode ? "Updating..." : "Saving to Firestore...");

        User userToSave = isEditMode ? targetUser : new User();
        userToSave.setName(name);
        userToSave.setEmail(email);
        userToSave.setPhone(phone);
        userToSave.setRole(role);
        userToSave.setDepartment(dept);
        if (!isEditMode) {
            userToSave.setCreatedAt(new Date());
        }

        CompletableFuture.runAsync(() -> FirebaseDAO.getInstance().saveUser(userToSave)).thenRun(() -> {
            Platform.runLater(this::navigateBack);
        }).exceptionally(ex -> {
            Platform.runLater(() -> {
                saveBtn.setDisable(false);
                saveBtn.setText(isEditMode ? "Update Changes →" : "Register & Save Employee →");
                statusLbl.setText("⚠️ Error saving employee: " + ex.getMessage());
                statusLbl.setStyle("-fx-text-fill: #DC2626;");
            });
            return null;
        });
    }

    private void navigateBack() {
        com.skillverse.FirstScreen.SceneNavigator.showHRDashboard();
    }

    private TextField createFormField(String initialText, String prompt) {
        TextField tf = new TextField(initialText != null ? initialText : "");
        tf.setPromptText(prompt);
        tf.setPrefHeight(40);
        tf.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6; -fx-padding: 0 12;");
        return tf;
    }

    private VBox createFieldContainer(String labelText, javafx.scene.Node control) {
        VBox box = new VBox(5);
        Label lbl = new Label(labelText);
        lbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");
        box.getChildren().addAll(lbl, control);
        return box;
    }
}
