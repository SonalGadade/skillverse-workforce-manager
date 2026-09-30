package com.skillverse.hr.view;

import com.skillverse.hr.controller.HRSigninController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class HRSignin {

    private Scene Signscene;

    private VBox signupCard;

    private VBox originalContent;

    private HRSigninController controller;

    public HRSignin() {

        controller = new HRSigninController();

        HBox root = new HBox();

        root.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #F5F9FF, #EEF4FF);"
        );

        VBox leftSection = new VBox();

        leftSection.setPrefWidth(700);

        leftSection.setPadding(
            new Insets(45, 55, 30, 70)
        );

        leftSection.setStyle(
            "-fx-background-color: transparent;"
        );

        Label logoSymbol = new Label("SV");

        logoSymbol.setStyle(
            "-fx-text-fill: #1769E0;" +
            "-fx-font-size: 32px;" +
            "-fx-font-weight: bold;"
        );

        Label logoText = new Label("SkillVerse");

        logoText.setStyle(
            "-fx-text-fill: #101A33;" +
            "-fx-font-size: 29px;" +
            "-fx-font-weight: bold;"
        );

        Label logoSubtitle = new Label(
            "AI Powered Internal Talent Ecosystem"
        );

        logoSubtitle.setStyle(
            "-fx-text-fill: #52627A;" +
            "-fx-font-size: 14px;"
        );

        VBox logoTextBox = new VBox(
            0,
            logoText,
            logoSubtitle
        );

        HBox logo = new HBox(
            12,
            logoSymbol,
            logoTextBox
        );

        logo.setAlignment(
            Pos.CENTER_LEFT
        );

        Label welcome = new Label(
            "Create your HR account"
        );

        welcome.setStyle(
            "-fx-text-fill: #101A33;" +
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;"
        );

        HBox headingLine = new HBox(
            8
        );

        Label skillVerseHeading = new Label(
            "Join SkillVerse"
        );

        skillVerseHeading.setStyle(
            "-fx-text-fill: #1769E0;" +
            "-fx-font-size: 40px;" +
            "-fx-font-weight: bold;"
        );

        Label platformHeading = new Label(
            "HR Platform"
        );

        platformHeading.setStyle(
            "-fx-text-fill: #101A33;" +
            "-fx-font-size: 40px;" +
            "-fx-font-weight: bold;"
        );

        headingLine.getChildren().addAll(
            skillVerseHeading,
            platformHeading
        );

        Label description = new Label(
            "Build stronger teams. Discover talent.\n" +
            "Drive organizational growth with SkillVerse."
        );

        description.setStyle(
            "-fx-text-fill: #53627A;" +
            "-fx-font-size: 18px;" +
            "-fx-line-spacing: 7px;"
        );

        StackPane dashboardArea = new StackPane();

        dashboardArea.setPrefHeight(300);

        dashboardArea.setMaxWidth(580);

        VBox dashboardCard = new VBox();

        dashboardCard.setPrefSize(
            410,
            225
        );

        dashboardCard.setMaxSize(
            410,
            225
        );

        dashboardCard.setPadding(
            new Insets(18)
        );

        dashboardCard.setStyle(
            "-fx-background-color: rgba(255,255,255,0.94);" +
            "-fx-background-radius: 18px;" +
            "-fx-border-color: #DCE8FF;" +
            "-fx-border-radius: 18px;" +
            "-fx-border-width: 1px;"
        );

        DropShadow dashboardShadow = new DropShadow();

        dashboardShadow.setRadius(20);

        dashboardShadow.setOffsetY(8);

        dashboardShadow.setColor(
            Color.rgb(60, 110, 190, 0.16)
        );

        dashboardCard.setEffect(
            dashboardShadow
        );

        HBox browserBar = new HBox(
            7
        );

        Label dot1 = new Label("●");
        Label dot2 = new Label("●");
        Label dot3 = new Label("●");

        dot1.setStyle(
            "-fx-text-fill: #A9C9FF;"
        );

        dot2.setStyle(
            "-fx-text-fill: #C8D9FF;"
        );

        dot3.setStyle(
            "-fx-text-fill: #DDE7FF;"
        );

        browserBar.getChildren().addAll(
            dot1,
            dot2,
            dot3
        );

        HBox dashboardContent = new HBox(
            15
        );

        VBox sidebar = new VBox(
            10
        );

        Label side1 = new Label("▰");
        Label side2 = new Label("●");
        Label side3 = new Label("▰");
        Label side4 = new Label("●");
        Label side5 = new Label("▰");

        side1.setStyle(
            "-fx-text-fill: #347CF4;"
        );

        side2.setStyle(
            "-fx-text-fill: #A9BDE8;"
        );

        side3.setStyle(
            "-fx-text-fill: #A9BDE8;"
        );

        side4.setStyle(
            "-fx-text-fill: #A9BDE8;"
        );

        side5.setStyle(
            "-fx-text-fill: #A9BDE8;"
        );

        sidebar.getChildren().addAll(
            side1,
            side2,
            side3,
            side4,
            side5
        );

        VBox chartBox = new VBox(
            12
        );

        chartBox.setPadding(
            new Insets(12)
        );

        chartBox.setStyle(
            "-fx-background-color: #F6F9FF;" +
            "-fx-background-radius: 12px;"
        );

        Label chartTitle = new Label(
            "Talent Overview"
        );

        chartTitle.setStyle(
            "-fx-text-fill: #263B61;" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );

        HBox bars = new HBox(
            8
        );

        int[] heights = {
            42, 72, 55, 105, 82, 118
        };

        for (int height : heights) {

            Region bar = new Region();

            bar.setPrefWidth(20);

            bar.setPrefHeight(
                height
            );

            bar.setStyle(
                "-fx-background-color: linear-gradient(to top, #3178F5, #7D5AEF);" +
                "-fx-background-radius: 8px 8px 2px 2px;"
            );

            bars.getChildren().add(
                bar
            );
        }

        chartBox.getChildren().addAll(
            chartTitle,
            bars
        );

        VBox employeeBox = new VBox(
            7
        );

        employeeBox.setPadding(
            new Insets(12)
        );

        employeeBox.setStyle(
            "-fx-background-color: #F7FAFF;" +
            "-fx-background-radius: 12px;"
        );

        Label employeeTitle = new Label(
            "New Talent"
        );

        employeeTitle.setStyle(
            "-fx-text-fill: #6B7890;" +
            "-fx-font-size: 11px;"
        );

        Label employeeNumber = new Label(
            "24"
        );

        employeeNumber.setStyle(
            "-fx-text-fill: #182A4B;" +
            "-fx-font-size: 25px;" +
            "-fx-font-weight: bold;"
        );

        Label employeeChange = new Label(
            "↗ 18.2%"
        );

        employeeChange.setStyle(
            "-fx-text-fill: #20B879;" +
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;"
        );

        employeeBox.getChildren().addAll(
            employeeTitle,
            employeeNumber,
            employeeChange
        );

        dashboardContent.getChildren().addAll(
            sidebar,
            chartBox,
            employeeBox
        );

        dashboardCard.getChildren().addAll(
            browserBar,
            dashboardContent
        );

        VBox.setMargin(
            dashboardContent,
            new Insets(15, 0, 0, 0)
        );

        Label peopleIcon = new Label(
            "👥"
        );

        peopleIcon.setStyle(
            "-fx-background-color: linear-gradient(to bottom right,#4488F7,#7650E8);" +
            "-fx-background-radius: 18px;" +
            "-fx-padding: 14px;" +
            "-fx-font-size: 22px;"
        );

        Label chartIcon = new Label(
            "▥"
        );

        chartIcon.setStyle(
            "-fx-background-color: #4285F4;" +
            "-fx-background-radius: 10px;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 24px;" +
            "-fx-padding: 8px 12px;"
        );

        Label checkIcon = new Label(
            "✓"
        );

        checkIcon.setStyle(
            "-fx-background-color: #55D39A;" +
            "-fx-background-radius: 10px;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 24px;" +
            "-fx-padding: 8px 12px;"
        );

        StackPane.setAlignment(
            dashboardCard,
            Pos.CENTER
        );

        StackPane.setAlignment(
            peopleIcon,
            Pos.TOP_RIGHT
        );

        StackPane.setMargin(
            peopleIcon,
            new Insets(20, 20, 0, 0)
        );

        StackPane.setAlignment(
            chartIcon,
            Pos.BOTTOM_LEFT
        );

        StackPane.setMargin(
            chartIcon,
            new Insets(0, 0, 30, 25)
        );

        StackPane.setAlignment(
            checkIcon,
            Pos.BOTTOM_RIGHT
        );

        StackPane.setMargin(
            checkIcon,
            new Insets(0, 20, 25, 0)
        );

        dashboardArea.getChildren().addAll(
            dashboardCard,
            peopleIcon,
            chartIcon,
            checkIcon
        );

        HBox features = new HBox(
            35
        );

        features.setAlignment(
            Pos.CENTER_LEFT
        );

        features.getChildren().add(
            createFeature(
                "👥",
                "People",
                "Management",
                "#EAF2FF",
                "#2675E8"
            )
        );

        features.getChildren().add(
            createFeature(
                "⌁",
                "Performance",
                "Analytics",
                "#F0EBFF",
                "#7853E8"
            )
        );

        features.getChildren().add(
            createFeature(
                "✓",
                "Smart",
                "Recruitment",
                "#E7FAF2",
                "#25B879"
            )
        );

        features.getChildren().add(
            createFeature(
                "★",
                "AI Powered",
                "Insights",
                "#FFF3E5",
                "#F39B2F"
            )
        );

        VBox leftContent = new VBox(
            18
        );

        leftContent.getChildren().addAll(
            welcome,
            headingLine,
            description,
            dashboardArea,
            features
        );

        VBox.setMargin(
            dashboardArea,
            new Insets(5, 0, -5, 0)
        );

        VBox.setVgrow(
            dashboardArea,
            Priority.ALWAYS
        );

        Label footer = new Label(
            "© 2024 SkillVerse. All rights reserved."
        );

        footer.setStyle(
            "-fx-text-fill: #71819A;" +
            "-fx-font-size: 13px;"
        );

        leftSection.getChildren().addAll(
            logo,
            leftContent,
            footer
        );

        VBox.setMargin(
            leftContent,
            new Insets(55, 0, 0, 0)
        );

        VBox.setMargin(
            footer,
            new Insets(20, 0, 0, 0)
        );

        StackPane rightSection = new StackPane();

        rightSection.setPrefWidth(
            720
        );

        rightSection.setPadding(
            new Insets(35, 65, 35, 25)
        );

        signupCard = new VBox();

        signupCard.setPrefWidth(
            560
        );

        signupCard.setMaxWidth(
            560
        );

        signupCard.setPadding(
            new Insets(35, 60, 32, 60)
        );

        signupCard.setAlignment(
            Pos.TOP_CENTER
        );

        signupCard.setStyle(
            "-fx-background-color: rgba(255,255,255,0.97);" +
            "-fx-background-radius: 24px;"
        );

        DropShadow cardShadow = new DropShadow();

        cardShadow.setRadius(
            30
        );

        cardShadow.setOffsetY(
            12
        );

        cardShadow.setColor(
            Color.rgb(70, 100, 160, 0.15)
        );

        signupCard.setEffect(
            cardShadow
        );

        Hyperlink back = new Hyperlink(
            "‹  Back to HR Page"
        );

        back.setStyle(
            "-fx-text-fill: #65738B;" +
            "-fx-font-size: 14px;"
        );

        back.setOnAction(e ->
            controller.openHR()
        );

        Label signIcon = new Label(
            "✦"
        );

        signIcon.setStyle(
            "-fx-background-color: #EAF2FF;" +
            "-fx-background-radius: 60px;" +
            "-fx-text-fill: #1769E0;" +
            "-fx-font-size: 32px;" +
            "-fx-padding: 14px 20px;"
        );

        Label title = new Label(
            "Create HR Account"
        );

        title.setStyle(
            "-fx-text-fill: #101A33;" +
            "-fx-font-size: 27px;" +
            "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
            "Enter your details to create your SkillVerse HR account"
        );

        subtitle.setWrapText(
            true
        );

        subtitle.setAlignment(
            Pos.CENTER
        );

        subtitle.setStyle(
            "-fx-text-fill: #65738B;" +
            "-fx-font-size: 13px;"
        );

        Label emailLabel = new Label(
            "Email Address"
        );

        emailLabel.setStyle(
            "-fx-text-fill: #101A33;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;"
        );

        TextField EmailField = new TextField();

        EmailField.setPromptText(
            "Enter your email"
        );

        EmailField.setPrefHeight(
            52
        );

        EmailField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #C7D0DD;" +
            "-fx-border-width: 1px;" +
            "-fx-border-radius: 9px;" +
            "-fx-background-radius: 9px;" +
            "-fx-font-size: 15px;" +
            "-fx-padding: 14px;" +
            "-fx-text-fill: #17243D;" +
            "-fx-prompt-text-fill: #7C8799;"
        );

        Label phoneLabel = new Label(
            "Phone Number"
        );

        phoneLabel.setStyle(
            "-fx-text-fill: #101A33;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;"
        );

        TextField PhoneField = new TextField();

        PhoneField.setPromptText(
            "Enter your phone number"
        );

        PhoneField.setPrefHeight(
            52
        );

        PhoneField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #C7D0DD;" +
            "-fx-border-width: 1px;" +
            "-fx-border-radius: 9px;" +
            "-fx-background-radius: 9px;" +
            "-fx-font-size: 15px;" +
            "-fx-padding: 14px;" +
            "-fx-text-fill: #17243D;" +
            "-fx-prompt-text-fill: #7C8799;"
        );

        CheckBox otpCheckBox = new CheckBox(
            "Send OTP for verification"
        );

        otpCheckBox.setSelected(
            false
        );

        otpCheckBox.setStyle(
            "-fx-text-fill: #52627A;" +
            "-fx-font-size: 14px;"
        );

        HBox otpSection = new HBox(
            9
        );

        otpSection.setAlignment(
            Pos.CENTER
        );

        PasswordField[] otpFields =
            new PasswordField[6];

        for (int i = 0; i < 6; i++) {

            PasswordField otpField =
                new PasswordField();

            otpField.setPrefSize(
                48,
                50
            );

            otpField.setMinSize(
                48,
                50
            );

            otpField.setMaxSize(
                48,
                50
            );

            otpField.setAlignment(
                Pos.CENTER
            );

            otpField.setPromptText(
                "•"
            );

            otpField.setStyle(
                "-fx-background-color: #F9FBFF;" +
                "-fx-border-color: #C7D0DD;" +
                "-fx-border-width: 1.5px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;" +
                "-fx-font-size: 19px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #17243D;" +
                "-fx-prompt-text-fill: #B3BECE;" +
                "-fx-padding: 0;"
            );

            otpFields[i] = otpField;

            final int index = i;

            otpField.addEventFilter(
                KeyEvent.KEY_TYPED,
                event -> {

                    String character =
                        event.getCharacter();

                    if (!character.matches("[0-9]")) {

                        event.consume();

                        return;
                    }

                    otpField.setText(
                        character
                    );

                    event.consume();

                    otpField.setStyle(
                        "-fx-background-color: #F4F8FF;" +
                        "-fx-border-color: #347CF4;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-font-size: 19px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #17243D;" +
                        "-fx-padding: 0;"
                    );

                    if (index < 5) {

                        otpFields[index + 1]
                            .requestFocus();
                    }
                }
            );

            otpField.setOnKeyPressed(
                event -> {

                    if (
                        event.getCode()
                            == KeyCode.BACK_SPACE
                    ) {

                        if (
                            !otpField
                                .getText()
                                .isEmpty()
                        ) {

                            otpField.clear();

                            otpField.setStyle(
                                "-fx-background-color: #F9FBFF;" +
                                "-fx-border-color: #C7D0DD;" +
                                "-fx-border-width: 1.5px;" +
                                "-fx-border-radius: 10px;" +
                                "-fx-background-radius: 10px;" +
                                "-fx-font-size: 19px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-text-fill: #17243D;" +
                                "-fx-prompt-text-fill: #B3BECE;" +
                                "-fx-padding: 0;"
                            );

                        } else if (
                            index > 0
                        ) {

                            otpFields[index - 1]
                                .requestFocus();

                            otpFields[index - 1]
                                .clear();

                            otpFields[index - 1]
                                .setStyle(
                                    "-fx-background-color: #F9FBFF;" +
                                    "-fx-border-color: #C7D0DD;" +
                                    "-fx-border-width: 1.5px;" +
                                    "-fx-border-radius: 10px;" +
                                    "-fx-background-radius: 10px;" +
                                    "-fx-font-size: 19px;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-text-fill: #17243D;" +
                                    "-fx-prompt-text-fill: #B3BECE;" +
                                    "-fx-padding: 0;"
                                );
                        }
                    }

                    if (
                        event.getCode()
                            == KeyCode.LEFT
                    ) {

                        if (index > 0) {

                            otpFields[index - 1]
                                .requestFocus();
                        }
                    }

                    if (
                        event.getCode()
                            == KeyCode.RIGHT
                    ) {

                        if (index < 5) {

                            otpFields[index + 1]
                                .requestFocus();
                        }
                    }
                }
            );

            otpSection.getChildren().add(
                otpField
            );
        }

        otpSection.setVisible(
            false
        );

        otpSection.setManaged(
            false
        );

        Label otpStatus = new Label(
            "Enter the 6-digit OTP sent to your phone"
        );

        otpStatus.setStyle(
            "-fx-text-fill: #718096;" +
            "-fx-font-size: 11px;"
        );

        otpStatus.setVisible(
            false
        );

        otpStatus.setManaged(
            false
        );

        otpCheckBox.setOnAction(e -> {

            boolean selected =
                otpCheckBox.isSelected();

            otpSection.setVisible(
                selected
            );

            otpSection.setManaged(
                selected
            );

            otpStatus.setVisible(
                selected
            );

            otpStatus.setManaged(
                selected
            );
        });

        Button NewAccbtn = new Button(
            "Create your account"
        );

        NewAccbtn.setMaxWidth(
            Double.MAX_VALUE
        );

        NewAccbtn.setPrefHeight(
            55
        );

        NewAccbtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #347CF4, #2E6FE7);" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 9px;" +
            "-fx-cursor: hand;"
        );

        NewAccbtn.setOnMouseEntered(e -> {

            NewAccbtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #246BE5, #245ED2);" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9px;" +
                "-fx-cursor: hand;"
            );
        });

        NewAccbtn.setOnMouseExited(e -> {

            NewAccbtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #347CF4, #2E6FE7);" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9px;" +
                "-fx-cursor: hand;"
            );
        });

        Label security = new Label(
            "🔒  Your information is securely encrypted"
        );

        security.setStyle(
            "-fx-text-fill: #8CA1B4;" +
            "-fx-font-size: 11px;"
        );

        Label orText = new Label(
            "Already have an account?"
        );

        orText.setStyle(
            "-fx-text-fill: #718096;" +
            "-fx-font-size: 13px;"
        );

        Hyperlink loginLink = new Hyperlink(
            "Login"
        );

        loginLink.setStyle(
            "-fx-text-fill: #155ED4;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;"
        );

        loginLink.setOnAction(e ->
            controller.openLogin()
        );

        HBox loginRow = new HBox(
            4,
            orText,
            loginLink
        );

        loginRow.setAlignment(
            Pos.CENTER
        );

        VBox form = new VBox(
            8
        );

        form.setMaxWidth(
            Double.MAX_VALUE
        );

        form.getChildren().addAll(
            emailLabel,
            EmailField,
            phoneLabel,
            PhoneField,
            otpCheckBox,
            otpSection,
            otpStatus,
            NewAccbtn,
            security,
            loginRow
        );

        VBox.setMargin(
            otpCheckBox,
            new Insets(3, 0, 0, 0)
        );

        VBox.setMargin(
            otpSection,
            new Insets(5, 0, 0, 0)
        );

        VBox.setMargin(
            otpStatus,
            new Insets(1, 0, 2, 0)
        );

        VBox.setMargin(
            NewAccbtn,
            new Insets(4, 0, 0, 0)
        );

        VBox.setMargin(
            security,
            new Insets(3, 0, 0, 0)
        );

        VBox.setMargin(
            loginRow,
            new Insets(7, 0, 0, 0)
        );

        VBox signContent = new VBox(
            9
        );

        signContent.setMaxWidth(
            Double.MAX_VALUE
        );

        signContent.setAlignment(
            Pos.TOP_CENTER
        );

        signContent.getChildren().addAll(
            signIcon,
            title,
            subtitle,
            form
        );

        VBox.setMargin(
            signIcon,
            new Insets(0, 0, 2, 0)
        );

        VBox.setMargin(
            subtitle,
            new Insets(0, 0, 7, 0)
        );

        originalContent = signContent;

        signupCard.getChildren().addAll(
            back,
            signContent
        );

        VBox.setMargin(
            signContent,
            new Insets(16, 0, 0, 0)
        );

        NewAccbtn.setOnAction(e -> {

            if (otpCheckBox.isSelected()) {

                StringBuilder enteredOTP =
                    new StringBuilder();

                for (
                    PasswordField field :
                    otpFields
                ) {

                    enteredOTP.append(
                        field.getText()
                    );
                }

                if (
                    enteredOTP.length() != 6
                ) {

                    Alert alert =
                        new Alert(
                            Alert.AlertType.WARNING
                        );

                    alert.setTitle(
                        "Invalid OTP"
                    );

                    alert.setHeaderText(
                        null
                    );

                    alert.setContentText(
                        "Please enter all 6 digits of the OTP."
                    );

                    alert.showAndWait();

                    return;
                }

                Alert alert =
                    new Alert(
                        Alert.AlertType.INFORMATION
                    );

                alert.setTitle(
                    "OTP Verified"
                );

                alert.setHeaderText(
                    null
                );

                alert.setContentText(
                    "Your phone number has been verified successfully."
                );

                alert.showAndWait();

                controller.openCreateAccount();

            } else {

                controller.openCreateAccount();
            }
        });

        rightSection.getChildren().add(
            signupCard
        );

        HBox.setHgrow(
            leftSection,
            Priority.ALWAYS
        );

        HBox.setHgrow(
            rightSection,
            Priority.ALWAYS
        );

        root.getChildren().addAll(
            leftSection,
            rightSection
        );

        Signscene = new Scene(
            root,
            1536,
            1024
        );
    }

    private VBox createFeature(
        String icon,
        String title,
        String subtitle,
        String background,
        String textColor
    ) {

        Label iconLabel = new Label(
            icon
        );

        iconLabel.setStyle(
            "-fx-background-color: " + background + ";" +
            "-fx-background-radius: 12px;" +
            "-fx-text-fill: " + textColor + ";" +
            "-fx-font-size: 23px;" +
            "-fx-padding: 10px 13px;"
        );

        Label titleLabel = new Label(
            title
        );

        titleLabel.setStyle(
            "-fx-text-fill: #18243C;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;"
        );

        Label subtitleLabel = new Label(
            subtitle
        );

        subtitleLabel.setStyle(
            "-fx-text-fill: #18243C;" +
            "-fx-font-size: 14px;"
        );

        VBox box = new VBox(
            3,
            iconLabel,
            titleLabel,
            subtitleLabel
        );

        box.setAlignment(
            Pos.CENTER
        );

        box.setMinWidth(
            105
        );

        return box;
    }

    public Scene getScene() {

        return Signscene;
    }
}