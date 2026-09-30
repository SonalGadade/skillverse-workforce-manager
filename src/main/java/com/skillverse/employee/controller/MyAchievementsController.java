package com.skillverse.employee.controller;

import com.skillverse.employee.model.MyAchievementsModel;
import com.skillverse.employee.view.MyAchievementsView;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyAchievementsController {

    private final MyAchievementsModel model;
    private final MyAchievementsView view;

    public MyAchievementsController() {

        model = new MyAchievementsModel();
        view = new MyAchievementsView();
    }

    public MyAchievementsModel getModel() {
        return model;
    }

    public MyAchievementsView getView() {
        return view;
    }

    public VBox getContent() {

        return view.createContent();
    }

    public void show(Stage stage) {

        VBox content = view.createContent();

        stage.getScene().setRoot(content);

        stage.show();
    }

    public void updateAchievementData(
            int totalAchievements,
            int certificates,
            int badges,
            int points
    ) {

        model.setTotalAchievements(totalAchievements);
        model.setCertificates(certificates);
        model.setBadges(badges);
        model.setPoints(points);
    }
}
