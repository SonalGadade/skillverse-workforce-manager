package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class JobView {

    private final Stage stage;

    public JobView() {
        this(null);
    }

    public JobView(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane createScrollPane() {
        AdminJobsView adminJobsView = new AdminJobsView(stage);
        return adminJobsView.createScrollPane();
    }
}