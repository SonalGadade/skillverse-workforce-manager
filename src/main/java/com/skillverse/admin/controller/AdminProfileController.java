package com.skillverse.admin.controller;

import com.skillverse.admin.view.AdminProfileView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class AdminProfileController {

    private final Stage stage;
    private final AdminProfileView view;

    public AdminProfileController(Stage stage, AdminProfileView view) {
        this.stage = stage;
        this.view = view;
    }

    public ScrollPane initialize() {
        return view.createScrollPane();
    }
}
