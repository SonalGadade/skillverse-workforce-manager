package com.skillverse.hr.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.Feedback;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.reflect.Method;

public class HRFeedbackView extends VBox {

    private final Label reviewCountLbl;
    private final Label avgRatingLbl;
    private final Label monitoredCountLbl;
    private final VBox feedbackListContainer;
    private final Button managerToHrTabBtn;
    private final Button orgWideTabBtn;

    private List<Feedback> allFeedbacks = new ArrayList<>();
    private boolean isShowingManagerToHR = true;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

    public HRFeedbackView() {
        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        // 1. Header
        VBox titleBox = new VBox(4);
        Label titleLabel = new Label("HR Feedback & Performance Monitoring");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Review feedback sent by Managers to HR operations and monitor organization-wide reviews.");
        subtitleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        // 2. Metrics Stat Cards Row
        HBox statsRow = new HBox(16);
        statsRow.setAlignment(Pos.CENTER_LEFT);

        reviewCountLbl = new Label("0 HR Reviews");
        reviewCountLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        VBox card1 = createStatCard("✉ Manager Feedback to HR", reviewCountLbl, "Direct manager-to-HR feedback", "#EFF6FF", "#2563EB");

        avgRatingLbl = new Label("5.0 / 5.0 ★");
        avgRatingLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        VBox card2 = createStatCard("★ HR Satisfaction Rating", avgRatingLbl, "Avg rating given by managers", "#FEF9C3", "#CA8A04");

        monitoredCountLbl = new Label("0 Monitored");
        monitoredCountLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        VBox card3 = createStatCard("📊 Org-Wide Reviews Monitored", monitoredCountLbl, "Total manager-employee feedback exchanges", "#F3E8FF", "#7E22CE");

        statsRow.getChildren().addAll(card1, card2, card3);

        // 3. Tab Navigation Buttons
        HBox tabsRow = new HBox(10);
        tabsRow.setAlignment(Pos.CENTER_LEFT);

        managerToHrTabBtn = new Button("Manager Feedback to HR");
        orgWideTabBtn = new Button("Organization-Wide Performance Overview");

        updateTabStyles();

        managerToHrTabBtn.setOnAction(e -> {
            isShowingManagerToHR = true;
            updateTabStyles();
            renderFilteredFeedbacks();
        });

        orgWideTabBtn.setOnAction(e -> {
            isShowingManagerToHR = false;
            updateTabStyles();
            renderFilteredFeedbacks();
        });

        tabsRow.getChildren().addAll(managerToHrTabBtn, orgWideTabBtn);

        // 4. Feedback List Container
        feedbackListContainer = new VBox(14);

        ScrollPane listScroll = new ScrollPane(feedbackListContainer);
        listScroll.setFitToWidth(true);
        listScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        ScrollPane mainScroll = new ScrollPane(new VBox(20, titleBox, statsRow, tabsRow, listScroll));
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScroll);

