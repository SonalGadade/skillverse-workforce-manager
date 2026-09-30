package com.skillverse.hr.view;

import com.skillverse.hr.controller.HRController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class HR extends Application {

    public static Stage HRstage;
    private Scene HRscene;

    private final String primaryBlue = "#2563EB";
    private final String lightBlue = "#EEF4FF";
    private final String background = "#F5F7FC";
    private final String white = "#FFFFFF";
    private final String heading = "#111827";
    private final String navy = "#111C3D";
    private final String secondary = "#64748B";
    private final String muted = "#94A3B8";
    private final String border = "#E5EAF3";

    @Override
    public void start(Stage myStage) throws Exception {

        HRstage = myStage;

        HRController controller = new HRController();

        HBox root = new HBox();

        root.setStyle(
                "-fx-background-color: " + background + ";"
        );

        VBox leftPanel = new VBox(30);

        leftPanel.setPrefWidth(560);

        leftPanel.setPadding(
                new Insets(45, 55, 45, 55)
        );

        leftPanel.setStyle(
                "-fx-background-color: " + white + ";" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-width: 0 1px 0 0;"
        );

        Label logoIcon = new Label("⚡");

        logoIcon.setStyle(
                "-fx-background-color: " + primaryBlue + ";" +
                "-fx-background-radius: 12px;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 20px;" +
                "-fx-padding: 9px 11px;"
        );

        Label logoText = new Label("SkillVerse");

        logoText.setStyle(
                "-fx-text-fill: " + navy + ";" +
                "-fx-font-size: 23px;" +
                "-fx-font-weight: bold;"
        );

        HBox logo = new HBox(
                10,
                logoIcon,
                logoText
        );

        logo.setAlignment(
                Pos.CENTER_LEFT
        );

        VBox brandContent = new VBox(18);

        VBox.setVgrow(
                brandContent,
                Priority.ALWAYS
        );

        Label workspace = new Label(
                "HR WORKSPACE"
        );

        workspace.setStyle(
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;"
        );

        Label title = new Label(
                "Manage people.\nBuild better teams."
        );

        title.setStyle(
                "-fx-text-fill: " + heading + ";" +
                "-fx-font-size: 38px;" +
                "-fx-font-weight: bold;"
        );

        title.setLineSpacing(3);

        Label description = new Label(
                "Empower your workforce, streamline recruitment " +
                "and manage your organization from one intelligent workspace."
        );

        description.setWrapText(true);

        description.setStyle(
                "-fx-text-fill: " + secondary + ";" +
                "-fx-font-size: 15px;" +
                "-fx-line-spacing: 5px;"
        );

        VBox feature1 = createFeature(
                "✓",
                "Employee Management",
                "Manage employee information from one place."
        );

        VBox feature2 = createFeature(
                "✓",
                "Smart Recruitment",
                "Track hiring activities and open positions."
        );

        VBox feature3 = createFeature(
                "✓",
                "Workforce Insights",
                "Monitor performance and organizational growth."
        );

        brandContent.getChildren().addAll(
                workspace,
                title,
                description,
                feature1,
                feature2,
                feature3
        );

        Label footer = new Label(
                "Secure • Reliable • Powered by SkillVerse"
        );

        footer.setStyle(
                "-fx-text-fill: " + muted + ";" +
                "-fx-font-size: 12px;"
        );

        leftPanel.getChildren().addAll(
                logo,
                brandContent,
                footer
        );

        VBox rightPanel = new VBox();

        rightPanel.setAlignment(
                Pos.CENTER
        );

        rightPanel.setPadding(
                new Insets(45, 80, 45, 80)
        );

        rightPanel.setStyle(
                "-fx-background-color: " + background + ";"
        );

        HBox.setHgrow(
                rightPanel,
                Priority.ALWAYS
        );

        VBox welcomeCard = new VBox(22);

        welcomeCard.setAlignment(
                Pos.CENTER
        );

        welcomeCard.setMaxWidth(470);

        welcomeCard.setPadding(
                new Insets(45)
        );

        welcomeCard.setStyle(
                "-fx-background-color: " + white + ";" +
                "-fx-background-radius: 22px;" +
                "-fx-border-color: " + border + ";" +
                "-fx-border-radius: 22px;" +
                "-fx-effect: dropshadow(gaussian, rgba(17,24,39,0.08), 20, 0.15, 0, 6);"
        );

        ImageView imageView;

        try {

            Image image = new Image(
                    getClass()
                            .getResource("/assets/profile.png")
                            .toExternalForm()
            );

            imageView = new ImageView(image);

            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);

            Circle clip = new Circle(
                    50,
                    50,
                    50
            );

            imageView.setClip(clip);

        } catch (Exception ex) {

            imageView = new ImageView();
        }

        VBox imageBox = new VBox(
                imageView
        );

        imageBox.setAlignment(
                Pos.CENTER
        );

        imageBox.setPrefSize(
                118,
                118
        );

        imageBox.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-background-radius: 60px;"
        );

        Label workspaceLabel = new Label(
                "HR PLATFORM"
        );

        workspaceLabel.setStyle(
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;"
        );

        Label welcome = new Label(
                "Welcome, HR Manager"
        );

        welcome.setStyle(
                "-fx-text-fill: " + heading + ";" +
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Access your SkillVerse HR workspace"
        );

        subtitle.setStyle(
                "-fx-text-fill: " + secondary + ";" +
                "-fx-font-size: 14px;"
        );

        Button loginbtn = new Button(
                "Login into your account"
        );

        loginbtn.setMaxWidth(
                Double.MAX_VALUE
        );

        loginbtn.setPrefHeight(48);

        loginbtn.setStyle(
                "-fx-background-color: " + primaryBlue + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 12px;" +
                "-fx-cursor: hand;"
        );

        loginbtn.setOnMouseEntered(e ->
                loginbtn.setStyle(
                        "-fx-background-color: #1D4ED8;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-cursor: hand;"
                )
        );

        loginbtn.setOnMouseExited(e ->
                loginbtn.setStyle(
                        "-fx-background-color: " + primaryBlue + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-cursor: hand;"
                )
        );

        loginbtn.setOnAction(e ->
                controller.openLogin()
        );

        Button signInBtn = new Button(
                "Create an HR account"
        );

        signInBtn.setMaxWidth(
                Double.MAX_VALUE
        );

        signInBtn.setPrefHeight(48);

        signInBtn.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: #D8E5FF;" +
                "-fx-border-radius: 12px;" +
                "-fx-cursor: hand;"
        );

        signInBtn.setOnAction(e ->
                controller.openSignin()
        );

        VBox buttons = new VBox(
                12,
                loginbtn,
                signInBtn
        );

        buttons.setMaxWidth(350);

        Label security = new Label(
                "🔒  Your data is secure and protected"
        );

        security.setStyle(
                "-fx-text-fill: " + muted + ";" +
                "-fx-font-size: 11px;"
        );

        welcomeCard.getChildren().addAll(
                imageBox,
                workspaceLabel,
                welcome,
                subtitle,
                buttons,
                security
        );

        rightPanel.getChildren().add(
                welcomeCard
        );

        root.getChildren().addAll(
                leftPanel,
                rightPanel
        );

        HRscene = new Scene(
                root,
                1600,
                800
        );

        HRstage.setTitle(
                "SkillVerse - HR Platform"
        );

        HRstage.setScene(
                HRscene
        );

        HRstage.setMaximized(true);

        HRstage.show();
    }

    private VBox createFeature(
            String icon,
            String title,
            String description
    ) {

        Label iconLabel = new Label(
                icon
        );

        iconLabel.setStyle(
                "-fx-background-color: " + lightBlue + ";" +
                "-fx-background-radius: 9px;" +
                "-fx-text-fill: " + primaryBlue + ";" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 7px 9px;"
        );

        Label titleLabel = new Label(
                title
        );

        titleLabel.setStyle(
                "-fx-text-fill: " + heading + ";" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;"
        );

        Label descriptionLabel = new Label(
                description
        );

        descriptionLabel.setWrapText(true);

        descriptionLabel.setStyle(
                "-fx-text-fill: " + secondary + ";" +
                "-fx-font-size: 11px;"
        );

        VBox text = new VBox(
                3,
                titleLabel,
                descriptionLabel
        );

        HBox row = new HBox(
                12,
                iconLabel,
                text
        );

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        VBox container = new VBox(
                row
        );

        container.setPadding(
                new Insets(12)
        );

        container.setStyle(
                "-fx-background-color: " + background + ";" +
                "-fx-background-radius: 12px;"
        );

        return container;
    }

    public Scene getScene() {

        return HRscene;
    }
}