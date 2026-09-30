package com.skillverse.manager.view;

import com.skillverse.CommonFeatures.User;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeFeedback;
import com.skillverse.CommonFeatures.EmployeeTask;
import com.skillverse.CommonFeatures.TrainingEnrollment;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class ManagerMemberDetailView extends VBox {

    private static final String BLUE = "#2563EB";
    private static final String GREEN = "#10B981";
    private static final String AMBER = "#D97706";
    private static final String RED = "#DC2626";
    private static final String TEXT = "#0F172A";
    private static final String MUTED = "#64748B";
    private static final String BG = "#F8FAFC";
    private static final String BORDER = "#E2E8F0";

    public ManagerMemberDetailView(User member, double overallScore, int compTasks, int totalTasks, double trainScore, Runnable onBackToMatrix) {
        setSpacing(24);
        setPadding(new Insets(32, 40, 40, 40));
        setStyle("-fx-background-color: " + BG + ";");

       
        HBox navBar = new HBox(16);
        navBar.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("← Back to Team Matrix");
        backBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8px 16px; -fx-background-radius: 8px; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackToMatrix != null) onBackToMatrix.run();
        });

        navBar.getChildren().add(backBtn);

       
        VBox headerBox = new VBox(6);
        Text title = new Text("Employee Detailed Profile & Matrix");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title.setFill(Color.web(TEXT));

        String memName = member.getFullName() != null ? member.getFullName() : member.getEmail();
        Text subtitle = new Text("Detailed performance tracking, active tasks checklist, and training enrollments for " + memName + ".");
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setFill(Color.web(MUTED));

        headerBox.getChildren().addAll(title, subtitle);

       
        VBox profileCard = new VBox(16);
        profileCard.setPadding(new Insets(24));
        profileCard.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");

        HBox profHeader = new HBox(16);
        profHeader.setAlignment(Pos.CENTER_LEFT);

        Circle av = new Circle(26, Color.web("#2563EB"));
        String init = !memName.isBlank() ? memName.substring(0, 1).toUpperCase() : "E";
        Text initTxt = new Text(init);
        initTxt.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        initTxt.setFill(Color.WHITE);
        StackPane avStack = new StackPane(av, initTxt);

        VBox profText = new VBox(4);
        Text nameTxt = new Text(memName);
        nameTxt.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nameTxt.setFill(Color.web(TEXT));

        Text roleTxt = new Text((member.getRole() != null ? member.getRole() : "Employee") + " • " + (member.getDepartment() != null ? member.getDepartment() : "Department") + " • " + member.getEmail());
        roleTxt.setFont(Font.font("Arial", 13));
        roleTxt.setFill(Color.web(MUTED));

        profText.getChildren().addAll(nameTxt, roleTxt);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label perfBadge = new Label(String.format("%.0f%% Performance Score", overallScore));
        if (overallScore >= 85.0) {
            perfBadge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 12px; -fx-font-size: 13px;");
        } else if (overallScore >= 60.0) {
            perfBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 12px; -fx-font-size: 13px;");
        } else {
            perfBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 12px; -fx-font-size: 13px;");
        }

        profHeader.getChildren().addAll(avStack, profText, sp, perfBadge);

       
        HBox summaryStrip = new HBox(20);
        summaryStrip.setPadding(new Insets(12, 16, 12, 16));
        summaryStrip.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px;");

        Label taskMetric = new Label("📌 Tasks: " + compTasks + " / " + totalTasks + " Completed");
        taskMetric.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #334155;");

        Label trainMetric = new Label("🎓 Training Assessment: " + String.format("%.0f%%", trainScore));
        trainMetric.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #334155;");

        summaryStrip.getChildren().addAll(taskMetric, trainMetric);
        profileCard.getChildren().addAll(profHeader, summaryStrip);

      
        VBox tasksCard = new VBox(16);
        tasksCard.setPadding(new Insets(24));
        tasksCard.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");

        Text tasksHeader = new Text("📋 Assigned Tasks & Deliverables");
        tasksHeader.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tasksHeader.setFill(Color.web(TEXT));

        VBox tasksContainer = new VBox(10);
        tasksContainer.getChildren().add(new Label("🔄 Loading employee tasks from Firestore..."));
        tasksCard.getChildren().addAll(tasksHeader, tasksContainer);

        
        VBox trainingCard = new VBox(16);
        trainingCard.setPadding(new Insets(24));
        trainingCard.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");

        Text trainingHeader = new Text("🎓 Training Enrollments & Course Progress");
        trainingHeader.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        trainingHeader.setFill(Color.web(TEXT));

        VBox trainingContainer = new VBox(10);
        trainingContainer.getChildren().add(new Label("🔄 Loading training enrollments from Firestore..."));
        trainingCard.getChildren().addAll(trainingHeader, trainingContainer);

       
        VBox feedbackCard = new VBox(16);
        feedbackCard.setPadding(new Insets(24));
        feedbackCard.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");

        Text feedbackHeader = new Text("✉ Performance Feedback Reviews");
        feedbackHeader.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        feedbackHeader.setFill(Color.web(TEXT));

        VBox feedbackContainer = new VBox(10);
        feedbackContainer.getChildren().add(new Label("🔄 Loading feedback reviews from Firestore..."));
        feedbackCard.getChildren().addAll(feedbackHeader, feedbackContainer);

     
        String memEmail = member.getEmail() != null ? member.getEmail().toLowerCase().trim() : "";
        FirebaseDAO.getInstance().getTasksForEmployee(memEmail).thenAccept(tasks -> {
            Platform.runLater(() -> {
                tasksContainer.getChildren().clear();
                if (tasks == null || tasks.isEmpty()) {
                    Label empty = new Label("No assigned tasks found for this employee.");
                    empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 13px;");
                    tasksContainer.getChildren().add(empty);
                } else {
                    for (EmployeeTask t : tasks) {
                        HBox row = new HBox(12);
                        row.setAlignment(Pos.CENTER_LEFT);
                        row.setPadding(new Insets(10, 14, 10, 14));
                        row.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8px; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px;");

                        boolean isComp = "COMPLETED".equalsIgnoreCase(t.getStatus());
                        Label tTitle = new Label(t.getTitle() != null ? t.getTitle() : "Task");
                        tTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + (isComp ? "#94A3B8;" : "#1E293B;") + (isComp ? " -fx-strikethrough: true;" : ""));

                        Region rSp = new Region(); HBox.setHgrow(rSp, Priority.ALWAYS);

                        Label badge = new Label(isComp ? "COMPLETED" : "IN PROGRESS");
                        badge.setStyle(isComp
                                ? "-fx-background-color: #DCFCE7; -fx-text-fill: #166534; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 8px; -fx-font-size: 11px;"
                                : "-fx-background-color: #FEF3C7; -fx-text-fill: #92400E; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 8px; -fx-font-size: 11px;");

                        row.getChildren().addAll(tTitle, rSp, badge);
                        tasksContainer.getChildren().add(row);
                    }
                }
            });
        });

        FirebaseDAO.getInstance().getEnrollmentsForEmployee(memEmail).thenAccept(enrollments -> {
            Platform.runLater(() -> {
                trainingContainer.getChildren().clear();
                if (enrollments == null || enrollments.isEmpty()) {
                    Label empty = new Label("No training enrollments found for this employee.");
                    empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 13px;");
                    trainingContainer.getChildren().add(empty);
                } else {
                    for (TrainingEnrollment te : enrollments) {
                        HBox row = new HBox(12);
                        row.setAlignment(Pos.CENTER_LEFT);
                        row.setPadding(new Insets(10, 14, 10, 14));
                        row.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8px; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px;");

                        VBox textB = new VBox(2);
                        Label cTitle = new Label(te.getProgramTitle() != null ? te.getProgramTitle() : "Training Program");
                        cTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0F172A;");
                        Label cSub = new Label("Status: " + (te.getCompletionStatus() != null ? te.getCompletionStatus() : "ENROLLED") + " • Attendance: " + (int) te.getAttendancePercentage() + "%");
                        cSub.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");
                        textB.getChildren().addAll(cTitle, cSub);

                        Region rSp = new Region(); HBox.setHgrow(rSp, Priority.ALWAYS);

                        Label scoreLbl = new Label("Score: " + (int) te.getAssessmentScore() + "%");
                        scoreLbl.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 8px; -fx-font-size: 12px;");

                        row.getChildren().addAll(textB, rSp, scoreLbl);
                        trainingContainer.getChildren().add(row);
                    }
                }
            });
        });

        FirebaseDAO.getInstance().getFeedbackForEmployee(memEmail).thenAccept(feedbacks -> {
            Platform.runLater(() -> {
                feedbackContainer.getChildren().clear();
                if (feedbacks == null || feedbacks.isEmpty()) {
                    Label empty = new Label("No recorded performance feedback reviews yet.");
                    empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 13px;");
                    feedbackContainer.getChildren().add(empty);
                } else {
                    for (EmployeeFeedback fb : feedbacks) {
                        VBox box = new VBox(4);
                        box.setPadding(new Insets(10, 14, 10, 14));
                        box.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8px; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px;");

                        HBox topFB = new HBox(10);
                        topFB.setAlignment(Pos.CENTER_LEFT);
                        Label rev = new Label("Reviewer: " + (fb.getReviewerName() != null ? fb.getReviewerName() : "Manager") + " (" + (fb.getReviewerRole() != null ? fb.getReviewerRole() : "Manager") + ")");
                        rev.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #0F172A;");

                        Region fSp = new Region(); HBox.setHgrow(fSp, Priority.ALWAYS);

                        Label starLbl = new Label("⭐ " + fb.getRating() + " / 5");
                        starLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #D97706; -fx-font-size: 12px;");
                        topFB.getChildren().addAll(rev, fSp, starLbl);

                        Label txtLbl = new Label("\"" + (fb.getFeedbackText() != null ? fb.getFeedbackText() : "") + "\"");
                        txtLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #334155; -fx-font-style: italic;");

                        box.getChildren().addAll(topFB, txtLbl);
                        feedbackContainer.getChildren().add(box);
                    }
                }
            });
        });

        ScrollPane scrollPane = new ScrollPane(new VBox(24, navBar, headerBox, profileCard, tasksCard, trainingCard, feedbackCard));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");

        getChildren().add(scrollPane);
    }
}
