package com.skillverse.employee.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.SeminarEvent;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
import javafx.stage.Stage;

import java.util.List;

public class MyEventsView {

    private static final String BLUE = "#1648C8";
    private static final String PURPLE = "#7C3AED";
    private static final String TEXT = "#111827";
    private static final String BG = "#F8F8FD";
    private static final String BORDER = "#E7E8F0";

    public VBox createEventsContent(String email) {
        VBox content = new VBox(25);
        content.setPadding(new Insets(35, 45, 40, 45));
        content.setStyle("-fx-background-color:" + BG + ";");

        // Header
        Label title = new Label("LIVE SEMINARS & EVENTS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(Color.web(TEXT));

        Label subtitle = new Label("Explore upcoming technical workshops, live trainer seminars, and interactive webinars.");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setTextFill(Color.web("#374151"));

        content.getChildren().addAll(title, subtitle);

        // Filter / Status Bar
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Label statusLbl = new Label("🔥 Active Live Sessions & Workshops");
        statusLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        statusLbl.setTextFill(Color.web(BLUE));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Sync Live Events");
        refreshBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 16px;");

        topBar.getChildren().addAll(statusLbl, sp, refreshBtn);
        content.getChildren().addAll(topBar, new Separator());

        // Events Cards Flow Container
        HBox eventsContainer = new HBox(20);
        ScrollPane scrollPane = new ScrollPane(eventsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        content.getChildren().add(scrollPane);

        // Async Loader
        Runnable loadEvents = () -> {
            eventsContainer.getChildren().clear();
            ProgressIndicator spinner = new ProgressIndicator();
            eventsContainer.getChildren().add(new HBox(10, new Label("Fetching upcoming seminars from Firestore..."), spinner));

            FirebaseDAO.getInstance().getAllSeminarEvents().thenAccept(events -> {
                Platform.runLater(() -> {
                    eventsContainer.getChildren().clear();
                    if (events == null || events.isEmpty()) {
                        VBox emptyCard = new VBox(12);
                        emptyCard.setPadding(new Insets(30));
                        emptyCard.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");
                        Label noEvt = new Label("📅 No Live Seminars Scheduled Currently");
                        noEvt.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                        noEvt.setTextFill(Color.web("#64748B"));
                        emptyCard.getChildren().add(noEvt);
                        eventsContainer.getChildren().add(emptyCard);
                    } else {
                        for (SeminarEvent event : events) {
                            eventsContainer.getChildren().add(buildSeminarEventCard(event));
                        }
                    }
                });
            });
        };

        refreshBtn.setOnAction(e -> loadEvents.run());
        loadEvents.run();

        return content;
    }

    public void show(Stage stage, String email) {
        EmployeeDashboardView dashboard = new EmployeeDashboardView();
        dashboard.show(stage, email);
    }

    private static VBox buildSeminarEventCard(SeminarEvent event) {
        VBox card = new VBox(14);
        card.setPrefWidth(340);
        card.setPrefHeight(380);
        card.setPadding(new Insets(22));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: " + BORDER + "; -fx-border-radius: 16px;");

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label iconLbl = new Label("📅");
        iconLbl.setPrefSize(42, 42);
        iconLbl.setAlignment(Pos.CENTER);
        iconLbl.setStyle("-fx-background-color: #EFF6FF; -fx-background-radius: 50%; -fx-text-fill: #1E60FF; -fx-font-size: 18px;");

        Region sp1 = new Region();
        HBox.setHgrow(sp1, Priority.ALWAYS);

        String deptStr = event.getTargetDepartment() != null ? event.getTargetDepartment() : "ALL";
        Label deptBadge = new Label(deptStr);
        deptBadge.setPadding(new Insets(5, 12, 5, 12));
        deptBadge.setStyle("-fx-background-color: #F3E8FF; -fx-background-radius: 16px; -fx-text-fill: #7C3AED; -fx-font-weight: bold; -fx-font-size: 11px;");

        topRow.getChildren().addAll(iconLbl, sp1, deptBadge);

        Label titleLbl = new Label(event.getTitle() != null ? event.getTitle() : "Live Seminar");
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleLbl.setTextFill(Color.web("#0F172A"));

        Label timeLbl = new Label("⏰ " + (event.getEventDate() != null ? event.getEventDate() : "Upcoming") + " • " + (event.getEventTime() != null ? event.getEventTime() : "TBD"));
        timeLbl.setFont(Font.font(13));
        timeLbl.setTextFill(Color.web("#475569"));

        Label trainerLbl = new Label("👤 Host Trainer: " + (event.getTrainerName() != null ? event.getTrainerName() : "Technical Trainer"));
        trainerLbl.setFont(Font.font(13));
        trainerLbl.setTextFill(Color.web("#64748B"));

        Label venueLbl = new Label("📍 Venue / Link: " + (event.getMeetingLinkOrVenue() != null && !event.getMeetingLinkOrVenue().isEmpty() ? event.getMeetingLinkOrVenue() : "Live Virtual Meeting Room"));
        venueLbl.setWrapText(true);
        venueLbl.setFont(Font.font(12));
        venueLbl.setTextFill(Color.web("#0284C7"));

        Region cardSpacer = new Region();
        VBox.setVgrow(cardSpacer, Priority.ALWAYS);

        Button joinBtn = new Button("🚀 Join Live Session / Link");
        joinBtn.setMaxWidth(Double.MAX_VALUE);
        joinBtn.setPrefHeight(44);
        joinBtn.setStyle("-fx-background-color: linear-gradient(to right, #1E60FF, #7C3AED); -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");

        joinBtn.setOnAction(e -> {
            String link = event.getMeetingLinkOrVenue() != null ? event.getMeetingLinkOrVenue() : "Virtual Room";
            new Alert(Alert.AlertType.INFORMATION, "Joining Session: " + event.getTitle() + "\nLink: " + link).showAndWait();
        });

        card.getChildren().addAll(topRow, titleLbl, timeLbl, trainerLbl, venueLbl, cardSpacer, joinBtn);
        return card;
    }
}
