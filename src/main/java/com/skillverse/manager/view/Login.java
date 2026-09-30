package com.skillverse.manager.view;

import com.skillverse.FirstScreen.SceneNavigator;
import com.skillverse.FirstScreen.UnifiedLoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

    public static Stage loginStage;

    @Override
    public void start(Stage stage) {
        loginStage = stage;
        Scene scene = getLoginScene(() -> SceneNavigator.showMainPortal());
        stage.setScene(scene);
        stage.setTitle("SkillVerse AI - Manager Login");
        stage.show();
    }

    public Scene getLoginScene() {
        return getLoginScene(() -> SceneNavigator.showMainPortal());
    }

    public Scene getLoginScene(Runnable callBackActionLogin) {
        return UnifiedLoginView.createLoginScene(
            "Manager",
            "Manager Platform",
            "Oversee team activities, project milestones, performance reviews, approvals and analytics.",
            "📊",
            "#2563EB",
            new String[]{"Team Management", "Project Tracking", "Performance Reviews", "Smart Analytics"},
            email -> SceneNavigator.showManagerDashboard()
        );
    }
}