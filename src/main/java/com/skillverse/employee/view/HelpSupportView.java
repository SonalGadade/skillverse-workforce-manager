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

public class HelpSupportView {

    public void show(Stage stage) {

        Label title = new Label("Help & Support");
        title.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label(
            "Find answers, get assistance and contact SkillVerse support"
        );
        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox faq1 = createFaq(
            "How can I update my profile?",
            "Open My Profile and update your personal information."
        );

        VBox faq2 = createFaq(
            "How can I access my training?",
            "Open Training or Learning & Courses to continue your courses."
        );

        VBox faq3 = createFaq(
            "Where can I check my performance?",
            "Open My Performance to view your score, goals and feedback."
        );

        VBox faq4 = createFaq(
            "How can I change my password?",
            "Open Settings and use the Change Password section."
        );

        VBox faqBox = new VBox(12);

        faqBox.getChildren().addAll(
            faq1,
            faq2,
            faq3,
            faq4
        );

        Label faqTitle = new Label("Frequently Asked Questions");
        faqTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label contactTitle = new Label("Contact Support");
        contactTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label contactText = new Label(
            "Having an issue? Describe your problem and our support team will assist you."
        );

        contactText.setWrapText(true);

        contactText.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #64748B;"
        );

        TextArea messageArea = new TextArea();

        messageArea.setPromptText(
            "Describe your issue..."
        );

        messageArea.setPrefHeight(100);
        messageArea.setWrapText(true);

        messageArea.setStyle(
            "-fx-background-color: #F8FAFC;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 12;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 12;"
        );

        Button sendButton =
            new Button("Send Request");

        sendButton.setPrefHeight(42);
        sendButton.setPrefWidth(150);

        sendButton.setStyle(
            "-fx-background-color: #2563EB;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        );

        VBox contactCard =
            new VBox(12);

        contactCard.setPadding(
            new Insets(22)
        );

        contactCard.setMaxWidth(650);

        contactCard.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #DCE8FA;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 18, 0, 0, 5);"
        );

        contactCard.getChildren().addAll(
            contactTitle,
            contactText,
            messageArea,
            sendButton
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
            faqTitle,
            faqBox,
            contactCard
        );

        BorderPane root =
            new BorderPane();

        root.setCenter(content);



        Scene scene =
            new Scene(root, 1450, 850);

        stage.setTitle(
            "SkillVerse - Help & Support"
        );

        stage.setScene(scene);
        stage.show();
    }

    private VBox createFaq(
        String question,
        String answer
    ) {

        Label questionLabel =
            new Label(question);

        questionLabel.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label answerLabel =
            new Label(answer);

        answerLabel.setWrapText(true);

        answerLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox faq =
            new VBox(6);

        faq.setPadding(
            new Insets(16)
        );

        faq.setStyle(
            "-fx-background-color: rgba(255,255,255,0.88);" +
            "-fx-background-radius: 14;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 14;"
        );

        faq.getChildren().addAll(
            questionLabel,
            answerLabel
        );

        return faq;
    }
}