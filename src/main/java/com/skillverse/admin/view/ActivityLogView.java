package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class ActivityLogView {

    private final Stage stage;

    public ActivityLogView() {
        this(null);
    }

    public ActivityLogView(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane createScrollPane() {
        AdminActivityLogView adminActivityLogView = new AdminActivityLogView(stage);
        return adminActivityLogView.createScrollPane();
    }

    public javafx.scene.layout.VBox getMainContainer() {
        return new AdminActivityLogView(stage);
    }
}
