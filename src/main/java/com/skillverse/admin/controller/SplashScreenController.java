package com.skillverse.admin.controller;



import com.skillverse.admin.view.HomePageView;
import com.skillverse.admin.view.SplashScreenView;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreenController {

    private final Stage stage;
    private final SplashScreenView view;

    public SplashScreenController(
            Stage stage,
            SplashScreenView view
    ) {
        this.stage = stage;
        this.view = view;
    }

    public void startSplash() {

        ImageView logo = view.getLogo();
        Label title = view.getTitle();
        Label subtitle = view.getSubtitle();


        logo.setOpacity(0);
        logo.setScaleX(0.7);
        logo.setScaleY(0.7);

        title.setOpacity(0);
        subtitle.setOpacity(0);


        FadeTransition fade =
                new FadeTransition(
                        Duration.millis(900),
                        logo
                );

        fade.setFromValue(0);
        fade.setToValue(1);

        ScaleTransition scale =
                new ScaleTransition(
                        Duration.millis(1000),
                        logo
                );

        scale.setFromX(0.7);
        scale.setFromY(0.7);

        scale.setToX(1);
        scale.setToY(1);

        ParallelTransition logoAnimation =
                new ParallelTransition(
                        fade,
                        scale
                );

        logoAnimation.play();


        PauseTransition titleDelay =
                new PauseTransition(
                        Duration.millis(500)
                );

        titleDelay.setOnFinished(event -> {

            FadeTransition titleFade =
                    new FadeTransition(
                            Duration.millis(600),
                            title
                    );

            titleFade.setFromValue(0);
            titleFade.setToValue(1);

            titleFade.play();
        });

        titleDelay.play();


        PauseTransition subtitleDelay =
                new PauseTransition(
                        Duration.millis(800)
                );

        subtitleDelay.setOnFinished(event -> {

            FadeTransition subtitleFade =
                    new FadeTransition(
                            Duration.millis(600),
                            subtitle
                    );

            subtitleFade.setFromValue(0);
            subtitleFade.setToValue(1);

            subtitleFade.play();
        });

        subtitleDelay.play();


        PauseTransition homeDelay =
                new PauseTransition(
                        Duration.seconds(2.5)
                );

        homeDelay.setOnFinished(event ->
                openHome()
        );

        homeDelay.play();
    }

    private void openHome() {

        HomePageView home =
                new HomePageView(stage);

        stage.setScene(
                home.createScene()
        );

        stage.show();
    }
}



