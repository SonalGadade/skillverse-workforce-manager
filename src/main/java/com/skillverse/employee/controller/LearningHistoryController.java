package com.skillverse.employee.controller;

import com.skillverse.employee.view.LearningHistoryView;
import javafx.stage.Stage;

public class LearningHistoryController {

    private Stage stage;

    public LearningHistoryController(Stage stage) {
        this.stage = stage;
    }

    public void openLearningHistory() {
        LearningHistoryView view =
            new LearningHistoryView();

        view.show(stage);
    }
}