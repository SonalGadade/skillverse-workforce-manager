package com.skillverse.CommonFeatures;

import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.CommonFeatures.LoginView;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ModernLogoutDialog {

    public static void show(Stage ownerStage, String roleTitle) {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(ownerStage);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initStyle(StageStyle.TRANSPARENT);

        StackPane rootOverlay = new StackPane();
        rootOverlay.setAlignment(Pos.CENTER);
        rootOverlay.setStyle("-fx-background-color: rgba(15, 23, 42, 0.45);");
        rootOverlay.setPadding(new Insets(20));

        VBox card = new VBox(16);
        card.setMaxWidth(380);
        card.setMinWidth(380);
        card.setPadding(new Insets(24, 24, 20, 24));
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #E2E8F0;" +
                "-fx-border-radius: 14;" +
                "-fx-border-width: 1;" +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 20, 0, 0, 8);"
        );

        Label iconBadge = new Label("🚪");
        iconBadge.setStyle(
                "-fx-font-size: 22px;" +
                "-fx-background-color: #FEE2E2;" +
                "-fx-text-fill: #DC2626;" +
                "-fx-padding: 10 14;" +
                "-fx-background-radius: 50;"
        );

        VBox textBox = new VBox(4);
        textBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Confirm Logout");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        Label descLabel = new Label("Are you sure you want to end your active session?");
        descLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B; -fx-text-alignment: center;");
        descLabel.setWrapText(true);

        textBox.getChildren().addAll(titleLabel, descLabel);

        HBox buttonRow = new HBox(10);
        buttonRow.setAlignment(Pos.CENTER);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setPrefWidth(140);
        cancelBtn.setPrefHeight(38);
        cancelBtn.setStyle(
                "-fx-background-color: #F1F5F9;" +
                "-fx-text-fill: #475569;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;"
        );
        cancelBtn.setOnAction(e -> closeWithAnimation(dialogStage, card, rootOverlay));

        Button logoutBtn = new Button("Yes, Log Out");
        logoutBtn.setPrefWidth(140);
        logoutBtn.setPrefHeight(38);
        logoutBtn.setStyle(
                "-fx-background-color: #DC2626;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;"
        );
        logoutBtn.setOnAction(e -> {
            dialogStage.close();
            UserSession.clearSession();
            ownerStage.getScene().setRoot(new LoginView(ownerStage));
        });

        buttonRow.getChildren().addAll(cancelBtn, logoutBtn);
        card.getChildren().addAll(iconBadge, textBox, buttonRow);
        rootOverlay.getChildren().add(card);

        rootOverlay.setOnMouseClicked(e -> {
            if (e.getTarget() == rootOverlay) {
                closeWithAnimation(dialogStage, card, rootOverlay);
            }
        });

        Scene scene = new Scene(rootOverlay);
        scene.setFill(Color.TRANSPARENT);
        dialogStage.setScene(scene);

        card.setScaleX(0.9);
        card.setScaleY(0.9);
        card.setOpacity(0);
        rootOverlay.setOpacity(0);

        dialogStage.setOnShown(e -> {
            FadeTransition fadeOverlay = new FadeTransition(Duration.millis(180), rootOverlay);
            fadeOverlay.setFromValue(0);
            fadeOverlay.setToValue(1);

            FadeTransition fadeCard = new FadeTransition(Duration.millis(200), card);
            fadeCard.setFromValue(0);
            fadeCard.setToValue(1);

            ScaleTransition scaleCard = new ScaleTransition(Duration.millis(200), card);
            scaleCard.setFromX(0.9);
            scaleCard.setFromY(0.9);
            scaleCard.setToX(1.0);
            scaleCard.setToY(1.0);

            new ParallelTransition(fadeOverlay, fadeCard, scaleCard).play();
        });

        dialogStage.show();
    }

    private static void closeWithAnimation(Stage stage, VBox card, StackPane overlay) {
        FadeTransition fadeOverlay = new FadeTransition(Duration.millis(120), overlay);
        fadeOverlay.setFromValue(1);
        fadeOverlay.setToValue(0);

        ScaleTransition scaleCard = new ScaleTransition(Duration.millis(120), card);
        scaleCard.setFromX(1.0);
        scaleCard.setFromY(1.0);
        scaleCard.setToX(0.92);
        scaleCard.setToY(0.92);

        ParallelTransition exitAnim = new ParallelTransition(fadeOverlay, scaleCard);
        exitAnim.setOnFinished(ev -> stage.close());
        exitAnim.play();
    }
}
