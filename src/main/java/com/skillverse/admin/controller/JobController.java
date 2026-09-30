package com.skillverse.admin.controller;

import com.skillverse.admin.view.JobView;
import javafx.scene.control.ScrollPane;

public class JobController {

    private final JobView view;

    public JobController(JobView view) {
        this.view = view;
    }

    public ScrollPane initialize() {
        return view.createScrollPane();
    }
}