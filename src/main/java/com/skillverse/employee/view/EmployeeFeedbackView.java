package com.skillverse.employee.view;

import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeFeedback;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class EmployeeFeedbackView {

    private final String PRIMARY_BLUE = "#1648C8";
    private final String ROYAL_BLUE = "#2563EB";
    private final String LIGHT_BLUE = "#b0cbfd";
    private final String DARK = "#111827";
    private final String MUTED = "#6B7280";
    private final String BORDER = "#E5E7EB";
    private final String SUCCESS = "#059669";

    private String userEmail;
    private VBox cardsContainer;
    private Label avgRatingLabel;
    private Label totalReviewsLabel;
    private Label pendingAckLabel;

    public EmployeeFeedbackView(String userEmail) {
        this.userEmail = userEmail;
    }

    public EmployeeFeedbackView() {
        this.userEmail = "";
    }

    public VBox createFeedbackContent(String email) {
        if (email != null && !email.isEmpty()) {
            this.userEmail = email;
        }

        VBox main = new VBox(22);
        main.setPadding(new Insets(28, 35, 40, 35));
        main.setOpacity(1.0);
        main.setStyle("-fx-background-color: #F8FAFC;");

        // TOP HEADER
        HBox headerBox = new HBox(16);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        Label title = new Label("Feedback Inbox & Reviews");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title.setTextFill(Color.web(DARK));

        Label subtitle = new Label("Performance reviews, sprint feedback, and coaching notes received from Manager, Trainer, and HR.");
        subtitle.setFont(Font.font("Arial", 13));
        subtitle.setTextFill(Color.web(MUTED));

        titleBox.getChildren().addAll(title, subtitle);

        Region headerSp = new Region();
        HBox.setHgrow(headerSp, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Sync Feedback");
        refreshBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 16px;");

        headerBox.getChildren().addAll(titleBox, headerSp, refreshBtn);

        // SUMMARY METRICS BAR
        HBox metricsBar = new HBox(16);
        avgRatingLabel = new Label("N/A");
        VBox metric1 = createMetricCard("⭐ Avg Rating", avgRatingLabel, "Based on performance reviews", "#f0d66e", "#D97706");

        totalReviewsLabel = new Label("0 Reviews");
        VBox metric2 = createMetricCard("💬 Total Reviews", totalReviewsLabel, "All time performance feedback", LIGHT_BLUE, PRIMARY_BLUE);

        pendingAckLabel = new Label("0 Pending");
        VBox metric3 = createMetricCard("⏳ Pending Acknowledgments", pendingAckLabel, "Requires review acknowledgment", "#f78a8a", "#DC2626");

        HBox.setHgrow(metric1, Priority.ALWAYS);
        HBox.setHgrow(metric2, Priority.ALWAYS);
        HBox.setHgrow(metric3, Priority.ALWAYS);

        metricsBar.getChildren().addAll(metric1, metric2, metric3);

        // FEEDBACK CARDS CONTAINER
        Label sectionTitle = new Label("Received Manager, Trainer & HR Reviews");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        sectionTitle.setTextFill(Color.web(DARK));

        cardsContainer = new VBox(16);

        VBox scrollContent = new VBox(20, headerBox, metricsBar, sectionTitle, cardsContainer);
        scrollContent.setOpacity(1.0);
        scrollContent.setStyle("-fx-background-color: #F8FAFC;");

        ScrollPane scrollPane = new ScrollPane(scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setOpacity(1.0);
        scrollPane.setStyle("-fx-background-color: #F8FAFC; -fx-background: #F8FAFC; -fx-control-inner-background: #F8FAFC;");

        main.getChildren().add(scrollPane);

        Runnable loadFeedback = () -> refreshFeedbackCardsFromFirestore();
        refreshBtn.setOnAction(e -> loadFeedback.run());
        loadFeedback.run();

        return main;
    }

    private VBox createMetricCard(String title, Label valueLabel, String subtext, String bgColor, String accentColor) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(18, 20, 18, 20));
        card.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        titleLabel.setTextFill(Color.web(accentColor));

        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        valueLabel.setTextFill(Color.web(DARK));

        Label sub = new Label(subtext);
        sub.setFont(Font.font("Arial", 11));
        sub.setTextFill(Color.web(MUTED));

        card.getChildren().addAll(titleLabel, valueLabel, sub);
        return card;
    }

    private void refreshFeedbackCardsFromFirestore() {
        if (cardsContainer == null) {
            return;
        }

        String currentEmail = (UserSession.getCurrentUser() != null && UserSession.getCurrentUser().getEmail() != null && !UserSession.getCurrentUser().getEmail().isBlank())
                ? UserSession.getCurrentUser().getEmail().toLowerCase().trim()
                : (userEmail != null ? userEmail.toLowerCase().trim() : "");

        cardsContainer.getChildren().setAll(new Label("🔄 Fetching feedback reviews from Firestore..."));

        FirebaseDAO.getInstance().getFeedbackForEmployee(currentEmail).thenAccept(feedbackList -> {
            Platform.runLater(() -> {
                cardsContainer.getChildren().clear();
                if (feedbackList == null || feedbackList.isEmpty()) {
                    avgRatingLabel.setText("N/A");
                    totalReviewsLabel.setText("0 Reviews");
                    pendingAckLabel.setText("0 Pending");

                    VBox emptyBox = new VBox(10);
                    emptyBox.setAlignment(Pos.CENTER);
                    emptyBox.setPadding(new Insets(40));
                    Label noDataLbl = new Label("No feedback or reviews received yet.");
                    noDataLbl.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 14px; -fx-font-style: italic;");
                    emptyBox.getChildren().add(noDataLbl);
                    cardsContainer.getChildren().add(emptyBox);
                } else {
                    double totalScore = 0;
                    int pendingCount = 0;

                    for (EmployeeFeedback item : feedbackList) {
                        totalScore += item.getRating();
                        cardsContainer.getChildren().add(createDynamicFeedbackCard(item));
                    }

                    double avg = totalScore / feedbackList.size();
                    avgRatingLabel.setText(String.format("%.1f / 5.0", avg));
                    totalReviewsLabel.setText(feedbackList.size() + " Reviews");
                    pendingAckLabel.setText(pendingCount + " Pending");
                }
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            Platform.runLater(() -> {
                cardsContainer.getChildren().setAll(new Label("⚠️ Failed to load feedback reviews."));
            });
            return null;
        });
    }

    private VBox createDynamicFeedbackCard(EmployeeFeedback item) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(22));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px; -fx-background-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 10, 0.08, 0, 3);");

        HBox headerRow = new HBox(12);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Label avatar = new Label(getInitials(item.getReviewerName()));
        avatar.setPrefSize(42, 42);
        avatar.setAlignment(Pos.CENTER);
        avatar.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        avatar.setStyle("-fx-background-color: " + LIGHT_BLUE + "; -fx-text-fill: " + PRIMARY_BLUE + "; -fx-background-radius: 21px; -fx-border-color: " + ROYAL_BLUE + "; -fx-border-radius: 21px;");

        VBox authorBox = new VBox(2);
        Label authorName = new Label(item.getReviewerName() != null && !item.getReviewerName().isBlank() ? item.getReviewerName() : "Reviewer");
        authorName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        authorName.setTextFill(Color.web(DARK));

        Label roleBadge = new Label("[" + (item.getReviewerRole() != null && !item.getReviewerRole().isBlank() ? item.getReviewerRole() : "Manager") + "]");
        roleBadge.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        roleBadge.setStyle("-fx-background-color: #F3E8FF; -fx-text-fill: #7C3AED; -fx-padding: 2px 8px; -fx-background-radius: 4px;");

        HBox nameRow = new HBox(8, authorName, roleBadge);
        nameRow.setAlignment(Pos.CENTER_LEFT);

        Label periodLabel = new Label("Review Period: " + (item.getQuarterOrDate() != null && !item.getQuarterOrDate().isBlank() ? item.getQuarterOrDate() : "Appraisal Review"));
        periodLabel.setFont(Font.font("Arial", 11));
        periodLabel.setTextFill(Color.web(MUTED));

        authorBox.getChildren().addAll(nameRow, periodLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        StringBuilder stars = new StringBuilder();
        int fullStars = Math.min(5, Math.max(1, (int) item.getRating()));
        for (int i = 0; i < 5; i++) {
            stars.append(i < fullStars ? "⭐" : "☆");
        }

        Label starText = new Label(stars.toString() + "  " + item.getRating() + " / 5.0");
        starText.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        starText.setTextFill(Color.web("#D97706"));

        headerRow.getChildren().addAll(avatar, authorBox, spacer, starText);

        Text contentText = new Text(item.getFeedbackText() != null ? item.getFeedbackText() : "");
        contentText.setFont(Font.font("Arial", 13.5));
        contentText.setFill(Color.web("#374151"));
        contentText.setWrappingWidth(900);

        card.getChildren().addAll(headerRow, contentText);
        return card;
    }

    private String getInitials(String name) {
        if (name == null || name.isEmpty()) {
            return "M";
        }
        String clean = name.replace("Manager • ", "").replace("Manager", "").trim();
        String[] parts = clean.split("\\s+");
        if (parts.length >= 2) {
            return (parts[0].substring(0, 1) + parts[1].substring(0, 1)).toUpperCase();
        }
        return clean.substring(0, Math.min(2, clean.length())).toUpperCase();
    }
}
