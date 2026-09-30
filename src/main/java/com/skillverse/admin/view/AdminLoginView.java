package com.skillverse.admin.view;

import com.skillverse.FirstScreen.SceneNavigator;
import com.skillverse.FirstScreen.UnifiedLoginView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminLoginView {

    private final Stage stage;

    public AdminLoginView(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {
        return UnifiedLoginView.createLoginScene(
            "Admin",
            "Admin Platform",
            "Manage users, departments, system configurations and platform security settings.",
            "🛡️",
            "#6366F1",
            new String[]{"User Management", "System Settings", "Data Analytics", "Platform Security"},
            email -> SceneNavigator.showAdminDashboard()
        );
    }
}