        loadLiveFeedbackData();
    }

    private void updateTabStyles() {
        if (isShowingManagerToHR) {
            managerToHrTabBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 8 18; -fx-background-radius: 20; -fx-cursor: hand;");
            orgWideTabBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: 500; -fx-padding: 8 18; -fx-background-radius: 20; -fx-cursor: hand;");
        } else {
            managerToHrTabBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: 500; -fx-padding: 8 18; -fx-background-radius: 20; -fx-cursor: hand;");
            orgWideTabBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 8 18; -fx-background-radius: 20; -fx-cursor: hand;");
        }
    }

    private void loadLiveFeedbackData() {
        FirebaseDAO.getInstance().getAllFeedbacks().thenAccept(list -> {
            Platform.runLater(() -> {
                this.allFeedbacks = (list != null) ? list : new ArrayList<>();
                recalculateMetrics();
                renderFilteredFeedbacks();
            });
        }).exceptionally(ex -> {
            Platform.runLater(() -> {
                this.allFeedbacks = new ArrayList<>();
                recalculateMetrics();
                renderFilteredFeedbacks();
            });
            return null;
        });
    }

    private void recalculateMetrics() {
        List<Feedback> hrFeedbacks = allFeedbacks.stream()
                .filter(f -> "HR".equalsIgnoreCase(stringValue(f, "getTargetType", "targetType")) || (stringValue(f, "getRecipient", "recipient") != null && stringValue(f, "getRecipient", "recipient").toUpperCase().contains("HR")))
                .collect(Collectors.toList());

        reviewCountLbl.setText(hrFeedbacks.size() + " HR Reviews");

        if (!hrFeedbacks.isEmpty()) {
            double avg = hrFeedbacks.stream().mapToDouble(HRFeedbackView::feedbackRating).average().orElse(5.0);
            avgRatingLbl.setText(String.format("%.1f / 5.0 ★", avg));
        } else {
            avgRatingLbl.setText("5.0 / 5.0 ★");
        }

        monitoredCountLbl.setText(allFeedbacks.size() + " Monitored");
    }

    private void renderFilteredFeedbacks() {
        feedbackListContainer.getChildren().clear();

        List<Feedback> displayList = allFeedbacks.stream().filter(f -> {
            boolean isHR = "HR".equalsIgnoreCase(stringValue(f, "getTargetType", "targetType")) || (stringValue(f, "getRecipient", "recipient") != null && stringValue(f, "getRecipient", "recipient").toUpperCase().contains("HR"));
            return isShowingManagerToHR ? isHR : !isHR;
        }).collect(Collectors.toList());

        if (displayList.isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(40));
            emptyBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");
            Label emptyLbl = new Label(isShowingManagerToHR ? "No Manager-to-HR feedback submitted yet." : "No organization-wide reviews recorded yet.");
            emptyLbl.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 14px;");
            emptyBox.getChildren().add(emptyLbl);
            feedbackListContainer.getChildren().add(emptyBox);
            return;
        }

        for (Feedback f : displayList) {
            feedbackListContainer.getChildren().add(createFeedbackCard(f));
        }
    }

    private HBox createFeedbackCard(Feedback f) {
        HBox card = new HBox(16);
        card.setPadding(new Insets(18, 20, 18, 20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        // Avatar Initials
        String sender = (stringValue(f, "getSenderName", "senderName") != null && !stringValue(f, "getSenderName", "senderName").trim().isEmpty()) ? stringValue(f, "getSenderName", "senderName") : "Manager";
        String initials = sender.length() >= 2 ? sender.substring(0, 2).toUpperCase() : "MG";
        Label avatarLbl = new Label(initials);
        avatarLbl.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 14 16; -fx-background-radius: 50;");

        VBox contentBox = new VBox(6);
        HBox.setHgrow(contentBox, Priority.ALWAYS);

        // Header Line
        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label nameRoleLbl = new Label((stringValue(f, "getSenderRole", "senderRole") != null ? stringValue(f, "getSenderRole", "senderRole") : "Manager") + " • " + sender);
        nameRoleLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        String timeStr = dateValue(f, "getCreatedAt", "createdAt") != null ? dateFormat.format(dateValue(f, "getCreatedAt", "createdAt")) : "Recently";
        Label metaLbl = new Label("Recipient: " + (stringValue(f, "getRecipient", "recipient") != null ? stringValue(f, "getRecipient", "recipient") : "HR Team") + " • " + timeStr);
        metaLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");

        HBox.setHgrow(metaLbl, Priority.ALWAYS);

        Label catBadge = new Label(stringValue(f, "getCategory", "category") != null ? stringValue(f, "getCategory", "category").toUpperCase() : "GENERAL FEEDBACK");
        catBadge.setStyle("-fx-background-color: #F3E8FF; -fx-text-fill: #7E22CE; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 6;");

        topRow.getChildren().addAll(nameRoleLbl, metaLbl, catBadge);

        // Rating Row
        HBox ratingRow = new HBox(6);
        ratingRow.setAlignment(Pos.CENTER_LEFT);
        Label starsLbl = new Label("★".repeat(Math.max(1, (int) Math.round(doubleValue(f, "getRating", "rating")))) + "☆".repeat(Math.max(0, 5 - (int) Math.round(doubleValue(f, "getRating", "rating")))));
        starsLbl.setStyle("-fx-text-fill: #F59E0B; -fx-font-size: 13px;");

        Label scoreLbl = new Label(String.format("%.1f / 5.0", doubleValue(f, "getRating", "rating")));
        scoreLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #1E293B;");

        ratingRow.getChildren().addAll(starsLbl, scoreLbl);

        // Comments
        Label commentsLbl = new Label(stringValue(f, "getComments", "comments") != null ? stringValue(f, "getComments", "comments") : "No written comments provided.");
        commentsLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");
        commentsLbl.setWrapText(true);

        contentBox.getChildren().addAll(topRow, ratingRow, commentsLbl);
        card.getChildren().addAll(avatarLbl, contentBox);

        return card;
    }

    private static Object propertyValue(Object object, String getterName, String fieldName) {
        if (object == null) {
            return null;
        }

        try {
            Method method = object.getClass().getMethod(getterName);
            return method.invoke(object);
        } catch (Exception ignored) {
        }

        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ignored) {
        }

        return null;
    }

    private static String stringValue(Object object, String getterName, String fieldName) {
        Object value = propertyValue(object, getterName, fieldName);
        return value == null ? null : String.valueOf(value);
    }

    private static java.util.Date dateValue(Object object, String getterName, String fieldName) {
        Object value = propertyValue(object, getterName, fieldName);
        if (value instanceof java.util.Date) {
            return (java.util.Date) value;
        }
        if (value instanceof com.google.cloud.Timestamp) {
            return ((com.google.cloud.Timestamp) value).toDate();
        }
        if (value instanceof java.sql.Timestamp) {
            return new java.util.Date(((java.sql.Timestamp) value).getTime());
        }
        return null;
    }

    private static double doubleValue(Object object, String getterName, String fieldName) {
        Object value = propertyValue(object, getterName, fieldName);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value != null) {
            try {
                return Double.parseDouble(String.valueOf(value));
            } catch (NumberFormatException ignored) {
            }
        }
        return 0.0;
    }

    private static double feedbackRating(Feedback feedback) {
        return doubleValue(feedback, "getRating", "rating");
    }

    private VBox createStatCard(String title, Label valueLbl, String subtitle, String bgColor, String accentColor) {
        VBox card = new VBox(6);
        card.setPrefWidth(280);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label t = new Label(title);
        t.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + accentColor + ";");

        Label sub = new Label(subtitle);
        sub.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748B;");

        card.getChildren().addAll(t, valueLbl, sub);
        return card;
    }
}
