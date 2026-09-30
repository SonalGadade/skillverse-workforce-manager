package com.skillverse.admin.controller;

import com.skillverse.FirstScreen.SceneNavigator;
import com.skillverse.FirstScreen.ValidationUtil;
import com.skillverse.admin.view.AdminLoginView;

import javafx.stage.Stage;

public class AdminLoginController {

    private final Stage stage;
    private final AdminLoginView view;

    public AdminLoginController(Stage stage, AdminLoginView view) {
        this.stage = stage;
        this.view = view;
    }

    public void initialize() {
    }

    public void login(String email, String password) {
        if (ValidationUtil.validateLoginInput(email, password)) {
            SceneNavigator.setCurrentUserEmail(email);
            SceneNavigator.showAdminDashboard();
        }
    }

    public void goHome() {
        SceneNavigator.showMainPortal();
    }
}