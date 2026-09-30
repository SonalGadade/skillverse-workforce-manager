package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;

import com.skillverse.manager.model.AnonymousModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Anonymous {

    private Scene anonymousFeedbackScene;

    private List<Button> anonymousButtons = new ArrayList<>();

    public Scene getAnonymousFeedbackScene(List<AnonymousModel> feedbackList, Runnable callBackActionDashboard) {

        BorderPane mainLayout = new BorderPane();

        mainLayout.setStyle("-fx-background-color: #F7FAFF;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 28, 18, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF;" + "-fx-border-color: #E2E8F0;" + "-fx-border-width: 0 0 1 0;");

        Text heading = new Text("Anonymous Feedback");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("← Dashboard");
        backButton.setStyle("-fx-background-color: #EAF2FF;" + "-fx-text-fill: #2563EB;" + "-fx-background-radius: 20px;" + "-fx-padding: 9px 18px;" + "-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-cursor: hand;");

        backButton.setOnAction(event -> {
            callBackActionDashboard.run();
        });

        topBar.getChildren().addAll(heading, spacer, backButton);

        VBox content = new VBox(18);
        content.setPadding(new Insets(25, 70, 35, 70));

        VBox introBox = new VBox(5);
        Text pageTitle = new Text("Anonymous Feedback");
        pageTitle.setFill(Color.web("#0F172A"));
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        Text description = new Text("Anonymous employee feedback visible to management");
        description.setFill(Color.web("#64748B"));
        description.setFont(Font.font("Arial", 12));

        introBox.getChildren().addAll(pageTitle, description);

        VBox privacyCard = new VBox(8);
        privacyCard.setPadding(new Insets(18));
        privacyCard.setStyle("-fx-background-color: #EAF2FF;" + "-fx-border-color: #D6E5FF;" + "-fx-border-radius: 15px;" + "-fx-background-radius: 15px;");

        Text privacyTitle = new Text("Privacy Protected");
        privacyTitle.setFill(Color.web("#2563EB"));
        privacyTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Text privacyText = new Text("Employee identity is hidden. Feedback is shown only for improvement and organizational development.");
        privacyText.setFill(Color.web("#475569"));
        privacyText.setFont(Font.font("Arial", 12));
        privacyText.setWrappingWidth(850);
        privacyCard.getChildren().addAll(privacyTitle, privacyText);

        content.getChildren().addAll(introBox, privacyCard);

        for (AnonymousModel feedback : feedbackList) {
            VBox feedbackCard = createAnonymousCard(feedback);
            content.getChildren().add(feedbackCard);
        }

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F7FAFF;" + "-fx-background: #F7FAFF;" + "-fx-border-color: transparent;");

        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane);

        anonymousFeedbackScene = new Scene(mainLayout, 1200, 700);
        return anonymousFeedbackScene;
    }

    private VBox createAnonymousCard(AnonymousModel feedback) {

        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF;" + "-fx-border-color: #E2E8F0;" + "-fx-border-radius: 16px;" + "-fx-background-radius: 16px;" + "-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.08), 15, 0.15, 0, 4);");

        HBox heading = new HBox();
        heading.setAlignment(Pos.CENTER_LEFT);

        Text categoryText = new Text(feedback.getCategory());
        categoryText.setFill(Color.web("#2563EB"));
        categoryText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        Text timeText = new Text(feedback.getTime());
        timeText.setFill(Color.web("#94A3B8"));
        timeText.setFont(Font.font("Arial", 11));

        heading.getChildren().addAll(categoryText, spacer, timeText);

        Text messageText = new Text(feedback.getMessage());
        messageText.setFill(Color.web("#334155"));
        messageText.setFont(Font.font("Arial", 14));
        messageText.setWrappingWidth(850);

        Button acknowledgeButton = new Button("Acknowledge");
        acknowledgeButton.setStyle("-fx-background-color: #EAF2FF;" + "-fx-text-fill: #2563EB;" + "-fx-background-radius: 15px;" + "-fx-border-color: #D6E5FF;" + "-fx-border-radius: 15px;" + "-fx-padding: 7px 14px;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-cursor: hand;");

        anonymousButtons.add(acknowledgeButton);

        acknowledgeButton.setOnAction(event -> {
            feedback.setAcknowledged(true);
            acknowledgeButton.setText("Acknowledged");
            acknowledgeButton.setDisable(true);
        });

        if (feedback.isAcknowledged()) {
            acknowledgeButton.setText("Acknowledged");
            acknowledgeButton.setDisable(true);
        }

        card.getChildren().addAll(heading, messageText, acknowledgeButton);

        return card;
    }
}