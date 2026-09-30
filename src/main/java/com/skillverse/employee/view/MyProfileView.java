package com.skillverse.employee.view;

import com.skillverse.FirstScreen.UnifiedProfileView;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyProfileView {

    private String userEmail;

    public MyProfileView(String email) {
        this.userEmail = email;
    }

    public VBox createProfileContent(String email) {
        if (email != null && !email.isBlank()) {
            this.userEmail = email;
        }
        UnifiedProfileView upv = new UnifiedProfileView("Employee", null);
        return upv.getViewContainer();
    }

    public void show(Stage stage, String email) {
        if (stage != null) {
            Scene scene = new Scene(createProfileContent(email), 1400, 800);
            stage.setScene(scene);
            stage.show();
        }
    }
}