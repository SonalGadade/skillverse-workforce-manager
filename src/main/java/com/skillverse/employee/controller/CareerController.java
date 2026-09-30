package com.skillverse.employee.controller;

import com.skillverse.employee.view.CareerView;
import javafx.stage.Stage;

public class CareerController {

    private Stage stage;

    public CareerController(Stage stage) {
        this.stage = stage;
    }

    public void openCareer() {
        CareerView careerView = new CareerView();
        careerView.show(stage);
    }
}