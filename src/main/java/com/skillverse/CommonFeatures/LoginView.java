package com.skillverse.CommonFeatures;

import com.skillverse.FirstScreen.SceneNavigator;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginView extends StackPane {
    public LoginView(Stage stage) {
        Platform.runLater(() -> {
            SceneNavigator.init(stage);
            SceneNavigator.showMainPortal();
        });
    }
}
