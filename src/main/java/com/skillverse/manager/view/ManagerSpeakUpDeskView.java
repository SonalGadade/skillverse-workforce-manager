package com.skillverse.manager.view;

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

public class ManagerSpeakUpDeskView {

    private VBox employeeTicketsContainer;
    private VBox hrEscalationsContainer;

    private Label totalMetricVal;
    private Label pendingMetricVal;
    private Label resolvedMetricVal;
    private Label highPrioMetricVal;

    public VBox createContent(Runnable backAction) {
        VBox mainRoot = new VBox(20);
        mainRoot.setPadding(new Insets(20));
        mainRoot.setStyle("-fx-background-color: #F6F8FC;");

      
        VBox titleBox = new VBox(4);
        Text title = new Text("🛡️ Speak Up Desk — Confidential Grievance & Escalation Portal");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setFill(Color.web("#0F172A"));

        Text subtitle = new Text("Review, investigate, and resolve employee grievances and HR escalations with full confidentiality.");
        subtitle.setFont(Font.font("Arial", 13));
        subtitle.setFill(Color.web("#64748B"));

        titleBox.getChildren().addAll(title, subtitle);

        
        HBox metricRow = new HBox(16);

        totalMetricVal = new Label("0");
        VBox cardTotal = createMetricCard("TOTAL RECEIVED", totalMetricVal, "#2563EB", "#9abce9");

        pendingMetricVal = new Label("0");
        VBox cardPending = createMetricCard("PENDING REVIEWS", pendingMetricVal, "#D97706", "#ddc873");

        resolvedMetricVal = new Label("0");
        VBox cardResolved = createMetricCard("RESOLVED CASES", resolvedMetricVal, "#15803D", "#6bc189");

        highPrioMetricVal = new Label("0");
        VBox cardHighPrio = createMetricCard("HIGH PRIORITY ALERTS", highPrioMetricVal, "#DC2626", "#cc7373");

        metricRow.getChildren().addAll(cardTotal, cardPending, cardResolved, cardHighPrio);

       
        StackPane tabContentStack = new StackPane();

        employeeTicketsContainer = new VBox(14);
        employeeTicketsContainer.setPadding(new Insets(18));
        ScrollPane empScroll = new ScrollPane(employeeTicketsContainer);
        empScroll.setFitToWidth(true);
        empScroll.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");
        empScroll.setPrefHeight(500);

        hrEscalationsContainer = new VBox(14);
        hrEscalationsContainer.setPadding(new Insets(18));
        ScrollPane hrScroll = new ScrollPane(hrEscalationsContainer);
        hrScroll.setFitToWidth(true);
        hrScroll.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");
        hrScroll.setPrefHeight(500);
        hrScroll.setVisible(false);
        hrScroll.setManaged(false);

        tabContentStack.getChildren().addAll(empScroll, hrScroll);

       
        HBox tabHeaderRow = new HBox(8);
        tabHeaderRow.setPadding(new Insets(6));
        tabHeaderRow.setAlignment(Pos.CENTER_LEFT);
        tabHeaderRow.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Button empTabBtn = new Button("👥   Employee Grievances");
        Button hrTabBtn = new Button("🏢   HR Escalations");

        String activeStyle = "-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(37,99,235,0.25), 8, 0, 0, 2);";
        String inactiveStyle = "-fx-background-color: #EFF6FF; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-border-color: #DBEAFE; -fx-border-radius: 8px; -fx-cursor: hand;";

        empTabBtn.setStyle(activeStyle);
        hrTabBtn.setStyle(inactiveStyle);

      
        empTabBtn.setOnMouseEntered(e -> {
            if (!empScroll.isVisible()) {
                empTabBtn.setStyle("-fx-background-color: #DBEAFE; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-border-color: #BFDBFE; -fx-border-radius: 8px; -fx-cursor: hand;");
            }
        });
        empTabBtn.setOnMouseExited(e -> {
            if (!empScroll.isVisible()) {
                empTabBtn.setStyle(inactiveStyle);
            }
        });

        hrTabBtn.setOnMouseEntered(e -> {
            if (!hrScroll.isVisible()) {
                hrTabBtn.setStyle("-fx-background-color: #DBEAFE; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-border-color: #BFDBFE; -fx-border-radius: 8px; -fx-cursor: hand;");
            }
        });
        hrTabBtn.setOnMouseExited(e -> {
            if (!hrScroll.isVisible()) {
                hrTabBtn.setStyle(inactiveStyle);
            }
        });

        empTabBtn.setOnAction(e -> {
            empTabBtn.setStyle(activeStyle);
            hrTabBtn.setStyle(inactiveStyle);
            empScroll.setVisible(true);
            empScroll.setManaged(true);
            hrScroll.setVisible(false);
            hrScroll.setManaged(false);
        });

        hrTabBtn.setOnAction(e -> {
            hrTabBtn.setStyle(activeStyle);
            empTabBtn.setStyle(inactiveStyle);
            hrScroll.setVisible(true);
            hrScroll.setManaged(true);
            empScroll.setVisible(false);
            empScroll.setManaged(false);
        });

        tabHeaderRow.getChildren().addAll(empTabBtn, hrTabBtn);

        mainRoot.getChildren().addAll(titleBox, metricRow, tabHeaderRow, tabContentStack);

        SpeakUpRepository.getInstance().addListener(this::refreshData);
        refreshData();

        return mainRoot;
    }

