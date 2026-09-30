package com.skillverse.hr.view;

import com.skillverse.FirstScreen.UnifiedProfileView;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class Profile {

    private Scene profileScene;
    private Button backButton;

    private final String userName;
    private final String role;
    private final String email;
    private final String about;

    public Profile(String userName, String role, String email, String about) {
        this.userName = userName;
        this.role = role;
        this.email = email;
        this.about = about;

        createProfile();
    }

    private void createProfile() {
        UnifiedProfileView upv = new UnifiedProfileView("HR", null);
        VBox profileContent = upv.getViewContainer();

        ScrollPane scrollPane = new ScrollPane(profileContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        this.profileScene = new Scene(scrollPane, 1380, 860);
    }

    public Scene getProfileScene() {
        return profileScene;
    }

    public Scene getScene() {
        return profileScene;
    }

    public Button getBackButton() {
        return backButton;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getAbout() {
        return about;
    }
}