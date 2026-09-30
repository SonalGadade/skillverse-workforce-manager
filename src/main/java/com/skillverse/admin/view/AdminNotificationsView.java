package com.skillverse.admin.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.AppNotification;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

public class AdminNotificationsView extends VBox {

    private final Stage stage;
    private final Label unreadBadge;
    private final HBox filterPillsBox;
    private final VBox notificationListContainer;
    private String activeCategoryFilter = "All";
    private List<AppNotification> masterList = new ArrayList<>();

    public AdminNotificationsView(Stage stage) {
        this();
    }

    public AdminNotificationsView() {
        this.stage = null;
        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("System Notifications 🔔");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Stay updated with real-time enterprise alerts, audits, and broadcast announcements.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        unreadBadge = new Label("0 Unread");
        unreadBadge.setStyle("-fx-background-color: #4F46E5; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 6 14; -fx-background-radius: 20;");

        Button sendBroadcastBtn = new Button("📢 Send Broadcast");
        sendBroadcastBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;");
        sendBroadcastBtn.setOnAction(e -> handleSendBroadcastDialog());

        headerBar.getChildren().addAll(titleBox, unreadBadge, sendBroadcastBtn);

        filterPillsBox = new HBox(10);
        filterPillsBox.setAlignment(Pos.CENTER_LEFT);
        renderFilterPills();

        VBox cardContainer = new VBox(14);
        cardContainer.setPadding(new Insets(20));
        cardContainer.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        HBox listHeader = new HBox(12);
        listHeader.setAlignment(Pos.CENTER_LEFT);
        Label listTitle = new Label("Recent Notifications");
        listTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        HBox.setHgrow(listTitle, Priority.ALWAYS);

        Button markAllReadBtn = new Button("Mark all as read");
        markAllReadBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563EB; -fx-font-size: 12px; -fx-cursor: hand; -fx-font-weight: bold;");
        markAllReadBtn.setOnAction(e -> handleMarkAllAsRead());

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-size: 12px; -fx-cursor: hand; -fx-font-weight: bold;");
        refreshBtn.setOnAction(e -> loadNotificationsFromFirestore());

        listHeader.getChildren().addAll(listTitle, markAllReadBtn, refreshBtn);

        notificationListContainer = new VBox(12);
        ScrollPane scrollPane = new ScrollPane(notificationListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        cardContainer.getChildren().addAll(listHeader, scrollPane);

        ScrollPane mainScroll = new ScrollPane(new VBox(20, headerBar, filterPillsBox, cardContainer));
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScroll);

        loadNotificationsFromFirestore();
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private void renderFilterPills() {
        filterPillsBox.getChildren().clear();
        String[] filters = {"All", "Unread", "System", "Employee", "Training", "Alert"};

        for (String filter : filters) {
            Button pill = new Button(filter);
            boolean isSelected = filter.equalsIgnoreCase(activeCategoryFilter);

            if (isSelected) {
                pill.setStyle("-fx-background-color: #4F46E5; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 6 16; -fx-background-radius: 20; -fx-cursor: hand;");
            } else {
                pill.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: 500; -fx-padding: 6 16; -fx-background-radius: 20; -fx-cursor: hand;");
            }

            pill.setOnAction(e -> {
                activeCategoryFilter = filter;
                renderFilterPills();
                filterAndRender();
            });
            filterPillsBox.getChildren().add(pill);
        }
    }

    public void loadNotificationsFromFirestore() {
        FirebaseDAO.getInstance().getAllNotifications().thenAccept(list -> {
            Platform.runLater(() -> {
                this.masterList = list != null ? list : new ArrayList<>();

                long unreadCount = masterList.stream().filter(n -> !n.isRead()).count();
                unreadBadge.setText(unreadCount + " Unread");

                filterAndRender();
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private void filterAndRender() {
        notificationListContainer.getChildren().clear();

        List<AppNotification> filtered = masterList.stream().filter(n -> {
            if ("All".equalsIgnoreCase(activeCategoryFilter)) return true;
            if ("Unread".equalsIgnoreCase(activeCategoryFilter)) return !n.isRead();
            return activeCategoryFilter.equalsIgnoreCase(n.getCategory());
        }).collect(Collectors.toList());

        if (filtered.isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(50));
            Label emptyLbl = new Label("No notifications found under category: " + activeCategoryFilter);
            emptyLbl.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 13px;");
            emptyBox.getChildren().add(emptyLbl);
            notificationListContainer.getChildren().add(emptyBox);
            return;
        }

        for (AppNotification notif : filtered) {
            notificationListContainer.getChildren().add(createNotificationRow(notif));
        }
    }

    private HBox createNotificationRow(AppNotification notif) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(14, 18, 14, 18));
        row.setStyle("-fx-background-color: " + (notif.isRead() ? "#F8FAFC;" : "#FFFFFF;") +
                "-fx-background-radius: 10; -fx-border-color: " + (notif.isRead() ? "#E2E8F0;" : "#C7D2FE;") +
                "; -fx-border-radius: 10; -fx-cursor: hand;");

        Label iconLbl = new Label(getIconForCategory(notif.getCategory()));
        iconLbl.setStyle("-fx-font-size: 16px; -fx-background-color: " + getBgForCategory(notif.getCategory()) +
                "; -fx-padding: 8 10; -fx-background-radius: 8;");

        VBox textContainer = new VBox(4);
        HBox.setHgrow(textContainer, Priority.ALWAYS);

        HBox titleRow = new HBox(8);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label titleLbl = new Label(notif.getTitle());
        titleLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        if (!notif.isRead()) {
            Label unreadDot = new Label("●");
            unreadDot.setStyle("-fx-text-fill: #4F46E5; -fx-font-size: 10px;");
            titleRow.getChildren().addAll(titleLbl, unreadDot);
        } else {
            titleRow.getChildren().add(titleLbl);
        }

        Label msgLbl = new Label(notif.getMessage());
        msgLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");
        msgLbl.setWrapText(true);

        textContainer.getChildren().addAll(titleRow, msgLbl);

        Label catBadge = new Label(notif.getCategory() != null ? notif.getCategory() : "System");
        catBadge.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3 8; -fx-background-radius: 4;");

        row.getChildren().addAll(iconLbl, textContainer, catBadge);

        row.setOnMouseClicked(e -> {
            if (!notif.isRead()) {
                FirebaseDAO.getInstance().markNotificationAsRead(notif.getId()).thenRun(() -> {
                    Platform.runLater(this::loadNotificationsFromFirestore);
                });
            }
        });

        return row;
    }

    private String getIconForCategory(String cat) {
        if (cat == null) return "ℹ️";
        switch (cat.toLowerCase()) {
            case "employee": return "👤";
            case "training": return "📚";
            case "alert": return "⚠️";
            case "system": return "⚙️";
            default: return "📢";
        }
    }

    private String getBgForCategory(String cat) {
        if (cat == null) return "#F1F5F9";
        switch (cat.toLowerCase()) {
            case "employee": return "#EFF6FF";
            case "training": return "#ECFDF5";
            case "alert": return "#FEF3C7";
            case "system": return "#F1F5F9";
            default: return "#EEF2FF";
        }
    }

    private void handleMarkAllAsRead() {
        FirebaseDAO.getInstance().markAllNotificationsAsRead().thenRun(() -> {
            Platform.runLater(this::loadNotificationsFromFirestore);
        });
    }

    private void handleSendBroadcastDialog() {
        if (getScene() != null && getScene().getRoot() != null) {
            javafx.scene.Node contentArea = getScene().getRoot().lookup("#contentArea");
            if (contentArea instanceof Pane) {
                ((Pane) contentArea).getChildren().setAll(new AdminSendBroadcastView());
                return;
            }
        }
        javafx.scene.Node current = this;
        while (current != null && !(current instanceof StackPane || current instanceof VBox || current instanceof BorderPane)) {
            current = current.getParent();
        }
        if (current instanceof Pane) {
            ((Pane) current).getChildren().setAll(new AdminSendBroadcastView());
        }
    }

    private void showModernToast(String title, String message, boolean isSuccess) {
        Platform.runLater(() -> {
            HBox toast = new HBox(12);
            toast.setAlignment(Pos.CENTER_LEFT);
            toast.setPadding(new Insets(14, 20, 14, 20));
            toast.setMaxWidth(480);

            Label iconLbl = new Label(isSuccess ? "✓" : "⚠️");
            iconLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + (isSuccess ? "#059669;" : "#D97706;"));

            VBox textContainer = new VBox(2);
            Label titleLbl = new Label(title);
            titleLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

            Label descLbl = new Label(message);
            descLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #475569;");
            descLbl.setWrapText(true);

            textContainer.getChildren().addAll(titleLbl, descLbl);
            HBox.setHgrow(textContainer, Priority.ALWAYS);

            toast.getChildren().addAll(iconLbl, textContainer);

            toast.setStyle(
                    "-fx-background-color: " + (isSuccess ? "#ECFDF5;" : "#FFFBEB;") +
                    "-fx-border-color: " + (isSuccess ? "#A7F3D0;" : "#FDE68A;") +
                    "-fx-border-width: 1.5; -fx-background-radius: 12; -fx-border-radius: 12;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 15, 0, 0, 6);"
            );

            toast.setOpacity(0);
            getChildren().add(0, toast);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toast);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), toast);
            slideIn.setFromY(-20);
            slideIn.setToY(0);

            ParallelTransition showAnim = new ParallelTransition(fadeIn, slideIn);
            PauseTransition delay = new PauseTransition(Duration.seconds(3.5));
            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), toast);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> getChildren().remove(toast));

            new SequentialTransition(showAnim, delay, fadeOut).play();
        });
    }
}
