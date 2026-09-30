package com.skillverse.admin.controller;

import com.skillverse.admin.view.AnalyticsView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class AnalyticsController {

    private final Stage stage;
    private final AnalyticsView view;

    public AnalyticsController(Stage stage, AnalyticsView view) {
        this.stage = stage;
        this.view = view;
    }

    public ScrollPane initialize() {
        return view.createScrollPane();
    }
}
