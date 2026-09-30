package com.skillverse.employee.controller;

import com.skillverse.employee.view.LoginView;
import com.skillverse.FirstScreen.SceneNavigator;
import com.skillverse.FirstScreen.ValidationUtil;

public class LoginController {

    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
    }

    public void handleLogin(String email, String password) {
        if (ValidationUtil.validateLoginInput(email, password)) {
            SceneNavigator.setCurrentUserEmail(email);
            SceneNavigator.showEmployeeDashboard();
        }
    }
}