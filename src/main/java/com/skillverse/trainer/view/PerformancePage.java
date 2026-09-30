
package com.skillverse.trainer.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.TrainingEnrollment;
import com.skillverse.trainer.model.Trainer;
import com.skillverse.CommonFeatures.UserSession;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class PerformancePage {

    public static void show(Stage stage, Trainer trainer) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + AppTheme.BG + ";");
        root.setLeft(TrainerSidebar.create(stage, trainer, "Performance"));

        VBox page = new VBox(20);
        page.setPadding(new Insets(24));

       
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        VBox heading = new VBox(4,
                AppTheme.title("Learner Performance & Course Analytics"),
                AppTheme.subtitle("Real-time telemetry on student course completion, test scores, and attendance rates.")
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Sync Live Performance");
        refreshBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 8px; -fx-cursor: hand;");

        header.getChildren().addAll(heading, spacer, refreshBtn);

       
VBox card1 = buildMetricCard(
        "🎯",
        "#1E60FF",
        "Avg. Course Completion",
        "60%"
);

VBox card2 = buildMetricCard(
        "💯",
        "#16A34A",
        "Avg. Test Score",
        "85 / 100"
);

VBox card3 = buildMetricCard(
        "👥",
        "#7C3AED",
        "Active Students",
        "4"
);

VBox card4 = buildMetricCard(
        "🎓",
        "#D97706",
        "Graduated / Certified",
        "5"
);

HBox statsRow = new HBox(14);

statsRow.getChildren().addAll(
        card1,
        card2,
        card3,
        card4
);



for (Node card : statsRow.getChildren()) {
    HBox.setHgrow(card, Priority.ALWAYS);
}

      
        VBox reportBox = new VBox(14);
        reportBox.setPadding(new Insets(20));
        reportBox.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");

        Text reportTitle = new Text("📊 Comprehensive Performance Distribution");
        reportTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        reportTitle.setFill(Color.web("#0F172A"));

        VBox reportContainer = new VBox(12);
        reportBox.getChildren().addAll(reportTitle, reportContainer);

        VBox scrollContent = new VBox(20, statsRow, reportBox);
        scrollContent.setOpacity(1.0);
        scrollContent.setStyle("-fx-background-color: " + AppTheme.BG + ";");

        ScrollPane scrollPane = new ScrollPane(scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setOpacity(1.0);
        scrollPane.setStyle("-fx-background-color: " + AppTheme.BG +
                "; -fx-background: " + AppTheme.BG +
                "; -fx-control-inner-background: " + AppTheme.BG + ";");

        page.setOpacity(1.0);
        page.setStyle("-fx-background-color: " + AppTheme.BG + ";");

        root.setOpacity(1.0);

        page.getChildren().addAll(header, scrollPane);
        root.setCenter(page);

        String trainerEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "trainer@skillverse.com";

        Runnable loadPerformance = () -> {
            reportContainer.getChildren().clear();
            ProgressIndicator spinner = new ProgressIndicator();
            reportContainer.getChildren().add(new HBox(10, new Label("Calculating analytics from Firestore..."), spinner));

            FirebaseDAO.getInstance().getEnrolledLearnersForTrainer(trainerEmail).thenAccept(enrollments -> {
                Platform.runLater(() -> {
                    reportContainer.getChildren().clear();
                    if (enrollments == null || enrollments.isEmpty()) {
                        reportContainer.getChildren().add(new Label("No learner performance metrics available yet."));
                    } else {
                        double totalAtt = 0;
                        double totalScore = 0;
                        int activeCount = 0;
                        int completedCount = 0;

                        for (TrainingEnrollment te : enrollments) {
                            totalAtt += te.getAttendancePercentage();
                            totalScore += te.getAssessmentScore();
                            if ("COMPLETED".equalsIgnoreCase(te.getCompletionStatus())) {
                                completedCount++;
                            } else {
                                activeCount++;
                            }

                           
                            VBox studentCard = new VBox(8);
                            studentCard.setPadding(new Insets(14));
                            studentCard.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8px; -fx-border-color: #E2E8F0; -fx-border-radius: 8px;");

                            HBox r1 = new HBox(10);
                            r1.setAlignment(Pos.CENTER_LEFT);
                            Text sName = new Text(te.getEmployeeName() != null ? te.getEmployeeName() : "Student");
                            sName.setFont(Font.font("System", FontWeight.BOLD, 14));

                            Label progLbl = new Label("Program: " + (te.getProgramTitle() != null ? te.getProgramTitle() : "General"));
                            progLbl.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");

                            Region sp = new Region();
                            HBox.setHgrow(sp, Priority.ALWAYS);

                            Label attBadge = new Label(String.format("Attendance: %.0f%%", te.getAttendancePercentage()));
                            attBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 4px;");

                            Label scoreBadge = new Label(String.format("Test Score: %.0f / 100", te.getAssessmentScore()));
                            scoreBadge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #16A34A; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 4px;");

                            r1.getChildren().addAll(sName, progLbl, sp, attBadge, scoreBadge);
                            studentCard.getChildren().add(r1);
                            reportContainer.getChildren().add(studentCard);
                        }

                        int size = enrollments.size();
                        double avgAtt = size > 0 ? totalAtt / size : 0;
                        double avgScore = size > 0 ? totalScore / size : 0;

                        ((Label) card1.getUserData()).setText(String.format("%.0f%%", avgAtt));
                        ((Label) card2.getUserData()).setText(String.format("%.0f / 100", avgScore));
                        ((Label) card3.getUserData()).setText(String.valueOf(activeCount));
                        ((Label) card4.getUserData()).setText(String.valueOf(completedCount));
                    }
                });
            });
        };

        refreshBtn.setOnAction(e -> loadPerformance.run());
        loadPerformance.run();

        stage.setTitle("SkillVerse - Performance");
        stage.setScene(new Scene(root, 1280, 800));
        stage.show();
    }

    private static VBox buildMetricCard(String icon, String color, String title, String initialVal) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");

        HBox top = new HBox(8);
        top.setAlignment(Pos.CENTER_LEFT);
        Label iconLbl = new Label(icon);
        iconLbl.setStyle("-fx-font-size: 18px;");

        Text titleText = new Text(title);
        titleText.setFont(Font.font("System", FontWeight.BOLD, 12));
        titleText.setFill(Color.web("#64748B"));

        top.getChildren().addAll(iconLbl, titleText);

        Label valLabel = new Label(initialVal);
        valLabel.setFont(Font.font("System", FontWeight.BOLD, 22));
        valLabel.setTextFill(Color.web(color));
        valLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 22px; -fx-font-weight: bold;");
        valLabel.setMinHeight(28);
        valLabel.setPrefHeight(30);
        valLabel.setVisible(true);
        valLabel.setManaged(true);

        card.getChildren().addAll(top, valLabel);
        card.setUserData(valLabel);
        return card;
    }
}