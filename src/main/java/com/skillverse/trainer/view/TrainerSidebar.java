package com.skillverse.trainer.view;

import java.io.InputStream;

import com.skillverse.trainer.controller.NavigationController;
import com.skillverse.trainer.model.Trainer;
import com.skillverse.FirstScreen.SceneNavigator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TrainerSidebar {

    public static VBox create(Stage stage, Trainer trainer) {
        return create(stage, trainer, "Dashboard");
    }

    public static VBox create(Stage stage, Trainer trainer, String activePage) {
        VBox side = new VBox(10);
        side.setPadding(new Insets(18, 14, 18, 14));
        side.setPrefWidth(240);
        side.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-width: 0 1px 0 0;");

        NavigationController navigation = new NavigationController(stage, trainer);

      
        HBox brandLeft = new HBox(10);
        brandLeft.setAlignment(Pos.CENTER_LEFT);

        ImageView logoImageView = null;
        try {
            InputStream is = TrainerSidebar.class.getResourceAsStream("/assets/Main Logo.jpeg");
            if (is == null) {
                is = TrainerSidebar.class.getResourceAsStream("/Main Logo.jpeg");
            }
            if (is != null) {
                Image img = new Image(is);
                logoImageView = new ImageView(img);
                logoImageView.setFitHeight(42);
                logoImageView.setPreserveRatio(true);
                logoImageView.setSmooth(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        VBox brandText = new VBox(1);
        Label brandTitle = new Label("SkillVerse");
        brandTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        brandTitle.setTextFill(Color.web("#0F172A"));

        Label brandTagline = new Label("TRAINER PORTAL");
        brandTagline.setFont(Font.font("Arial", FontWeight.BOLD, 9));
        brandTagline.setTextFill(Color.web("#1E60FF"));

        brandText.getChildren().addAll(brandTitle, brandTagline);

        if (logoImageView != null) {
            brandLeft.getChildren().addAll(logoImageView, brandText);
        } else {
            Label fallbackIcon = new Label("SV");
            fallbackIcon.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 6px 10px; -fx-background-radius: 8px;");
            brandLeft.getChildren().addAll(fallbackIcon, brandText);
        }

        Label wsBadge = new Label("TRAINER PLATFORM");
        wsBadge.setStyle("-fx-text-fill: #1E60FF; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-color: #EFF6FF; -fx-background-radius: 12px;");

        VBox logoHeaderBox = new VBox(8, brandLeft, wsBadge, new Separator());

       
        boolean isDash = "Dashboard".equalsIgnoreCase(activePage);
        boolean isCourses = "My Courses".equalsIgnoreCase(activePage) || "Courses".equalsIgnoreCase(activePage);
        boolean isLearners = "Learners".equalsIgnoreCase(activePage);
        boolean isEvents = "Seminars & Events".equalsIgnoreCase(activePage) || "Seminars".equalsIgnoreCase(activePage) || "Events".equalsIgnoreCase(activePage);
        boolean isAssessments = "Assessments".equalsIgnoreCase(activePage);
        boolean isPerformance = "Performance".equalsIgnoreCase(activePage);
        boolean isComm = "Communication".equalsIgnoreCase(activePage);

        Button dashboard = nav("🏠  Dashboard", isDash);
        Button courses = nav("📚  My Courses", isCourses);
        Button learners = nav("👥  Learners", isLearners);
        Button events = nav("📅  Seminars & Events", isEvents);
        Button assessments = nav("📝  Assessments", isAssessments);
        Button performance = nav("📈  Performance", isPerformance);
        Button communication = nav("💬  Communication", isComm);

        dashboard.setOnAction(e -> navigation.goToDashboard());
        courses.setOnAction(e -> navigation.goToCourses());
        learners.setOnAction(e -> navigation.goToLearners());
        events.setOnAction(e -> navigation.goToSeminars());
        assessments.setOnAction(e -> navigation.goToAssessments());
        performance.setOnAction(e -> navigation.goToPerformance());
        communication.setOnAction(e -> navigation.goToCommunication());

        VBox menu = new VBox(
            4,
            dashboard,
            courses,
            learners,
            events,
            assessments,
            performance,
            communication
        );

        VBox.setVgrow(menu, Priority.ALWAYS);

       
        Button logout = new Button("→  Logout");
        logout.setMaxWidth(Double.MAX_VALUE);
        logout.setPrefHeight(38);
        logout.setAlignment(Pos.CENTER_LEFT);
        logout.setStyle("-fx-background-color: transparent; -fx-text-fill: #EF4444; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 0 12px; -fx-background-radius: 8px; -fx-cursor: hand;");
        logout.setOnMouseEntered(e -> logout.setStyle("-fx-background-color: #FEF2F2; -fx-text-fill: #DC2626; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 0 12px; -fx-background-radius: 8px; -fx-cursor: hand;"));
        logout.setOnMouseExited(e -> logout.setStyle("-fx-background-color: transparent; -fx-text-fill: #EF4444; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 0 12px; -fx-background-radius: 8px; -fx-cursor: hand;"));
        logout.setOnAction(e -> {
            Stage currentStage = (Stage) logout.getScene().getWindow();
            com.skillverse.CommonFeatures.ModernLogoutDialog.show(currentStage != null ? currentStage : stage, "Trainer");
        });

        side.getChildren().addAll(
            logoHeaderBox,
            menu,
            logout
        );

        return side;
    }

    private static Button nav(String text, boolean isActive) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(40);
        button.setAlignment(Pos.CENTER_LEFT);

        String activeStyle =
            "-fx-background-color: #EFF6FF;" +
            "-fx-text-fill: #1E60FF;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 0 12px;" +
            "-fx-background-radius: 8px;" +
            "-fx-border-color: #1E60FF;" +
            "-fx-border-radius: 8px;" +
            "-fx-border-width: 1.5px;" +
            "-fx-cursor: hand;";

        String defaultStyle =
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #475569;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 0 12px;" +
            "-fx-background-radius: 8px;" +
            "-fx-border-color: transparent;" +
            "-fx-border-radius: 8px;" +
            "-fx-border-width: 1px;" +
            "-fx-cursor: hand;";

        String hoverStyle =
            "-fx-background-color: #F8FAFC;" +
            "-fx-text-fill: #1E60FF;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 0 12px;" +
            "-fx-background-radius: 8px;" +
            "-fx-border-color: #E2E8F0;" +
            "-fx-border-radius: 8px;" +
            "-fx-border-width: 1px;" +
            "-fx-cursor: hand;";

        button.setStyle(isActive ? activeStyle : defaultStyle);

        button.setOnMouseEntered(e -> {
            if (!isActive) button.setStyle(hoverStyle);
        });

        button.setOnMouseExited(e -> {
            button.setStyle(isActive ? activeStyle : defaultStyle);
        });

        return button;
    }
}
