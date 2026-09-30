package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class DashboardHomeView {

    private final Stage stage;

    public DashboardHomeView(Stage stage) {
        this.stage = stage;
    }

    public void setTotalEmployees(int value) {}
    public void setTotalDepartments(int value) {}
    public void setOpenJobs(int value) {}
    public void setPendingRequests(int value) {}

    public ScrollPane createScrollPane() {
        AdminDashboardView adminView = new AdminDashboardView(stage);
        ScrollPane sp = new ScrollPane(adminView);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }
}
