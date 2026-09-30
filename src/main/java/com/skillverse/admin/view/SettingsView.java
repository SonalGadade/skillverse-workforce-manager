package com.skillverse.admin.view;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SettingsView {

    private final Stage stage;

   

    private TextField adminNameField;
    private TextField organizationField;
    private TextField emailField;

    private ComboBox<String> timezoneBox;
    private ComboBox<String> languageBox;

    private CheckBox emailNotification;
    private CheckBox employeeAlert;
    private CheckBox jobAlert;
    private CheckBox approvalAlert;
    private CheckBox systemAlert;

    private CheckBox compactDashboard;
    private CheckBox twoFactor;
    private CheckBox autoRefresh;

    private ComboBox<String> themeBox;
    private ComboBox<String> itemsPerPageBox;

    private Button saveButton;
    private Button changePasswordButton;
    private Button loginActivityButton;
    private Button logoutDevicesButton;
    private Button resetButton;

    private Label statusLabel;

    public SettingsView(Stage stage) {
        this.stage = stage;
    }


    public TextField getAdminNameField() {
        return adminNameField;
    }

    public TextField getOrganizationField() {
        return organizationField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public ComboBox<String> getTimezoneBox() {
        return timezoneBox;
    }

    public ComboBox<String> getLanguageBox() {
        return languageBox;
    }

    public CheckBox getEmailNotification() {
        return emailNotification;
    }

    public CheckBox getEmployeeAlert() {
        return employeeAlert;
    }

    public CheckBox getJobAlert() {
        return jobAlert;
    }

    public CheckBox getApprovalAlert() {
        return approvalAlert;
    }

    public CheckBox getSystemAlert() {
        return systemAlert;
    }

    public CheckBox getCompactDashboard() {
        return compactDashboard;
    }

    public CheckBox getTwoFactor() {
        return twoFactor;
    }

    public CheckBox getAutoRefresh() {
        return autoRefresh;
    }

    public ComboBox<String> getThemeBox() {
        return themeBox;
    }

    public ComboBox<String> getItemsPerPageBox() {
        return itemsPerPageBox;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getChangePasswordButton() {
        return changePasswordButton;
    }

    public Button getLoginActivityButton() {
        return loginActivityButton;
    }

    public Button getLogoutDevicesButton() {
        return logoutDevicesButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

 

    public ScrollPane createScrollPane() {

        VBox main = new VBox(20);

        main.setPadding(
            new Insets(25, 30, 35, 30)
        );

        main.setStyle(
            "-fx-background-color:#EEF2FF;"
        );


        HBox header = new HBox();

        header.setAlignment(
            Pos.CENTER_LEFT
        );

        VBox titleBox = new VBox(4);

        Label title =
            new Label("Settings");

        title.setFont(
            Font.font("Arial", 28)
        );

        title.setStyle(
            "-fx-font-weight:bold;"
        );

        title.setTextFill(
            Color.web("#202A44")
        );

        Label subtitle =
            new Label(
                "Manage your dashboard and account preferences"
            );

        subtitle.setFont(
            Font.font("Arial", 13)
        );

        subtitle.setTextFill(
            Color.web("#66728B")
        );

        titleBox.getChildren().addAll(
            title,
            subtitle
        );

        Region space = new Region();

        HBox.setHgrow(
            space,
            Priority.ALWAYS
        );

        saveButton =
            new Button("Save Changes");

        saveButton.setPrefHeight(40);

        saveButton.setPadding(
            new Insets(0, 20, 0, 20)
        );

        saveButton.setStyle(
            "-fx-background-color:#5368C9;" +
            "-fx-text-fill:white;" +
            "-fx-font-size:12px;" +
            "-fx-font-weight:bold;" +
            "-fx-background-radius:9;" +
            "-fx-cursor:hand;"
        );

        header.getChildren().addAll(
            titleBox,
            space,
            saveButton
        );

      

        VBox general =
            createCard(
                "General Settings",
                "Basic administrator and organization information"
            );

        adminNameField =
            createTextField("Admin Name");

        organizationField =
            createTextField("Organization Name");

        emailField =
            createTextField("Email Address");

        timezoneBox =
            new ComboBox<>();

        timezoneBox.getItems().addAll(
            "Asia/Kolkata",
            "Asia/Dubai",
            "Europe/London",
            "America/New_York"
        );

        timezoneBox.setValue(
            "Asia/Kolkata"
        );

        styleComboBox(timezoneBox);

        languageBox =
            new ComboBox<>();

        languageBox.getItems().addAll(
            "English",
            "Hindi",
            "Marathi"
        );

        languageBox.setValue(
            "English"
        );

        styleComboBox(languageBox);

        GridPane generalGrid =
            new GridPane();

        generalGrid.setHgap(18);
        generalGrid.setVgap(14);

        generalGrid.add(
            fieldBox(
                "Admin Name",
                adminNameField
            ),
            0, 0
        );

        generalGrid.add(
            fieldBox(
                "Organization",
                organizationField
            ),
            1, 0
        );

        generalGrid.add(
            fieldBox(
                "Email Address",
                emailField
            ),
            0, 1
        );

        generalGrid.add(
            fieldBox(
                "Time Zone",
                timezoneBox
            ),
            1, 1
        );

        generalGrid.add(
            fieldBox(
                "Language",
                languageBox
            ),
            0, 2
        );

        general.getChildren().add(
            generalGrid
        );

   

        VBox appearance =
            createCard(
                "Appearance",
                "Customize how your dashboard looks"
            );

        themeBox =
            new ComboBox<>();

        themeBox.getItems().addAll(
            "Light",
            "Dark",
            "System Default"
        );

        themeBox.setValue(
            "Light"
        );

        styleComboBox(themeBox);

        compactDashboard =
            createCheckBox(
                "Compact Dashboard",
                "Use a more compact dashboard layout"
            );

        HBox appearanceRow =
            new HBox(30);

        appearanceRow.setAlignment(
            Pos.CENTER_LEFT
        );

        appearanceRow.getChildren().addAll(
            fieldBox(
                "Theme",
                themeBox
            ),
            compactDashboard
        );

        appearance.getChildren().add(
            appearanceRow
        );



        VBox notifications =
            createCard(
                "Notification Settings",
                "Choose which alerts you want to receive"
            );

        emailNotification =
            createCheckBox(
                "Email Notifications",
                "Receive important notifications through email"
            );

        employeeAlert =
            createCheckBox(
                "New Employee Alerts",
                "Notify when a new employee is registered"
            );

        jobAlert =
            createCheckBox(
                "Job Application Alerts",
                "Notify about new job applications"
            );

        approvalAlert =
            createCheckBox(
                "Approval Requests",
                "Notify when approval is required"
            );

        systemAlert =
            createCheckBox(
                "System Alerts",
                "Receive important system notifications"
            );

        emailNotification.setSelected(true);
        employeeAlert.setSelected(true);
        jobAlert.setSelected(true);
        approvalAlert.setSelected(true);
        systemAlert.setSelected(true);

        VBox notificationList =
            new VBox(12);

        notificationList.getChildren().addAll(
            emailNotification,
            employeeAlert,
            jobAlert,
            approvalAlert,
            systemAlert
        );

        notifications.getChildren().add(
            notificationList
        );


        VBox security =
            createCard(
                "Security",
                "Manage account security and login preferences"
            );

        twoFactor =
            createCheckBox(
                "Two-Factor Authentication",
                "Add an extra layer of security to your account"
            );

        twoFactor.setSelected(true);

        changePasswordButton =
            smallButton("Change Password");

        loginActivityButton =
            smallButton("View Activity");

        HBox passwordRow =
            securityRow(
                "Password",
                "Update your administrator password",
                changePasswordButton
            );

        HBox activityRow =
            securityRow(
                "Login Activity",
                "Review recent account login activity",
                loginActivityButton
            );

        VBox securityContent =
            new VBox(14);

        securityContent.getChildren().addAll(
            twoFactor,
            passwordRow,
            activityRow
        );

        security.getChildren().add(
            securityContent
        );

    

        VBox system =
            createCard(
                "System Preferences",
                "Configure dashboard behavior"
            );

        autoRefresh =
            createCheckBox(
                "Automatic Data Refresh",
                "Automatically refresh dashboard information"
            );

        autoRefresh.setSelected(true);

        itemsPerPageBox =
            new ComboBox<>();

        itemsPerPageBox.getItems().addAll(
            "10",
            "20",
            "50",
            "100"
        );

        itemsPerPageBox.setValue(
            "20"
        );

        styleComboBox(
            itemsPerPageBox
        );

        HBox systemRow =
            new HBox(35);

        systemRow.setAlignment(
            Pos.CENTER_LEFT
        );

        systemRow.getChildren().addAll(
            autoRefresh,
            fieldBox(
                "Items Per Page",
                itemsPerPageBox
            )
        );

        system.getChildren().add(
            systemRow
        );

     

        VBox danger =
            new VBox(12);

        danger.setPadding(
            new Insets(18)
        );

        danger.setStyle(
            "-fx-background-color:#FFF7F7;" +
            "-fx-background-radius:12;" +
            "-fx-border-color:#F2C7C7;" +
            "-fx-border-radius:12;"
        );

        Label dangerTitle =
            new Label("Danger Zone");

        dangerTitle.setFont(
            Font.font("Arial", 15)
        );

        dangerTitle.setStyle(
            "-fx-font-weight:bold;"
        );

        dangerTitle.setTextFill(
            Color.web("#B23A45")
        );

        Label dangerText =
            new Label(
                "These actions may affect your account settings."
            );

        dangerText.setFont(
            Font.font("Arial", 11)
        );

        dangerText.setTextFill(
            Color.web("#777777")
        );

        logoutDevicesButton =
            new Button(
                "Logout From All Devices"
            );

        logoutDevicesButton.setStyle(
            "-fx-background-color:#FCE7E8;" +
            "-fx-text-fill:#C8454E;" +
            "-fx-border-color:#E9A5AA;" +
            "-fx-border-radius:7;" +
            "-fx-background-radius:7;" +
            "-fx-cursor:hand;"
        );

        resetButton =
            new Button(
                "Reset Settings"
            );

        resetButton.setStyle(
            "-fx-background-color:#FCE7E8;" +
            "-fx-text-fill:#C8454E;" +
            "-fx-border-color:#E9A5AA;" +
            "-fx-border-radius:7;" +
            "-fx-background-radius:7;" +
            "-fx-cursor:hand;"
        );

        HBox dangerButtons =
            new HBox(10);

        dangerButtons.getChildren().addAll(
            logoutDevicesButton,
            resetButton
        );

        danger.getChildren().addAll(
            dangerTitle,
            dangerText,
            dangerButtons
        );



        statusLabel =
            new Label("");

        statusLabel.setFont(
            Font.font("Arial", 11)
        );

        statusLabel.setTextFill(
            Color.web("#2A9962")
        );

        main.getChildren().addAll(
            header,
            general,
            appearance,
            notifications,
            security,
            system,
            danger,
            statusLabel
        );

     

        ScrollPane scroll =
            new ScrollPane(main);

        scroll.setFitToWidth(true);

        scroll.setHbarPolicy(
            ScrollPane.ScrollBarPolicy.NEVER
        );

        scroll.setVbarPolicy(
            ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scroll.setStyle(
            "-fx-background-color:#EEF2FF;" +
            "-fx-border-color:transparent;"
        );

        scroll.skinProperty().addListener(
            (obs, oldSkin, newSkin) -> {

                if (newSkin != null) {

                    scroll.lookupAll(
                        ".scroll-bar"
                    ).forEach(node ->
                        node.setStyle(
                            "-fx-opacity:0;" +
                            "-fx-background-color:transparent;"
                        )
                    );

                    scroll.lookupAll(
                        ".corner"
                    ).forEach(node ->
                        node.setStyle(
                            "-fx-background-color:transparent;"
                        )
                    );

                    scroll.lookupAll(
                        ".viewport"
                    ).forEach(node ->
                        node.setStyle(
                            "-fx-background-color:#EEF2FF;"
                        )
                    );
                }
            }
        );

        return scroll;
    }



    private TextField createTextField(
        String prompt
    ) {

        TextField field =
            new TextField();

        field.setPromptText(
            prompt
        );

        field.setPrefHeight(40);

        field.setStyle(
            "-fx-background-color:#F8F9FD;" +
            "-fx-border-color:#D5DAEA;" +
            "-fx-border-radius:8;" +
            "-fx-background-radius:8;" +
            "-fx-padding:0 12;" +
            "-fx-font-size:11px;"
        );

        return field;
    }

  

    private CheckBox createCheckBox(
        String title,
        String description
    ) {

        CheckBox box =
            new CheckBox();

        Label titleLabel =
            new Label(title);

        titleLabel.setFont(
            Font.font("Arial", 11)
        );

        titleLabel.setStyle(
            "-fx-font-weight:bold;"
        );

        titleLabel.setTextFill(
            Color.web("#303B54")
        );

        Label descriptionLabel =
            new Label(description);

        descriptionLabel.setFont(
            Font.font("Arial", 9)
        );

        descriptionLabel.setTextFill(
            Color.web("#778198")
        );

        VBox text =
            new VBox(2);

        text.getChildren().addAll(
            titleLabel,
            descriptionLabel
        );

        HBox container =
            new HBox(9);

        container.setAlignment(
            Pos.CENTER_LEFT
        );

        container.getChildren().addAll(
            box,
            text
        );

        box.setUserData(container);

        return box;
    }


    private void styleComboBox(
        ComboBox<String> combo
    ) {

        combo.setPrefHeight(40);

        combo.setPrefWidth(220);

        combo.setStyle(
            "-fx-background-color:#F8F9FD;" +
            "-fx-border-color:#D5DAEA;" +
            "-fx-border-radius:8;" +
            "-fx-background-radius:8;" +
            "-fx-font-size:11px;"
        );
    }

  

    private VBox fieldBox(
        String title,
        Control control
    ) {

        VBox box =
            new VBox(6);

        Label label =
            new Label(title);

        label.setFont(
            Font.font("Arial", 10)
        );

        label.setStyle(
            "-fx-font-weight:bold;"
        );

        label.setTextFill(
            Color.web("#46516B")
        );

        if (control instanceof TextField) {
            control.setPrefWidth(260);
        }

        box.getChildren().addAll(
            label,
            control
        );

        return box;
    }

  

    private VBox createCard(
        String title,
        String description
    ) {

        VBox card =
            new VBox(12);

        card.setPadding(
            new Insets(18)
        );

        card.setStyle(
            "-fx-background-color:#E7EBF8;" +
            "-fx-background-radius:13;" +
            "-fx-border-color:#D0D7EC;" +
            "-fx-border-radius:13;" +
            "-fx-effect:dropshadow(" +
            "gaussian,rgba(55,70,120,.10),12,.2,0,3);"
        );

        Label heading =
            new Label(title);

        heading.setFont(
            Font.font("Arial", 15)
        );

        heading.setStyle(
            "-fx-font-weight:bold;"
        );

        heading.setTextFill(
            Color.web("#202A44")
        );

        Label sub =
            new Label(description);

        sub.setFont(
            Font.font("Arial", 10)
        );

        sub.setTextFill(
            Color.web("#727D94")
        );

        card.getChildren().addAll(
            heading,
            sub
        );

        return card;
    }



    private Button smallButton(
        String text
    ) {

        Button button =
            new Button(text);

        button.setPrefHeight(34);

        button.setStyle(
            "-fx-background-color:#E1E6FA;" +
            "-fx-text-fill:#4E61B7;" +
            "-fx-border-color:#C7CFEC;" +
            "-fx-border-radius:7;" +
            "-fx-background-radius:7;" +
            "-fx-font-size:10px;" +
            "-fx-font-weight:bold;" +
            "-fx-cursor:hand;"
        );

        return button;
    }



    private HBox securityRow(
        String title,
        String description,
        Button button
    ) {

        HBox row =
            new HBox();

        row.setAlignment(
            Pos.CENTER_LEFT
        );

        VBox text =
            new VBox(2);

        Label titleLabel =
            new Label(title);

        titleLabel.setFont(
            Font.font("Arial", 11)
        );

        titleLabel.setStyle(
            "-fx-font-weight:bold;"
        );

        titleLabel.setTextFill(
            Color.web("#303B54")
        );

        Label descriptionLabel =
            new Label(description);

        descriptionLabel.setFont(
            Font.font("Arial", 9)
        );

        descriptionLabel.setTextFill(
            Color.web("#778198")
        );

        text.getChildren().addAll(
            titleLabel,
            descriptionLabel
        );

        Region space =
            new Region();

        HBox.setHgrow(
            space,
            Priority.ALWAYS
        );

        row.getChildren().addAll(
            text,
            space,
            button
        );

        return row;
    }
}
