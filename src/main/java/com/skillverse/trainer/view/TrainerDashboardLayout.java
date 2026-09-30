package com.skillverse.trainer.view;

import com.skillverse.CommonFeatures.ModernLogoutDialog;
import com.skillverse.trainer.model.Trainer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TrainerDashboardLayout {

    public static VBox createSidebar(Stage stage, Trainer trainer) {
        return TrainerSidebar.create(stage, trainer);
    }

    public static void setupLogoutButton(Button logoutBtn, Stage stage) {
        logoutBtn.setOnAction(e -> {
            Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
            ModernLogoutDialog.show(currentStage != null ? currentStage : stage, "Trainer");
        });
    }

 

    public static HBox createRightHeaderControls(Pane contentArea) {

        HBox rightHeaderControls = new HBox(12);
        rightHeaderControls.setAlignment(Pos.CENTER_RIGHT);

 
        StackPane avatarPill = new StackPane();
        avatarPill.setStyle("-fx-background-color: #2563EB; -fx-background-radius: 50; -fx-padding: 6 12; -fx-cursor: hand;");
        Label avatarText = new Label("T");
        avatarText.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        avatarPill.getChildren().add(avatarText);

        avatarPill.setOnMouseClicked(e -> {
            System.out.println("👤 [Trainer] Avatar clicked -> Opening ProfileView");
            if (contentArea != null) {
                contentArea.getChildren().setAll(new com.skillverse.CommonFeatures.ProfileView());
            } else if (avatarPill.getScene() != null && avatarPill.getScene().getRoot() != null) {
                javafx.scene.Parent root = avatarPill.getScene().getRoot();
                javafx.scene.Node ca = root.lookup("#contentArea");
                if (ca instanceof Pane) {
                    ((Pane) ca).getChildren().setAll(new com.skillverse.CommonFeatures.ProfileView());
                }
            }
        });

        rightHeaderControls.getChildren().add(avatarPill);
        return rightHeaderControls;
    }
}
