package com.skillverse.admin.controller;

import com.skillverse.admin.view.NotificationView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class NotificationController {

    private final Stage stage;
    private final NotificationView view;

    public NotificationController(Stage stage, NotificationView view) {
        this.stage = stage;
        this.view = view;
    }

    public ScrollPane initialize() {
        return view.createScrollPane();
    }
}
