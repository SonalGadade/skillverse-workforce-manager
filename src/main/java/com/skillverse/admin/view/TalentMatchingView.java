package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class TalentMatchingView {

    private final Stage stage;

    public TalentMatchingView() {
        this(null);
    }

    public TalentMatchingView(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane createScrollPane() {
        AdminTalentMatchingView adminTalentMatchingView = new AdminTalentMatchingView(stage);
        return adminTalentMatchingView.createScrollPane();
    }

    public javafx.scene.layout.VBox getMainContainer() {
        return new AdminTalentMatchingView(stage);
    }
}