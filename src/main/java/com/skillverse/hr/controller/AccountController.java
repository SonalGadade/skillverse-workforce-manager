package com.skillverse.hr.controller;

import com.skillverse.hr.view.Feed;
import com.skillverse.hr.view.HR;
import com.skillverse.hr.view.Profile;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AccountController {

    private String userName;
    private String initials;
    private Scene accountScene;

    private Label dashboard;
    private Label feed;
    private Label employees;
    private Label recruitment;
    private Label payroll;
    private Label training;
    private Label performance;
    private Label leave;
    private Label departments;
    private Label reports;
    private Label feedback;
    private Label speakUp;

    private Label profile;
    private Label topProfile;

    private Button logoutButton;

    private TextField searchField;
    private Button searchButton;

    public AccountController(
            String userName,
            String initials,
            Scene accountScene,
            Label dashboard,
            Label feed,
            Label employees,
            Label recruitment,
            Label payroll,
            Label training,
            Label performance,
            Label leave,
            Label departments,
            Label reports,
            Label feedback,
            Label speakUp,
            Label profileCircle,
            Label topProfile,
            Button logoutButton,
            TextField searchField,
            Button searchButton
    ) {

        this.userName = userName;
        this.initials = initials;
        this.accountScene = accountScene;

        this.dashboard = dashboard;
        this.feed = feed;
        this.employees = employees;
        this.recruitment = recruitment;
        this.payroll = payroll;
        this.training = training;
        this.performance = performance;
        this.leave = leave;
        this.departments = departments;
        this.reports = reports;
        this.feedback = feedback;
        this.speakUp = speakUp;

        this.profile = profileCircle;
        this.topProfile = topProfile;

        this.logoutButton = logoutButton;

        this.searchField = searchField;
        this.searchButton = searchButton;

        setupNavigation();
        setupSearch();
        setupLogout();
    }

    private void setupNavigation() {

        dashboard.setOnMouseClicked(e -> {

            HR.HRstage.setScene(
                    accountScene
            );

        });

        feed.setOnMouseClicked(e -> {

            openFeed();

        });

        employees.setOnMouseClicked(e -> {

            showPage(
                    "Employees"
            );

        });

        recruitment.setOnMouseClicked(e -> {

            showPage(
                    "Recruitment"
            );

        });

        payroll.setOnMouseClicked(e -> {

            showPage(
                    "Payroll & PF"
            );

        });

        training.setOnMouseClicked(e -> {

            showPage(
                    "Training"
            );

        });

        performance.setOnMouseClicked(e -> {

            showPage(
                    "Performance"
            );

        });

        leave.setOnMouseClicked(e -> {

            showPage(
                    "Leave Management"
            );

        });

        departments.setOnMouseClicked(e -> {

            showPage(
                    "Departments"
            );

        });

        reports.setOnMouseClicked(e -> {

            showPage(
                    "Reports"
            );

        });

        if (feedback != null) {
            feedback.setOnMouseClicked(e -> {
                openFeedback();
            });
        }

        if (speakUp != null) {
            speakUp.setOnMouseClicked(e -> {
                openSpeakUp();
            });
        }

        profile.setOnMouseClicked(e -> {

            openProfile();

        });

        topProfile.setOnMouseClicked(e -> {

            openProfile();

        });

    }

    private void openSpeakUp() {
        VBox root = new VBox(18);
        root.setPadding(new javafx.geometry.Insets(20));
        root.setStyle("-fx-background-color: #F6F8FC;");

        com.skillverse.hr.view.HRSpeakUpView hrSpeakUpView = new com.skillverse.hr.view.HRSpeakUpView();
        VBox speakUpContent = hrSpeakUpView.createContent();

        root.getChildren().addAll(speakUpContent);

        javafx.scene.control.ScrollPane scroll = new javafx.scene.control.ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #F6F8FC; -fx-border-color: transparent;");

        Scene speakUpScene = new Scene(scroll, 1600, 800);
        HR.HRstage.setScene(speakUpScene);
    }

    private void openFeedback() {
        VBox root = new VBox(18);
        root.setPadding(new javafx.geometry.Insets(20));
        root.setStyle("-fx-background-color: #F6F8FC;");

        com.skillverse.hr.view.HRFeedbackView hrFeedbackView = new com.skillverse.hr.view.HRFeedbackView();
        VBox feedbackContent = hrFeedbackView;

        root.getChildren().addAll(feedbackContent);

        javafx.scene.control.ScrollPane scroll = new javafx.scene.control.ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: #F6F8FC; -fx-border-color: transparent;");

        Scene feedbackScene = new Scene(scroll, 1600, 800);
        HR.HRstage.setScene(feedbackScene);
    }

    private void openFeed() {

        Feed feedPage = new Feed();

        feedPage.getBackButton().setOnAction(e -> {

            HR.HRstage.setScene(
                    accountScene
            );

        });

        HR.HRstage.setScene(
                feedPage.getScene()
        );

    }

    public void connectQuickActions(
            VBox employeesCard,
            VBox recruitmentCard,
            VBox trainingCard,
            VBox feedCard,
            VBox anonymousChatCard
    ) {

        employeesCard.setOnMouseClicked(e -> {

            showPage(
                    "Employees"
            );

        });

        recruitmentCard.setOnMouseClicked(e -> {

            showPage(
                    "Recruitment"
            );

        });

        trainingCard.setOnMouseClicked(e -> {

            showPage(
                    "Training"
            );

        });

        feedCard.setOnMouseClicked(e -> {

            openFeed();

        });

        anonymousChatCard.setOnMouseClicked(e -> {

            showAnonymousChat();

        });

    }

    private void openProfile() {

        Profile profilePage = new Profile(
                userName,
                "HR Manager",
                userName,
                "HR Manager at SkillVerse"
        );

        HR.HRstage.setScene(
                profilePage.getScene()
        );

    }

    private void showPage(
            String pageName
    ) {

        VBox root = new VBox(18);

        root.setPadding(
                new javafx.geometry.Insets(35)
        );

        root.setStyle(
                "-fx-background-color: #F6F8FC;"
        );

        Label workspace = new Label(
                "SKILLVERSE  /  HR WORKSPACE"
        );

        workspace.setStyle(
                "-fx-text-fill: #2563EB;"
                + "-fx-font-size: 10px;"
                + "-fx-font-weight: bold;"
        );

        Label title = new Label(
                pageName
        );

        title.setStyle(
                "-fx-text-fill: #1E293B;"
                + "-fx-font-size: 28px;"
                + "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                pageName
                + " information will appear here."
        );

        subtitle.setStyle(
                "-fx-text-fill: #64748B;"
                + "-fx-font-size: 13px;"
        );

        VBox card = new VBox(15);

        card.setPadding(
                new javafx.geometry.Insets(25)
        );

        card.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 15px;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-radius: 15px;"
        );

        Label message = new Label(
                pageName
                + " module is ready to be connected with your data."
        );

        message.setStyle(
                "-fx-text-fill: #64748B;"
                + "-fx-font-size: 12px;"
        );

        card.getChildren().addAll(
                message
        );

        root.getChildren().addAll(
                workspace,
                title,
                subtitle,
                card
        );

        Scene pageScene = new Scene(
                root,
                1600,
                800
        );

        HR.HRstage.setScene(
                pageScene
        );

    }

    private void setupSearch() {

        searchButton.setOnAction(e -> {

            performSearch();

        });

        searchField.setOnAction(e -> {

            performSearch();

        });

    }

    private void performSearch() {

        String searchedUsername
                = searchField.getText().trim();

        if (searchedUsername.isEmpty()) {

            showInformation(
                    "Search Username",
                    "Please enter a username to search."
            );

            return;

        }

        if (searchedUsername.equalsIgnoreCase(
                userName
        )) {

            showInformation(
                    "User Found",
                    "Username: " + userName
                    + "\nRole: HR Manager"
            );

        } else {

            showInformation(
                    "User Search",
                    "No user found for username: "
                    + searchedUsername
                    + "\n\nFirebase user search can be connected here."
            );

        }

    }

    private void setupLogout() {
        logoutButton.setOnAction(e -> {
            com.skillverse.FirstScreen.SceneNavigator.confirmAndLogout("HR");
        });
    }

    private void showAnonymousChat() {

        VBox root = new VBox(20);

        root.setPadding(
                new javafx.geometry.Insets(35)
        );

        root.setStyle(
                "-fx-background-color: #F6F8FC;"
        );

        Label title = new Label(
                "Anonymous Chat"
        );

        title.setStyle(
                "-fx-text-fill: #1E293B;"
                + "-fx-font-size: 28px;"
                + "-fx-font-weight: bold;"
        );

        Button backButton = new Button(
                "←  Dashboard"
        );

        backButton.setStyle(
                "-fx-background-color: white;"
                + "-fx-text-fill: #1E293B;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-background-radius: 8px;"
                + "-fx-border-radius: 8px;"
                + "-fx-padding: 10px 18px;"
        );

        backButton.setOnAction(e -> {

            HR.HRstage.setScene(
                    accountScene
            );

        });

        VBox chatCard = new VBox(15);

        chatCard.setPadding(
                new javafx.geometry.Insets(25)
        );

        chatCard.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 15px;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-radius: 15px;"
        );

        Label anonymousLabel = new Label(
                "Anonymous User"
        );

        anonymousLabel.setStyle(
                "-fx-background-color: #EFF6FF;"
                + "-fx-text-fill: #2563EB;"
                + "-fx-padding: 9px 14px;"
                + "-fx-background-radius: 9px;"
                + "-fx-font-size: 12px;"
                + "-fx-font-weight: bold;"
        );

        Label welcomeMessage = new Label(
                "Welcome to Anonymous Chat.\n"
                + "Your identity will not be displayed in this conversation."
        );

        welcomeMessage.setStyle(
                "-fx-text-fill: #64748B;"
                + "-fx-font-size: 12px;"
        );

        TextField messageField = new TextField();

        messageField.setPromptText(
                "Type an anonymous message..."
        );

        Button sendButton = new Button(
                "Send"
        );

        sendButton.setStyle(
                "-fx-background-color: #2563EB;"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 9px;"
                + "-fx-font-weight: bold;"
        );

        javafx.scene.layout.HBox messageBox
                = new javafx.scene.layout.HBox(
                        10,
                        messageField,
                        sendButton
                );

        sendButton.setOnAction(e -> {

            String message
                    = messageField.getText().trim();

            if (!message.isEmpty()) {

                Label sentMessage = new Label(
                        "You (Anonymous): "
                        + message
                );

                sentMessage.setWrapText(
                        true
                );

                sentMessage.setStyle(
                        "-fx-background-color: #EFF6FF;"
                        + "-fx-text-fill: #1E293B;"
                        + "-fx-padding: 10px 14px;"
                        + "-fx-background-radius: 10px;"
                        + "-fx-font-size: 12px;"
                );

                chatCard.getChildren().add(
                        chatCard.getChildren().size() - 1,
                        sentMessage
                );

                messageField.clear();

            }

        });

        messageField.setOnAction(e -> {

            sendButton.fire();

        });

        chatCard.getChildren().addAll(
                anonymousLabel,
                welcomeMessage,
                messageBox
        );

        root.getChildren().addAll(
                title,
                backButton,
                chatCard
        );

        HR.HRstage.setScene(
                new Scene(
                        root,
                        1600,
                        800
                )
        );

    }

    private void showInformation(
            String title,
            String message
    ) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle(
                title
        );

        alert.setHeaderText(
                null
        );

        alert.setContentText(
                message
        );

        alert.showAndWait();

    }

}
