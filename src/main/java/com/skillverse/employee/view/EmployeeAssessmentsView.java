package com.skillverse.employee.view;

import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.TrainerAssessment;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class EmployeeAssessmentsView {

    private static final String TEXT = "#111827";
    private static final String BG = "#F8F8FD";
    private static final String BORDER = "#E7E8F0";

    public VBox createAssessmentsContent(String email) {
        VBox content = new VBox(25);
        content.setPadding(new Insets(35, 45, 40, 45));
        content.setStyle("-fx-background-color:" + BG + ";");

        // Title
        Label title = new Label("ASSESSMENTS & SKILL TESTS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(Color.web(TEXT));

        Label subtitle = new Label("Complete active trainer assessments, skill checks, and view your evaluation results.");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setTextFill(Color.web("#374151"));

        content.getChildren().addAll(title, subtitle);

        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        User currentUser = UserSession.getCurrentUser();
        String dept = currentUser != null ? currentUser.getDepartment() : "ALL";

        Label statusLbl = new Label("📝 Active Tests & Quizzes for " + (dept != null ? dept : "All Departments"));
        statusLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        statusLbl.setTextFill(Color.web("#1E60FF"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Sync Assessments");
        refreshBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 16px;");

        topBar.getChildren().addAll(statusLbl, sp, refreshBtn);
        content.getChildren().addAll(topBar, new Separator());

        // Assessment Cards Container
        VBox assessmentsContainer = new VBox(16);
        ScrollPane scrollPane = new ScrollPane(assessmentsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        content.getChildren().add(scrollPane);

        Runnable loadAssessments = () -> {
            assessmentsContainer.getChildren().clear();
            ProgressIndicator spinner = new ProgressIndicator();
            assessmentsContainer.getChildren().add(new HBox(10, new Label("Loading assigned tests from Firestore..."), spinner));

            FirebaseDAO.getInstance().getAssessmentsForEmployee(dept).thenAccept(assessments -> {
                Platform.runLater(() -> {
                    assessmentsContainer.getChildren().clear();
                    if (assessments == null || assessments.isEmpty()) {
                        VBox emptyCard = new VBox(12);
                        emptyCard.setPadding(new Insets(30));
                        emptyCard.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");
                        Label noTest = new Label("📝 No Pending Assessments Assigned");
                        noTest.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                        noTest.setTextFill(Color.web("#64748B"));
                        emptyCard.getChildren().add(noTest);
                        assessmentsContainer.getChildren().add(emptyCard);
                    } else {
                        for (TrainerAssessment assessment : assessments) {
                            assessmentsContainer.getChildren().add(buildAssessmentRow(assessment));
                        }
                    }
                });
            });
        };

        refreshBtn.setOnAction(e -> loadAssessments.run());
        loadAssessments.run();

        return content;
    }

    private static VBox buildAssessmentRow(TrainerAssessment ta) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");

        HBox r1 = new HBox(12);
        r1.setAlignment(Pos.CENTER_LEFT);

        Label marksBadge = new Label("💯 Marks: " + ta.getTotalMarks());
        marksBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Label timeBadge = new Label("⏱️ Duration: " + (ta.getDurationMinutes() != null ? ta.getDurationMinutes() : "60") + " Mins");
        timeBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Label deadlineBadge = new Label("⏳ Deadline: " + (ta.getDeadlineDate() != null ? ta.getDeadlineDate() : "Open"));
        deadlineBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button startBtn = new Button("✍️ Start Assessment");
        startBtn.setStyle("-fx-background-color: #16A34A; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
        startBtn.setOnAction(e -> {
            new Alert(Alert.AlertType.INFORMATION, "Starting Test: " + ta.getTitle() + "\nDuration: " + ta.getDurationMinutes() + " mins").showAndWait();
        });

        r1.getChildren().addAll(marksBadge, timeBadge, deadlineBadge, sp, startBtn);

        Label titleText = new Label(ta.getTitle() != null ? ta.getTitle() : "Skill Test");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleText.setTextFill(Color.web("#0F172A"));

        Label subText = new Label("Course: " + (ta.getCourseName() != null ? ta.getCourseName() : "Technical Training") + " • Target: " + (ta.getTargetDepartment() != null ? ta.getTargetDepartment() : "ALL"));
        subText.setFont(Font.font("Arial", 13));
        subText.setTextFill(Color.web("#64748B"));

        card.getChildren().addAll(r1, titleText, subText);
        return card;
    }
}
