package com.skillverse.CommonFeatures;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.AppNotification;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class TrainerNotificationsView extends VBox {

    private final VBox listContainer;
    private final Label unreadBadge;

    public TrainerNotificationsView() {
        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox header = new HBox(14);
        header.setAlignment(Pos.CENTER_LEFT);



        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label title = new Label("Trainer Notifications 🔔");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitle = new Label("View real-time course alerts, learner requests, and broadcast messages.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(title, subtitle);

        unreadBadge = new Label("Live Alerts");
        unreadBadge.setStyle("-fx-background-color: #EEF2FF; -fx-text-fill: #4F46E5; -fx-font-weight: bold; -fx-padding: 6 14; -fx-background-radius: 20;");

        header.getChildren().addAll(titleBox, unreadBadge);

        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        listContainer = new VBox(10);
        ScrollPane scroll = new ScrollPane(listContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        card.getChildren().add(scroll);

        ScrollPane mainScroll = new ScrollPane(new VBox(20, header, card));
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScroll);

        loadNotifications();
    }

    private void loadNotifications() {
        listContainer.getChildren().clear();
        try {
            FirebaseDAO.getInstance().getAllNotifications().thenAccept(notifications -> {
                Platform.runLater(() -> {
                    if (notifications != null && !notifications.isEmpty()) {
                        for (AppNotification n : notifications) {
                            listContainer.getChildren().add(createNotifCard(n.getTitle(), n.getMessage(), n.getCategory()));
                        }
                    } else {
                        renderFallbackNotifications();
                    }
                });
            }).exceptionally(ex -> {
                Platform.runLater(this::renderFallbackNotifications);
                return null;
            });
        } catch (Exception e) {
            renderFallbackNotifications();
        }
    }

    private void renderFallbackNotifications() {
        listContainer.getChildren().clear();
        listContainer.getChildren().addAll(
                createNotifCard("New Learner Enrolled 🎓", "Ketaki Gadade enrolled in Full Stack Developer course.", "Enrollment"),
                createNotifCard("Assessment Submitted 📝", "Java Module 1 assessment submitted for grading.", "Assessment"),
                createNotifCard("System Announcement 📢", "System maintenance scheduled for upcoming weekend.", "Broadcast")
        );
    }

    private HBox createNotifCard(String title, String message, String category) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(14));
        row.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8; -fx-border-color: #E2E8F0; -fx-border-radius: 8;");

        Label iconLbl = new Label("🔔");
        iconLbl.setStyle("-fx-font-size: 16px; -fx-background-color: #EEF2FF; -fx-padding: 8 10; -fx-background-radius: 6;");

        VBox text = new VBox(3);
        HBox.setHgrow(text, Priority.ALWAYS);
        Label t = new Label(title);
        t.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label m = new Label(message);
        m.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");
        text.getChildren().addAll(t, m);

        Label catBadge = new Label(category != null ? category : "Alert");
        catBadge.setStyle("-fx-background-color: #E2E8F0; -fx-text-fill: #475569; -fx-font-size: 11px; -fx-padding: 3 8; -fx-background-radius: 4;");

        row.getChildren().addAll(iconLbl, text, catBadge);
        return row;
    }
}
