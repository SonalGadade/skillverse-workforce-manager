package com.skillverse.employee.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.TrainerAnnouncement;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class AnnouncementsView {

    private static final String BLUE = "#1648C8";
    private static final String TEXT = "#111827";
    private static final String MUTED = "#6B7280";
    private static final String BG = "#F8F8FD";
    private static final String BORDER = "#E7E8F0";

    public VBox createAnnouncementsContent(String email) {
        VBox page = new VBox(22);
        page.setPadding(new Insets(38, 42, 45, 42));
        page.setStyle("-fx-background-color:" + BG + ";");

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("ANNOUNCEMENTS & NOTICE BOARD");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        title.setTextFill(Color.web(TEXT));

        Region headerSpace = new Region();
        HBox.setHgrow(headerSpace, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Sync Notices");
        refreshBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 16px;");

        header.getChildren().addAll(title, headerSpace, refreshBtn);
        page.getChildren().addAll(header, new Separator());

        // Feed Container
        VBox feedContainer = new VBox(16);
        ScrollPane scrollPane = new ScrollPane(feedContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        page.getChildren().add(scrollPane);

        Runnable loadAnnouncements = () -> {
            feedContainer.getChildren().clear();
            ProgressIndicator spinner = new ProgressIndicator();
            feedContainer.getChildren().add(new HBox(10, new Label("Loading broadcasts from Firestore..."), spinner));

            FirebaseDAO.getInstance().getRecentAnnouncements().thenAccept(announcements -> {
                Platform.runLater(() -> {
                    feedContainer.getChildren().clear();
                    if (announcements == null || announcements.isEmpty()) {
                        VBox emptyBox = new VBox(12);
                        emptyBox.setPadding(new Insets(30));
                        emptyBox.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");
                        Label noAnn = new Label("📢 No Active Announcements Broadcasted");
                        noAnn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                        noAnn.setTextFill(Color.web("#64748B"));
                        emptyBox.getChildren().add(noAnn);
                        feedContainer.getChildren().add(emptyBox);
                    } else {
                        for (TrainerAnnouncement ann : announcements) {
                            feedContainer.getChildren().add(buildAnnouncementCard(ann));
                        }
                    }
                });
            });
        };

        refreshBtn.setOnAction(e -> loadAnnouncements.run());
        loadAnnouncements.run();

        return page;
    }

    private static VBox buildAnnouncementCard(TrainerAnnouncement ann) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));

        boolean isHigh = "HIGH".equalsIgnoreCase(ann.getPriority());
        if (isHigh) {
            card.setStyle("-fx-background-color: #FEF2F2; -fx-background-radius: 14px; -fx-border-color: #EF4444; -fx-border-width: 2px; -fx-border-radius: 14px;");
        } else {
            card.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");
        }

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        String trainerName = ann.getTrainerName() != null ? ann.getTrainerName() : "Technical Trainer";
        Label prioBadge = new Label(isHigh ? "📢 Trainer Announcement from " + trainerName : "📌 Broadcast from " + trainerName);
        prioBadge.setStyle(isHigh
                ? "-fx-background-color: #DC2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;"
                : "-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Label audienceBadge = new Label("🎯 Dept: " + (ann.getTargetAudience() != null ? ann.getTargetAudience() : "ALL"));
        audienceBadge.setStyle("-fx-background-color: #F3E8FF; -fx-text-fill: #7C3AED; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label dateLabel = new Label(ann.getCreatedAt() != null ? ann.getCreatedAt().toString() : "Recent");
        dateLabel.setFont(Font.font("Arial", 11));
        dateLabel.setTextFill(Color.web(MUTED));

        topRow.getChildren().addAll(prioBadge, audienceBadge, sp, dateLabel);

        Text titleText = new Text(ann.getTitle() != null ? ann.getTitle() : "Notice");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleText.setFill(Color.web("#0F172A"));

        Text msgText = new Text(ann.getMessage() != null ? ann.getMessage() : "");
        msgText.setFont(Font.font("Arial", 14));
        msgText.setFill(Color.web("#334155"));

        card.getChildren().addAll(topRow, titleText, msgText);
        return card;
    }
}