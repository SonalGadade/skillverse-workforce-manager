package com.skillverse.admin.controller;

import com.skillverse.admin.view.ApprovalView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class ApprovalController {

    private final Stage stage;
    private final ApprovalView view;

    public ApprovalController(Stage stage, ApprovalView view) {
        this.stage = stage;
        this.view = view;
    }

    public ScrollPane initialize() {
        return view.createScrollPane();
    }
}