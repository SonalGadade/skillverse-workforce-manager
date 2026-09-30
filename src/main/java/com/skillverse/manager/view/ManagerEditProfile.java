package com.skillverse.manager.view;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
import javafx.stage.FileChooser;

public class ManagerEditProfile {

    private Scene editProfileScene;
    private Image selectedImage;
    private Button cancelButton;
    private Button saveButton;
    private Button changePhotoButton;
    private TextField nameField;
    private TextField usernameField;
    private TextArea bioField;
    private TextField emailField;
    private StackPaneWithCircle avatar;

    public Scene getManagerEditProfileScene() {

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #F7F8FC;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(14,28,14,28));
        topBar.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #E2E8F0;-fx-border-width: 0 0 1 0;");

        cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: transparent;-fx-text-fill: #334155;-fx-font-size: 12px;-fx-cursor: hand;");

        Text title = new Text("Edit Profile");

        title.setFill(Color.web("#0F172A"));
        title.setFont(Font.font("Arial",FontWeight.BOLD,16));

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer,Priority.ALWAYS);
        HBox.setHgrow(rightSpacer,Priority.ALWAYS);

        saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #2563EB;-fx-text-fill: white;-fx-background-radius: 7px;-fx-padding: 7px 18px;-fx-font-size: 11px;-fx-font-weight: bold;-fx-cursor: hand;");

        topBar.getChildren().addAll(cancelButton,leftSpacer,title,rightSpacer,saveButton);

        VBox content = new VBox(22);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(35,30,50,30));

        VBox photoSection = new VBox(10);
        photoSection.setAlignment(Pos.CENTER);

        Circle avatarCircle = new Circle(55);

        avatarCircle.setFill(Color.web("#EAF2FF"));

        Text avatarText = new Text(getInitials(ManagerProfile.profileName));
        avatarText.setFill(Color.web("#2563EB"));
        avatarText.setFont(Font.font("Arial",FontWeight.BOLD,27));

        avatar = new StackPaneWithCircle(avatarCircle,avatarText);

        if (ManagerProfile.profileImage != null) {
            ImageView existingImage = createProfileImage(ManagerProfile.profileImage,110);
            avatar.getChildren().clear();
            avatar.getChildren().add(existingImage);

        }

        changePhotoButton = new Button("Change Photo");
        changePhotoButton.setStyle("-fx-background-color: transparent;-fx-text-fill: #2563EB;-fx-font-size: 12px;-fx-font-weight: bold;-fx-cursor: hand;");

        changePhotoButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Photo");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg"));
            File file = fileChooser.showOpenDialog(editProfileScene.getWindow());

            if (file != null) {
                selectedImage = new Image(file.toURI().toString());

                ImageView newImage = createProfileImage(selectedImage,110);

                avatar.getChildren().clear();
                avatar.getChildren().add(newImage);
            }
        });

        photoSection.getChildren().addAll(avatar,changePhotoButton);

        VBox form = new VBox(18);

        form.setMaxWidth(600);

        Label nameLabel = createLabel("Name");
        nameField = createTextField(ManagerProfile.profileName);

        Label usernameLabel = createLabel("Username");
        usernameField = createTextField(ManagerProfile.username);

        Label bioLabel = createLabel("Bio");
        bioField = new TextArea(ManagerProfile.bio);
        bioField.setPrefRowCount(4);
        bioField.setWrapText(true);
        bioField.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #CBD5E1;-fx-border-radius: 8px;-fx-background-radius: 8px;-fx-font-size: 12px;-fx-text-fill: #0F172A;");

        Label emailLabel = createLabel("Email");
        emailField = createTextField(ManagerProfile.email);

        form.getChildren().addAll(nameLabel,nameField,usernameLabel,usernameField,bioLabel,bioField,emailLabel,emailField);

        content.getChildren().addAll(photoSection,form);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F7F8FC;-fx-background: #F7F8FC;");

        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane);

        editProfileScene = new Scene(mainLayout,1200,700);
        return editProfileScene;
    }

    private Label createLabel(String text) {

        Label label = new Label(text);
        label.setTextFill(Color.web("#334155"));
        label.setFont(Font.font("Arial",FontWeight.BOLD,11));
        return label;
    }

    private TextField createTextField(String value) {

        TextField field = new TextField(value);
        field.setPrefHeight(42);
        field.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #CBD5E1;-fx-border-radius: 8px;-fx-background-radius: 8px;-fx-padding: 0 12px;-fx-font-size: 12px;-fx-text-fill: #0F172A;");
            return field;
    }

    private ImageView createProfileImage(Image image,double size) {

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(false);

        Circle clip = new Circle(size / 2,size / 2,size / 2);

        imageView.setClip(clip);
        return imageView;
    }

    private String getInitials(String name) {

        if (name == null || name.trim().isEmpty()) {
            return "HM";
        }

        String[] parts = name.trim().split("\\s+");

        if (parts.length == 1) {
            return parts[0].substring(0,Math.min(2,parts[0].length())).toUpperCase();
        }

        return (parts[0].substring(0,1) + parts[1].substring(0,1)).toUpperCase();
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getChangePhotoButton() {
        return changePhotoButton;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextArea getBioField() {
        return bioField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public Image getSelectedImage() {
        return selectedImage;
    }

    public Scene getEditProfileScene() {
        return editProfileScene;
    }

    private static class StackPaneWithCircle extends StackPane {
        StackPaneWithCircle(Circle circle,Text text) {
            setPrefSize(110,110);
            getChildren().addAll(circle,text);
        }
    }
}