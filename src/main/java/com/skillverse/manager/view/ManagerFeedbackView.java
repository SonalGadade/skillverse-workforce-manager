package com.skillverse.manager.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeFeedback;
import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Date;
import java.util.List;

public class ManagerFeedbackView extends VBox {

    private final ComboBox<String> recipientTypeCombo;
    private final ComboBox<UserItem> personCombo;
    private final ComboBox<String> categoryCombo;
    private final HBox starsBox;
    private double currentRating = 5.0;
    private final Label ratingScoreLbl;
    private final TextArea feedbackText;
    private final VBox historyContainer;

    public static class UserItem {
        private final String email;
        private final String name;
        private final String role;

        public UserItem(String email, String name, String role) {
            this.email = email;
            this.name = name;
            this.role = role;
        }

        public String getEmail() { return email; }
        public String getName() { return name; }
        public String getRole() { return role; }

        @Override
        public String toString() {
            return name + " (" + email + ") - " + role;
        }
    }

    public VBox createContent(Runnable onBack) {
        return this;
    }

    public ManagerFeedbackView() {
        setSpacing(24);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

       
        HBox headerBar = new HBox(12);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("← Dashboard");
        backBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            VBox parent = (VBox) getParent();
            if (parent != null) {
                parent.getChildren().clear();
                parent.getChildren().setAll(new ManagerDashboardView());
            }
        });

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLbl = new Label("Give Performance Feedback 💬");
        titleLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLbl = new Label("Provide synchronized constructive feedback to Employees and HR team members.");
        subtitleLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLbl, subtitleLbl);

        headerBar.getChildren().addAll(titleBox, backBtn);

     
        HBox columnsBox = new HBox(24);
        columnsBox.setAlignment(Pos.TOP_LEFT);

      
        VBox formCard = new VBox(16);
        formCard.setPrefWidth(520);
        formCard.setPadding(new Insets(24));
        formCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label formHeader = new Label("✍  New Feedback Entry");
        formHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

       
        VBox recTypeBox = new VBox(6);
        Label recTypeLbl = new Label("RECIPIENT TYPE");
        recTypeLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");
        recipientTypeCombo = new ComboBox<>();
        recipientTypeCombo.getItems().addAll("Employee", "HR Team");
        recipientTypeCombo.setValue("Employee");
        recipientTypeCombo.setMaxWidth(Double.MAX_VALUE);
        recipientTypeCombo.setOnAction(e -> loadRecipients());
        recTypeBox.getChildren().addAll(recTypeLbl, recipientTypeCombo);

      
        VBox personBox = new VBox(6);
        Label personLbl = new Label("SELECT PERSON / TEAM");
        personLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");
        personCombo = new ComboBox<>();
        personCombo.setPromptText("Select recipient");
        personCombo.setMaxWidth(Double.MAX_VALUE);
        personBox.getChildren().addAll(personLbl, personCombo);

    
        VBox catBox = new VBox(6);
        Label catLbl = new Label("FEEDBACK CATEGORY");
        catLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");
        categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll(
                "Sprint Performance",
                "Leadership & Collaboration",
                "Project Delivery & Quality",
                "Skill Growth & Initiative",
                "General Coaching & Mentorship"
        );
        categoryCombo.setValue("Sprint Performance");
        categoryCombo.setMaxWidth(Double.MAX_VALUE);
        catBox.getChildren().addAll(catLbl, categoryCombo);

 
        VBox ratingBox = new VBox(6);
        Label ratingLbl = new Label("RATING");
        ratingLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");

        starsBox = new HBox(8);
        starsBox.setAlignment(Pos.CENTER_LEFT);
        ratingScoreLbl = new Label("5.0 / 5.0 ★");
        ratingScoreLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #D97706;");

        renderStarButtons();
        HBox ratingRow = new HBox(12, starsBox, ratingScoreLbl);
        ratingRow.setAlignment(Pos.CENTER_LEFT);
        ratingBox.getChildren().addAll(ratingLbl, ratingRow);

   
        VBox commentBox = new VBox(6);
        Label commentLbl = new Label("FEEDBACK COMMENTS");
        commentLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");
        feedbackText = new TextArea();
        feedbackText.setPromptText("Write constructive feedback, specific observations, and actionable praise...");
        feedbackText.setPrefRowCount(4);
        feedbackText.setWrapText(true);
        commentBox.getChildren().addAll(commentLbl, feedbackText);

        Button submitBtn = new Button("Send Feedback →");
        submitBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 12 24; -fx-background-radius: 8; -fx-cursor: hand;");
        submitBtn.setMaxWidth(Double.MAX_VALUE);
        submitBtn.setOnAction(e -> handleSendFeedback());

        formCard.getChildren().addAll(formHeader, recTypeBox, personBox, catBox, ratingBox, commentBox, submitBtn);

      
        VBox historyCard = new VBox(16);
        HBox.setHgrow(historyCard, Priority.ALWAYS);
        historyCard.setPadding(new Insets(24));
        historyCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        HBox historyHeader = new HBox(12);
        historyHeader.setAlignment(Pos.CENTER_LEFT);
        Label historyTitle = new Label("📋 Recent Feedback History");
        historyTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        HBox.setHgrow(historyTitle, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadFeedbackHistory());

        historyHeader.getChildren().addAll(historyTitle, refreshBtn);

        historyContainer = new VBox(12);
        ScrollPane historyScroll = new ScrollPane(historyContainer);
        historyScroll.setFitToWidth(true);
        historyScroll.setPrefHeight(480);
        historyScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        historyCard.getChildren().addAll(historyHeader, historyScroll);
        columnsBox.getChildren().addAll(formCard, historyCard);

        ScrollPane mainScroll = new ScrollPane(new VBox(20, headerBar, columnsBox));
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScroll);

       
        loadRecipients();
        loadFeedbackHistory();
    }

    private void showModernToast(String title, String message, boolean isSuccess) {
        Platform.runLater(() -> {
            HBox toast = new HBox(12);
            toast.setAlignment(Pos.CENTER_LEFT);
            toast.setPadding(new Insets(14, 20, 14, 20));
            toast.setMaxWidth(460);

          
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

            SequentialTransition fullSequence = new SequentialTransition(showAnim, delay, fadeOut);
            fullSequence.play();
        });
    }

    private void renderStarButtons() {
        starsBox.getChildren().clear();
        for (int i = 1; i <= 5; i++) {
            final int starVal = i;
            Button starBtn = new Button(starVal <= (int) currentRating ? "★" : "☆");
            starBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #D97706; -fx-font-size: 18px; -fx-cursor: hand; -fx-padding: 0;");
            starBtn.setOnAction(e -> {
                currentRating = starVal;
                ratingScoreLbl.setText(String.format("%.1f / 5.0 ★", currentRating));
                renderStarButtons();
            });
            starsBox.getChildren().add(starBtn);
        }
    }

    private void loadRecipients() {
        String type = recipientTypeCombo.getValue();
        personCombo.getItems().clear();

        FirebaseDAO.getInstance().getAllEmployees().thenAccept(users -> {
            Platform.runLater(() -> {
                for (User u : users) {
                    if ("HR Team".equalsIgnoreCase(type) && "HR".equalsIgnoreCase(u.getRole())) {
                        personCombo.getItems().add(new UserItem(u.getEmail(), u.getName(), "HR"));
                    } else if ("Employee".equalsIgnoreCase(type) && !"HR".equalsIgnoreCase(u.getRole()) && !"MANAGER".equalsIgnoreCase(u.getRole())) {
                        personCombo.getItems().add(new UserItem(u.getEmail(), u.getName(), u.getRole() != null ? u.getRole() : "Employee"));
                    }
                }
                if (!personCombo.getItems().isEmpty()) {
                    personCombo.getSelectionModel().select(0);
                }
            });
        });
    }

    private void handleSendFeedback() {
        UserItem recipient = personCombo.getValue();
        String comment = feedbackText.getText().trim();
        String category = categoryCombo.getValue();

        if (recipient == null || comment.isEmpty()) {
            showModernToast("Validation Error", "Please select a recipient and enter feedback comments.", false);
            return;
        }

        EmployeeFeedback fb = new EmployeeFeedback();
        fb.setEmployeeEmail(recipient.getEmail().toLowerCase().trim());
        fb.setReviewerEmail(UserSession.getCurrentUser() != null && UserSession.getCurrentUser().getEmail() != null ? UserSession.getCurrentUser().getEmail().toLowerCase().trim() : "manager@skillverse.com");
        fb.setReviewerName(UserSession.getCurrentUser() != null && UserSession.getCurrentUser().getName() != null ? UserSession.getCurrentUser().getName() : "Manager");
        fb.setReviewerRole("Manager");
        fb.setCategoryTag(category.toUpperCase());
        fb.setRating(currentRating);
        fb.setFeedbackText(comment);
        fb.setAcknowledged(false);
        fb.setCreatedAt(new Date());

        FirebaseDAO.getInstance().sendFeedback(fb).thenRun(() -> {
            Platform.runLater(() -> {
                feedbackText.clear();
                loadFeedbackHistory();
                showModernToast("Feedback Synchronized! 💬", "Feedback sent successfully and synchronized with " + recipient.getName() + "'s Portal!", true);
            });
        });
    }

    private void loadFeedbackHistory() {
        if (UserSession.getCurrentUser() == null) return;
        String managerEmail = UserSession.getCurrentUser().getEmail().toLowerCase().trim();

        FirebaseDAO.getInstance().getFeedbackSentByManager(managerEmail).thenAccept(list -> {
            Platform.runLater(() -> {
                historyContainer.getChildren().clear();
                if (list == null || list.isEmpty()) {
                    Label emptyLbl = new Label("No feedback sent yet. Use the form to submit coaching notes.");
                    emptyLbl.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-padding: 20;");
                    historyContainer.getChildren().add(emptyLbl);
                } else {
                    for (EmployeeFeedback item : list) {
                        historyContainer.getChildren().add(createHistoryCard(item));
                    }
                }
            });
        });
    }

    private VBox createHistoryCard(EmployeeFeedback fb) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(14));
        card.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8; -fx-border-color: #E2E8F0; -fx-border-radius: 8;");

        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label toLbl = new Label("To: " + fb.getEmployeeEmail());
        toLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #1E293B; -fx-font-size: 13px;");
        HBox.setHgrow(toLbl, Priority.ALWAYS);

        Label catBadge = new Label(fb.getCategoryTag());
        catBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1D4ED8; -fx-padding: 2 8; -fx-background-radius: 4; -fx-font-size: 10px; -fx-font-weight: bold;");

        Label ratingBadge = new Label("★ " + fb.getRating());
        ratingBadge.setStyle("-fx-text-fill: #D97706; -fx-font-weight: bold; -fx-font-size: 12px;");

        topRow.getChildren().addAll(toLbl, catBadge, ratingBadge);

        Label commentLbl = new Label(fb.getFeedbackText());
        commentLbl.setWrapText(true);
        commentLbl.setStyle("-fx-text-fill: #475569; -fx-font-size: 12px;");

        card.getChildren().addAll(topRow, commentLbl);
        return card;
    }
}
