package com.skillverse.manager.view;

import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.CommonFeatures.LeaveRequest;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeTask;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class ManagerDashboardView extends VBox {

    private static final String BLUE = "#2563EB";
    private static final String PURPLE = "#7C3AED";
    private static final String GREEN = "#10B981";
    private static final String AMBER = "#D97706";
    private static final String TEXT = "#0F172A";
    private static final String MUTED = "#64748B";
    private static final String BG = "#F8FAFC";
    private static final String BORDER = "#E2E8F0";

    private String managerEmail;
    private String managerName;
    private String managerDept;

    public ManagerDashboardView() {
        this(null);
    }

    public ManagerDashboardView(String email) {
        User currentUser = UserSession.getCurrentUser();
        this.managerEmail = (currentUser != null && currentUser.getEmail() != null && !currentUser.getEmail().isBlank())
                ? currentUser.getEmail().toLowerCase().trim()
                : (email != null ? email.toLowerCase().trim() : "manager@skillverse.com");

        this.managerName = (currentUser != null && currentUser.getFullName() != null && !currentUser.getFullName().isBlank())
                ? currentUser.getFullName()
                : "Manager";

        this.managerDept = (currentUser != null && currentUser.getDepartment() != null && !currentUser.getDepartment().isBlank())
                ? currentUser.getDepartment()
                : "Engineering";

        setSpacing(24);
        setPadding(new Insets(32, 40, 40, 40));
        setStyle("-fx-background-color: " + BG + ";");

    
        HBox heroBanner = createHeroBanner();

        Label teamSizeVal = new Label("0");
        Label perfVal = new Label("0%");
        //Label goalsVal = new Label("0 / 0 Tasks");
        Label coursesVal = new Label("0 Enrolled");

        HBox metricsRow = new HBox(16);
        metricsRow.getChildren().addAll(
                createStatCard("👥 Team Size", teamSizeVal, "Active Team Members", "#77a6e4", BLUE),
                createStatCard("📈 Avg Team Performance", perfVal, "+4.2% positive trend", "#66ec8e", GREEN),
                //createStatCard("🎯 Goals & Tasks Completed", goalsVal, "Team Completion Rate", "#f7d656", AMBER),
                createStatCard("📚 Team Upskilling", coursesVal, "Active Enrollments", "#b574fa", PURPLE)
        );
        for (javafx.scene.Node n : metricsRow.getChildren()) HBox.setHgrow(n, Priority.ALWAYS);

        /*HBox chartsRow = new HBox(20);
        LineChart<String, Number> lineChart = createPerformanceTrendChart();
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Task Distribution");
        pieChart.setStyle("-fx-font-weight: bold;");

        VBox chart1Box = createChartCard("📈 Team Performance Weekly Progress", lineChart);
        VBox chart2Box = createChartCard("📊 Task Status Distribution", pieChart);
        HBox.setHgrow(chart1Box, Priority.ALWAYS);
        HBox.setHgrow(chart2Box, Priority.ALWAYS);
        chartsRow.getChildren().addAll(chart1Box, chart2Box);*/
        VBox teamTableCard = new VBox(12);
        teamTableCard.setPadding(new Insets(20));
        teamTableCard.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");
        Text teamTableTitle = new Text("👥 Team Overview & Performance");
        teamTableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        teamTableTitle.setFill(Color.web(TEXT));
        VBox teamMembersContainer = new VBox(10);
        teamTableCard.getChildren().addAll(teamTableTitle, teamMembersContainer);

        VBox leaveApprovalCard = new VBox(12);
        leaveApprovalCard.setPadding(new Insets(20));
        leaveApprovalCard.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");
        Text leaveApprovalTitle = new Text("📅 Pending Leave Applications for Approval");
        leaveApprovalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        leaveApprovalTitle.setFill(Color.web(TEXT));
        VBox leaveListContainer = new VBox(10);
        leaveApprovalCard.getChildren().addAll(leaveApprovalTitle, leaveListContainer);

        ScrollPane scrollPane = new ScrollPane(new VBox(24, heroBanner, metricsRow, /*chartsRow,*/ leaveApprovalCard, teamTableCard));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");

        getChildren().add(scrollPane);

    
        loadDashboardData(teamSizeVal, perfVal, /*goalsVal,*/ coursesVal, /*pieChart,*/ teamMembersContainer, leaveListContainer);
    }

    private HBox createHeroBanner() {
        HBox banner = new HBox();
        banner.setPadding(new Insets(24, 30, 24, 30));
        banner.setStyle(
                "-fx-background-color: linear-gradient(to right, #1E60FF, #3B82F6);" +
                "-fx-background-radius: 16px;" +
                "-fx-effect: dropshadow(gaussian, rgba(30,96,255,0.2), 12, 0, 0, 3);"
        );

        VBox left = new VBox(6);
        Text title = new Text("Welcome back, " + managerName + " 👋");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.WHITE);

        Text sub = new Text("Tracking team progress, goal alignment, and skill growth across " + managerDept + " projects.");
        sub.setFont(Font.font("Arial", 14));
        sub.setFill(Color.web("#E0E7FF"));

        left.getChildren().addAll(title, sub);
        banner.getChildren().add(left);
        return banner;
    }

    private VBox createStatCard(String title, Label valLabel, String subtext, String bgColor, String textColor) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");

        Label t = new Label(title);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        t.setTextFill(Color.web(textColor));

        valLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        valLabel.setTextFill(Color.web(TEXT));

        Label sub = new Label(subtext);
        sub.setFont(Font.font("Arial", 11.5));
        sub.setTextFill(Color.web(MUTED));

        card.getChildren().addAll(t, valLabel, sub);
        return card;
    }

    private VBox createChartCard(String title, javafx.scene.Node chart) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");

        Text t = new Text(title);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        t.setFill(Color.web(TEXT));

        box.getChildren().addAll(t, chart);
        return box;
    }

    /*private LineChart<String, Number> createPerformanceTrendChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Weeks");

        NumberAxis yAxis = new NumberAxis(50, 100, 10);
        yAxis.setLabel("Performance %");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setLegendVisible(false);
        lineChart.setPrefHeight(220);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("W1", 72));
        series.getData().add(new XYChart.Data<>("W2", 78));
        series.getData().add(new XYChart.Data<>("W3", 83));
        series.getData().add(new XYChart.Data<>("W4", 88));

        lineChart.getData().add(series);
        return lineChart;
    }*/

    //private void loadDashboardData(Label teamSizeVal, Label perfVal, Label goalsVal, Label coursesVal, /*PieChart pieChart,*/ VBox teamContainer, VBox leaveListContainer) {
    private void loadDashboardData(Label teamSizeVal, Label perfVal, Label coursesVal, VBox teamContainer, VBox leaveListContainer) {
        FirebaseDAO.getInstance().getEmployeesForManager(managerEmail).thenAccept(teamMembers -> {
            Platform.runLater(() -> {
                teamContainer.getChildren().clear();
                int teamCount = 0;
                if (teamMembers != null) {
                    for (User member : teamMembers) {
                        String role = member.getRole() != null ? member.getRole().trim().toUpperCase() : "";
                        if ("EMPLOYEE".equals(role) || "HR".equals(role)) {
                            teamContainer.getChildren().add(createTeamMemberRow(member));
                            teamCount++;
                        }
                    }
                }
                teamSizeVal.setText(String.valueOf(teamCount));

                if (teamCount == 0) {
                    teamContainer.getChildren().add(new Label("No team members (HR or Employee) assigned yet."));
                }
            });
        });

        FirebaseDAO.getInstance().getAllTrainingPrograms().thenAccept(programs -> {
            Platform.runLater(() -> {
                int count = programs != null ? programs.size() : 0;
                coursesVal.setText(count + " Courses");
            });
        });

        // Load tasks and calculate live stats
       /*  FirebaseDAO.getInstance().getTasksForEmployee(managerEmail).thenAccept(tasks -> {
            Platform.runLater(() -> {
                int total = tasks != null ? tasks.size() : 0;
                int pending = 0;
                int inProgress = 0;
                int completed = 0;

                if (tasks != null) {
                    for (EmployeeTask t : tasks) {
                        String status = t.getStatus() != null ? t.getStatus().toUpperCase() : "PENDING";
                        if ("COMPLETED".equals(status)) completed++;
                        else if ("IN_PROGRESS".equals(status)) inProgress++;
                        else pending++;
                    }
                }

                goalsVal.setText(completed + " / " + total + " Tasks");
                double perfPct = total > 0 ? ((double) completed / total) * 100.0 : 85.0;
                perfVal.setText(String.format("%.0f%%", perfPct));

                pieChart.getData().clear();
                pieChart.getData().add(new PieChart.Data("Pending (" + pending + ")", pending > 0 ? pending : 1));
                pieChart.getData().add(new PieChart.Data("In Progress (" + inProgress + ")", inProgress > 0 ? inProgress : 2));
                pieChart.getData().add(new PieChart.Data("Completed (" + completed + ")", completed > 0 ? completed : 3));
            });
        });*/

        // Load tasks and calculate live performance
FirebaseDAO.getInstance().getTasksForEmployee(managerEmail).thenAccept(tasks -> {
    Platform.runLater(() -> {
        int total = tasks != null ? tasks.size() : 0;
        int completed = 0;

        if (tasks != null) {
            for (EmployeeTask t : tasks) {
                String status = t.getStatus() != null ? t.getStatus().toUpperCase() : "PENDING";
                if ("COMPLETED".equals(status)) completed++;
            }
        }

        double perfPct = total > 0 ? ((double) completed / total) * 100.0 : 85.0;
        perfVal.setText(String.format("%.0f%%", perfPct));
    });
});

       
        loadPendingLeaves(leaveListContainer);
    }

    private void loadPendingLeaves(VBox leaveListContainer) {
        FirebaseDAO.getInstance().getPendingLeaveRequestsForManager(managerEmail).thenAccept(leaves -> {
            Platform.runLater(() -> {
                leaveListContainer.getChildren().clear();
                if (leaves == null || leaves.isEmpty()) {
                    Label empty = new Label("No pending leave requests at this time.");
                    empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px;");
                    leaveListContainer.getChildren().add(empty);
                } else {
                    for (LeaveRequest lr : leaves) {
                        leaveListContainer.getChildren().add(createLeaveApprovalRow(lr, leaveListContainer));
                    }
                }
            });
        });
    }

    private HBox createLeaveApprovalRow(LeaveRequest lr, VBox leaveListContainer) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.setStyle("-fx-background-color: #FFFBEB; -fx-border-color: #FDE68A; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        VBox info = new VBox(3);
        Text nameText = new Text(lr.getEmployeeName() != null ? lr.getEmployeeName() : lr.getEmployeeEmail());
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameText.setFill(Color.web(TEXT));

        Text detailText = new Text(lr.getLeaveType() + " \u2022 " + lr.getStartDate()
                + (lr.getEndDate() != null && !lr.getEndDate().equals(lr.getStartDate()) ? " \u2013 " + lr.getEndDate() : "")
                + " (" + lr.getDurationDays() + (lr.getDurationDays() == 1 ? " day" : " days") + ")");
        detailText.setFont(Font.font("Arial", 12));
        detailText.setFill(Color.web(MUTED));

        Text reasonText = new Text("Reason: " + (lr.getReason() != null ? lr.getReason() : "N/A"));
        reasonText.setFont(Font.font("Arial", 12));
        reasonText.setFill(Color.web("#6B7280"));

        info.getChildren().addAll(nameText, detailText, reasonText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button approveBtn = new Button("\u2713 Approve");
        approveBtn.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 8px; -fx-cursor: hand;");
        approveBtn.setOnAction(e -> {
            approveBtn.setDisable(true);
            FirebaseDAO.getInstance().updateLeaveStatus(lr.getLeaveId(), "APPROVED", "Approved by manager").thenAccept(ok -> {
                Platform.runLater(() -> loadPendingLeaves(leaveListContainer));
            });
        });

        Button rejectBtn = new Button("\u2717 Reject");
        rejectBtn.setStyle("-fx-background-color: #DC2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 8px; -fx-cursor: hand;");
        rejectBtn.setOnAction(e -> {
            rejectBtn.setDisable(true);
            FirebaseDAO.getInstance().updateLeaveStatus(lr.getLeaveId(), "REJECTED", "Rejected by manager").thenAccept(ok -> {
                Platform.runLater(() -> loadPendingLeaves(leaveListContainer));
            });
        });

        HBox btnBox = new HBox(8, approveBtn, rejectBtn);
        btnBox.setAlignment(Pos.CENTER_RIGHT);

        row.getChildren().addAll(info, spacer, btnBox);
        return row;
    }

    private HBox createTeamMemberRow(User u) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 14, 10, 14));
        row.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Circle avatar = new Circle(16, Color.web("#2563EB"));
        Text init = new Text(u.getFullName() != null && !u.getFullName().isBlank() ? u.getFullName().substring(0, 1).toUpperCase() : "E");
        init.setFill(Color.WHITE);
        init.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        StackPane avPane = new StackPane(avatar, init);

        VBox inf = new VBox(2);
        Text name = new Text(u.getFullName() != null ? u.getFullName() : u.getEmail());
        name.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        Text role = new Text(u.getRole() != null ? u.getRole() : "Team Member");
        role.setFont(Font.font("Arial", 11));
        role.setFill(Color.web(MUTED));
        inf.getChildren().addAll(name, role);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label perfBadge = new Label("88% Performance");
        perfBadge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-weight: bold; -fx-padding: 3px 10px; -fx-background-radius: 10px; -fx-font-size: 11px;");

        row.getChildren().addAll(avPane, inf, sp, perfBadge);
        return row;
    }
}
