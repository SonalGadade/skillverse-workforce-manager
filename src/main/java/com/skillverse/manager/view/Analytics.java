package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Analytics {

    private Scene analyticsScene;



    public Scene getAnalyticsScene(Runnable callBackActionDashboard) {

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #F7FAFF;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 28, 18, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-width: 0 0 1 0;");

        Text heading = new Text("Analytics");
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

        VBox content = new VBox(20);
        content.setPadding(new Insets(25, 50, 35, 50));

        VBox intro = new VBox(5);

        Text introHeading = new Text("Team Analytics");
        introHeading.setFill(Color.web("#0F172A"));
        introHeading.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        Text introSubtitle = new Text("Monitor team performance, skills and overall progress.");
        introSubtitle.setFill(Color.web("#64748B"));
        introSubtitle.setFont(Font.font("Arial", 12));
        intro.getChildren().addAll(introHeading, introSubtitle);

        HBox stats = new HBox(18);

        VBox performanceStat = createStat("Team Performance", "88%");
        VBox trainingStat = createStat("Training Completion", "76%");
        VBox goalStat = createStat("Goal Completion", "79%");
        VBox promotionStat = createStat("Promotion Ready", "5");

        HBox.setHgrow(performanceStat, Priority.ALWAYS);
        HBox.setHgrow(trainingStat, Priority.ALWAYS);
        HBox.setHgrow(goalStat, Priority.ALWAYS);
        HBox.setHgrow(promotionStat, Priority.ALWAYS);

        stats.getChildren().addAll(performanceStat, trainingStat, goalStat, promotionStat);

        HBox charts = new HBox(20);

        VBox skillCard = createSkillChart();
        VBox performanceCard = createPerformanceSummary();

        HBox.setHgrow(skillCard, Priority.ALWAYS);
        HBox.setHgrow(performanceCard, Priority.ALWAYS);

        skillCard.setPrefWidth(520);
        performanceCard.setPrefWidth(520);

        charts.getChildren().addAll(skillCard, performanceCard);

        content.getChildren().addAll(intro, stats, charts);

        mainLayout.setTop(topBar);
        mainLayout.setCenter(content);

        analyticsScene = new Scene(mainLayout, 1200, 700);
        return analyticsScene;
    }

    private VBox createStat(String title, String value) {

        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 16px;-fx-background-radius: 16px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.08), 15, 0.15, 0, 4);");

        Text titleText = new Text(title);
        titleText.setFill(Color.web("#64748B"));
        titleText.setFont(Font.font("Arial", 11));

        Text valueText = new Text(value);
        valueText.setFill(Color.web("#2563EB"));
        valueText.setFont(Font.font("Arial", FontWeight.BOLD, 26));

        card.getChildren().addAll(titleText, valueText);
        return card;
    }

    private VBox createSkillChart() {

        VBox card = new VBox(12);
        card.setPadding(new Insets(22));
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 18px;-fx-background-radius: 18px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.08), 15, 0.15, 0, 4);");

        Text heading = new Text("Team Skill Distribution");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Text subtitle = new Text("Current skill distribution across the team");
        subtitle.setFill(Color.web("#64748B"));
        subtitle.setFont(Font.font("Arial", 11));

        PieChart chart = new PieChart();

        chart.getData().addAll(
            new PieChart.Data("Java", 35),
            new PieChart.Data("Cloud", 25),
            new PieChart.Data("AI/ML", 20),
            new PieChart.Data("Other", 20)
        );

        chart.setLegendVisible(true);
        chart.setLabelsVisible(true);
        chart.setStyle("-fx-background-color: transparent;");

        VBox.setVgrow(chart, Priority.ALWAYS);

        card.getChildren().addAll(heading, subtitle, chart);
        return card;
    }

    private VBox createPerformanceSummary() {

        VBox card = new VBox(18);
        card.setPadding(new Insets(22));
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 18px;-fx-background-radius: 18px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.08), 15, 0.15, 0, 4);");

        Text heading = new Text("Team Performance Summary");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Text subtitle = new Text("Overview of your team's current performance");
        subtitle.setFill(Color.web("#64748B"));
        subtitle.setFont(Font.font("Arial", 11));

        card.getChildren().addAll(heading,subtitle,
            createMetric("Productivity", "92%"),
            createMetric("Learning", "76%"),
            createMetric("Goal Achievement", "88%"),
            createMetric("Engagement", "84%")
        );

        return card;
    }

    private HBox createMetric(String name, String value) {

        HBox row = new HBox();

        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 10, 12, 10));
        row.setStyle("-fx-background-color: #F8FAFC;-fx-background-radius: 10px;-fx-border-color: #EEF2F7;-fx-border-radius: 10px;");

        Text nameText = new Text(name);
        nameText.setFill(Color.web("#475569"));
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Text valueText = new Text(value);
        valueText.setFill(Color.web("#16A34A"));
        valueText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(nameText, spacer, valueText);

        return row;
    }
}