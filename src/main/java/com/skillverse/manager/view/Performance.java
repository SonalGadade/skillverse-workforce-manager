package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

public class Performance {

    private Scene performanceScene;

    private List<Button> performanceButtons = new ArrayList<>();

    private Button backButton;

    public Scene getPerformanceScene() {

        BorderPane mainLayout = new BorderPane();

        mainLayout.setStyle("-fx-background-color: #07101F;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 28, 18, 28));
        topBar.setStyle("-fx-background-color: #0A1428;-fx-border-color: #16233D;-fx-border-width: 0 0 1 0;");

        Text heading = new Text("Performance");
        heading.setFill(Color.WHITE);
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        backButton = new Button("← Dashboard");
        backButton.setStyle("-fx-background-color: #12336B;-fx-text-fill: white;-fx-background-radius: 20px;-fx-padding: 9px 18px;-fx-cursor: hand;");

        topBar.getChildren().addAll(heading, spacer, backButton);

        VBox content = new VBox(20);
        content.setPadding(new Insets(25, 35, 35, 35));

        HBox stats = new HBox(18);

        stats.getChildren().addAll(
                createStat("Average Performance", "88%"),
                createStat("Above Target", "9"),
                createStat("Needs Attention", "3"));

        VBox chartCard = new VBox(15);
        chartCard.setPadding(new Insets(22));
        chartCard.setStyle("-fx-background-color: #0E1830;-fx-border-color: #1A2A4A;-fx-border-radius: 18px;-fx-background-radius: 18px;");

        Text chartHeading = new Text("Employee Performance");
        chartHeading.setFill(Color.WHITE);
        chartHeading.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        CategoryAxis xAxis = new CategoryAxis();

        NumberAxis yAxis = new NumberAxis(0, 100, 20);

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setAnimated(false);
        chart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("Priya", 94));
        series.getData().add(new XYChart.Data<>("Rahul", 87));
        series.getData().add(new XYChart.Data<>("Sneha", 91));
        series.getData().add(new XYChart.Data<>("Amit", 82));
        series.getData().add(new XYChart.Data<>("Neha", 89));

        chart.getData().add(series);

        VBox.setVgrow(chart, Priority.ALWAYS);
        chartCard.getChildren().addAll(chartHeading, chart);
        content.getChildren().addAll(stats, chartCard);

        mainLayout.setTop(topBar);
        mainLayout.setCenter(content);

        performanceScene = new Scene(mainLayout, 1200, 750);
        return performanceScene;
    }

    public Button getBackButton() {
        return backButton;
    }

    private VBox createStat(String title, String value) {

        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setPrefWidth(260);
        card.setStyle("-fx-background-color: #0E1830;-fx-border-color: #1A2A4A;-fx-border-radius: 16px;-fx-background-radius: 16px;");

        Text titleText = new Text(title);
        titleText.setFill(Color.web("#94A3B8"));

        Text valueText = new Text(value);
        valueText.setFill(Color.web("#3B82F6"));
        valueText.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        card.getChildren().addAll(titleText, valueText);
        return card;
    }
}