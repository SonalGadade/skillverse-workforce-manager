package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyGoalsView {

    private static final String BLUE = "#1648C8";
    private static final String PURPLE = "#7C3AED";
    private static final String GREEN = "#059669";
    private static final String ORANGE = "#E28A00";
    private static final String DARK = "#111827";
    private static final String TEXT = "#374151";
    private static final String MUTED = "#6B7280";
    private static final String BG = "#F8F8FD";
    private static final String BORDER = "#E7E8F0";

    private String employeeEmail;

    public VBox createContent(String email) {
        employeeEmail = email;
        return new EmployeeTasksView(email).createTasksContent(email);
    }

    private HBox createHeader() {

        HBox header =
                new HBox();

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        Label title =
                new Label(
                        "My Goals"
                );

        title.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        30
                )
        );

        title.setStyle(
                "-fx-text-fill: " +
                DARK +
                ";"
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label employee =
                new Label(
                        employeeEmail == null
                                ? "Employee"
                                : employeeEmail
                );

        employee.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        13
                )
        );

        employee.setStyle(
                "-fx-text-fill: " +
                MUTED +
                ";"
        );

        HBox userBox =
                new HBox(employee);

        userBox.setAlignment(
                Pos.CENTER
        );

        userBox.setPadding(
                new Insets(
                        10,
                        15,
                        10,
                        15
                )
        );

        userBox.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: " +
                BORDER +
                ";" +
                "-fx-border-radius: 12;"
        );

        header.getChildren().addAll(
                title,
                spacer,
                userBox
        );

        return header;
    }

    private VBox createTitleSection() {

        VBox box =
                new VBox(7);

        Label small =
                new Label(
                        "PROFESSIONAL DEVELOPMENT"
                );

        small.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        12
                )
        );

        small.setStyle(
                "-fx-text-fill: " +
                BLUE +
                ";"
        );

        Label title =
                new Label(
                        "Track your goals and progress"
                );

        title.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        32
                )
        );

        title.setStyle(
                "-fx-text-fill: " +
                DARK +
                ";"
        );

        Label description =
                new Label(
                        "Set meaningful goals, monitor your progress and achieve your professional milestones."
                );

        description.setWrapText(true);

        description.setFont(
                Font.font(
                        "System",
                        14
                )
        );

        description.setStyle(
                "-fx-text-fill: " +
                TEXT +
                ";"
        );

        box.getChildren().addAll(
                small,
                title,
                description
        );

        return box;
    }

    private HBox createSummaryCards() {

        HBox summary =
                new HBox(18);

        summary.setFillHeight(true);

        summary.getChildren().addAll(
                createSummaryCard(
                        "Total Goals",
                        "6",
                        "Active goals",
                        BLUE
                ),
                createSummaryCard(
                        "Completed",
                        "3",
                        "Goals achieved",
                        GREEN
                ),
                createSummaryCard(
                        "In Progress",
                        "2",
                        "Currently working",
                        PURPLE
                ),
                createSummaryCard(
                        "Pending",
                        "1",
                        "Not started",
                        ORANGE
                )
        );

        return summary;
    }

    private VBox createSummaryCard(
            String title,
            String value,
            String subtitle,
            String accent
    ) {

        VBox card =
                new VBox(7);

        card.setPadding(
                new Insets(20)
        );

        card.setPrefHeight(135);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 17;" +
                "-fx-border-color: " +
                BORDER +
                ";" +
                "-fx-border-radius: 17;"
        );

        Label titleLabel =
                new Label(title);

        titleLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        12
                )
        );

        titleLabel.setStyle(
                "-fx-text-fill: " +
                MUTED +
                ";"
        );

        Label valueLabel =
                new Label(value);

        valueLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        30
                )
        );

        valueLabel.setStyle(
                "-fx-text-fill: " +
                accent +
                ";"
        );

        Label subtitleLabel =
                new Label(subtitle);

        subtitleLabel.setFont(
                Font.font(
                        "System",
                        12
                )
        );

        subtitleLabel.setStyle(
                "-fx-text-fill: " +
                TEXT +
                ";"
        );

        card.getChildren().addAll(
                titleLabel,
                valueLabel,
                subtitleLabel
        );

        HBox.setHgrow(
                card,
                Priority.ALWAYS
        );

        return card;
    }

    private VBox createGoalsSection() {

        VBox container =
                new VBox(18);

        container.setPadding(
                new Insets(24)
        );

        container.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 18;" +
                "-fx-border-color: " +
                BORDER +
                ";" +
                "-fx-border-radius: 18;"
        );

        HBox heading =
                new HBox();

        heading.setAlignment(
                Pos.CENTER_LEFT
        );

        Label title =
                new Label(
                        "My Goals"
                );

        title.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        21
                )
        );

        title.setStyle(
                "-fx-text-fill: " +
                DARK +
                ";"
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Button addGoal =
                new Button(
                        "+ Add Goal"
                );

        addGoal.setPrefHeight(38);

        addGoal.setPadding(
                new Insets(
                        0,
                        18,
                        0,
                        18
                )
        );

        addGoal.setStyle(
                "-fx-background-color: " +
                BLUE +
                ";" +
                "-fx-background-radius: 10;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );

        heading.getChildren().addAll(
                title,
                spacer,
                addGoal
        );

        GridPane grid =
                new GridPane();

        grid.setHgap(18);
        grid.setVgap(18);

        ColumnConstraints column1 =
                new ColumnConstraints();

        ColumnConstraints column2 =
                new ColumnConstraints();

        column1.setPercentWidth(50);
        column2.setPercentWidth(50);

        grid.getColumnConstraints().addAll(
                column1,
                column2
        );

        VBox goal1 =
                createGoalCard(
                        "Complete Java Certification",
                        "Complete an advanced Java certification to improve backend development skills.",
                        "75%",
                        "In Progress",
                        PURPLE
                );

        VBox goal2 =
                createGoalCard(
                        "Improve Problem Solving",
                        "Solve coding problems regularly and improve algorithmic thinking.",
                        "60%",
                        "In Progress",
                        BLUE
                );

        VBox goal3 =
                createGoalCard(
                        "Complete Project",
                        "Finish the SkillVerse employee management project successfully.",
                        "100%",
                        "Completed",
                        GREEN
                );

        VBox goal4 =
                createGoalCard(
                        "Learn Cloud Computing",
                        "Complete cloud fundamentals and understand modern cloud architecture.",
                        "30%",
                        "Pending",
                        ORANGE
                );

        grid.add(
                goal1,
                0,
                0
        );

        grid.add(
                goal2,
                1,
                0
        );

        grid.add(
                goal3,
                0,
                1
        );

        grid.add(
                goal4,
                1,
                1
        );

        container.getChildren().addAll(
                heading,
                grid
        );

        return container;
    }

    private VBox createGoalCard(
            String title,
            String description,
            String progress,
            String status,
            String accent
    ) {

        VBox card =
                new VBox(12);

        card.setPadding(
                new Insets(20)
        );

        card.setMinHeight(190);

        card.setStyle(
                "-fx-background-color: #FBFBFE;" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: " +
                BORDER +
                ";" +
                "-fx-border-radius: 15;"
        );

        HBox top =
                new HBox();

        top.setAlignment(
                Pos.CENTER_LEFT
        );

        Label titleLabel =
                new Label(title);

        titleLabel.setWrapText(true);

        titleLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        16
                )
        );

        titleLabel.setStyle(
                "-fx-text-fill: " +
                DARK +
                ";"
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label statusLabel =
                new Label(status);

        statusLabel.setPadding(
                new Insets(
                        5,
                        10,
                        5,
                        10
                )
        );

        statusLabel.setStyle(
                "-fx-background-color: " +
                accent +
                "20;" +
                "-fx-background-radius: 20;" +
                "-fx-text-fill: " +
                accent +
                ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );

        top.getChildren().addAll(
                titleLabel,
                spacer,
                statusLabel
        );

        Label descriptionLabel =
                new Label(description);

        descriptionLabel.setWrapText(true);

        descriptionLabel.setFont(
                Font.font(
                        "System",
                        13
                )
        );

        descriptionLabel.setStyle(
                "-fx-text-fill: " +
                MUTED +
                ";"
        );

        HBox progressHeader =
                new HBox();

        Label progressText =
                new Label("Progress");

        progressText.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        12
                )
        );

        progressText.setStyle(
                "-fx-text-fill: " +
                TEXT +
                ";"
        );

        Region progressSpacer =
                new Region();

        HBox.setHgrow(
                progressSpacer,
                Priority.ALWAYS
        );

        Label progressValue =
                new Label(progress);

        progressValue.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        12
                )
        );

        progressValue.setStyle(
                "-fx-text-fill: " +
                accent +
                ";"
        );

        progressHeader.getChildren().addAll(
                progressText,
                progressSpacer,
                progressValue
        );

        double percentage =
                Double.parseDouble(
                        progress.replace("%", "")
                ) / 100.0;

        StackPane progressContainer =
                new StackPane();

        progressContainer.setPrefHeight(8);

        progressContainer.setMaxWidth(
                Double.MAX_VALUE
        );

        Region background =
                new Region();

        background.setPrefHeight(8);

        background.setMaxWidth(
                Double.MAX_VALUE
        );

        background.setStyle(
                "-fx-background-color: #E9EAF1;" +
                "-fx-background-radius: 10;"
        );

        Region progressBar =
                new Region();

        progressBar.setPrefHeight(8);

        progressBar.prefWidthProperty().bind(
                progressContainer.widthProperty()
                        .multiply(percentage)
        );

        progressBar.setStyle(
                "-fx-background-color: " +
                accent +
                ";" +
                "-fx-background-radius: 10;"
        );

        progressContainer.getChildren().addAll(
                background,
                progressBar
        );

        StackPane.setAlignment(
                background,
                Pos.CENTER_LEFT
        );

        StackPane.setAlignment(
                progressBar,
                Pos.CENTER_LEFT
        );

        card.getChildren().addAll(
                top,
                descriptionLabel,
                progressHeader,
                progressContainer
        );

        return card;
    }

    public void show(Stage stage, String email) {
        
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }
}