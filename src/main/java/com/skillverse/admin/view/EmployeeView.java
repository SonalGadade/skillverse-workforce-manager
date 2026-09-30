package com.skillverse.admin.view;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class EmployeeView {

    private final Stage stage;

    public EmployeeView(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane createScrollPane() {
        AdminEmployeesView adminEmployeesView = new AdminEmployeesView(stage);
        return adminEmployeesView.createScrollPane();
    }

    public static class EmployeeData {
        private String id;
        private String name;
        private String email;
        private String role;
        private String department;
        private String status;

        public EmployeeData(String id, String name, String email, String role, String department, String status) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
            this.department = department;
            this.status = status;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
        public String getDepartment() { return department; }
        public String getStatus() { return status; }
    }
}
