package com.skillverse.manager.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CreateAccount {

    private Scene createAccountScene;

    public Scene getCreateAccountScene(Runnable callBackActionManagerLogin, Runnable callBackActionCreateAccount) {

        Text logoSV = new Text("SV");
        logoSV.setStyle("-fx-font-size: 40px;-fx-font-weight: bold;-fx-fill: #287CF5;");

        Text logoText = new Text("SkillVerse");
        logoText.setStyle("-fx-font-size: 28px;-fx-font-weight: bold;-fx-fill: #0F172A;");

        HBox logoBox = new HBox(12, logoSV, logoText);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        Text logoSubtitle = new Text("AI Powered Internal Talent Ecosystem");
        logoSubtitle.setStyle("-fx-font-size: 12px;-fx-fill: #475569;");

        VBox logoSection = new VBox(2, logoBox, logoSubtitle);
        logoSection.setAlignment(Pos.CENTER_LEFT);

        Text welcomeText = new Text("Welcome to");
        welcomeText.setStyle("-fx-font-size: 27px;-fx-font-weight: bold;-fx-fill: #0F172A;");

        Text skillVerseText = new Text("SkillVerse");
        skillVerseText.setStyle("-fx-font-size: 30px;-fx-font-weight: bold;-fx-fill: #7C3AED;");

        Text managerPlatformText = new Text("Manager Platform");
        managerPlatformText.setStyle("-fx-font-size: 27px;-fx-font-weight: bold;-fx-fill: #0F172A;");

        Text descriptionText = new Text("Lead your team. Track performance.\nAchieve goals together.");
        descriptionText.setStyle("-fx-font-size: 15px;-fx-fill: #475569;");

        VBox welcomeBox = new VBox(0, welcomeText, skillVerseText, managerPlatformText, descriptionText);
        welcomeBox.setAlignment(Pos.CENTER_LEFT);

        Text overviewTitle = new Text("Team Overview");
        overviewTitle.setStyle("-fx-font-size: 13px;-fx-font-weight: bold;-fx-fill: #334155;");

        Text membersNumber = new Text("18");
        membersNumber.setStyle("-fx-font-size: 20px;-fx-font-weight: bold;-fx-fill: #287CF5;");

        Text membersText = new Text("Members");
        membersText.setStyle("-fx-font-size: 9px;-fx-fill: #64748B;");

        VBox membersBox = new VBox(0, membersNumber, membersText);
        membersBox.setStyle("-fx-background-color: #DCEAFF;-fx-background-radius: 8px;-fx-padding: 8px 15px;");

        Text performanceNumber = new Text("88%");
        performanceNumber.setStyle("-fx-font-size: 20px;-fx-font-weight: bold;-fx-fill: #16A36A;");

        Text performanceText = new Text("Performance");
        performanceText.setStyle("-fx-font-size: 9px;-fx-fill: #64748B;");

        VBox performanceBox = new VBox(0, performanceNumber, performanceText);
        performanceBox.setStyle("-fx-background-color: #DDF7EB;-fx-background-radius: 8px;-fx-padding: 8px 15px;");

        HBox overviewStats = new HBox(10, membersBox, performanceBox);

        Text graphText = new Text("Performance Insights");
        graphText.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-fill: #64748B;");

        Text graph = new Text("      ╱╲      ╱╲\n" +
                              "   ╱      ╲╱      ╲\n" +
                              " ╱                ╲");

        graph.setStyle("-fx-font-size: 17px;-fx-font-weight: bold;-fx-fill: #287CF5;");

        VBox graphBox = new VBox(4, graphText, graph);
        graphBox.setStyle("-fx-background-color: #F8FAFF;-fx-background-radius: 10px;-fx-padding: 10px 15px;");

        VBox overviewCard = new VBox(12, overviewTitle, overviewStats, graphBox);
        overviewCard.setPrefWidth(360);
        overviewCard.setStyle("-fx-background-color: #FFFFFF;-fx-background-radius: 15px;-fx-border-color: #E2E8F0;-fx-border-radius: 15px;-fx-padding: 18px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.12), 18, 0.2, 0, 6);");

        Text analyticsNumber = new Text("92%");
        analyticsNumber.setStyle("-fx-font-size: 22px;-fx-font-weight: bold;-fx-fill: #287CF5;");

        Text analyticsTitle = new Text("Analytics");
        analyticsTitle.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-fill: #64748B;");

        Text analyticsSubtitle = new Text("Team growth");
        analyticsSubtitle.setStyle("-fx-font-size: 9px;-fx-fill: #94A3B8;");

        VBox analyticsCard = new VBox(3, analyticsTitle, analyticsNumber, analyticsSubtitle);
        analyticsCard.setStyle("-fx-background-color: #FFFFFF;-fx-background-radius: 12px;-fx-padding: 12px 18px;-fx-border-color: #E2E8F0;-fx-border-radius: 12px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.10), 12, 0.2, 0, 5);");

        HBox overviewArea = new HBox(15, overviewCard, analyticsCard);
        overviewArea.setAlignment(Pos.CENTER_LEFT);

        Text teamTitle = new Text("Team Management");
        teamTitle.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-fill: #1E293B;");

        Text teamDescription = new Text("Manage your team effectively.");
        teamDescription.setStyle("-fx-font-size: 9px;-fx-fill: #64748B;");

        VBox teamFeature = new VBox(4, teamTitle, teamDescription);
        teamFeature.setAlignment(Pos.CENTER);

        Text projectTitle = new Text("Project Tracking");
        projectTitle.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-fill: #1E293B;");

        Text projectDescription = new Text("Track projects and deadlines.");
        projectDescription.setStyle("-fx-font-size: 9px;-fx-fill: #64748B;");

        VBox projectFeature = new VBox(4, projectTitle, projectDescription);
        projectFeature.setAlignment(Pos.CENTER);

        Text performanceTitle = new Text("Performance Insights");
        performanceTitle.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-fill: #1E293B;");

        Text performanceDescription = new Text("Monitor performance and productivity.");
        performanceDescription.setStyle("-fx-font-size: 9px;-fx-fill: #64748B;");

        VBox performanceFeature = new VBox(4, performanceTitle, performanceDescription);
        performanceFeature.setAlignment(Pos.CENTER);

        Text analyticsFeatureTitle = new Text("Smart Analytics");
        analyticsFeatureTitle.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-fill: #1E293B;");

        Text analyticsFeatureDescription = new Text("Make data-driven decisions.");
        analyticsFeatureDescription.setStyle("-fx-font-size: 9px;-fx-fill: #64748B;");

        VBox analyticsFeature = new VBox(4, analyticsFeatureTitle, analyticsFeatureDescription);
        analyticsFeature.setAlignment(Pos.CENTER);

        HBox featuresBox = new HBox(25, teamFeature, projectFeature, performanceFeature, analyticsFeature);
        featuresBox.setAlignment(Pos.CENTER);

        VBox leftPanel = new VBox(28, logoSection, welcomeBox, overviewArea, featuresBox);
        leftPanel.setPrefWidth(620);
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setPadding(new Insets(35, 45, 35, 50));
        leftPanel.setStyle("-fx-background-color: #F1F7FF;");

        Text heading = new Text("Create Account");
        heading.setStyle("-fx-font-size: 30px;-fx-font-weight: bold;-fx-fill: #0F172A;");

        Text subtitle = new Text("Create your SkillVerse manager account");
        subtitle.setStyle("-fx-font-size: 14px;-fx-fill: #64748B;");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        nameField.setPrefHeight(48);
        nameField.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #CBD5E1;-fx-border-radius: 8px;-fx-background-radius: 8px;-fx-padding: 0px 15px;-fx-font-size: 13px;-fx-text-fill: #0F172A;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");
        emailField.setPrefHeight(48);
        emailField.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #CBD5E1;-fx-border-radius: 8px;-fx-background-radius: 8px;-fx-padding: 0px 15px;-fx-font-size: 13px;-fx-text-fill: #0F172A;");

        Text otpTitle = new Text("Enter OTP");
        otpTitle.setStyle("-fx-font-size: 12px;-fx-font-weight: bold;-fx-fill: #475569;");

        TextField otp1 = new TextField();
        TextField otp2 = new TextField();
        TextField otp3 = new TextField();
        TextField otp4 = new TextField();
        TextField otp5 = new TextField();
        TextField otp6 = new TextField();

        TextField[] otpFields = {otp1, otp2, otp3, otp4, otp5, otp6};

        for (TextField otpField : otpFields) {
            otpField.setPrefWidth(48);
            otpField.setPrefHeight(45);
            otpField.setAlignment(Pos.CENTER);
            otpField.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #CBD5E1;-fx-border-radius: 8px;-fx-background-radius: 8px;-fx-font-size: 18px;-fx-font-weight: bold;-fx-text-fill: #0F172A;");
        }

        for (int i = 0; i < otpFields.length; i++) {
            final int index = i;

            otpFields[i].textProperty().addListener((observable, oldValue, newValue) -> {

                if (!newValue.matches("\\d?")) {
                    otpFields[index].setText(newValue.replaceAll("[^\\d]", ""));
                    return;
                }

                if (newValue.length() > 1) {
                    otpFields[index].setText(newValue.substring(0, 1));
                }

                if (newValue.length() == 1 && index < otpFields.length - 1) {
                    otpFields[index + 1].requestFocus();
                }
            });
        }

        HBox otpBox = new HBox(8, otp1, otp2, otp3, otp4, otp5, otp6);
        otpBox.setAlignment(Pos.CENTER);

        VBox otpSection = new VBox(6, otpTitle, otpBox);
        otpSection.setAlignment(Pos.CENTER_LEFT);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(48);
        passwordField.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #CBD5E1;-fx-border-radius: 8px;-fx-background-radius: 8px;-fx-padding: 0px 15px;-fx-font-size: 13px;-fx-text-fill: #0F172A;");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setPrefHeight(48);
        confirmPasswordField.setStyle("-fx-background-color: #FFFFFF;-fx-border-color: #CBD5E1;-fx-border-radius: 8px;-fx-background-radius: 8px;-fx-padding: 0px 15px;-fx-font-size: 13px;-fx-text-fill: #0F172A;");

        Button createButton = new Button("Create Account");
        createButton.setPrefWidth(400);
        createButton.setPrefHeight(45);
        createButton.setStyle("-fx-background-color: #2F80ED;-fx-text-fill: white;-fx-font-size: 13px;-fx-font-weight: bold;-fx-background-radius: 8px;-fx-border-radius: 8px;-fx-cursor: hand;");

        createButton.setOnAction(event -> {
            callBackActionCreateAccount.run();
        });

        VBox box = new VBox(15, heading, subtitle, nameField, emailField, otpSection, passwordField, confirmPasswordField, createButton);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(38, 45, 35, 45));
        box.setMaxWidth(480);
        box.setStyle("-fx-background-color: #FFFFFF;-fx-background-radius: 20px;-fx-border-color: #E2E8F0;-fx-border-radius: 20px;-fx-effect: dropshadow(gaussian, rgba(55,90,140,0.14), 25, 0.2, 0, 8);");

        VBox rightPanel = new VBox(box);
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPadding(new Insets(30, 40, 30, 40));
        rightPanel.setPrefWidth(580);
        rightPanel.setStyle("-fx-background-color: #F8FBFF;");

        HBox root = new HBox(leftPanel, rightPanel);
        root.setStyle("-fx-background-color: #F8FBFF;");

        HBox.setHgrow(leftPanel, Priority.ALWAYS);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        createAccountScene = new Scene(root, 1200, 700);

        return createAccountScene;
    }
}