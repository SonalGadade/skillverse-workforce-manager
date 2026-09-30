package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class AnalyticsView {

    private final Stage stage;

    public AnalyticsView() {
        this(null);
    }

    public AnalyticsView(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane createScrollPane() {
        AdminAnalyticsView adminAnalyticsView = new AdminAnalyticsView(stage);
        return adminAnalyticsView.createScrollPane();
    }

    public javafx.scene.layout.VBox getMainContainer() {
        return new AdminAnalyticsView(stage);
    }
}
