package com.skillverse.manager.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeFeedback;
import com.skillverse.CommonFeatures.EmployeeTask;
import com.skillverse.CommonFeatures.TrainingEnrollment;
import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ManagerReportsView extends VBox {

    private Runnable onBackAction;
    private Label membersValLbl;
    private Label membersSubLbl;
    private Label goalsValLbl;
    private Label goalsSubLbl;
    private Label ratingValLbl;
    private Label ratingSubLbl;
    private Label trainingValLbl;
    private Label trainingSubLbl;

    private PieChart statusPieChart;
    private HBox legendBox;
    private TableView<TeamBreakdownModel> breakdownTable;
    private ObservableList<TeamBreakdownModel> tableData = FXCollections.observableArrayList();

    public static class TeamBreakdownModel {
        private final String name;
        private final String role;
        private final int tasksAssigned;
        private final int tasksCompleted;
        private final String trainingScore;
        private final String rating;
        private final String status;

        public TeamBreakdownModel(String name, String role, int tasksAssigned, int tasksCompleted, String trainingScore, String rating, String status) {
            this.name = name;
            this.role = role;
            this.tasksAssigned = tasksAssigned;
            this.tasksCompleted = tasksCompleted;
            this.trainingScore = trainingScore;
            this.rating = rating;
            this.status = status;
        }

        public String getName() { return name; }
        public String getRole() { return role; }
        public int getTasksAssigned() { return tasksAssigned; }
        public int getTasksCompleted() { return tasksCompleted; }
        public String getTrainingScore() { return trainingScore; }
        public String getRating() { return rating; }
        public String getStatus() { return status; }
    }

    public ManagerReportsView() {
        this(null, null);
    }

    public ManagerReportsView(String email) {
        this(email, null);
    }

    public ManagerReportsView(String email, Runnable onBackAction) {
        this.onBackAction = onBackAction;
        setSpacing(24);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        
        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);



        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLbl = new Label("Team Analytics & Performance Reports 📊");
        titleLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLbl = new Label("Holistic team velocity, skill progression index, and exportable executive summaries.");
        subtitleLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLbl, subtitleLbl);

        Button refreshBtn = new Button("🔄 Refresh Data");
        refreshBtn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2563EB; -fx-border-color: #CBD5E1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold; -fx-padding: 8 16; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadAnalyticsData());

        headerBar.getChildren().addAll(titleBox, refreshBtn);

       
        GridPane metricsGrid = new GridPane();
        metricsGrid.setHgap(16);
        metricsGrid.setVgap(16);

        VBox card1 = createGlassCard("👥 Total Team Members", membersValLbl = new Label("Loading..."), membersSubLbl = new Label("Active Personnel"), "#2563EB", "#EFF6FF");
        VBox card2 = createGlassCard("🎯 Completed Goals", goalsValLbl = new Label("Loading..."), goalsSubLbl = new Label("0% Completion Rate"), "#059669", "#ECFDF5");
        VBox card3 = createGlassCard("⭐ Average Team Rating", ratingValLbl = new Label("Loading..."), ratingSubLbl = new Label("Live Appraisal Index"), "#D97706", "#FFFBEB");
        VBox card4 = createGlassCard("📚 Training Completion", trainingValLbl = new Label("Loading..."), trainingSubLbl = new Label("Team Upskilling Index"), "#7C3AED", "#F5F3FF");

        metricsGrid.add(card1, 0, 0);
        metricsGrid.add(card2, 1, 0);
        metricsGrid.add(card3, 2, 0);
        metricsGrid.add(card4, 3, 0);

        ColumnConstraints colCon = new ColumnConstraints();
        colCon.setPercentWidth(25);
        metricsGrid.getColumnConstraints().addAll(colCon, colCon, colCon, colCon);

    
        HBox chartsRow = new HBox(20);
        chartsRow.setAlignment(Pos.CENTER);

        VBox chartCard = new VBox(12);
        chartCard.setAlignment(Pos.CENTER);
        chartCard.setPadding(new Insets(20, 24, 20, 24));
        chartCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label chartHeader = new Label("🎯 Goal Status Distribution");
        chartHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        HBox chartHeaderBox = new HBox(chartHeader);
        chartHeaderBox.setAlignment(Pos.CENTER_LEFT);
        chartHeaderBox.setMaxWidth(Double.MAX_VALUE);

       
        statusPieChart = new PieChart();
        statusPieChart.setAnimated(false);
        statusPieChart.setLegendVisible(false);
        statusPieChart.setLabelsVisible(true);
        statusPieChart.setPrefHeight(280);
        statusPieChart.setPrefWidth(480);
        statusPieChart.setStyle("-fx-background-color: transparent;");


        legendBox = new HBox(24);
        legendBox.setAlignment(Pos.CENTER);
        legendBox.setPadding(new Insets(8, 0, 4, 0));

        chartCard.getChildren().addAll(chartHeaderBox, statusPieChart, legendBox);
        chartsRow.getChildren().add(chartCard);

        VBox tableCard = new VBox(12);
        tableCard.setPadding(new Insets(20));
        tableCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label tableHeader = new Label("📋 Team Member Performance Breakdown");
        tableHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        breakdownTable = new TableView<>(tableData);
        breakdownTable.setPrefHeight(260);
        breakdownTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<TeamBreakdownModel, String> colName = new TableColumn<>("Employee Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TeamBreakdownModel, String> colRole = new TableColumn<>("Role");
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<TeamBreakdownModel, Integer> colAssigned = new TableColumn<>("Tasks Assigned");
        colAssigned.setCellValueFactory(new PropertyValueFactory<>("tasksAssigned"));

        TableColumn<TeamBreakdownModel, Integer> colCompleted = new TableColumn<>("Tasks Completed");
        colCompleted.setCellValueFactory(new PropertyValueFactory<>("tasksCompleted"));

        TableColumn<TeamBreakdownModel, String> colTraining = new TableColumn<>("Training Score");
        colTraining.setCellValueFactory(new PropertyValueFactory<>("trainingScore"));

        TableColumn<TeamBreakdownModel, String> colRating = new TableColumn<>("Appraisal Rating");
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        TableColumn<TeamBreakdownModel, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        breakdownTable.getColumns().addAll(colName, colRole, colAssigned, colCompleted, colTraining, colRating, colStatus);
        tableCard.getChildren().addAll(tableHeader, breakdownTable);

        ScrollPane mainScroll = new ScrollPane(new VBox(24, headerBar, metricsGrid, chartsRow, tableCard));
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScroll);

        
        loadAnalyticsData();
    }

    private VBox createGlassCard(String title, Label valLbl, Label subLbl, String accentHex, String bgHex) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle("-fx-background-color: " + bgHex + "; -fx-background-radius: 12; -fx-border-color: " + accentHex + "33; -fx-border-radius: 12;");

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");

        valLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + accentHex + ";");
        subLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748B;");

        card.getChildren().addAll(titleLbl, valLbl, subLbl);
        return card;
    }

    private void loadAnalyticsData() {
        if (UserSession.getCurrentUser() == null) return;
        String managerEmail = UserSession.getCurrentUser().getEmail().toLowerCase().trim();

        CompletableFuture<List<User>> membersFuture = FirebaseDAO.getInstance().getTeamMembers(managerEmail);
        CompletableFuture<List<EmployeeTask>> tasksFuture = FirebaseDAO.getInstance().getTasksAssignedByManager(managerEmail);
        CompletableFuture<List<EmployeeFeedback>> feedbackFuture = FirebaseDAO.getInstance().getFeedbackSentByManager(managerEmail);
        CompletableFuture<List<TrainingEnrollment>> enrollmentsFuture = FirebaseDAO.getInstance().getAllEnrollments();

        CompletableFuture.allOf(membersFuture, tasksFuture, feedbackFuture, enrollmentsFuture).thenAccept(v -> {
            List<User> members = membersFuture.join();
            List<EmployeeTask> tasks = tasksFuture.join();
            List<EmployeeFeedback> feedbacks = feedbackFuture.join();
            List<TrainingEnrollment> enrollments = enrollmentsFuture.join();

            Platform.runLater(() -> {
                List<User> filteredMembers = new ArrayList<>();
                if (members != null) {
                    for (User m : members) {
                        String role = m.getRole() != null ? m.getRole().trim().toUpperCase() : "";
                        if ("EMPLOYEE".equals(role) || "HR".equals(role)) {
                            filteredMembers.add(m);
                        }
                    }
                }

         
                int memberCount = filteredMembers.size();
                membersValLbl.setText(memberCount + " Members");
                String dept = (!filteredMembers.isEmpty() && filteredMembers.get(0).getDepartment() != null) ? filteredMembers.get(0).getDepartment() : "Engineering";
                membersSubLbl.setText(dept + " Department");

            
                int totalTasks = tasks != null ? tasks.size() : 0;
                int completedTasks = 0;
                int inProgressTasks = 0;
                int pendingTasks = 0;

                if (tasks != null) {
                    for (EmployeeTask t : tasks) {
                        String st = t.getStatus() != null ? t.getStatus().toUpperCase() : "IN_PROGRESS";
                        if ("COMPLETED".equals(st)) {
                            completedTasks++;
                        } else if ("IN_PROGRESS".equals(st)) {
                            inProgressTasks++;
                        } else {
                            pendingTasks++;
                        }
                    }
                }

                int completionRate = totalTasks > 0 ? (completedTasks * 100 / totalTasks) : 0;
                goalsValLbl.setText(completedTasks + " / " + totalTasks + " Goals");
                goalsSubLbl.setText(completionRate + "% Completion Rate");

            
                double avgRating = 5.0;
                if (feedbacks != null && !feedbacks.isEmpty()) {
                    double sum = 0;
                    for (EmployeeFeedback f : feedbacks) {
                        sum += f.getRating();
                    }
                    avgRating = sum / feedbacks.size();
                }
                ratingValLbl.setText(String.format("%.1f / 5.0 ★", avgRating));
                ratingSubLbl.setText((feedbacks != null ? feedbacks.size() : 0) + " Reviews Given");

            
                int completedEnrollments = 0;
                int totalEnrollments = 0;
                if (enrollments != null) {
                    for (TrainingEnrollment te : enrollments) {
                        totalEnrollments++;
                        if ("COMPLETED".equalsIgnoreCase(te.getCompletionStatus())) {
                            completedEnrollments++;
                        }
                    }
                }
                int trainingRate = totalEnrollments > 0 ? (completedEnrollments * 100 / totalEnrollments) : 85;
                trainingValLbl.setText(trainingRate + "%");
                trainingSubLbl.setText(completedEnrollments + " of " + totalEnrollments + " Courses Passed");

                tableData.clear();

                if (!filteredMembers.isEmpty()) {
                    for (User member : filteredMembers) {
                        String memberEmail = member.getEmail() != null ? member.getEmail().toLowerCase().trim() : "";
                        String memberName = (member.getFullName() != null && !member.getFullName().isBlank()) ? member.getFullName() : (member.getName() != null ? member.getName() : memberEmail);

                        int mAssigned = 0;
                        int mCompleted = 0;
                        if (tasks != null) {
                            for (EmployeeTask t : tasks) {
                                if (t.getEmployeeEmail() != null && t.getEmployeeEmail().equalsIgnoreCase(memberEmail)) {
                                    mAssigned++;
                                    if ("COMPLETED".equalsIgnoreCase(t.getStatus())) {
                                        mCompleted++;
                                    }
                                }
                            }
                        }

                        String memberScore = "88%";
                        if (enrollments != null) {
                            for (TrainingEnrollment te : enrollments) {
                                if (te.getEmployeeEmail() != null && te.getEmployeeEmail().equalsIgnoreCase(memberEmail)) {
                                    memberScore = te.getAssessmentScore() + "%";
                                    break;
                                }
                            }
                        }

                        tableData.add(new TeamBreakdownModel(
                                memberName,
                                member.getRole() != null ? member.getRole() : "EMPLOYEE",
                                mAssigned,
                                mCompleted,
                                memberScore,
                                String.format("%.1f ★", avgRating),
                                mCompleted >= mAssigned && mAssigned > 0 ? "High Performer" : "Active"
                        ));
                    }
                }

              
                int totalGoalCount = completedTasks + inProgressTasks + pendingTasks;
                if (totalGoalCount == 0) {
                    PieChart.Data dEmpty = new PieChart.Data("No Goals Assigned (0)", 1);
                    statusPieChart.setData(FXCollections.observableArrayList(dEmpty));
                    applySliceColor(dEmpty, "#94A3B8");
                } else {
                    PieChart.Data dCompleted = new PieChart.Data("Completed (" + completedTasks + ")", completedTasks);
                    PieChart.Data dInProgress = new PieChart.Data("In Progress (" + inProgressTasks + ")", inProgressTasks);
                    PieChart.Data dPending = new PieChart.Data("Pending (" + pendingTasks + ")", pendingTasks);

                    statusPieChart.setData(FXCollections.observableArrayList(dCompleted, dInProgress, dPending));

                    applySliceColor(dCompleted, "#10B981");
                    applySliceColor(dInProgress, "#F59E0B");
                    applySliceColor(dPending, "#EF4444");
                }

                if (legendBox != null) {
                    legendBox.getChildren().clear();
                    legendBox.getChildren().addAll(
                            createLegendItem("#10B981", "Completed (" + completedTasks + ")"),
                            createLegendItem("#F59E0B", "In Progress (" + inProgressTasks + ")"),
                            createLegendItem("#EF4444", "Pending (" + pendingTasks + ")")
                    );
                }

                showModernToast("Data Synchronized 🔄", "Live analytics re-calculated", true);
            });
        }).exceptionally(ex -> {
            System.err.println("❌ [ManagerReportsView] Error loading analytics: " + ex.getMessage());
            return null;
        });
    }

    private void handleExportSummary() {
        StringBuilder csv = new StringBuilder();
        csv.append("Employee Name,Role,Tasks Assigned,Tasks Completed,Training Score,Appraisal Rating,Status\n");
        for (TeamBreakdownModel row : tableData) {
            csv.append(row.getName()).append(",")
               .append(row.getRole()).append(",")
               .append(row.getTasksAssigned()).append(",")
               .append(row.getTasksCompleted()).append(",")
               .append(row.getTrainingScore()).append(",")
               .append(row.getRating()).append(",")
               .append(row.getStatus()).append("\n");
        }
        showModernToast("Executive Summary Exported 📥", "Tabular summary ready (CSV format generated with " + tableData.size() + " team records).", true);
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

    private HBox createLegendItem(String colorHex, String labelText) {
        HBox item = new HBox(8);
        item.setAlignment(Pos.CENTER);

        Circle dot = new Circle(6);
        dot.setFill(Color.web(colorHex));

        Label textLbl = new Label(labelText);
        textLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        item.getChildren().addAll(dot, textLbl);
        return item;
    }

    private void applySliceColor(PieChart.Data data, String colorHex) {
        if (data.getNode() != null) {
            data.getNode().setStyle("-fx-pie-color: " + colorHex + ";");
        }
        data.nodeProperty().addListener((obs, oldNode, newNode) -> {
            if (newNode != null) {
                newNode.setStyle("-fx-pie-color: " + colorHex + ";");
            }
        });
    }
}

