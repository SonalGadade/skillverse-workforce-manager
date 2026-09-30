package com.skillverse.hr.view;

import com.skillverse.FirstScreen.SceneNavigator;
import com.skillverse.FirstScreen.UnifiedLoginView;
import javafx.scene.Scene;

public class HRLogin {

    private Scene loginScene;

    public HRLogin() {
        loginScene = UnifiedLoginView.createLoginScene(
            "HR",
            "HR Platform",
            "Manage employees, recruitment, payroll, leave management, and organizational growth.",
            "👥",
            "#1E60FF",
            new String[]{"Recruitment", "Payroll & PF", "Leave Tracking", "Performance"},
            email -> SceneNavigator.showHRDashboard()
        );
    }

    public Scene getScene() {
        return loginScene;
    }
}