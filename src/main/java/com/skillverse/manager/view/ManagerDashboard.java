
package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;

import com.skillverse.manager.controller.ManagerDashboardController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ManagerDashboard {

    public static Stage managerStage;

    private Scene managerDashboardScene;

    private List<Button> menuButtons = new ArrayList<>();

    private ManagerDashboardController controller;

    public ManagerDashboard() {
        controller = new ManagerDashboardController();
    }

    public Stage getStage() {
        return managerStage;
    }

    public Scene getManagerDashboardScene(Runnable callBackActionLogin) {

        managerStage = Login.loginStage;

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #F7FAFF;");

        VBox sidebar = new VBox(5);
        sidebar.setPrefWidth(245);
        sidebar.setPadding(new Insets(22,15,20,15));
        sidebar.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-width: 0 1 0 0;");

        HBox logoBox = new HBox(10);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        StackPane logo = new StackPane();
        logo.setPrefSize(46,38);

        Text logoS = new Text("S");
        logoS.setFill(Color.web("#2F80ED"));
        logoS.setFont(Font.font("Arial",FontWeight.BOLD,30));
        logoS.setTranslateX(-5);

        Text logoV = new Text("V");
        logoV.setFill(Color.web("#8B5CF6"));
        logoV.setFont(Font.font("Arial",FontWeight.BOLD,30));
        logoV.setTranslateX(6);

        logo.getChildren().addAll(logoS,logoV);

        VBox logoTextBox = new VBox(0);

        Text skillVerseText = new Text("SkillVerse");
        skillVerseText.setFill(Color.web("#0F172A"));
        skillVerseText.setFont(Font.font("Arial",FontWeight.BOLD,20));

        Text managerPlatformText = new Text("Manager Platform");
        managerPlatformText.setFill(Color.web("#64748B"));
        managerPlatformText.setFont(Font.font("Arial",9));

        logoTextBox.getChildren().addAll(skillVerseText,managerPlatformText);
        logoBox.getChildren().addAll(logo,logoTextBox);

        Text workspaceText = new Text("WORKSPACE");
        workspaceText.setFill(Color.web("#64748B"));
        workspaceText.setFont(Font.font("Arial",FontWeight.BOLD,9));
        workspaceText.setStyle("-fx-letter-spacing: 1px;");

        VBox.setMargin(workspaceText,new Insets(25,0,5,10));

        Button dashboardButton = createMenuButton("⌂","Dashboard");
        Button feedButton = createMenuButton("●","Feed");
        Button myTeamButton = createMenuButton("♙","My Team");
        Button performanceButton = createMenuButton("↗","Performance");
        Button goalsButton = createMenuButton("✓","Goals & KPIs");
        Button feedbackButton = createMenuButton("✉","Feedback");
        Button interviewsButton = createMenuButton("▤","Interviews");
        Button analyticsButton = createMenuButton("▥","Analytics");
        Button learningApprovalButton = createMenuButton("◆","Learning Approval");
        Button anonymousButton = createMenuButton("◉","Anonymous Feedback");

        menuButtons.add(dashboardButton);
        menuButtons.add(feedButton);
        menuButtons.add(myTeamButton);
        menuButtons.add(performanceButton);
        menuButtons.add(goalsButton);
        menuButtons.add(feedbackButton);
        menuButtons.add(interviewsButton);
        menuButtons.add(analyticsButton);
        menuButtons.add(learningApprovalButton);
        menuButtons.add(anonymousButton);

        dashboardButton.setOnAction(event -> {
            setActiveButton(dashboardButton);
            controller.openDashboard(this);
        });

        feedButton.setOnAction(event -> {
            setActiveButton(feedButton);
            controller.openFeed(this);
        });

        myTeamButton.setOnAction(event -> {
            setActiveButton(myTeamButton);
            controller.openMyTeam(this);
        });

        performanceButton.setOnAction(event -> {
            setActiveButton(performanceButton);
            controller.openPerformance(this);
        });

        goalsButton.setOnAction(event -> {
            setActiveButton(goalsButton);
            controller.openGoals(this);
        });

        feedbackButton.setOnAction(event -> {
            setActiveButton(feedbackButton);
            controller.openFeedback(this);
        });

        interviewsButton.setOnAction(event -> {
            setActiveButton(interviewsButton);
            controller.openInterviews(this);
        });

        analyticsButton.setOnAction(event -> {
            setActiveButton(analyticsButton);
            controller.openAnalytics(this);
        });

        learningApprovalButton.setOnAction(event -> {
            setActiveButton(learningApprovalButton);
            controller.openLearningApproval(this);
        });

        anonymousButton.setOnAction(event -> {
            setActiveButton(anonymousButton);
            controller.openAnonymousFeedback(this);
        });

        Text analyticsLabel = new Text("ANALYTICS & REPORTS");
        analyticsLabel.setFill(Color.web("#64748B"));
        analyticsLabel.setFont(Font.font("Arial",FontWeight.BOLD,9));
        analyticsLabel.setStyle("-fx-letter-spacing: 1px;");

        VBox.setMargin(analyticsLabel,new Insets(18,0,5,10));

        Region sidebarSpacer = new Region();
        VBox.setVgrow(sidebarSpacer,Priority.ALWAYS);

        Button settingsButton = createMenuButton("⚙","Settings");
        Button logoutButton = createMenuButton("→","Logout");

        Text versionText = new Text("SkillVerse v1.0");
        versionText.setFill(Color.web("#94A3B8"));
        versionText.setFont(Font.font("Arial",9));

        settingsButton.setOnAction(event -> {
            controller.openSettings(this);
        });

        logoutButton.setOnAction(event -> {
            controller.logout(this,callBackActionLogin);
        });

        sidebar.getChildren().addAll(
                logoBox,
                workspaceText,
                dashboardButton,
                feedButton,
                myTeamButton,
                performanceButton,
                goalsButton,
                feedbackButton,
                interviewsButton,
                analyticsLabel,
                analyticsButton,
                learningApprovalButton,
                anonymousButton,
                sidebarSpacer,
                settingsButton,
                logoutButton,
                versionText
        );

        HBox topBar = new HBox(16);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(14,25,14,28));
        topBar.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-width: 0 0 1 0;");

        VBox pageHeadingBox = new VBox(2);

        Text workspaceHeading = new Text("MANAGER WORKSPACE");
        workspaceHeading.setFill(Color.web("#64748B"));
        workspaceHeading.setFont(Font.font("Arial",FontWeight.BOLD,9));
        workspaceHeading.setStyle("-fx-letter-spacing: 1px;");

        Text pageTitle = new Text("Dashboard");
        pageTitle.setFill(Color.web("#0F172A"));
        pageTitle.setFont(Font.font("Arial",FontWeight.BOLD,23));

        pageHeadingBox.getChildren().addAll(workspaceHeading,pageTitle);

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer,Priority.ALWAYS);

        Button notificationButton = new Button("♧");
        notificationButton.setPrefSize(42,38);
        notificationButton.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 20px;-fx-background-radius: 20px;-fx-text-fill: #334155;-fx-font-size: 16px;-fx-cursor: hand;");

        StackPane notificationPane = new StackPane(notificationButton);

        Circle notificationDot = new Circle(4,Color.web("#EF4444"));
        notificationDot.setTranslateX(13);
        notificationDot.setTranslateY(-12);

        notificationPane.getChildren().add(notificationDot);

        HBox profileBox = new HBox(10);
        profileBox.setAlignment(Pos.CENTER_LEFT);

        Circle profileCircle = new Circle(20);
        profileCircle.setFill(Color.web("#DBEAFE"));

        Text profileInitial = new Text("M");
        profileInitial.setFill(Color.web("#2563EB"));
        profileInitial.setFont(Font.font("Arial",FontWeight.BOLD,14));

        StackPane profileAvatar = new StackPane();
        profileAvatar.getChildren().addAll(profileCircle,profileInitial);

        VBox profileInfo = new VBox(1);

        Text managerName = new Text("Manager");
        managerName.setFill(Color.web("#0F172A"));
        managerName.setFont(Font.font("Arial",FontWeight.BOLD,12));

        Text managerEmail = new Text("manager@skillverse.com");
        managerEmail.setFill(Color.web("#64748B"));
        managerEmail.setFont(Font.font("Arial",9));

        profileInfo.getChildren().addAll(managerName,managerEmail);

        Text profileArrow = new Text("⌄");
        profileArrow.setFill(Color.web("#334155"));
        profileArrow.setFont(Font.font("Arial",FontWeight.BOLD,13));

        profileBox.getChildren().addAll(profileAvatar,profileInfo,profileArrow);
        profileBox.setStyle("-fx-cursor: hand;");

        profileBox.setOnMouseClicked(event -> {
            controller.openManagerProfile(this);
        });

        topBar.getChildren().addAll(pageHeadingBox,topSpacer,notificationPane,profileBox);

        VBox dashboardContent = new VBox(14);
        dashboardContent.setPadding(new Insets(18,25,25,25));
        dashboardContent.setStyle("-fx-background-color: #F7FAFF;");

        HBox welcomeBanner = new HBox();
        welcomeBanner.setAlignment(Pos.CENTER_LEFT);
        welcomeBanner.setPrefHeight(100);
        welcomeBanner.setPadding(new Insets(18,24,18,25));
        welcomeBanner.setStyle("-fx-background-color: linear-gradient(to right, #FFFFFF, #F1F7FF);-fx-background-radius: 12px;-fx-border-color: #E2E8F0;-fx-border-radius: 12px;");

        VBox welcomeTextBox = new VBox(5);

        Text welcomeText = new Text("Welcome back, Manager 👋");
        welcomeText.setFill(Color.web("#0F172A"));
        welcomeText.setFont(Font.font("Arial",FontWeight.BOLD,22));

        Text subtitle = new Text("Here's what's happening with your team and projects today.");
        subtitle.setFill(Color.web("#64748B"));
        subtitle.setFont(Font.font("Arial",11));

        welcomeTextBox.getChildren().addAll(welcomeText,subtitle);

        Region welcomeSpacer = new Region();
        HBox.setHgrow(welcomeSpacer,Priority.ALWAYS);

        StackPane welcomeGraphic = createWelcomeGraphic();

        welcomeBanner.getChildren().addAll(welcomeTextBox,welcomeSpacer,welcomeGraphic);

        HBox statsRow = new HBox(14);

        statsRow.getChildren().addAll(
                createStatCard("Team Members","14","↑ 2 this month","#3B82F6","#E8F1FF"),
                createStatCard("Avg Performance","88%","↑ 6% from last month","#16A34A","#E8F8EF"),
                createStatCard("Goals Completed","79%","12 goals completed","#8B5CF6","#F0E9FF"),
                createStatCard("Learning Progress","76%","8 courses active","#14B8A6","#E2F8F5")
        );

        HBox chartsRow = new HBox(14);

        VBox performanceCard = createPerformanceChart();
        VBox projectStatusCard = createProjectStatus();

        HBox.setHgrow(performanceCard,Priority.ALWAYS);
        HBox.setHgrow(projectStatusCard,Priority.ALWAYS);

        chartsRow.getChildren().addAll(performanceCard,projectStatusCard);

        HBox lowerRow = new HBox(14);

        VBox teamCard = createTeamOverview();
        VBox activityCard = createRecentActivity();
        VBox focusCard = createManagerFocus();

        HBox.setHgrow(teamCard,Priority.ALWAYS);
        HBox.setHgrow(activityCard,Priority.ALWAYS);
        HBox.setHgrow(focusCard,Priority.ALWAYS);

        lowerRow.getChildren().addAll(teamCard,activityCard,focusCard);

        dashboardContent.getChildren().addAll(welcomeBanner,statsRow,chartsRow,lowerRow);

        ScrollPane scrollPane = new ScrollPane(dashboardContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F7FAFF;-fx-background: #F7FAFF;");

        mainLayout.setLeft(sidebar);
        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane);

        managerDashboardScene = new Scene(mainLayout,1200,700);

        managerStage.setScene(managerDashboardScene);
        managerStage.setTitle("SkillVerse - Manager Dashboard");
        managerStage.setMinWidth(1200);
        managerStage.setMinHeight(700);

        setActiveButton(dashboardButton);

        return managerDashboardScene;
    }

    private Button createMenuButton(String icon,String text) {

        Button button = new Button(icon + "    " + text);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(36);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(8,12,8,12));

        button.setStyle("-fx-background-color: transparent;-fx-text-fill: #475569;-fx-background-radius: 9px;-fx-font-size: 12px;-fx-cursor: hand;");

        return button;
    }

    private void setActiveButton(Button activeButton) {

        for (Button button : menuButtons) {

            button.setStyle("-fx-background-color: transparent;-fx-text-fill: #475569;-fx-background-radius: 9px;-fx-font-size: 12px;-fx-cursor: hand;");
        }

        activeButton.setStyle("-fx-background-color: #EAF2FF;-fx-text-fill: #2563EB;-fx-background-radius: 9px;-fx-font-size: 12px;-fx-cursor: hand;-fx-font-weight: bold;");
    }

    public void backToManagerDashboard() {

        managerStage.setScene(managerDashboardScene);
    }

    private VBox createStatCard(String title,String value,String description,String accentColor,String iconBackground) {

        HBox card = new HBox(12);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(14));
        card.setPrefHeight(86);
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 12px;-fx-background-radius: 12px;");

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(48,48);
        iconBox.setMaxSize(48,48);
        iconBox.setStyle("-fx-background-color: " + iconBackground + ";-fx-background-radius: 50px;");

        Text icon = new Text(title.equals("Team Members") ? "♟" : title.equals("Avg Performance") ? "↗" : title.equals("Goals Completed") ? "✓" : "◆");
        icon.setFill(Color.web(accentColor));
        icon.setFont(Font.font("Arial",FontWeight.BOLD,19));

        iconBox.getChildren().add(icon);

        VBox info = new VBox(2);

        Text titleText = new Text(title);
        titleText.setFill(Color.web("#475569"));
        titleText.setFont(Font.font("Arial",10));

        Text valueText = new Text(value);
        valueText.setFill(Color.web("#0F172A"));
        valueText.setFont(Font.font("Arial",FontWeight.BOLD,22));

        Text descriptionText = new Text(description);
        descriptionText.setFill(Color.web("#16A34A"));
        descriptionText.setFont(Font.font("Arial",8));

        info.getChildren().addAll(titleText,valueText,descriptionText);

        card.getChildren().addAll(iconBox,info);

        VBox wrapper = new VBox(card);

        HBox.setHgrow(card,Priority.ALWAYS);

        return wrapper;
    }

    private StackPane createWelcomeGraphic() {

        StackPane graphic = new StackPane();
        graphic.setPrefSize(270,82);

        Rectangle screen = new Rectangle(170,66);
        screen.setArcWidth(10);
        screen.setArcHeight(10);
        screen.setFill(Color.WHITE);
        screen.setStroke(Color.web("#DCE8F8"));
        screen.setTranslateX(12);

        Line graph1 = new Line(-55,12,-25,-3);
        graph1.setStroke(Color.web("#3B82F6"));
        graph1.setStrokeWidth(2);

        Line graph2 = new Line(-25,-3,5,5);
        graph2.setStroke(Color.web("#3B82F6"));
        graph2.setStrokeWidth(2);

        Line graph3 = new Line(5,5,40,-18);
        graph3.setStroke(Color.web("#3B82F6"));
        graph3.setStrokeWidth(2);

        Circle p1 = new Circle(3,Color.web("#3B82F6"));
        p1.setTranslateX(-55);
        p1.setTranslateY(12);

        Circle p2 = new Circle(3,Color.web("#3B82F6"));
        p2.setTranslateX(-25);
        p2.setTranslateY(-3);

        Circle p3 = new Circle(3,Color.web("#3B82F6"));
        p3.setTranslateX(5);
        p3.setTranslateY(5);

        Circle p4 = new Circle(3,Color.web("#3B82F6"));
        p4.setTranslateX(40);
        p4.setTranslateY(-18);

        graphic.getChildren().addAll(screen,graph1,graph2,graph3,p1,p2,p3,p4);

        return graphic;
    }

    private VBox createPerformanceChart() {

        VBox card = new VBox(8);
        card.setPadding(new Insets(16));
        card.setPrefHeight(205);
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 12px;-fx-background-radius: 12px;");

        HBox headingRow = new HBox();
        headingRow.setAlignment(Pos.CENTER_LEFT);

        Text heading = new Text("Team Performance Overview");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial",FontWeight.BOLD,14));

        Region spacer = new Region();
        HBox.setHgrow(spacer,Priority.ALWAYS);

        Button periodButton = new Button("This Week  ˅");
        periodButton.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 7px;-fx-background-radius: 7px;-fx-font-size: 9px;-fx-text-fill: #334155;-fx-padding: 5px 9px;");

        headingRow.getChildren().addAll(heading,spacer,periodButton);

        StackPane chart = new StackPane();
        chart.setPrefHeight(135);
        chart.setStyle("-fx-background-color: #FBFDFF;-fx-background-radius: 8px;");

        for (int i = 0;i < 4;i++) {

            Line grid = new Line(-270,-45 + i * 30,270,-45 + i * 30);
            grid.setStroke(Color.web("#EDF2F7"));

            chart.getChildren().add(grid);
        }

        double[] x = {-245,-165,-85,-5,75,155,235};
        double[] y = {25,2,-7,-22,0,18,-5};

        for (int i = 0;i < x.length - 1;i++) {

            Line line = new Line(x[i],y[i],x[i + 1],y[i + 1]);
            line.setStroke(Color.web("#2F80ED"));
            line.setStrokeWidth(2);

            chart.getChildren().add(line);
        }

        for (int i = 0;i < x.length;i++) {

            Circle point = new Circle(3.5,Color.web("#2F80ED"));
            point.setTranslateX(x[i]);
            point.setTranslateY(y[i]);

            chart.getChildren().add(point);
        }

        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

        HBox daysBox = new HBox();
        daysBox.setAlignment(Pos.CENTER);

        for (String day : days) {

            Text dayText = new Text(day);
            dayText.setFill(Color.web("#64748B"));
            dayText.setFont(Font.font("Arial",8));

            HBox.setHgrow(dayText,Priority.ALWAYS);

            daysBox.getChildren().add(dayText);
        }

        card.getChildren().addAll(headingRow,chart,daysBox);

        return card;
    }

    private VBox createProjectStatus() {

        VBox card = new VBox(10);

        card.setPadding(new Insets(16));
        card.setPrefWidth(350);
        card.setPrefHeight(205);
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 12px;-fx-background-radius: 12px;");

        Text heading = new Text("Project Status");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial",FontWeight.BOLD,14));

        HBox body = new HBox(12);
        body.setAlignment(Pos.CENTER);

        PieChart pieChart = new PieChart();

        pieChart.getData().addAll(
                new PieChart.Data("Completed",40),
                new PieChart.Data("In Progress",40),
                new PieChart.Data("On Hold",10),
                new PieChart.Data("Not Started",10)
        );

        pieChart.setLegendVisible(false);
        pieChart.setLabelsVisible(false);
        pieChart.setStartAngle(90);
        pieChart.setPrefSize(125,125);

        StackPane donut = new StackPane(pieChart);

        Circle center = new Circle(35,Color.WHITE);
        donut.getChildren().add(center);

        VBox legend = new VBox(9);

        legend.getChildren().addAll(
                createLegendRow("Completed","40%","#45C98A"),
                createLegendRow("In Progress","40%","#3B82F6"),
                createLegendRow("On Hold","10%","#F59E0B"),
                createLegendRow("Not Started","10%","#A8B2C1")
        );

        body.getChildren().addAll(donut,legend);

        card.getChildren().addAll(heading,body);

        return card;
    }

    private HBox createLegendRow(String name,String value,String color) {

        HBox row = new HBox(7);
        row.setAlignment(Pos.CENTER_LEFT);

        Circle dot = new Circle(4,Color.web(color));

        Text nameText = new Text(name);
        nameText.setFill(Color.web("#475569"));
        nameText.setFont(Font.font("Arial",9));

        Region spacer = new Region();
        HBox.setHgrow(spacer,Priority.ALWAYS);

        Text valueText = new Text(value);
        valueText.setFill(Color.web("#0F172A"));
        valueText.setFont(Font.font("Arial",FontWeight.BOLD,9));

        row.getChildren().addAll(dot,nameText,spacer,valueText);

        return row;
    }

    private VBox createTeamOverview() {

        VBox card = new VBox(11);

        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 12px;-fx-background-radius: 12px;");

        Text heading = new Text("Team Overview");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial",FontWeight.BOLD,14));

        card.getChildren().addAll(
                heading,
                createTeamRow("Priya Sharma","Software Engineer","94%"),
                createTeamRow("Rahul Verma","Data Scientist","87%"),
                createTeamRow("Sneha Joshi","Product Designer","91%"),
                createTeamRow("Amit Kumar","Software Engineer","82%")
        );

        return card;
    }

    private HBox createTeamRow(String name,String role,String performance) {

        HBox row = new HBox(9);
        row.setAlignment(Pos.CENTER_LEFT);

        Circle circle = new Circle(16);
        circle.setFill(Color.web("#E8F1FF"));

        Text initial = new Text(name.substring(0,1));
        initial.setFill(Color.web("#2563EB"));
        initial.setFont(Font.font("Arial",FontWeight.BOLD,11));

        StackPane avatar = new StackPane();
        avatar.getChildren().addAll(circle,initial);

        VBox info = new VBox(2);

        Text nameText = new Text(name);
        nameText.setFill(Color.web("#0F172A"));
        nameText.setFont(Font.font("Arial",FontWeight.BOLD,10));

        Text roleText = new Text(role);
        roleText.setFill(Color.web("#64748B"));
        roleText.setFont(Font.font("Arial",8));

        info.getChildren().addAll(nameText,roleText);

        Region spacer = new Region();
        HBox.setHgrow(spacer,Priority.ALWAYS);

        Text performanceText = new Text(performance);
        performanceText.setFill(Color.web("#16A34A"));
        performanceText.setFont(Font.font("Arial",FontWeight.BOLD,10));

        row.getChildren().addAll(avatar,info,spacer,performanceText);

        return row;
    }

    private VBox createRecentActivity() {

        VBox card = new VBox(11);

        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 12px;-fx-background-radius: 12px;");

        Text heading = new Text("Recent Activity");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial",FontWeight.BOLD,14));

        card.getChildren().addAll(
                heading,
                createActivity("Priya completed a goal","2 hours ago"),
                createActivity("Rahul completed a course","5 hours ago"),
                createActivity("New interview scheduled","Yesterday"),
                createActivity("New feedback received","Yesterday")
        );

        return card;
    }

    private HBox createActivity(String activity,String time) {

        HBox row = new HBox(9);

        Circle dot = new Circle(4,Color.web("#2F80ED"));

        VBox info = new VBox(2);

        Text activityText = new Text(activity);
        activityText.setFill(Color.web("#334155"));
        activityText.setFont(Font.font("Arial",10));

        Text timeText = new Text(time);
        timeText.setFill(Color.web("#94A3B8"));
        timeText.setFont(Font.font("Arial",8));

        info.getChildren().addAll(activityText,timeText);

        row.getChildren().addAll(dot,info);

        return row;
    }

    private VBox createManagerFocus() {

        VBox card = new VBox(11);

        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 12px;-fx-background-radius: 12px;");

        HBox headingRow = new HBox();
        headingRow.setAlignment(Pos.CENTER_LEFT);

        StackPane icon = new StackPane();
        icon.setPrefSize(34,34);
        icon.setMaxSize(34,34);
        icon.setStyle("-fx-background-color: #E4F8EF;-fx-background-radius: 9px;");

        Text iconText = new Text("◎");
        iconText.setFill(Color.web("#16A34A"));
        iconText.setFont(Font.font("Arial",FontWeight.BOLD,17));

        icon.getChildren().add(iconText);

        VBox headingInfo = new VBox(2);

        Text heading = new Text("Manager Focus");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial",FontWeight.BOLD,13));

        Text small = new Text("Today's priority");
        small.setFill(Color.web("#94A3B8"));
        small.setFont(Font.font("Arial",8));

        headingInfo.getChildren().addAll(heading,small);

        headingRow.getChildren().addAll(icon,headingInfo);

        Text focusText = new Text("Improve team collaboration\nand productivity.");
        focusText.setFill(Color.web("#475569"));
        focusText.setFont(Font.font("Arial",10));
        focusText.setLineSpacing(3);

        Button arrowButton = new Button("›");
        arrowButton.setPrefSize(30,30);
        arrowButton.setStyle("-fx-background-color: #F8FAFC;-fx-border-color: #E2E8F0;-fx-border-radius: 50px;-fx-background-radius: 50px;-fx-text-fill: #334155;-fx-font-size: 17px;-fx-cursor: hand;");

        HBox arrowRow = new HBox();
        arrowRow.setAlignment(Pos.CENTER_RIGHT);
        arrowRow.getChildren().add(arrowButton);

        card.getChildren().addAll(headingRow,focusText,arrowRow);

        return card;
    }
}