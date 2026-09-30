package com.skillverse.hr.view;

import com.skillverse.hr.controller.AccountController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Account {

    private Scene accountScene;

    private final String background = "#F6F8FC";
    private final String white = "#FFFFFF";

    private final String primaryBlue = "#2563EB";
    private final String darkBlue = "#1D4ED8";

    private final String navy = "#172554";
    private final String textDark = "#1E293B";
    private final String textLight = "#64748B";
    private final String mutedText = "#94A3B8";

    private final String border = "#E2E8F0";
    private final String lightBlue = "#EFF6FF";
    private final String hoverBlue = "#EAF2FF";

    private final String success = "#16A34A";
    private final String purple = "#7C3AED";
    private final String cyan = "#0891B2";
    private final String red = "#DC2626";

    private String userName;

    public Account(String userName) {

        this.userName = userName;

        String initials = getInitials(userName);

        VBox sidebar = new VBox(7);

        sidebar.setPrefWidth(250);
        sidebar.setMinWidth(250);

        sidebar.setPadding(
                new Insets(26, 15, 20, 15)
        );

        sidebar.setStyle(
                "-fx-background-color: " + white + ";" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-width: 0 1px 0 0;"
        );

        Label logoIcon = new Label("S");

        logoIcon.setAlignment(Pos.CENTER);
        logoIcon.setPrefSize(40, 40);

        logoIcon.setStyle(
                "-fx-background-color: " + primaryBlue + ";" +
                "-fx-background-radius: 10px;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );

        Label logoText = new Label("SkillVerse");

        logoText.setStyle(
                "-fx-text-fill: " + navy + ";" +
                "-fx-font-size: 21px;" +
                "-fx-font-weight: bold;"
        );

        HBox logo = new HBox(
                11,
                logoIcon,
                logoText
        );

        logo.setAlignment(Pos.CENTER_LEFT);

        logo.setPadding(
                new Insets(0, 8, 24, 8)
        );

        Label hrIcon = new Label("HR");

        hrIcon.setAlignment(Pos.CENTER);
        hrIcon.setPrefSize(38, 38);

        hrIcon.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-background-radius: 10px;" +
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );

        Label hrTitle = new Label("HR Manager");

        hrTitle.setStyle(
                "-fx-text-fill: " + textDark + ";" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;"
        );

        Label hrAccess = new Label("HR Portal");

        hrAccess.setStyle(
                "-fx-text-fill: " + mutedText + ";" +
                "-fx-font-size: 10px;"
        );

        VBox hrText = new VBox(
                2,
                hrTitle,
                hrAccess
        );

        HBox hrProfile = new HBox(
                10,
                hrIcon,
                hrText
        );

        hrProfile.setAlignment(Pos.CENTER_LEFT);

        hrProfile.setPadding(
                new Insets(10)
        );

        hrProfile.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-background-radius: 12px;"
        );

        VBox menu = new VBox(5);

        menu.setPadding(
                new Insets(18, 0, 10, 0)
        );

        Label dashboard = createMenuItem(
                "▦",
                "Dashboard",
                true
        );

        Label feed = createMenuItem(
                "◉",
                "Feed",
                false
        );

        Label employees = createMenuItem(
                "♙",
                "Employees",
                false
        );

        Label recruitment = createMenuItem(
                "＋",
                "Recruitment",
                false
        );

        Label payroll = createMenuItem(
                "₹",
                "Payroll & PF",
                false
        );

        Label training = createMenuItem(
                "◇",
                "Training",
                false
        );

        Label performance = createMenuItem(
                "↗",
                "Performance",
                false
        );

        Label leave = createMenuItem(
                "□",
                "Leave Management",
                false
        );

        Label departments = createMenuItem(
                "▤",
                "Departments",
                false
        );

        Label reports = createMenuItem(
                "▧",
                "Reports",
                false
        );

        Label feedback = createMenuItem(
                "✉",
                "Feedback",
                false
        );

        Label speakUp = createMenuItem(
                "📢",
                "Speak Up",
                false
        );

        menu.getChildren().addAll(
                dashboard,
                feed,
                employees,
                recruitment,
                payroll,
                training,
                performance,
                leave,
                departments,
                reports,
                feedback,
                speakUp
        );

        VBox.setVgrow(
                menu,
                Priority.ALWAYS
        );

        Label profileCircle = new Label(initials);

        profileCircle.setAlignment(Pos.CENTER);
        profileCircle.setPrefSize(38, 38);

        profileCircle.setStyle(
                "-fx-background-color: " + primaryBlue + ";" +
                "-fx-background-radius: 50%;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );

        Label profileName = new Label(userName);

        profileName.setStyle(
                "-fx-text-fill: " + textDark + ";" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;"
        );

        Label profileRole = new Label("HR Manager");

        profileRole.setStyle(
                "-fx-text-fill: " + mutedText + ";" +
                "-fx-font-size: 10px;"
        );

        VBox profileDetails = new VBox(
                2,
                profileName,
                profileRole
        );

        HBox profile = new HBox(
                10,
                profileCircle,
                profileDetails
        );

        profile.setAlignment(Pos.CENTER_LEFT);

        profile.setPadding(
                new Insets(12, 5, 0, 5)
        );

        profile.setStyle(
                "-fx-cursor: hand;"
        );

        Button logoutButton = new Button(
                "⇥   Logout"
        );

        logoutButton.setMaxWidth(
                Double.MAX_VALUE
        );

        logoutButton.setPrefHeight(40);

        logoutButton.setAlignment(
                Pos.CENTER_LEFT
        );

        logoutButton.setPadding(
                new Insets(0, 12, 0, 14)
        );

        logoutButton.setStyle(
                getLogoutStyle()
        );

        logoutButton.setOnMouseEntered(e -> {

            logoutButton.setStyle(
                    getLogoutHoverStyle()
            );

        });

        logoutButton.setOnMouseExited(e -> {

            logoutButton.setStyle(
                    getLogoutStyle()
            );

        });

        VBox bottomArea = new VBox(
                12,
                profile,
                logoutButton
        );

        bottomArea.setPadding(
                new Insets(12, 0, 0, 0)
        );

        bottomArea.setStyle(
                "-fx-border-color: " + border + ";" +
                "-fx-border-width: 1px 0 0 0;"
        );

        sidebar.getChildren().addAll(
                logo,
                hrProfile,
                menu,
                bottomArea
        );

        VBox mainArea = new VBox();

        mainArea.setStyle(
                "-fx-background-color: " + background + ";"
        );

        HBox topBar = new HBox(15);

        topBar.setAlignment(Pos.CENTER_LEFT);

        topBar.setPadding(
                new Insets(18, 30, 18, 30)
        );

        topBar.setStyle(
                "-fx-background-color: " + white + ";" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-width: 0 0 1px 0;"
        );

        VBox breadcrumb = new VBox(2);

        Label breadcrumbSmall = new Label("SKILLVERSE  /  HR WORKSPACE");

        breadcrumbSmall.setStyle(
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
        );

        Label breadcrumbTitle = new Label("Dashboard");

        breadcrumbTitle.setStyle(
                "-fx-text-fill: " + textDark + ";" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;"
        );

        breadcrumb.getChildren().addAll(
                breadcrumbSmall,
                breadcrumbTitle
        );


        Label notification = new Label("●");

        notification.setAlignment(Pos.CENTER);

        notification.setPrefSize(34, 34);

        notification.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-background-radius: 50%;" +
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 9px;" +
                "-fx-cursor: hand;"
        );

        Label topProfile = new Label(initials);

        topProfile.setAlignment(Pos.CENTER);

        topProfile.setPrefSize(38, 38);

        topProfile.setStyle(
                "-fx-background-color: " + primaryBlue + ";" +
                "-fx-background-radius: 50%;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );

        HBox.setHgrow(
                breadcrumb,
                Priority.ALWAYS
        );

        topBar.getChildren().addAll(
                breadcrumb,
                notification,
                topProfile
        );

        ScrollPane scrollPane = createDashboardScrollPane();

        VBox.setVgrow(
                scrollPane,
                Priority.ALWAYS
        );

        mainArea.getChildren().addAll(
                topBar,
                scrollPane
        );

        HBox root = new HBox(
                sidebar,
                mainArea
        );

        HBox.setHgrow(
                mainArea,
                Priority.ALWAYS
        );

        accountScene = new Scene(
                root,
                1600,
                800
        );

        AccountController controller =
                new AccountController(
                        userName,
                        initials,
                        accountScene,
                        dashboard,
                        feed,
                        employees,
                        recruitment,
                        payroll,
                        training,
                        performance,
                        leave,
                        departments,
                        reports,
                        feedback,
                        speakUp,
                        profileCircle,
                        topProfile,
                        logoutButton,
                        null,
                        null
                );

        VBox content = (VBox) scrollPane.getContent();

        connectQuickActions(
                controller,
                content
        );
    }

    private ScrollPane createDashboardScrollPane() {

        VBox content = createDashboardContent();

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setContent(content);

        scrollPane.setFitToWidth(true);

        scrollPane.setFitToHeight(false);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setStyle(
                "-fx-background-color: " + background + ";" +
                "-fx-border-color: transparent;"
        );

        return scrollPane;
    }

    private VBox createDashboardContent() {

        VBox content = new VBox(18);

        content.setPadding(
                new Insets(24, 30, 30, 30)
        );

        VBox pageHeader = new VBox(4);

        Label pageTitle = new Label("Dashboard");

        pageTitle.setStyle(
                "-fx-text-fill: " + textDark + ";" +
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        Label pageSubtitle = new Label(
                "Overview of your HR workspace and workplace activities."
        );

        pageSubtitle.setStyle(
                "-fx-text-fill: " + textLight + ";" +
                "-fx-font-size: 12px;"
        );

        pageHeader.getChildren().addAll(
                pageTitle,
                pageSubtitle
        );

        VBox welcome = new VBox(5);

        welcome.setMaxWidth(900);

        welcome.setPrefWidth(900);

        welcome.setPadding(
                new Insets(18, 22, 18, 22)
        );

        welcome.setStyle(
                "-fx-background-color: linear-gradient(to right, " +
                navy + ", " +
                primaryBlue + ");" +
                "-fx-background-radius: 14px;"
        );

        Label welcomeSmall = new Label(
                "HR WORKSPACE"
        );

        welcomeSmall.setStyle(
                "-fx-text-fill: rgba(255,255,255,0.75);" +
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;"
        );

        Label welcomeTitle = new Label(
                "Welcome back, " // + userName
        );

        welcomeTitle.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 21px;" +
                "-fx-font-weight: bold;"
        );

        Label welcomeDescription = new Label(
                "Manage people, workplace activities and organizational growth from one workspace."
        );

        welcomeDescription.setStyle(
                "-fx-text-fill: rgba(255,255,255,0.85);" +
                "-fx-font-size: 11px;"
        );

        welcome.getChildren().addAll(
                welcomeSmall,
                welcomeTitle,
                welcomeDescription
        );

        Label quickTitle = new Label("Quick Access");

        quickTitle.setStyle(
                "-fx-text-fill: " + textDark + ";" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;"
        );

        VBox quickActions = new VBox(12);

        HBox row1 = new HBox(12);

        row1.setMaxWidth(760);

        VBox employeesCard = createQuickCard(
                "♙",
                "Employees",
                "Manage employee information",
                primaryBlue
        );

        VBox recruitmentCard = createQuickCard(
                "＋",
                "Recruitment",
                "Manage hiring activities",
                purple
        );

        row1.getChildren().addAll(
                employeesCard,
                recruitmentCard
        );

        HBox.setHgrow(
                employeesCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                recruitmentCard,
                Priority.ALWAYS
        );

        employeesCard.setId("employeesCard");
        recruitmentCard.setId("recruitmentCard");

        HBox row2 = new HBox(12);

        row2.setMaxWidth(760);

        VBox trainingCard = createQuickCard(
                "◇",
                "Training",
                "Manage learning programs",
                cyan
        );

        VBox feedCard = createQuickCard(
                "◉",
                "Feed",
                "View workplace updates",
                success
        );

        row2.getChildren().addAll(
                trainingCard,
                feedCard
        );

        HBox.setHgrow(
                trainingCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                feedCard,
                Priority.ALWAYS
        );

        trainingCard.setId("trainingCard");
        feedCard.setId("feedCard");

        HBox row3 = new HBox(12);

        row3.setMaxWidth(760);

        VBox anonymousChatCard = createQuickCard(
                "☁",
                "Anonymous Chat",
                "Talk privately without revealing your identity",
                purple
        );

        VBox placeholder = new VBox();

        placeholder.setMinWidth(0);
        placeholder.setPrefWidth(0);
        placeholder.setMaxWidth(Double.MAX_VALUE);

        row3.getChildren().addAll(
                anonymousChatCard,
                placeholder
        );

        HBox.setHgrow(
                anonymousChatCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                placeholder,
                Priority.ALWAYS
        );

        anonymousChatCard.setId("anonymousChatCard");

        quickActions.getChildren().addAll(
                row1,
                row2,
                row3
        );

        VBox information = new VBox(7);

        information.setMaxWidth(900);

        information.setPadding(
                new Insets(16)
        );

        information.setStyle(
                "-fx-background-color: " + white + ";" +
                "-fx-background-radius: 13px;" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-radius: 13px;"
        );

        Label informationTitle = new Label(
                "Workspace Overview"
        );

        informationTitle.setStyle(
                "-fx-text-fill: " + textDark + ";" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;"
        );

        Label informationText = new Label(
                "Use the navigation menu to manage employees, recruitment, training, performance, payroll and workplace communication."
        );

        informationText.setWrapText(true);

        informationText.setStyle(
                "-fx-text-fill: " + textLight + ";" +
                "-fx-font-size: 11px;"
        );

        information.getChildren().addAll(
                informationTitle,
                informationText
        );

        content.getChildren().addAll(
                pageHeader,
                welcome,
                quickTitle,
                quickActions,
                information
        );

        return content;
    }

    private void connectQuickActions(
            AccountController controller,
            VBox content
    ) {

        VBox employeesCard =
                (VBox) content.lookup("#employeesCard");

        VBox recruitmentCard =
                (VBox) content.lookup("#recruitmentCard");

        VBox trainingCard =
                (VBox) content.lookup("#trainingCard");

        VBox feedCard =
                (VBox) content.lookup("#feedCard");

        VBox anonymousChatCard =
                (VBox) content.lookup("#anonymousChatCard");

        controller.connectQuickActions(
                employeesCard,
                recruitmentCard,
                trainingCard,
                feedCard,
                anonymousChatCard
        );
    }

    private VBox createQuickCard(
            String icon,
            String title,
            String description,
            String accent
    ) {

        Label iconLabel = new Label(icon);

        iconLabel.setAlignment(Pos.CENTER);

        iconLabel.setPrefSize(34, 34);

        iconLabel.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-background-radius: 9px;" +
                "-fx-text-fill: " + accent + ";" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;"
        );

        Label titleLabel = new Label(title);

        titleLabel.setStyle(
                "-fx-text-fill: " + textDark + ";" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;"
        );

        Label descriptionLabel = new Label(description);

        descriptionLabel.setWrapText(true);

        descriptionLabel.setMaxWidth(300);

        descriptionLabel.setStyle(
                "-fx-text-fill: " + textLight + ";" +
                "-fx-font-size: 10px;"
        );

        VBox card = new VBox(
                7,
                iconLabel,
                titleLabel,
                descriptionLabel
        );

        card.setMinWidth(0);

        card.setPrefWidth(360);

        card.setMaxWidth(Double.MAX_VALUE);

        card.setMinHeight(105);

        card.setPrefHeight(105);

        card.setPadding(
                new Insets(14)
        );

        card.setStyle(
                "-fx-background-color: " + white + ";" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-cursor: hand;"
        );

        card.setOnMouseEntered(e -> {

            card.setStyle(
                    "-fx-background-color: " + hoverBlue + ";" +
                    "-fx-background-radius: 12px;" +
                    "-fx-border-color: " + primaryBlue + ";" +
                    "-fx-border-radius: 12px;" +
                    "-fx-cursor: hand;"
            );

        });

        card.setOnMouseExited(e -> {

            card.setStyle(
                    "-fx-background-color: " + white + ";" +
                    "-fx-background-radius: 12px;" +
                    "-fx-border-color: " + border + ";" +
                    "-fx-border-radius: 12px;" +
                    "-fx-cursor: hand;"
            );

        });

        return card;
    }

    private Label createMenuItem(
            String icon,
            String text,
            boolean active
    ) {

        Label item = new Label(
                icon + "    " + text
        );

        item.setMaxWidth(
                Double.MAX_VALUE
        );

        item.setPrefHeight(42);

        item.setAlignment(
                Pos.CENTER_LEFT
        );

        item.setPadding(
                new Insets(0, 12, 0, 14)
        );

        if (active) {

            item.setStyle(
                    "-fx-background-color: " + lightBlue + ";" +
                    "-fx-background-radius: 9px;" +
                    "-fx-text-fill: " + primaryBlue + ";" +
                    "-fx-font-size: 13px;" +
                    "-fx-font-weight: bold;"
            );

        } else {

            item.setStyle(
                    "-fx-background-color: transparent;" +
                    "-fx-background-radius: 9px;" +
                    "-fx-text-fill: " + textLight + ";" +
                    "-fx-font-size: 13px;" +
                    "-fx-cursor: hand;"
            );

            item.setOnMouseEntered(e -> {

                item.setStyle(
                        "-fx-background-color: " + hoverBlue + ";" +
                        "-fx-background-radius: 9px;" +
                        "-fx-text-fill: " + primaryBlue + ";" +
                        "-fx-font-size: 13px;" +
                        "-fx-cursor: hand;"
                );

            });

            item.setOnMouseExited(e -> {

                item.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-background-radius: 9px;" +
                        "-fx-text-fill: " + textLight + ";" +
                        "-fx-font-size: 13px;" +
                        "-fx-cursor: hand;"
                );

            });
        }

        return item;
    }

    private String getLogoutStyle() {

        return
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + red + ";" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9px;" +
                "-fx-cursor: hand;";
    }

    private String getLogoutHoverStyle() {

        return
                "-fx-background-color: #FEF2F2;" +
                "-fx-text-fill: " + red + ";" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9px;" +
                "-fx-cursor: hand;";
    }

    private String getInitials(String name) {

        if (name == null || name.trim().isEmpty()) {

            return "HR";
        }

        String[] parts =
                name.trim().split("\\s+");

        if (parts.length == 1) {

            return parts[0]
                    .substring(0, 1)
                    .toUpperCase();
        }

        return (
                parts[0].substring(0, 1) +
                parts[parts.length - 1].substring(0, 1)
        ).toUpperCase();
    }

    public Scene getScene() {

        return accountScene;
    }
}