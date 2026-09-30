package com.skillverse.employee.controller;

import javafx.stage.Stage;

public class EmployeeDashboardController {

    private final Stage stage;
    private final String email;

    public EmployeeDashboardController(
            Stage stage,
            String email
    ) {
        this.stage = stage;
        this.email = email;
    }

    public void openDashboard() {

        EmployeeDashboardView dashboard =
                new EmployeeDashboardView();

        dashboard.show(stage, email);
    }
}