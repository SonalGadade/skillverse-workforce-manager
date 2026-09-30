package com.skillverse.employee.controller;

import com.skillverse.employee.view.MyProfileView;
import javafx.stage.Stage;

public class ProfileController {

    private Stage stage;

    public ProfileController(Stage stage) {
        this.stage = stage;
    }

    public void openProfile() {
        MyProfileView view = new MyProfileView(null);
        view.show(stage, null);
    }
}