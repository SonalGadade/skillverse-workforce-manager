package com.skillverse.admin.controller;

import com.skillverse.admin.view.TalentMatchingView;
import javafx.scene.control.ScrollPane;

public class TalentMatchingController {

    private final TalentMatchingView view;

    public TalentMatchingController(TalentMatchingView view) {
        this.view = view;
    }

    public ScrollPane initialize() {
        return view.createScrollPane();
    }
}
