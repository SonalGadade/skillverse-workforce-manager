package com.skillverse.employee.controller;

import com.skillverse.employee.view.MyTasksView;
import javafx.stage.Stage;

public class MyTasksController {

    private Stage stage;

    public MyTasksController(Stage stage) {
        this.stage = stage;
    }

    public void openTasks() {
        MyTasksView tasksView =
            new MyTasksView();

        tasksView.show(stage, null);
    }
}