package com.skillverse.hr.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HREmployeeManagementView extends VBox {

    public HREmployeeManagementView() {
        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(14);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label title = new Label("Employee Directory & Management 👥");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitle = new Label("View, register, and manage enterprise personnel records.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(title, subtitle);

        Button addEmpBtn = new Button("+ Add New Employee");
        addEmpBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        addEmpBtn.setOnAction(e -> {
            javafx.scene.Parent root = getScene().getRoot();
            if (root != null) {
                javafx.scene.Node contentArea = root.lookup("#contentArea");
                if (contentArea instanceof Pane) {
                    ((Pane) contentArea).getChildren().setAll(new HREmployeeFormView());
                }
            }
        });

        headerBar.getChildren().addAll(titleBox, addEmpBtn);

        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label directoryInfo = new Label("Full-page employee management view active. Click '+ Add New Employee' to register personnel.");
        directoryInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #475569;");
        card.getChildren().add(directoryInfo);

        getChildren().addAll(headerBar, card);
    }
}
