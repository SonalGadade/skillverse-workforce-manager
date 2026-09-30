package com.skillverse.hr.controller;

import com.skillverse.hr.view.Account;
import com.skillverse.hr.view.HR;
import com.skillverse.hr.view.HRSignin;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccController {

    private TextField nameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Hyperlink back;
    private Button submit;
    private String errorFieldStyle;

    public CreateAccController(
            TextField nameField,
            PasswordField passwordField,
            PasswordField confirmPasswordField,
            Hyperlink back,
            Button submit,
            String errorFieldStyle
    ) {

        this.nameField = nameField;
        this.passwordField = passwordField;
        this.confirmPasswordField = confirmPasswordField;
        this.back = back;
        this.submit = submit;
        this.errorFieldStyle = errorFieldStyle;

        setupNavigation();
    }

    private void setupNavigation() {

        back.setOnAction(e -> {

            HRSignin signIn = new HRSignin();

            HR.HRstage.setScene(
                    signIn.getScene()
            );
        });

        submit.setOnAction(e -> {

            String name =
                    nameField.getText().trim();

            String password =
                    passwordField.getText();

            String confirmPassword =
                    confirmPasswordField.getText();

            if (name.isEmpty()) {

                nameField.setStyle(
                        errorFieldStyle
                );

                return;
            }

            if (!password.equals(confirmPassword)) {

                confirmPasswordField.setStyle(
                        errorFieldStyle
                );

                return;
            }

            Account account =
                    new Account(name);

            HR.HRstage.setScene(
                    account.getScene()
            );
        });
    }
}