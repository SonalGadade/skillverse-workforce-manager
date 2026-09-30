package com.skillverse.admin.controller;

import com.skillverse.admin.view.AboutUsView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class AboutUsController {

    private final Stage stage;
    private final AboutUsView view;

    public AboutUsController(Stage stage, AboutUsView view) {
        this.stage = stage;
        this.view = view;
    }

    public ScrollPane initialize() {
        return view.createScrollPane();
    }
}
