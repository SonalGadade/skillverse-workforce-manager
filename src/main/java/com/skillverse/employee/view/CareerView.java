package com.skillverse.employee.view;

import com.skillverse.employee.controller.EmployeeDashboardView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CareerView {

    public void show(Stage stage) {

        Label title = new Label("Career Growth");
        title.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label(
            "Explore your career path and plan your professional growth"
        );
        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox currentRole = createCareerCard(
            "Current Role",
            "Software Developer",
            "Your current position",
            "#2563EB"
        );

        VBox nextRole = createCareerCard(
            "Next Target Role",
            "Senior Software Developer",
            "Recommended career progression",
            "#7C3AED"
        );

        VBox futureRole = createCareerCard(
            "Future Goal",
            "Technical Lead",
            "Long-term career target",
            "#16A34A"
        );

        HBox careerCards = new HBox(20);

        careerCards.getChildren().addAll(
            currentRole,
            nextRole,
            futureRole
        );

        Label skillsTitle =
            new Label("Recommended Skills");

        skillsTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        VBox skill1 = createSkill(
            "Advanced Java",
            "High Priority",
            "#2563EB"
        );

        VBox skill2 = createSkill(
            "System Design",
            "High Priority",
            "#7C3AED"
        );

        VBox skill3 = createSkill(
            "Leadership",
            "Medium Priority",
            "#16A34A"
        );

        VBox skill4 = createSkill(
            "Communication",
            "Medium Priority",
            "#EA580C"
        );

        HBox skillRow1 = new HBox(15);
        skillRow1.getChildren().addAll(
            skill1,
            skill2
        );

        HBox skillRow2 = new HBox(15);
        skillRow2.getChildren().addAll(
            skill3,
            skill4
        );

        VBox skillsBox = new VBox(15);

        skillsBox.getChildren().addAll(
            skillRow1,
            skillRow2
        );

        Label adviceTitle =
            new Label("Career Advice");

        adviceTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label adviceText =
            new Label(
                "Continue improving your technical skills and take part in "
                + "leadership and communication programs. Completing your "
                + "recommended learning courses can help prepare you for "
                + "your next career opportunity."
            );

        adviceText.setWrapText(true);

        adviceText.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox adviceCard =
            new VBox(10);

        adviceCard.setPadding(
            new Insets(22)
        );

        adviceCard.setMaxWidth(850);

        adviceCard.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #DCE8FA;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 18, 0, 0, 5);"
        );

        adviceCard.getChildren().add(
            adviceText
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
            careerCards,
            skillsTitle,
            skillsBox,
            adviceTitle,
            adviceCard
        );

        BorderPane root =
            new BorderPane();

        root.setCenter(content);



        Scene scene =
            new Scene(root, 1450, 850);

        stage.setTitle(
            "SkillVerse - Career Growth"
        );

        stage.setScene(scene);
        stage.show();
    }

    private VBox createCareerCard(
        String title,
        String role,
        String description,
        String textColor
    ) {

        Label titleLabel =
            new Label(title);

        titleLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #64748B;"
        );

        Label roleLabel =
            new Label(role);

        roleLabel.setWrapText(true);

        roleLabel.setStyle(
            "-fx-font-size: 19px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        Label descriptionLabel =
            new Label(description);

        descriptionLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #94A3B8;"
        );

        VBox card =
            new VBox(10);

        card.setPadding(
            new Insets(22)
        );

        card.setPrefWidth(300);
        card.setPrefHeight(155);

        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.08), 18, 0, 0, 5);"
        );

        card.getChildren().addAll(
            titleLabel,
            roleLabel,
            descriptionLabel
        );

        return card;
    }

    private VBox createSkill(
        String skill,
        String priority,
        String textColor
    ) {

        Label skillLabel =
            new Label(skill);

        skillLabel.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label priorityLabel =
            new Label(priority);

        priorityLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        VBox card =
            new VBox(7);

        card.setPadding(
            new Insets(16)
        );

        card.setPrefWidth(300);

        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 14;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 14;"
        );

        card.getChildren().addAll(
            skillLabel,
            priorityLabel
        );

        return card;
    }
}