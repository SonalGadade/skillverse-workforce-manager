package com.skillverse.employee.view;

import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeTask;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class EmployeeTasksView extends VBox {

    private final VBox taskListContainer;
    private String userEmail;

    public EmployeeTasksView() {
        this("");
    }

    public EmployeeTasksView(String email) {
        this.userEmail = email;

        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        // Header Section
        VBox headerBox = new VBox(6);
        Label titleLabel = new Label("My Goals & Tasks Checklist");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        Label subtitleLabel = new Label("Interactive task list, manager deadlines, and personal growth goals.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        headerBox.getChildren().addAll(titleLabel, subtitleLabel);

        // Task Cards Container
        taskListContainer = new VBox(12);
        taskListContainer.setPadding(new Insets(16));
        taskListContainer.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        // Loading State Placeholder
        Label loadingLabel = new Label("Loading assigned tasks from database...");
        loadingLabel.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 14px;");
        taskListContainer.getChildren().add(loadingLabel);

        ScrollPane scrollPane = new ScrollPane(taskListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().addAll(headerBox, scrollPane);

        // Fetch Dynamic Data
        loadTasksFromFirestore();
    }

    public void show(Stage stage, String email) {
        if (email != null && !email.isBlank()) this.userEmail = email;
        Scene scene = new Scene(this, 1500, 900);
        stage.setTitle("SkillVerse | My Goals & Tasks");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public VBox createTasksContent(String email) {
        if (email != null && !email.isBlank()) {
            this.userEmail = email;
            loadTasksFromFirestore();
        }
        return this;
    }

    private void loadTasksFromFirestore() {
        String emailToQuery = (UserSession.getCurrentUser() != null && UserSession.getCurrentUser().getEmail() != null && !UserSession.getCurrentUser().getEmail().isBlank())
                ? UserSession.getCurrentUser().getEmail().toLowerCase().trim()
                : (userEmail != null ? userEmail.toLowerCase().trim() : "");

        if (emailToQuery.isBlank()) {
            renderEmptyState("User session expired. Please log in again.");
            return;
        }

        FirebaseDAO.getInstance().getTasksForEmployee(emailToQuery).thenAccept(tasks -> {
            Platform.runLater(() -> {
                taskListContainer.getChildren().clear();
                if (tasks == null || tasks.isEmpty()) {
                    renderEmptyState("No assigned goals or tasks found. Your manager will assign tasks here.");
                } else {
                    for (EmployeeTask task : tasks) {
                        taskListContainer.getChildren().add(createDynamicTaskRow(task));
                    }
                }
            });
        }).exceptionally(ex -> {
            Platform.runLater(() -> renderEmptyState("Failed to connect to database. Please check your internet."));
            return null;
        });
    }

    private void renderEmptyState(String message) {
        taskListContainer.getChildren().clear();
        VBox emptyBox = new VBox(10);
        emptyBox.setAlignment(Pos.CENTER);
        emptyBox.setPadding(new Insets(40));

        Label emptyIcon = new Label("📋");
        emptyIcon.setStyle("-fx-font-size: 32px;");

        Label emptyLabel = new Label(message);
        emptyLabel.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 14px; -fx-font-style: italic;");

        emptyBox.getChildren().addAll(emptyIcon, emptyLabel);
        taskListContainer.getChildren().add(emptyBox);
    }

    private HBox createDynamicTaskRow(EmployeeTask task) {
        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(14, 18, 14, 18));
        row.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8; -fx-border-color: #E2E8F0; -fx-border-radius: 8;");

        CheckBox checkBox = new CheckBox();
        boolean isCompleted = "COMPLETED".equalsIgnoreCase(task.getStatus());
        checkBox.setSelected(isCompleted);

        VBox textContainer = new VBox(4);
        HBox.setHgrow(textContainer, Priority.ALWAYS);

        Label titleLabel = new Label(task.getTitle() != null ? task.getTitle() : "Untitled Task");
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + (isCompleted ? "#94A3B8;" : "#1E293B;") + (isCompleted ? " -fx-strikethrough: true;" : ""));

        String meta = "Deadline: " + (task.getDueDate() != null ? task.getDueDate() : "No deadline")
                + " • Priority: " + (task.getPriority() != null ? task.getPriority() : "NORMAL");
        Label metaLabel = new Label(meta);
        metaLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");

        textContainer.getChildren().addAll(titleLabel, metaLabel);

        Label statusBadge = new Label(isCompleted ? "COMPLETED" : "IN PROGRESS");
        updateBadgeStyle(statusBadge, isCompleted);

        checkBox.setOnAction(e -> {
            boolean checked = checkBox.isSelected();
            String newStatus = checked ? "COMPLETED" : "IN_PROGRESS";

            task.setStatus(newStatus);

            // Instant UI Update
            updateBadgeStyle(statusBadge, checked);
            titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + (checked ? "#94A3B8;" : "#1E293B;") + (checked ? " -fx-strikethrough: true;" : ""));

            // Async Firestore Update
            if (task.getTaskId() != null && !task.getTaskId().isEmpty()) {
                FirebaseDAO.getInstance().updateTaskStatus(task.getTaskId(), newStatus);
            }
        });

        row.getChildren().addAll(checkBox, textContainer, statusBadge);
        return row;
    }

    private void updateBadgeStyle(Label badge, boolean isCompleted) {
        if (isCompleted) {
            badge.setText("COMPLETED");
            badge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #166534; -fx-padding: 4 10; -fx-background-radius: 12; -fx-font-weight: bold; -fx-font-size: 11px;");
        } else {
            badge.setText("IN PROGRESS");
            badge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #92400E; -fx-padding: 4 10; -fx-background-radius: 12; -fx-font-weight: bold; -fx-font-size: 11px;");
        }
    }
}
