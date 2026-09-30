package com.skillverse.admin.controller;

import com.skillverse.admin.view.ActivityLogView;
import javafx.scene.control.ScrollPane;

public class ActivityLogController {

    private final ActivityLogView view;

    public ActivityLogController(ActivityLogView view) {
        this.view = view;
    }

    public ScrollPane initialize() {
        return view.createScrollPane();
    }
}
