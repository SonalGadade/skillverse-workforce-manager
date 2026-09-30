package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LearningHistoryView {

    public void show(Stage stage) {

        Label title = new Label("Learning History");

        title.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label(
            "Track your completed courses, training programs and learning achievements"
        );

        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox course1 = createCourseCard(
            "Advanced Java Programming",
            "Programming & Development",
            "Completed",
            "15 August 2026",
            "Certificate Available",
            "#2563EB"
        );

        VBox course2 = createCourseCard(
            "Professional Communication",
            "Soft Skills",
            "Completed",
            "02 August 2026",
            "Certificate Available",
            "#7C3AED"
        );

        VBox course3 = createCourseCard(
            "Database Management",
            "Technical Skills",
            "Completed",
            "20 July 2026",
            "Certificate Available",
            "#16A34A"
        );

        VBox course4 = createCourseCard(
            "Leadership Fundamentals",
            "Leadership",
            "Completed",
            "10 July 2026",
            "Certificate Available",
            "#EA580C"
        );

        VBox course5 = createCourseCard(
            "UI/UX Design Basics",
            "Design",
            "Completed",
            "25 June 2026",
            "Certificate Available",
            "#0891B2"
        );

        VBox course6 = createCourseCard(
            "Cloud Computing Essentials",
            "Cloud Technology",
            "Completed",
            "12 June 2026",
            "Certificate Available",
            "#9333EA"
        );

        HBox row1 = new HBox(20);

        row1.getChildren().addAll(
            course1,
            course2,
            course3
        );

        HBox row2 = new HBox(20);

        row2.getChildren().addAll(
            course4,
            course5,
            course6
        );

        Label summaryTitle = new Label(
            "Learning Summary"
        );

        summaryTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        VBox completedCard = createSummaryCard(
            "Courses Completed",
            "6",
            "#2563EB"
        );

        VBox certificatesCard = createSummaryCard(
            "Certificates",
            "6",
            "#16A34A"
        );

        VBox learningHoursCard = createSummaryCard(
            "Learning Hours",
            "48 Hrs",
            "#7C3AED"
        );

        HBox summaryCards = new HBox(20);

        summaryCards.getChildren().addAll(
            completedCard,
            certificatesCard,
            learningHoursCard
        );



        VBox content = new VBox(25);

        content.setPadding(
            new Insets(40)
        );

        content.setAlignment(
            Pos.TOP_LEFT
        );

        content.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #F8FAFF, #EEF4FF);"
        );

        content.getChildren().addAll(
            title,
            subtitle,
            summaryTitle,
            summaryCards,
            row1,
            row2
        );

        BorderPane root = new BorderPane();

        root.setCenter(content);



        Scene scene = new Scene(
            root,
            1450,
            850
        );

        stage.setTitle(
            "SkillVerse - Learning History"
        );

        stage.setScene(scene);
        stage.show();
    }

    private VBox createCourseCard(
        String courseName,
        String category,
        String status,
        String completionDate,
        String certificate,
        String textColor
    ) {

        Label courseLabel =
            new Label(courseName);

        courseLabel.setWrapText(true);

        courseLabel.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label categoryLabel =
            new Label(category);

        categoryLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #64748B;"
        );

        Label statusLabel =
            new Label(status);

        statusLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        Label dateLabel =
            new Label(
                "Completed: " + completionDate
            );

        dateLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #64748B;"
        );

        Label certificateLabel =
            new Label(certificate);

        certificateLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        Button viewButton =
            new Button("View Certificate");

        viewButton.setPrefHeight(36);

        viewButton.setStyle(
            "-fx-background-color: #F1F5FF;" +
            "-fx-text-fill: " + textColor + ";" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 9;" +
            "-fx-cursor: hand;"
        );

        VBox card =
            new VBox(9);

        card.setPadding(
            new Insets(18)
        );

        card.setPrefWidth(300);
        card.setPrefHeight(190);

        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 16, 0, 0, 4);"
        );

        card.getChildren().addAll(
            courseLabel,
            categoryLabel,
            statusLabel,
            dateLabel,
            certificateLabel,
            viewButton
        );

        return card;
    }

    private VBox createSummaryCard(
        String title,
        String value,
        String textColor
    ) {

        Label titleLabel =
            new Label(title);

        titleLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-text-fill: #64748B;"
        );

        Label valueLabel =
            new Label(value);

        valueLabel.setStyle(
            "-fx-font-size: 25px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        VBox card =
            new VBox(8);

        card.setPadding(
            new Insets(18)
        );

        card.setPrefWidth(210);
        card.setPrefHeight(105);

        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 16;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.06), 15, 0, 0, 4);"
        );

        card.getChildren().addAll(
            titleLabel,
            valueLabel
        );

        return card;
    }
}