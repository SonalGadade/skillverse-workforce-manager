package com.skillverse.employee.view;

import javafx.scene.layout.VBox;

public class MyPerformanceView {

    public VBox createPerformanceContent(String email) {
        return new EmployeePerformanceView().createPerformanceContent(email);
    }
}