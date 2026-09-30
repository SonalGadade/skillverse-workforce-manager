

package com.skillverse.manager.controller;

import com.skillverse.manager.model.LoginModel;
import com.skillverse.manager.view.CreateAccount;
import com.skillverse.manager.view.Login;
import com.skillverse.manager.view.ManagerDashboard;

public class LoginController {

    private LoginModel loginModel;

    public LoginController() {
        loginModel = new LoginModel("", "");
    }

    public void login(String email, String password, Login loginView) {
        System.out.println("1. Login button clicked");

        loginModel = new LoginModel(email, password);

        boolean validLogin = loginModel.validateLogin();

        System.out.println("2. Valid Login: " + validLogin);

        if (validLogin) {
            System.out.println("3. Opening Manager Dashboard");
            openManagerDashboard(loginView);
        } else {
            System.out.println("Invalid Email or Password");
        }
    }

    public void openManagerDashboard(Login loginView) {
        System.out.println("4. Creating Manager Dashboard");

        ManagerDashboard managerDashboard = new ManagerDashboard();

        Runnable callBackActionLogin = new Runnable() {
            @Override
            public void run() {
                loginView.loginStage.setScene(loginView.getLoginScene());
            }
        };

        System.out.println("5. Getting Manager Dashboard Scene");

        loginView.loginStage.setScene(managerDashboard.getManagerDashboardScene(callBackActionLogin));

        System.out.println("6. Manager Dashboard Scene Set");
    }

    public void openCreateAccount(Login loginView) {
        CreateAccount createAccount = new CreateAccount();

        Runnable callBackActionManagerLogin = new Runnable() {
            @Override
            public void run() {
                loginView.loginStage.setScene(loginView.getLoginScene());
            }
        };

        Runnable callBackActionCreateAccount = new Runnable() {
            @Override
            public void run() {
                loginView.loginStage.setScene(loginView.getLoginScene());
            }
        };

        loginView.loginStage.setScene(createAccount.getCreateAccountScene(callBackActionManagerLogin, callBackActionCreateAccount));
    }
}