package com.skillverse.admin.view;

import com.skillverse.CommonFeatures.ProfileView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminProfileView extends VBox {

    public AdminProfileView() {
        getChildren().add(new ProfileView());
    }

    public AdminProfileView(Stage stage) {
        this();
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }
}
