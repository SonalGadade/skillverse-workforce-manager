package com.skillverse.employee.view;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class MySkillsView {

    private String employeeEmail = "employee@skillverse.com";

    public MySkillsView() {}

    public MySkillsView(String employeeEmail) {
        if (employeeEmail != null && !employeeEmail.trim().isEmpty()) {
            this.employeeEmail = employeeEmail;
        }
    }

    public void show(Stage stage) {
        show(stage, employeeEmail);
    }

    public void show(Stage stage, String email) {
        if (email != null && !email.trim().isEmpty()) {
            employeeEmail = email;
        }

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FAFC;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(createSkillsContent(employeeEmail, null));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1500, 900);
        stage.setTitle("SkillVerse | My Skills & Capability Gap Analysis");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public VBox createSkillsContent(String email) {
        return createSkillsContent(email, null);
    }

    public VBox createSkillsContent(String email, Consumer<String> onNavigateToLearning) {
        return new EmployeeSkillsView().createSkillsContent(email, onNavigateToLearning);
    }
}