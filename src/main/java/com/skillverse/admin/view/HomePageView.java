package com.skillverse.admin.view;




import com.skillverse.admin.controller.HomePageController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class HomePageView {

    private final Stage stage;

    private Button adminButton;
    private Button hrButton;
    private Button managerButton;
    private Button employeeButton;
    private Button trainerButton;

    public HomePageView(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {

        BorderPane root =
                new BorderPane();

        root.setStyle(
                "-fx-background-color:linear-gradient(" +
                "to bottom right,#DCEBFF,#E9E4FF,#D5E8FF);"
        );


        HBox top = new HBox(12);

        top.setAlignment(
                Pos.CENTER_LEFT
        );

        top.setPadding(
                new Insets(22, 40, 0, 40)
        );

        ImageView logo = null;
        try {
            InputStream is = getClass().getResourceAsStream("/assets/Main Logo.jpeg");
            if (is == null) {
                is = getClass().getResourceAsStream("/Main Logo.jpeg");
            }
            if (is != null) {
                logo = new ImageView(new Image(is));
                logo.setFitHeight(42);
                logo.setPreserveRatio(true);
                logo.setSmooth(true);
            }
        } catch (Exception e) {
            logo = null;
        }

        VBox brand =
                new VBox(1);

        Label name =
                new Label("SkillVerse");

        name.setFont(
                Font.font("Arial", 22)
        );

        name.setStyle(
                "-fx-font-weight:bold;"
        );

        name.setTextFill(
                Color.web("#17213D")
        );

        Label small =
                new Label(
                        "AI-Powered Employee Growth Platform"
                );

        small.setFont(
                Font.font("Arial", 11)
        );

        small.setTextFill(
                Color.web("#69728A")
        );

        brand.getChildren().addAll(
                name,
                small
        );

        top.getChildren().addAll(
                logo,
                brand
        );

        root.setTop(top);


        VBox center =
                new VBox(12);

        center.setAlignment(
                Pos.TOP_CENTER
        );

        center.setPadding(
                new Insets(42, 35, 25, 35)
        );

        Label heading =
                new Label(
                        "Unlock Your Skills. Shape Your Future."
                );

        heading.setFont(
                Font.font("Arial", 34)
        );

        heading.setStyle(
                "-fx-font-weight:bold;"
        );

        heading.setTextFill(
                Color.web("#111936")
        );

        Label sub =
                new Label(
                        "Choose your role to enter your personalized SkillVerse workspace."
                );

        sub.setFont(
                Font.font("Arial", 16)
        );

        sub.setTextFill(
                Color.web("#515A70")
        );


        HBox cards =
                new HBox(18);

        cards.setAlignment(
                Pos.CENTER
        );

        cards.setPadding(
                new Insets(35, 0, 25, 0)
        );

        adminButton =
                createRoleButton(
                        "⚙",
                        "ADMIN",
                        "Full system control"
                );

        hrButton =
                createRoleButton(
                        "♙",
                        "HR MANAGER",
                        "People & talent management"
                );

        managerButton =
                createRoleButton(
                        "♜",
                        "MANAGER",
                        "Team performance"
                );

        employeeButton =
                createRoleButton(
                        "♢",
                        "EMPLOYEE",
                        "Skills & career growth"
                );

        trainerButton =
                createRoleButton(
                        "♧",
                        "TRAINER",
                        "Training management"
                );

        cards.getChildren().addAll(
                roleCard(
                        adminButton,
                        "ADMIN",
                        "Full system control\n& configuration"
                ),

                roleCard(
                        hrButton,
                        "HR MANAGER",
                        "People analytics &\ntalent management"
                ),

                roleCard(
                        managerButton,
                        "MANAGER",
                        "Team performance\n& progress"
                ),

                roleCard(
                        employeeButton,
                        "EMPLOYEE",
                        "Skills, learning &\ncareer growth"
                ),

                roleCard(
                        trainerButton,
                        "TRAINER",
                        "Training & learning\nmanagement"
                )
        );

        Label bottom =
                new Label(
                        "Learn • Grow • Lead with AI"
                );

        bottom.setFont(
                Font.font("Arial", 15)
        );

        bottom.setTextFill(
                Color.web("#555F78")
        );

        center.getChildren().addAll(
                heading,
                sub,
                cards,
                bottom
        );

        root.setCenter(center);


        HomePageController controller =
                new HomePageController(
                        stage,
                        this
                );

        controller.initialize();

        return new Scene(
                root,
                1280,
                720
        );
    }

    private VBox roleCard(
            Button button,
            String title,
            String description
    ) {

        VBox card =
                new VBox(12);

        card.setAlignment(
                Pos.CENTER
        );

        card.setPrefSize(
                205,
                250
        );

        card.setPadding(
                new Insets(18)
        );

        card.setStyle(
                "-fx-background-color:rgba(255,255,255,0.88);" +
                "-fx-background-radius:18;" +
                "-fx-border-color:#AEB7D9;" +
                "-fx-border-radius:18;" +
                "-fx-effect:dropshadow(" +
                "gaussian,rgba(40,55,110,.18),14,0,0,6);"
        );

        Label titleLabel =
                new Label(title);

        titleLabel.setFont(
                Font.font("Arial", 17)
        );

        titleLabel.setStyle(
                "-fx-font-weight:bold;"
        );

        titleLabel.setTextFill(
                Color.web("#171D38")
        );

        Label desc =
                new Label(description);

        desc.setAlignment(
                Pos.CENTER
        );

        desc.setTextAlignment(
                javafx.scene.text.TextAlignment.CENTER
        );

        desc.setFont(
                Font.font("Arial", 13)
        );

        desc.setTextFill(
                Color.web("#4E566B")
        );

        card.getChildren().addAll(
                button,
                titleLabel,
                desc
        );

        return card;
    }

    private Button createRoleButton(
            String icon,
            String title,
            String description
    ) {

        Button button =
                new Button(
                        icon + "   Login →"
                );

        button.setPrefSize(
                145,
                42
        );

        button.setStyle(
                "-fx-background-color:" +
                "linear-gradient(to right,#36A8FF,#7547E8);" +
                "-fx-text-fill:white;" +
                "-fx-font-size:14px;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:22;"
        );

        return button;
    }

    public Button getAdminButton() {
        return adminButton;
    }

    public Button getHrButton() {
        return hrButton;
    }

    public Button getManagerButton() {
        return managerButton;
    }

    public Button getEmployeeButton() {
        return employeeButton;
    }

    public Button getTrainerButton() {
        return trainerButton;
    }
}