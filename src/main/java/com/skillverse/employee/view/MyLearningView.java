package com.skillverse.employee.view;

import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.SeminarEvent;
import com.skillverse.CommonFeatures.TrainerAssessment;
import com.skillverse.CommonFeatures.TrainingEnrollment;
import com.skillverse.CommonFeatures.TrainingProgram;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyLearningView {

    private static final String BLUE = "#2563EB";
    private static final String PURPLE = "#7C3AED";
    private static final String GREEN = "#10B981";
    private static final String TEXT = "#0F172A";
    private static final String BG = "#F8FAFC";
    private static final String BORDER = "#E5E7EB";

    public VBox createLearningContent(String email) {
        VBox content = new VBox(24);
        content.setPadding(new Insets(32, 40, 40, 40));
        content.setStyle("-fx-background-color: " + BG + ";");

        // 1. Header Banner & Quick Learning Stats
        HBox headerRow = new HBox(20);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        Text title = new Text("Learning Hub & Skill Development");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setFill(Color.web(TEXT));

        Text subtitle = new Text("Track your enrolled courses, live sessions, and assessments.");
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setFill(Color.web("#64748B"));

        titleBox.getChildren().addAll(title, subtitle);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Sync Learning Hub");
        refreshBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 16px;");

        headerRow.getChildren().addAll(titleBox, headerSpacer, refreshBtn);

        VBox toastBox = new VBox();

        // Top Metrics Row (3 compact summary cards)
        Label enrolledVal = new Label("0");
        Label attendanceVal = new Label("0%");
        Label pendingVal = new Label("0");

        HBox metricsRow = new HBox(16);
        metricsRow.getChildren().addAll(
                createSummaryMetricCard("📚", "Enrolled Courses", enrolledVal, "#EFF6FF", BLUE),
                createSummaryMetricCard("🎯", "Avg. Attendance", attendanceVal, "#F3E8FF", PURPLE),
                createSummaryMetricCard("📝", "Pending Assessments", pendingVal, "#FEF3C7", "#D97706")
        );
        for (javafx.scene.Node n : metricsRow.getChildren()) {
            HBox.setHgrow(n, Priority.ALWAYS);
        }

        HBox twoColumnGrid = new HBox(24);

        VBox leftColumn = new VBox(20);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        leftColumn.prefWidthProperty().bind(twoColumnGrid.widthProperty().multiply(0.59));

        VBox activeCoursesList = new VBox(12);
        VBox exploreList = new VBox(12);

        VBox activeCoursesCard = createWidgetCard("My Active Courses & Performance", activeCoursesList);
        VBox exploreCard = createWidgetCard("Explore & Enroll New Programs", exploreList);

        leftColumn.getChildren().addAll(activeCoursesCard, exploreCard);

        VBox rightColumn = new VBox(20);
        rightColumn.prefWidthProperty().bind(twoColumnGrid.widthProperty().multiply(0.39));

        VBox liveLecturesList = new VBox(12);
        VBox assessmentsList = new VBox(12);

        VBox liveLecturesWidget = createWidgetCard("🎥 Upcoming Live Lectures & Events", liveLecturesList);
        VBox assessmentsWidget = createWidgetCard("📝 Pending Assessments & Quizzes", assessmentsList);

        rightColumn.getChildren().addAll(liveLecturesWidget, assessmentsWidget);

        twoColumnGrid.getChildren().addAll(leftColumn, rightColumn);

        ScrollPane scrollPane = new ScrollPane(new VBox(20, headerRow, metricsRow, toastBox, twoColumnGrid));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        content.getChildren().add(scrollPane);

        String userEmail = email != null ? email : (UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "");
        User currentUser = UserSession.getCurrentUser();
        String dept = currentUser != null ? currentUser.getDepartment() : "ALL";

        Runnable loadData = () -> {
            activeCoursesList.getChildren().setAll(new Label("🔄 Loading active courses..."));
            exploreList.getChildren().setAll(new Label("🔄 Loading available programs..."));
            liveLecturesList.getChildren().setAll(new Label("🔄 Loading upcoming live sessions..."));
            assessmentsList.getChildren().setAll(new Label("🔄 Loading due assessments..."));

            FirebaseDAO.getInstance().getEmployeeTrainingProgress(userEmail).thenAccept(myEnrollments -> {
                Platform.runLater(() -> {
                    int enrolledCount = myEnrollments != null ? myEnrollments.size() : 0;
                    double totalAtt = 0;
                    if (myEnrollments != null) {
                        for (TrainingEnrollment te : myEnrollments) {
                            totalAtt += te.getAttendancePercentage();
                        }
                    }
                    enrolledVal.setText(String.valueOf(enrolledCount));
                    attendanceVal.setText(String.format("%.0f%%", enrolledCount > 0 ? totalAtt / enrolledCount : 0));

                    renderActiveCourses(activeCoursesList, myEnrollments);
                });

                FirebaseDAO.getInstance().getAllTrainingPrograms().thenAccept(allPrograms -> {
                    Platform.runLater(() -> renderExploreCatalog(exploreList, allPrograms, myEnrollments, userEmail, activeCoursesList, toastBox));
                });
            });

            FirebaseDAO.getInstance().getAllSeminarEvents().thenAccept(events -> {
                Platform.runLater(() -> renderLiveLecturesWidget(liveLecturesList, events));
            });

            FirebaseDAO.getInstance().getAssessmentsForEmployee(dept).thenAccept(assessments -> {
                Platform.runLater(() -> {
                    pendingVal.setText(String.valueOf(assessments != null ? assessments.size() : 0));
                    renderAssessmentsWidget(assessmentsList, assessments);
                });
            });
        };

        refreshBtn.setOnAction(e -> loadData.run());
        loadData.run();

        return content;
    }

    private static VBox createSummaryMetricCard(String icon, String label, Label valLabel, String bgColor, String textColor) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");

        HBox top = new HBox(8);
        top.setAlignment(Pos.CENTER_LEFT);

        Label i = new Label(icon);
        i.setStyle("-fx-font-size: 18px;");

        Text l = new Text(label);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        l.setFill(Color.web("#64748B"));

        top.getChildren().addAll(i, l);

        valLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        valLabel.setTextFill(Color.web(textColor));

        card.getChildren().addAll(top, valLabel);
        return card;
    }

    private static VBox createWidgetCard(String titleText, VBox container) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");

        Text title = new Text(titleText);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        title.setFill(Color.web(TEXT));

        card.getChildren().addAll(title, container);
        return card;
    }

    private static void renderActiveCourses(VBox container, List<TrainingEnrollment> enrollments) {
        container.getChildren().clear();
        if (enrollments == null || enrollments.isEmpty()) {
            Label empty = new Label("No active course enrollments yet. Browse available programs below to enroll!");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (TrainingEnrollment te : enrollments) {
            VBox card = new VBox(12);
            card.setPadding(new Insets(16));
            card.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            HBox header = new HBox(12);
            header.setAlignment(Pos.CENTER_LEFT);

            VBox inf = new VBox(2);
            Text title = new Text(te.getProgramTitle() != null ? te.getProgramTitle() : "Training Program");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            title.setFill(Color.web(TEXT));

            Text sub = new Text("Department: " + (te.getEmployeeDepartment() != null ? te.getEmployeeDepartment() : "General"));
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(title, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            boolean isDone = "COMPLETED".equalsIgnoreCase(te.getCompletionStatus());
            Label statusBadge = new Label(isDone ? "COMPLETED" : "IN PROGRESS");
            statusBadge.setStyle(isDone
                    ? "-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-weight: bold; -fx-padding: 3px 10px; -fx-background-radius: 10px; -fx-font-size: 11px;"
                    : "-fx-background-color: #DBEAFE; -fx-text-fill: #1E40AF; -fx-font-weight: bold; -fx-padding: 3px 10px; -fx-background-radius: 10px; -fx-font-size: 11px;");

            header.getChildren().addAll(inf, sp, statusBadge);

            HBox progressRow = new HBox(16);
            progressRow.setAlignment(Pos.CENTER_LEFT);

            double att = te.getAttendancePercentage();
            double score = te.getAssessmentScore();

            VBox p1 = new VBox(3);
            Text l1 = new Text(String.format("Attendance: %.0f%%", att));
            l1.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            l1.setFill(Color.web(BLUE));
            ProgressBar pb1 = new ProgressBar(att / 100.0);
            pb1.setPrefWidth(150);
            pb1.setStyle("-fx-accent: #2563EB;");
            p1.getChildren().addAll(l1, pb1);

            VBox p2 = new VBox(3);
            Text l2 = new Text(String.format("Assessment Score: %.0f%%", score));
            l2.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            l2.setFill(Color.web(GREEN));
            ProgressBar pb2 = new ProgressBar(score / 100.0);
            pb2.setPrefWidth(150);
            pb2.setStyle("-fx-accent: #10B981;");
            p2.getChildren().addAll(l2, pb2);

            Region sp2 = new Region();
            HBox.setHgrow(sp2, Priority.ALWAYS);

            Button continueBtn = new Button("Continue Course →");
            continueBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-size: 12px;");
            continueBtn.setOnAction(e -> {
                new Alert(Alert.AlertType.INFORMATION, "Continuing Course: " + te.getProgramTitle()).showAndWait();
            });

            progressRow.getChildren().addAll(p1, p2, sp2, continueBtn);
            card.getChildren().addAll(header, progressRow);
            container.getChildren().add(card);
        }
    }

    private static void renderExploreCatalog(VBox container, List<TrainingProgram> allPrograms, List<TrainingEnrollment> myEnrollments, String userEmail, VBox activeList, VBox toastBox) {
        container.getChildren().clear();
        if (allPrograms == null || allPrograms.isEmpty()) {
            Label empty = new Label("No new programs available for enrollment.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        Set<String> enrolledIds = new HashSet<>();
        if (myEnrollments != null) {
            for (TrainingEnrollment te : myEnrollments) {
                if (te.getProgramId() != null) {
                    enrolledIds.add(te.getProgramId());
                }
            }
        }

        for (TrainingProgram tp : allPrograms) {
            HBox row = new HBox(14);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(12, 14, 12, 14));
            row.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

            VBox inf = new VBox(2);
            Text name = new Text(tp.getTitle() != null ? tp.getTitle() : "Course");
            name.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            name.setFill(Color.web(TEXT));

            Text meta = new Text("Instructor: " + (tp.getAssignedTrainerName() != null ? tp.getAssignedTrainerName() : "Trainer") + " • Dept: " + (tp.getDepartment() != null ? tp.getDepartment() : "General"));
            meta.setFont(Font.font("Arial", 11));
            meta.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(name, meta);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Button enrollBtn = new Button();
            if (enrolledIds.contains(tp.getProgramId())) {
                enrollBtn.setText("✓ Enrolled");
                enrollBtn.setDisable(true);
                enrollBtn.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-weight: bold; -fx-padding: 5px 12px; -fx-background-radius: 6px;");
            } else {
                enrollBtn.setText("+ Enroll Now");
                enrollBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5px 12px; -fx-background-radius: 6px; -fx-cursor: hand;");
                enrollBtn.setOnAction(e -> {
                    TrainingEnrollment newTe = new TrainingEnrollment();
                    newTe.setProgramId(tp.getProgramId());
                    newTe.setProgramTitle(tp.getTitle());
                    newTe.setEmployeeEmail(userEmail);
                    newTe.setEmployeeName(UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getFullName() : "Employee");
                    newTe.setEmployeeDepartment(UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getDepartment() : "General");
                    newTe.setAttendancePercentage(0.0);
                    newTe.setAssessmentScore(0.0);
                    newTe.setCompletionStatus("ENROLLED");

                    FirebaseDAO.getInstance().enrollEmployee(newTe).thenAccept(ok -> {
                        Platform.runLater(() -> {
                            if (ok) {
                                showToast(toastBox, "✅ Enrolled in '" + tp.getTitle() + "'!", "#15803D", "#DCFCE7");
                                enrollBtn.setText("✓ Enrolled");
                                enrollBtn.setDisable(true);
                                enrollBtn.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-weight: bold; -fx-padding: 5px 12px; -fx-background-radius: 6px;");
                                FirebaseDAO.getInstance().getEmployeeTrainingProgress(userEmail).thenAccept(newList -> {
                                    Platform.runLater(() -> renderActiveCourses(activeList, newList));
                                });
                            }
                        });
                    });
                });
            }

            row.getChildren().addAll(inf, sp, enrollBtn);
            container.getChildren().add(row);
        }
    }

    private static void renderLiveLecturesWidget(VBox container, List<SeminarEvent> events) {
        container.getChildren().clear();
        if (events == null || events.isEmpty()) {
            Label empty = new Label("No live seminars scheduled at the moment.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 12px;");
            container.getChildren().add(empty);
            return;
        }

        for (SeminarEvent event : events) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(14));
            card.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            HBox r1 = new HBox(8);
            r1.setAlignment(Pos.CENTER_LEFT);

            Label dateBadge = new Label("📅 " + (event.getEventDate() != null ? event.getEventDate() : "Aug 30") + " • " + (event.getEventTime() != null ? event.getEventTime() : "10:00 AM"));
            dateBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 4px; -fx-font-size: 11px;");

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Button joinBtn = new Button("Join Live Lecture ↗");
            joinBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 12px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-size: 11px;");
            joinBtn.setOnAction(e -> {
                String link = event.getMeetingLinkOrVenue() != null && !event.getMeetingLinkOrVenue().isBlank() ? event.getMeetingLinkOrVenue() : "https://meet.google.com";
                try {
                    if (link.startsWith("http://") || link.startsWith("https://")) {
                        Desktop.getDesktop().browse(new URI(link));
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Meeting Venue: " + link).showAndWait();
                    }
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.INFORMATION, "Live Link: " + link).showAndWait();
                }
            });

            r1.getChildren().addAll(dateBadge, sp, joinBtn);

            Text topicText = new Text(event.getTitle() != null ? event.getTitle() : "Live Seminar");
            topicText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            topicText.setFill(Color.web(TEXT));

            Text trainerText = new Text("Trainer: " + (event.getTrainerName() != null ? event.getTrainerName() : "Technical Trainer"));
            trainerText.setFont(Font.font("Arial", 11));
            trainerText.setFill(Color.web("#64748B"));

            card.getChildren().addAll(r1, topicText, trainerText);
            container.getChildren().add(card);
        }
    }

    private static void renderAssessmentsWidget(VBox container, List<TrainerAssessment> assessments) {
        container.getChildren().clear();
        if (assessments == null || assessments.isEmpty()) {
            Label empty = new Label("No pending assessments. You are all caught up!");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 12px;");
            container.getChildren().add(empty);
            return;
        }

        for (TrainerAssessment ta : assessments) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(14));
            card.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            Text titleText = new Text(ta.getTitle() != null ? ta.getTitle() : "Assessment");
            titleText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            titleText.setFill(Color.web(TEXT));

            Text courseText = new Text("Course: " + (ta.getCourseName() != null ? ta.getCourseName() : "General"));
            courseText.setFont(Font.font("Arial", 11));
            courseText.setFill(Color.web("#64748B"));

            HBox r2 = new HBox(8);
            r2.setAlignment(Pos.CENTER_LEFT);

            Label timeBadge = new Label("⏱️ " + (ta.getDurationMinutes() != null ? ta.getDurationMinutes() : "60") + " Mins");
            timeBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 3px 6px; -fx-background-radius: 4px; -fx-font-size: 10px;");

            Label marksBadge = new Label("🎯 Marks: " + ta.getTotalMarks());
            marksBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 3px 6px; -fx-background-radius: 4px; -fx-font-size: 10px;");

            Label dueBadge = new Label("⏳ Due: " + (ta.getDeadlineDate() != null ? ta.getDeadlineDate() : "Open"));
            dueBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-padding: 3px 6px; -fx-background-radius: 4px; -fx-font-size: 10px;");

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Button startBtn = new Button("Start Assessment →");
            startBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 12px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-size: 11px;");
            startBtn.setOnAction(e -> {
                new Alert(Alert.AlertType.INFORMATION, "Starting Test: " + ta.getTitle() + "\nCourse: " + ta.getCourseName()).showAndWait();
            });

            r2.getChildren().addAll(timeBadge, marksBadge, dueBadge, sp, startBtn);

            card.getChildren().addAll(titleText, courseText, r2);
            container.getChildren().add(card);
        }
    }

    private static void showToast(VBox toastContainer, String message, String textColor, String bgColor) {
        toastContainer.getChildren().clear();
        HBox toast = new HBox();
        toast.setPadding(new Insets(10, 14, 10, 14));
        toast.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: " + textColor + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        Label lbl = new Label(message);
        lbl.setStyle("-fx-text-fill: " + textColor + "; -fx-font-weight: bold; -fx-font-size: 12px;");
        toast.getChildren().add(lbl);
        toastContainer.getChildren().add(toast);
    }
}
