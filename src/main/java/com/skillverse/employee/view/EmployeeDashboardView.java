package com.skillverse.employee.view;

import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeSkill;
import com.skillverse.CommonFeatures.EmployeeTask;
import com.skillverse.CommonFeatures.TrainerAnnouncement;
import com.skillverse.CommonFeatures.TrainingEnrollment;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EmployeeDashboardView {

    private static final String BLUE = "#1648C8";
    private static final String PURPLE = "#7C3AED";
    private static final String GREEN = "#059669";
    private static final String ORANGE = "#E28A00";
    private static final String DARK = "#111827";
    private static final String TEXT = "#374151";
    private static final String MUTED = "#6B7280";
    private static final String BG = "#F8F8FD";
    private static final String BORDER = "#E7E8F0";
    private static final String LIGHT_BLUE = "#E8F0FF";

    private BorderPane root;
    private Stage stage;
    private String email;

    public void show(Stage stage, String email) {
        this.stage = stage;
        this.email = email != null && !email.isBlank() ? email : (UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "");

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + BG + ";");
        root.setLeft(createSidebar(root));

        showDashboardContent();

        Scene scene = new Scene(root, 1500, 900);
        stage.setTitle("SkillVerse | Employee Dashboard");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    private void showDashboardContent() {
        root.setCenter(null);
        VBox content = createDashboardContent();
        root.setCenter(createScrollPane(content));
    }

    private void showContent(VBox content) {
        root.setCenter(null);
        root.setCenter(createScrollPane(content));
    }

    private ScrollPane createScrollPane(VBox content) {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        return scrollPane;
    }

    private VBox createSidebar(Node eventsNode) {
        VBox sidebar = new VBox(7);
        sidebar.setPrefWidth(275);
        sidebar.setMinWidth(275);
        sidebar.setMaxWidth(275);
        sidebar.setPadding(new Insets(28, 17, 20, 17));
        sidebar.setStyle("-fx-background-color: white; -fx-border-color: transparent " + BORDER + " transparent transparent;");

        // BRAND
        VBox brand = new VBox(3);
        brand.setPadding(new Insets(0, 12, 18, 12));

        HBox logoRow = new HBox(12);
        logoRow.setAlignment(Pos.CENTER_LEFT);

        Label logoIcon = new Label("✣");
        logoIcon.setPrefSize(44, 44);
        logoIcon.setAlignment(Pos.CENTER);
        logoIcon.setStyle("-fx-background-color: " + LIGHT_BLUE + "; -fx-background-radius: 12; -fx-text-fill: " + BLUE + "; -fx-font-size: 20px; -fx-font-weight: bold;");

        VBox logoText = new VBox(0);
        Label logo = new Label("SkillVerse");
        logo.setStyle("-fx-text-fill: " + BLUE + "; -fx-font-size: 27px; -fx-font-weight: bold;");

        Label subtitle = new Label("Talent Ecosystem");
        subtitle.setStyle("-fx-text-fill: " + DARK + "; -fx-font-size: 12px;");

        logoText.getChildren().addAll(logo, subtitle);
        logoRow.getChildren().addAll(logoIcon, logoText);
        brand.getChildren().add(logoRow);

        // DASHBOARD
        Button dashboard = createMenuButton("▦", "Dashboard", true);
        dashboard.setOnAction(e -> showDashboardContent());

        // PROFILE
        Button profile = createMenuButton("♙", "Profile", false);
        profile.setOnAction(e -> showContent(new MyProfileView(email).createProfileContent(email)));

        // SKILLS
        Button skills = createMenuButton("♧", "Skills", false);
        skills.setOnAction(e -> showContent(new MySkillsView().createSkillsContent(email)));

        // LEARNING
        Button learning = createMenuButton("◇", "Learning", false);
        learning.setOnAction(e -> showContent(new MyLearningView().createLearningContent(email)));

        // ASSESSMENTS
        Button assessmentsBtn = createMenuButton("📝", "Assessments", false);
        assessmentsBtn.setOnAction(e -> showContent(new EmployeeAssessmentsView().createAssessmentsContent(email)));

        // TASKS
        Button tasks = createMenuButton("▣", "Tasks", false);
        tasks.setOnAction(e -> showContent(new EmployeeTasksView(email).createTasksContent(email)));

        // TEAM
        Button team = createMenuButton("♧", "Team", false);
        team.setOnAction(e -> showContent(new MyTeamView().createTeamContent(email)));

        // PERFORMANCE
        Button performance = createMenuButton("↗", "Performance", false);
        performance.setOnAction(e -> showPerformanceContent());

        // GOALS
        Button goals = createMenuButton("⚑", "Goals", false);
        goals.setOnAction(e -> showContent(new EmployeeTasksView(email).createTasksContent(email)));

        // ACHIEVEMENTS
        Button achievements = createMenuButton("✪", "Achievements", false);
        achievements.setOnAction(e -> showContent(new MyAchievementsView().createContent()));

        // ATTENDANCE
        Button attendance = createMenuButton("▣", "Attendance", false);
        attendance.setOnAction(e -> new MyAttendanceView().show(stage, email));

        // LEAVE
        Button leave = createMenuButton("✓", "Leave", false);
        leave.setOnAction(e -> showContent(new MyLeaveView().createLeaveContent(email)));

        // DOCUMENTS
        Button documents = createMenuButton("▤", "Documents", false);
        documents.setOnAction(e -> showContent(new MyDocumentsView().createDocumentsContent(stage, email)));

        // EVENTS
        Button events = createMenuButton("▣", "Events", false);
        events.setOnAction(e -> showContent(new MyEventsView().createEventsContent(email)));

        // ANNOUNCEMENTS
        Button announcements = createMenuButton("♢", "Announcements", false);
        announcements.setOnAction(e -> showContent(new AnnouncementsView().createAnnouncementsContent(email)));

        // FEEDBACK
        Button feedbackBtn = createMenuButton("✉", "Feedback", false);
        feedbackBtn.setOnAction(e -> showContent(new EmployeeFeedbackView(email).createFeedbackContent(email)));

        // SPEAK UP
        Button speakUpBtn = createMenuButton("📢", "Speak Up Box", false);
        speakUpBtn.setOnAction(e -> showContent(new EmployeeSpeakUpView(email).createContent()));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button mentor = new Button("♧   Find a Mentor");
        mentor.setPrefHeight(48);
        mentor.setMaxWidth(Double.MAX_VALUE);
        mentor.setStyle("-fx-background-color: linear-gradient(to right, " + BLUE + ", " + PURPLE + "); -fx-background-radius: 12; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        Region divider = new Region();
        divider.setPrefHeight(1);
        divider.setStyle("-fx-background-color: #E8E8EF;");

        Button settings = createMenuButton("⚙", "Settings", false);
        settings.setOnAction(e -> showContent(new MySettingsView().createSettingsContent(email)));

        Button help = createMenuButton("?", "Help & Support", false);
        help.setOnAction(e -> showSimpleMessage("Help & Support", "How can we help you?"));

        Button logout = createMenuButton("⇥", "Logout", false);
        logout.setOnAction(e -> logout());

        sidebar.getChildren().addAll(
                brand, dashboard, skills, learning, assessmentsBtn, tasks, team,
                performance, goals, achievements, attendance, leave, documents,
                events, announcements, feedbackBtn, speakUpBtn, spacer, mentor,
                divider, settings, help, logout
        );

        return sidebar;
    }

    private Button createMenuButton(String icon, String text, boolean active) {
        Label iconLabel = new Label(icon);
        iconLabel.setMinWidth(28);
        iconLabel.setAlignment(Pos.CENTER);

        Label textLabel = new Label(text);
        textLabel.setFont(Font.font("System", active ? FontWeight.BOLD : FontWeight.NORMAL, 14));

        HBox content = new HBox(14, iconLabel, textLabel);
        content.setAlignment(Pos.CENTER_LEFT);

        Button button = new Button();
        button.setGraphic(content);
        button.setPrefHeight(45);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);

        if (active) {
            iconLabel.setStyle("-fx-text-fill: " + PURPLE + "; -fx-font-size: 18px; -fx-font-weight: bold;");
            textLabel.setStyle("-fx-text-fill: " + PURPLE + ";");
            button.setStyle("-fx-background-color: #E7D8FF; -fx-background-radius: 12; -fx-padding: 0 13 0 13;");
        } else {
            iconLabel.setStyle("-fx-text-fill: " + DARK + "; -fx-font-size: 18px; -fx-font-weight: bold;");
            textLabel.setStyle("-fx-text-fill: " + DARK + ";");
            button.setStyle("-fx-background-color: transparent; -fx-background-radius: 12; -fx-padding: 0 13 0 13;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #F3F4FA; -fx-background-radius: 12; -fx-padding: 0 13 0 13;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-background-radius: 12; -fx-padding: 0 13 0 13;"));
        }

        return button;
    }

    private VBox createDashboardContent() {
        VBox page = new VBox(24);
        page.setPadding(new Insets(30, 38, 40, 38));
        page.setStyle("-fx-background-color: " + BG + ";");

        String currentUserEmail = email != null && !email.isBlank() ? email : (UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "");

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Employee Workspace");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: " + DARK + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label emailLabel = new Label(currentUserEmail.isEmpty() ? "Employee" : currentUserEmail);
        emailLabel.setStyle("-fx-text-fill: " + MUTED + "; -fx-font-size: 13px; -fx-font-weight: bold;");

        HBox userBox = new HBox(emailLabel);
        userBox.setPadding(new Insets(8, 14, 8, 14));
        userBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: " + BORDER + "; -fx-border-radius: 10;");

        header.getChildren().addAll(title, spacer, userBox);

        // Welcome Box
        VBox welcome = new VBox(4);
        Label small = new Label("EMPLOYEE PORTAL");
        small.setStyle("-fx-text-fill: " + BLUE + "; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label welcomeTitle = new Label("Welcome back!");
        welcomeTitle.setStyle("-fx-text-fill: " + DARK + "; -fx-font-size: 34px; -fx-font-weight: bold;");

        Label subtitle = new Label("Track your active goals, skill competencies, performance telemetry, and notices.");
        subtitle.setStyle("-fx-text-fill: " + TEXT + "; -fx-font-size: 14px;");

        welcome.getChildren().addAll(small, welcomeTitle, subtitle);

        // STATS ROW (5 dynamic cards)
        Label perfVal = new Label("0%");
        Label perfSub = new Label("Overall Rating");

        Label learnVal = new Label("0%");
        Label learnSub = new Label("Avg Test Score");

        Label skillsVal = new Label("0");
        Label skillsSub = new Label("Verified Competencies");

        Label goalsVal = new Label("0 / 0");
        Label goalsSub = new Label("Goals Completed");

        Label leaveVal = new Label("0 Days");
        Label leaveSub = new Label("Available Balance");

        HBox stats = new HBox(14);
        stats.getChildren().addAll(
                createStatCardNode("Performance Rating", perfVal, perfSub, BLUE),
                createStatCardNode("Learning Progress", learnVal, learnSub, PURPLE),
                createStatCardNode("Skills Acquired", skillsVal, skillsSub, GREEN),
                createStatCardNode("Goals Completed", goalsVal, goalsSub, ORANGE),
                createStatCardNode("Leaves Balance", leaveVal, leaveSub, "#0284C7")
        );
        for (Node n : stats.getChildren()) {
            HBox.setHgrow(n, Priority.ALWAYS);
        }

        VBox noticesCard = new VBox(14);
        noticesCard.setPadding(new Insets(20));
        noticesCard.setStyle("-fx-background-color: white; -fx-background-radius: 14; -fx-border-color: " + BORDER + "; -fx-border-radius: 14;");

        Label noticesTitle = new Label("📢 Trainer Broadcasts & Company Notices");
        noticesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        noticesTitle.setStyle("-fx-text-fill: " + DARK + ";");

        VBox noticesList = new VBox(10);
        noticesCard.getChildren().addAll(noticesTitle, noticesList);

        page.getChildren().addAll(header, welcome, stats, noticesCard);

        final double[] taskCompletionRatio = {0.0};
        final double[] enrollmentAvgScore = {0.0};

        Runnable updateOverallPerformanceRating = () -> {
            double rating = (taskCompletionRatio[0] * 0.5) + (enrollmentAvgScore[0] * 0.5);
            perfVal.setText(String.format("%.0f%%", rating));
        };

        FirebaseDAO.getInstance().getTasksForEmployee(currentUserEmail).thenAccept(taskList -> {
            Platform.runLater(() -> {
                int totalCount = taskList != null ? taskList.size() : 0;
                int completedCount = 0;
                if (taskList != null) {
                    for (EmployeeTask task : taskList) {
                        if ("COMPLETED".equalsIgnoreCase(task.getStatus())) {
                            completedCount++;
                        }
                    }
                }
                goalsVal.setText(completedCount + " / " + totalCount);
                taskCompletionRatio[0] = totalCount > 0 ? ((double) completedCount / totalCount) * 100.0 : 0.0;
                updateOverallPerformanceRating.run();
            });
        });

        FirebaseDAO.getInstance().getSkillsAndGaps(currentUserEmail).thenAccept(skills -> {
            Platform.runLater(() -> {
                int highProf = 0;
                if (skills != null) {
                    for (EmployeeSkill sk : skills) {
                        if (sk.getProficiencyPercentage() >= 80) {
                            highProf++;
                        }
                    }
                }
                skillsVal.setText(String.valueOf(highProf));
            });
        });

        FirebaseDAO.getInstance().getEmployeeTrainingProgress(currentUserEmail).thenAccept(enrollments -> {
            Platform.runLater(() -> {
                double avgScore = 0;
                int cnt = enrollments != null ? enrollments.size() : 0;
                if (enrollments != null && cnt > 0) {
                    for (TrainingEnrollment te : enrollments) {
                        avgScore += te.getAssessmentScore();
                    }
                    avgScore /= cnt;
                } else {
                    avgScore = 0.0;
                }
                enrollmentAvgScore[0] = avgScore;
                learnVal.setText(String.format("%.0f%%", avgScore));
                updateOverallPerformanceRating.run();
            });
        });

        FirebaseDAO.getInstance().getUserLeaveBalance(currentUserEmail).thenAccept(leaves -> {
            Platform.runLater(() -> {
                leaveVal.setText((leaves != null ? leaves : 18) + " Days");
            });
        });

        FirebaseDAO.getInstance().getRecentAnnouncements().thenAccept(announcements -> {
            Platform.runLater(() -> {
                noticesList.getChildren().clear();
                if (announcements == null || announcements.isEmpty()) {
                    Label emptyAnn = new Label("No announcements broadcasted.");
                    emptyAnn.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
                    noticesList.getChildren().add(emptyAnn);
                } else {
                    for (TrainerAnnouncement ann : announcements) {
                        HBox noticeRow = new HBox(12);
                        noticeRow.setPadding(new Insets(10, 12, 10, 12));
                        noticeRow.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

                        Label tag = new Label("📢 " + (ann.getTrainerName() != null ? ann.getTrainerName() : "Trainer"));
                        tag.setStyle("-fx-font-weight: bold; -fx-text-fill: #1E60FF;");

                        Label msg = new Label(ann.getTitle() + ": " + ann.getMessage());
                        msg.setFont(Font.font("Arial", 13));

                        noticeRow.getChildren().addAll(tag, msg);
                        noticesList.getChildren().add(noticeRow);
                    }
                }
            });
        });

        return page;
    }

    private VBox createStatCardNode(String title, Label valLabel, Label subLabel, String accentColor) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 14; -fx-border-color: " + BORDER + "; -fx-border-radius: 14;");

        Label t = new Label(title);
        t.setStyle("-fx-text-fill: " + MUTED + "; -fx-font-size: 12px; -fx-font-weight: bold;");

        valLabel.setStyle("-fx-text-fill: " + accentColor + "; -fx-font-size: 28px; -fx-font-weight: bold;");
        subLabel.setStyle("-fx-text-fill: " + TEXT + "; -fx-font-size: 12px;");

        card.getChildren().addAll(t, valLabel, subLabel);
        return card;
    }

    private void showPerformanceContent() {
        showContent(new EmployeePerformanceView().createPerformanceContent(email));
    }

    private void showSimpleMessage(String titleText, String messageText) {
        VBox page = new VBox(15);
        page.setPadding(new Insets(40));
        page.setStyle("-fx-background-color: " + BG + ";");

        Label title = new Label(titleText);
        title.setStyle("-fx-text-fill: " + DARK + "; -fx-font-size: 32px; -fx-font-weight: bold;");

        Label message = new Label(messageText);
        message.setStyle("-fx-text-fill: " + TEXT + "; -fx-font-size: 16px;");

        page.getChildren().addAll(title, message);
        showContent(page);
    }

    private void logout() {
        com.skillverse.FirstScreen.SceneNavigator.confirmAndLogout("Employee");
    }
}
