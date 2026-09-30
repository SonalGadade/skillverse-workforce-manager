package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;
import com.skillverse.manager.model.InterviewModel;
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

public class Interviews {

    private Scene interviewsScene;

    private List<Button> interviewButtons = new ArrayList<>();

    public Scene getInterviewsScene(List<InterviewModel> interviewList, Runnable callBackActionDashboard) {

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #F7FAFF;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 28, 18, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-width: 0 0 1 0;");

        Text heading = new Text("Interviews");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("← Dashboard");
        backButton.setStyle("-fx-background-color: #EAF2FF;-fx-text-fill: #2563EB;-fx-background-radius: 20px;-fx-padding: 9px 18px;-fx-font-size: 12px;-fx-font-weight: bold;-fx-cursor: hand;");
        backButton.setOnAction(event -> {
            callBackActionDashboard.run();

        });

        topBar.getChildren().addAll(heading, spacer, backButton);

        VBox content = new VBox(18);
        content.setPadding(new Insets(25, 70, 35, 70));

        VBox introBox = new VBox(5);

        Text pageTitle = new Text("Interviews");
        pageTitle.setFill(Color.web("#0F172A"));
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        Text description = new Text("Technical interviews and candidate evaluations");
        description.setFill(Color.web("#64748B"));
        description.setFont(Font.font("Arial", 12));

        introBox.getChildren().addAll(pageTitle, description);

        content.getChildren().add(introBox);

        for (InterviewModel interview : interviewList) {
            content.getChildren().add(createInterview(interview));

        }

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F7FAFF;-fx-background: #F7FAFF;-fx-border-color: transparent;");

        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane);

        interviewsScene = new Scene(mainLayout, 1200, 700);
        return interviewsScene;
    }

    private HBox createInterview(InterviewModel interview) {
        HBox card = new HBox(18);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 16px;-fx-background-radius: 16px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.08), 15, 0.15, 0, 4);");

        VBox candidateInfo = new VBox(5);

        Text candidateText = new Text(interview.getCandidate());
        candidateText.setFill(Color.web("#0F172A"));
        candidateText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Text positionText = new Text(interview.getPosition());
        positionText.setFill(Color.web("#64748B"));
        positionText.setFont(Font.font("Arial", 12));

        candidateInfo.getChildren().addAll(candidateText, positionText);

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox schedule = new VBox(4);

        Text timeText = new Text(interview.getTime());
        timeText.setFill(Color.web("#2563EB"));
        timeText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Text roundText = new Text(interview.getRound());
        roundText.setFill(Color.web("#64748B"));
        roundText.setFont(Font.font("Arial", 11));

        schedule.getChildren().addAll(timeText, roundText);

        Button evaluateButton = new Button("Evaluate");
        evaluateButton.setStyle("-fx-background-color: #2563EB;-fx-text-fill: white;-fx-background-radius: 15px;-fx-padding: 8px 15px;-fx-font-size: 11px;-fx-font-weight: bold;-fx-cursor: hand;");

        interviewButtons.add(evaluateButton);
        card.getChildren().addAll(candidateInfo, spacer, schedule, evaluateButton);
        return card;
    }
}