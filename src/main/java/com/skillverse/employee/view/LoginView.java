package com.skillverse.employee.view;

import com.skillverse.FirstScreen.SceneNavigator;
import com.skillverse.FirstScreen.UnifiedLoginView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginView {

    public void show(Stage stage) {
        Scene scene = UnifiedLoginView.createLoginScene(
            "Employee",
            "Employee Platform",
            "Access individual skills, track daily tasks, learning paths and career goals.",
            "👤",
            "#8B5CF6",
            new String[]{"Skill Development", "Task Tracking", "Learning Paths", "Goal Alignment"},
            email -> SceneNavigator.showEmployeeDashboard()
        );
        stage.setScene(scene);
        stage.setTitle("SkillVerse AI - Employee Login");
        stage.show();
    }
}