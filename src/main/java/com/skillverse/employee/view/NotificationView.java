package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NotificationView {

    public void show(Stage stage) {

        Label title = new Label("Notifications");
        title.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #111827;"
        );

        Label subtitle = new Label(
            "Stay updated with your latest activities and announcements"
        );
        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #64748B;"
        );

        VBox notification1 = createNotification(
            "Training Reminder",
            "Your Advanced Java Programming training is waiting for you.",
            "Today",
            "#2563EB"
        );

        VBox notification2 = createNotification(
            "Performance Update",
            "Your latest performance score has been updated to 92%.",
            "Yesterday",
            "#7C3AED"
        );

        VBox notification3 = createNotification(
            "New Learning Course",
            "A new JavaFX Application Development course is available.",
            "2 days ago",
            "#16A34A"
        );

        VBox notification4 = createNotification(
            "Achievement Unlocked",
            "Congratulations! You completed your latest training milestone.",
            "5 days ago",
            "#EA580C"
        );

        VBox notificationsBox = new VBox(15);

        notificationsBox.getChildren().addAll(
            notification1,
            notification2,
            notification3,
            notification4
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
            notificationsBox
        );

        BorderPane root = new BorderPane();

        root.setCenter(content);



        Scene scene = new Scene(
            root,
            1450,
            850
        );

        stage.setTitle(
            "SkillVerse - Notifications"
        );

        stage.setScene(scene);
        stage.show();
    }

    private VBox createNotification(
        String notificationTitle,
        String message,
        String time,
        String textColor
    ) {

        Label title = new Label(notificationTitle);

        title.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + textColor + ";"
        );

        Label messageLabel = new Label(message);

        messageLabel.setWrapText(true);

        messageLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #64748B;"
        );

        Label timeLabel = new Label(time);

        timeLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #94A3B8;"
        );

        VBox notification = new VBox(8);

        notification.setPadding(new Insets(20));

        notification.setStyle(
            "-fx-background-color: rgba(255,255,255,0.92);" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: #E1E8F5;" +
            "-fx-border-radius: 16;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.07), 18, 0, 0, 5);"
        );

        notification.getChildren().addAll(
            title,
            messageLabel,
            timeLabel
        );

        return notification;
    }
}