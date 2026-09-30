package com.skillverse.trainer.view;

import com.skillverse.FirstScreen.SceneNavigator;
import com.skillverse.FirstScreen.UnifiedLoginView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginPage {

    public static void show(Stage stage) {
        Scene scene = UnifiedLoginView.createLoginScene(
            "Trainer",
            "Trainer Platform",
            "Design interactive courses, evaluate assessments, schedule live seminars and inspire growth.",
            "🎓",
            "#1E60FF",
            new String[]{"Course Creation", "Learner Progress", "Skill Evaluation", "Live Seminars"},
            email -> SceneNavigator.showTrainerDashboard()
        );
        stage.setScene(scene);
        stage.setTitle("SkillVerse AI - Trainer Login");
        stage.show();
    }
}