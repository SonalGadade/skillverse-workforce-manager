
package com.skillverse.trainer.view;

import com.skillverse.trainer.controller.RegisterController;
import com.skillverse.trainer.model.Trainer;

import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RegisterPage {

    public static void show(Stage stage) {

        BorderPane root =
                new BorderPane();

        root.setStyle(
                "-fx-background-color:" +
                AppTheme.BG +
                ";"
        );

        VBox left =
                new VBox(18);

        left.setPadding(
                new Insets(50)
        );

        left.setPrefWidth(450);

        ImageView logoImageView = null;
        try {
            InputStream is = RegisterPage.class.getResourceAsStream("/assets/Main Logo.jpeg");
            if (is == null) is = RegisterPage.class.getResourceAsStream("/Main Logo.jpeg");
            if (is != null) {
                Image img = new Image(is);
                logoImageView = new ImageView(img);
                logoImageView.setFitHeight(45);
                logoImageView.setPreserveRatio(true);
                logoImageView.setSmooth(true);
            }
        } catch (Exception ex) {}

        Label logoLabel = new Label("SkillVerse");
        logoLabel.setStyle("-fx-font-size:24;-fx-font-weight:bold;-fx-text-fill:#0F172A;");

        HBox logoBox = new HBox(10);
        logoBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        if (logoImageView != null) {
            logoBox.getChildren().addAll(logoImageView, logoLabel);
        } else {
            logoBox.getChildren().add(logoLabel);
        }

        Label role =
                new Label(
                        "TRAINER WORKSPACE"
                );

        role.setStyle(
                "-fx-text-fill:" +
                AppTheme.BLUE +
                ";-fx-font-weight:bold;"
        );

        Label heading =
                new Label(
                        "Build your Trainer\nprofile."
                );

        heading.setStyle(
                "-fx-font-size:36;" +
                "-fx-font-weight:bold;"
        );

        Label description =
                AppTheme.subtitle(
                        "Complete your profile and start creating courses,\n" +
                        "assessments and learner experiences."
                );

        left.getChildren().addAll(
                logoBox,
                role,
                heading,
                description
        );

        String[] features = {
                "Personalized trainer workspace",
                "Course and assessment management",
                "Learner progress insights"
        };

        for (String feature :
                features) {

            left.getChildren().add(
                    AppTheme.card(
                            new Label(
                                    "✓  " + feature
                            )
                    )
            );
        }

        VBox card =
                new VBox(11);

        card.setPadding(
                new Insets(30)
        );

        card.setMaxWidth(570);

        card.setStyle(
                "-fx-background-color:white;" +
                "-fx-background-radius:18;" +
                "-fx-border-color:" +
                AppTheme.BORDER +
                ";" +
                "-fx-border-radius:18;"
        );

        Hyperlink back =
                new Hyperlink(
                        "‹ Back to Sign In"
                );

        back.setStyle(
                "-fx-text-fill:" +
                AppTheme.BLUE +
                ";"
        );

        back.setOnAction(
                e -> LoginPage.show(stage)
        );

        TextField name =
                AppTheme.field(
                        "Enter full name"
                );

        TextField email =
                AppTheme.field(
                        "Enter email"
                );

        PasswordField password =
                AppTheme.passwordField(
                        "Enter password"
                );

        PasswordField confirm =
                AppTheme.passwordField(
                        "Re-enter your password"
                );

        TextField phone =
                AppTheme.field(
                        "Enter phone number"
                );

        TextArea address =
                new TextArea();

        address.setPromptText(
                "Enter your address"
        );

        address.setPrefRowCount(2);

        address.setStyle(
                "-fx-background-color:white;" +
                "-fx-border-color:" +
                AppTheme.BORDER +
                ";" +
                "-fx-border-radius:8;" +
                "-fx-background-radius:8;"
        );

        ToggleGroup genderGroup =
                new ToggleGroup();

        RadioButton male =
                new RadioButton("Male");

        RadioButton female =
                new RadioButton("Female");

        male.setToggleGroup(
                genderGroup
        );

        female.setToggleGroup(
                genderGroup
        );

        female.setSelected(true);

        HBox gender =
                new HBox(
                        18,
                        male,
                        female
                );

        Button create =
                AppTheme.primary(
                        "Create Trainer Account"
                );

        RegisterController controller =
                new RegisterController(stage);

        create.setOnAction(e -> {

            String selectedGender =
                    female.isSelected()
                            ? "Female"
                            : "Male";

            Trainer trainer =
                    controller.register(
                            name.getText(),
                            email.getText(),
                            password.getText(),
                            confirm.getText(),
                            phone.getText(),
                            address.getText(),
                            selectedGender
                    );

            if (trainer != null) {

                DashboardPage.show(
                        stage,
                        trainer
                );
            }
        });

        card.getChildren().addAll(
                back,

                AppTheme.title(
                        "Create your Trainer profile"
                ),

                AppTheme.subtitle(
                        "Enter your details to complete your SkillVerse account."
                ),

                new Label("Full Name *"),
                name,

                new Label("Email Address *"),
                email,

                new Label("Password *"),
                password,

                new Label("Confirm Password *"),
                confirm,

                new Label("Phone Number"),
                phone,

                new Label("Address"),
                address,

                new Label("Gender"),
                gender,

                create,

                new Label(
                        "Your information is securely protected."
                )
        );

        StackPane center =
                new StackPane(card);

        center.setPadding(
                new Insets(30)
        );

        root.setLeft(left);
        root.setCenter(center);

        stage.setTitle(
                "SkillVerse - Create Trainer Profile"
        );

        stage.setScene(
                new Scene(
                        root,
                        1200,
                        760
                )
        );

        stage.show();
    }
}