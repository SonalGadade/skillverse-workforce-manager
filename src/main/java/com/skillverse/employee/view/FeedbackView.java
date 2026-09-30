package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FeedbackView {

    public void show(Stage stage) {

        Label title = new Label("My Feedback");
        title.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label(
            "View feedback, ratings and suggestions for your professional growth"
        );
        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox overallCard = createRatingCard(
            "Overall Performance",
            "4.6 / 5",
            "Excellent",
            "#2563EB"
        );

        VBox technicalCard = createRatingCard(
            "Technical Skills",
            "4.8 / 5",
            "Excellent",
            "#7C3AED"
        );

        VBox communicationCard = createRatingCard(
            "Communication",
            "4.3 / 5",
            "Very Good",
            "#16A34A"
        );

        HBox ratingCards = new HBox(20);

        ratingCards.getChildren().addAll(
            overallCard,
            technicalCard,
            communicationCard
        );

        Label feedbackTitle =
            new Label("Latest Manager Feedback");

        feedbackTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label managerLabel =
            new Label("Manager: Rahul Sharma");

        managerLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #2563EB;"
        );

        Label feedbackText =
            new Label(
                "Sarah has demonstrated strong technical skills and consistently "
                + "completed assigned tasks on time. She has shown excellent "
                + "progress in Java and application development. Continued focus "
                + "on communication and leadership skills will help her grow further."
            );

        feedbackText.setWrapText(true);

        feedbackText.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #64748B;"
        );

        Label dateLabel =
            new Label("Reviewed: 15 August 2026");

        dateLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #94A3B8;"
        );

        VBox managerFeedback =
            new VBox(12);

        managerFeedback.setPadding(
            new Insets(22)
        );

        managerFeedback.setMaxWidth(850);

        managerFeedback.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #DCE8FA;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 18, 0, 0, 5);"
        );

        managerFeedback.getChildren().addAll(
            managerLabel,
            feedbackText,
            dateLabel
        );

        Label suggestionTitle =
            new Label("Share Your Feedback");

        suggestionTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        TextArea feedbackArea =
            new TextArea();

        feedbackArea.setPromptText(
            "Share your suggestions or feedback..."
        );

        feedbackArea.setPrefHeight(100);
        feedbackArea.setWrapText(true);

        feedbackArea.setStyle(
            "-fx-background-color: #F8FAFC;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 12;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 12;"
        );

        Button submitButton =
            new Button("Submit Feedback");

        submitButton.setPrefHeight(42);
        submitButton.setPrefWidth(160);

        submitButton.setStyle(
            "-fx-background-color: #2563EB;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        );

        VBox feedbackCard =
            new VBox(12);

        feedbackCard.setPadding(
            new Insets(22)
        );

        feedbackCard.setMaxWidth(850);

        feedbackCard.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 18, 0, 0, 5);"
        );

        feedbackCard.getChildren().addAll(
            suggestionTitle,
            feedbackArea,
            submitButton
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
            ratingCards,
            feedbackTitle,
            managerFeedback,
            feedbackCard
        );

        BorderPane root =
            new BorderPane();

        root.setCenter(content);



        Scene scene =
            new Scene(root, 1450, 850);

        stage.setTitle(
            "SkillVerse - My Feedback"
        );

        stage.setScene(scene);
        stage.show();
    }

    private VBox createRatingCard(
        String title,
        String rating,
        String status,
        String textColor
    ) {

        Label titleLabel =
            new Label(title);

        titleLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #64748B;"
        );

        Label ratingLabel =
            new Label(rating);

        ratingLabel.setStyle(
            "-fx-font-size: 25px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        Label statusLabel =
            new Label(status);

        statusLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        VBox card =
            new VBox(9);

        card.setPadding(
            new Insets(22)
        );

        card.setPrefWidth(280);
        card.setPrefHeight(145);

        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.08), 18, 0, 0, 5);"
        );

        card.getChildren().addAll(
            titleLabel,
            ratingLabel,
            statusLabel
        );

        return card;
    }
}