    private void refreshData() {
        if (employeeTicketsContainer == null || hrEscalationsContainer == null) return;

        List<SpeakUpTicket> empList = SpeakUpRepository.getInstance().getEmployeeTicketsForManager();
        List<SpeakUpTicket> hrList = SpeakUpRepository.getInstance().getHREscalationsForManager();

      
        int total = empList.size() + hrList.size();
        long pending = empList.stream().filter(t -> !"RESOLVED".equalsIgnoreCase(t.getStatus())).count() +
                       hrList.stream().filter(t -> !"RESOLVED".equalsIgnoreCase(t.getStatus())).count();
        long resolved = empList.stream().filter(t -> "RESOLVED".equalsIgnoreCase(t.getStatus())).count() +
                        hrList.stream().filter(t -> "RESOLVED".equalsIgnoreCase(t.getStatus())).count();
        long highPrio = empList.stream().filter(t -> "HIGH".equalsIgnoreCase(t.getPriority()) || "URGENT".equalsIgnoreCase(t.getPriority())).count() +
                        hrList.stream().filter(t -> "HIGH".equalsIgnoreCase(t.getPriority()) || "URGENT".equalsIgnoreCase(t.getPriority())).count();

        totalMetricVal.setText(String.valueOf(total));
        pendingMetricVal.setText(String.valueOf(pending));
        resolvedMetricVal.setText(String.valueOf(resolved));
        highPrioMetricVal.setText(String.valueOf(highPrio));

     
        employeeTicketsContainer.getChildren().clear();
        if (empList.isEmpty()) {
            employeeTicketsContainer.getChildren().add(createEmptyLabel("No employee grievances received."));
        } else {
            for (SpeakUpTicket ticket : empList) {
                employeeTicketsContainer.getChildren().add(createTicketCard(ticket, true));
            }
        }

       
        hrEscalationsContainer.getChildren().clear();
        if (hrList.isEmpty()) {
            hrEscalationsContainer.getChildren().add(createEmptyLabel("No HR escalations received."));
        } else {
            for (SpeakUpTicket ticket : hrList) {
                hrEscalationsContainer.getChildren().add(createTicketCard(ticket, false));
            }
        }
    }

    private VBox createTicketCard(SpeakUpTicket ticket, boolean isEmployee) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

     
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Label ticketBadge = new Label(ticket.getTicketId());
        ticketBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 3px 8px; -fx-background-radius: 6px;");

        Label catBadge = new Label(ticket.getCategory());
        catBadge.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-size: 11px; -fx-padding: 3px 8px; -fx-background-radius: 6px;");

        Label prioBadge = getPriorityBadge(ticket.getPriority());

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label statusBadge = getStatusBadge(ticket.getStatus());

        topBar.getChildren().addAll(ticketBadge, catBadge, prioBadge, sp, statusBadge);

    
        Text subj = new Text(ticket.getSubject());
        subj.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        subj.setFill(Color.web("#0F172A"));

        Text desc = new Text(ticket.getDescription());
        desc.setFont(Font.font("Arial", 12.5));
        desc.setFill(Color.web("#334155"));

       
        HBox bottomRow = new HBox(12);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        String nameDisplay = ticket.isAnonymous() ? "👤 Anonymous Employee" : ("👤 " + ticket.getSenderName() + " (" + ticket.getSenderEmail() + ")");
        Text submitter = new Text(nameDisplay + "  •  " + ticket.getTimestamp());
        submitter.setFont(Font.font("Arial", 11));
        submitter.setFill(Color.web("#64748B"));

        Region sp2 = new Region();
        HBox.setHgrow(sp2, Priority.ALWAYS);

