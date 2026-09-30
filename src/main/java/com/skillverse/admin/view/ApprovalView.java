package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class ApprovalView {

    private final Stage stage;

    public ApprovalView() {
        this(null);
    }

    public ApprovalView(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane createScrollPane() {
        AdminApprovalsView adminApprovalsView = new AdminApprovalsView(stage);
        return adminApprovalsView.createScrollPane();
    }

    public javafx.scene.layout.VBox getMainContainer() {
        return new AdminApprovalsView(stage);
    }
}