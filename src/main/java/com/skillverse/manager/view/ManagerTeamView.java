package com.skillverse.manager.view;

import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeTask;
import com.skillverse.CommonFeatures.TrainingEnrollment;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class ManagerTeamView extends VBox {

    private static final String BLUE = "#2563EB";
    private static final String GREEN = "#10B981";
    private static final String AMBER = "#D97706";
    private static final String RED = "#DC2626";
    private static final String TEXT = "#0F172A";
    private static final String MUTED = "#64748B";
    private static final String BG = "#F8FAFC";
    private static final String BORDER = "#E2E8F0";

    private String managerEmail;
    private ScrollPane mainScrollPane;

    public ManagerTeamView() {
        this(null, null);
    }

    public ManagerTeamView(String email, Consumer<String> onNavigateToAssignTask) {
        User currentUser = UserSession.getCurrentUser();
        this.managerEmail = (currentUser != null && currentUser.getEmail() != null && !currentUser.getEmail().isBlank())
                ? currentUser.getEmail().toLowerCase().trim()
                : (email != null ? email.toLowerCase().trim() : "manager@skillverse.com");

        setSpacing(24);
        setPadding(new Insets(32, 40, 40, 40));
        setStyle("-fx-background-color: " + BG + ";");

     
        VBox headerBox = new VBox(6);
        Text title = new Text("My Team & Performance Matrix");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setFill(Color.web(TEXT));

        Text subtitle = new Text("Monitor team capabilities, task execution velocity, and continuous learning.");
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setFill(Color.web(MUTED));

        headerBox.getChildren().addAll(title, subtitle);

   
        Label totalTeamVal = new Label("0");
        Label avgVelocityVal = new Label("0%");
        Label highPerfVal = new Label("0");

        HBox summaryRow = new HBox(16);
        summaryRow.getChildren().addAll(
                createStatBadge("👥 Total Team Members", totalTeamVal, "#5a8ed3", BLUE),
                createStatBadge("⚡ Average Velocity", avgVelocityVal, "#d9be4f", AMBER),
                createStatBadge("🌟 High Performers (>85%)", highPerfVal, "#5fd789", GREEN)
        );
        for (javafx.scene.Node n : summaryRow.getChildren()) HBox.setHgrow(n, Priority.ALWAYS);

     
        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);

        mainScrollPane = new ScrollPane(new VBox(24, headerBox, summaryRow, cardsGrid));
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScrollPane);

      
        loadTeamMatrixData(totalTeamVal, avgVelocityVal, highPerfVal, cardsGrid);
    }

    private VBox createStatBadge(String title, Label valLabel, String bgColor, String textColor) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");

        Label t = new Label(title);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        t.setTextFill(Color.web(textColor));

        valLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        valLabel.setTextFill(Color.web(TEXT));

        card.getChildren().addAll(t, valLabel);
        return card;
    }

    private void loadTeamMatrixData(Label totalTeamVal, Label avgVelocityVal, Label highPerfVal, GridPane grid) {
        grid.getChildren().clear();
        grid.add(new Label("🔄 Syncing team matrix from Firestore..."), 0, 0);

        FirebaseDAO.getInstance().getEmployeesForManager(managerEmail).thenAccept(members -> {
            Platform.runLater(() -> {
                grid.getChildren().clear();
                java.util.List<User> filteredMembers = new java.util.ArrayList<>();
                if (members != null) {
                    for (User m : members) {
                        String role = m.getRole() != null ? m.getRole().trim().toUpperCase() : "";
                        if ("EMPLOYEE".equals(role) || "HR".equals(role)) {
                            filteredMembers.add(m);
                        }
                    }
                }

                if (filteredMembers.isEmpty()) {
                    totalTeamVal.setText("0");
                    avgVelocityVal.setText("0%");
                    highPerfVal.setText("0");

                    VBox emptyBox = new VBox(10);
                    emptyBox.setAlignment(Pos.CENTER);
                    emptyBox.setPadding(new Insets(40));
                    Label emptyLbl = new Label("No team members (HR or Employee) assigned to your supervision yet.");
                    emptyLbl.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 14px; -fx-font-style: italic;");
                    emptyBox.getChildren().add(emptyLbl);
                    grid.add(emptyBox, 0, 0);
                    return;
                }

                totalTeamVal.setText(String.valueOf(filteredMembers.size()));

                final int[] highPerfCount = {0};
                final double[] totalPerfSum = {0.0};
                final int[] loadedCount = {0};

                ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(50);
                ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(50);
                grid.getColumnConstraints().setAll(col1, col2);

                int row = 0;
                int col = 0;

                for (User member : filteredMembers) {
                    final int r = row;
                    final int c = col;

                    String memEmail = member.getEmail() != null ? member.getEmail().toLowerCase().trim() : "";

                  
                    FirebaseDAO.getInstance().getTasksForEmployee(memEmail).thenAccept(tasks -> {
                        int totalTasks = tasks != null ? tasks.size() : 0;
                        int compTasks = 0;
                        if (tasks != null) {
                            for (EmployeeTask t : tasks) {
                                if ("COMPLETED".equalsIgnoreCase(t.getStatus())) compTasks++;
                            }
                        }
                        double taskPct = totalTasks > 0 ? ((double) compTasks / totalTasks) * 100.0 : 80.0;
                        final int finalCompTasks = compTasks;
                        final int finalTotalTasks = totalTasks;

                    
                        FirebaseDAO.getInstance().getEnrollmentsForEmployee(memEmail).thenAccept(enrollments -> {
                            double avgScore = 85.0;
                            if (enrollments != null && !enrollments.isEmpty()) {
                                double sumScore = 0.0;
                                for (TrainingEnrollment te : enrollments) {
                                    sumScore += te.getAssessmentScore() > 0 ? te.getAssessmentScore() : 85.0;
                                }
                                avgScore = sumScore / enrollments.size();
                            }
                            final double finalAvgScore = avgScore;

                 
                            double overallScore = (taskPct * 0.6) + (finalAvgScore * 0.4);

                            Platform.runLater(() -> {
                                if (overallScore >= 85.0) highPerfCount[0]++;
                                totalPerfSum[0] += overallScore;
                                loadedCount[0]++;

                                double avgVel = loadedCount[0] > 0 ? (totalPerfSum[0] / loadedCount[0]) : 0.0;
                                avgVelocityVal.setText(String.format("%.0f%%", avgVel));
                                highPerfVal.setText(String.valueOf(highPerfCount[0]));

                                VBox card = createTeamMemberCard(member, overallScore, finalCompTasks, finalTotalTasks, finalAvgScore);
                                grid.add(card, c, r);
                            });
                        });
                    });

                    col++;
                    if (col > 1) {
                        col = 0;
                        row++;
                    }
                }
            });
        });
    }

    private VBox createTeamMemberCard(User u, double score, int compTasks, int totalTasks, double trainScore) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0, 0, 2);");

        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);

        Circle avatar = new Circle(20, Color.web("#2563EB"));
        String initStr = (u.getFullName() != null && !u.getFullName().isBlank()) ? u.getFullName().substring(0, 1).toUpperCase() : "E";
        Text initText = new Text(initStr);
        initText.setFill(Color.WHITE);
        initText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        StackPane avPane = new StackPane(avatar, initText);

        VBox textGroup = new VBox(2);
        Text nameText = new Text(u.getFullName() != null ? u.getFullName() : u.getEmail());
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        nameText.setFill(Color.web(TEXT));

        Text roleText = new Text((u.getRole() != null ? u.getRole() : "Team Member") + " • " + (u.getDepartment() != null ? u.getDepartment() : "Engineering"));
        roleText.setFont(Font.font("Arial", 12));
        roleText.setFill(Color.web(MUTED));

        textGroup.getChildren().addAll(nameText, roleText);
        top.getChildren().addAll(avPane, textGroup);

       
        VBox perfBox = new VBox(4);
        HBox perfRow = new HBox(10);
        perfRow.setAlignment(Pos.CENTER_LEFT);

        Text perfLabel = new Text("Performance Index:");
        perfLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        perfLabel.setFill(Color.web(TEXT));

        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);

        Label badge = new Label(String.format("%.0f%% Performance", score));
        if (score >= 85.0) {
            badge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-weight: bold; -fx-padding: 3px 10px; -fx-background-radius: 10px; -fx-font-size: 11px;");
        } else if (score >= 60.0) {
            badge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 3px 10px; -fx-background-radius: 10px; -fx-font-size: 11px;");
        } else {
            badge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-padding: 3px 10px; -fx-background-radius: 10px; -fx-font-size: 11px;");
        }

        perfRow.getChildren().addAll(perfLabel, sp, badge);

        ProgressBar pb = new ProgressBar(score / 100.0);
        pb.setMaxWidth(Double.MAX_VALUE);
        pb.setStyle("-fx-accent: " + (score >= 85.0 ? GREEN : (score >= 60.0 ? AMBER : RED)) + ";");

        perfBox.getChildren().addAll(perfRow, pb);

  
        HBox metricsRow = new HBox(16);
        Label tasksLbl = new Label("📌 Tasks: " + compTasks + " / " + totalTasks + " Completed");
        tasksLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #475569; -fx-font-weight: bold;");

        Label trainLbl = new Label("🎓 Training: " + String.format("%.0f%% Score", trainScore));
        trainLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #475569; -fx-font-weight: bold;");

        metricsRow.getChildren().addAll(tasksLbl, trainLbl);


        HBox btnRow = new HBox(10);
        Button viewDetailBtn = new Button("View Detailed Profile →");
        viewDetailBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(viewDetailBtn, Priority.ALWAYS);
        viewDetailBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 8px; -fx-border-color: #BFDBFE; -fx-border-radius: 8px; -fx-cursor: hand;");

        btnRow.getChildren().add(viewDetailBtn);

        viewDetailBtn.setOnAction(e -> {
            ManagerMemberDetailView detailView = new ManagerMemberDetailView(u, score, compTasks, totalTasks, trainScore, () -> {
                getChildren().setAll(mainScrollPane);
            });
            getChildren().setAll(detailView);
        });

        card.getChildren().addAll(top, perfBox, metricsRow, btnRow);
        return card;
    }
}
