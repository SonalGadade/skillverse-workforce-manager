package com.skillverse.FirstScreen;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Config.CloudinaryService;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.JobApplication;
import com.skillverse.CommonFeatures.JobPosting;
import com.skillverse.CommonFeatures.SeminarEvent;
import com.skillverse.CommonFeatures.TrainerAnnouncement;
import com.skillverse.CommonFeatures.TrainerAssessment;
import com.skillverse.CommonFeatures.TrainingEnrollment;
import com.skillverse.CommonFeatures.TrainingProgram;
import com.skillverse.employee.model.AIChatMessage;
import com.skillverse.employee.service.AILearningEngine;
import com.skillverse.employee.service.AILearningEngine.EmployeeContext;
import com.skillverse.CommonFeatures.EmployeeSkill;
import javafx.application.Platform;
import javafx.stage.Stage;
import com.skillverse.hr.view.Post;
import com.skillverse.hr.view.PostRepository;
import com.skillverse.manager.view.ManagerFeedbackView;
import com.skillverse.employee.view.EmployeeFeedbackView;
import com.skillverse.employee.view.EmployeeSkillsView;
import com.skillverse.employee.view.EmployeeSpeakUpView;
import com.skillverse.hr.view.HRFeedbackView;
import com.skillverse.hr.view.HRSpeakUpView;
import com.skillverse.manager.view.ManagerSpeakUpDeskView;
import com.skillverse.trainer.view.TrainerDashboardView;
import com.skillverse.trainer.view.TrainerTrainingsView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class UnifiedDashboardView {

    private final String roleName;
    private final String roleBadgeText;
    private final String roleIconSymbol;
    private BorderPane mainLayout;
    private VBox centerContainer;
    private Button activeNavBtn = null;

    // Active Profile State
    private String userFullName;
    private String userEmail;
    private String userPhone = "+1 (555) 019-2834";
    private String userLocation = "San Francisco, CA";
    private String userDepartment;
    private String userDob = "1998-05-14";
    private String userBio = "Full-Stack Java Developer | Tech Enthusiast | Open to Collaboration 🚀";
    private String userAvatarPath = null;

    // Notification Unread State
    private boolean hasUnreadNotifs = true;
    private Circle notifDot;

    // Dynamic Profile & Header references
    private StackPane bottomUserAvatarBox;
    private Text userNameText;
    private Text userRoleText;
    private StackPane topRoleAvatarBox;

    // Temporary upload image path storage for Feed & Profile achievement creation
    private String selectedHiringPosterPath = null;
    private String selectedAchievementPosterPath = null;

    // Sample Data Models
    private static class EmployeeModel {

        String name, email, role, department, designation, joiningDate, status;
        boolean active;

        EmployeeModel(String n, String e, String r, String d, String desg, String jd, boolean act) {
            name = n;
            email = e;
            role = r;
            department = d;
            designation = desg;
            joiningDate = jd;
            active = act;
            status = act ? "Active" : "Inactive";
        }

        EmployeeModel(String n, String e, String r, String d, String desg, String jd, String st) {
            name = n;
            email = e;
            role = r;
            department = d;
            designation = desg;
            joiningDate = jd;
            status = st;
            active = "Active".equalsIgnoreCase(st);
        }
    }

    private static class CandidateModel {

        String id, name, role, resumeStatus, stage;

        CandidateModel(String id, String n, String r, String rs, String stg) {
            this.id = id;
            name = n;
            role = r;
            resumeStatus = rs;
            stage = stg;
        }

        CandidateModel(String n, String r, String rs, String stg) {
            this(null, n, r, rs, stg);
        }
    }

    private static class GoalModel {

        String title, assignee, deadline, kpiMetric, progressPct, priority;
        boolean completed;
        String completedTime;

        GoalModel(String t, String a, String d, String k, String p, String prio, boolean c, String ct) {
            title = t;
            assignee = a;
            deadline = d;
            kpiMetric = k;
            progressPct = p;
            priority = prio;
            completed = c;
            completedTime = ct;
        }
    }

    private static class SkillModel {

        String skillName, level, category;
        int ratingScore;

        SkillModel(String s, String l, String c, int r) {
            skillName = s;
            level = l;
            category = c;
            ratingScore = r;
        }
    }

    private List<EmployeeModel> employeeList = new ArrayList<>();
    private List<CandidateModel> candidateList = new ArrayList<>();
    private List<GoalModel> goalList = new ArrayList<>();
    private List<SkillModel> employeeSkillList = new ArrayList<>();
    private List<Post> employeeAchievementPosts = new ArrayList<>();

    public UnifiedDashboardView(String roleName, String roleBadgeText, String roleIconSymbol) {
        this.roleName = roleName;
        this.roleBadgeText = roleBadgeText;
        this.roleIconSymbol = roleIconSymbol;

        // Sync initial profile
        this.userEmail = (SceneNavigator.getCurrentUserEmail() != null && !SceneNavigator.getCurrentUserEmail().isBlank())
                ? SceneNavigator.getCurrentUserEmail()
                : (roleName.toLowerCase() + "@skillverse.com");
        this.userFullName = capitalize(roleName) + " User";
        this.userDepartment = "Manager".equalsIgnoreCase(roleName) ? "Engineering & Tech" : ("HR".equalsIgnoreCase(roleName) ? "Human Resources" : "Product Development");

        User sessionUser = UserSession.getCurrentUser();
        if (sessionUser != null) {
            if (sessionUser.getEmail() != null && !sessionUser.getEmail().isBlank()) {
                this.userEmail = sessionUser.getEmail();
            }
            if (sessionUser.getFullName() != null && !sessionUser.getFullName().isBlank()) {
                this.userFullName = sessionUser.getFullName();
            }
            if (sessionUser.getDepartment() != null && !sessionUser.getDepartment().isBlank()) {
                this.userDepartment = sessionUser.getDepartment();
            }
            if (sessionUser.getProfileImageUrl() != null && !sessionUser.getProfileImageUrl().isBlank()) {
                this.userAvatarPath = sessionUser.getProfileImageUrl();
            }
        } else {
            sessionUser = new User(this.userFullName, this.userEmail, "password123", roleName, this.userDepartment);
            UserSession.setCurrentUser(sessionUser);
        }

        initSampleData();
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private void initSampleData() {

        if (candidateList.isEmpty()) {
            candidateList.add(new CandidateModel("Fiona Gallagher", "Sr. Software Engineer", "Reviewed", "Interviewing"));
            candidateList.add(new CandidateModel("George Clark", "Talent Specialist", "Reviewed", "Shortlisted"));
            candidateList.add(new CandidateModel("Hannah Abbott", "Sales Account Exec", "Submitted", "Applied"));
            candidateList.add(new CandidateModel("Ian Malcolm", "Sr. Software Engineer", "Reviewed", "Hired"));
        }

        if (goalList.isEmpty()) {

        }

        if (employeeSkillList.isEmpty()) {
            employeeSkillList.add(new SkillModel("JavaFX & Java 17", "Expert", "Backend / Core", 95));
            employeeSkillList.add(new SkillModel("Spring Boot Microservices", "Intermediate", "Backend", 80));
            employeeSkillList.add(new SkillModel("UI/UX Design Systems", "Expert", "Frontend", 90));
            employeeSkillList.add(new SkillModel("AI & ML Pipelines", "Intermediate", "Data Science", 75));
            employeeSkillList.add(new SkillModel("Docker & Cloud Security", "Beginner", "DevOps", 60));
        }

        if (employeeAchievementPosts.isEmpty()) {
            employeeAchievementPosts.add(
                    new Post(
                            userFullName,
                            "Software Engineer • Achievement",
                            "Earned Spring Boot Microservices Professional Certificate! 🎓",
                            "Certification",
                            "Excited to share that I have completed the Advanced Microservices & Cloud Security Certification course with distinction! Looking forward to building scalable backends.",
                            null,
                            "Yesterday",
                            14
                    )
            );
        }
    }

    public Scene createDashboardScene() {
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #F8FAFC;");

        VBox sidebar = createSidebar();
        mainLayout.setLeft(sidebar);

        HBox topHeader = createTopHeader();
        mainLayout.setTop(topHeader);

        centerContainer = new VBox(20);
        centerContainer.setPadding(new Insets(24, 30, 30, 30));
        centerContainer.setStyle("-fx-background-color: #F8FAFC;");

        loadDashboardHome();

        ScrollPane scrollPane = new ScrollPane(centerContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");

        mainLayout.setCenter(scrollPane);

        return new Scene(mainLayout, 1380, 860);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(14);
        sidebar.setPrefWidth(260);
        sidebar.setPadding(new Insets(20, 16, 20, 16));
        sidebar.setStyle(
                "-fx-background-color: #FFFFFF;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-width: 0 1 0 0;"
        );

        HBox logoBox = new HBox(10);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        ImageView logoImageView = null;
        try {
            InputStream is = UnifiedDashboardView.class.getResourceAsStream("/assets/Main Logo.jpeg");
            if (is == null) {
                is = UnifiedDashboardView.class.getResourceAsStream("/Main Logo.jpeg");
            }
            if (is != null) {
                Image img = new Image(is);
                logoImageView = new ImageView(img);
                logoImageView.setFitHeight(42);
                logoImageView.setPreserveRatio(true);
                logoImageView.setSmooth(true);
            }
        } catch (Exception ex) {
            logoImageView = null;
        }

        StackPane sIcon = new StackPane();
        sIcon.setPrefSize(36, 36);
        sIcon.setStyle("-fx-background-color: #1E60FF; -fx-background-radius: 10px;");
        Text sText = new Text("S");
        sText.setFill(Color.WHITE);
        sText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        sIcon.getChildren().add(sText);

        VBox logoText = new VBox(0);
        Text title = new Text("SkillVerse");
        title.setFill(Color.web("#0F172A"));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Text sub = new Text("Enterprise Ecosystem");
        sub.setFill(Color.web("#64748B"));
        sub.setFont(Font.font("Arial", 10));

        logoText.getChildren().addAll(title, sub);

        if (logoImageView != null) {
            logoBox.getChildren().addAll(logoImageView, logoText);
        } else {
            logoBox.getChildren().addAll(sIcon, logoText);
        }

        // Role Badge Card
        HBox roleBadge = new HBox(8);
        roleBadge.setPadding(new Insets(8, 12, 8, 12));
        roleBadge.setAlignment(Pos.CENTER_LEFT);
        roleBadge.setStyle(
                "-fx-background-color: #EFF6FF;"
                + "-fx-background-radius: 10px;"
                + "-fx-border-color: #DBEAFE;"
                + "-fx-border-radius: 10px;"
        );
        Label rIcon = new Label(roleIconSymbol);
        rIcon.setStyle("-fx-font-size: 14px;");
        Text rText = new Text(roleBadgeText);
        rText.setFill(Color.web("#1E40AF"));
        rText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        roleBadge.getChildren().addAll(rIcon, rText);

        VBox menuBox = new VBox(4);

        Button btnDash = createNavBtn("📊", "Dashboard", () -> loadDashboardHome());

        if ("Manager".equalsIgnoreCase(roleName)) {
            Label section1 = new Label("WORKSPACE");
            section1.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 10px 14px 4px 14px;");

            Button btnDashNav = createNavBtn("🏠", "Dashboard", () -> loadDashboardHome());
            Button btnFeed = createNavBtn("📰", "Feed", () -> loadFeedView());
            Button btnTeam = createNavBtn("👥", "My Team", () -> loadMyTeamView());
            Button btnPerf = createNavBtn("📈", "Performance", () -> loadPerformanceView());
            Button btnGoals = createNavBtn("🎯", "Assign Goals & Tasks", () -> loadAssignGoalsView());
            Button btnFeedBack = createNavBtn("💬", "Feedback", () -> loadManagerFeedbackView());
            Button btnSpeakUp = createNavBtn("🛡️", "Speak Up Desk", () -> loadManagerSpeakUpDeskView());

            Label section2 = new Label("ANALYTICS & REPORTS");
            section2.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 14px 14px 4px 14px;");

            Button btnRep = createNavBtn("📊", "Reports", () -> loadManagerReportsView());
            Button btnCareers = createNavBtn("💼", "Careers & Openings", () -> loadCareerOpportunitiesView());

            activeNavBtn = btnDashNav;
            setNavActive(btnDashNav, true);
            menuBox.getChildren().addAll(section1, btnDashNav, btnFeed, btnTeam, btnCareers, btnPerf, btnGoals, btnFeedBack, btnSpeakUp, section2, btnRep);

        } else if ("Employee".equalsIgnoreCase(roleName)) {
            Button btnSkills = createNavBtn("🛡️", "My Skills & Gaps", () -> loadEmployeeSkillsView());
            Button btnLearn = createNavBtn("🎓", "Learning & Courses", () -> loadEmployeeLearningView());
            Button btnAI = createNavBtn("🤖", "AI Learning Assistant", () -> loadAIAssistantView());
            Button btnGoals = createNavBtn("🎯", "My Goals & Tasks", () -> loadEmployeeGoalsView());
            Button btnPerf = createNavBtn("📈", "My Performance", () -> loadEmployeePerformanceView());
            Button btnFeedback = createNavBtn("✉️", "Feedback & Reviews", () -> loadEmployeeFeedbackView());
            Button btnSpeakUp = createNavBtn("📢", "Speak Up Box", () -> loadEmployeeSpeakUpView());
            Button btnCareer = createNavBtn("💼", "Career & Openings", () -> loadCareerOpportunitiesView());
            Button btnFeed = createNavBtn("📰", "Social Feed", () -> loadFeedView());

            activeNavBtn = btnDash;
            setNavActive(btnDash, true);
            menuBox.getChildren().addAll(btnDash, btnSkills, btnLearn, btnAI, btnGoals, btnPerf, btnFeedback, btnSpeakUp, btnCareer, btnFeed);

        } else if ("Trainer".equalsIgnoreCase(roleName)) {
            Label section1 = new Label("TRAINER WORKSPACE");
            section1.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 10px 14px 4px 14px;");

            Button btnDashNav = createNavBtn("🏠", "Dashboard", () -> loadDashboardHome());
            Button btnFeed = createNavBtn("📰", "Social Feed", () -> loadFeedView());
            Button btnTrainerCourses = createNavBtn("🎓", "Assigned Trainings & Student Grading", () -> loadTrainerTrainingsView());
            Button btnSpeakUp = createNavBtn("📢", "Speak Up Box", () -> loadEmployeeSpeakUpView());

            activeNavBtn = btnDashNav;
            setNavActive(btnDashNav, true);
            menuBox.getChildren().addAll(section1, btnDashNav, btnFeed, btnTrainerCourses, btnSpeakUp);

        } else {

            Button btnFeed = createNavBtn("📰", "Feed", () -> loadFeedView());
            Button btnEmp = createNavBtn("👥", "Employee Management", () -> loadEmployeeManagementView());
            Button btnRec = createNavBtn("🎯", "Recruitment", () -> loadRecruitmentView());
            Button btnTrain = createNavBtn("🎓", "Training", () -> loadTrainingView());
            Button btnPerf = createNavBtn("📈", "Performance", () -> loadPerformanceView());
            Button btnFeedback = createNavBtn("✉️", "Feedback", () -> loadHRFeedbackViewPage());
            Button btnSpeakUp = createNavBtn("📢", "Speak Up", () -> loadHRSpeakUpView());
            Button btnRep = createNavBtn("📑", "Reports", () -> loadReportsView());

            activeNavBtn = btnDash;
            setNavActive(btnDash, true);
            menuBox.getChildren().addAll(btnDash, btnFeed, btnEmp, btnRec, btnTrain, btnPerf, btnFeedback, btnSpeakUp, btnRep);
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox bottomBox = new VBox(10);
        bottomBox.setPadding(new Insets(10, 0, 0, 0));
        bottomBox.setStyle("-fx-border-color: #E2E8F0; -fx-border-width: 1 0 0 0;");

        HBox userBox = new HBox(10);
        userBox.setAlignment(Pos.CENTER_LEFT);
        userBox.setPadding(new Insets(6, 8, 6, 8));
        userBox.setStyle("-fx-background-radius: 8px; -fx-cursor: hand;");
        userBox.setOnMouseEntered(e -> userBox.setStyle("-fx-background-color: #F1F5F9; -fx-background-radius: 8px; -fx-cursor: hand;"));
        userBox.setOnMouseExited(e -> userBox.setStyle("-fx-background-color: transparent; -fx-background-radius: 8px; -fx-cursor: hand;"));
        userBox.setOnMouseClicked(e -> showProfileModal());

        bottomUserAvatarBox = new StackPane();

        VBox userInfo = new VBox(1);
        userNameText = new Text(userEmail);
        userNameText.setFill(Color.web("#0F172A"));
        userNameText.setFont(Font.font("Arial", FontWeight.BOLD, 11));

        userRoleText = new Text((userFullName != null ? userFullName : roleName) + " • Edit Profile");
        userRoleText.setFill(Color.web("#64748B"));
        userRoleText.setFont(Font.font("Arial", 10));

        userInfo.getChildren().addAll(userNameText, userRoleText);
        userBox.getChildren().addAll(bottomUserAvatarBox, userInfo);

        refreshUserHeaderAndSidebar();

        Button logoutBtn = new Button("→  Logout");
        logoutBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setPrefHeight(36);
        logoutBtn.setStyle(
                "-fx-background-color: #FEF2F2;"
                + "-fx-text-fill: #DC2626;"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 8px;"
                + "-fx-border-color: #FCA5A5;"
                + "-fx-border-radius: 8px;"
                + "-fx-cursor: hand;"
        );
        logoutBtn.setOnAction(e -> {
            Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
            com.skillverse.CommonFeatures.ModernLogoutDialog.show(currentStage, roleName);
        });

        bottomBox.getChildren().addAll(userBox, logoutBtn);

        sidebar.getChildren().addAll(logoBox, roleBadge, menuBox, spacer, bottomBox);
        return sidebar;
    }

    private Button createNavBtn(String icon, String text, Runnable action) {
        Button btn = new Button(icon + "   " + text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(36);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-text-fill: #475569;"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: normal;"
                + "-fx-background-radius: 8px;"
                + "-fx-cursor: hand;"
        );

        btn.setOnAction(e -> {
            if (activeNavBtn != null) {
                setNavActive(activeNavBtn, false);
            }
            activeNavBtn = btn;
            setNavActive(btn, true);
            action.run();
        });

        return btn;
    }

    private void setNavActive(Button btn, boolean active) {
        if (active) {
            btn.setStyle(
                    "-fx-background-color: #EFF6FF;"
                    + "-fx-text-fill: #1E60FF;"
                    + "-fx-font-size: 13px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-background-radius: 8px;"
                    + "-fx-cursor: hand;"
            );
        } else {
            btn.setStyle(
                    "-fx-background-color: transparent;"
                    + "-fx-text-fill: #475569;"
                    + "-fx-font-size: 13px;"
                    + "-fx-font-weight: normal;"
                    + "-fx-background-radius: 8px;"
                    + "-fx-cursor: hand;"
            );
        }
    }

    private HBox createTopHeader() {
        HBox topHeader = new HBox();
        topHeader.setPadding(new Insets(12, 30, 12, 30));
        topHeader.setAlignment(Pos.CENTER_LEFT);
        topHeader.setStyle(
                "-fx-background-color: #FFFFFF;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-width: 0 0 1 0;"
        );

        // Breadcrumb
        HBox breadcrumb = new HBox(6);
        breadcrumb.setAlignment(Pos.CENTER_LEFT);
        Text bc1 = new Text("SkillVerse");
        bc1.setFill(Color.web("#64748B"));
        bc1.setFont(Font.font("Arial", 12));
        Text bcSlash = new Text("/");
        bcSlash.setFill(Color.web("#94A3B8"));
        bcSlash.setFont(Font.font("Arial", 12));
        Text bc2 = new Text(roleName + " Workspace");
        bc2.setFill(Color.web("#1E60FF"));
        bc2.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        breadcrumb.getChildren().addAll(bc1, bcSlash, bc2);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox searchBar = new HBox(8);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPrefWidth(380);
        searchBar.setPadding(new Insets(2, 4, 2, 12));
        searchBar.setStyle(
                "-fx-background-color: #F8FAFC;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-radius: 20px;"
                + "-fx-background-radius: 20px;"
        );

        TextField searchField = new TextField();
        searchField.setPromptText("Search modules, team & skills...");
        searchField.setPrefHeight(32);
        searchField.setStyle("-fx-background-color: transparent; -fx-prompt-text-fill: #94A3B8; -fx-font-size: 12px;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        Button searchBtn = new Button("Search");
        searchBtn.setPrefHeight(28);
        searchBtn.setStyle(
                "-fx-background-color: #1E60FF;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 11px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 14px;"
                + "-fx-cursor: hand;"
        );
        searchBar.getChildren().addAll(searchField, searchBtn);

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        HBox rightActions = new HBox(14);
        rightActions.setAlignment(Pos.CENTER_RIGHT);

        StackPane notifIcon = new StackPane();
        notifIcon.setStyle("-fx-cursor: hand;");
        Label bell = new Label("🔔");
        bell.setStyle("-fx-font-size: 18px;");

        notifDot = new Circle(4, Color.web("#EF4444"));
        notifDot.setTranslateX(6);
        notifDot.setTranslateY(-6);
        notifDot.setVisible(hasUnreadNotifs);

        notifIcon.getChildren().addAll(bell, notifDot);
        notifIcon.setOnMouseClicked(e -> {
            System.out.println("🔔 [Header] Notification Bell clicked");
            if ("Trainer".equalsIgnoreCase(roleName)) {
                centerContainer.getChildren().clear();
                centerContainer.getChildren().add(new com.skillverse.CommonFeatures.TrainerNotificationsView());
            } else {
                showNotificationPopup();
            }
        });

        topRoleAvatarBox = new StackPane();
        topRoleAvatarBox.setStyle("-fx-cursor: hand;");
        topRoleAvatarBox.setOnMouseClicked(e -> {
            System.out.println("👤 [Header] Avatar clicked");
            centerContainer.getChildren().clear();
            centerContainer.getChildren().add(new com.skillverse.CommonFeatures.ProfileView());
        });

        rightActions.getChildren().addAll(notifIcon, topRoleAvatarBox);

        topHeader.getChildren().addAll(breadcrumb, spacer, rightActions);
        refreshUserHeaderAndSidebar();
        return topHeader;
    }

    private void refreshUserHeaderAndSidebar() {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getEmail() != null && !currentUser.getEmail().isBlank()) {
                this.userEmail = currentUser.getEmail();
            }
            if (currentUser.getFullName() != null && !currentUser.getFullName().isBlank()) {
                this.userFullName = currentUser.getFullName();
            }
            if (currentUser.getDepartment() != null && !currentUser.getDepartment().isBlank()) {
                this.userDepartment = currentUser.getDepartment();
            }
            if (currentUser.getProfileImageUrl() != null && !currentUser.getProfileImageUrl().isBlank()) {
                this.userAvatarPath = currentUser.getProfileImageUrl();
            }
        }

        Platform.runLater(() -> {
            User current = UserSession.getCurrentUser();
            String displayName = (current != null && current.getName() != null && !current.getName().isEmpty())
                    ? current.getName() : (current != null ? current.getEmail() : "User");
            String role = current != null && current.getRole() != null ? current.getRole() : "Member";

            if (userNameText != null) {
                userNameText.setText(displayName);
            }
            if (userRoleText != null) {
                userRoleText.setText(role + " • " + (this.userDepartment != null ? this.userDepartment : "SkillVerse AI Enterprise"));
            }

            if (bottomUserAvatarBox != null) {
                bottomUserAvatarBox.getChildren().clear();
                if (userAvatarPath != null && !userAvatarPath.isBlank()) {
                    try {
                        Image img = new Image(userAvatarPath, true);
                        ImageView iv = new ImageView(img);
                        iv.setFitWidth(32);
                        iv.setFitHeight(32);
                        iv.setPreserveRatio(true);
                        Circle clip = new Circle(16, 16, 16);
                        iv.setClip(clip);
                        bottomUserAvatarBox.getChildren().add(iv);
                    } catch (Exception ex) {
                        Circle userAvatar = new Circle(16, Color.web("#1E60FF"));
                        Text userInit = new Text(getInitials(userFullName));
                        userInit.setFill(Color.WHITE);
                        userInit.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                        bottomUserAvatarBox.getChildren().addAll(userAvatar, userInit);
                    }
                } else {
                    Circle userAvatar = new Circle(16, Color.web("#1E60FF"));
                    Text userInit = new Text(getInitials(userFullName));
                    userInit.setFill(Color.WHITE);
                    userInit.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    bottomUserAvatarBox.getChildren().addAll(userAvatar, userInit);
                }
            }

            if (topRoleAvatarBox != null) {
                topRoleAvatarBox.getChildren().clear();
                if (userAvatarPath != null && !userAvatarPath.isBlank()) {
                    try {
                        Image img = new Image(userAvatarPath, true);
                        ImageView iv = new ImageView(img);
                        iv.setFitWidth(36);
                        iv.setFitHeight(36);
                        iv.setPreserveRatio(true);
                        Circle clip = new Circle(18, 18, 18);
                        iv.setClip(clip);
                        topRoleAvatarBox.getChildren().add(iv);
                    } catch (Exception ex) {
                        Circle roleAvatar = new Circle(18, Color.web("#1E60FF"));
                        Text roleInitial = new Text(getInitials(userFullName));
                        roleInitial.setFill(Color.WHITE);
                        roleInitial.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                        topRoleAvatarBox.getChildren().addAll(roleAvatar, roleInitial);
                    }
                } else {
                    Circle roleAvatar = new Circle(18, Color.web("#1E60FF"));
                    Text roleInitial = new Text(getInitials(userFullName));
                    roleInitial.setFill(Color.WHITE);
                    roleInitial.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    topRoleAvatarBox.getChildren().addAll(roleAvatar, roleInitial);
                }
            }
        });
    }

    private String getInitials(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return "U";
        }
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length >= 2) {
            return (parts[0].substring(0, 1) + parts[1].substring(0, 1)).toUpperCase();
        } else if (parts[0].length() >= 2) {
            return parts[0].substring(0, 2).toUpperCase();
        }
        return parts[0].substring(0, 1).toUpperCase();
    }

    private void showProfileModal() {
        centerContainer.getChildren().clear();
        UnifiedProfileView upv = new UnifiedProfileView(roleName, () -> loadDashboardHome(), this::refreshUserHeaderAndSidebar);
        centerContainer.getChildren().add(upv.getViewContainer());
    }

    private VBox createStatCounter(String label, String value) {
        VBox box = new VBox(1);
        box.setAlignment(Pos.CENTER);
        Text valText = new Text(value);
        valText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        valText.setFill(Color.web("#0F172A"));
        Text lblText = new Text(label);
        lblText.setFont(Font.font("Arial", 11));
        lblText.setFill(Color.web("#64748B"));
        box.getChildren().addAll(valText, lblText);
        return box;
    }

    private void showNotificationPopup() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Global Notification Center");
        dialog.setHeaderText("Active system alerts and role notifications (" + roleName + ")");

        ButtonType readType = new ButtonType("Mark All as Read", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(readType, ButtonType.CANCEL);

        VBox container = new VBox(12);
        container.setPadding(new Insets(16));
        container.setPrefWidth(480);

        if ("Employee".equalsIgnoreCase(roleName)) {
            container.getChildren().addAll(
                    createNotifCard("🎓", "Course Deadline Reminder", "AI & ML Fundamentals Module 3 is due tomorrow", "20 mins ago", true),
                    createNotifCard("⭐", "Performance Rating Received", "Manager evaluated your Q3 appraisal rating: 4.8 / 5.0", "2 hours ago", true),
                    createNotifCard("💼", "Internal Job Opportunity", "HR posted Senior Java Engineer position", "4 hours ago", false)
            );
        } else {
            container.getChildren().addAll(
                    createNotifCard("📩", "New System Message", "Welcome to SkillVerse AI Workspace", "Just now", true)
            );
        }

        dialog.getDialogPane().setContent(container);
        dialog.setResultConverter(btn -> {
            if (btn == readType) {
                hasUnreadNotifs = false;
                if (notifDot != null) {
                    notifDot.setVisible(false);
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    private HBox createNotifCard(String iconStr, String title, String detail, String time, boolean unread) {
        HBox card = new HBox(12);
        card.setPadding(new Insets(12));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
                "-fx-background-color: " + (unread ? "#EFF6FF;" : "#F8FAFC;")
                + "-fx-border-color: " + (unread ? "#BFDBFE;" : "#E2E8F0;")
                + "-fx-border-radius: 10px; -fx-background-radius: 10px;"
        );

        Label icon = new Label(iconStr);
        icon.setStyle("-fx-font-size: 18px;");
        VBox textStack = new VBox(2);
        Text t = new Text(title);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        t.setFill(Color.web("#0F172A"));
        Text d = new Text(detail);
        d.setFont(Font.font("Arial", 11));
        d.setFill(Color.web("#475569"));
        Text tm = new Text(time);
        tm.setFont(Font.font("Arial", 10));
        tm.setFill(Color.web("#94A3B8"));

        textStack.getChildren().addAll(t, d, tm);
        card.getChildren().addAll(icon, textStack);
        return card;
    }

    private void loadDashboardHome() {
        centerContainer.getChildren().clear();

        if ("Manager".equalsIgnoreCase(roleName)) {
            loadManagerDashboardOverview();
        } else if ("Employee".equalsIgnoreCase(roleName)) {
            loadEmployeeDashboardOverview();
        } else if ("Trainer".equalsIgnoreCase(roleName)) {
            loadTrainerDashboardOverview();
        } else {
            loadHRDashboardOverview();
        }
    }

    private void loadTrainerDashboardOverview() {
        centerContainer.getChildren().clear();
        TrainerDashboardView tView = new TrainerDashboardView();
        centerContainer.getChildren().add(tView);
    }

    private void loadEmployeeDashboardOverview() {
        VBox titleBox = new VBox(2);
        Text pageTitle = new Text("Employee Dashboard Overview");
        pageTitle.setFill(Color.web("#0F172A"));
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        Text pageSub = new Text("Self-growth, daily tasks, learning paths, career goals, and internal jobs.");
        pageSub.setFill(Color.web("#64748B"));
        pageSub.setFont(Font.font("Arial", 13));
        titleBox.getChildren().addAll(pageTitle, pageSub);

        GridPane metricsGrid = new GridPane();
        metricsGrid.setHgap(16);
        metricsGrid.setVgap(16);
        metricsGrid.add(createStatMiniCard("My Performance", "87% Rating", "Q3 Evaluation: 4.8 / 5", "#1E60FF"), 0, 0);
        metricsGrid.add(createStatMiniCard("Learning Progress", "65% Completed", "Spring Boot Track", "#10B981"), 1, 0);
        metricsGrid.add(createStatMiniCard("Skills Acquired", "8 Verified", "Java, UI/UX, AI", "#8B5CF6"), 2, 0);
        metricsGrid.add(createStatMiniCard("Goals Completed", "6 / 8 Tasks", "Sprint 4 Target", "#F59E0B"), 3, 0);

        HBox overviewRow = new HBox(20);

        VBox card3 = new VBox(14);
        card3.setPadding(new Insets(20));
        card3.setPrefWidth(520);
        HBox.setHgrow(card3, Priority.ALWAYS);
        card3.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        HBox c3Head = new HBox(10);
        c3Head.setAlignment(Pos.CENTER_LEFT);
        Text c3Title = new Text("📅 Leaves & Attendance Balance");
        c3Title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        c3Title.setFill(Color.web("#0F172A"));
        Region c3Sp = new Region();
        HBox.setHgrow(c3Sp, Priority.ALWAYS);
        Button applyLeaveBtn = new Button("+ Apply Leave");
        applyLeaveBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-cursor: hand;");
        applyLeaveBtn.setOnAction(e -> showApplyLeaveModal());
        c3Head.getChildren().addAll(c3Title, c3Sp, applyLeaveBtn);

        HBox leaveBalBox = new HBox(14);
        leaveBalBox.getChildren().addAll(
                createLeaveMiniBox("Casual Leave", "12 Days Left", "#10B981"),
                createLeaveMiniBox("Sick Leave", "8 Days Left", "#1E60FF"),
                createLeaveMiniBox("Earned Leave", "5 Days Left", "#8B5CF6")
        );
        card3.getChildren().addAll(c3Head, leaveBalBox);

        VBox card4 = new VBox(14);
        card4.setPadding(new Insets(20));
        card4.setPrefWidth(520);
        HBox.setHgrow(card4, Priority.ALWAYS);
        card4.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px; -fx-cursor: hand;");

        HBox c4Head = new HBox(10);
        c4Head.setAlignment(Pos.CENTER_LEFT);
        Text c4Title = new Text("📢 Trainer Notices & Broadcasts");
        c4Title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        c4Title.setFill(Color.web("#0F172A"));
        Region c4Sp = new Region();
        HBox.setHgrow(c4Sp, Priority.ALWAYS);

        Button viewAnnounceBtn = new Button("View All Announcements →");
        viewAnnounceBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-cursor: hand;");
        viewAnnounceBtn.setOnAction(e -> loadAnnouncementsView());

        c4Head.getChildren().addAll(c4Title, c4Sp, viewAnnounceBtn);

        VBox noticeList = new VBox(8);
        Text n1 = new Text("• We are Hiring: Senior Java & Full-Stack Engineers! Check out internal job openings.");
        n1.setFont(Font.font("Arial", 12));
        n1.setFill(Color.web("#334155"));
        Text n2 = new Text("• Annual Employee Wellness & Skill-Building Workshop begins next Monday.");
        n2.setFont(Font.font("Arial", 12));
        n2.setFill(Color.web("#334155"));
        noticeList.getChildren().addAll(n1, n2);

        card4.getChildren().addAll(c4Head, noticeList);
        card4.setOnMouseClicked(e -> loadAnnouncementsView());

        overviewRow.getChildren().addAll(card3, card4);

        centerContainer.getChildren().addAll(titleBox, metricsGrid, overviewRow);
    }

    private void loadAnnouncementsView() {
        centerContainer.getChildren().clear();
        com.skillverse.employee.view.AnnouncementsView av = new com.skillverse.employee.view.AnnouncementsView();
        VBox content = av.createAnnouncementsContent(userEmail);

        Button backBtn = new Button("← Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 8px; -fx-cursor: hand;");
        backBtn.setOnAction(e -> loadDashboardHome());

        HBox topBox = new HBox(backBtn);
        topBox.setPadding(new Insets(10, 0, 10, 0));

        content.getChildren().add(0, topBox);
        centerContainer.getChildren().add(content);
    }

    private void loadFeedView() {
        centerContainer.getChildren().clear();
        com.skillverse.hr.view.Feed feed = new com.skillverse.hr.view.Feed(roleName);
        if (feed.getBackButton() != null) {
            feed.getBackButton().setOnAction(e -> loadDashboardHome());
        }
        centerContainer.getChildren().add(feed.getScene().getRoot());
    }

    private void loadAssignGoalsView() {
        centerContainer.getChildren().setAll(new com.skillverse.manager.view.ManagerAssignTasksView(userEmail, () -> loadDashboardHome()));
    }

    private VBox createLeaveMiniBox(String title, String val, String hex) {
        VBox b = new VBox(4);
        b.setPadding(new Insets(12));
        b.setPrefWidth(150);
        b.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        Text t = new Text(title);
        t.setFont(Font.font("Arial", 11));
        t.setFill(Color.web("#64748B"));
        Text v = new Text(val);
        v.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        v.setFill(Color.web(hex));
        b.getChildren().addAll(t, v);
        return b;
    }

    private void showApplyLeaveModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Apply for Leave");
        dialog.setHeaderText("Submit leave request for manager approval");

        ButtonType submitType = new ButtonType("Submit Request", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Casual Leave", "Sick Leave", "Earned Leave");
        typeCombo.setValue("Casual Leave");

        DatePicker startDate = new DatePicker(java.time.LocalDate.now());
        DatePicker endDate = new DatePicker(java.time.LocalDate.now().plusDays(1));
        TextField reasonF = new TextField();
        reasonF.setPromptText("Reason for leave");

        grid.add(new Label("Leave Type:"), 0, 0);
        grid.add(typeCombo, 1, 0);
        grid.add(new Label("Start Date:"), 0, 1);
        grid.add(startDate, 1, 1);
        grid.add(new Label("End Date:"), 0, 2);
        grid.add(endDate, 1, 2);
        grid.add(new Label("Reason:"), 0, 3);
        grid.add(reasonF, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == submitType) {
            String curEmail = userEmail != null && !userEmail.isBlank() ? userEmail : (UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "employee@skillverse.com");
            String curName = userFullName != null && !userFullName.isBlank() ? userFullName : "Employee User";

            java.time.LocalDate sDate = startDate.getValue() != null ? startDate.getValue() : java.time.LocalDate.now();
            java.time.LocalDate eDate = endDate.getValue() != null ? endDate.getValue() : sDate.plusDays(1);
            long days = java.time.temporal.ChronoUnit.DAYS.between(sDate, eDate);
            if (days <= 0) {
                days = 1;
            }

            com.skillverse.CommonFeatures.LeaveRequest req = new com.skillverse.CommonFeatures.LeaveRequest(
                    "leave_" + System.currentTimeMillis() + "_" + java.util.UUID.randomUUID().toString().substring(0, 4),
                    curEmail,
                    curName,
                    typeCombo.getValue(),
                    sDate.toString(),
                    eDate.toString(),
                    (int) days,
                    reasonF.getText() != null && !reasonF.getText().isBlank() ? reasonF.getText().trim() : "Personal leave",
                    "PENDING",
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()),
                    "manager@skillverse.com",
                    ""
            );

            FirebaseDAO.getInstance().applyForLeave(req).thenAccept(ok -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "✅ Leave request submitted successfully! It is now stored in the database and visible in Manager login.");
                    alert.showAndWait();
                    loadEmployeeDashboardOverview();
                });
            });
        }
    }

    private void loadManagerDashboardOverview() {
        centerContainer.getChildren().clear();
        centerContainer.getChildren().setAll(new com.skillverse.manager.view.ManagerDashboardView(userEmail));
    }

    private HBox createManagerKpiCard(String title, String val, String sub, String iconStr, String bgHex, String accentHex) {
        HBox card = new HBox(14);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 14px; -fx-background-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.04), 8, 0, 0, 2);");

        Label iconLbl = new Label(iconStr);
        iconLbl.setAlignment(Pos.CENTER);
        iconLbl.setPrefSize(42, 42);
        iconLbl.setStyle("-fx-background-color: " + bgHex + "; -fx-background-radius: 10px; -fx-font-size: 18px;");

        VBox textGroup = new VBox(2);
        Text tTitle = new Text(title);
        tTitle.setFont(Font.font("Arial", 12));
        tTitle.setFill(Color.web("#64748B"));
        Text tVal = new Text(val);
        tVal.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        tVal.setFill(Color.web("#0F172A"));
        Text tSub = new Text(sub);
        tSub.setFont(Font.font("Arial", 11));
        tSub.setFill(Color.web(accentHex));

        textGroup.getChildren().addAll(tTitle, tVal, tSub);
        card.getChildren().addAll(iconLbl, textGroup);
        return card;
    }

    private HBox createTeamMemberRow(String initial, String name, String role, String score, String scoreHex) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Circle av = new Circle(14, Color.web("#EFF6FF"));
        Text init = new Text(initial);
        init.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        init.setFill(Color.web("#1E60FF"));
        StackPane avStack = new StackPane(av, init);

        VBox textGrp = new VBox(1);
        Text tName = new Text(name);
        tName.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        tName.setFill(Color.web("#0F172A"));
        Text tRole = new Text(role);
        tRole.setFont(Font.font("Arial", 10));
        tRole.setFill(Color.web("#64748B"));
        textGrp.getChildren().addAll(tName, tRole);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label scoreBadge = new Label(score);
        scoreBadge.setStyle("-fx-background-color: #F8FAFC; -fx-text-fill: " + scoreHex + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 8px; -fx-border-color: #E2E8F0; -fx-border-radius: 8px;");

        row.getChildren().addAll(avStack, textGrp, sp, scoreBadge);
        return row;
    }

    private HBox createActivityItem(String text, String time) {
        HBox item = new HBox(8);
        item.setAlignment(Pos.CENTER_LEFT);

        Text dot = new Text("•");
        dot.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        dot.setFill(Color.web("#1E60FF"));
        Text desc = new Text(text);
        desc.setFont(Font.font("Arial", 12));
        desc.setFill(Color.web("#334155"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Text tTime = new Text(time);
        tTime.setFont(Font.font("Arial", 10));
        tTime.setFill(Color.web("#94A3B8"));

        item.getChildren().addAll(dot, desc, sp, tTime);
        return item;
    }

    private void loadMyTeamView() {
        centerContainer.getChildren().setAll(new com.skillverse.manager.view.ManagerTeamView(userEmail, targetEmail -> loadManagerGoalsView()));
    }

    private void loadManagerGoalsView() {
        centerContainer.getChildren().setAll(new com.skillverse.manager.view.ManagerAssignTasksView(userEmail, () -> loadDashboardHome()));
    }

    private void showCreateGoalModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create Goal / Assign Task");
        dialog.setHeaderText("Assign new performance goal to team member");

        ButtonType saveType = new ButtonType("Assign Goal", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        TextField titleF = new TextField();
        titleF.setPromptText("Goal Title");
        TextField assignF = new TextField();
        assignF.setPromptText("Assignee Name (e.g. Bob Smith)");
        TextField dateF = new TextField("2026-09-15");
        TextField kpiF = new TextField();
        kpiF.setPromptText("KPI Metric (e.g. 100% Code Coverage)");

        grid.add(new Label("Goal Title:"), 0, 0);
        grid.add(titleF, 1, 0);
        grid.add(new Label("Assignee:"), 0, 1);
        grid.add(assignF, 1, 1);
        grid.add(new Label("Deadline:"), 0, 2);
        grid.add(dateF, 1, 2);
        grid.add(new Label("KPI Metric:"), 0, 3);
        grid.add(kpiF, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == saveType && !titleF.getText().isBlank()) {
                goalList.add(new GoalModel(titleF.getText(), assignF.getText(), dateF.getText(), kpiF.getText(), "0%", "HIGH", false, null));
                loadManagerGoalsView();
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void loadManagerSkillsView() {
        centerContainer.getChildren().clear();
        VBox root = new VBox(20);

        VBox titleBox = new VBox(2);
        Text t = new Text("Team Skills & Skill-Gap Matrix");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        t.setFill(Color.web("#0F172A"));
        Text s = new Text("Inventory of team capabilities and critical skill-gap analytics.");
        s.setFont(Font.font("Arial", 13));
        s.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(t, s);

        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        VBox rows = new VBox(12);
        for (SkillModel sm : employeeSkillList) {
            HBox r = new HBox(16);
            r.setAlignment(Pos.CENTER_LEFT);
            r.setPadding(new Insets(12, 16, 12, 16));
            r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            VBox inf = new VBox(2);
            Text st = new Text(sm.skillName);
            st.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            Text sub = new Text("Category: " + sm.category + " • Level: " + sm.level);
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(st, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            ProgressBar pb = new ProgressBar(sm.ratingScore / 100.0);
            pb.setPrefWidth(140);
            pb.setStyle("-fx-accent: #6366F1;");
            Label scoreLbl = new Label(sm.ratingScore + "%");
            scoreLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #6366F1;");

            r.getChildren().addAll(inf, sp, pb, scoreLbl);
            rows.getChildren().add(r);
        }
        card.getChildren().add(rows);

        root.getChildren().addAll(titleBox, card);
        centerContainer.getChildren().add(root);
    }

    private void loadManagerLearningView() {
        centerContainer.getChildren().clear();
        VBox root = new VBox(20);

        HBox headerBar = new HBox();
        headerBar.setAlignment(Pos.CENTER_LEFT);
        VBox titleBox = new VBox(2);
        Text t = new Text("Learning Management & Approvals");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        t.setFill(Color.web("#0F172A"));
        Text s = new Text("Assign courses to team members and track training completions.");
        s.setFont(Font.font("Arial", 13));
        s.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(t, s);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Button assignBtn = new Button("+ Recommend Course");
        assignBtn.setStyle("-fx-background-color: #F59E0B; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
        assignBtn.setOnAction(e -> showAssignTrainingModal());
        headerBar.getChildren().addAll(titleBox, sp, assignBtn);

        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        VBox list = new VBox(12);
        list.getChildren().addAll(
                createCourseRow("Spring Boot Microservices & Architecture", "Engineering", 12, 0.75),
                createCourseRow("AI Prompt Engineering for Developers", "Engineering & Product", 8, 0.90),
                createCourseRow("Docker & Kubernetes DevOps Track", "DevOps", 5, 0.50)
        );
        card.getChildren().add(list);

        root.getChildren().addAll(headerBar, card);
        centerContainer.getChildren().add(root);
    }

    private void loadManagerFeedbackView() {
        centerContainer.getChildren().setAll(new com.skillverse.manager.view.ManagerFeedbackView());
    }

    private void loadEmployeeFeedbackView() {
        EmployeeFeedbackView view = new EmployeeFeedbackView(userEmail);
        centerContainer.getChildren().setAll(view.createFeedbackContent(userEmail));
    }

    private void loadHRFeedbackView() {
        loadHRFeedbackViewPage();
    }

    private void loadHRFeedbackViewPage() {
        centerContainer.getChildren().clear();

        VBox root = new VBox(20);
        root.setPadding(new Insets(24, 32, 24, 32));
        root.setStyle("-fx-background-color: #F8FAFC;");

        // Header
        VBox titleBox = new VBox(4);
        javafx.scene.text.Text titleTxt = new javafx.scene.text.Text("HR Feedback & Performance Monitoring");
        titleTxt.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleTxt.setFill(Color.web("#0F172A"));
        Label subLbl = new Label("Real-time manager feedback sent to HR, stored in database.");
        subLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleTxt, subLbl);

        // Stats row
        Label reviewCountLbl = new Label("Loading...");
        reviewCountLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label avgRatingLbl = new Label("—");
        avgRatingLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        VBox statCard1 = new VBox(4,
                new Label("✉ Manager Feedback to HR") {
            {
                setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #2563EB;");
            }
        },
                reviewCountLbl,
                new Label("Direct manager-to-HR feedback") {
            {
                setStyle("-fx-font-size: 11px; -fx-text-fill: #64748B;");
            }
        }
        );
        statCard1.setPadding(new Insets(16));
        statCard1.setStyle("-fx-background-color: #EFF6FF; -fx-border-color: #BFDBFE; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        VBox statCard2 = new VBox(4,
                new Label("★ Avg HR Rating") {
            {
                setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #CA8A04;");
            }
        },
                avgRatingLbl,
                new Label("Avg rating by managers") {
            {
                setStyle("-fx-font-size: 11px; -fx-text-fill: #64748B;");
            }
        }
        );
        statCard2.setPadding(new Insets(16));
        statCard2.setStyle("-fx-background-color: #FEF9C3; -fx-border-color: #FDE68A; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        HBox statsRow = new HBox(16, statCard1, statCard2);

        // Feedback list
        VBox feedbackCard = new VBox(14);
        feedbackCard.setPadding(new Insets(20));
        feedbackCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        HBox fHead = new HBox(12);
        fHead.setAlignment(Pos.CENTER_LEFT);
        Label fTitle = new Label("📋 Manager Feedback to HR — Live Feed");
        fTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Region fSp = new Region();
        HBox.setHgrow(fSp, Priority.ALWAYS);
        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-cursor: hand;");
        fHead.getChildren().addAll(fTitle, fSp, refreshBtn);

        VBox feedList = new VBox(10);
        feedList.getChildren().add(new Label("🔄 Loading manager feedback") {
            {
                setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            }
        });

        feedbackCard.getChildren().addAll(fHead, feedList);

        ScrollPane scroll = new ScrollPane(new VBox(20, titleBox, statsRow, feedbackCard));
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        root.getChildren().add(scroll);
        centerContainer.getChildren().add(root);

        Runnable loadFeedback = () -> {
            FirebaseDAO.getInstance().getAllManagerFeedbacks().thenAccept(allFb -> {
                Platform.runLater(() -> {
                    feedList.getChildren().clear();
                    if (allFb == null || allFb.isEmpty()) {
                        Label empty = new Label("No Manager-to-HR feedback submitted yet.");
                        empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-padding: 16px;");
                        feedList.getChildren().add(empty);
                        reviewCountLbl.setText("0 HR Reviews");
                        avgRatingLbl.setText("—");
                        return;
                    }

                    reviewCountLbl.setText(allFb.size() + " Feedback(s)");
                    double avgR = allFb.stream().mapToDouble(f -> f.getRating()).average().orElse(0);
                    avgRatingLbl.setText(String.format("%.1f / 5.0 ★", avgR));

                    for (com.skillverse.CommonFeatures.EmployeeFeedback fb : allFb) {
                        VBox card = new VBox(8);
                        card.setPadding(new Insets(14, 16, 14, 16));
                        card.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

                        HBox topRow = new HBox(12);
                        topRow.setAlignment(Pos.CENTER_LEFT);
                        Label nameLbl = new Label("👤 " + (fb.getReviewerName() != null ? fb.getReviewerName() : "Manager"));
                        nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0F172A;");
                        Region sp2 = new Region();
                        HBox.setHgrow(sp2, Priority.ALWAYS);
                        Label ratingLbl = new Label(String.format("%.1f ★", fb.getRating()));
                        ratingLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #D97706; -fx-font-size: 13px; -fx-background-color: #FEF3C7; -fx-padding: 3px 8px; -fx-background-radius: 6px;");
                        Label catLbl = new Label(fb.getCategoryTag() != null ? fb.getCategoryTag() : "FEEDBACK");
                        catLbl.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 6px;");
                        topRow.getChildren().addAll(nameLbl, sp2, catLbl, ratingLbl);

                        Label emailLbl = new Label((fb.getReviewerEmail() != null ? fb.getReviewerEmail() : "") + " → " + (fb.getEmployeeEmail() != null ? fb.getEmployeeEmail() : "HR Team"));
                        emailLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748B;");

                        Label textLbl = new Label(fb.getFeedbackText() != null ? fb.getFeedbackText() : "");
                        textLbl.setWrapText(true);
                        textLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #334155; -fx-padding: 4px 0 0 0;");

                        card.getChildren().addAll(topRow, emailLbl, textLbl);
                        feedList.getChildren().add(card);
                    }
                });
            });
        };

        refreshBtn.setOnAction(e -> loadFeedback.run());
        loadFeedback.run();
    }

    private void loadEmployeeSpeakUpView() {
        centerContainer.getChildren().clear();
        EmployeeSpeakUpView view = new EmployeeSpeakUpView(userEmail);
        centerContainer.getChildren().add(view.createContent());
    }

    private void loadHRSpeakUpView() {
        centerContainer.getChildren().clear();
        HRSpeakUpView view = new HRSpeakUpView();
        centerContainer.getChildren().add(view.createContent());
    }

    private void loadManagerSpeakUpDeskView() {
        centerContainer.getChildren().clear();
        ManagerSpeakUpDeskView view = new ManagerSpeakUpDeskView();
        centerContainer.getChildren().add(view.createContent(() -> loadDashboardHome()));
    }

    private void loadManagerReportsView() {
        centerContainer.getChildren().setAll(new com.skillverse.manager.view.ManagerReportsView(userEmail, () -> loadDashboardHome()));
    }

    private void loadEmployeeSkillsView() {
        centerContainer.getChildren().clear();
        String userEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "";

        VBox skillsContent = new EmployeeSkillsView().createSkillsContent(userEmail, target -> loadEmployeeLearningView());
        VBox root = new VBox(16, skillsContent);
        centerContainer.getChildren().add(root);
    }

    private void showAddSkillModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Skill");
        dialog.setHeaderText("Add a new skill to your profile for verification");

        ButtonType addType = new ButtonType("Add Skill", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField skillF = new TextField();
        skillF.setPromptText("Skill Name (e.g. React.js)");
        ComboBox<String> levelCombo = new ComboBox<>();
        levelCombo.getItems().addAll("Beginner", "Intermediate", "Expert");
        levelCombo.setValue("Intermediate");
        TextField catF = new TextField("Frontend");

        grid.add(new Label("Skill Name:"), 0, 0);
        grid.add(skillF, 1, 0);
        grid.add(new Label("Proficiency Level:"), 0, 1);
        grid.add(levelCombo, 1, 1);
        grid.add(new Label("Category:"), 0, 2);
        grid.add(catF, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == addType && !skillF.getText().isBlank()) {
                employeeSkillList.add(new SkillModel(skillF.getText(), levelCombo.getValue(), catF.getText(), 80));
                loadEmployeeSkillsView();
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void loadAIAssistantView() {
        centerContainer.getChildren().clear();
        VBox root = new VBox(20);

        HBox headerRow = new HBox(16);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(2);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Text t = new Text("🤖 AI Learning Assistant");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        t.setFill(Color.web("#0F172A"));
        Text s = new Text("Personalized career intelligence, skill gap analysis, and tailored learning roadmaps.");
        s.setFont(Font.font("Arial", 13));
        s.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(t, s);

        Button clearChatBtn = new Button("🔄 Restart Chat");
        clearChatBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #64748B; -fx-font-size: 12px; -fx-cursor: hand; -fx-padding: 6 12;");

        headerRow.getChildren().addAll(titleBox, clearChatBtn);

        VBox chatCard = new VBox(14);
        chatCard.setPadding(new Insets(20));
        chatCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        VBox chatMessagesBox = new VBox(12);
        chatMessagesBox.setPadding(new Insets(10));
        chatMessagesBox.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        ScrollPane chatScroll = new ScrollPane(chatMessagesBox);
        chatScroll.setFitToWidth(true);
        VBox.setVgrow(chatScroll, Priority.ALWAYS);
        chatScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        final EmployeeContext[] contextHolder = new EmployeeContext[]{new EmployeeContext()};
        contextHolder[0].employeeEmail = userEmail;
        contextHolder[0].fullName = userFullName != null ? userFullName : "Employee";
        contextHolder[0].department = userDepartment != null ? userDepartment : "General";
        contextHolder[0].role = roleName != null ? roleName : "Employee";
        contextHolder[0].experienceLevel = "Mid-Level Professional";
        contextHolder[0].targetCareerGoal = "Senior " + (roleName != null ? roleName : "Specialist");

        FlowPane promptChips = new FlowPane();
        promptChips.setHgap(8);
        promptChips.setVgap(8);
        promptChips.setAlignment(Pos.CENTER_LEFT);

        String[][] quickPrompts = {
            {"🎯 Analyze Skill Gaps", "Analyze my current skill gaps and missing competencies"},
            {"🗺️ Learning Roadmap", "Create a personalized learning roadmap for my career growth"},
            {"📚 Recommend Courses", "Recommend the best training courses for my department and skills"},
            {"🤝 Recommend Mentors", "Match me with available mentors and trainers"},
            {"💼 Suggest Projects", "Suggest suitable internal projects and openings for me"},
            {"🚀 Career Advice", "How can I prepare for promotion and advance in my career?"}
        };

        HBox inputRow = new HBox(10);
        inputRow.setAlignment(Pos.CENTER_LEFT);
        TextField queryInput = new TextField();
        queryInput.setPromptText("Ask AI for a learning roadmap, course recommendation, or skill advice...");
        queryInput.setPrefHeight(44);
        queryInput.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        HBox.setHgrow(queryInput, Priority.ALWAYS);

        Button askBtn = new Button("Ask AI  →");
        askBtn.setPrefHeight(44);
        askBtn.setStyle("-fx-background-color: #8B5CF6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 0 20px; -fx-background-radius: 8px; -fx-cursor: hand;");

        java.util.function.Consumer<String> executeQuery = (queryText) -> {
            if (queryText == null || queryText.trim().isEmpty()) {
                return;
            }
            String trimmedQ = queryText.trim();
            chatMessagesBox.getChildren().add(createChatMessage(userFullName, trimmedQ, true));

            AIChatMessage userMsg = new AIChatMessage(null, userEmail, "User", trimmedQ, "QUERY");
            FirebaseDAO.getInstance().saveAIChatMessage(userMsg);

            String aiResponse = AILearningEngine.processQuery(trimmedQ, contextHolder[0]);
            chatMessagesBox.getChildren().add(createChatMessage("AI Assistant", aiResponse, false));

            AIChatMessage aiMsg = new AIChatMessage(null, userEmail, "AI Assistant", aiResponse, "RESPONSE");
            FirebaseDAO.getInstance().saveAIChatMessage(aiMsg);

            Platform.runLater(() -> {
                chatScroll.layout();
                chatScroll.setVvalue(1.0);
            });
        };

        askBtn.setOnAction(e -> {
            String q = queryInput.getText().trim();
            if (!q.isEmpty()) {
                queryInput.clear();
                executeQuery.accept(q);
            }
        });

        queryInput.setOnAction(e -> {
            String q = queryInput.getText().trim();
            if (!q.isEmpty()) {
                queryInput.clear();
                executeQuery.accept(q);
            }
        });

        for (String[] p : quickPrompts) {
            Button chip = new Button(p[0]);
            chip.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #334155; -fx-font-size: 11px; -fx-font-weight: bold; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px; -fx-padding: 5 12; -fx-cursor: hand;");
            chip.setOnAction(e -> executeQuery.accept(p[1]));
            promptChips.getChildren().add(chip);
        }

        clearChatBtn.setOnAction(e -> {
            FirebaseDAO.getInstance().clearAIChatHistory(userEmail);
            chatMessagesBox.getChildren().clear();
            chatMessagesBox.getChildren().add(createChatMessage("AI Assistant", AILearningEngine.generateGreeting(contextHolder[0]), false));
        });

        inputRow.getChildren().addAll(queryInput, askBtn);
        chatCard.getChildren().addAll(chatScroll, promptChips, inputRow);
        VBox.setVgrow(chatCard, Priority.ALWAYS);

        root.getChildren().addAll(headerRow, chatCard);
        VBox.setVgrow(root, Priority.ALWAYS);
        centerContainer.getChildren().add(root);

        chatMessagesBox.getChildren().add(createChatMessage("AI Assistant", "🔄 Syncing your profile data from SkillVerse...", false));

        FirebaseDAO.getInstance().getSkillsAndGaps(userEmail).thenAccept(skillsList -> {
            contextHolder[0].employeeSkills = skillsList != null ? skillsList : new ArrayList<>();

            FirebaseDAO.getInstance().getEmployeeTrainingProgress(userEmail).thenAccept(enrollmentsList -> {
                contextHolder[0].enrollments = enrollmentsList != null ? enrollmentsList : new ArrayList<>();

                FirebaseDAO.getInstance().getAllTrainingPrograms().thenAccept(progList -> {
                    contextHolder[0].availableCourses = progList != null ? progList : new ArrayList<>();

                    FirebaseDAO.getInstance().getAllTrainersAsync().thenAccept(trainerList -> {
                        contextHolder[0].availableMentors = trainerList != null ? trainerList : new ArrayList<>();

                        FirebaseDAO.getInstance().getActiveJobs().thenAccept(jobsList -> {
                            contextHolder[0].availableProjects = jobsList != null ? jobsList : new ArrayList<>();

                            // Now load persistent chat history
                            FirebaseDAO.getInstance().getAIChatHistory(userEmail).thenAccept(history -> {
                                Platform.runLater(() -> {
                                    chatMessagesBox.getChildren().clear();
                                    if (history != null && !history.isEmpty()) {
                                        for (AIChatMessage msg : history) {
                                            boolean isUser = msg.isUser();
                                            String sender = isUser ? userFullName : "AI Assistant";
                                            chatMessagesBox.getChildren().add(createChatMessage(sender, msg.getMessageText(), isUser));
                                        }
                                    } else {
                                        // Dynamic personalized initial greeting
                                        String initialGreeting = AILearningEngine.generateGreeting(contextHolder[0]);
                                        chatMessagesBox.getChildren().add(createChatMessage("AI Assistant", initialGreeting, false));
                                    }
                                    chatScroll.layout();
                                    chatScroll.setVvalue(1.0);
                                });
                            });
                        });
                    });
                });
            });
        });
    }

    private HBox createChatMessage(String sender, String message, boolean isUser) {
        HBox row = new HBox();
        row.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        VBox bubble = new VBox(4);
        bubble.setPadding(new Insets(10, 14, 10, 14));
        bubble.setMaxWidth(620);
        bubble.setStyle(
                "-fx-background-color: " + (isUser ? "#1E60FF;" : "#FFFFFF;")
                + "-fx-text-fill: " + (isUser ? "white;" : "#0F172A;")
                + "-fx-border-color: " + (isUser ? "#1E60FF;" : "#E2E8F0;")
                + "-fx-border-radius: 12px; -fx-background-radius: 12px;"
        );

        Text sText = new Text(sender);
        sText.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        sText.setFill(isUser ? Color.web("#93C5FD") : Color.web("#8B5CF6"));
        Text mText = new Text(message);
        mText.setFont(Font.font("Arial", 13));
        mText.setFill(isUser ? Color.WHITE : Color.web("#0F172A"));
        mText.setWrappingWidth(580);

        bubble.getChildren().addAll(sText, mText);
        row.getChildren().add(bubble);
        return row;
    }

    private void loadEmployeeGoalsView() {
        centerContainer.getChildren().clear();
        centerContainer.getChildren().setAll(new com.skillverse.employee.view.EmployeeTasksView());
    }

    private void loadEmployeePerformanceView() {
        com.skillverse.employee.view.EmployeePerformanceView view = new com.skillverse.employee.view.EmployeePerformanceView();
        centerContainer.getChildren().setAll(view.createPerformanceContent(userEmail));
    }

    private void loadCareerView() {
        loadCareerOpportunitiesView();
    }

    private VBox careerToastBannerBox = null;

    private void loadCareerOpportunitiesView() {
        centerContainer.getChildren().clear();
        VBox root = new VBox(20);

        VBox titleBox = new VBox(2);
        Text t = new Text("💼 Internal Mobility & Career Opportunities");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        t.setFill(Color.web("#0F172A"));
        Text s = new Text("Explore open internal positions, expand your career path, and track your application status in real time.");
        s.setFont(Font.font("Arial", 13));
        s.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(t, s);

        careerToastBannerBox = new VBox();

        // Tab Navigation Bar
        HBox tabBar = new HBox(12);
        Button tab1Btn = new Button("💼 Explore Openings");
        Button tab2Btn = new Button("📋 My Applications");

        tab1Btn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
        tab2Btn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");

        tabBar.getChildren().addAll(tab1Btn, tab2Btn);

        VBox tabContentContainer = new VBox(16);

        root.getChildren().addAll(titleBox, careerToastBannerBox, tabBar, tabContentContainer);
        centerContainer.getChildren().add(root);

        String userEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "";

        tab1Btn.setOnAction(e -> {
            tab1Btn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
            tab2Btn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
            renderExploreOpeningsTab(tabContentContainer, userEmail);
        });

        tab2Btn.setOnAction(e -> {
            tab2Btn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
            tab1Btn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
            renderMyApplicationsTab(tabContentContainer, userEmail);
        });

        // Default tab: Explore Openings
        renderExploreOpeningsTab(tabContentContainer, userEmail);
    }

    private void renderExploreOpeningsTab(VBox container, String userEmail) {
        container.getChildren().clear();
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        Text jobTitle = new Text("Available Internal Job Openings");
        jobTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        jobTitle.setFill(Color.web("#0F172A"));

        VBox jobList = new VBox(12);
        jobList.getChildren().add(new Label("🔄 Loading active job openings "));
        card.getChildren().addAll(jobTitle, jobList);
        container.getChildren().add(card);

        FirebaseDAO.getInstance().getActiveJobs().thenAccept(activeJobs -> {
            FirebaseDAO.getInstance().getAppliedJobIdsForUserAsync(userEmail).thenAccept(appliedJobIds -> {
                Platform.runLater(() -> renderCareerJobRows(jobList, activeJobs, appliedJobIds));
            });
        });
    }

    private void renderMyApplicationsTab(VBox container, String userEmail) {
        container.getChildren().clear();
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        Text title = new Text("📋 My Submitted Applications & Real-time Status");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        title.setFill(Color.web("#0F172A"));

        VBox myAppsBox = new VBox(14);
        myAppsBox.getChildren().add(new Label("🔄 Loading your submitted applications"));
        card.getChildren().addAll(title, myAppsBox);
        container.getChildren().add(card);

        FirebaseDAO.getInstance().getMyApplications(userEmail).thenAccept(myApps -> {
            Platform.runLater(() -> renderMyApplicationsCards(myAppsBox, myApps));
        });
    }

    private void renderMyApplicationsCards(VBox container, List<JobApplication> myApps) {
        container.getChildren().clear();
        if (myApps == null || myApps.isEmpty()) {
            Label empty = new Label("You have not submitted any internal job applications yet.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (JobApplication app : myApps) {
            VBox appCard = new VBox(14);
            appCard.setPadding(new Insets(18));
            appCard.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

            // Header Row: Title, Department & Dynamic Color Status Badge
            HBox header = new HBox(16);
            header.setAlignment(Pos.CENTER_LEFT);
            VBox info = new VBox(2);
            Text jt = new Text(app.getJobTitle() + " · " + (app.getApplicantDepartment().isBlank() ? "General" : app.getApplicantDepartment()));
            jt.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            jt.setFill(Color.web("#0F172A"));
            Text sub = new Text("Applied Date: " + (app.getAppliedAt() != null && !app.getAppliedAt().isBlank() ? app.getAppliedAt() : "Recent"));
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            info.getChildren().addAll(jt, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            String st = app.getStatus() != null ? app.getStatus().toUpperCase() : "APPLIED";
            Label statusBadge = new Label();
            statusBadge.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            statusBadge.setPadding(new Insets(5, 12, 5, 12));

            if ("OFFERED".equalsIgnoreCase(st) || "SELECTED / OFFERED".equalsIgnoreCase(st) || "Hired".equalsIgnoreCase(st)) {
                statusBadge.setText("🟢 🎉 Congratulations! Selected / Offered");
                statusBadge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-background-radius: 12px;");
            } else if ("REJECTED".equalsIgnoreCase(st)) {
                statusBadge.setText("🔴 Not Selected");
                statusBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #B91C1C; -fx-background-radius: 12px;");
            } else if ("INTERVIEW_SCHEDULED".equalsIgnoreCase(st) || "INTERVIEW".equalsIgnoreCase(st) || "SCHEDULE INTERVIEW".equalsIgnoreCase(st) || "Interviewing".equalsIgnoreCase(st)) {
                statusBadge.setText("🟣 📅 Interview Scheduled");
                statusBadge.setStyle("-fx-background-color: #F3E8FF; -fx-text-fill: #7C3AED; -fx-background-radius: 12px;");
            } else if ("REVIEWING".equalsIgnoreCase(st) || "Under Review".equalsIgnoreCase(st)) {
                statusBadge.setText("🔵 Under Review by HR");
                statusBadge.setStyle("-fx-background-color: #DBEAFE; -fx-text-fill: #1E40AF; -fx-background-radius: 12px;");
            } else {
                statusBadge.setText("🟡 Applied / Submitted");
                statusBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #92400E; -fx-background-radius: 12px;");
            }

            header.getChildren().addAll(info, sp, statusBadge);

            HBox stepper = createApplicationProgressStepper(st);

            appCard.getChildren().addAll(header, stepper);
            container.getChildren().add(appCard);
        }
    }

    private HBox createApplicationProgressStepper(String status) {
        HBox bar = new HBox(0);
        bar.setAlignment(Pos.CENTER);
        bar.setPadding(new Insets(10, 0, 4, 0));

        boolean step1 = true;
        boolean step2 = "REVIEWING".equalsIgnoreCase(status) || "INTERVIEW_SCHEDULED".equalsIgnoreCase(status) || "INTERVIEW".equalsIgnoreCase(status) || "OFFERED".equalsIgnoreCase(status) || "REJECTED".equalsIgnoreCase(status);
        boolean step3 = "INTERVIEW_SCHEDULED".equalsIgnoreCase(status) || "INTERVIEW".equalsIgnoreCase(status) || "OFFERED".equalsIgnoreCase(status);
        boolean step4Offered = "OFFERED".equalsIgnoreCase(status) || "Hired".equalsIgnoreCase(status);
        boolean step4Rejected = "REJECTED".equalsIgnoreCase(status);

        HBox s1 = createStepNode("1", "Applied", step1, false, false);
        Region line1 = createStepLine(step2, false);
        HBox s2 = createStepNode("2", "Under Review", step2, false, false);
        Region line2 = createStepLine(step3, false);
        HBox s3 = createStepNode("3", "Interview", step3, false, false);
        Region line3 = createStepLine(step4Offered || step4Rejected, step4Rejected);
        HBox s4 = createStepNode("4", step4Rejected ? "Not Selected" : (step4Offered ? "Offered 🎉" : "Decision"), step4Offered || step4Rejected, step4Offered, step4Rejected);

        bar.getChildren().addAll(s1, line1, s2, line2, s3, line3, s4);
        return bar;
    }

    private HBox createStepNode(String stepNum, String label, boolean active, boolean isOffered, boolean isRejected) {
        HBox node = new HBox(6);
        node.setAlignment(Pos.CENTER);
        Circle circ = new Circle(10);
        Text num = new Text(stepNum);
        num.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        if (isOffered) {
            circ.setFill(Color.web("#10B981"));
            num.setFill(Color.WHITE);
        } else if (isRejected) {
            circ.setFill(Color.web("#EF4444"));
            num.setFill(Color.WHITE);
        } else if (active) {
            circ.setFill(Color.web("#1E60FF"));
            num.setFill(Color.WHITE);
        } else {
            circ.setFill(Color.web("#E2E8F0"));
            num.setFill(Color.web("#94A3B8"));
        }

        StackPane circleStack = new StackPane(circ, num);
        Text lbl = new Text(label);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        lbl.setFill(Color.web(active ? (isRejected ? "#DC2626" : (isOffered ? "#059669" : "#0F172A")) : "#94A3B8"));

        node.getChildren().addAll(circleStack, lbl);
        return node;
    }

    private Region createStepLine(boolean active, boolean isRejected) {
        Region line = new Region();
        line.setPrefHeight(2);
        HBox.setHgrow(line, Priority.ALWAYS);
        line.setStyle("-fx-background-color: " + (active ? (isRejected ? "#FCA5A5" : "#93C5FD") : "#E2E8F0") + ";");
        return line;
    }

    private void showCareerSuccessToast(String message) {
        if (careerToastBannerBox == null) {
            return;
        }
        careerToastBannerBox.getChildren().clear();
        HBox toast = new HBox(10);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(10, 16, 10, 16));
        toast.setStyle("-fx-background-color: #DCFCE7; -fx-border-color: #86EFAC; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 14px;");
        Text msg = new Text(message);
        msg.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        msg.setFill(Color.web("#15803D"));
        toast.getChildren().addAll(icon, msg);
        careerToastBannerBox.getChildren().add(toast);
    }

    private void renderCareerJobRows(VBox container, List<JobPosting> jobs, List<String> appliedJobIds) {
        container.getChildren().clear();
        if (jobs == null || jobs.isEmpty()) {
            Label empty = new Label("No active internal job openings available at the moment.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (JobPosting job : jobs) {
            HBox r = new HBox(16);
            r.setAlignment(Pos.CENTER_LEFT);
            r.setPadding(new Insets(16));
            r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

            VBox inf = new VBox(4);
            Text jt = new Text(job.getTitle() + "  (" + job.getDepartment() + ")");
            jt.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            jt.setFill(Color.web("#0F172A"));

            HBox tagRow = new HBox(8);
            Label locTag = new Label(job.getLocation());
            locTag.setStyle("-fx-background-color: #E0E7FF; -fx-text-fill: #3730A3; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 6px;");
            Label typeTag = new Label(job.getJobType());
            typeTag.setStyle("-fx-background-color: #F3E8FF; -fx-text-fill: #6B21A8; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 6px;");
            Label expTag = new Label(job.getExperience());
            expTag.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #92400E; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 6px;");
            tagRow.getChildren().addAll(locTag, typeTag, expTag);

            Text sub = new Text("Salary: " + job.getSalaryRange() + (job.getDescription().isBlank() ? "" : (" • " + job.getDescription())));
            sub.setFont(Font.font("Arial", 12));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(jt, tagRow, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            boolean hasApplied = appliedJobIds != null && appliedJobIds.contains(job.getJobId());

            Button applyBtn = new Button(hasApplied ? "✓ Applied" : "Apply Now  →");
            if (hasApplied) {
                applyBtn.setStyle("-fx-background-color: #E2E8F0; -fx-text-fill: #64748B; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8px 18px; -fx-background-radius: 8px;");
                applyBtn.setDisable(true);
            } else {
                applyBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
                applyBtn.setOnAction(e -> showApplyForJobModal(job));
            }

            r.getChildren().addAll(inf, sp, applyBtn);
            container.getChildren().add(r);
        }
    }

    private void showApplyForJobModal(JobPosting job) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Internal Job Application");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType submitBtnType = new ButtonType("Submit Application", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(submitBtnType, cancelBtnType);

        Button submitBtn = (Button) pane.lookupButton(submitBtnType);
        submitBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");
        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(16));
        content.setPrefWidth(460);

        Text title = new Text("🎯 Apply for: " + job.getTitle());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Color.web("#0F172A"));

        User curUser = UserSession.getCurrentUser();
        String uName = curUser != null && curUser.getFullName() != null ? curUser.getFullName() : userFullName;
        String uEmail = curUser != null && curUser.getEmail() != null ? curUser.getEmail() : "user@skillverse.com";
        String uDept = curUser != null && curUser.getDepartment() != null ? curUser.getDepartment() : "General";

        TextField nameF = new TextField(uName);
        nameF.setEditable(false);
        nameF.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextField emailF = new TextField(uEmail);
        emailF.setEditable(false);
        emailF.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextField roleF = new TextField(roleName);
        roleF.setEditable(false);
        roleF.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextField deptF = new TextField(uDept);
        deptF.setEditable(false);
        deptF.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextField expF = new TextField();
        expF.setPromptText("Relevant Experience (e.g. 3 Years in Java & Microservices)");
        expF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextArea noteF = new TextArea();
        noteF.setPromptText("Cover Note / Reason for Internal Transfer...");
        noteF.setPrefRowCount(3);
        noteF.setWrapText(true);
        noteF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox form = new VBox(10);

        Label lbl1 = new Label("Applicant Name");
        lbl1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lbl1.setTextFill(Color.web("#334155"));
        VBox g1 = new VBox(4, lbl1, nameF);

        Label lbl2 = new Label("Email Address");
        lbl2.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lbl2.setTextFill(Color.web("#334155"));
        VBox g2 = new VBox(4, lbl2, emailF);

        HBox row1 = new HBox(10, g1, g2);
        HBox.setHgrow(g1, Priority.ALWAYS);
        HBox.setHgrow(g2, Priority.ALWAYS);

        Label lbl3 = new Label("Current Role");
        lbl3.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lbl3.setTextFill(Color.web("#334155"));
        VBox g3 = new VBox(4, lbl3, roleF);

        Label lbl4 = new Label("Current Department");
        lbl4.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lbl4.setTextFill(Color.web("#334155"));
        VBox g4 = new VBox(4, lbl4, deptF);

        HBox row2 = new HBox(10, g3, g4);
        HBox.setHgrow(g3, Priority.ALWAYS);
        HBox.setHgrow(g4, Priority.ALWAYS);

        Label lbl5 = new Label("Qualifications & Relevant Experience");
        lbl5.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lbl5.setTextFill(Color.web("#334155"));
        VBox g5 = new VBox(4, lbl5, expF);

        Label lbl6 = new Label("Cover Note / Reason for Internal Transfer");
        lbl6.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lbl6.setTextFill(Color.web("#334155"));
        VBox g6 = new VBox(4, lbl6, noteF);

        form.getChildren().addAll(row1, row2, g5, g6);
        content.getChildren().addAll(title, form);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == submitBtnType) {
                JobApplication app = new JobApplication();
                app.setJobId(job.getJobId());
                app.setJobTitle(job.getTitle());
                app.setApplicantName(uName);
                app.setApplicantEmail(uEmail);
                app.setApplicantRole(roleName);
                app.setApplicantDepartment(uDept);
                app.setExperienceYears(expF.getText().trim());
                app.setCoverNote(noteF.getText().trim());
                app.setStatus("APPLIED");

                FirebaseDAO.getInstance().applyForJob(app).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadCareerOpportunitiesView();
                        showCareerSuccessToast("Application submitted successfully to HR! 🚀");
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void loadHRDashboardOverview() {
        VBox titleBox = new VBox(2);
        Text pageTitle = new Text("HR Dashboard Overview");
        pageTitle.setFill(Color.web("#0F172A"));
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        Text pageSub = new Text("Overview of employee directory, recruitment, L&D, performance & reports.");
        pageSub.setFill(Color.web("#64748B"));
        pageSub.setFont(Font.font("Arial", 13));
        titleBox.getChildren().addAll(pageTitle, pageSub);

        VBox heroBanner = new VBox(10);
        heroBanner.setPadding(new Insets(24, 30, 24, 30));
        heroBanner.setStyle("-fx-background-color: linear-gradient(to right, #1E60FF, #0D47A1); -fx-background-radius: 18px;");
        Text bannerRoleTag = new Text("HR WORKSPACE");
        bannerRoleTag.setFill(Color.web("#93C5FD"));
        bannerRoleTag.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        Text bannerHeading = new Text("Welcome back, " + userFullName);
        bannerHeading.setFill(Color.WHITE);
        bannerHeading.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Text bannerSubText = new Text("Manage people, workplace activities and organizational growth from one workspace.");
        bannerSubText.setFill(Color.web("#E0E7FF"));
        bannerSubText.setFont(Font.font("Arial", 13));
        heroBanner.getChildren().addAll(bannerRoleTag, bannerHeading, bannerSubText);

        GridPane quickGrid = new GridPane();
        quickGrid.setHgap(20);
        quickGrid.setVgap(20);

        VBox card1 = createQuickCard("👥", "Employee Management", "Add, update directory, departments & account status", "#1E60FF", () -> loadEmployeeManagementView());
        VBox card2 = createQuickCard("🎯", "Recruitment & Hiring", "Create job postings, track candidates & schedule interviews", "#8B5CF6", () -> loadRecruitmentView());
        VBox card3 = createQuickCard("📈", "Performance Management", "Department reviews, manager reports & appraisal cycles", "#10B981", () -> loadPerformanceView());
        VBox card4 = createQuickCard("🎓", "Learning & Development (L&D)", "Manage courses, training progress & organization skill gaps", "#F59E0B", () -> loadTrainingView());
        VBox card5 = createQuickCard("📑", "Reports & Analytics", "Headcount, skill matrix, recruitment stats & org analytics", "#6366F1", () -> loadReportsView());
        VBox card6 = createQuickCard("📰", "Feed & Communication", "View & broadcast organizational updates", "#EC4899", () -> loadFeedView());

        quickGrid.add(card1, 0, 0);
        quickGrid.add(card2, 1, 0);
        quickGrid.add(card3, 0, 1);
        quickGrid.add(card4, 1, 1);
        quickGrid.add(card5, 0, 2);
        quickGrid.add(card6, 1, 2);

        centerContainer.getChildren().addAll(titleBox, heroBanner, quickGrid);
    }

    private VBox employeeToastBannerBox = null;

    private void loadEmployeeManagementView() {
        centerContainer.getChildren().clear();
        VBox rootBox = new VBox(18);
        HBox headerBar = new HBox();
        headerBar.setAlignment(Pos.CENTER_LEFT);
        VBox titleBox = new VBox(2);
        Text t = new Text("Employee Management");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        t.setFill(Color.web("#0F172A"));
        titleBox.getChildren().add(t);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Button addEmpBtn = new Button("+ Add New Employee");
        addEmpBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
        addEmpBtn.setOnAction(e -> {
            centerContainer.getChildren().clear();
            centerContainer.getChildren().add(new com.skillverse.hr.view.HREmployeeFormView());
        });
        headerBar.getChildren().addAll(titleBox, sp, addEmpBtn);

        employeeToastBannerBox = new VBox();

        // Search Bar Card
        HBox searchCard = new HBox(10);
        searchCard.setAlignment(Pos.CENTER_LEFT);
        searchCard.setPadding(new Insets(10, 16, 10, 16));
        searchCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        Label searchIcon = new Label("🔍");
        searchIcon.setStyle("-fx-font-size: 14px;");
        TextField searchField = new TextField();
        searchField.setPromptText("Filter employees by name, email, or department...");
        searchField.setPrefHeight(36);
        searchField.setStyle("-fx-background-color: transparent; -fx-font-size: 13px;");
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchCard.getChildren().addAll(searchIcon, searchField);

        VBox dirCard = new VBox(16);
        dirCard.setPadding(new Insets(20));
        dirCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        VBox tableBox = new VBox(10);
        Label loadingText = new Label("🔄 Loading live employee directory ");
        loadingText.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        tableBox.getChildren().add(loadingText);

        dirCard.getChildren().addAll(new Text("Registered Team Directory (Live Firestore Collection: users)"), tableBox);

        rootBox.getChildren().addAll(headerBar, employeeToastBannerBox, searchCard, dirCard);
        centerContainer.getChildren().add(rootBox);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            renderEmployeeRows(tableBox, newVal);
        });

        FirebaseDAO.getInstance().getAllEmployeesAsync().thenAccept(users -> {
            Platform.runLater(() -> {
                employeeList.clear();
                for (User u : users) {
                    String role = u.getRole() != null ? u.getRole().toUpperCase() : "EMPLOYEE";

                    if ("EMPLOYEE".equalsIgnoreCase(role)) {
                        String st = u.getStatus() != null ? u.getStatus() : "Active";
                        String jd = u.getJoiningDate() != null ? u.getJoiningDate() : "2024-01-15";
                        employeeList.add(new EmployeeModel(
                                u.getFullName() != null ? u.getFullName() : "Team Member",
                                u.getEmail() != null ? u.getEmail() : "user@skillverse.com",
                                role,
                                u.getDepartment() != null ? u.getDepartment() : "General",
                                role,
                                jd,
                                st
                        ));
                    }
                }
                renderEmployeeRows(tableBox, searchField.getText());
            });
        });
    }

    private void showEmployeeSuccessToast(String message) {
        if (employeeToastBannerBox == null) {
            return;
        }
        employeeToastBannerBox.getChildren().clear();
        HBox toast = new HBox(10);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(10, 16, 10, 16));
        toast.setStyle("-fx-background-color: #DCFCE7; -fx-border-color: #86EFAC; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 14px;");
        Text msg = new Text(message);
        msg.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        msg.setFill(Color.web("#15803D"));
        toast.getChildren().addAll(icon, msg);
        employeeToastBannerBox.getChildren().add(toast);
    }

    private void renderEmployeeRows(VBox container, String query) {
        container.getChildren().clear();
        String filter = query != null ? query.trim().toLowerCase() : "";

        List<EmployeeModel> filtered = new ArrayList<>();
        for (EmployeeModel emp : employeeList) {
            if (filter.isEmpty() || emp.name.toLowerCase().contains(filter) || emp.email.toLowerCase().contains(filter) || emp.department.toLowerCase().contains(filter)) {
                filtered.add(emp);
            }
        }

        if (filtered.isEmpty()) {
            Label emptyLbl = new Label(filter.isEmpty() ? "No registered team members found " : "No employees match search query '" + filter + "'.");
            emptyLbl.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(emptyLbl);
            return;
        }

        for (EmployeeModel emp : filtered) {
            HBox row = new HBox(16);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(12, 16, 12, 16));
            row.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            Circle avatar = new Circle(16, Color.web("#1E60FF"));
            Text initial = new Text(getInitials(emp.name));
            initial.setFill(Color.WHITE);
            initial.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            StackPane avBox = new StackPane(avatar, initial);

            VBox info = new VBox(2);
            Text n = new Text(emp.name + "  (" + emp.role + ")");
            n.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            n.setFill(Color.web("#0F172A"));
            Text sub = new Text(emp.email + " • Dept: " + emp.department + " • Joined: " + emp.joiningDate);
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            info.getChildren().addAll(n, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label statusTag = new Label(emp.status != null ? emp.status.toUpperCase() : "ACTIVE");
            String statusBg = "#DCFCE7;";
            String statusFg = "#15803D;";
            if ("OFFBOARDED".equalsIgnoreCase(emp.status) || "Inactive".equalsIgnoreCase(emp.status)) {
                statusBg = "#FEE2E2;";
                statusFg = "#B91C1C;";
            } else if ("On Leave".equalsIgnoreCase(emp.status)) {
                statusBg = "#FEF3C7;";
                statusFg = "#D97706;";
            }
            statusTag.setStyle("-fx-background-color: " + statusBg + "-fx-text-fill: " + statusFg + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 12px;");

            Button editRoleBtn = new Button("✏️ Edit Role / Dept");
            editRoleBtn.setStyle("-fx-background-color: white; -fx-text-fill: #1E60FF; -fx-border-color: #DBEAFE; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-cursor: hand;");
            editRoleBtn.setOnAction(e -> {
                User u = new User();
                u.setName(emp.name);
                u.setEmail(emp.email);
                u.setRole(emp.role);
                u.setDepartment(emp.department);
                u.setStatus(emp.status);
                centerContainer.getChildren().clear();
                centerContainer.getChildren().add(new com.skillverse.hr.view.HREmployeeFormView(u));
            });

            Button removeBtn = new Button("🗑️ Remove / Offboard");
            removeBtn.setStyle("-fx-background-color: #FEF2F2; -fx-text-fill: #DC2626; -fx-border-color: #FCA5A5; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-cursor: hand;");
            removeBtn.setOnAction(e -> showOffboardConfirmationModal(emp));

            row.getChildren().addAll(avBox, info, sp, statusTag, editRoleBtn, removeBtn);
            container.getChildren().add(row);
        }
    }

    private void showEditEmployeeRoleDeptModal(EmployeeModel emp) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Employee Profile");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType saveBtnType = new ButtonType("Save Changes", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(saveBtnType, cancelBtnType);

        Button saveBtn = (Button) pane.lookupButton(saveBtnType);
        saveBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");
        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(16));
        content.setPrefWidth(420);

        Text title = new Text("✏️ Edit Employee Profile");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Color.web("#0F172A"));

        TextField nameF = new TextField(emp.name);
        nameF.setEditable(false);
        nameF.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextField emailF = new TextField(emp.email);
        emailF.setEditable(false);
        emailF.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        ComboBox<String> deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("Human Resources", "Engineering & Tech", "Sales & Marketing", "Finance", "Product", "Operations", "General");
        deptCombo.setValue(emp.department != null && !emp.department.isBlank() ? emp.department : "Engineering & Tech");
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("EMPLOYEE", "MANAGER", "TRAINER", "HR");
        roleCombo.setValue(emp.role != null ? emp.role.toUpperCase() : "EMPLOYEE");
        roleCombo.setMaxWidth(Double.MAX_VALUE);
        roleCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Active", "On Leave", "Inactive");
        statusCombo.setValue(emp.status != null ? emp.status : "Active");
        statusCombo.setMaxWidth(Double.MAX_VALUE);
        statusCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.add(new Label("Employee Name:"), 0, 0);
        grid.add(nameF, 1, 0);
        grid.add(new Label("Email Address:"), 0, 1);
        grid.add(emailF, 1, 1);
        grid.add(new Label("Department:"), 0, 2);
        grid.add(deptCombo, 1, 2);
        grid.add(new Label("Role / Position:"), 0, 3);
        grid.add(roleCombo, 1, 3);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(statusCombo, 1, 4);

        content.getChildren().addAll(title, grid);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtnType) {
                String newDept = deptCombo.getValue();
                String newRole = roleCombo.getValue();
                String newStatus = statusCombo.getValue();

                FirebaseDAO.getInstance().updateEmployeeStatusAndDeptAsync(emp.email, newStatus, newDept, newRole).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadEmployeeManagementView();
                        showEmployeeSuccessToast(emp.name + " profile updated successfully! 🚀");
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void showOffboardConfirmationModal(EmployeeModel emp) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirm Offboarding / Removal");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #FCA5A5; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType offboardBtnType = new ButtonType("Mark as Inactive / Exited", ButtonData.OK_DONE);
        ButtonType deleteBtnType = new ButtonType("Delete Permanently", ButtonData.OTHER);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        pane.getButtonTypes().addAll(offboardBtnType, deleteBtnType, cancelBtnType);

        Button offboardBtn = (Button) pane.lookupButton(offboardBtnType);
        offboardBtn.setStyle("-fx-background-color: #F59E0B; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 16px;");

        Button deleteBtn = (Button) pane.lookupButton(deleteBtnType);
        deleteBtn.setStyle("-fx-background-color: #DC2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 16px;");

        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 16px;");

        VBox content = new VBox(12);
        content.setPadding(new Insets(16));
        content.setPrefWidth(460);

        HBox titleBox = new HBox(8);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        Label warnIcon = new Label("⚠️");
        warnIcon.setStyle("-fx-font-size: 22px;");
        Text titleText = new Text("Confirm Offboarding / Removal");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleText.setFill(Color.web("#991B1B"));
        titleBox.getChildren().addAll(warnIcon, titleText);

        Text msgText = new Text("Are you sure you want to remove " + emp.name + " (" + emp.email + ") from the company directory?");
        msgText.setFont(Font.font("Arial", 13));
        msgText.setFill(Color.web("#475569"));
        msgText.setWrappingWidth(420);

        VBox optionDesc = new VBox(4);
        optionDesc.setStyle("-fx-background-color: #FEF2F2; -fx-padding: 10px 14px; -fx-background-radius: 8px;");
        Text desc1 = new Text("• Mark as Inactive / Exited: Sets status to OFFBOARDED so audit trails remain safe.");
        desc1.setFont(Font.font("Arial", 11));
        desc1.setFill(Color.web("#7F1D1D"));
        Text desc2 = new Text("• Delete Permanently: Permanently removes the user document");
        desc2.setFont(Font.font("Arial", 11));
        desc2.setFill(Color.web("#7F1D1D"));
        optionDesc.getChildren().addAll(desc1, desc2);

        content.getChildren().addAll(titleBox, msgText, optionDesc);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == offboardBtnType) {
                FirebaseDAO.getInstance().offboardEmployeeAsync(emp.email).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadEmployeeManagementView();
                        showEmployeeSuccessToast(emp.name + " has been marked as OFFBOARDED.");
                    });
                });
            } else if (btn == deleteBtnType) {
                FirebaseDAO.getInstance().deleteUserPermanentlyAsync(emp.email).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadEmployeeManagementView();
                        showEmployeeSuccessToast(emp.name + " has been removed successfully.");
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void showAddEmployeeModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Employee");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType saveBtnType = new ButtonType("Add Employee", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(saveBtnType, cancelBtnType);

        Button saveBtn = (Button) pane.lookupButton(saveBtnType);
        saveBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");
        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(16));
        content.setPrefWidth(420);

        Text title = new Text("👤 Add New Employee");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Color.web("#0F172A"));

        TextField nameF = new TextField();
        nameF.setPromptText("Full Name");
        nameF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextField emailF = new TextField();
        emailF.setPromptText("Email Address");
        emailF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("EMPLOYEE", "MANAGER", "TRAINER", "HR");
        roleCombo.setValue("EMPLOYEE");
        roleCombo.setMaxWidth(Double.MAX_VALUE);
        roleCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        ComboBox<String> deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("Human Resources", "Engineering & Tech", "Sales & Marketing", "Finance", "Product", "Operations", "General");
        deptCombo.setValue("Engineering & Tech");
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        TextField phoneF = new TextField();
        phoneF.setPromptText("Phone Number");
        phoneF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextField dateF = new TextField("2026-08-28");
        dateF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(nameF, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailF, 1, 1);
        grid.add(new Label("Role:"), 0, 2);
        grid.add(roleCombo, 1, 2);
        grid.add(new Label("Department:"), 0, 3);
        grid.add(deptCombo, 1, 3);
        grid.add(new Label("Phone:"), 0, 4);
        grid.add(phoneF, 1, 4);
        grid.add(new Label("Joining Date:"), 0, 5);
        grid.add(dateF, 1, 5);

        content.getChildren().addAll(title, grid);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtnType && !nameF.getText().isBlank() && !emailF.getText().isBlank()) {
                User newEmp = new User();
                newEmp.setFullName(nameF.getText().trim());
                newEmp.setEmail(emailF.getText().trim());
                newEmp.setRole(roleCombo.getValue());
                newEmp.setDepartment(deptCombo.getValue());
                newEmp.setPhone(phoneF.getText().trim());
                newEmp.setStatus("Active");
                newEmp.setJoiningDate(dateF.getText().trim());
                newEmp.setPassword("password123");

                FirebaseDAO.getInstance().addEmployeeAsync(newEmp).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadEmployeeManagementView();
                        showEmployeeSuccessToast(newEmp.getFullName() + " added to directory successfully! 🚀");
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private VBox recruitmentToastBannerBox = null;

    private void loadRecruitmentView() {
        centerContainer.getChildren().clear();
        VBox rootBox = new VBox(20);
        HBox headerBar = new HBox();
        headerBar.setAlignment(Pos.CENTER_LEFT);
        VBox titleBox = new VBox(2);
        Text t = new Text("Recruitment & Hiring");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        t.setFill(Color.web("#0F172A"));
        Text s = new Text("Create job postings, manage active openings & track candidate applications");
        s.setFont(Font.font("Arial", 13));
        s.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(t, s);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Button newJobBtn = new Button("+ Post New Job Opening");
        newJobBtn.setStyle("-fx-background-color: #8B5CF6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
        newJobBtn.setOnAction(e -> {
            centerContainer.getChildren().clear();
            centerContainer.getChildren().add(new com.skillverse.hr.view.HRJobPostingFormView());
        });
        headerBar.getChildren().addAll(titleBox, sp, newJobBtn);

        recruitmentToastBannerBox = new VBox();

        VBox jobsCard = new VBox(16);
        jobsCard.setPadding(new Insets(20));
        jobsCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        VBox jobsBox = new VBox(10);
        jobsBox.getChildren().add(new Label("🔄 Loading active job postings"));
        jobsCard.getChildren().addAll(new Text("Active Job Openings (job_postings)"), jobsBox);

        VBox pipeCard = new VBox(16);
        pipeCard.setPadding(new Insets(20));
        pipeCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        VBox candBox = new VBox(10);
        candBox.getChildren().add(new Label("🔄 Loading candidate applications"));
        pipeCard.getChildren().addAll(new Text("Candidate Application Tracker (job_applications)"), candBox);

        rootBox.getChildren().addAll(headerBar, recruitmentToastBannerBox, jobsCard, pipeCard);
        centerContainer.getChildren().add(rootBox);

        FirebaseDAO.getInstance().getActiveJobs().thenAccept(jobList -> {
            Platform.runLater(() -> renderJobPostingRows(jobsBox, jobList, candBox));
        });

        FirebaseDAO.getInstance().getApplicationsForJob(null).thenAccept(appList -> {
            Platform.runLater(() -> renderCandidateApplications(candBox, appList));
        });
    }

    private void showRecruitmentSuccessToast(String message) {
        if (recruitmentToastBannerBox == null) {
            return;
        }
        recruitmentToastBannerBox.getChildren().clear();
        HBox toast = new HBox(10);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(10, 16, 10, 16));
        toast.setStyle("-fx-background-color: #DCFCE7; -fx-border-color: #86EFAC; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 14px;");
        Text msg = new Text(message);
        msg.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        msg.setFill(Color.web("#15803D"));
        toast.getChildren().addAll(icon, msg);
        recruitmentToastBannerBox.getChildren().add(toast);
    }

    private void renderJobPostingRows(VBox container, List<JobPosting> jobList, VBox candidateBox) {
        container.getChildren().clear();
        if (jobList == null || jobList.isEmpty()) {
            Label empty = new Label("No active job postings. Click '+ Post New Job Opening' to add one.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (JobPosting job : jobList) {
            HBox row = new HBox(16);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(12, 16, 12, 16));
            row.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            VBox info = new VBox(2);
            Text t = new Text(job.getTitle() + " (" + job.getDepartment() + ")");
            t.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            t.setFill(Color.web("#0F172A"));
            Text sub = new Text("Location: " + job.getLocation() + " • Type: " + job.getJobType() + " • Exp: " + job.getExperience() + " • Salary: " + job.getSalaryRange());
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            info.getChildren().addAll(t, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label statusTag = new Label("OPEN");
            statusTag.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 12px;");

            Button viewAppsBtn = new Button("View Applicants");
            viewAppsBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-border-color: #DBEAFE; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-cursor: hand;");
            viewAppsBtn.setOnAction(e -> {
                FirebaseDAO.getInstance().getApplicationsForJob(job.getJobId()).thenAccept(appList -> {
                    Platform.runLater(() -> renderCandidateApplications(candidateBox, appList));
                });
            });

            Button closeJobBtn = new Button("Close Opening");
            closeJobBtn.setStyle("-fx-background-color: #FEF2F2; -fx-text-fill: #DC2626; -fx-border-color: #FCA5A5; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-cursor: hand;");
            closeJobBtn.setOnAction(e -> {
                FirebaseDAO.getInstance().closeJobPostingAsync(job.getJobId()).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadRecruitmentView();
                        showRecruitmentSuccessToast("Job opening for '" + job.getTitle() + "' has been closed.");
                    });
                });
            });

            row.getChildren().addAll(info, sp, statusTag, viewAppsBtn, closeJobBtn);
            container.getChildren().add(row);
        }
    }

    private void renderCandidateApplications(VBox container, List<JobApplication> appList) {
        container.getChildren().clear();
        if (appList == null || appList.isEmpty()) {
            Label empty = new Label("No candidate applications found for this position.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (JobApplication app : appList) {
            HBox row = new HBox(16);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(12, 16, 12, 16));
            row.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            String appId = app.getApplicationId();
            String name = app.getApplicantName();
            String jobTitle = app.getJobTitle();
            String email = app.getApplicantEmail();
            String stage = app.getStatus() != null ? app.getStatus() : "APPLIED";
            String role = app.getApplicantRole() != null ? app.getApplicantRole() : "EMPLOYEE";
            String exp = app.getExperienceYears() != null ? app.getExperienceYears() : "";

            VBox info = new VBox(2);
            Text n = new Text(name + "  (" + role + ")");
            n.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            n.setFill(Color.web("#0F172A"));
            Text sub = new Text("Applied: " + jobTitle + (email.isBlank() ? "" : (" • " + email)) + (exp.isBlank() ? "" : (" • Exp: " + exp)));
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            info.getChildren().addAll(n, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label stageTag = new Label(stage.toUpperCase());
            String tagStyle = "-fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 12px; ";
            if ("OFFERED".equalsIgnoreCase(stage) || "SELECTED / OFFERED".equalsIgnoreCase(stage) || "Hired".equalsIgnoreCase(stage)) {
                tagStyle += "-fx-background-color: #DCFCE7; -fx-text-fill: #15803D;";
            } else if ("REJECTED".equalsIgnoreCase(stage)) {
                tagStyle += "-fx-background-color: #FEE2E2; -fx-text-fill: #B91C1C;";
            } else if ("INTERVIEW_SCHEDULED".equalsIgnoreCase(stage) || "INTERVIEW".equalsIgnoreCase(stage) || "SCHEDULE INTERVIEW".equalsIgnoreCase(stage) || "Interviewing".equalsIgnoreCase(stage)) {
                tagStyle += "-fx-background-color: #F3E8FF; -fx-text-fill: #7C3AED;";
            } else if ("REVIEWING".equalsIgnoreCase(stage) || "Under Review".equalsIgnoreCase(stage)) {
                tagStyle += "-fx-background-color: #DBEAFE; -fx-text-fill: #1E40AF;";
            } else {
                tagStyle += "-fx-background-color: #FEF3C7; -fx-text-fill: #92400E;";
            }
            stageTag.setStyle(tagStyle);

            ComboBox<String> stageBox = new ComboBox<>();
            stageBox.getItems().addAll("REVIEWING", "INTERVIEW_SCHEDULED", "OFFERED", "REJECTED");
            stageBox.setValue(stage.toUpperCase().contains("INTERVIEW") ? "INTERVIEW_SCHEDULED" : stage.toUpperCase());
            stageBox.setStyle("-fx-font-size: 11px; -fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 6px;");
            stageBox.setOnAction(e -> {
                String newStage = stageBox.getValue();
                FirebaseDAO.getInstance().updateApplicationStatus(appId, newStage).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadRecruitmentView();
                        showRecruitmentSuccessToast("Candidate " + name + " status updated to " + newStage + "! 🎯");
                    });
                });
            });

            row.getChildren().addAll(info, sp, stageTag, stageBox);
            container.getChildren().add(row);
        }
    }

    private void showCreateJobModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Post New Job Opening");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType postBtnType = new ButtonType("Publish Job Opening", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(postBtnType, cancelBtnType);

        Button postBtn = (Button) pane.lookupButton(postBtnType);
        postBtn.setStyle("-fx-background-color: #8B5CF6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");
        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(16));
        content.setPrefWidth(460);

        Text title = new Text("🎯 Post New Job Opening");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Color.web("#0F172A"));

        TextField titleF = new TextField();
        titleF.setPromptText("Job Title (e.g. Lead Full-Stack Engineer)");
        titleF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        ComboBox<String> deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("Engineering & Tech", "Human Resources", "Sales & Marketing", "Finance", "Product", "Operations", "Design");
        deptCombo.setValue("Engineering & Tech");
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        ComboBox<String> expCombo = new ComboBox<>();
        expCombo.getItems().addAll("Entry Level", "1-3 Years", "3-5 Years", "5+ Years", "Executive");
        expCombo.setValue("3-5 Years");
        expCombo.setMaxWidth(Double.MAX_VALUE);
        expCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Full-time", "Part-time", "Contract", "Remote");
        typeCombo.setValue("Full-time");
        typeCombo.setMaxWidth(Double.MAX_VALUE);
        typeCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        TextField salF = new TextField();
        salF.setPromptText("Salary Range (e.g. $90,000 - $120,000 / yr)");
        salF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextArea descF = new TextArea();
        descF.setPromptText("Job Description & Key Responsibilities...");
        descF.setPrefRowCount(3);
        descF.setWrapText(true);
        descF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.add(new Label("Job Title:"), 0, 0);
        grid.add(titleF, 1, 0);
        grid.add(new Label("Department:"), 0, 1);
        grid.add(deptCombo, 1, 1);
        grid.add(new Label("Experience:"), 0, 2);
        grid.add(expCombo, 1, 2);
        grid.add(new Label("Job Type:"), 0, 3);
        grid.add(typeCombo, 1, 3);
        grid.add(new Label("Salary Range:"), 0, 4);
        grid.add(salF, 1, 4);
        grid.add(new Label("Description:"), 0, 5);
        grid.add(descF, 1, 5);

        content.getChildren().addAll(title, grid);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == postBtnType && !titleF.getText().isBlank()) {
                JobPosting jp = new JobPosting();
                jp.setTitle(titleF.getText().trim());
                jp.setDepartment(deptCombo.getValue());
                jp.setExperience(expCombo.getValue());
                jp.setJobType(typeCombo.getValue());
                jp.setLocation(typeCombo.getValue().contains("Remote") ? "Remote" : "Hybrid");
                jp.setSalaryRange(salF.getText().trim());
                jp.setDescription(descF.getText().trim());
                jp.setPostedByEmail(UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "");
                jp.setStatus("OPEN");

                FirebaseDAO.getInstance().createJobPosting(jp).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadRecruitmentView();
                        showRecruitmentSuccessToast("Job Opening for '" + jp.getTitle() + "' published successfully! 🚀");
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private VBox trainingToastBannerBox = null;

    private void loadTrainingView() {
        centerContainer.getChildren().clear();
        VBox rootBox = new VBox(20);
        HBox headerBar = new HBox();
        headerBar.setAlignment(Pos.CENTER_LEFT);
        VBox titleBox = new VBox(2);
        Text t = new Text("🎓 Learning & Development (L&D)");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        t.setFill(Color.web("#0F172A"));
        Text s = new Text("Create training programs, assign registered trainers, and track employee progress.");
        s.setFont(Font.font("Arial", 13));
        s.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(t, s);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Button newProgBtn = new Button("+ Create & Assign Training Program");
        newProgBtn.setStyle("-fx-background-color: #F59E0B; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 8px; -fx-cursor: hand;");
        newProgBtn.setOnAction(e -> {
            centerContainer.getChildren().clear();
            centerContainer.getChildren().add(new com.skillverse.hr.view.HRTrainingProgramFormView());
        });
        headerBar.getChildren().addAll(titleBox, sp, newProgBtn);

        trainingToastBannerBox = new VBox();

        VBox courseCard = new VBox(16);
        courseCard.setPadding(new Insets(20));
        courseCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        Text listTitle = new Text("Active Company Training Programs (training_programs)");
        listTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        listTitle.setFill(Color.web("#0F172A"));

        VBox courseList = new VBox(12);
        courseList.getChildren().add(new Label("🔄 Loading active training programs"));
        courseCard.getChildren().addAll(listTitle, courseList);

        rootBox.getChildren().addAll(headerBar, trainingToastBannerBox, courseCard);
        centerContainer.getChildren().add(rootBox);

        FirebaseDAO.getInstance().getAllTrainingPrograms().thenAccept(programs -> {
            Platform.runLater(() -> renderTrainingProgramsList(courseList, programs));
        });
    }

    private void showTrainingSuccessToast(String message) {
        if (trainingToastBannerBox == null) {
            return;
        }
        trainingToastBannerBox.getChildren().clear();
        HBox toast = new HBox(10);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(10, 16, 10, 16));
        toast.setStyle("-fx-background-color: #DCFCE7; -fx-border-color: #86EFAC; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 14px;");
        Text msg = new Text(message);
        msg.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        msg.setFill(Color.web("#15803D"));
        toast.getChildren().addAll(icon, msg);
    }

    private void showAssignTrainingModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create & Assign Training Program");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType saveBtnType = new ButtonType("Publish & Assign Program", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(saveBtnType, cancelBtnType);

        Button saveBtn = (Button) pane.lookupButton(saveBtnType);
        saveBtn.setStyle("-fx-background-color: #F59E0B; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");
        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(16));
        content.setPrefWidth(460);
        Text title = new Text("🎓 Create New Training Program");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Color.web("#0F172A"));

        TextField titleF = new TextField();
        titleF.setPromptText("e.g. Advanced Java & Cloud Microservices");
        titleF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1; -fx-border-radius: 6px;");
        TextField descF = new TextField();
        descF.setPromptText("e.g. Full-stack cloud architecture training with Spring Boot & Firebase");
        descF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1; -fx-border-radius: 6px;");

        ComboBox<String> trainerCombo = new ComboBox<>();
        trainerCombo.setPromptText("Loading registered trainers...");
        trainerCombo.setMaxWidth(Double.MAX_VALUE);
        trainerCombo.setStyle("-fx-padding: 4px; -fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 6px;");

        FirebaseDAO.getInstance().getAllTrainersAsync().thenAccept(trainers -> {
            Platform.runLater(() -> {
                trainerCombo.getItems().clear();
                if (trainers == null || trainers.isEmpty()) {
                    trainerCombo.getItems().add("trainer@skillverse.com (Default Trainer)");
                } else {
                    for (User u : trainers) {
                        trainerCombo.getItems().add(u.getFullName() + " (" + u.getEmail() + ")");
                    }
                }
                if (!trainerCombo.getItems().isEmpty()) {
                    trainerCombo.setValue(trainerCombo.getItems().get(0));
                }
            });
        });

        ComboBox<String> deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("Engineering", "HR & Talent", "Sales & Growth", "Finance", "Operations", "All Departments");
        deptCombo.setValue("Engineering");
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setStyle("-fx-padding: 4px; -fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 6px;");

        TextField durationF = new TextField("4 Weeks");
        durationF.setPromptText("Duration (e.g. 4 Weeks)");
        durationF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1; -fx-border-radius: 6px;");
        TextField startDateF = new TextField("Aug 30, 2026");
        startDateF.setPromptText("Start Date");
        startDateF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1; -fx-border-radius: 6px;");

        VBox form = new VBox(10,
                new VBox(4, new Label("Program Title:"), titleF),
                new VBox(4, new Label("Description:"), descF),
                new VBox(4, new Label("Assigned Trainer:"), trainerCombo),
                new VBox(4, new Label("Target Department:"), deptCombo),
                new HBox(10,
                        new VBox(4, new Label("Duration:"), durationF),
                        new VBox(4, new Label("Start Date:"), startDateF)
                )
        );

        content.getChildren().addAll(title, form);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtnType && !titleF.getText().isBlank()) {
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
                tp.setTitle(titleF.getText().trim());
                tp.setDescription(descF.getText().trim());
                tp.setAssignedTrainerName(trainerName);
                tp.setAssignedTrainerEmail(trainerEmail);
                tp.setDepartment(deptCombo.getValue());
                tp.setDurationWeeks(durationF.getText().trim());
                tp.setStartDate(startDateF.getText().trim());
                tp.setStatus("ACTIVE");

                FirebaseDAO.getInstance().createTrainingProgram(tp).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadTrainingView();
                        showTrainingSuccessToast("Training program '" + tp.getTitle() + "' created and assigned to " + tp.getAssignedTrainerName() + "! 🎓");
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void renderTrainingProgramsList(VBox container, List<TrainingProgram> programs) {
        container.getChildren().clear();
        if (programs == null || programs.isEmpty()) {
            Label empty = new Label("No active training programs found. Click '+ Create & Assign Training Program' to assign one.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (TrainingProgram tp : programs) {
            HBox r = new HBox(16);
            r.setAlignment(Pos.CENTER_LEFT);
            r.setPadding(new Insets(14, 16, 14, 16));
            r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            VBox inf = new VBox(2);
            Text n = new Text(tp.getTitle());
            n.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            n.setFill(Color.web("#0F172A"));
            Text sub = new Text("Trainer: " + tp.getAssignedTrainerName() + " (" + tp.getAssignedTrainerEmail() + ") • Dept: " + tp.getDepartment() + " • Duration: " + tp.getDurationWeeks());
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(n, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label badge = new Label(tp.getStatus());
            badge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4px 12px; -fx-background-radius: 12px;");

            r.getChildren().addAll(inf, sp, badge);
            container.getChildren().add(r);
        }
    }

    private VBox trainerToastBannerBox = null;

    private void loadTrainerTrainingsView() {
        centerContainer.getChildren().clear();
        TrainerTrainingsView trainerView = new TrainerTrainingsView();
        centerContainer.getChildren().addAll(trainerView);
    }

    private void showTrainerSuccessToast(String message) {
        if (trainerToastBannerBox == null) {
            return;
        }
        trainerToastBannerBox.getChildren().clear();
        HBox toast = new HBox(10);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(10, 16, 10, 16));
        toast.setStyle("-fx-background-color: #DCFCE7; -fx-border-color: #86EFAC; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 14px;");
        Text msg = new Text(message);
        msg.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        msg.setFill(Color.web("#15803D"));
        toast.getChildren().addAll(icon, msg);
    }

    private void showTrainerCreateProgramModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Training Program");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType publishBtnType = new ButtonType("Publish / Create Course", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(publishBtnType, cancelBtnType);

        Button pubBtn = (Button) pane.lookupButton(publishBtnType);
        pubBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");
        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(16));
        content.setPrefWidth(460);
        Text title = new Text("🎓 Add New Training Course");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Color.web("#0F172A"));

        TextField titleF = new TextField();
        titleF.setPromptText("Course Title (e.g. React & TypeScript Engineering)");
        titleF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        ComboBox<String> deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("Engineering", "HR", "Marketing", "Sales", "All");
        deptCombo.setValue("Engineering");
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setStyle("-fx-padding: 4px; -fx-background-color: white; -fx-border-color: #CBD5E1;");

        TextField durationF = new TextField("4 Weeks");
        durationF.setPromptText("Duration (Weeks / Hours)");
        durationF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");
        TextField startF = new TextField("Aug 30, 2026");
        startF.setPromptText("Start Date");
        startF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");
        TextField endF = new TextField("Sep 30, 2026");
        endF.setPromptText("End Date");
        endF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        TextArea descF = new TextArea();
        descF.setPromptText("Course Description / Curriculum Outline...");
        descF.setPrefRowCount(3);
        descF.setWrapText(true);
        descF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        VBox form = new VBox(10,
                new VBox(4, new Label("Program Title:"), titleF),
                new VBox(4, new Label("Target Department:"), deptCombo),
                new HBox(10,
                        new VBox(4, new Label("Duration:"), durationF),
                        new VBox(4, new Label("Start Date:"), startF),
                        new VBox(4, new Label("End Date:"), endF)
                ),
                new VBox(4, new Label("Course Description / Outline:"), descF)
        );

        content.getChildren().addAll(title, form);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == publishBtnType && !titleF.getText().isBlank()) {
                String tEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail().toLowerCase().trim() : "trainer@skillverse.com";
                String tName = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getFullName() : "Trainer";

                TrainingProgram tp = new TrainingProgram();
                tp.setTitle(titleF.getText().trim());
                tp.setDescription(descF.getText().trim());
                tp.setAssignedTrainerEmail(tEmail);
                tp.setAssignedTrainerName(tName);
                tp.setDepartment(deptCombo.getValue());
                tp.setDurationWeeks(durationF.getText().trim());
                tp.setStartDate(startF.getText().trim());
                tp.setEndDate(endF.getText().trim());
                tp.setStatus("ACTIVE");

                FirebaseDAO.getInstance().createTrainingProgram(tp).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadTrainerTrainingsView();
                        showTrainerSuccessToast("Training program created successfully! 🎓");
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void renderTrainerProgramsList(VBox container, VBox gradingBox, List<TrainingProgram> programs) {
        container.getChildren().clear();
        if (programs == null || programs.isEmpty()) {
            Label empty = new Label("No training programs currently assigned or created. Click '+ Add Training Program' to create one!");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (TrainingProgram tp : programs) {
            HBox r = new HBox(16);
            r.setAlignment(Pos.CENTER_LEFT);
            r.setPadding(new Insets(14, 16, 14, 16));
            r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            VBox inf = new VBox(2);
            Text n = new Text(tp.getTitle());
            n.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            n.setFill(Color.web("#0F172A"));
            Text sub = new Text("Trainer: " + tp.getAssignedTrainerName() + " • Dept: " + tp.getDepartment() + " • Duration: " + tp.getDurationWeeks() + " • Start: " + tp.getStartDate());
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(n, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            boolean isCompleted = "COMPLETED".equalsIgnoreCase(tp.getStatus());

            Label statusBadge = new Label(isCompleted ? "COMPLETED" : "ACTIVE");
            statusBadge.setStyle(isCompleted ? "-fx-background-color: #E2E8F0; -fx-text-fill: #475569; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 10px;"
                    : "-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 10px;");

            Button viewGradingBtn = new Button("👥 View Enrolled Students & Grade");
            viewGradingBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-size: 11px;");
            viewGradingBtn.setOnAction(e -> renderProgramStudentGradingView(gradingBox, tp));

            Button completeBtn = new Button(isCompleted ? "✓ Completed" : "Mark as Completed");
            completeBtn.setDisable(isCompleted);
            completeBtn.setStyle(isCompleted ? "-fx-background-color: #F1F5F9; -fx-text-fill: #94A3B8; -fx-font-weight: bold; -fx-padding: 6px 12px; -fx-background-radius: 8px; -fx-font-size: 11px;"
                    : "-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 6px 12px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-size: 11px;");
            completeBtn.setOnAction(e -> {
                FirebaseDAO.getInstance().updateTrainingProgramStatusAsync(tp.getProgramId(), "COMPLETED").thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadTrainerTrainingsView();
                        showTrainerSuccessToast("Training program '" + tp.getTitle() + "' marked as COMPLETED! 🎉");
                    });
                });
            });

            r.getChildren().addAll(inf, sp, statusBadge, viewGradingBtn, completeBtn);
            container.getChildren().add(r);
        }
    }

    private void renderProgramStudentGradingView(VBox gradingBox, TrainingProgram program) {
        gradingBox.getChildren().clear();

        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        Text title = new Text("👥 Student Roster & Grading: " + program.getTitle());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        title.setFill(Color.web("#0F172A"));

        VBox studentList = new VBox(12);
        studentList.getChildren().add(new Label("🔄 Loading enrolled employees"));
        card.getChildren().addAll(title, studentList);
        gradingBox.getChildren().add(card);

        FirebaseDAO.getInstance().getEnrollmentsForProgram(program.getProgramId()).thenAccept(enrollments -> {
            Platform.runLater(() -> renderStudentGradingRows(studentList, program, enrollments, gradingBox));
        });
    }

    private void renderStudentGradingRows(VBox container, TrainingProgram program, List<TrainingEnrollment> enrollments, VBox gradingBox) {
        container.getChildren().clear();
        if (enrollments == null || enrollments.isEmpty()) {
            Label empty = new Label("No employees have enrolled in this training program yet.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (TrainingEnrollment te : enrollments) {
            HBox r = new HBox(16);
            r.setAlignment(Pos.CENTER_LEFT);
            r.setPadding(new Insets(12, 16, 12, 16));
            r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            VBox inf = new VBox(2);
            Text n = new Text(te.getEmployeeName() + " (" + te.getEmployeeDepartment() + ")");
            n.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            n.setFill(Color.web("#0F172A"));
            Text sub = new Text("Email: " + te.getEmployeeEmail() + " • Status: " + te.getCompletionStatus());
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(n, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label attTag = new Label("Attendance: " + (int) te.getAttendancePercentage() + "%");
            attTag.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E40AF; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4px 10px; -fx-background-radius: 10px;");

            Label scoreTag = new Label("Score: " + (int) te.getAssessmentScore() + "%");
            scoreTag.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4px 10px; -fx-background-radius: 10px;");

            Button gradeBtn = new Button("✍️ Grade / Update Scores");
            gradeBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 12px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-size: 11px;");
            gradeBtn.setOnAction(e -> showUpdateStudentScoresModal(te, () -> renderProgramStudentGradingView(gradingBox, program)));

            r.getChildren().addAll(inf, sp, attTag, scoreTag, gradeBtn);
            container.getChildren().add(r);
        }
    }

    private void showUpdateStudentScoresModal(TrainingEnrollment te, Runnable onSuccess) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Student Scores & Attendance");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType saveBtnType = new ButtonType("Save Marks & Attendance", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(saveBtnType, cancelBtnType);

        Button saveBtn = (Button) pane.lookupButton(saveBtnType);
        saveBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");
        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(16));
        content.setPrefWidth(420);
        Text title = new Text("✍️ Update Scores for " + te.getEmployeeName());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Color.web("#0F172A"));

        TextField attF = new TextField(String.valueOf((int) te.getAttendancePercentage()));
        attF.setPromptText("Attendance % (0-100)");
        attF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");
        TextField scoreF = new TextField(String.valueOf((int) te.getAssessmentScore()));
        scoreF.setPromptText("Assessment Score % (0-100)");
        scoreF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("IN_PROGRESS", "COMPLETED", "DROPPED");
        statusCombo.setValue(te.getCompletionStatus() != null ? te.getCompletionStatus() : "IN_PROGRESS");
        statusCombo.setMaxWidth(Double.MAX_VALUE);
        statusCombo.setStyle("-fx-padding: 4px; -fx-background-color: white; -fx-border-color: #CBD5E1;");

        TextField dateF = new TextField("Aug 29, 2026");
        dateF.setPromptText("Completion Date");
        dateF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        VBox form = new VBox(10,
                new VBox(4, new Label("Attendance Percentage (%):"), attF),
                new VBox(4, new Label("Assessment Test Score (%):"), scoreF),
                new VBox(4, new Label("Completion Status:"), statusCombo),
                new VBox(4, new Label("Completion Date:"), dateF)
        );

        content.getChildren().addAll(title, form);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtnType) {
                double attVal = 0.0;
                double scoreVal = 0.0;
                try {
                    attVal = Double.parseDouble(attF.getText().trim());
                } catch (Exception ex) {
                }
                try {
                    scoreVal = Double.parseDouble(scoreF.getText().trim());
                } catch (Exception ex) {
                }

                String newStatus = statusCombo.getValue();
                String dateVal = dateF.getText().trim();

                FirebaseDAO.getInstance().updateTrainerGrading(te.getEnrollmentId(), attVal, scoreVal, newStatus, dateVal).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        showTrainerSuccessToast("Employee training records updated successfully for " + te.getEmployeeName() + "! 🚀");
                        if (onSuccess != null) {
                            onSuccess.run();
                        }
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private VBox employeeLearningToastBox = null;

    private void loadEmployeeLearningView() {
        centerContainer.getChildren().clear();
        VBox rootBox = new VBox(20);

        VBox titleBox = new VBox(2);
        Text t = new Text("Learning Hub & Skill Development");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        t.setFill(Color.web("#0F172A"));
        Text s = new Text("Track your enrolled courses, live sessions, and assessments.");
        s.setFont(Font.font("Arial", 13));
        s.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(t, s);

        employeeLearningToastBox = new VBox();

        HBox twoColumnGrid = new HBox(20);

        VBox leftColumn = new VBox(16);
        leftColumn.prefWidthProperty().bind(twoColumnGrid.widthProperty().multiply(0.59));

        VBox myTrainingsCard = new VBox(14);
        myTrainingsCard.setPadding(new Insets(18));
        myTrainingsCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");
        Text myTitle = new Text("My Active Courses & Performance");
        myTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        myTitle.setFill(Color.web("#0F172A"));
        VBox myTrainingsList = new VBox(12);
        myTrainingsList.getChildren().add(new Label("🔄 Loading your active courses..."));
        myTrainingsCard.getChildren().addAll(myTitle, myTrainingsList);

        VBox exploreCard = new VBox(14);
        exploreCard.setPadding(new Insets(18));
        exploreCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");
        Text exploreTitle = new Text("Explore & Enroll New Programs");
        exploreTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        exploreTitle.setFill(Color.web("#0F172A"));
        VBox exploreList = new VBox(12);
        exploreList.getChildren().add(new Label("🔄 Loading available training programs..."));
        exploreCard.getChildren().addAll(exploreTitle, exploreList);

        leftColumn.getChildren().addAll(myTrainingsCard, exploreCard);

        // RIGHT COLUMN (~40% Width)
        VBox rightColumn = new VBox(16);
        rightColumn.prefWidthProperty().bind(twoColumnGrid.widthProperty().multiply(0.39));

        VBox liveCard = new VBox(14);
        liveCard.setPadding(new Insets(18));
        liveCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");
        Text liveTitle = new Text("🎥 Upcoming Live Lectures & Events");
        liveTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        liveTitle.setFill(Color.web("#0F172A"));
        VBox liveList = new VBox(12);
        liveList.getChildren().add(new Label("🔄 Loading upcoming live lectures..."));
        liveCard.getChildren().addAll(liveTitle, liveList);

        VBox assessmentsCard = new VBox(14);
        assessmentsCard.setPadding(new Insets(18));
        assessmentsCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");
        Text assessTitle = new Text("📝 Pending Assessments & Quizzes");
        assessTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        assessTitle.setFill(Color.web("#0F172A"));
        VBox assessList = new VBox(12);
        assessList.getChildren().add(new Label("🔄 Loading pending assessments..."));
        assessmentsCard.getChildren().addAll(assessTitle, assessList);

        rightColumn.getChildren().addAll(liveCard, assessmentsCard);

        twoColumnGrid.getChildren().addAll(leftColumn, rightColumn);

        rootBox.getChildren().addAll(titleBox, employeeLearningToastBox, twoColumnGrid);
        centerContainer.getChildren().add(rootBox);

        String userEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "";
        User currentUser = UserSession.getCurrentUser();
        String dept = currentUser != null ? currentUser.getDepartment() : "ALL";

        FirebaseDAO.getInstance().getEmployeeTrainingProgress(userEmail).thenAccept(myEnrollments -> {
            Platform.runLater(() -> renderMyEnrolledTrainings(myTrainingsList, myEnrollments));

            FirebaseDAO.getInstance().getAllTrainingPrograms().thenAccept(allPrograms -> {
                Platform.runLater(() -> renderExploreTrainingPrograms(exploreList, allPrograms, myEnrollments, userEmail, myTrainingsList));
            });
        });

        FirebaseDAO.getInstance().getAllSeminarEvents().thenAccept(events -> {
            Platform.runLater(() -> renderLiveSeminarsSection(liveList, events));
        });

        FirebaseDAO.getInstance().getAssessmentsForEmployee(dept).thenAccept(assessments -> {
            Platform.runLater(() -> renderPendingAssessmentsSection(assessList, assessments));
        });
    }

    private void renderLiveSeminarsSection(VBox container, List<SeminarEvent> events) {
        container.getChildren().clear();
        if (events == null || events.isEmpty()) {
            Label empty = new Label("No live seminars scheduled at the moment.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (SeminarEvent event : events) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(14));
            card.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

            FlowPane badgesPane = new FlowPane();
            badgesPane.setHgap(8);
            badgesPane.setVgap(8);
            badgesPane.setAlignment(Pos.CENTER_LEFT);

            Label liveIcon = new Label("📡 LIVE SESSION");
            liveIcon.setMinWidth(Region.USE_PREF_SIZE);
            liveIcon.setStyle("-fx-background-color: #F3E8FF; -fx-text-fill: #7C3AED; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 11px;");

            Label dateBadge = new Label("📅 " + (event.getEventDate() != null ? event.getEventDate() : "Upcoming") + " • " + (event.getEventTime() != null ? event.getEventTime() : "TBD"));
            dateBadge.setMinWidth(Region.USE_PREF_SIZE);
            dateBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 11px;");

            Label deptBadge = new Label("🏢 Dept: " + (event.getTargetDepartment() != null ? event.getTargetDepartment() : "ALL"));
            deptBadge.setMinWidth(Region.USE_PREF_SIZE);
            deptBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 11px;");

            badgesPane.getChildren().addAll(liveIcon, dateBadge, deptBadge);

            Text titleText = new Text(event.getTitle() != null ? event.getTitle() : "Live Seminar");
            titleText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            titleText.setFill(Color.web("#0F172A"));

            Text subText = new Text("Topic: " + (event.getTopic() != null ? event.getTopic() : "General") + " • Trainer: " + (event.getTrainerName() != null ? event.getTrainerName() : "Senior Trainer"));
            subText.setFont(Font.font("Arial", 12));
            subText.setFill(Color.web("#64748B"));

            Button joinBtn = new Button("🎥 Join Live Lecture / Session");
            joinBtn.setMaxWidth(Double.MAX_VALUE);
            joinBtn.setStyle("-fx-background-color: linear-gradient(to right, #1E60FF, #7C3AED); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 8px; -fx-cursor: hand;");
            joinBtn.setOnAction(e -> {
                String link = event.getMeetingLinkOrVenue() != null && !event.getMeetingLinkOrVenue().isBlank() ? event.getMeetingLinkOrVenue() : "https://meet.google.com";
                try {
                    if (link.startsWith("http://") || link.startsWith("https://")) {
                        java.awt.Desktop.getDesktop().browse(new java.net.URI(link));
                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Meeting Location / Room: " + link);
                        alert.showAndWait();
                    }
                } catch (Exception ex) {
                    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Live Session Link: " + link);
                    alert.showAndWait();
                }
            });

            card.getChildren().addAll(badgesPane, titleText, subText, joinBtn);
            container.getChildren().add(card);
        }
    }

    private void renderPendingAssessmentsSection(VBox container, List<TrainerAssessment> assessments) {
        container.getChildren().clear();
        if (assessments == null || assessments.isEmpty()) {
            Label empty = new Label("No pending assessments. You are all caught up!");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (TrainerAssessment ta : assessments) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(14));
            card.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

            FlowPane badgesPane = new FlowPane();
            badgesPane.setHgap(8);
            badgesPane.setVgap(8);
            badgesPane.setAlignment(Pos.CENTER_LEFT);

            Label marksBadge = new Label("💯 Marks: " + ta.getTotalMarks());
            marksBadge.setMinWidth(Region.USE_PREF_SIZE);
            marksBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 11px;");

            Label timeBadge = new Label("⏱️ " + (ta.getDurationMinutes() != null ? ta.getDurationMinutes() : "60") + " Mins");
            timeBadge.setMinWidth(Region.USE_PREF_SIZE);
            timeBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 11px;");

            Label deadlineBadge = new Label("⏳ Due: " + (ta.getDeadlineDate() != null ? ta.getDeadlineDate() : "Open"));
            deadlineBadge.setMinWidth(Region.USE_PREF_SIZE);
            deadlineBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 11px;");

            badgesPane.getChildren().addAll(marksBadge, timeBadge, deadlineBadge);

            Text titleText = new Text(ta.getTitle() != null ? ta.getTitle() : "Skill Test");
            titleText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            titleText.setFill(Color.web("#0F172A"));

            Text subText = new Text("Course: " + (ta.getCourseName() != null ? ta.getCourseName() : "Technical Training") + " • Target: " + (ta.getTargetDepartment() != null ? ta.getTargetDepartment() : "ALL"));
            subText.setFont(Font.font("Arial", 12));
            subText.setFill(Color.web("#64748B"));

            Button startBtn = new Button("Start Assessment →");
            startBtn.setMaxWidth(Double.MAX_VALUE);
            startBtn.setStyle("-fx-background-color: #16A34A; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 8px; -fx-cursor: hand;");
            startBtn.setOnAction(e -> {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Starting Test: " + ta.getTitle() + "\nCourse: " + ta.getCourseName() + "\nDuration: " + ta.getDurationMinutes() + " mins");
                alert.showAndWait();
            });

            card.getChildren().addAll(badgesPane, titleText, subText, startBtn);
            container.getChildren().add(card);
        }
    }

    private void showEmployeeLearningSuccessToast(String message) {
        if (employeeLearningToastBox == null) {
            return;
        }
        employeeLearningToastBox.getChildren().clear();
        HBox toast = new HBox(10);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(10, 16, 10, 16));
        toast.setStyle("-fx-background-color: #DCFCE7; -fx-border-color: #86EFAC; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 14px;");
        Text msg = new Text(message);
        msg.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        msg.setFill(Color.web("#15803D"));
        toast.getChildren().addAll(icon, msg);
    }

    private void renderMyEnrolledTrainings(VBox container, List<TrainingEnrollment> myEnrollments) {
        container.getChildren().clear();
        if (myEnrollments == null || myEnrollments.isEmpty()) {
            Label empty = new Label("You have not enrolled in any training programs yet. Explore open programs below!");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        for (TrainingEnrollment te : myEnrollments) {
            VBox card = new VBox(12);
            card.setPadding(new Insets(16));
            card.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

            HBox header = new HBox(16);
            header.setAlignment(Pos.CENTER_LEFT);
            VBox inf = new VBox(2);
            Text jt = new Text(te.getProgramTitle());
            jt.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            jt.setFill(Color.web("#0F172A"));
            Text sub = new Text("Status: " + te.getCompletionStatus() + (te.getCompletionDate() != null && !te.getCompletionDate().isBlank() ? (" • Date: " + te.getCompletionDate()) : ""));
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(jt, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label badge = new Label(te.getCompletionStatus());
            if ("COMPLETED".equalsIgnoreCase(te.getCompletionStatus())) {
                badge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4px 12px; -fx-background-radius: 12px;");
            } else if ("IN_PROGRESS".equalsIgnoreCase(te.getCompletionStatus())) {
                badge.setStyle("-fx-background-color: #DBEAFE; -fx-text-fill: #1E40AF; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4px 12px; -fx-background-radius: 12px;");
            } else {
                badge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #92400E; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4px 12px; -fx-background-radius: 12px;");
            }
            header.getChildren().addAll(inf, sp, badge);

            HBox metricsBox = new HBox(20);
            metricsBox.setAlignment(Pos.CENTER_LEFT);
            double attPct = te.getAttendancePercentage();
            double scorePct = te.getAssessmentScore();

            VBox m1 = new VBox(4);
            Text l1 = new Text("Attendance: " + (int) attPct + "%");
            l1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            l1.setFill(Color.web("#1E40AF"));
            ProgressBar pb1 = new ProgressBar(attPct / 100.0);
            pb1.setPrefWidth(140);
            pb1.setStyle("-fx-accent: #1E60FF;");
            m1.getChildren().addAll(l1, pb1);

            VBox m2 = new VBox(4);
            Text l2 = new Text("Assessment Score: " + (int) scorePct + "%");
            l2.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            l2.setFill(Color.web("#D97706"));
            ProgressBar pb2 = new ProgressBar(scorePct / 100.0);
            pb2.setPrefWidth(140);
            pb2.setStyle("-fx-accent: #F59E0B;");
            m2.getChildren().addAll(l2, pb2);

            metricsBox.getChildren().addAll(m1, m2);
            card.getChildren().addAll(header, metricsBox);
            container.getChildren().add(card);
        }
    }

    private void renderExploreTrainingPrograms(VBox container, List<TrainingProgram> allPrograms, List<TrainingEnrollment> myEnrollments, String userEmail, VBox myTrainingsList) {
        container.getChildren().clear();
        if (allPrograms == null || allPrograms.isEmpty()) {
            Label empty = new Label("No company training programs available at this moment.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        java.util.Set<String> enrolledProgIds = new java.util.HashSet<>();
        if (myEnrollments != null) {
            for (TrainingEnrollment te : myEnrollments) {
                if (te.getProgramId() != null) {
                    enrolledProgIds.add(te.getProgramId());
                }
            }
        }

        for (TrainingProgram tp : allPrograms) {
            HBox r = new HBox(16);
            r.setAlignment(Pos.CENTER_LEFT);
            r.setPadding(new Insets(14, 16, 14, 16));
            r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            VBox inf = new VBox(2);
            Text n = new Text(tp.getTitle());
            n.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            n.setFill(Color.web("#0F172A"));
            Text sub = new Text("Trainer: " + tp.getAssignedTrainerName() + " • Dept: " + tp.getDepartment() + " • Duration: " + tp.getDurationWeeks());
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(n, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Button enrollBtn = new Button();
            if (enrolledProgIds.contains(tp.getProgramId())) {
                enrollBtn.setText("✓ Enrolled");
                enrollBtn.setDisable(true);
                enrollBtn.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 8px;");
            } else {
                enrollBtn.setText("Enroll Now →");
                enrollBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 8px; -fx-cursor: hand;");
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
                            loadEmployeeLearningView();
                            showEmployeeLearningSuccessToast("Enrolled in '" + tp.getTitle() + "' successfully! 🎓");
                        });
                    });
                });
            }

            r.getChildren().addAll(inf, sp, enrollBtn);
            container.getChildren().add(r);
        }
    }

    private void loadPerformanceView() {
        centerContainer.getChildren().clear();
        VBox rootBox = new VBox(20);

        VBox titleBox = new VBox(2);
        Text t = new Text("📈 HR Performance Management & Dynamic L&D Metrics");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        t.setFill(Color.web("#0F172A"));
        Text s = new Text("Automated real-time calculation of employee attendance, assessment test scores, completion rates, and overall L&D index.");
        s.setFont(Font.font("Arial", 13));
        s.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(t, s);

        GridPane metricsGrid = new GridPane();
        metricsGrid.setHgap(16);
        metricsGrid.setVgap(16);
        metricsGrid.add(createStatMiniCard("Average Attendance", "...", "Calculating...", "#1E60FF"), 0, 0);
        metricsGrid.add(createStatMiniCard("Average Assessment Score", "...", "Calculating...", "#8B5CF6"), 1, 0);
        metricsGrid.add(createStatMiniCard("Training Completion Rate", "...", "Calculating...", "#10B981"), 0, 1);
        metricsGrid.add(createStatMiniCard("Overall L&D Performance Index", "...", "Calculating...", "#F59E0B"), 1, 1);

        VBox perfCard = new VBox(16);
        perfCard.setPadding(new Insets(20));
        perfCard.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        Text tableTitle = new Text("Employee-wise L&D Performance & Evaluation Scorecard");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        tableTitle.setFill(Color.web("#0F172A"));

        VBox rowList = new VBox(12);
        rowList.getChildren().add(new Label("🔄 Computing real-time performance metrics"));
        perfCard.getChildren().addAll(tableTitle, rowList);

        rootBox.getChildren().addAll(titleBox, metricsGrid, perfCard);
        centerContainer.getChildren().add(rootBox);

        FirebaseDAO.getInstance().getAllTrainingMetricsForHR().thenAccept(enrollments -> {
            Platform.runLater(() -> {
                renderHRPerformanceMetrics(metricsGrid, rowList, enrollments);
            });
        });
    }

    private void renderHRPerformanceMetrics(GridPane metricsGrid, VBox container, List<TrainingEnrollment> enrollments) {
        metricsGrid.getChildren().clear();
        container.getChildren().clear();

        if (enrollments == null || enrollments.isEmpty()) {
            metricsGrid.add(createStatMiniCard("Average Attendance", "90.0%", "Workforce Avg", "#1E60FF"), 0, 0);
            metricsGrid.add(createStatMiniCard("Average Assessment Score", "85.0%", "Test Avg", "#8B5CF6"), 1, 0);
            metricsGrid.add(createStatMiniCard("Training Completion Rate", "100%", "Completed Tracks", "#10B981"), 0, 1);
            metricsGrid.add(createStatMiniCard("Overall L&D Performance Index", "88.0 / 100", "Company Index", "#F59E0B"), 1, 1);

            Label empty = new Label("No employee training records found");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
            container.getChildren().add(empty);
            return;
        }

        double totalAttendance = 0;
        double totalAssessment = 0;
        int completedCount = 0;
        int totalEnrolled = enrollments.size();

        for (TrainingEnrollment te : enrollments) {
            totalAttendance += te.getAttendancePercentage();
            totalAssessment += te.getAssessmentScore();
            if ("COMPLETED".equalsIgnoreCase(te.getCompletionStatus())) {
                completedCount++;
            }
        }

        double avgAttendance = totalEnrolled > 0 ? (totalAttendance / totalEnrolled) : 0.0;
        double avgAssessment = totalEnrolled > 0 ? (totalAssessment / totalEnrolled) : 0.0;
        double completionRatePct = totalEnrolled > 0 ? ((double) completedCount / totalEnrolled) * 100.0 : 0.0;
        double overallPerformanceIndex = (0.4 * avgAttendance) + (0.4 * avgAssessment) + (0.2 * completionRatePct);

        metricsGrid.add(createStatMiniCard("Average Attendance", String.format("%.1f%%", avgAttendance), "All Enrolled Employees", "#1E60FF"), 0, 0);
        metricsGrid.add(createStatMiniCard("Average Assessment Score", String.format("%.1f%%", avgAssessment), "All Graded Assessments", "#8B5CF6"), 1, 0);
        metricsGrid.add(createStatMiniCard("Training Completion Rate", String.format("%.1f%%", completionRatePct), completedCount + " of " + totalEnrolled + " Completed", "#10B981"), 0, 1);
        metricsGrid.add(createStatMiniCard("Overall L&D Performance Index", String.format("%.1f / 100", overallPerformanceIndex), "Weighted Score Index", "#F59E0B"), 1, 1);

        for (TrainingEnrollment te : enrollments) {
            HBox r = new HBox(16);
            r.setAlignment(Pos.CENTER_LEFT);
            r.setPadding(new Insets(12, 16, 12, 16));
            r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            VBox inf = new VBox(2);
            Text n = new Text(te.getEmployeeName() + " (" + te.getEmployeeDepartment() + ")");
            n.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            n.setFill(Color.web("#0F172A"));
            Text sub = new Text("Course: " + te.getProgramTitle() + " • Status: " + te.getCompletionStatus());
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(n, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label attTag = new Label("Att: " + (int) te.getAttendancePercentage() + "%");
            attTag.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E40AF; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4px 10px; -fx-background-radius: 10px;");

            Label scoreTag = new Label("Score: " + (int) te.getAssessmentScore() + "%");
            scoreTag.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4px 10px; -fx-background-radius: 10px;");

            double overallEmpScore = (0.5 * te.getAttendancePercentage()) + (0.5 * te.getAssessmentScore());
            Label ratingTag = new Label();
            ratingTag.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            ratingTag.setPadding(new Insets(4, 10, 4, 10));

            if (overallEmpScore >= 85) {
                ratingTag.setText("⭐ Exceeds Expectations (" + (int) overallEmpScore + ")");
                ratingTag.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-background-radius: 10px;");
            } else if (overallEmpScore >= 70) {
                ratingTag.setText("Meets Expectations (" + (int) overallEmpScore + ")");
                ratingTag.setStyle("-fx-background-color: #DBEAFE; -fx-text-fill: #1E40AF; -fx-background-radius: 10px;");
            } else {
                ratingTag.setText("Needs Improvement (" + (int) overallEmpScore + ")");
                ratingTag.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #B91C1C; -fx-background-radius: 10px;");
            }

            r.getChildren().addAll(inf, sp, attTag, scoreTag, ratingTag);
            container.getChildren().add(r);
        }
    }

    private void loadReportsView() {
        centerContainer.getChildren().clear();
        VBox rootBox = new VBox(20);
        VBox titleBox = new VBox(2);
        Text t = new Text("Reports & Analytics");
        t.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        t.setFill(Color.web("#0F172A"));
        Text s = new Text("Dynamic headcount distribution, recruitment stats, and training completion rate");
        s.setFont(Font.font("Arial", 13));
        s.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(t, s);

        GridPane metricsGrid = new GridPane();
        metricsGrid.setHgap(16);
        metricsGrid.setVgap(16);
        metricsGrid.add(createStatMiniCard("Total Headcount (users)", "...", "Loading...", "#1E60FF"), 0, 0);
        metricsGrid.add(createStatMiniCard("Open Job Openings", "...", "Loading...", "#8B5CF6"), 1, 0);
        metricsGrid.add(createStatMiniCard("Training Completion", "...", "Loading...", "#10B981"), 0, 1);
        metricsGrid.add(createStatMiniCard("Total Applicants", "...", "Loading...", "#F59E0B"), 1, 1);

        rootBox.getChildren().addAll(titleBox, metricsGrid);
        centerContainer.getChildren().add(rootBox);

        FirebaseDAO.getInstance().getHRReportStatsAsync().thenAccept(stats -> {
            Platform.runLater(() -> {
                metricsGrid.getChildren().clear();
                int headcount = (int) stats.getOrDefault("totalHeadcount", 0);
                int openJobs = (int) stats.getOrDefault("openJobsCount", 0);
                int trainRate = (int) stats.getOrDefault("trainingCompletionRate", 89);
                int applicants = (int) stats.getOrDefault("totalApplicants", 0);

                metricsGrid.add(createStatMiniCard("Total Headcount (users)", String.valueOf(headcount), "Active Workforce", "#1E60FF"), 0, 0);
                metricsGrid.add(createStatMiniCard("Open Job Openings", String.valueOf(openJobs), "Open Positions", "#8B5CF6"), 1, 0);
                metricsGrid.add(createStatMiniCard("Training Completion", trainRate + "%", "L&D Rate", "#10B981"), 0, 1);
                metricsGrid.add(createStatMiniCard("Total Applicants", String.valueOf(applicants), "Candidate Applications", "#F59E0B"), 1, 1);
            });
        });
    }

    private VBox createStatMiniCard(String title, String mainValue, String sub, String accentHex) {
        VBox card = new VBox(8);
        card.setPrefWidth(260);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Text tLabel = new Text(title);
        tLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        tLabel.setFill(Color.web("#64748B"));
        Text val = new Text(mainValue);
        val.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        val.setFill(Color.web(accentHex));
        Text subL = new Text(sub);
        subL.setFont(Font.font("Arial", 11));
        subL.setFill(Color.web("#94A3B8"));

        card.getChildren().addAll(tLabel, val, subL);
        return card;
    }

    private VBox createQuickCard(String iconSymbol, String title, String subtitle, String accentHex, Runnable onClick) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: #FFFFFF;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-radius: 16px;"
                + "-fx-background-radius: 16px;"
                + "-fx-cursor: hand;"
                + "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.04), 10, 0.1, 0, 3);"
        );

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);
        Label icon = new Label(iconSymbol);
        icon.setPrefSize(42, 42);
        icon.setAlignment(Pos.CENTER);
        icon.setStyle("-fx-background-color: " + accentHex + "1E; -fx-text-fill: " + accentHex + "; -fx-font-size: 20px; -fx-background-radius: 10px;");

        VBox textStack = new VBox(2);
        Text t = new Text(title);
        t.setFill(Color.web("#0F172A"));
        t.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text s = new Text(subtitle);
        s.setFill(Color.web("#64748B"));
        s.setFont(Font.font("Arial", 12));
        textStack.getChildren().addAll(t, s);

        topRow.getChildren().addAll(icon, textStack);
        card.getChildren().add(topRow);
        card.setOnMouseClicked(e -> onClick.run());
        return card;
    }

    private void loadSimpleContent(String moduleName, String description) {
        centerContainer.getChildren().clear();
        VBox box = new VBox(16);
        box.setPadding(new Insets(24));
        box.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        Text title = new Text(roleName + " Workspace — " + moduleName);
        title.setFill(Color.web("#0F172A"));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        Text desc = new Text(description);
        desc.setFill(Color.web("#64748B"));
        desc.setFont(Font.font("Arial", 14));

        box.getChildren().addAll(title, desc);
        centerContainer.getChildren().add(box);
    }

    private HBox createCourseRow(String courseName, String dept, int enrolled, double progress) {
        HBox r = new HBox(16);
        r.setAlignment(Pos.CENTER_LEFT);
        r.setPadding(new Insets(12, 16, 12, 16));
        r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        VBox inf = new VBox(2);
        Text n = new Text(courseName);
        n.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Text sub = new Text("Dept: " + dept + " • " + enrolled + " Enrolled");
        sub.setFont(Font.font("Arial", 11));
        sub.setFill(Color.web("#64748B"));
        inf.getChildren().addAll(n, sub);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        ProgressBar pb = new ProgressBar(progress);
        pb.setPrefWidth(120);
        pb.setStyle("-fx-accent: #10B981;");
        Text pct = new Text((int) (progress * 100) + "% Completed");
        pct.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        pct.setFill(Color.web("#0F172A"));

        r.getChildren().addAll(inf, sp, pb, pct);
        return r;
    }
}
