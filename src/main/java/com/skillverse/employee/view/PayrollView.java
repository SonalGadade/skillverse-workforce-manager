package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PayrollView {

    public void show(Stage stage) {

        Label title = new Label("Payroll & Salary");
        title.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label(
            "View your salary details, earnings and deductions"
        );
        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox basicSalary = createSalaryCard(
            "Basic Salary",
            "₹45,000",
            "Monthly",
            "#2563EB"
        );

        VBox allowances = createSalaryCard(
            "Allowances",
            "₹8,500",
            "Monthly",
            "#7C3AED"
        );

        VBox deductions = createSalaryCard(
            "Deductions",
            "₹4,500",
            "Monthly",
            "#EA580C"
        );

        VBox netSalary = createSalaryCard(
            "Net Salary",
            "₹49,000",
            "Monthly",
            "#16A34A"
        );

        HBox salaryCards = new HBox(20);

        salaryCards.getChildren().addAll(
            basicSalary,
            allowances,
            deductions,
            netSalary
        );

        Label breakdownTitle = new Label("Salary Breakdown");
        breakdownTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        VBox basicRow = createSalaryRow(
            "Basic Salary",
            "₹45,000"
        );

        VBox hraRow = createSalaryRow(
            "House Rent Allowance",
            "₹5,000"
        );

        VBox travelRow = createSalaryRow(
            "Travel Allowance",
            "₹2,000"
        );

        VBox performanceRow = createSalaryRow(
            "Performance Bonus",
            "₹1,500"
        );

        VBox pfRow = createSalaryRow(
            "Provident Fund",
            "- ₹3,500"
        );

        VBox taxRow = createSalaryRow(
            "Professional Tax",
            "- ₹1,000"
        );

        VBox breakdownCard = new VBox(12);

        breakdownCard.setPadding(new Insets(22));
        breakdownCard.setMaxWidth(750);

        breakdownCard.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 18, 0, 0, 5);"
        );

        breakdownCard.getChildren().addAll(
            basicRow,
            hraRow,
            travelRow,
            performanceRow,
            pfRow,
            taxRow
        );

        Label payslipTitle = new Label("Latest Payslip");
        payslipTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label payslipText = new Label(
            "July 2026 Salary Slip\n" +
            "Net Salary: ₹49,000"
        );

        payslipText.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #64748B;"
        );

        Button viewButton = new Button("View Payslip");

        viewButton.setPrefHeight(42);
        viewButton.setPrefWidth(150);

        viewButton.setStyle(
            "-fx-background-color: #EEF4FF;" +
            "-fx-text-fill: #2563EB;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        );

        VBox payslipCard = new VBox(12);

        payslipCard.setPadding(new Insets(22));
        payslipCard.setMaxWidth(750);

        payslipCard.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #DCE8FA;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 18, 0, 0, 5);"
        );

        payslipCard.getChildren().addAll(
            payslipText,
            viewButton
        );



        VBox content = new VBox(25);

        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_LEFT);

        content.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #F8FAFF, #EEF4FF);"
        );

        content.getChildren().addAll(
            title,
            subtitle,
            salaryCards,
            breakdownTitle,
            breakdownCard,
            payslipTitle,
            payslipCard
        );

        BorderPane root = new BorderPane();

        root.setCenter(content);



        Scene scene = new Scene(root, 1450, 850);

        stage.setTitle("SkillVerse - Payroll & Salary");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createSalaryCard(
        String title,
        String value,
        String description,
        String textColor
    ) {

        Label titleLabel = new Label(title);

        titleLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #64748B;"
        );

        Label valueLabel = new Label(value);

        valueLabel.setStyle(
            "-fx-font-size: 25px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        Label descriptionLabel = new Label(description);

        descriptionLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #94A3B8;"
        );

        VBox card = new VBox(9);

        card.setPadding(new Insets(22));
        card.setPrefWidth(270);
        card.setPrefHeight(145);

        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.08), 18, 0, 0, 5);"
        );

        card.getChildren().addAll(
            titleLabel,
            valueLabel,
            descriptionLabel
        );

        return card;
    }

    private VBox createSalaryRow(
        String name,
        String amount
    ) {

        Label nameLabel = new Label(name);

        nameLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #475569;"
        );

        Label amountLabel = new Label(amount);

        amountLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        HBox row = new HBox();

        row.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(
            nameLabel,
            javafx.scene.layout.Priority.ALWAYS
        );

        row.getChildren().addAll(
            nameLabel,
            amountLabel
        );

        VBox wrapper = new VBox();

        wrapper.setPadding(
            new Insets(8, 0, 8, 0)
        );

        wrapper.getChildren().add(row);

        return wrapper;
    }
}