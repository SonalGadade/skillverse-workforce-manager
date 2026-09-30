package com.skillverse.admin.controller;




import com.skillverse.admin.view.AdminLoginView;
import com.skillverse.admin.view.HomePageView;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class HomePageController {

    private final Stage stage;
    private final HomePageView view;

    public HomePageController(
            Stage stage,
            HomePageView view
    ) {
        this.stage = stage;
        this.view = view;
    }

    public void initialize() {

        view.getAdminButton().setOnAction(
                event -> openAdminLogin()
        );

        view.getHrButton().setOnAction(
                event -> showComingSoon("HR Manager")
        );

        view.getManagerButton().setOnAction(
                event -> showComingSoon("Manager")
        );

        view.getEmployeeButton().setOnAction(
                event -> showComingSoon("Employee")
        );

        view.getTrainerButton().setOnAction(
                event -> showComingSoon("Trainer")
        );
    }

    private void openAdminLogin() {

        AdminLoginView login =
                new AdminLoginView(stage);

        stage.setScene(
                login.createScene()
        );

        stage.show();
    }

    private void showComingSoon(
            String role
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle("SkillVerse AI");
        alert.setHeaderText(null);

        alert.setContentText(
                role + " Login will be added next."
        );

        alert.showAndWait();
    }
}