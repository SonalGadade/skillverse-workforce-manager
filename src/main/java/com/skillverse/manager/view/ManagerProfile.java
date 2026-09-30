package com.skillverse.manager.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public class ManagerProfile {

    public static String profileName = "Manager";
    public static String username = "manager";
    public static String bio = "manager in pvt limited";
    public static String email = "manager@skillverse.com";
    public static Image profileImage = null;

    private Scene managerProfileScene;

    private Button editProfileButton;

    public Scene getManagerProfileScene() {

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #F7F8FC;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12, 28, 12, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-width: 0 0 1 0;");

        StackPane logo = new StackPane();
        logo.setPrefSize(38, 38);

        Circle logoCircle = new Circle(18);
        logoCircle.setFill(Color.web("#2563EB"));

        Text logoText = new Text("S");
        logoText.setFill(Color.WHITE);
        logoText.setFont(Font.font("Arial", FontWeight.BOLD, 17));

        logo.getChildren().addAll(logoCircle, logoText);

        Text skillVerseText = new Text("SkillVerse");
        skillVerseText.setFill(Color.web("#0F172A"));
        skillVerseText.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        HBox logoBox = new HBox(8);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.getChildren().addAll(logo, skillVerseText);

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        Text workspaceText = new Text("MANAGER WORKSPACE");
        workspaceText.setFill(Color.web("#94A3B8"));
        workspaceText.setFont(Font.font("Arial", FontWeight.BOLD, 9));

        topBar.getChildren().addAll(logoBox, topSpacer, workspaceText);

        VBox content = new VBox(18);
        content.setPadding(new Insets(28, 45, 40, 45));
        content.setStyle("-fx-background-color: #F7F8FC;");

        VBox profileCard = new VBox();
        profileCard.setPadding(new Insets(32));
        profileCard.setPrefHeight(270);
        profileCard.setStyle("-fx-background-color: #FFFFFF;-fx-background-radius: 18px;-fx-border-color: #E2E8F0;-fx-border-radius: 18px;");

        HBox profileContent = new HBox(30);
        profileContent.setAlignment(Pos.CENTER_LEFT);

        Circle profileCircle = new Circle(65);
        profileCircle.setFill(Color.web("#EAF2FF"));

        Text profileInitials = new Text("HM");
        profileInitials.setFill(Color.web("#2563EB"));
        profileInitials.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        StackPane profileAvatar = new StackPane();
        profileAvatar.getChildren().addAll(profileCircle, profileInitials);

        VBox profileInfo = new VBox(8);

        Text profileName = new Text(ManagerProfile.profileName);
        profileName.setFill(Color.web("#0F172A"));
        profileName.setFont(Font.font("Arial", FontWeight.BOLD, 26));

        Circle verifiedCircle = new Circle(10, Color.web("#2563EB"));

        Text check = new Text("✓");
        check.setFill(Color.WHITE);
        check.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        StackPane verified = new StackPane();
        verified.getChildren().addAll(verifiedCircle, check);

        HBox nameRow = new HBox(8);
        nameRow.setAlignment(Pos.CENTER_LEFT);
        nameRow.getChildren().addAll(profileName, verified);

        Text username = new Text(ManagerProfile.username);
        username.setFill(Color.web("#2563EB"));
        username.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Text bioName = new Text(ManagerProfile.bio);
        bioName.setFill(Color.web("#64748B"));
        bioName.setFont(Font.font("Arial", 11));

        HBox stats = new HBox(35);
        stats.getChildren().addAll(
                createProfileStat("18", "Posts"),
                createProfileStat("248", "Followers"),
                createProfileStat("126", "Following"));

        editProfileButton = new Button("Edit Profile");
        editProfileButton.setPrefHeight(42);
        editProfileButton.setPadding(new Insets(8, 24, 8, 24));
        editProfileButton.setStyle("-fx-background-color: #2563EB;-fx-text-fill: white;-fx-background-radius: 8px;-fx-font-size: 11px;-fx-font-weight: bold;-fx-cursor: hand;");

        Button shareProfileButton = new Button("Share Profile");
        shareProfileButton.setPrefHeight(42);
        shareProfileButton.setPadding(new Insets(8, 24, 8, 24));
        shareProfileButton.setStyle("-fx-background-color: #EAF2FF;-fx-text-fill: #2563EB;-fx-background-radius: 8px;-fx-font-size: 11px;-fx-font-weight: bold;-fx-cursor: hand;");

        HBox profileButtons = new HBox(10);
        profileButtons.getChildren().addAll(editProfileButton, shareProfileButton);

        profileInfo.getChildren().addAll(nameRow, username, bioName, stats, profileButtons);

        profileContent.getChildren().addAll(profileAvatar, profileInfo);

        profileCard.getChildren().add(profileContent);

        VBox aboutCard = createSectionCard("About", "Manageer in pvt limited");

        VBox skillsCard = new VBox(15);
        skillsCard.setPadding(new Insets(25));
        skillsCard.setStyle("-fx-background-color: #FFFFFF;-fx-background-radius: 16px;-fx-border-color: #E2E8F0;-fx-border-radius: 16px;");

        Text skillsTitle = new Text("Skills");
        skillsTitle.setFill(Color.web("#0F172A"));
        skillsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        FlowPane skillsPane = new FlowPane();
        skillsPane.setHgap(10);
        skillsPane.setVgap(10);

        String[] skills = {"Leadership", "Recruitment", "Team Management", "Communication", "Employee Relations", "Training"};

        for (String skill : skills) {
            skillsPane.getChildren().add(createSkillTag(skill));
        }

        skillsCard.getChildren().addAll(skillsTitle, skillsPane);

        HBox postsHeading = new HBox(8);

        Text postsTitle = new Text("Posts");
        postsTitle.setFill(Color.web("#0F172A"));
        postsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Text postCount = new Text("  ");
        postCount.setFill(Color.web("#94A3B8"));
        postCount.setFont(Font.font("Arial", 11));

        postsHeading.setAlignment(Pos.CENTER_LEFT);
        postsHeading.getChildren().addAll(postsTitle, postCount);

        content.getChildren().addAll(profileCard, aboutCard, skillsCard, postsHeading);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F7F8FC;-fx-background: #F7F8FC;");

        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane);

        managerProfileScene = new Scene(mainLayout, 1200, 700);

        return managerProfileScene;
    }

    public Button getEditProfileButton() {
        return editProfileButton;
    }

    private VBox createProfileStat(String number, String label) {

        VBox box = new VBox(3);

        Text numberText = new Text(number);
        numberText.setFill(Color.web("#0F172A"));
        numberText.setFont(Font.font("Arial", FontWeight.BOLD, 17));

        Text labelText = new Text(label);
        labelText.setFill(Color.web("#64748B"));
        labelText.setFont(Font.font("Arial", 10));

        box.getChildren().addAll(numberText, labelText);

        return box;
    }

    private VBox createSectionCard(String title, String text) {

        VBox card = new VBox(15);

        card.setPadding(new Insets(25));

        card.setStyle("-fx-background-color: #FFFFFF;-fx-background-radius: 16px;-fx-border-color: #E2E8F0;-fx-border-radius: 16px;");

        Text titleText = new Text(title);
        titleText.setFill(Color.web("#0F172A"));
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Text contentText = new Text(text);
        contentText.setFill(Color.web("#64748B"));
        contentText.setFont(Font.font("Arial", 11));

        card.getChildren().addAll(titleText, contentText);

        return card;
    }

    private Button createSkillTag(String skill) {

        Button tag = new Button(skill);

        tag.setPadding(new Insets(8, 15, 8, 15));

        tag.setStyle("-fx-background-color: #EAF2FF;-fx-text-fill: #2563EB;-fx-background-radius: 20px;-fx-font-size: 10px;-fx-font-weight: bold;");

        return tag;
    }
}