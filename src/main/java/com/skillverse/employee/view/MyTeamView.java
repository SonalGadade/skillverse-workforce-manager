package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyTeamView {

    private static final String BLUE = "#1648C8";
    private static final String PURPLE = "#7C3AED";
    private static final String GREEN = "#22C55E";
    private static final String DARK = "#111827";
    private static final String TEXT = "#374151";
    private static final String MUTED = "#6B7280";
    private static final String BG = "#F8F8FD";
    private static final String BORDER = "#E7E8F0";

    public void show(Stage stage, String email) {

        BorderPane root = new BorderPane();

        root.setStyle(
                "-fx-background-color: " + BG + ";"
        );

        root.setLeft(
                createSidebar(stage, email)
        );

        ScrollPane scrollPane = new ScrollPane(
                createTeamContent(email)
        );

        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );
        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );

        root.setCenter(scrollPane);

        Scene scene = new Scene(
                root,
                1500,
                900
        );

        stage.setTitle(
                "SkillVerse | My Team"
        );

        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    private VBox createSidebar(
            Stage stage,
            String email
    ) {

        VBox sidebar = new VBox(5);

        sidebar.setPrefWidth(275);
        sidebar.setMinWidth(275);

        sidebar.setPadding(
                new Insets(
                        30,
                        17,
                        20,
                        17
                )
        );

        sidebar.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: transparent " +
                BORDER +
                " transparent transparent;"
        );

        VBox brand = new VBox(2);

        brand.setPadding(
                new Insets(
                        0,
                        12,
                        10,
                        12
                )
        );

        Label logo = new Label("SkillVerse");

        logo.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        28
                )
        );

        logo.setStyle(
                "-fx-text-fill: " + BLUE + ";"
        );

        Label subtitle = new Label(
                "AI-Powered Talent\nEcosystem"
        );

        subtitle.setFont(
                Font.font(
                        "System",
                        13
                )
        );

        subtitle.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        brand.getChildren().addAll(
                logo,
                subtitle
        );

        Button newTask = new Button(
                "+   Add Task"
        );

        newTask.setPrefHeight(50);
        newTask.setMaxWidth(
                Double.MAX_VALUE
        );

        newTask.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        14
                )
        );

        newTask.setStyle(
                "-fx-background-color: linear-gradient(to right, #1648C8, #7C3AED);" +
                "-fx-background-radius: 13;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        );

        VBox.setMargin(
                newTask,
                new Insets(
                        18,
                        8,
                        27,
                        8
                )
        );

        Button dashboard = createMenuButton(
                "▦",
                "Dashboard",
                false
        );

        dashboard.setOnAction(e -> {

            EmployeeDashboardView view =
                    new EmployeeDashboardView();

            view.show(
                    stage,
                    email
            );
        });

        Button profile = createMenuButton(
                "♙",
                "Profile",
                false
        );

        profile.setOnAction(e -> {

            MyProfileView view =
                    new MyProfileView(email);

            view.show(stage, email);
        });

        Button skills = createMenuButton(
                "♧",
                "Skills",
                false
        );

        skills.setOnAction(e -> {

            MySkillsView view =
                    new MySkillsView();

            view.show(
                    stage,
                    email
            );
        });

        Button learning = createMenuButton(
                "◇",
                "Learning",
                false
        );

        Button tasks = createMenuButton(
                "▣",
                "Tasks",
                false
        );

        tasks.setOnAction(e -> {

            MyTasksView view =
                    new MyTasksView();

            view.show(
                    stage,
                    email
            );
        });

        Button team = createMenuButton(
                "♧",
                "Team",
                true
        );

        Button performance = createMenuButton(
                "↗",
                "Performance",
                false
        );

        Button goals = createMenuButton(
                "⚑",
                "Goals",
                false
        );

        Button achievements = createMenuButton(
                "♕",
                "Achievements",
                false
        );

        Button career = createMenuButton(
                "↗",
                "Career",
                false
        );

        Button services = createMenuButton(
                "♧",
                "Services",
                false
        );

        Region spacer = new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );

        Button settings = createMenuButton(
                "⚙",
                "Settings",
                false
        );

        Button logout = createMenuButton(
                "⇥",
                "Logout",
                false
        );

        sidebar.getChildren().addAll(
                brand,
                newTask,
                dashboard,
                profile,
                skills,
                learning,
                tasks,
                team,
                performance,
                goals,
                achievements,
                career,
                services,
                spacer,
                settings,
                logout
        );

        return sidebar;
    }

    private Button createMenuButton(
            String icon,
            String text,
            boolean active
    ) {

        Label iconLabel = new Label(icon);

        iconLabel.setMinWidth(28);

        iconLabel.setAlignment(
                Pos.CENTER
        );

        iconLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        18
                )
        );

        Label textLabel = new Label(text);

        textLabel.setFont(
                Font.font(
                        "System",
                        active
                                ? FontWeight.BOLD
                                : FontWeight.NORMAL,
                        14
                )
        );

        HBox content = new HBox(
                14,
                iconLabel,
                textLabel
        );

        content.setAlignment(
                Pos.CENTER_LEFT
        );

        Button button = new Button();

        button.setGraphic(content);

        button.setPrefHeight(45);

        button.setMaxWidth(
                Double.MAX_VALUE
        );

        button.setAlignment(
                Pos.CENTER_LEFT
        );

        if (active) {

            iconLabel.setStyle(
                    "-fx-text-fill: " + BLUE + ";"
            );

            textLabel.setStyle(
                    "-fx-text-fill: " + BLUE + ";"
            );

            button.setStyle(
                    "-fx-background-color: #DCE8FF;" +
                    "-fx-background-radius: 11;" +
                    "-fx-padding: 0 13 0 13;"
            );

        } else {

            iconLabel.setStyle(
                    "-fx-text-fill: " + DARK + ";"
            );

            textLabel.setStyle(
                    "-fx-text-fill: " + DARK + ";"
            );

            button.setStyle(
                    "-fx-background-color: transparent;" +
                    "-fx-background-radius: 11;" +
                    "-fx-padding: 0 13 0 13;" +
                    "-fx-cursor: hand;"
            );

            button.setOnMouseEntered(e ->
                    button.setStyle(
                            "-fx-background-color: #F3F5FA;" +
                            "-fx-background-radius: 11;" +
                            "-fx-padding: 0 13 0 13;" +
                            "-fx-cursor: hand;"
                    )
            );

            button.setOnMouseExited(e ->
                    button.setStyle(
                            "-fx-background-color: transparent;" +
                            "-fx-background-radius: 11;" +
                            "-fx-padding: 0 13 0 13;" +
                            "-fx-cursor: hand;"
                    )
            );
        }

        return button;
    }

    public VBox createTeamContent(
            String email
    ) {

        VBox page = new VBox(25);

        page.setPadding(
                new Insets(
                        38,
                        42,
                        45,
                        42
                )
        );

        page.setStyle(
                "-fx-background-color: " + BG + ";"
        );

        HBox heading = createHeading();

        HBox searchBar = createSearchBar();

        HBox cards = createTeamCards();

        page.getChildren().addAll(
                heading,
                searchBar,
                cards
        );

        return page;
    }

    private HBox createHeading() {

        HBox heading = new HBox();

        heading.setAlignment(
                Pos.CENTER_LEFT
        );

        VBox titleBox = new VBox(5);

        Label title = new Label(
                "MY TEAM"
        );

        title.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        38
                )
        );

        title.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        Label subtitle = new Label(
                "Connect with your team and collaborate effectively."
        );

        subtitle.setFont(
                Font.font(
                        "System",
                        15
                )
        );

        subtitle.setStyle(
                "-fx-text-fill: " + TEXT + ";"
        );

        titleBox.getChildren().addAll(
                title,
                subtitle
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        VBox members = new VBox(2);

        members.setPadding(
                new Insets(
                        13,
                        20,
                        13,
                        20
                )
        );

        members.setMinWidth(220);

        members.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 14;"
        );

        HBox row = new HBox(12);

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        Label icon = new Label("♧");

        icon.setPrefSize(
                45,
                45
        );

        icon.setAlignment(
                Pos.CENTER
        );

        icon.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        20
                )
        );

        icon.setStyle(
                "-fx-background-color: #F0E8FF;" +
                "-fx-background-radius: 23;" +
                "-fx-text-fill: " + PURPLE + ";"
        );

        VBox text = new VBox(1);

        Label memberTitle = new Label(
                "TEAM MEMBERS"
        );

        memberTitle.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        11
                )
        );

        memberTitle.setStyle(
                "-fx-text-fill: " + MUTED + ";"
        );

        Label count = new Label("18");

        count.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        25
                )
        );

        count.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        text.getChildren().addAll(
                memberTitle,
                count
        );

        row.getChildren().addAll(
                icon,
                text
        );

        members.getChildren().add(row);

        heading.getChildren().addAll(
                titleBox,
                spacer,
                members
        );

        return heading;
    }

    private HBox createSearchBar() {

        HBox container = new HBox(15);

        container.setPadding(
                new Insets(
                        16,
                        17,
                        16,
                        17
                )
        );

        container.setAlignment(
                Pos.CENTER_LEFT
        );

        container.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 17;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 17;"
        );

        Label search = new Label(
                "⌕   Search team members..."
        );

        search.setPrefWidth(415);
        search.setPrefHeight(52);

        search.setAlignment(
                Pos.CENTER_LEFT
        );

        search.setPadding(
                new Insets(
                        0,
                        18,
                        0,
                        18
                )
        );

        search.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 13;" +
                "-fx-border-color: #CBD0DC;" +
                "-fx-border-radius: 13;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        HBox filters = new HBox(4);

        filters.setPadding(
                new Insets(4)
        );

        filters.setStyle(
                "-fx-background-color: #F0F2FA;" +
                "-fx-background-radius: 10;"
        );

        Button all = createFilterButton(
                "All",
                true
        );

        Button online = createFilterButton(
                "Online",
                false
        );

        Button offline = createFilterButton(
                "Offline",
                false
        );

        filters.getChildren().addAll(
                all,
                online,
                offline
        );

        container.getChildren().addAll(
                search,
                spacer,
                filters
        );

        return container;
    }

    private Button createFilterButton(
            String text,
            boolean active
    ) {

        Button button = new Button(text);

        button.setPrefWidth(82);
        button.setPrefHeight(38);

        button.setStyle(
                active
                        ? "-fx-background-color: white;" +
                          "-fx-background-radius: 9;" +
                          "-fx-text-fill: " + BLUE + ";"
                        : "-fx-background-color: transparent;" +
                          "-fx-background-radius: 9;" +
                          "-fx-text-fill: " + DARK + ";"
        );

        return button;
    }

    private HBox createTeamCards() {

        HBox cards = new HBox(25);

        cards.setAlignment(
                Pos.TOP_LEFT
        );

        cards.getChildren().addAll(

                createMemberCard(
                        "Rahul Patil",
                        "Software Developer",
                        "Computer Engineering",
                        "rahul@skillverse.com",
                        true,
                        BLUE,
                        "RP"
                ),

                createMemberCard(
                        "Priya Sharma",
                        "UI/UX Designer",
                        "Design",
                        "priya@skillverse.com",
                        false,
                        PURPLE,
                        "PS"
                ),

                createMemberCard(
                        "David Chen",
                        "Data Scientist",
                        "Analytics",
                        "david@skillverse.com",
                        true,
                        PURPLE,
                        "DC"
                ),

                createMemberCard(
                        "Sarah Jenkins",
                        "Product Manager",
                        "Product",
                        "sarah@skillverse.com",
                        true,
                        GREEN,
                        "SJ"
                )
        );

        return cards;
    }

    private VBox createMemberCard(
            String name,
            String role,
            String department,
            String email,
            boolean online,
            String accent,
            String initials
    ) {

        VBox card = new VBox(8);

        card.setPrefWidth(235);
        card.setMinWidth(210);
        card.setMinHeight(385);

        card.setAlignment(
                Pos.TOP_CENTER
        );

        card.setPadding(
                new Insets(
                        28,
                        17,
                        20,
                        17
                )
        );

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 17;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 17;"
        );

        StackPane avatarBox = new StackPane();

        Circle avatar = new Circle(48);

        avatar.setFill(
                Color.web("#E9EEF8")
        );

        Label initialsLabel = new Label(
                initials
        );

        initialsLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        20
                )
        );

        initialsLabel.setStyle(
                "-fx-text-fill: " + accent + ";"
        );

        Circle status = new Circle(7);

        status.setFill(
                online
                        ? Color.web(GREEN)
                        : Color.web("#A8AFBB")
        );

        status.setStroke(
                Color.WHITE
        );

        status.setStrokeWidth(3);

        StackPane.setAlignment(
                status,
                Pos.BOTTOM_RIGHT
        );

        avatarBox.getChildren().addAll(
                avatar,
                initialsLabel,
                status
        );

        Label nameLabel = new Label(
                name
        );

        nameLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        19
                )
        );

        nameLabel.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        Label roleLabel = new Label(
                role
        );

        roleLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        14
                )
        );

        roleLabel.setStyle(
                "-fx-text-fill: " + PURPLE + ";"
        );

        Label departmentLabel = new Label(
                department
        );

        departmentLabel.setFont(
                Font.font(
                        "System",
                        13
                )
        );

        departmentLabel.setStyle(
                "-fx-text-fill: " + TEXT + ";"
        );

        Label emailLabel = new Label(
                "✉  " + email
        );

        emailLabel.setFont(
                Font.font(
                        "System",
                        11
                )
        );

        emailLabel.setPadding(
                new Insets(
                        8,
                        10,
                        8,
                        10
                )
        );

        emailLabel.setStyle(
                "-fx-background-color: #F0F3FC;" +
                "-fx-background-radius: 15;" +
                "-fx-text-fill: " + DARK + ";"
        );

        Region spacer = new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );

        HBox buttons = new HBox(9);

        buttons.setAlignment(
                Pos.CENTER
        );

        Button profile = new Button(
                "View Profile"
        );

        profile.setPrefSize(
                82,
                45
        );

        profile.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #C9CED9;" +
                "-fx-border-radius: 9;" +
                "-fx-background-radius: 9;" +
                "-fx-text-fill: " + DARK + ";" +
                "-fx-cursor: hand;"
        );

        Button message = new Button(
                "Message"
        );

        message.setPrefSize(
                92,
                45
        );

        message.setStyle(
                "-fx-background-color: " + BLUE + ";" +
                "-fx-background-radius: 9;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        );

        buttons.getChildren().addAll(
                profile,
                message
        );

        card.getChildren().addAll(
                avatarBox,
                nameLabel,
                roleLabel,
                departmentLabel,
                emailLabel,
                spacer,
                buttons
        );

        return card;
    }
}