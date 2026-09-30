package com.skillverse.trainer.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.SeminarEvent;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeminarPage {

    public static void show(Stage stage, Trainer trainer) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + AppTheme.BG + ";");
        root.setLeft(TrainerSidebar.create(stage, trainer, "Seminars & Events"));

       
        StackPane contentStack = new StackPane();
        contentStack.setPadding(new Insets(24));

    
        VBox toastBox = new VBox();

        VBox listView = new VBox(20);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        VBox heading = new VBox(4,
                AppTheme.title("Seminars & Live Workshops"),
                AppTheme.subtitle("Schedule and host live training events, webinars, and technical workshops.")
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button scheduleNewBtn = new Button("+ Schedule New Seminar / Event");
        scheduleNewBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand;");

        header.getChildren().addAll(heading, headerSpacer, scheduleNewBtn);

      
        VBox eventsContainer = new VBox(14);
        ScrollPane scrollPane = new ScrollPane(eventsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        listView.getChildren().addAll(header, toastBox, scrollPane);

        VBox formView = new VBox(20);
        formView.setVisible(false);

        HBox formHeader = new HBox(15);
        formHeader.setAlignment(Pos.CENTER_LEFT);
        VBox formHeading = new VBox(4,
                AppTheme.title("Schedule Seminar / Event"),
                AppTheme.subtitle("Publish live webinars and interactive sessions to the employee portal notice board.")
        );

        Region formSpacer = new Region();
        HBox.setHgrow(formSpacer, Priority.ALWAYS);

        Button backToListBtn = new Button("← Cancel & Back to List");
        backToListBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 8px; -fx-cursor: hand;");

        formHeader.getChildren().addAll(formHeading, formSpacer, backToListBtn);

    
        VBox formCard = new VBox(16);
        formCard.setPadding(new Insets(24));
        formCard.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(16);

      
        Label titleLbl = createFormLabel("Seminar Title *");
        TextField titleField = AppTheme.field("e.g. Advanced Java Concurrency & Async Masterclass");
        grid.add(titleLbl, 0, 0);
        grid.add(titleField, 0, 1);

     
        Label topicLbl = createFormLabel("Topic / Category *");
        TextField topicField = AppTheme.field("e.g. Software Architecture");
        grid.add(topicLbl, 1, 0);
        grid.add(topicField, 1, 1);


        Label dateLbl = createFormLabel("Event Date *");
        DatePicker datePicker = new DatePicker(LocalDate.now().plusDays(1));
        datePicker.setPrefHeight(43);
        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-radius: 8px;");
        grid.add(dateLbl, 0, 2);
        grid.add(datePicker, 0, 3);

      
        Label timeLbl = createFormLabel("Event Time *");
        TextField timeField = AppTheme.field("e.g. 10:00 AM - 12:00 PM EST");
        grid.add(timeLbl, 1, 2);
        grid.add(timeField, 1, 3);

       
        Label deptLbl = createFormLabel("Target Department *");
        ComboBox<String> deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("ALL", "Engineering", "Product & Design", "Human Resources", "Sales & Marketing", "Finance");
        deptCombo.setValue("ALL");
        deptCombo.setPrefHeight(43);
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-radius: 8px;");
        grid.add(deptLbl, 0, 4);
        grid.add(deptCombo, 0, 5);

       
        Label venueLbl = createFormLabel("Live Meeting Link / Room Venue *");
        TextField venueField = AppTheme.field("e.g. https://meet.google.com/abc-defg-hij or Conference Room 3B");
        grid.add(venueLbl, 1, 4);
        grid.add(venueField, 1, 5);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

   
        Label descLbl = createFormLabel("Event Description & Agenda");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Enter detailed session objectives, prerequisites, and meeting guidelines...");
        descArea.setPrefRowCount(4);
        descArea.setWrapText(true);
        descArea.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-radius: 8px; -fx-padding: 8px;");

       
        Button publishBtn = new Button("🚀 Publish Seminar to Portal");
        publishBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12px 28px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-size: 14px;");

        formCard.getChildren().addAll(grid, descLbl, descArea, publishBtn);
        formView.getChildren().addAll(formHeader, formCard);

        contentStack.getChildren().addAll(listView, formView);
        root.setCenter(contentStack);

        Runnable loadEvents = () -> {
            eventsContainer.getChildren().clear();
            ProgressIndicator spinner = new ProgressIndicator();
            eventsContainer.getChildren().add(new HBox(10, new Label("Loading live seminars..."), spinner));

            FirebaseDAO.getInstance().getAllSeminarEvents().thenAccept(events -> {
                Platform.runLater(() -> {
                    eventsContainer.getChildren().clear();
                    if (events == null || events.isEmpty()) {
                        VBox emptyBox = AppTheme.card(
                                new Label("📅 No Scheduled Seminars Yet"),
                                new Label("Click '+ Schedule New Seminar / Event' above to create and publish your first session.")
                        );
                        emptyBox.setAlignment(Pos.CENTER);
                        eventsContainer.getChildren().add(emptyBox);
                    } else {
                        for (SeminarEvent event : events) {
                            eventsContainer.getChildren().add(buildSeminarCard(event));
                        }
                    }
                });
            });
        };

       
        scheduleNewBtn.setOnAction(e -> {
            listView.setVisible(false);
            formView.setVisible(true);
        });

        backToListBtn.setOnAction(e -> {
            formView.setVisible(false);
            listView.setVisible(true);
        });

        publishBtn.setOnAction(e -> {
            String title = titleField.getText() != null ? titleField.getText().trim() : "";
            String topic = topicField.getText() != null ? topicField.getText().trim() : "";
            String dateStr = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
            String timeStr = timeField.getText() != null ? timeField.getText().trim() : "";
            String dept = deptCombo.getValue();
            String venue = venueField.getText() != null ? venueField.getText().trim() : "";

            if (title.isEmpty() || topic.isEmpty() || dateStr.isEmpty() || timeStr.isEmpty()) {
                showToast(toastBox, "⚠️ Please fill in all required fields (Title, Topic, Date, Time).", "#DC2626", "#FEF2F2");
                return;
            }

            SeminarEvent event = new SeminarEvent();
            event.setTitle(title);
            event.setTopic(topic);
            event.setEventDate(dateStr);
            event.setEventTime(timeStr);
            event.setTargetDepartment(dept);
            event.setMeetingLinkOrVenue(venue);
            
            String tEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "trainer@skillverse.com";
            String tName = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getFullName() : "Senior Trainer";
            event.setTrainerEmail(tEmail);
            event.setTrainerName(tName);
            event.setCreatedAt(new Date());

            publishBtn.setDisable(true);
            publishBtn.setText("Publishing...");

            FirebaseDAO.getInstance().createSeminarEvent(event).thenAccept(success -> {
                Platform.runLater(() -> {
                    publishBtn.setDisable(false);
                    publishBtn.setText("🚀 Publish Seminar to Portal");
                    if (success) {
                        showToast(toastBox, "✅ Seminar '" + title + "' published successfully to Employee Portal!", "#16A34A", "#F0FDF4");
                        // Reset fields
                        titleField.clear();
                        topicField.clear();
                        timeField.clear();
                        venueField.clear();
                        descArea.clear();
                       
                        formView.setVisible(false);
                        listView.setVisible(true);
                        loadEvents.run();
                    } else {
                        showToast(toastBox, "❌ Failed to save seminar event to Firestore. Check connection.", "#DC2626", "#FEF2F2");
                    }
                });
            });
        });

       
        loadEvents.run();

        stage.setTitle("SkillVerse - Seminars & Events");
        stage.setScene(new Scene(root, 1280, 800));
        stage.show();
    }

    private static Label createFormLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        lbl.setTextFill(Color.web("#334155"));
        return lbl;
    }

    private static VBox buildSeminarCard(SeminarEvent event) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label dateBadge = new Label("📅 " + (event.getEventDate() != null ? event.getEventDate() : "TBD"));
        dateBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Label timeBadge = new Label("⏰ " + (event.getEventTime() != null ? event.getEventTime() : "TBD"));
        timeBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Label deptBadge = new Label("🎯 Dept: " + (event.getTargetDepartment() != null ? event.getTargetDepartment() : "ALL"));
        deptBadge.setStyle("-fx-background-color: #F3E8FF; -fx-text-fill: #7C3AED; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        topRow.getChildren().addAll(dateBadge, timeBadge, deptBadge, sp);

        Text titleText = new Text(event.getTitle() != null ? event.getTitle() : "Untitled Seminar");
        titleText.setFont(Font.font("System", FontWeight.BOLD, 17));
        titleText.setFill(Color.web("#0F172A"));

        Text topicText = new Text("Topic: " + (event.getTopic() != null ? event.getTopic() : "General"));
        topicText.setFont(Font.font("System", 13));
        topicText.setFill(Color.web("#64748B"));

        HBox bottomRow = new HBox(10);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        Label linkLbl = new Label("🔗 Location/Link: " + (event.getMeetingLinkOrVenue() != null && !event.getMeetingLinkOrVenue().isEmpty() ? event.getMeetingLinkOrVenue() : "Virtual Meeting Room"));
        linkLbl.setFont(Font.font("System", 12));
        linkLbl.setTextFill(Color.web("#0284C7"));

        Region sp2 = new Region();
        HBox.setHgrow(sp2, Priority.ALWAYS);

        Label trainerLbl = new Label("Hosted by " + (event.getTrainerName() != null ? event.getTrainerName() : "Trainer"));
        trainerLbl.setFont(Font.font("System", 12));
        trainerLbl.setTextFill(Color.web("#94A3B8"));

        bottomRow.getChildren().addAll(linkLbl, sp2, trainerLbl);

        card.getChildren().addAll(topRow, titleText, topicText, bottomRow);
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