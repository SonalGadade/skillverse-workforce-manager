package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;

import com.skillverse.manager.model.GoalsKPIModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GoalsKPIs {

    private Scene goalsScene;

    private List<Button> goalButtons = new ArrayList<>();

    public Scene getGoalsKPIsScene(List<GoalsKPIModel> goalList, Runnable callBackActionDashboard) {

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #F7FAFF;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 28, 18, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-width: 0 0 1 0;");

        Text heading = new Text("Goals & KPIs");
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

        Text pageTitle = new Text("Goals & KPIs");
        pageTitle.setFill(Color.web("#0F172A"));
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        Text pageDescription = new Text("Track team objectives, KPIs and progress");
        pageDescription.setFill(Color.web("#64748B"));
        pageDescription.setFont(Font.font("Arial", 12));

        introBox.getChildren().addAll(pageTitle, pageDescription);
        content.getChildren().add(introBox);

        for (GoalsKPIModel goal : goalList) {
            content.getChildren().add(createGoal(goal));

        }

        mainLayout.setTop(topBar);
        mainLayout.setCenter(content);

        goalsScene = new Scene(mainLayout, 1200, 700);
        return goalsScene;
    }

    private VBox createGoal(GoalsKPIModel goal) {

        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 16px;-fx-background-radius: 16px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.08), 15, 0.15, 0, 4);");

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER_LEFT);

        Text goalText = new Text(goal.getGoal());
        goalText.setFill(Color.web("#0F172A"));
        goalText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        Text percentageText = new Text(goal.getPercentage());
        percentageText.setFill(Color.web("#2563EB"));
        percentageText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        top.getChildren().addAll(goalText, spacer, percentageText);

        Text employeeText = new Text("Assigned to " + goal.getEmployee());
        employeeText.setFill(Color.web("#64748B"));
        employeeText.setFont(Font.font("Arial", 12));

        ProgressBar progressBar = new ProgressBar(goal.getProgress());
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.setPrefHeight(8);
        progressBar.setStyle("-fx-accent: #3B82F6;-fx-background-color: #E2E8F0;");

        Button reviewButton = new Button("Review Goal");
        reviewButton.setStyle("-fx-background-color: #EAF2FF;-fx-text-fill: #2563EB;-fx-background-radius: 15px;-fx-border-color: #D6E5FF;-fx-border-radius: 15px;-fx-padding: 7px 14px;-fx-font-size: 11px;-fx-font-weight: bold;-fx-cursor: hand;");

        goalButtons.add(reviewButton);

        card.getChildren().addAll(top, employeeText, progressBar, reviewButton);
        return card;
    }

    public Scene getGoalsKPIsScene() {
        return goalsScene;
    }
}