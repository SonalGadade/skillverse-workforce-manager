package com.skillverse.employee.view;

import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeTask;
import com.skillverse.CommonFeatures.TrainingEnrollment;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class EmployeePerformanceView {

    private static final String BLUE = "#2563EB";
    private static final String PURPLE = "#7C3AED";
    private static final String GREEN = "#10B981";
    private static final String AMBER = "#D97706";
    private static final String TEXT = "#0F172A";
    private static final String BG = "#F8FAFC";
    private static final String BORDER = "#E5E7EB";

    public VBox createPerformanceContent(String email) {
        String userEmail = email != null && !email.isBlank() ? email : (UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "employee@skillverse.com");

        VBox page = new VBox(24);
        page.setPadding(new Insets(32, 40, 40, 40));
        page.setStyle("-fx-background-color: " + BG + ";");

        // Header
        HBox headerRow = new HBox(20);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        Text title = new Text("My Performance Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setFill(Color.web(TEXT));

        Text subtitle = new Text("Real-time telemetry aggregated from task completion, trainer grading, and attendance.");
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setFill(Color.web("#64748B"));

        titleBox.getChildren().addAll(title, subtitle);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Sync Performance Telemetry");
        refreshBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 16px;");

        headerRow.getChildren().addAll(titleBox, sp, refreshBtn);

        // Dynamic Metric Card Labels
        Label overallScoreLbl = new Label("0%");
        Label taskCompletionRateLbl = new Label("0%");
        Label avgAssessmentScoreLbl = new Label("0%");
        Label avgAttendanceLbl = new Label("0%");

        HBox statsRow = new HBox(16);
        statsRow.getChildren().addAll(
                createPerfCard("🏆 Overall Weighted Rating", overallScoreLbl, "(40% Tasks + 40% Tests + 20% Attendance)", "#74aef9", BLUE),
                createPerfCard("✅ Task Completion Rate", taskCompletionRateLbl, "Completed vs total goals", "#d363dd", GREEN),
                createPerfCard("🎓 Avg Assessment Score", avgAssessmentScoreLbl, "Graded by Technical Trainers", "#56f876", PURPLE),
                createPerfCard("⏱️ Training Attendance", avgAttendanceLbl, "Live sessions & seminars", "#f9d851", AMBER)
        );
        for (javafx.scene.Node n : statsRow.getChildren()) HBox.setHgrow(n, Priority.ALWAYS);

        // Breakdown Container
        VBox breakdownCard = new VBox(16);
        breakdownCard.setPadding(new Insets(20));
        breakdownCard.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");

        Text breakTitle = new Text("📊 Competency & Evaluation Breakdown");
        breakTitle.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        breakTitle.setFill(Color.web(TEXT));

        VBox breakdownList = new VBox(12);
        breakdownCard.getChildren().addAll(breakTitle, breakdownList);

        ScrollPane scrollPane = new ScrollPane(new VBox(24, headerRow, statsRow, breakdownCard));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        page.getChildren().add(scrollPane);

        Runnable loadPerformanceData = () -> {
            breakdownList.getChildren().setAll(new Label("🔄 Calculating performance metrics from Firestore..."));

            // 1. Fetch Tasks
            FirebaseDAO.getInstance().getTasksForEmployee(userEmail).thenAccept(tasks -> {
                int totalTasks = tasks != null ? tasks.size() : 0;
                int completedTasks = 0;
                if (tasks != null) {
                    for (EmployeeTask t : tasks) {
                        if ("COMPLETED".equalsIgnoreCase(t.getStatus())) completedTasks++;
                    }
                }
                double taskCompletionRate = totalTasks > 0 ? ((double) completedTasks / totalTasks) * 100.0 : 0.0;

                // 2. Fetch Enrollments & Assessments
                FirebaseDAO.getInstance().getEmployeeTrainingProgress(userEmail).thenAccept(enrollments -> {
                    double totalScore = 0;
                    double totalAtt = 0;
                    int count = enrollments != null ? enrollments.size() : 0;

                    if (enrollments != null && !enrollments.isEmpty()) {
                        for (TrainingEnrollment te : enrollments) {
                            totalScore += te.getAssessmentScore();
                            totalAtt += te.getAttendancePercentage();
                        }
                    }

                    double avgAssessment = count > 0 ? totalScore / count : 0.0;
                    double avgAttendance = count > 0 ? totalAtt / count : 0.0;

                    // Calculate Weighted Overall Score: 40% Tasks + 40% Assessments + 20% Attendance
                    double overallWeightedScore = (taskCompletionRate * 0.40) + (avgAssessment * 0.40) + (avgAttendance * 0.20);

                    Platform.runLater(() -> {
                        overallScoreLbl.setText(String.format("%.1f%%", overallWeightedScore));
                        taskCompletionRateLbl.setText(String.format("%.0f%%", taskCompletionRate));
                        avgAssessmentScoreLbl.setText(String.format("%.0f%%", avgAssessment));
                        avgAttendanceLbl.setText(String.format("%.0f%%", avgAttendance));

                        breakdownList.getChildren().clear();
                        breakdownList.getChildren().add(buildBreakdownRow("Tasks & Goals Execution (40% Weight)", (int) taskCompletionRate, BLUE));
                        breakdownList.getChildren().add(buildBreakdownRow("Trainer Assessment Scores (40% Weight)", (int) avgAssessment, PURPLE));
                        breakdownList.getChildren().add(buildBreakdownRow("Seminar & Class Attendance (20% Weight)", (int) avgAttendance, AMBER));
                        breakdownList.getChildren().add(buildBreakdownRow("Overall Composite Rating Score", (int) overallWeightedScore, GREEN));
                    });
                });
            });
        };

        refreshBtn.setOnAction(e -> loadPerformanceData.run());
        loadPerformanceData.run();

        return page;
    }

    private static VBox createPerfCard(String title, Label valLabel, String subtext, String bgColor, String textColor) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");

        Label t = new Label(title);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        t.setTextFill(Color.web(textColor));

        valLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        valLabel.setTextFill(Color.web(TEXT));

        Label sub = new Label(subtext);
        sub.setFont(Font.font("Arial", 11));
        sub.setTextFill(Color.web("#64748B"));

        card.getChildren().addAll(t, valLabel, sub);
        return card;
    }

    private static HBox buildBreakdownRow(String name, int pct, String colorHex) {
        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 14, 10, 14));
        row.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Text nameText = new Text(name);
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        nameText.setFill(Color.web(TEXT));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        ProgressBar pb = new ProgressBar(pct / 100.0);
        pb.setPrefWidth(220);
        pb.setStyle("-fx-accent: " + colorHex + ";");

        Text scoreText = new Text(pct + "%");
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        scoreText.setFill(Color.web(colorHex));

        row.getChildren().addAll(nameText, sp, pb, scoreText);
        return row;
    }


}
