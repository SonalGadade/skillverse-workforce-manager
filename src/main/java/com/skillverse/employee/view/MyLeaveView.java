package com.skillverse.employee.view;

import com.skillverse.CommonFeatures.LeaveRequest;
import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class MyLeaveView {

    private static final String BLUE = "#1648C8";
    private static final String PURPLE = "#7C3AED";
    private static final String BG = "#F8F8FD";
    private static final String BORDER = "#E7E8F0";

    private VBox leaveTableBody;
    private Label footerEntries;
    private String currentEmail;

    public VBox createLeaveContent(String email) {
        this.currentEmail = email != null && !email.isBlank() ? email
                : (UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "");

        VBox content = new VBox(22);
        content.setPadding(new Insets(35, 40, 40, 40));
        content.setStyle("-fx-background-color: " + BG + ";");

        HBox heading = new HBox();
        heading.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(5);

        Label small = new Label("LEAVE MANAGEMENT");
        small.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        small.setTextFill(Color.web(PURPLE));

        Label title = new Label("My Leave");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        Label sub = new Label("Apply for leave, track your requests and view your leave balance.");
        sub.setFont(Font.font(17));
        sub.setTextFill(Color.web("#374151"));

        titleBox.getChildren().addAll(small, title, sub);

        Region headingSpace = new Region();
        HBox.setHgrow(headingSpace, Priority.ALWAYS);

        Button apply = new Button("+   Apply Leave");
        apply.setPrefHeight(48);
        apply.setStyle(
                "-fx-background-color: linear-gradient(to right, #1648C8, #7C3AED);"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 10;"
        );

        apply.setOnAction(e -> showApplyLeaveModal());

        heading.getChildren().addAll(titleBox, headingSpace, apply);

        HBox cards = new HBox(20);
        cards.getChildren().addAll(
                balanceCard("\u2602", "Casual Leave", "10", "Days Available", "#E9FFF6", "#059669"),
                balanceCard("\u25A3", "Sick Leave", "8", "Days Available", "#EDF4FF", BLUE),
                balanceCard("\u2668", "Earned Leave", "15", "Days Available", "#FAF1FF", PURPLE),
                balanceCard("\u25A3", "Public Holidays", "12", "Days Remaining", "#FFF6E9", "#EA580C")
        );

        VBox table = new VBox();
        table.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 18;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 18;"
        );

        HBox filters = new HBox(25);
        filters.setAlignment(Pos.CENTER_LEFT);
        filters.setPadding(new Insets(20));

        Button all = filter("All Requests", true);
        Button pending = filter("Pending", false);
        Button approved = filter("Approved", false);
        Button rejected = filter("Rejected", false);

        Region filterSpace = new Region();
        HBox.setHgrow(filterSpace, Priority.ALWAYS);

        TextField filterField = new TextField();
        filterField.setPromptText("\u2315  Filter requests...");
        filterField.setPrefWidth(250);
        filterField.setPrefHeight(42);
        filterField.setStyle(
                "-fx-border-color: #E3E5EF;"
                + "-fx-border-radius: 12;"
                + "-fx-background-radius: 12;"
        );

        filters.getChildren().addAll(all, pending, approved, rejected, filterSpace, filterField);

        table.getChildren().add(filters);
        table.getChildren().add(new Separator());

        GridPane header = new GridPane();
        header.setPadding(new Insets(18, 25, 18, 25));
        header.setHgap(30);

        header.add(h("LEAVE TYPE"), 0, 0);
        header.add(h("DATES"), 1, 0);
        header.add(h("DURATION"), 2, 0);
        header.add(h("REASON"), 3, 0);
        header.add(h("STATUS"), 4, 0);

        header.getColumnConstraints().addAll(
                new ColumnConstraints(200),
                new ColumnConstraints(230),
                new ColumnConstraints(130),
                new ColumnConstraints(180),
                new ColumnConstraints(130)
        );

        table.getChildren().add(header);

        leaveTableBody = new VBox();
        table.getChildren().add(leaveTableBody);

        HBox footer = new HBox();
        footer.setPadding(new Insets(15));
        footer.setAlignment(Pos.CENTER_LEFT);

        footerEntries = new Label("Loading...");

        Region footerSpace = new Region();
        HBox.setHgrow(footerSpace, Priority.ALWAYS);

        Button previous = new Button("Previous");
        Button one = new Button("1");
        Button next = new Button("Next");

        previous.setStyle(
                "-fx-background-color: white;"
                + "-fx-border-color: #D9DCE7;"
                + "-fx-border-radius: 7;"
                + "-fx-background-radius: 7;"
        );

        one.setStyle(
                "-fx-background-color: " + BLUE + ";"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 7;"
        );

        next.setStyle(
                "-fx-background-color: white;"
                + "-fx-border-color: #D9DCE7;"
                + "-fx-border-radius: 7;"
                + "-fx-background-radius: 7;"
        );

        footer.getChildren().addAll(footerEntries, footerSpace, previous, one, next);
        table.getChildren().add(footer);

        HBox policy = new HBox(20);
        policy.setAlignment(Pos.CENTER_LEFT);
        policy.setPadding(new Insets(18));
        policy.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 15;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 15;"
        );

        Label icon = new Label("\u25A4");
        icon.setFont(Font.font(28));
        icon.setTextFill(Color.web(BLUE));

        VBox policyText = new VBox(4);

        Label policyTitle = new Label("Leave Policy");
        policyTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label policySub = new Label("Check out the company leave policy and guidelines.");
        policySub.setTextFill(Color.web("#6B7280"));

        policyText.getChildren().addAll(policyTitle, policySub);

        Region policySpace = new Region();
        HBox.setHgrow(policySpace, Priority.ALWAYS);

        Button viewPolicy = new Button("View Policy   \u2192");
        viewPolicy.setPrefHeight(42);
        viewPolicy.setStyle(
                "-fx-background-color: white;"
                + "-fx-border-color: #D4D9E8;"
                + "-fx-border-radius: 9;"
                + "-fx-background-radius: 9;"
                + "-fx-text-fill: " + BLUE + ";"
                + "-fx-font-weight: bold;"
        );

        policy.getChildren().addAll(icon, policyText, policySpace, viewPolicy);

        content.getChildren().addAll(heading, cards, table, policy);

        loadLeaveData();

        return content;
    }

    private void loadLeaveData() {
        FirebaseDAO.getInstance().getLeaveRequestsForEmployee(currentEmail).thenAccept(leaves -> {
            Platform.runLater(() -> {
                leaveTableBody.getChildren().clear();
                if (leaves == null || leaves.isEmpty()) {
                    Label empty = new Label("No leave requests found. Click '+ Apply Leave' to submit your first request.");
                    empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 14px; -fx-padding: 20px;");
                    leaveTableBody.getChildren().add(empty);
                    footerEntries.setText("Showing 0 entries");
                } else {
                    for (LeaveRequest lr : leaves) {
                        String iconStr = "Sick Leave".equals(lr.getLeaveType()) ? "\u25A3"
                                : "Earned Leave".equals(lr.getLeaveType()) ? "\u2668" : "\u2602";
                        String statusColor = "APPROVED".equalsIgnoreCase(lr.getStatus()) ? "#059669"
                                : "REJECTED".equalsIgnoreCase(lr.getStatus()) ? "#DC2626" : "#D97706";
                        String dates = lr.getStartDate();
                        if (lr.getEndDate() != null && !lr.getEndDate().equals(lr.getStartDate())) {
                            dates += " - " + lr.getEndDate();
                        }
                        leaveTableBody.getChildren().add(
                                leaveRow(iconStr, lr.getLeaveType(), dates,
                                        lr.getDurationDays() + (lr.getDurationDays() == 1 ? " Day" : " Days"),
                                        lr.getReason(),
                                        lr.getStatus() != null ? lr.getStatus().substring(0, 1).toUpperCase() + lr.getStatus().substring(1).toLowerCase() : "Pending",
                                        statusColor)
                        );
                    }
                    footerEntries.setText("Showing 1 to " + leaves.size() + " of " + leaves.size() + " entries");
                }
            });
        });
    }

    private void showApplyLeaveModal() {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNDECORATED);
        modal.setTitle("Apply for Leave");

        VBox root = new VBox(18);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: white; -fx-background-radius: 18; -fx-border-color: #CBD5E1; -fx-border-radius: 18; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 6);");
        root.setPrefWidth(480);

        Label modalTitle = new Label("\uD83D\uDCDD  Apply for Leave");
        modalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        modalTitle.setTextFill(Color.web("#0F172A"));

        // Leave Type
        Label typeLabel = new Label("Leave Type");
        typeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Casual Leave", "Sick Leave", "Earned Leave");
        typeCombo.setValue("Casual Leave");
        typeCombo.setPrefWidth(420);
        typeCombo.setPrefHeight(40);
        typeCombo.setStyle("-fx-border-color: #CBD5E1; -fx-border-radius: 8; -fx-background-radius: 8;");

        // Start Date
        Label startLabel = new Label("Start Date");
        startLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        DatePicker startPicker = new DatePicker(LocalDate.now().plusDays(1));
        startPicker.setPrefWidth(420);
        startPicker.setPrefHeight(40);

        // End Date
        Label endLabel = new Label("End Date");
        endLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        DatePicker endPicker = new DatePicker(LocalDate.now().plusDays(1));
        endPicker.setPrefWidth(420);
        endPicker.setPrefHeight(40);

        // Reason
        Label reasonLabel = new Label("Reason");
        reasonLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        TextArea reasonArea = new TextArea();
        reasonArea.setPromptText("Enter reason for leave...");
        reasonArea.setPrefHeight(80);
        reasonArea.setPrefWidth(420);
        reasonArea.setStyle("-fx-border-color: #CBD5E1; -fx-border-radius: 8; -fx-background-radius: 8;");

        // Status feedback
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: #DC2626; -fx-font-size: 12px;");

        // Buttons
        HBox btnRow = new HBox(12);
        btnRow.setAlignment(Pos.CENTER_RIGHT);

        Button cancel = new Button("Cancel");
        cancel.setPrefHeight(42);
        cancel.setStyle(
                "-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold;"
        );
        cancel.setOnAction(e -> modal.close());

        Button submit = new Button("Submit Leave Request");
        submit.setPrefHeight(42);
        submit.setStyle(
                "-fx-background-color: linear-gradient(to right, #1648C8, #7C3AED); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-font-size: 14px;"
        );

        submit.setOnAction(e -> {
            if (startPicker.getValue() == null || endPicker.getValue() == null) {
                statusLabel.setText("Please select both start and end dates.");
                return;
            }
            if (reasonArea.getText() == null || reasonArea.getText().trim().isEmpty()) {
                statusLabel.setText("Please enter a reason for leave.");
                return;
            }
            if (endPicker.getValue().isBefore(startPicker.getValue())) {
                statusLabel.setText("End date cannot be before start date.");
                return;
            }

            long days = ChronoUnit.DAYS.between(startPicker.getValue(), endPicker.getValue()) + 1;
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");

            String empName = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getFullName() : "Employee";

            LeaveRequest lr = new LeaveRequest(
                    UUID.randomUUID().toString(),
                    currentEmail,
                    empName,
                    typeCombo.getValue(),
                    startPicker.getValue().format(fmt),
                    endPicker.getValue().format(fmt),
                    (int) days,
                    reasonArea.getText().trim(),
                    "PENDING",
                    LocalDate.now().format(fmt),
                    "",
                    ""
            );

            submit.setDisable(true);
            submit.setText("Submitting...");

            FirebaseDAO.getInstance().applyForLeave(lr).thenAccept(success -> {
                Platform.runLater(() -> {
                    if (success) {
                        modal.close();
                        loadLeaveData();
                    } else {
                        statusLabel.setText("Failed to submit. Please try again.");
                        submit.setDisable(false);
                        submit.setText("Submit Leave Request");
                    }
                });
            });
        });

        btnRow.getChildren().addAll(cancel, submit);

        root.getChildren().addAll(
                modalTitle,
                typeLabel, typeCombo,
                startLabel, startPicker,
                endLabel, endPicker,
                reasonLabel, reasonArea,
                statusLabel,
                btnRow
        );

        modal.setScene(new Scene(root));
        modal.showAndWait();
    }

    private VBox balanceCard(String icon, String title, String value, String subtitle, String bg, String color) {
        VBox box = new VBox(6);
        box.setPrefWidth(245);
        box.setPadding(new Insets(18));
        box.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 16;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 16;"
        );

        Label i = new Label(icon);
        i.setPrefSize(46, 46);
        i.setAlignment(Pos.CENTER);
        i.setStyle(
                "-fx-background-color: " + bg + ";"
                + "-fx-background-radius: 13;"
                + "-fx-text-fill: " + color + ";"
                + "-fx-font-size: 20px;"
        );

        Label t = new Label(title);
        t.setTextFill(Color.web("#374151"));

        Label v = new Label(value);
        v.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        Label s = new Label(subtitle);
        s.setTextFill(Color.web("#6B7280"));

        box.getChildren().addAll(i, t, v, s);
        HBox.setHgrow(box, Priority.ALWAYS);
        return box;
    }

    private Button filter(String text, boolean active) {
        Button b = new Button(text);
        b.setPrefHeight(40);
        b.setStyle(
                active
                        ? "-fx-background-color:#EEF3FF;-fx-text-fill:#1648C8;-fx-font-weight:bold;-fx-background-radius:20;"
                        : "-fx-background-color:transparent;-fx-text-fill:#374151;"
        );
        return b;
    }

    private Label h(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        l.setTextFill(Color.web("#374151"));
        return l;
    }

    private HBox leaveRow(String icon, String type, String dates, String duration, String reason, String status, String color) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15, 25, 15, 25));

        Label i = new Label(icon);
        i.setPrefSize(42, 42);
        i.setAlignment(Pos.CENTER);
        i.setStyle("-fx-background-color:#EEF3FF;-fx-background-radius:10;-fx-text-fill:#1648C8;-fx-font-size:18px;");

        Label t = new Label(type);
        t.setPrefWidth(155);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label d = new Label(dates);
        d.setPrefWidth(230);

        Label dur = new Label(duration);
        dur.setPrefWidth(110);

        Label r = new Label(reason);
        r.setPrefWidth(170);

        String statusBg = "#ECFDF5";
        if ("REJECTED".equalsIgnoreCase(status) || "Rejected".equals(status)) {
            statusBg = "#FEF2F2"; 
        }else if ("PENDING".equalsIgnoreCase(status) || "Pending".equals(status)) {
            statusBg = "#FFFBEB";
        }

        Label s = new Label(status);
        s.setPadding(new Insets(6, 12, 6, 12));
        s.setStyle(
                "-fx-background-color:" + statusBg + ";"
                + "-fx-background-radius:20;"
                + "-fx-text-fill:" + color + ";"
                + "-fx-font-weight:bold;"
        );

        Label dots = new Label("\u22EE");
        dots.setFont(Font.font(20));

        row.getChildren().addAll(i, t, d, dur, r, s, dots);
        return row;
    }
}
