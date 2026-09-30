package com.skillverse.hr.view;

import com.skillverse.CommonFeatures.SpeakUpRepository;
import com.skillverse.CommonFeatures.SpeakUpTicket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class HRSpeakUpView {

    private VBox escalationsContainer;

    public VBox createContent() {
        VBox mainRoot = new VBox(20);
        mainRoot.setPadding(new Insets(20));
        mainRoot.setStyle("-fx-background-color: #F6F8FC;");

        // Header Section
        VBox titleBox = new VBox(4);
        Text title = new Text("📢 HR Escalations & Speak Up Desk");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setFill(Color.web("#0F172A"));

        Text subtitle = new Text("Submit policy violations, process non-compliance, and inter-departmental issues to Management.");
        subtitle.setFont(Font.font("Arial", 13));
        subtitle.setFill(Color.web("#64748B"));

        titleBox.getChildren().addAll(title, subtitle);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(42);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(58);
        grid.getColumnConstraints().addAll(col1, col2);

        VBox formCard = new VBox(14);
        formCard.setPadding(new Insets(20));
        formCard.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: #E2E8F0; -fx-border-radius: 14px;");

        Text formTitle = new Text("Submit HR Escalation to Manager");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        formTitle.setFill(Color.web("#0F172A"));

        // Subject
        Label subjLbl = new Label("ESCALATION SUBJECT");
        subjLbl.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        subjLbl.setTextFill(Color.web("#64748B"));

        TextField subjField = new TextField();
        subjField.setPromptText("Brief subject (e.g. Policy Violation, Review Delay)");
        subjField.setPrefHeight(38);
        subjField.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 0 10px;");

        // Category
        Label catLbl = new Label("CATEGORY");
        catLbl.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        catLbl.setTextFill(Color.web("#64748B"));

        ComboBox<String> catBox = new ComboBox<>();
        catBox.getItems().addAll("Process Non-Compliance", "Inter-Department Dispute", "Policy Violation", "Other");
        catBox.setValue("Process Non-Compliance");
        catBox.setMaxWidth(Double.MAX_VALUE);
        catBox.setPrefHeight(38);

        // Priority
        Label prioLbl = new Label("PRIORITY");
        prioLbl.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        prioLbl.setTextFill(Color.web("#64748B"));

        ComboBox<String> prioBox = new ComboBox<>();
        prioBox.getItems().addAll("Low", "Medium", "High", "Urgent");
        prioBox.setValue("High");
        prioBox.setMaxWidth(Double.MAX_VALUE);
        prioBox.setPrefHeight(38);

        // Description
        Label descLbl = new Label("DETAILED ESCALATION NOTES");
        descLbl.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        descLbl.setTextFill(Color.web("#64748B"));

        TextArea descArea = new TextArea();
        descArea.setPromptText("Provide comprehensive context, affected departments, and requested management action...");
        descArea.setPrefRowCount(5);
        descArea.setWrapText(true);
        descArea.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        // Submit Button
        Button submitBtn = new Button("Submit Escalation to Manager →");
        submitBtn.setMaxWidth(Double.MAX_VALUE);
        submitBtn.setPrefHeight(42);
        submitBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-cursor: hand;");

        submitBtn.setOnAction(e -> {
            String subjectText = subjField.getText().trim();
            String descText = descArea.getText().trim();

            if (subjectText.isEmpty() || descText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter both a Subject and Description for your escalation.", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            SpeakUpTicket ticket = new SpeakUpTicket(
                    "SPK-" + (2000 + (int) (Math.random() * 8999)),
                    "HR",
                    "Alice Johnson (HR Lead)",
                    "hr@skillverse.com",
                    catBox.getValue(),
                    prioBox.getValue().toUpperCase(),
                    subjectText,
                    descText,
                    "PENDING",
                    null,
                    "Just now",
                    false
            );

            SpeakUpRepository.getInstance().addTicket(ticket);

            subjField.clear();
            descArea.clear();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "HR Escalation submitted to Manager successfully.", ButtonType.OK);
            successAlert.setHeaderText("Escalation Sent 📢");
            successAlert.showAndWait();
        });

        formCard.getChildren().addAll(formTitle, subjLbl, subjField, catLbl, catBox, prioLbl, prioBox, descLbl, descArea, submitBtn);

        VBox historyCard = new VBox(14);
        historyCard.setPadding(new Insets(20));
        historyCard.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: #E2E8F0; -fx-border-radius: 14px;");

        Text historyTitle = new Text("Submitted HR Escalation History");
        historyTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        historyTitle.setFill(Color.web("#0F172A"));

        escalationsContainer = new VBox(12);

        ScrollPane historyScroll = new ScrollPane(escalationsContainer);
        historyScroll.setFitToWidth(true);
        historyScroll.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        historyScroll.setPrefHeight(450);

        historyCard.getChildren().addAll(historyTitle, historyScroll);

        grid.add(formCard, 0, 0);
        grid.add(historyCard, 1, 0);

        mainRoot.getChildren().addAll(titleBox, grid);

        SpeakUpRepository.getInstance().addListener(this::refreshEscalationCards);
        refreshEscalationCards();

        return mainRoot;
    }

    private void refreshEscalationCards() {
        if (escalationsContainer == null) {
            return;
        }
        escalationsContainer.getChildren().clear();

        List<SpeakUpTicket> list = SpeakUpRepository.getInstance().getTicketsForHR("hr@skillverse.com");

        if (list.isEmpty()) {
            VBox emptyBox = new VBox(8);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(40));
            Text emptyText = new Text("No HR escalations filed yet.");
            emptyText.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            emptyText.setFill(Color.web("#94A3B8"));
            emptyBox.getChildren().add(emptyText);
            escalationsContainer.getChildren().add(emptyBox);
            return;
        }

        for (SpeakUpTicket t : list) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(14));
            card.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            HBox topRow = new HBox(8);
            topRow.setAlignment(Pos.CENTER_LEFT);

            Label ticketBadge = new Label(t.getTicketId());
            ticketBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 3px 8px; -fx-background-radius: 6px;");

            Label catBadge = new Label(t.getCategory());
            catBadge.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-size: 11px; -fx-padding: 3px 8px; -fx-background-radius: 6px;");

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label statusBadge = getStatusBadge(t.getStatus());

            topRow.getChildren().addAll(ticketBadge, catBadge, sp, statusBadge);

            Text subjText = new Text(t.getSubject());
            subjText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            subjText.setFill(Color.web("#0F172A"));

            Text descText = new Text(t.getDescription());
            descText.setFont(Font.font("Arial", 12));
            descText.setFill(Color.web("#475569"));
            descText.setWrappingWidth(420);

            HBox metaRow = new HBox(12);
            Text dateText = new Text("Submitted: " + t.getTimestamp() + "  •  Priority: " + t.getPriority());
            dateText.setFont(Font.font("Arial", 11));
            dateText.setFill(Color.web("#94A3B8"));
            metaRow.getChildren().add(dateText);

            card.getChildren().addAll(topRow, subjText, descText, metaRow);

            if (t.getManagerResolutionNote() != null && !t.getManagerResolutionNote().trim().isEmpty()) {
                VBox responseBox = new VBox(4);
                responseBox.setPadding(new Insets(10));
                responseBox.setStyle("-fx-background-color: #F0FDF4; -fx-border-color: #BBF7D0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

                Text respTitle = new Text("💬 Manager Action & Resolution:");
                respTitle.setFont(Font.font("Arial", FontWeight.BOLD, 11));
                respTitle.setFill(Color.web("#166534"));

                Text respBody = new Text(t.getManagerResolutionNote());
                respBody.setFont(Font.font("Arial", 12));
                respBody.setFill(Color.web("#15803D"));

                responseBox.getChildren().addAll(respTitle, respBody);
                card.getChildren().add(responseBox);
            }

            escalationsContainer.getChildren().add(card);
        }
    }

    private Label getStatusBadge(String status) {
        Label label = new Label();
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 3px 10px; -fx-background-radius: 12px;");

        switch (status.toUpperCase()) {
            case "PENDING":
                label.setText("Pending 🔴");
                label.setStyle(label.getStyle() + "-fx-background-color: #FEF2F2; -fx-text-fill: #DC2626;");
                break;
            case "UNDER_REVIEW":
                label.setText("Under Review 🟡");
                label.setStyle(label.getStyle() + "-fx-background-color: #FEF3C7; -fx-text-fill: #D97706;");
                break;
            case "ACTION_TAKEN":
                label.setText("Action Taken 🟢");
                label.setStyle(label.getStyle() + "-fx-background-color: #DCFCE7; -fx-text-fill: #15803D;");
                break;
            case "RESOLVED":
                label.setText("Resolved ⚪");
                label.setStyle(label.getStyle() + "-fx-background-color: #F1F5F9; -fx-text-fill: #475569;");
                break;
            default:
                label.setText(status);
                label.setStyle(label.getStyle() + "-fx-background-color: #F1F5F9; -fx-text-fill: #475569;");
        }
        return label;
    }
}
