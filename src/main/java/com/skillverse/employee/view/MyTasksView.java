package com.skillverse.employee.view;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyTasksView {

    private String userEmail = "employee@skillverse.com";

    public MyTasksView() {}

    public MyTasksView(String email) {
        if (email != null && !email.isBlank()) {
            this.userEmail = email;
        }
    }

    public void show(Stage stage, String email) {
        new EmployeeTasksView(email).show(stage, email);
    }

    public VBox createTasksContent(String email) {
        return new EmployeeTasksView(email).createTasksContent(email);
    }
}