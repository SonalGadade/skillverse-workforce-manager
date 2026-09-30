package com.skillverse.trainer.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.TrainerAnnouncement;
import com.skillverse.trainer.model.Trainer;
import com.skillverse.CommonFeatures.UserSession;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;

public class CommunicationPage {

    public static void show(Stage stage, Trainer trainer) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + AppTheme.BG + ";");
        root.setLeft(TrainerSidebar.create(stage, trainer, "Communication"));

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(24));

        VBox toastBox = new VBox();

       
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        VBox heading = new VBox(4,
                AppTheme.title("Trainer Communication & Notice Broadcaster"),
                AppTheme.subtitle("Broadcast course updates, test notifications, and department announcements directly to employee feeds.")
        );

        header.getChildren().add(heading);

      
        VBox formCard = new VBox(16);
        formCard.setPadding(new Insets(20));
        formCard.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");

        Text formHeaderTitle = new Text("📢 Broadcast New Announcement");
        formHeaderTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        formHeaderTitle.setFill(Color.web("#0F172A"));

        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(14);

        TextField headlineField = AppTheme.field("Announcement Headline (e.g. Schedule Update for Java Bootcamp)");
        ComboBox<String> priorityCombo = new ComboBox<>();
        priorityCombo.getItems().addAll("NORMAL", "HIGH");
        priorityCombo.setValue("NORMAL");
        priorityCombo.setPrefHeight(43);
        priorityCombo.setMaxWidth(Double.MAX_VALUE);
        priorityCombo.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-radius: 8px;");

        ComboBox<String> audienceCombo = new ComboBox<>();
        audienceCombo.getItems().addAll("ALL", "Engineering", "Product & Design", "Human Resources", "Sales & Marketing", "Finance");
        audienceCombo.setValue("ALL");
        audienceCombo.setPrefHeight(43);
        audienceCombo.setMaxWidth(Double.MAX_VALUE);
        audienceCombo.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-radius: 8px;");

        grid.add(new Label("Announcement Headline: *"), 0, 0);
        grid.add(headlineField, 0, 1);
        grid.add(new Label("Priority Level: *"), 1, 0);
        grid.add(priorityCombo, 1, 1);
        grid.add(new Label("Target Audience / Department: *"), 2, 0);
        grid.add(audienceCombo, 2, 1);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(50);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(25);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(25);
        grid.getColumnConstraints().addAll(c1, c2, c3);

        Label bodyLabel = new Label("Announcement Message Body: *");
        bodyLabel.setFont(Font.font("System", FontWeight.BOLD, 12));

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Write your broadcast message to employees here...");
        messageArea.setPrefRowCount(3);
        messageArea.setWrapText(true);
        messageArea.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-radius: 8px; -fx-padding: 8px;");

        HBox btnRow = new HBox();
        btnRow.setAlignment(Pos.CENTER_RIGHT);
        Button broadcastBtn = new Button("🚀 Broadcast Announcement Now");
        broadcastBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 24px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-size: 13px;");
        btnRow.getChildren().add(broadcastBtn);

        formCard.getChildren().addAll(formHeaderTitle, grid, bodyLabel, messageArea, btnRow);

       
        VBox feedSection = new VBox(14);
        Text feedTitle = new Text("📋 Broadcast History & Portal Notice Feed");
        feedTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        feedTitle.setFill(Color.web("#0F172A"));

        VBox announcementsContainer = new VBox(12);
        feedSection.getChildren().addAll(feedTitle, announcementsContainer);

        ScrollPane scrollPane = new ScrollPane(new VBox(20, formCard, feedSection));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        mainContent.getChildren().addAll(header, toastBox, scrollPane);
        root.setCenter(mainContent);

        Runnable loadAnnouncements = () -> {
            announcementsContainer.getChildren().clear();
            ProgressIndicator spinner = new ProgressIndicator();
            announcementsContainer.getChildren().add(new HBox(10, new Label("Loading broadcast history..."), spinner));

            FirebaseDAO.getInstance().getRecentAnnouncements().thenAccept(announcements -> {
                Platform.runLater(() -> {
                    announcementsContainer.getChildren().clear();
                    if (announcements == null || announcements.isEmpty()) {
                        VBox emptyBox = AppTheme.card(
                                new Label("📢 No Announcements Broadcasted Yet"),
                                new Label("Use the form above to broadcast your first announcement to employees.")
                        );
                        emptyBox.setAlignment(Pos.CENTER);
                        announcementsContainer.getChildren().add(emptyBox);
                    } else {
                        for (TrainerAnnouncement ann : announcements) {
                            announcementsContainer.getChildren().add(buildAnnouncementCard(ann));
                        }
                    }
                });
            });
        };

        broadcastBtn.setOnAction(e -> {
            String title = headlineField.getText() != null ? headlineField.getText().trim() : "";
            String msg = messageArea.getText() != null ? messageArea.getText().trim() : "";
            String priority = priorityCombo.getValue();
            String audience = audienceCombo.getValue();

            if (title.isEmpty() || msg.isEmpty()) {
                showToast(toastBox, "⚠️ Please provide both headline and message body.", "#DC2626", "#FEF2F2");
                return;
            }

            TrainerAnnouncement ann = new TrainerAnnouncement();
            ann.setTitle(title);
            ann.setMessage(msg);
            ann.setPriority(priority);
            ann.setTargetAudience(audience);

            String tName = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getFullName() : "Trainer";
            String tEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "trainer@skillverse.com";
            ann.setTrainerName(tName);
            ann.setTrainerEmail(tEmail);
            ann.setCreatedAt(new Date());

            broadcastBtn.setDisable(true);
            broadcastBtn.setText("Broadcasting...");

            FirebaseDAO.getInstance().createAnnouncement(ann).thenAccept(success -> {
                Platform.runLater(() -> {
                    broadcastBtn.setDisable(false);
                    broadcastBtn.setText("🚀 Broadcast Announcement Now");
                    if (success) {
                        showToast(toastBox, "✅ Announcement '" + title + "' broadcasted to Employee Portal!", "#16A34A", "#F0FDF4");
                        headlineField.clear();
                        messageArea.clear();
                        loadAnnouncements.run();
                    } else {
                        showToast(toastBox, "❌ Error broadcasting announcement.", "#DC2626", "#FEF2F2");
                    }
                });
            });
        });

        loadAnnouncements.run();

        stage.setTitle("SkillVerse - Communication");
        stage.setScene(new Scene(root, 1280, 800));
        stage.show();
    }

    private static VBox buildAnnouncementCard(TrainerAnnouncement ann) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        boolean isHigh = "HIGH".equalsIgnoreCase(ann.getPriority());
        Label prioBadge = new Label(isHigh ? "🔥 HIGH PRIORITY" : "📌 NORMAL");
        prioBadge.setStyle(isHigh
                ? "-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 4px; -fx-font-size: 11px;"
                : "-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 4px; -fx-font-size: 11px;");

        Label audienceBadge = new Label("🎯 Target: " + (ann.getTargetAudience() != null ? ann.getTargetAudience() : "ALL"));
        audienceBadge.setStyle("-fx-background-color: #F3E8FF; -fx-text-fill: #7C3AED; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 4px; -fx-font-size: 11px;");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label dateLabel = new Label(ann.getCreatedAt() != null ? ann.getCreatedAt().toString() : "Just now");
        dateLabel.setFont(Font.font("System", 11));
        dateLabel.setTextFill(Color.web("#94A3B8"));

        topRow.getChildren().addAll(prioBadge, audienceBadge, sp, dateLabel);

        Text titleText = new Text(ann.getTitle() != null ? ann.getTitle() : "Announcement");
        titleText.setFont(Font.font("System", FontWeight.BOLD, 15));
        titleText.setFill(Color.web("#0F172A"));

        Text msgText = new Text(ann.getMessage() != null ? ann.getMessage() : "");
        msgText.setFont(Font.font("System", 13));
        msgText.setFill(Color.web("#334155"));

        Label footerLbl = new Label("Broadcasted by " + (ann.getTrainerName() != null ? ann.getTrainerName() : "Trainer"));
        footerLbl.setFont(Font.font("System", 11));
        footerLbl.setTextFill(Color.web("#64748B"));

        card.getChildren().addAll(topRow, titleText, msgText, footerLbl);
        return card;
    }

    private static void showToast(VBox toastContainer, String message, String textColor, String bgColor) {
        toastContainer.getChildren().clear();
        HBox toast = new HBox();
        toast.setPadding(new Insets(12, 16, 12, 16));
        toast.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: " + textColor + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        Label lbl = new Label(message);
        lbl.setStyle("-fx-text-fill: " + textColor + "; -fx-font-weight: bold; -fx-font-size: 13px;");
        toast.getChildren().add(lbl);
        toastContainer.getChildren().add(toast);
    }
}