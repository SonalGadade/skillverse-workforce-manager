package com.skillverse.FirstScreen;

import java.io.InputStream;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainPortalView {

    private boolean isDarkMode = false;

    private static final String UNIFIED_BRAND_COLOR = "#1E60FF";

    // Color Tokens
    private String bgColor;
    private String cardBg;
    private String cardBorder;
    private String textWhite;
    private String textMuted;
    private String subheadColor;
    private String hoverCardBg;

    public Scene createScene() {
        applyTheme();

        VBox root = new VBox(0);
        root.setPadding(new Insets(20, 35, 18, 35));
        root.setStyle("-fx-background-color: " + bgColor + ";");
        root.setAlignment(Pos.TOP_CENTER);

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);

        HBox brandLeft = new HBox(12);
        brandLeft.setAlignment(Pos.CENTER_LEFT);

        ImageView logoImageView = null;
        try {
            InputStream is = getClass().getResourceAsStream("/assets/Main Logo.jpeg");
            if (is == null) {
                is = getClass().getResourceAsStream("/Main Logo.jpeg");
            }
            if (is != null) {
                Image img = new Image(is);
                logoImageView = new ImageView(img);
                logoImageView.setFitHeight(42);
                logoImageView.setPreserveRatio(true);
                logoImageView.setSmooth(true);
            }
        } catch (Exception ex) {
            logoImageView = null;
        }

        VBox brandText = new VBox(1);
        Label brandTitle = new Label("SkillVerse");
        brandTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        brandTitle.setTextFill(Color.web(textWhite));

        Label brandTagline = new Label("LEARN  •  GROW  •  LEAD");
        brandTagline.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        brandTagline.setTextFill(Color.web(UNIFIED_BRAND_COLOR));

        brandText.getChildren().addAll(brandTitle, brandTagline);

        if (logoImageView != null) {
            brandLeft.getChildren().addAll(logoImageView, brandText);
        } else {
            Label fallbackIcon = new Label("SV");
            fallbackIcon.setStyle(
                    "-fx-background-color: linear-gradient(to right, #1E60FF, #4F46E5);"
                    + "-fx-text-fill: white;"
                    + "-fx-font-size: 16px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-padding: 6px 10px;"
                    + "-fx-background-radius: 8px;"
            );
            brandLeft.getChildren().addAll(fallbackIcon, brandText);
        }

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        Label promptText = new Label("Select your role workspace below to continue");
        promptText.setStyle(
                "-fx-background-color: " + (isDarkMode ? "#1E293B;" : "#EFF6FF;")
                + "-fx-text-fill: " + (isDarkMode ? "#38BDF8;" : "#1E60FF;")
                + "-fx-font-size: 12px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 6px 18px;"
                + "-fx-background-radius: 20px;"
        );

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        Button themeToggleBtn = new Button(isDarkMode ? "☀ Light Mode" : "🌙 Dark Mode");
        themeToggleBtn.setStyle(
                "-fx-background-color: " + (isDarkMode ? "#1E293B;" : "#FFFFFF;")
                + "-fx-text-fill: " + (isDarkMode ? "#F8FAFC;" : "#0F172A;")
                + "-fx-font-size: 12px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 6px 14px;"
                + "-fx-background-radius: 18px;"
                + "-fx-border-color: " + (isDarkMode ? "#334155;" : "#CBD5E1;")
                + "-fx-border-radius: 18px;"
                + "-fx-cursor: hand;"
        );

        themeToggleBtn.setOnAction(e -> {
            isDarkMode = !isDarkMode;
            SceneNavigator.loadScene(createScene());
        });

        topBar.getChildren().addAll(brandLeft, spacer1, promptText, spacer2, themeToggleBtn);

        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);

        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(18);
        grid.setAlignment(Pos.CENTER);

        VBox hrCard = createRoleCard(
                "HR Workspace",
                "Manage employees, recruitment, payroll, leave management & organizational structure.",
                "👥",
                "Access HR Login",
                () -> SceneNavigator.showHRLogin()
        );

        VBox employeeCard = createRoleCard(
                "Employee Portal",
                "Access individual skills, learning tracks, daily tasks, attendance & career goals.",
                "👤",
                "Access Employee Login",
                () -> SceneNavigator.showEmployeeLogin()
        );

        VBox managerCard = createRoleCard(
                "Manager Portal",
                "Oversee team activities, project milestones, KPIs, approvals & performance reviews.",
                "📊",
                "Access Manager Login",
                () -> SceneNavigator.showManagerLogin()
        );

        VBox trainerCard = createRoleCard(
                "Trainer Hub",
                "Design courses, evaluate assessments, schedule live seminars & monitor learner stats.",
                "🎓",
                "Access Trainer Login",
                () -> SceneNavigator.showTrainerLogin()
        );

        VBox adminCard = createRoleCard(
                "Admin Panel",
                "System configuration, AI talent matching, security settings, analytics & activity logs.",
                "🛡️",
                "Access Admin Login",
                () -> SceneNavigator.showAdminLogin()
        );

        grid.add(hrCard, 0, 0);
        grid.add(employeeCard, 1, 0);

        grid.add(managerCard, 0, 1);
        grid.add(trainerCard, 1, 1);

        grid.add(adminCard, 0, 2);
        GridPane.setColumnSpan(adminCard, 2);
        GridPane.setHalignment(adminCard, HPos.CENTER);

        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);

        Label footerText = new Label("SkillVerse AI Platform v1.0 • Multi-Module Enterprise System");
        footerText.setTextFill(Color.web(textMuted));
        footerText.setFont(Font.font("Arial", 11));

        footer.getChildren().add(footerText);

        root.getChildren().addAll(topBar, topSpacer, grid, bottomSpacer, footer);

        return new Scene(root, 1360, 840);
    }

    private void applyTheme() {
        if (isDarkMode) {
            bgColor = "#0B0F19";
            cardBg = "#131B2E";
            cardBorder = "#1E293B";
            textWhite = "#F8FAFC";
            textMuted = "#64748B";
            subheadColor = "#94A3B8";
            hoverCardBg = "#1E293B";
        } else {
            bgColor = "#F8FAFC";
            cardBg = "#FFFFFF";
            cardBorder = "#E2E8F0";
            textWhite = "#0F172A";
            textMuted = "#64748B";
            subheadColor = "#475569";
            hoverCardBg = "#EEF2FF";
        }
    }

    private VBox createRoleCard(
            String title,
            String description,
            String iconSymbol,
            String buttonText,
            Runnable onLaunch
    ) {
        VBox card = new VBox(12);
        card.setPrefWidth(380);
        card.setMinWidth(360);
        card.setMaxWidth(400);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle(
                "-fx-background-color: " + cardBg + ";"
                + "-fx-border-color: " + cardBorder + ";"
                + "-fx-border-width: 1px;"
                + "-fx-border-radius: 14px;"
                + "-fx-background-radius: 14px;"
                + "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.06), 10, 0.1, 0, 3);"
        );

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label(iconSymbol);
        icon.setPrefSize(42, 42);
        icon.setAlignment(Pos.CENTER);
        icon.setStyle(
                "-fx-background-color: " + UNIFIED_BRAND_COLOR + "1E;"
                + "-fx-text-fill: " + UNIFIED_BRAND_COLOR + ";"
                + "-fx-font-size: 20px;"
                + "-fx-background-radius: 10px;"
        );

        VBox titleBox = new VBox(2);
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        titleLabel.setTextFill(Color.web(textWhite));

        Label badge = new Label("ROLE WORKSPACE");
        badge.setFont(Font.font("Arial", FontWeight.BOLD, 8));
        badge.setTextFill(Color.web(UNIFIED_BRAND_COLOR));

        titleBox.getChildren().addAll(badge, titleLabel);
        topRow.getChildren().addAll(icon, titleBox);

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setFont(Font.font("Arial", 12));
        descLabel.setTextFill(Color.web(subheadColor));

        Button actionBtn = new Button(buttonText + "  →");
        actionBtn.setMaxWidth(Double.MAX_VALUE);
        actionBtn.setPrefHeight(38);
        actionBtn.setStyle(
                "-fx-background-color: " + UNIFIED_BRAND_COLOR + ";"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 12px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 8px;"
                + "-fx-cursor: hand;"
        );

        actionBtn.setOnAction(e -> onLaunch.run());

        card.getChildren().addAll(topRow, descLabel, actionBtn);

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: " + hoverCardBg + ";"
                + "-fx-border-color: " + UNIFIED_BRAND_COLOR + ";"
                + "-fx-border-width: 1px;"
                + "-fx-border-radius: 14px;"
                + "-fx-background-radius: 14px;"
                + "-fx-effect: dropshadow(gaussian, rgba(30,96,255,0.18), 14, 0.2, 0, 5);"
        ));

        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: " + cardBg + ";"
                + "-fx-border-color: " + cardBorder + ";"
                + "-fx-border-width: 1px;"
                + "-fx-border-radius: 14px;"
                + "-fx-background-radius: 14px;"
                + "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.06), 10, 0.1, 0, 3);"
        ));

        return card;
    }
}
