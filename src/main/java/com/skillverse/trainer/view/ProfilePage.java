package com.skillverse.trainer.view;

import com.skillverse.trainer.model.Trainer;
import com.skillverse.FirstScreen.UnifiedProfileView;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfilePage {

    public static void show(Stage stage, Trainer trainer) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FAFC;");

        VBox sidebar = TrainerSidebar.create(stage, trainer);
        root.setLeft(sidebar);

        UnifiedProfileView upv = new UnifiedProfileView("Trainer", () -> DashboardPage.show(stage, trainer));
        VBox profileContent = upv.getViewContainer();

        ScrollPane scrollPane = new ScrollPane(profileContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1380, 860);
        stage.setScene(scene);
        stage.setTitle("SkillVerse AI - Trainer Profile");
        stage.show();
    }
}