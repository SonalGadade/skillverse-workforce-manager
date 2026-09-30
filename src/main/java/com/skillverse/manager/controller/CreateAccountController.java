package com.skillverse.manager.controller;

import com.skillverse.manager.model.CreateAccountModel;
import com.skillverse.manager.view.CreateAccount;

import javafx.scene.Scene;

public class CreateAccountController {

    private CreateAccount createAccountView;

    private CreateAccountModel createAccountModel;

    public CreateAccountController() {
        createAccountView = new CreateAccount();
    }

    public Scene getCreateAccountScene(Runnable callBackActionManagerLogin) {

        return createAccountView.getCreateAccountScene(callBackActionManagerLogin, () -> handleCreateAccount());
    }

    private void handleCreateAccount() {

    }

    public boolean createAccount(String name, String email, String password, String confirmPassword) {

        createAccountModel = new CreateAccountModel(name, email, password, confirmPassword);

        if (!createAccountModel.validateName()) {

            System.out.println("Name cannot be empty.");

            return false;
        }

        if (!createAccountModel.validateEmail()) {

            System.out.println("Please enter a valid email address.");

            return false;
        }

        if (!createAccountModel.validatePassword()) {

            System.out.println("Password cannot be empty.");

            return false;
        }

        if (!createAccountModel.validateConfirmPassword()) {

            System.out.println("Passwords do not match.");

            return false;
        }

        System.out.println("Account created successfully.");

        System.out.println("Name : " + createAccountModel.getName());

        System.out.println("Email : " + createAccountModel.getEmail());

        return true;
    }

    public CreateAccountModel getCreateAccountModel() {

        return createAccountModel;
    }

    private void navigateToManagerLogin(Runnable callBackActionManagerLogin) {

        if (callBackActionManagerLogin != null) {

            callBackActionManagerLogin.run();
        }
    }
}
