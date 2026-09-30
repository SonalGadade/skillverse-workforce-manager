package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class NotificationView {

    private final Stage stage;

    public NotificationView() {
        this(null);
    }

    public NotificationView(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane createScrollPane() {
        AdminNotificationsView adminNotificationsView = new AdminNotificationsView(stage);
        return adminNotificationsView.createScrollPane();
    }

    public javafx.scene.layout.VBox getMainContainer() {
        return new AdminNotificationsView(stage);
    }
}