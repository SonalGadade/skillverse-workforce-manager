package com.skillverse.hr.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.TrainingProgram;
import com.skillverse.CommonFeatures.User;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HRTrainingProgramFormView extends VBox {

    private final TextField titleField;
    private final TextField descField;
    private final ComboBox<String> trainerCombo;
    private final ComboBox<String> deptCombo;
    private final TextField durationField;
    private final TextField startDateField;
    private final Button publishBtn;
    private final Label statusLbl;

    public HRTrainingProgramFormView() {
        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        
        HBox headerBar = new HBox(14);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("← Back to L&D");
        backBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
        backBtn.setOnAction(e -> navigateBack());

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("Create & Assign Training Program 🎓");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Configure new L&D initiatives, assign registered trainers, and publish directly to Firestore.");
        subtitleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        headerBar.getChildren().addAll(backBtn, titleBox);
        
        VBox card = new VBox(18);
        card.setMaxWidth(720);
        card.setPadding(new Insets(28));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        titleField = createFormField("e.g. Advanced Java & Cloud Microservices");
        descField = createFormField("e.g. Full-stack cloud architecture training with Spring Boot & Firestore.");

        trainerCombo = new ComboBox<>();
        trainerCombo.setPromptText("Loading registered trainers...");
        trainerCombo.setMaxWidth(Double.MAX_VALUE);
        trainerCombo.setPrefHeight(40);
        trainerCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6;");

        FirebaseDAO.getInstance().getAllTrainersAsync().thenAccept(trainers -> {
            Platform.runLater(() -> {
                trainerCombo.getItems().clear();
                if (trainers == null || trainers.isEmpty()) {
                    trainerCombo.getItems().add("trainer@skillverse.com (Default Trainer)");
                } else {
                    for (User u : trainers) {
                        trainerCombo.getItems().add(u.getFullName() + " (" + u.getEmail() + ")");
                    }
                }{
                    
                }
                if (!trainerCombo.getItems().isEmpty()) trainerCombo.setValue(trainerCombo.getItems().get(0));
            });
        });

        deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("Engineering", "HR & Talent", "Sales & Growth", "Finance", "Operations", "All Departments");
        deptCombo.setValue("Engineering");
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setPrefHeight(40);
        deptCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6;");

        durationField = createFormField("4 Weeks");
        startDateField = createFormField("Aug 30, 2026");

        statusLbl = new Label();
        statusLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        HBox actionRow = new HBox(12);
        actionRow.setAlignment(Pos.CENTER_LEFT);

        publishBtn = new Button("Publish & Assign Program →");
        publishBtn.setStyle("-fx-background-color: #F59E0B; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");
        publishBtn.setOnAction(e -> handlePublishProgram());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> navigateBack());

        actionRow.getChildren().addAll(publishBtn, cancelBtn);

        card.getChildren().addAll(
                createFieldContainer("PROGRAM TITLE *", titleField),
                createFieldContainer("DESCRIPTION", descField),
                createFieldContainer("ASSIGNED TRAINER *", trainerCombo),
                createFieldContainer("TARGET DEPARTMENT *", deptCombo),
                createFieldContainer("DURATION", durationField),
                createFieldContainer("START DATE", startDateField),
                statusLbl,
                actionRow
        );

        ScrollPane scroll = new ScrollPane(new VBox(20, headerBar, card));
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(scroll);
    }

    private void handlePublishProgram() {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            statusLbl.setText("⚠️ Please fill in Program Title.");
            statusLbl.setStyle("-fx-text-fill: #DC2626;");
            return;
        }

        publishBtn.setDisable(true);
        publishBtn.setText("Publishing to Firestore...");

        String selectedTrainerStr = trainerCombo.getValue() != null ? trainerCombo.getValue() : "Trainer (trainer@skillverse.com)";
        String trainerEmail = "trainer@skillverse.com";
        String trainerName = "Assigned Trainer";

        if (selectedTrainerStr.contains("(") && selectedTrainerStr.contains(")")) {
            trainerName = selectedTrainerStr.substring(0, selectedTrainerStr.indexOf("(")).trim();
            trainerEmail = selectedTrainerStr.substring(selectedTrainerStr.indexOf("(") + 1, selectedTrainerStr.indexOf(")")).trim();
        } else {
            trainerName = selectedTrainerStr;
        }

        TrainingProgram tp = new TrainingProgram();
        tp.setTitle(title);
        tp.setDescription(descField.getText().trim());
        tp.setAssignedTrainerName(trainerName);
        tp.setAssignedTrainerEmail(trainerEmail);
        tp.setDepartment(deptCombo.getValue());
        tp.setDurationWeeks(durationField.getText().trim());
        tp.setStartDate(startDateField.getText().trim());
        tp.setStatus("ACTIVE");

        FirebaseDAO.getInstance().createTrainingProgram(tp).thenAccept(ok -> {
            Platform.runLater(this::navigateBack);
        }).exceptionally(ex -> {
            Platform.runLater(() -> {
                publishBtn.setDisable(false);
                publishBtn.setText("Publish & Assign Program →");
                statusLbl.setText("⚠️ Error creating training program: " + ex.getMessage());
                statusLbl.setStyle("-fx-text-fill: #DC2626;");
            });
            return null;
        });
    }

    private void navigateBack() {
        com.skillverse.FirstScreen.SceneNavigator.showHRDashboard();
    }

    private TextField createFormField(String prompt) {
        TextField tf = new TextField();
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
