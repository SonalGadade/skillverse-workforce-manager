package com.skillverse.manager.view;

import java.util.ArrayList;
import java.util.List;

import com.skillverse.manager.model.FeedModel;

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

public class Feed {

    private Scene feedScene;

    private List<Button> actionButtons = new ArrayList<>();

    public Scene getFeedScene(List<FeedModel> postList, Runnable callBackActionDashboard) {

        BorderPane mainLayout = new BorderPane();

        mainLayout.setStyle("-fx-background-color: #F7FAFF;");

        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 28, 18, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-width: 0 0 1 0;");

        HBox brandBox = new HBox(10);
        brandBox.setAlignment(Pos.CENTER_LEFT);

        Text logo = new Text("SV");
        logo.setFill(Color.web("#3B82F6"));
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        Text heading = new Text("Employee Feed");
        heading.setFill(Color.web("#0F172A"));
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        brandBox.getChildren().addAll(logo, heading);

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backButton = new Button("← Dashboard");
        backButton.setStyle("-fx-background-color: #EAF2FF;-fx-text-fill: #2563EB;-fx-background-radius: 20px;-fx-padding: 9px 18px;-fx-font-size: 12px;-fx-font-weight: bold;-fx-cursor: hand;");

        backButton.setOnAction(event -> {
            callBackActionDashboard.run();

        });

        topBar.getChildren().addAll(brandBox, spacer, backButton);

        VBox feedContent = new VBox(18);
        feedContent.setPadding(new Insets(25, 70, 35, 70));

        VBox introBox = new VBox(5);

        Text feedTitle = new Text("Employee Feed");
        feedTitle.setFill(Color.web("#0F172A"));
        feedTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Text feedSubtitle = new Text("Stay connected with your team and celebrate their progress.");
        feedSubtitle.setFill(Color.web("#64748B"));
        feedSubtitle.setFont(Font.font("Arial", 12));

        introBox.getChildren().addAll(feedTitle, feedSubtitle);

        VBox createPost = new VBox(15);
        createPost.setPadding(new Insets(20));
        createPost.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 16px;-fx-background-radius: 16px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.08), 15, 0.15, 0, 4);");

        Text createPostTitle = new Text("Share something with your team");
        createPostTitle.setFill(Color.web("#0F172A"));
        createPostTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        HBox postActions = new HBox(12);

        Button updateButton = new Button("Project Update");

        Button achievementButton = new Button("Achievement");

        Button learningButton = new Button("Learning Progress");

        actionButtons.clear();
        actionButtons.add(updateButton);
        actionButtons.add(achievementButton);
        actionButtons.add(learningButton);

        for (Button button : actionButtons) {
            button.setStyle("-fx-background-color: #EAF2FF;-fx-text-fill: #2563EB;-fx-background-radius: 18px;-fx-border-color: #D6E5FF;-fx-border-radius: 18px;-fx-padding: 8px 14px;-fx-font-size: 11px;-fx-font-weight: bold;-fx-cursor: hand;");

        }

        postActions.getChildren().addAll(updateButton, achievementButton, learningButton);
        createPost.getChildren().addAll(createPostTitle, postActions);
        feedContent.getChildren().addAll(introBox, createPost);

        for (FeedModel post : postList) {
            VBox postCard = createPostCard(post);
            feedContent.getChildren().add(postCard);

        }

        ScrollPane scrollPane = new ScrollPane(feedContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: #F7FAFF;-fx-background-color: #F7FAFF;-fx-border-color: transparent;");

        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane);

        feedScene = new Scene(mainLayout, 1200, 700);
        return feedScene;
    }

    private VBox createPostCard(FeedModel post) {

        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-radius: 16px;-fx-background-radius: 16px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.08), 15, 0.15, 0, 4);");

        Text nameText = new Text(post.getName());
        nameText.setFill(Color.web("#0F172A"));
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Text roleText = new Text(post.getRole() + "  •  " + post.getTime());
        roleText.setFill(Color.web("#64748B"));
        roleText.setFont(Font.font("Arial", 11));

        Text postText = new Text(post.getPost());
        postText.setFill(Color.web("#334155"));
        postText.setFont(Font.font("Arial", 14));
        postText.setWrappingWidth(850);

        HBox actions = new HBox(20);

        Button likeButton = new Button("♡ " + post.getLikes() + " Likes");

        Button commentButton = new Button("○ " + post.getComments() + " Comments");

        Button saveButton = new Button("▱ Save");

        for (Button button : new Button[] {
                likeButton, commentButton, saveButton}) {

            button.setStyle("-fx-background-color: transparent;-fx-text-fill: #2563EB;-fx-cursor: hand;-fx-font-size: 12px;-fx-padding: 4px;");

        }

        actions.getChildren().addAll(likeButton, commentButton, saveButton);

        card.getChildren().addAll(nameText, roleText, postText, actions);
        return card;
    }

    public Scene getFeedScene() {
        return feedScene;
        }
}