package com.skillverse.manager.view;

import com.skillverse.Config.FirebaseConfig;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeTask;
import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerAssignTasksView extends VBox {

    private final ComboBox<AssigneeItem> assigneeComboBox;
    private final TextField titleField;
    private final TextField metricField;
    private final ComboBox<String> priorityComboBox;
    private final DatePicker dueDatePicker;
    private final VBox activeTasksContainer;
    private final Label activeCountBadge;

    public static class AssigneeItem {
        private final String email;
        private final String displayName;

        public AssigneeItem(String email, String displayName) {
            this.email = email;
            this.displayName = displayName;
        }

        public String getEmail() { return email; }
        public String getDisplayName() { return displayName; }

        @Override
        public String toString() { return displayName; }
    }

    public ManagerAssignTasksView(String email) {
        this();
    }

    public ManagerAssignTasksView(String email, Runnable onBackToDashboard) {
        this();
    }

    public ManagerAssignTasksView() {
        setSpacing(24);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(12);
        headerBar.setAlignment(Pos.CENTER_LEFT);



        VBox titleBox = new VBox(4);
        Label titleLabel = new Label("Assign Goals & Daily Tasks 🎯");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Assign performance goals and operational tasks to team employees with live tracking.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        headerBar.getChildren().addAll(titleBox);

        VBox createCard = new VBox(16);
        createCard.setPadding(new Insets(20));
        createCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label formHeader = new Label("Create & Assign New Goal / Task");
        formHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        HBox row1 = new HBox(16);
        row1.setAlignment(Pos.CENTER_LEFT);

        VBox assigneeBox = new VBox(6);
        Label assigneeLbl = new Label("Assignee:");
        assigneeLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569;");
        assigneeComboBox = new ComboBox<>();
        assigneeComboBox.setPrefWidth(240);
        assigneeComboBox.setPromptText("Select Team Member");
        assigneeBox.getChildren().addAll(assigneeLbl, assigneeComboBox);

        VBox titleInputBox = new VBox(6);
        HBox.setHgrow(titleInputBox, Priority.ALWAYS);
        Label titleInputLbl = new Label("Goal / Task Title:");
        titleInputLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569;");
        titleField = new TextField();
        titleField.setPromptText("e.g. Implement OAuth2 Security Layer");
        titleInputBox.getChildren().addAll(titleInputLbl, titleField);

        VBox priorityBox = new VBox(6);
        Label priorityLbl = new Label("Priority:");
        priorityLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569;");
        priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("HIGH", "MEDIUM", "LOW");
        priorityComboBox.setValue("HIGH");
        priorityComboBox.setPrefWidth(120);
        priorityBox.getChildren().addAll(priorityLbl, priorityComboBox);

        row1.getChildren().addAll(assigneeBox, titleInputBox, priorityBox);


        HBox row2 = new HBox(16);
        row2.setAlignment(Pos.BOTTOM_LEFT);

        VBox dateBox = new VBox(6);
        Label dateLbl = new Label("Target Due Date:");
        dateLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569;");
        dueDatePicker = new DatePicker(LocalDate.now().plusDays(7));
        dueDatePicker.setPrefWidth(180);
        dateBox.getChildren().addAll(dateLbl, dueDatePicker);

        VBox metricBox = new VBox(6);
        HBox.setHgrow(metricBox, Priority.ALWAYS);
        Label metricLbl = new Label("Key Target Metric / KPI:");
        metricLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569;");
        metricField = new TextField();
        metricField.setPromptText("e.g. 100% Code Coverage & Zero critical flaws");
        metricBox.getChildren().addAll(metricLbl, metricField);

        Button assignBtn = new Button("Assign Goal / Task →");
        assignBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        assignBtn.setOnAction(e -> handleAssignTask());

        row2.getChildren().addAll(dateBox, metricBox, assignBtn);
        createCard.getChildren().addAll(formHeader, row1, row2);

        VBox boardCard = new VBox(14);
        boardCard.setPadding(new Insets(20));
        boardCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        HBox boardHeader = new HBox(12);
        boardHeader.setAlignment(Pos.CENTER_LEFT);

        Label boardTitle = new Label("Active Team & HR Assigned Goals");
        boardTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        HBox.setHgrow(boardTitle, Priority.ALWAYS);

        activeCountBadge = new Label("Loading...");
        activeCountBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1D4ED8; -fx-padding: 4 10; -fx-background-radius: 12; -fx-font-weight: bold;");

        Button refreshBtn = new Button("🔄 Refresh Board");
        refreshBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadTasksFromFirestore());

        boardHeader.getChildren().addAll(boardTitle, activeCountBadge, refreshBtn);

        activeTasksContainer = new VBox(10);
        boardCard.getChildren().addAll(boardHeader, activeTasksContainer);

        ScrollPane scrollPane = new ScrollPane(new VBox(16, headerBar, createCard, boardCard));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(scrollPane);

        loadTeamMembers();
        loadTasksFromFirestore();
    }

    private void loadTeamMembers() {
        if (UserSession.getCurrentUser() == null) return;
        String managerEmail = UserSession.getCurrentUser().getEmail().toLowerCase().trim();

        FirebaseDAO.getInstance().getAllEmployees().thenAccept(users -> {
            Platform.runLater(() -> {
                assigneeComboBox.getItems().clear();
                assigneeComboBox.getItems().add(new AssigneeItem("ALL", "All Team Members"));
                for (User u : users) {
                    if (u.getEmail() != null && !u.getEmail().equalsIgnoreCase(managerEmail)) {
                        assigneeComboBox.getItems().add(new AssigneeItem(u.getEmail(), u.getName() + " (" + (u.getRole() != null ? u.getRole() : "Member") + ")"));
                    }
                }
                if (!assigneeComboBox.getItems().isEmpty()) {
                    assigneeComboBox.getSelectionModel().select(0);
                }
            });
        });
    }

    private void handleAssignTask() {
        String title = titleField.getText().trim();
        String metric = metricField.getText().trim();
        AssigneeItem selectedAssignee = assigneeComboBox.getValue();

        if (title.isEmpty() || selectedAssignee == null) {
            showAlert("Validation Error", "Please provide a task title and select an assignee.");
            return;
        }

        EmployeeTask newTask = new EmployeeTask();
        newTask.setTitle(title);
        newTask.setMetric(metric.isEmpty() ? "Standard QA Met" : metric);
        newTask.setPriority(priorityComboBox.getValue());
        newTask.setDueDate(dueDatePicker.getValue() != null ? dueDatePicker.getValue().toString() : LocalDate.now().plusDays(7).toString());
        newTask.setStatus("IN_PROGRESS");
        newTask.setEmployeeEmail(selectedAssignee.getEmail().toLowerCase().trim());
        newTask.setManagerEmail(UserSession.getCurrentUser().getEmail().toLowerCase().trim());
        newTask.setCreatedAt(new Date());

        FirebaseDAO.getInstance().createEmployeeTask(newTask).thenRun(() -> {
            Platform.runLater(() -> {
                titleField.clear();
                metricField.clear();
                loadTasksFromFirestore();
            });
        });
    }

    private void loadTasksFromFirestore() {
        if (UserSession.getCurrentUser() == null) return;
        String managerEmail = UserSession.getCurrentUser().getEmail().toLowerCase().trim();

        FirebaseDAO.getInstance().getTasksAssignedByManager(managerEmail).thenAccept(tasks -> {
            Platform.runLater(() -> {
                activeTasksContainer.getChildren().clear();
                if (tasks == null || tasks.isEmpty()) {
                    activeCountBadge.setText("0 Goals Active");
                    Label emptyLbl = new Label("No assigned goals found. Use the form above to assign goals to your team.");
                    emptyLbl.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-padding: 20;");
                    activeTasksContainer.getChildren().add(emptyLbl);
                } else {
                    activeCountBadge.setText(tasks.size() + " Goals Active");
                    for (EmployeeTask task : tasks) {
                        activeTasksContainer.getChildren().add(createTaskRow(task));
                    }
                }
            });
        });
    }

    private HBox createTaskRow(EmployeeTask task) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8; -fx-border-color: #E2E8F0; -fx-border-radius: 8;");

        Label iconLbl = new Label("👤");
        iconLbl.setStyle("-fx-font-size: 16px;");

        VBox textContainer = new VBox(4);
        HBox.setHgrow(textContainer, Priority.ALWAYS);

        Label titleLabel = new Label(task.getTitle());
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        String meta = "Assigned to: " + task.getEmployeeEmail() + " • Due: " + task.getDueDate() + " • Target: " + task.getMetric();
        Label metaLabel = new Label(meta);
        metaLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");

        textContainer.getChildren().addAll(titleLabel, metaLabel);

        Label priorityBadge = new Label(task.getPriority() != null ? task.getPriority() : "HIGH");
        priorityBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #991B1B; -fx-padding: 3 8; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold;");

        boolean isCompleted = "COMPLETED".equalsIgnoreCase(task.getStatus());
        Label statusBadge = new Label(isCompleted ? "Completed" : "In Progress");
        statusBadge.setStyle(isCompleted
                ? "-fx-background-color: #DCFCE7; -fx-text-fill: #166534; -fx-padding: 3 8; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold;"
                : "-fx-background-color: #DBEAFE; -fx-text-fill: #1E40AF; -fx-padding: 3 8; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold;");

        row.getChildren().addAll(iconLbl, textContainer, priorityBadge, statusBadge);
        return row;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
