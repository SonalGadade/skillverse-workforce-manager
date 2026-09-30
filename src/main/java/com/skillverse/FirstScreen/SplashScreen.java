package com.skillverse.FirstScreen;

import java.io.InputStream;

import com.skillverse.FirstScreen.SceneNavigator;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreen extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {

        primaryStage = stage;

        SceneNavigator.init(primaryStage);

        showSplashScreen();
    }

    private void showSplashScreen() {

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #F8FAFC;");

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);

        ImageView logoImageView = null;

        try {

            InputStream is = SplashScreen.class
                    .getResourceAsStream("/assets/Main Logo.jpeg");

            if (is == null) {
                is = SplashScreen.class
                        .getResourceAsStream("/Main Logo.jpeg");
            }

            if (is != null) {

                Image image = new Image(is);

                logoImageView = new ImageView(image);

                logoImageView.setFitWidth(260);
                logoImageView.setPreserveRatio(true);
                logoImageView.setSmooth(true);
            }

        } catch (Exception ex) {

            logoImageView = null;
        }

        HBox brandRow = new HBox(16);
        brandRow.setAlignment(Pos.CENTER);

        if (logoImageView != null) {

            brandRow.getChildren().add(logoImageView);

        } else {

            StackPane logoIcon = new StackPane();

            logoIcon.setPrefSize(64, 64);

            logoIcon.setStyle(
                    "-fx-background-color: linear-gradient(to right, #1E60FF, #4F46E5);"
                    + "-fx-background-radius: 16px;"
            );

            Text logoS = new Text("SV");

            logoS.setFill(Color.WHITE);

            logoS.setFont(
                    Font.font(
                            "Arial",
                            FontWeight.BOLD,
                            30
                    )
            );

            logoIcon.getChildren().add(logoS);

            VBox titleBox = new VBox(2);

            titleBox.setAlignment(Pos.CENTER_LEFT);

            Text title = new Text("SkillVerse AI");

            title.setFill(Color.web("#0F172A"));

            title.setFont(
                    Font.font(
                            "Arial",
                            FontWeight.BOLD,
                            38
                    )
            );

            Text tagline = new Text("LEARN  •  GROW  •  LEAD");

            tagline.setFill(Color.web("#1E60FF"));

            tagline.setFont(
                    Font.font(
                            "Arial",
                            FontWeight.BOLD,
                            13
                    )
            );

            titleBox.getChildren().addAll(
                    title,
                    tagline
            );

            brandRow.getChildren().addAll(
                    logoIcon,
                    titleBox
            );
        }

        Label taglineLabel = new Label(
                "LEARN  •  GROW  •  LEAD"
        );

        taglineLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        13
                )
        );

        taglineLabel.setTextFill(
                Color.web("#1E60FF")
        );

        taglineLabel.setStyle(
                "-fx-letter-spacing: 2px;"
        );

        ProgressIndicator progressIndicator
                = new ProgressIndicator();

        progressIndicator.setPrefSize(32, 32);

        progressIndicator.setStyle(
                "-fx-progress-color: #1E60FF;"
        );

        Label loadingText = new Label(
                "Loading SkillVerse Enterprise Ecosystem..."
        );

        loadingText.setFont(
                Font.font(
                        "Arial",
                        12
                )
        );

        loadingText.setTextFill(
                Color.web("#64748B")
        );

        VBox loadingBox = new VBox(8);

        loadingBox.setAlignment(Pos.CENTER);

        loadingBox.getChildren().addAll(
                progressIndicator,
                loadingText
        );

        contentBox.getChildren().addAll(
                brandRow,
                taglineLabel,
                loadingBox
        );

        VBox.setMargin(
                loadingBox,
                new Insets(
                        20,
                        0,
                        0,
                        0
                )
        );

        root.getChildren().add(contentBox);

        Scene splashScene = new Scene(
                root,
                1360,
                840
        );

        primaryStage.setScene(splashScene);

        primaryStage.setTitle(
                "SkillVerse AI - Launching Ecosystem..."
        );

        primaryStage.centerOnScreen();

        primaryStage.show();

        contentBox.setScaleX(0.85);
        contentBox.setScaleY(0.85);
        contentBox.setOpacity(0.2);

        ScaleTransition scaleTransition
                = new ScaleTransition(
                        Duration.seconds(2.2),
                        contentBox
                );

        scaleTransition.setFromX(0.85);
        scaleTransition.setFromY(0.85);

        scaleTransition.setToX(1.02);
        scaleTransition.setToY(1.02);

        FadeTransition fadeTransition
                = new FadeTransition(
                        Duration.seconds(2.2),
                        contentBox
                );

        fadeTransition.setFromValue(0.2);
        fadeTransition.setToValue(1.0);

        ParallelTransition parallelTransition
                = new ParallelTransition(
                        scaleTransition,
                        fadeTransition
                );

        parallelTransition.setOnFinished(event -> {

            SceneNavigator.showMainPortal();

        });

        parallelTransition.play();
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public static void setScene(Scene scene) {

        if (primaryStage == null) {
            throw new IllegalStateException(
                    "SplashScreen Stage has not been initialized."
            );
        }

        primaryStage.setScene(scene);
    }
}
