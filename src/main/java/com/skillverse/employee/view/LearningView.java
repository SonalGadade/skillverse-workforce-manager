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

public class LearningView {

    public void show(Stage stage) {

        Label title = new Label("Learning & Courses");
        title.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label(
            "Explore courses and build skills for your career growth"
        );
        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox javaCourse = createCourseCard(
            "Advanced Java Programming",
            "Programming",
            "12 Lessons",
            "75% Complete",
            "#2563EB"
        );

        VBox javaFxCourse = createCourseCard(
            "JavaFX UI Development",
            "Application Development",
            "10 Lessons",
            "60% Complete",
            "#7C3AED"
        );

        VBox sqlCourse = createCourseCard(
            "SQL & Database Management",
            "Database",
            "8 Lessons",
            "40% Complete",
            "#16A34A"
        );

        VBox communicationCourse = createCourseCard(
            "Professional Communication",
            "Soft Skills",
            "6 Lessons",
            "90% Complete",
            "#EA580C"
        );

        HBox row1 = new HBox(20);

        row1.getChildren().addAll(
            javaCourse,
            javaFxCourse
        );

        HBox row2 = new HBox(20);

        row2.getChildren().addAll(
            sqlCourse,
            communicationCourse
        );

        Label recommendedTitle =
            new Label("Recommended Courses");

        recommendedTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label recommendedText =
            new Label(
                "Based on your current skills and career goals, " +
                "these courses can help you improve your technical expertise."
            );

        recommendedText.setWrapText(true);

        recommendedText.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox recommendationCard =
            new VBox(10);

        recommendationCard.setPadding(
            new Insets(22)
        );

        recommendationCard.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #DCE8FA;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 18, 0, 0, 5);"
        );

        recommendationCard.getChildren().add(
            recommendedText
        );



        VBox content =
            new VBox(25);

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
            row1,
            row2,
            recommendedTitle,
            recommendationCard
        );

        BorderPane root =
            new BorderPane();

        root.setCenter(content);



        Scene scene =
            new Scene(root, 1450, 850);

        stage.setTitle(
            "SkillVerse - Learning & Courses"
        );

        stage.setScene(scene);
        stage.show();
    }

    private VBox createCourseCard(
        String courseName,
        String category,
        String lessons,
        String progress,
        String textColor
    ) {

        Label name =
            new Label(courseName);

        name.setWrapText(true);

        name.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label categoryLabel =
            new Label(category);

        categoryLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-text-fill: #64748B;"
        );

        Label lessonsLabel =
            new Label(lessons);

        lessonsLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-text-fill: #94A3B8;"
        );

        Label progressLabel =
            new Label(progress);

        progressLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        Button continueButton =
            new Button("Continue Course");

        continueButton.setPrefHeight(40);

        continueButton.setStyle(
            "-fx-background-color: #F1F5FF;" +
            "-fx-text-fill: " + textColor + ";" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        );

        VBox card =
            new VBox(10);

        card.setPadding(
            new Insets(22)
        );

        card.setPrefWidth(400);
        card.setPrefHeight(210);

        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.08), 18, 0, 0, 5);"
        );

        card.getChildren().addAll(
            name,
            categoryLabel,
            lessonsLabel,
            progressLabel,
            continueButton
        );

        return card;
    }
}