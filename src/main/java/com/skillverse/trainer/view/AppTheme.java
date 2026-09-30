
package com.skillverse.trainer.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class AppTheme {

    public static final String BLUE = "#2563EB";
    public static final String DARK_BLUE = "#172554";
    public static final String BG = "#F5F7FB";
    public static final String BORDER = "#E4E7EC";
    public static final String TEXT = "#172033";
    public static final String MUTED = "#667085";

    private AppTheme() {
    }

    public static Label title(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 28));
        label.setTextFill(Color.web(TEXT));
        return label;
    }

    public static Label subtitle(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", 14));
        label.setTextFill(Color.web(MUTED));
        label.setWrapText(true);
        return label;
    }

    public static TextField field(String prompt) {
        TextField field = new TextField();

        field.setPromptText(prompt);
        field.setPrefHeight(43);

        field.setStyle(
                "-fx-background-color:white;" +
                "-fx-border-color:" + BORDER + ";" +
                "-fx-border-radius:8;" +
                "-fx-background-radius:8;" +
                "-fx-padding:0 12;"
        );

        return field;
    }

    public static PasswordField passwordField(String prompt) {
        PasswordField field = new PasswordField();

        field.setPromptText(prompt);
        field.setPrefHeight(43);

        field.setStyle(
                "-fx-background-color:white;" +
                "-fx-border-color:" + BORDER + ";" +
                "-fx-border-radius:8;" +
                "-fx-background-radius:8;" +
                "-fx-padding:0 12;"
        );

        return field;
    }

    public static Button primary(String text) {
        Button button = new Button(text);

        button.setPrefHeight(44);
        button.setMaxWidth(Double.MAX_VALUE);

        button.setStyle(
                "-fx-background-color:" + BLUE + ";" +
                "-fx-text-fill:white;" +
                "-fx-background-radius:8;" +
                "-fx-font-weight:bold;" +
                "-fx-cursor:hand;"
        );

        return button;
    }

    public static Button secondary(String text) {
        Button button = new Button(text);

        button.setPrefHeight(42);
        button.setMaxWidth(Double.MAX_VALUE);

        button.setStyle(
                "-fx-background-color:#EEF4FF;" +
                "-fx-text-fill:#2457B8;" +
                "-fx-background-radius:8;" +
                "-fx-font-weight:bold;" +
                "-fx-cursor:hand;"
        );

        return button;
    }

    public static VBox card(javafx.scene.Node... nodes) {

        VBox box = new VBox(10);

        box.getChildren().addAll(nodes);
        box.setPadding(new Insets(20));

        box.setStyle(
                "-fx-background-color:white;" +
                "-fx-background-radius:14;" +
                "-fx-border-color:" + BORDER + ";" +
                "-fx-border-radius:14;"
        );

        return box;
    }
}