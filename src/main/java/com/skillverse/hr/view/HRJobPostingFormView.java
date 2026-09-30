package com.skillverse.hr.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.JobPosting;
import com.skillverse.CommonFeatures.UserSession;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HRJobPostingFormView extends VBox {

    private final TextField titleField;
    private final ComboBox<String> deptCombo;
    private final ComboBox<String> expCombo;
    private final ComboBox<String> typeCombo;
    private final TextField salaryField;
    private final TextArea descField;
    private final Button publishBtn;
    private final Label statusLbl;

    public HRJobPostingFormView() {
        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(14);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("← Back to Recruitment");
        backBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
        backBtn.setOnAction(e -> navigateBack());

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("Post New Job Opening 🎯");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Create a new job posting, configure role specs, and publish directly to Firestore.");
        subtitleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        headerBar.getChildren().addAll(backBtn, titleBox);

        VBox card = new VBox(18);
        card.setMaxWidth(720);
        card.setPadding(new Insets(28));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        titleField = createFormField("Job Title (e.g. Lead Full-Stack Engineer)");

        deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("Engineering & Tech", "Human Resources", "Sales & Marketing", "Finance", "Product", "Operations", "Design");
        deptCombo.setValue("Engineering & Tech");
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setPrefHeight(40);
        deptCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6;");

        expCombo = new ComboBox<>();
        expCombo.getItems().addAll("Entry Level", "1-3 Years", "3-5 Years", "5+ Years", "Executive");
        expCombo.setValue("3-5 Years");
        expCombo.setMaxWidth(Double.MAX_VALUE);
        expCombo.setPrefHeight(40);
        expCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6;");

        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Full-time", "Part-time", "Contract", "Remote");
        typeCombo.setValue("Full-time");
        typeCombo.setMaxWidth(Double.MAX_VALUE);
        typeCombo.setPrefHeight(40);
        typeCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6;");

        salaryField = createFormField("Salary Range (e.g. $90,000 - $120,000 / yr)");

        descField = new TextArea();
        descField.setPromptText("Job Description & Key Responsibilities...");
        descField.setPrefRowCount(4);
        descField.setWrapText(true);
        descField.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6; -fx-padding: 8;");

        statusLbl = new Label();
        statusLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        HBox actionRow = new HBox(12);
        actionRow.setAlignment(Pos.CENTER_LEFT);

        publishBtn = new Button("Publish Job Opening →");
        publishBtn.setStyle("-fx-background-color: #8B5CF6; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");
        publishBtn.setOnAction(e -> handlePublishJob());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> navigateBack());

        actionRow.getChildren().addAll(publishBtn, cancelBtn);

        card.getChildren().addAll(
                createFieldContainer("JOB TITLE *", titleField),
                createFieldContainer("DEPARTMENT *", deptCombo),
                createFieldContainer("EXPERIENCE LEVEL *", expCombo),
                createFieldContainer("JOB TYPE *", typeCombo),
                createFieldContainer("SALARY RANGE", salaryField),
                createFieldContainer("DESCRIPTION & RESPONSIBILITIES", descField),
                statusLbl,
                actionRow
        );

        ScrollPane scroll = new ScrollPane(new VBox(20, headerBar, card));
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(scroll);
    }

    private void handlePublishJob() {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            statusLbl.setText("⚠️ Please fill in Job Title.");
            statusLbl.setStyle("-fx-text-fill: #DC2626;");
            return;
        }

        publishBtn.setDisable(true);
        publishBtn.setText("Publishing to Firestore...");

        JobPosting jp = new JobPosting();
        jp.setTitle(title);
        jp.setDepartment(deptCombo.getValue());
        jp.setExperience(expCombo.getValue());
        jp.setJobType(typeCombo.getValue());
        jp.setLocation(typeCombo.getValue().contains("Remote") ? "Remote" : "Hybrid");
        jp.setSalaryRange(salaryField.getText().trim());
        jp.setDescription(descField.getText().trim());
        jp.setPostedByEmail(UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "");

        FirebaseDAO.getInstance().createJobPosting(jp).thenAccept(ok -> {
            Platform.runLater(this::navigateBack);
        }).exceptionally(ex -> {
            Platform.runLater(() -> {
                publishBtn.setDisable(false);
                publishBtn.setText("Publish Job Opening →");
                statusLbl.setText("⚠️ Error creating job: " + ex.getMessage());
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
