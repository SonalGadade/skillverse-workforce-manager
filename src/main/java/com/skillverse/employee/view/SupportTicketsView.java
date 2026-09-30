package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SupportTicketsView {

    public void show(Stage stage) {

        Label title = new Label("Support & Help Desk");

        title.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label(
            "Raise support requests and track your submitted tickets"
        );

        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #64748B;"
        );

        Label createTitle = new Label(
            "Create Support Request"
        );

        createTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        TextField subjectField = new TextField();

        subjectField.setPromptText(
            "Enter issue subject"
        );

        subjectField.setPrefHeight(42);

        subjectField.setStyle(
            "-fx-background-color: #F8FAFC;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 0 12;"
        );

        ComboBox<String> categoryBox =
            new ComboBox<>();

        categoryBox.getItems().addAll(
            "IT Support",
            "HR Support",
            "Payroll",
            "Leave",
            "Account Access",
            "Other"
        );

        categoryBox.setPromptText(
            "Select Category"
        );

        categoryBox.setPrefHeight(42);
        categoryBox.setPrefWidth(300);

        TextArea descriptionArea =
            new TextArea();

        descriptionArea.setPromptText(
            "Describe your issue..."
        );

        descriptionArea.setPrefHeight(100);
        descriptionArea.setWrapText(true);

        descriptionArea.setStyle(
            "-fx-background-color: #F8FAFC;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 10;"
        );

        Button submitButton =
            new Button("Submit Request");

        submitButton.setPrefHeight(43);
        submitButton.setPrefWidth(170);

        submitButton.setStyle(
            "-fx-background-color: #2563EB;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        );

        VBox createCard =
            new VBox(14);

        createCard.setPadding(
            new Insets(22)
        );

        createCard.setMaxWidth(850);

        createCard.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #DCE8FA;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 18, 0, 0, 5);"
        );

        createCard.getChildren().addAll(
            createTitle,
            subjectField,
            categoryBox,
            descriptionArea,
            submitButton
        );

        Label ticketsTitle =
            new Label("My Support Tickets");

        ticketsTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        VBox ticket1 = createTicketCard(
            "TCK-2026-001",
            "Unable to access training portal",
            "IT Support",
            "17 August 2026",
            "Open",
            "#2563EB"
        );

        VBox ticket2 = createTicketCard(
            "TCK-2026-002",
            "Leave balance clarification",
            "HR Support",
            "12 August 2026",
            "Resolved",
            "#16A34A"
        );

        VBox ticket3 = createTicketCard(
            "TCK-2026-003",
            "Payslip download issue",
            "Payroll",
            "08 August 2026",
            "In Progress",
            "#EA580C"
        );

        VBox ticketsBox =
            new VBox(12);

        ticketsBox.getChildren().addAll(
            ticket1,
            ticket2,
            ticket3
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
            createCard,
            ticketsTitle,
            ticketsBox
        );

        BorderPane root =
            new BorderPane();

        root.setCenter(content);



        Scene scene =
            new Scene(root, 1450, 850);

        stage.setTitle(
            "SkillVerse - Support & Help Desk"
        );

        stage.setScene(scene);
        stage.show();
    }

    private VBox createTicketCard(
        String ticketId,
        String subject,
        String category,
        String date,
        String status,
        String textColor
    ) {

        Label idLabel =
            new Label(ticketId);

        idLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        Label subjectLabel =
            new Label(subject);

        subjectLabel.setWrapText(true);

        subjectLabel.setStyle(
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

        Label dateLabel =
            new Label(
                "Created: " + date
            );

        dateLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #94A3B8;"
        );

        Label statusLabel =
            new Label(status);

        statusLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        Button viewButton =
            new Button("View Ticket");

        viewButton.setPrefHeight(36);

        viewButton.setStyle(
            "-fx-background-color: #F1F5FF;" +
            "-fx-text-fill: " + textColor + ";" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 9;" +
            "-fx-cursor: hand;"
        );

        HBox bottomRow =
            new HBox(20);

        bottomRow.setAlignment(
            Pos.CENTER_LEFT
        );

        bottomRow.getChildren().addAll(
            categoryLabel,
            dateLabel,
            statusLabel,
            viewButton
        );

        VBox card =
            new VBox(10);

        card.setPadding(
            new Insets(18)
        );

        card.setMaxWidth(850);

        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 16;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 16, 0, 0, 4);"
        );

        card.getChildren().addAll(
            idLabel,
            subjectLabel,
            bottomRow
        );

        return card;
    }
}