package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.shape.Circle;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MyTeam {

    private Scene myTeamScene;

    private List<Button> employeeButtons = new ArrayList<>();

    public Scene getMyTeamScene(Runnable callBackActionDashboard) {

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #07101F;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 28, 18, 28));
        topBar.setStyle("-fx-background-color: #0A1428;-fx-border-color: #16233D;-fx-border-width: 0 0 1 0;");

        Text heading = new Text("My Team");
        heading.setFill(Color.WHITE);
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("← Dashboard");
        backButton.setStyle("-fx-background-color: #12336B;-fx-text-fill: white;-fx-background-radius: 20px;-fx-padding: 9px 18px;-fx-cursor: hand;");

        backButton.setOnAction(event -> {
            callBackActionDashboard.run();
        });

        topBar.getChildren().addAll(heading, spacer, backButton);

        VBox content = new VBox(20);
        content.setPadding(new Insets(25, 35, 35, 35));

        HBox stats = new HBox(18);

        stats.getChildren().addAll(
                createStat("Team Members", "14"),
                createStat("Active", "12"),
                createStat("On Leave", "2"),
                createStat("Avg Performance", "88%"));

        VBox membersCard = new VBox(15);
        membersCard.setPadding(new Insets(22));
        membersCard.setStyle("-fx-background-color: #0E1830;-fx-border-color: #1A2A4A;-fx-border-radius: 18px;-fx-background-radius: 18px;");

        Text membersHeading = new Text("Team Members");
        membersHeading.setFill(Color.WHITE);
        membersHeading.setFont(Font.font("Arial", FontWeight.BOLD, 17));

        VBox members = new VBox(12);

        members.getChildren().addAll(
                createMember("Priya Sharma", "Sr. Engineer", "94%", "Active"),
                createMember("Rahul Verma", "Data Scientist", "87%", "Active"),
                createMember("Sneha Joshi", "Product Designer", "91%", "On Leave"),
                createMember("Amit Kumar", "Software Engineer", "82%", "Active"),
                createMember("Neha Patil", "Frontend Developer", "89%", "Active"));

        membersCard.getChildren().addAll(membersHeading, members);

        content.getChildren().addAll(stats, membersCard);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #07101F;-fx-background: #07101F;");

        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane);

        myTeamScene = new Scene(mainLayout, 1200, 750);

        return myTeamScene;
    }

    private VBox createStat(String title, String value) {

        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setPrefWidth(220);
        card.setStyle("-fx-background-color: #0E1830;-fx-border-color: #1A2A4A;-fx-border-radius: 16px;-fx-background-radius: 16px;");

        Text titleText = new Text(title);
        titleText.setFill(Color.web("#94A3B8"));

        Text valueText = new Text(value);
        valueText.setFill(Color.web("#60A5FA"));
        valueText.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        card.getChildren().addAll(titleText, valueText);

        return card;
    }

    private HBox createMember(String name, String role, String performance, String status) {

        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(13));
        row.setStyle("-fx-background-color: #101B35;-fx-background-radius: 14px;");

        Circle circle = new Circle(20);
        circle.setFill(Color.web("#2563EB"));

        Text initials = new Text(name.substring(0, 2).toUpperCase());
        initials.setFill(Color.WHITE);
        initials.setFont(Font.font("Arial", FontWeight.BOLD, 11));

        StackPane avatar = new StackPane();
        avatar.getChildren().addAll(circle, initials);

        VBox info = new VBox(3);

        Text nameText = new Text(name);
        nameText.setFill(Color.WHITE);
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Text roleText = new Text(role);
        roleText.setFill(Color.web("#64748B"));

        info.getChildren().addAll(nameText, roleText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Text performanceText = new Text(performance);
        performanceText.setFill(Color.web("#10B981"));
        performanceText.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Text statusText = new Text(status);
        statusText.setFill(status.equals("On Leave") ? Color.web("#F59E0B") : Color.web("#10B981"));

        Button viewButton = new Button("View Profile");
        viewButton.setStyle("-fx-background-color: #12336B;-fx-text-fill: #93C5FD;-fx-background-radius: 15px;-fx-padding: 7px 12px;-fx-cursor: hand;");

        employeeButtons.add(viewButton);

        row.getChildren().addAll(avatar, info, spacer, performanceText, statusText, viewButton);

        return row;
    }

    public List<Button> getEmployeeButtons() {
        return employeeButtons;
    }
}