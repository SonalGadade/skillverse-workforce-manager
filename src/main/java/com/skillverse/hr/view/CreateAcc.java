package com.skillverse.hr.view;

import com.skillverse.hr.controller.CreateAccController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CreateAcc {

    private Scene CreateAccscene;

    private final String primaryBlue = "#2563EB";
    private final String lightBlue = "#EEF4FF";
    private final String background = "#F5F7FC";
    private final String white = "#FFFFFF";

    private final String mainHeading = "#111827";
    private final String darkNavy = "#111C3D";
    private final String secondaryText = "#64748B";
    private final String mutedText = "#94A3B8";
    private final String border = "#E5EAF3";

    public CreateAcc() {

        VBox leftPanel = new VBox();

        leftPanel.setPrefWidth(470);
        leftPanel.setMinWidth(470);

        leftPanel.setPadding(
                new Insets(38, 42, 35, 42)
        );

        leftPanel.setStyle(
                "-fx-background-color: " + white + ";" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-width: 0 1px 0 0;"
        );

        Label logoIcon = new Label("S");

        logoIcon.setAlignment(Pos.CENTER);

        logoIcon.setPrefSize(38, 38);

        logoIcon.setStyle(
                "-fx-background-color: " + primaryBlue + ";" +
                "-fx-background-radius: 11px;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );

        Label logoText = new Label("SkillVerse");

        logoText.setStyle(
                "-fx-text-fill: " + darkNavy + ";" +
                "-fx-font-size: 21px;" +
                "-fx-font-weight: bold;"
        );

        HBox logo = new HBox(
                11,
                logoIcon,
                logoText
        );

        logo.setAlignment(Pos.CENTER_LEFT);

        Label platform = new Label("HR PLATFORM");

        platform.setStyle(
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
        );

        VBox.setVgrow(logo, Priority.NEVER);

        VBox leftContent = new VBox(18);

        leftContent.setAlignment(Pos.CENTER_LEFT);

        VBox.setVgrow(leftContent, Priority.ALWAYS);

        Label profileIcon = new Label("HR");

        profileIcon.setAlignment(Pos.CENTER);

        profileIcon.setPrefSize(62, 62);

        profileIcon.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-background-radius: 18px;" +
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;"
        );

        Label heading = new Label(
                "Build your HR\nworkspace"
        );

        heading.setStyle(
                "-fx-text-fill: " + mainHeading + ";" +
                "-fx-font-size: 34px;" +
                "-fx-font-weight: bold;"
        );

        Label description = new Label(
                "Complete your profile and start managing " +
                "your workforce with SkillVerse."
        );

        description.setWrapText(true);

        description.setStyle(
                "-fx-text-fill: " + secondaryText + ";" +
                "-fx-font-size: 14px;"
        );

        VBox feature1 = createFeature(
                "01",
                "Personalized HR workspace",
                "Everything you need in one place."
        );

        VBox feature2 = createFeature(
                "02",
                "Employee management",
                "Organize and manage your workforce."
        );

        VBox feature3 = createFeature(
                "03",
                "Workforce insights",
                "Track performance and organizational growth."
        );

        leftContent.getChildren().addAll(
                profileIcon,
                heading,
                description,
                feature1,
                feature2,
                feature3
        );

        Label footer = new Label(
                "Secure • Reliable • Powered by SkillVerse"
        );

        footer.setStyle(
                "-fx-text-fill: " + mutedText + ";" +
                "-fx-font-size: 11px;"
        );

        leftPanel.getChildren().addAll(
                logo,
                platform,
                leftContent,
                footer
        );

        VBox rightPanel = new VBox();

        rightPanel.setPadding(
                new Insets(30, 70, 30, 70)
        );

        rightPanel.setStyle(
                "-fx-background-color: " + background + ";"
        );

        Hyperlink back = new Hyperlink(
                "‹  Back to Sign In"
        );

        back.setStyle(
                "-fx-text-fill: " + secondaryText + ";" +
                "-fx-font-size: 12px;" +
                "-fx-border-color: transparent;" +
                "-fx-padding: 5px 0;" +
                "-fx-cursor: hand;"
        );

        back.setOnMouseEntered(e -> {

            back.setStyle(
                    "-fx-text-fill: " + primaryBlue + ";" +
                    "-fx-font-size: 12px;" +
                    "-fx-border-color: transparent;" +
                    "-fx-padding: 5px 0;" +
                    "-fx-cursor: hand;"
            );

        });

        back.setOnMouseExited(e -> {

            back.setStyle(
                    "-fx-text-fill: " + secondaryText + ";" +
                    "-fx-font-size: 12px;" +
                    "-fx-border-color: transparent;" +
                    "-fx-padding: 5px 0;" +
                    "-fx-cursor: hand;"
            );

        });

        VBox formCard = new VBox(17);

        formCard.setPadding(new Insets(30));

        formCard.setMaxWidth(540);

        formCard.setStyle(
                "-fx-background-color: " + white + ";" +
                "-fx-background-radius: 22px;" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-radius: 22px;" +
                "-fx-effect: dropshadow(gaussian, rgba(17,24,39,0.06), 16, 0, 0, 4);"
        );

        Label formIcon = new Label("HR");

        formIcon.setAlignment(Pos.CENTER);

        formIcon.setPrefSize(48, 48);

        formIcon.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-background-radius: 14px;" +
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;"
        );

        Label title = new Label(
                "Create your HR profile"
        );

        title.setStyle(
                "-fx-text-fill: " + mainHeading + ";" +
                "-fx-font-size: 27px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Enter your details to complete your SkillVerse account."
        );

        subtitle.setWrapText(true);

        subtitle.setStyle(
                "-fx-text-fill: " + secondaryText + ";" +
                "-fx-font-size: 13px;"
        );

        formCard.getChildren().addAll(
                formIcon,
                title,
                subtitle
        );

        Label nameLabel = createFieldLabel("FULL NAME");

        TextField nameField = createTextField(
                "Enter your full name"
        );

        Label passwordLabel = createFieldLabel("PASSWORD");

        PasswordField passwordField =
                createPasswordField(
                        "Enter your password"
                );

        Label confirmPasswordLabel =
                createFieldLabel(
                        "CONFIRM PASSWORD"
                );

        PasswordField confirmPasswordField =
                createPasswordField(
                        "Re-enter your password"
                );

        Label addressLabel = createFieldLabel("ADDRESS");

        TextArea addressField = new TextArea();

        addressField.setPromptText(
                "Enter your address"
        );

        addressField.setPrefHeight(65);

        addressField.setWrapText(true);

        addressField.setStyle(
                getTextAreaStyle()
        );

        Label genderLabel = createFieldLabel("GENDER");

        RadioButton male = new RadioButton("Male");

        RadioButton female = new RadioButton("Female");

        male.setStyle(getRadioStyle());

        female.setStyle(getRadioStyle());

        ToggleGroup genderGroup = new ToggleGroup();

        male.setToggleGroup(genderGroup);

        female.setToggleGroup(genderGroup);

        HBox genderBox = new HBox(
                28,
                male,
                female
        );

        genderBox.setAlignment(Pos.CENTER_LEFT);

        Button submit = new Button(
                "Create HR Account"
        );

        submit.setMaxWidth(Double.MAX_VALUE);

        submit.setPrefHeight(46);

        submit.setStyle(getButtonStyle());

        submit.setOnMouseEntered(e -> {

            submit.setStyle(
                    "-fx-background-color: #1D4ED8;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 13px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 12px;" +
                    "-fx-cursor: hand;"
            );

        });

        submit.setOnMouseExited(e -> {

            submit.setStyle(
                    getButtonStyle()
            );

        });

        Label security = new Label(
                "Your account information is securely protected."
        );

        security.setStyle(
                "-fx-text-fill: " + mutedText + ";" +
                "-fx-font-size: 10px;"
        );

        VBox form = new VBox(
                7,
                nameLabel,
                nameField,
                passwordLabel,
                passwordField,
                confirmPasswordLabel,
                confirmPasswordField,
                addressLabel,
                addressField,
                genderLabel,
                genderBox,
                submit,
                security
        );

        form.setPadding(
                new Insets(5, 0, 0, 0)
        );

        formCard.getChildren().add(form);

        CreateAccController controller =
                new CreateAccController(
                        nameField,
                        passwordField,
                        confirmPasswordField,
                        back,
                        submit,
                        getErrorFieldStyle()
                );

        HBox formContainer = new HBox(formCard);

        formContainer.setAlignment(Pos.TOP_CENTER);

        VBox.setVgrow(
                formContainer,
                Priority.ALWAYS
        );

        rightPanel.getChildren().addAll(
                back,
                formContainer
        );

        HBox root = new HBox(
                leftPanel,
                rightPanel
        );

        HBox.setHgrow(
                rightPanel,
                Priority.ALWAYS
        );

        CreateAccscene = new Scene(
                root,
                1600,
                800
        );
    }

    private VBox createFeature(
            String number,
            String title,
            String description
    ) {

        Label numberLabel = new Label(number);

        numberLabel.setAlignment(Pos.CENTER);

        numberLabel.setPrefSize(32, 32);

        numberLabel.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-background-radius: 9px;" +
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
        );

        Label titleLabel = new Label(title);

        titleLabel.setStyle(
                "-fx-text-fill: " + mainHeading + ";" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;"
        );

        Label descriptionLabel = new Label(description);

        descriptionLabel.setStyle(
                "-fx-text-fill: " + mutedText + ";" +
                "-fx-font-size: 11px;"
        );

        VBox text = new VBox(
                3,
                titleLabel,
                descriptionLabel
        );

        HBox row = new HBox(
                11,
                numberLabel,
                text
        );

        row.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(row);

        card.setPadding(new Insets(13));

        card.setStyle(
                "-fx-background-color: " + background + ";" +
                "-fx-background-radius: 14px;" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-radius: 14px;"
        );

        return card;
    }

    private Label createFieldLabel(String text) {

        Label label = new Label(text);

        label.setStyle(
                "-fx-text-fill: " + secondaryText + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
        );

        return label;
    }

    private TextField createTextField(String prompt) {

        TextField field = new TextField();

        field.setPromptText(prompt);

        field.setPrefHeight(43);

        field.setStyle(
                getTextFieldStyle()
        );

        field.focusedProperty().addListener(
                (obs, oldValue, newValue) -> {

                    if (newValue) {

                        field.setStyle(
                                getFocusedFieldStyle()
                        );

                    } else {

                        field.setStyle(
                                getTextFieldStyle()
                        );
                    }
                }
        );

        return field;
    }

    private PasswordField createPasswordField(String prompt) {

        PasswordField field = new PasswordField();

        field.setPromptText(prompt);

        field.setPrefHeight(43);

        field.setStyle(
                getTextFieldStyle()
        );

        field.focusedProperty().addListener(
                (obs, oldValue, newValue) -> {

                    if (newValue) {

                        field.setStyle(
                                getFocusedFieldStyle()
                        );

                    } else {

                        field.setStyle(
                                getTextFieldStyle()
                        );
                    }
                }
        );

        return field;
    }

    private String getTextFieldStyle() {

        return
                "-fx-background-color: #FAFBFE;" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 11px;" +
                "-fx-background-radius: 11px;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 11px;" +
                "-fx-text-fill: " + mainHeading + ";" +
                "-fx-prompt-text-fill: " + mutedText + ";";
    }

    private String getFocusedFieldStyle() {

        return
                "-fx-background-color: " + white + ";" +
                "-fx-border-color: " + primaryBlue + ";" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 11px;" +
                "-fx-background-radius: 11px;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 11px;" +
                "-fx-text-fill: " + mainHeading + ";" +
                "-fx-prompt-text-fill: " + mutedText + ";";
    }

    private String getTextAreaStyle() {

        return
                "-fx-background-color: #FAFBFE;" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 11px;" +
                "-fx-background-radius: 11px;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 9px;" +
                "-fx-text-fill: " + mainHeading + ";" +
                "-fx-prompt-text-fill: " + mutedText + ";";
    }

    private String getRadioStyle() {

        return
                "-fx-text-fill: " + secondaryText + ";" +
                "-fx-font-size: 12px;";
    }

    private String getButtonStyle() {

        return
                "-fx-background-color: " + primaryBlue + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 12px;" +
                "-fx-cursor: hand;";
    }

    private String getErrorFieldStyle() {

        return
                "-fx-background-color: #FFF7F7;" +
                "-fx-border-color: #EF4444;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 11px;" +
                "-fx-background-radius: 11px;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 11px;" +
                "-fx-text-fill: " + mainHeading + ";" +
                "-fx-prompt-text-fill: " + mutedText + ";";
    }

    public Scene getScene() {

        return CreateAccscene;
    }
}