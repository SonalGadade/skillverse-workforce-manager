package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class SkillView {

    private final Stage stage;

    public SkillView() {
        this(null);
    }

    public SkillView(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane createScrollPane() {
        AdminSkillsView adminSkillsView = new AdminSkillsView(stage);
        return adminSkillsView.createScrollPane();
    }
}
