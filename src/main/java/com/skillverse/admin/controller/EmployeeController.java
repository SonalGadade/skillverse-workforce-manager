package com.skillverse.admin.controller;

import com.skillverse.admin.view.EmployeeView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class EmployeeController {

    private final Stage stage;
    private final EmployeeView view;

    public EmployeeController(Stage stage, EmployeeView view) {
        this.stage = stage;
        this.view = view;
    }

    public ScrollPane initialize() {
        return view.createScrollPane();
    }
}
