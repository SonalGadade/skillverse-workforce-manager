package com.skillverse.trainer.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.TrainingEnrollment;
import com.skillverse.trainer.model.Trainer;
import com.skillverse.CommonFeatures.UserSession;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class LearnersPage {

    public static void show(Stage stage, Trainer trainer) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + AppTheme.BG + ";");
        root.setLeft(TrainerSidebar.create(stage, trainer, "Learners"));

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(24));

        VBox toastBox = new VBox();

   
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        VBox heading = new VBox(4,
                AppTheme.title("Learners & Student Grading"),
                AppTheme.subtitle("Assign courses to employees, record live class attendance, and grade test performance.")
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button toggleAddBtn = new Button("+ Enroll / Assign Learner");
        toggleAddBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand;");

        header.getChildren().addAll(heading, headerSpacer, toggleAddBtn);

        
        VBox enrollFormCard = new VBox(16);
        enrollFormCard.setPadding(new Insets(20));
        enrollFormCard.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #2563EB; -fx-border-width: 1px; -fx-border-radius: 12px;");
        enrollFormCard.setVisible(false);
        enrollFormCard.setManaged(false);

        Text formTitle = new Text("➕ Assign Employee to Training Batch");
        formTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        formTitle.setFill(Color.web("#0F172A"));

        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(14);

        TextField nameField = AppTheme.field("Employee Full Name (e.g. Ayushi Patil)");
        TextField emailField = AppTheme.field("Employee Email (e.g. ayushi@skillverse.com)");
        TextField courseField = AppTheme.field("Course Program Title (e.g. Java Full Stack)");
        TextField deptField = AppTheme.field("Department (e.g. Engineering)");

        grid.add(new Label("Employee Name:"), 0, 0);
        grid.add(nameField, 0, 1);
        grid.add(new Label("Employee Email:"), 1, 0);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Training Program:"), 0, 2);
        grid.add(courseField, 0, 3);
        grid.add(new Label("Department:"), 1, 2);
        grid.add(deptField, 1, 3);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        HBox formActions = new HBox(12);
        formActions.setAlignment(Pos.CENTER_RIGHT);
        Button cancelFormBtn = new Button("Cancel");
        cancelFormBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 6px; -fx-cursor: hand;");

        Button submitEnrollBtn = new Button("Confirm & Assign Learner");
        submitEnrollBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 20px; -fx-background-radius: 6px; -fx-cursor: hand;");

        formActions.getChildren().addAll(cancelFormBtn, submitEnrollBtn);
        enrollFormCard.getChildren().addAll(formTitle, grid, formActions);

       
        VBox learnersListContainer = new VBox(14);
        ScrollPane scrollPane = new ScrollPane(learnersListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        mainBox.getChildren().addAll(header, toastBox, enrollFormCard, scrollPane);
        root.setCenter(mainBox);

        String trainerEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "trainer@skillverse.com";

        Runnable loadLearners = () -> {
            learnersListContainer.getChildren().clear();
            ProgressIndicator spinner = new ProgressIndicator();
            learnersListContainer.getChildren().add(new HBox(10, new Label("Loading learners list..."), spinner));

            FirebaseDAO.getInstance().getEnrolledLearnersForTrainer(trainerEmail).thenAccept(enrollments -> {
                Platform.runLater(() -> {
                    learnersListContainer.getChildren().clear();
                    if (enrollments == null || enrollments.isEmpty()) {
                        VBox emptyCard = AppTheme.card(
                                new Label("👥 No Enrolled Learners Found"),
                                new Label("Click '+ Enroll / Assign Learner' to assign your first employee batch.")
                        );
                        emptyCard.setAlignment(Pos.CENTER);
                        learnersListContainer.getChildren().add(emptyCard);
                    } else {
                        for (TrainingEnrollment te : enrollments) {
                            learnersListContainer.getChildren().add(buildLearnerRow(te, toastBox));
                        }
                    }
                });
            });
        };

        toggleAddBtn.setOnAction(e -> {
            boolean visible = !enrollFormCard.isVisible();
            enrollFormCard.setVisible(visible);
            enrollFormCard.setManaged(visible);
        });

        cancelFormBtn.setOnAction(e -> {
            enrollFormCard.setVisible(false);
            enrollFormCard.setManaged(false);
        });

        submitEnrollBtn.setOnAction(e -> {
            String name = nameField.getText() != null ? nameField.getText().trim() : "";
            String email = emailField.getText() != null ? emailField.getText().trim() : "";
            String course = courseField.getText() != null ? courseField.getText().trim() : "";
            String dept = deptField.getText() != null ? deptField.getText().trim() : "General";

            if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
                showToast(toastBox, "⚠️ Please enter Employee Name, Email, and Training Program.", "#DC2626", "#FEF2F2");
                return;
            }

            TrainingEnrollment te = new TrainingEnrollment();
            te.setEmployeeName(name);
            te.setEmployeeEmail(email);
            te.setProgramTitle(course);
            te.setEmployeeDepartment(dept);
            te.setAttendancePercentage(0.0);
            te.setAssessmentScore(0.0);
            te.setCompletionStatus("IN_PROGRESS");

            submitEnrollBtn.setDisable(true);
            submitEnrollBtn.setText("Assigning...");

            FirebaseDAO.getInstance().assignLearnerToCourse(te).thenAccept(success -> {
                Platform.runLater(() -> {
                    submitEnrollBtn.setDisable(false);
                    submitEnrollBtn.setText("Confirm & Assign Learner");
                    if (success) {
                        showToast(toastBox, "✅ Learner " + name + " assigned to course '" + course + "' successfully!", "#16A34A", "#F0FDF4");
                        nameField.clear();
                        emailField.clear();
                        courseField.clear();
                        deptField.clear();
                        enrollFormCard.setVisible(false);
                        enrollFormCard.setManaged(false);
                        loadLearners.run();
                    } else {
                        showToast(toastBox, "❌ Error assigning learner to course.", "#DC2626", "#FEF2F2");
                    }
                });
            });
        });

        loadLearners.run();

        stage.setTitle("SkillVerse - Learners & Grading");
        stage.setScene(new Scene(root, 1280, 800));
        stage.show();
    }

    private static VBox buildLearnerRow(TrainingEnrollment te, VBox toastBox) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");

        HBox headerRow = new HBox(12);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        VBox infoBox = new VBox(2);
        Text nameText = new Text(te.getEmployeeName() != null ? te.getEmployeeName() : "Employee");
        nameText.setFont(Font.font("System", FontWeight.BOLD, 15));
        nameText.setFill(Color.web("#0F172A"));

        Text subText = new Text((te.getEmployeeEmail() != null ? te.getEmployeeEmail() : "") + " • " + (te.getEmployeeDepartment() != null ? te.getEmployeeDepartment() : "General") + " • Program: " + (te.getProgramTitle() != null ? te.getProgramTitle() : "Training"));
        subText.setFont(Font.font("System", 12));
        subText.setFill(Color.web("#64748B"));
        infoBox.getChildren().addAll(nameText, subText);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        String statusStr = te.getCompletionStatus() != null ? te.getCompletionStatus() : "IN_PROGRESS";
        Label statusBadge = new Label(statusStr);
        String badgeStyle = "COMPLETED".equalsIgnoreCase(statusStr)
                ? "-fx-background-color: #DCFCE7; -fx-text-fill: #16A34A;"
                : "-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF;";
        statusBadge.setStyle(badgeStyle + " -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 11px;");

        Button toggleExpandBtn = new Button("✏️ Grade & Update Progress");
        toggleExpandBtn.setStyle("-fx-background-color: #F8FAFC; -fx-text-fill: #1E60FF; -fx-border-color: #CBD5E1; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-weight: bold; -fx-cursor: hand;");

        headerRow.getChildren().addAll(infoBox, sp, statusBadge, toggleExpandBtn);

        
        VBox expandPanel = new VBox(14);
        expandPanel.setPadding(new Insets(16));
        expandPanel.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-border-color: #CBD5E1; -fx-border-width: 1.5px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.04), 8, 0, 0, 2);");
        expandPanel.setVisible(false);
        expandPanel.setManaged(false);

        GridPane controlsGrid = new GridPane();
        controlsGrid.setHgap(16);
        controlsGrid.setVgap(12);

     
        Label attValLabel = new Label(String.format("Attendance: %.0f%%", te.getAttendancePercentage()));
        attValLabel.setStyle("-fx-text-fill: #0F172A; -fx-font-weight: bold; -fx-font-size: 13px;");
        Slider attSlider = new Slider(0, 100, te.getAttendancePercentage());
        attSlider.setShowTickMarks(true);
        attSlider.setShowTickLabels(true);
        attSlider.setStyle("-fx-control-inner-background: #FFFFFF; -fx-accent: #1E60FF;");
        attSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            attValLabel.setText(String.format("Attendance: %.0f%%", newVal.doubleValue()));
        });

    
        Label scoreValLabel = new Label(String.format("Assessment Marks: %.0f / 100", te.getAssessmentScore()));
        scoreValLabel.setStyle("-fx-text-fill: #0F172A; -fx-font-weight: bold; -fx-font-size: 13px;");
        Slider scoreSlider = new Slider(0, 100, te.getAssessmentScore());
        scoreSlider.setShowTickMarks(true);
        scoreSlider.setShowTickLabels(true);
        scoreSlider.setStyle("-fx-control-inner-background: #FFFFFF; -fx-accent: #16A34A;");
        scoreSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            scoreValLabel.setText(String.format("Assessment Marks: %.0f / 100", newVal.doubleValue()));
        });

        
        Label statusLabel = new Label("Completion Status:");
        statusLabel.setStyle("-fx-text-fill: #0F172A; -fx-font-weight: bold; -fx-font-size: 13px;");
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("IN_PROGRESS", "COMPLETED", "ENROLLED", "DROPPED");
        statusCombo.setValue(statusStr);
        statusCombo.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-border-radius: 6px; -fx-text-fill: #0F172A; -fx-font-weight: bold;");

        controlsGrid.add(attValLabel, 0, 0);
        controlsGrid.add(attSlider, 0, 1);
        controlsGrid.add(scoreValLabel, 1, 0);
        controlsGrid.add(scoreSlider, 1, 1);
        controlsGrid.add(statusLabel, 2, 0);
        controlsGrid.add(statusCombo, 2, 1);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(40);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(40);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(20);
        controlsGrid.getColumnConstraints().addAll(c1, c2, c3);

        HBox saveRow = new HBox();
        saveRow.setAlignment(Pos.CENTER_RIGHT);
        Button saveGradingBtn = new Button("💾 Save Grade Updates");
        saveGradingBtn.setStyle("-fx-background-color: #16A34A; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 6px; -fx-cursor: hand;");

        saveRow.getChildren().add(saveGradingBtn);
        expandPanel.getChildren().addAll(controlsGrid, saveRow);

        toggleExpandBtn.setOnAction(e -> {
            boolean exp = !expandPanel.isVisible();
            expandPanel.setVisible(exp);
            expandPanel.setManaged(exp);
        });

        saveGradingBtn.setOnAction(e -> {
            double attendance = attSlider.getValue();
            double score = scoreSlider.getValue();
            String newStatus = statusCombo.getValue();
            String dateStr = "COMPLETED".equalsIgnoreCase(newStatus) ? LocalDate.now().toString() : "";

            saveGradingBtn.setDisable(true);
            saveGradingBtn.setText("Saving...");

            String eId = te.getEnrollmentId() != null ? te.getEnrollmentId() : "";

            FirebaseDAO.getInstance().updateTrainerGrading(eId, attendance, score, newStatus, dateStr).thenAccept(success -> {
                Platform.runLater(() -> {
                    saveGradingBtn.setDisable(false);
                    saveGradingBtn.setText("💾 Save Grade Updates");
                    if (success) {
                        statusBadge.setText(newStatus);
                        statusBadge.setStyle("COMPLETED".equalsIgnoreCase(newStatus)
                                ? "-fx-background-color: #DCFCE7; -fx-text-fill: #16A34A; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 11px;"
                                : "-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 11px;");
                        showToast(toastBox, "✅ Grades and progress saved for " + te.getEmployeeName() + "!", "#16A34A", "#F0FDF4");
                        expandPanel.setVisible(false);
                        expandPanel.setManaged(false);
                    } else {
                        showToast(toastBox, "❌ Failed to save grades to Firestore.", "#DC2626", "#FEF2F2");
                    }
                });
            });
        });

        card.getChildren().addAll(headerRow, expandPanel);
        return card;
    }

    private static void showToast(VBox toastContainer, String message, String textColor, String bgColor) {
        toastContainer.getChildren().clear();
        HBox toast = new HBox();
        toast.setPadding(new Insets(12, 16, 12, 16));
        toast.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: " + textColor + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        Label lbl = new Label(message);
        lbl.setStyle("-fx-text-fill: " + textColor + "; -fx-font-weight: bold; -fx-font-size: 13px;");
        toast.getChildren().add(lbl);
        toastContainer.getChildren().add(toast);
    }
}