        Button actionBtn = new Button(isEmployee ? "Resolve / Reply →" : "Respond / Mark Resolved →");
        actionBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11.5px; -fx-padding: 6px 14px; -fx-background-radius: 6px; -fx-cursor: hand;");

        actionBtn.setOnAction(e -> openResolutionDialog(ticket));

        bottomRow.getChildren().addAll(submitter, sp2, actionBtn);

        card.getChildren().addAll(topBar, subj, desc, bottomRow);

        if (ticket.getManagerResolutionNote() != null && !ticket.getManagerResolutionNote().trim().isEmpty()) {
            VBox noteBox = new VBox(4);
            noteBox.setPadding(new Insets(10));
            noteBox.setStyle("-fx-background-color: #F0FDF4; -fx-border-color: #BBF7D0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

            Text nTitle = new Text("Manager Resolution Note:");
            nTitle.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            nTitle.setFill(Color.web("#166534"));

            Text nBody = new Text(ticket.getManagerResolutionNote());
            nBody.setFont(Font.font("Arial", 12));
            nBody.setFill(Color.web("#15803D"));

            noteBox.getChildren().addAll(nTitle, nBody);
            card.getChildren().add(noteBox);
        }

        return card;
    }

    private void openResolutionDialog(SpeakUpTicket ticket) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Resolve Grievance - " + ticket.getTicketId());

        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().add(ButtonType.CLOSE);
        pane.lookupButton(ButtonType.CLOSE).setVisible(false);
        pane.setStyle("-fx-background-color: white;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(24));
        content.setPrefWidth(460);

        Text title = new Text("Resolve Grievance: " + ticket.getTicketId());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Text subj = new Text(ticket.getSubject());
        subj.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        subj.setFill(Color.web("#2563EB"));

        Label statusLbl = new Label("UPDATE STATUS");
        statusLbl.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        statusLbl.setTextFill(Color.web("#64748B"));

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("UNDER_REVIEW", "ACTION_TAKEN", "RESOLVED");
        statusCombo.setValue(ticket.getStatus());
        statusCombo.setMaxWidth(Double.MAX_VALUE);

        Label noteLbl = new Label("RESOLUTION / RESPONSE NOTE");
        noteLbl.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        noteLbl.setTextFill(Color.web("#64748B"));

        TextArea noteArea = new TextArea(ticket.getManagerResolutionNote() != null ? ticket.getManagerResolutionNote() : "");
        noteArea.setPromptText("Enter your investigation findings, action taken, or response to the submitter...");
        noteArea.setPrefRowCount(4);

        Button saveBtn = new Button("Save Resolution & Update Status →");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setPrefHeight(40);
        saveBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");

        saveBtn.setOnAction(e -> {
            SpeakUpRepository.getInstance().updateTicketStatus(ticket.getTicketId(), statusCombo.getValue(), noteArea.getText());
            dialog.close();
        });

        content.getChildren().addAll(title, subj, statusLbl, statusCombo, noteLbl, noteArea, saveBtn);
        pane.setContent(content);

        dialog.showAndWait();
    }

    private VBox createMetricCard(String label, Label valueLabel, String textHex, String bgHex) {
        VBox card = new VBox(4);
        card.setPadding(new Insets(14, 18, 14, 18));
        card.setPrefWidth(220);
        card.setStyle("-fx-background-color: " + bgHex + "; -fx-background-radius: 12px; -fx-border-color: derive(" + bgHex + ", -10%); -fx-border-radius: 12px;");

        Text lbl = new Text(label);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        lbl.setFill(Color.web(textHex));

        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        valueLabel.setTextFill(Color.web(textHex));

        card.getChildren().addAll(lbl, valueLabel);
        return card;
    }

    private Label createEmptyLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbl.setTextFill(Color.web("#94A3B8"));
        lbl.setPadding(new Insets(30));
        return lbl;
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

    private Label getPriorityBadge(String prio) {
        Label label = new Label(prio);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 2px 7px; -fx-background-radius: 6px;");

        switch (prio.toUpperCase()) {
            case "URGENT":
            case "HIGH":
                label.setStyle(label.getStyle() + "-fx-background-color: #FEE2E2; -fx-text-fill: #991B1B;");
                break;
            case "MEDIUM":
                label.setStyle(label.getStyle() + "-fx-background-color: #FEF3C7; -fx-text-fill: #92400E;");
                break;
            default:
                label.setStyle(label.getStyle() + "-fx-background-color: #F1F5F9; -fx-text-fill: #475569;");
        }
        return label;
    }
}
