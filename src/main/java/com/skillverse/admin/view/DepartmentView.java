package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class DepartmentView {

    private final Stage stage;

    public DepartmentView() {
        this(null);
    }

    public DepartmentView(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane createScrollPane() {
        AdminDepartmentsView adminDeptsView = new AdminDepartmentsView();
        return adminDeptsView.createScrollPane();
    }
}