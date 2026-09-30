package com.skillverse.trainer.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.TrainingEnrollment;
import com.skillverse.CommonFeatures.TrainingProgram;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.skillverse.trainer.controller.NavigationController;
import javafx.stage.Stage;

public class TrainerDashboardView extends VBox {

   
    private Label totalStudentsVal;
    private Label activeCoursesVal;
    private Label assessmentsVal;
    private Label avgAttendanceVal;
    private Label completedVal;


    private VBox tasksListBox = new VBox();
    private StackPane engagementChartContainer;
    private VBox upcomingEventsBox = new VBox();
    private VBox performanceTableContainer;
    private VBox submissionsListBox;
    private ComboBox<String> courseFilterCombo;
    private VBox toastBannerBox;

    
    private List<TrainingProgram> cachedPrograms = new ArrayList<>();
    private List<TrainingEnrollment> cachedEnrollments = new ArrayList<>();
    private List<com.skillverse.CommonFeatures.TrainerAssessment> cachedAssessments = new ArrayList<>();
    private List<String> taskList = new ArrayList<>();

    public TrainerDashboardView() {
        setSpacing(20);
        setPadding(new Insets(24));
        setStyle("-fx-background-color: #F8FAFC;");

     
        HBox headerBar = buildHeader();

        toastBannerBox = new VBox();

       
        HBox topMetricsRow = buildTopMetricsRow();

      
        HBox middleRow = buildMiddleRow();

 
        HBox bottomRow = buildBottomRow();

       
        VBox mainContent = new VBox(20, headerBar, toastBannerBox, topMetricsRow, middleRow, bottomRow);
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        getChildren().add(scrollPane);

        loadDashboardData();
    }

    private HBox buildHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(2);
        String trainerName = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getFullName() : "Trainer";
        Text greeting = new Text("Welcome back, " + trainerName + " 👋");
        greeting.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        greeting.setFill(Color.web("#0F172A"));

        Text subtitle = new Text("Monitor student engagement, grade assessments, and track live course metrics.");
        subtitle.setFont(Font.font("Arial", 13));
        subtitle.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(greeting, subtitle);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Sync Live Data");
        refreshBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 8px; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadDashboardData());

        header.getChildren().addAll(titleBox, sp, refreshBtn);
        return header;
    }


    private HBox buildTopMetricsRow() {
        HBox row = new HBox(14);
        row.setPadding(new Insets(10, 0, 10, 0));
        row.setAlignment(Pos.CENTER);

        Stage currentStage = (getScene() != null && getScene().getWindow() instanceof Stage) ? (Stage) getScene().getWindow() : null;

       
        VBox c1 = createStatCard("👥", "#4F46E5", "Enrolled Students", "0");
        totalStudentsVal = (Label) c1.getUserData();
        c1.setOnMouseClicked(e -> {
            Stage st = (Stage) c1.getScene().getWindow();
            LearnersPage.show(st, null);
        });

  
        VBox c2 = createStatCard("📚", "#2563EB", "Active Courses", "0");
        activeCoursesVal = (Label) c2.getUserData();
        c2.setOnMouseClicked(e -> {
            Stage st = (Stage) c2.getScene().getWindow();
            CoursePage.show(st, null);
        });

       
        VBox c3 = createStatCard("📝", "#D97706", "Assessments", "0");
        assessmentsVal = (Label) c3.getUserData();
        c3.setOnMouseClicked(e -> {
            Stage st = (Stage) c3.getScene().getWindow();
            AssessmentPage.show(st, null);
        });

        VBox c4 = createStatCard("🎯", "#059669", "Avg. Attendance", "0%");
        avgAttendanceVal = (Label) c4.getUserData();
        c4.setOnMouseClicked(e -> {
            Stage st = (Stage) c4.getScene().getWindow();
            PerformancePage.show(st, null);
        });

     
        VBox c5 = createStatCard("✅", "#7C3AED", "Completed / Certified", "0");
        completedVal = (Label) c5.getUserData();
        c5.setOnMouseClicked(e -> {
            Stage st = (Stage) c5.getScene().getWindow();
            PerformancePage.show(st, null);
        });

        row.getChildren().addAll(c1, c2, c3, c4, c5);
        for (javafx.scene.Node n : row.getChildren()) {
            HBox.setHgrow(n, Priority.ALWAYS);
        }

        return row;
    }


private VBox createStatCard(
        String iconSymbol,
        String accentHex,
        String labelText,
        String initialVal
) {


    VBox card = new VBox(10);

    card.setPadding(new Insets(16));

    card.setMinHeight(130);
    card.setPrefHeight(150);

    card.setMaxWidth(Double.MAX_VALUE);

    card.setAlignment(Pos.TOP_LEFT);

    card.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #E2E8F0;" +
            "-fx-border-width: 1px;" +
            "-fx-border-radius: 14px;" +
            "-fx-background-radius: 14px;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(15,23,42,0.08)," +
                "8," +
                "0.1," +
                "0," +
                "2" +
            ");"
    );

    HBox top = new HBox(10);

    top.setAlignment(Pos.CENTER_LEFT);

    Label icon = new Label(iconSymbol);

    icon.setMinWidth(42);
    icon.setMinHeight(42);

    icon.setPrefWidth(42);
    icon.setPrefHeight(42);

    icon.setAlignment(Pos.CENTER);

    icon.setStyle(
            "-fx-font-family: 'Segoe UI Emoji';" +
            "-fx-font-size: 24px;" +
            "-fx-background-color: " + accentHex + "1A;" +
            "-fx-text-fill: " + accentHex + ";" +
            "-fx-background-radius: 10px;"
    );


    Label title = new Label(labelText);

    title.setWrapText(true);

    title.setStyle(
            "-fx-font-family: 'Arial';" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #64748B;"
    );


  
    top.getChildren().addAll(
            icon,
            title
    );

    Label valLbl = new Label(initialVal);

    valLbl.setStyle(
            "-fx-font-family: 'Arial';" +
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #0F172A;"
    );


    card.getChildren().addAll(
            top,
            valLbl
    );

    card.setUserData(valLbl);


    return card;
}

    private HBox buildMiddleRow() {
        HBox row = new HBox(16);

       
        VBox engagementCard = new VBox(14);
        engagementCard.setPadding(new Insets(18));
        engagementCard.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 14px; -fx-background-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0.1, 0, 2);");

        Text eTitle = new Text("📊 Attendance & Engagement Rate");
        eTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        eTitle.setFill(Color.web("#0F172A"));

        engagementChartContainer = new StackPane();
        engagementChartContainer.setPrefHeight(200);

        HBox legendRow = new HBox(24);
        legendRow.setAlignment(Pos.CENTER);
        Label presTag = new Label("🟢 Present");
        presTag.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #e02900;");
        Label absTag = new Label("⚪ Absent");
        absTag.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #e39b00;");
        legendRow.getChildren().addAll(presTag, absTag);

        engagementCard.getChildren().addAll(eTitle, engagementChartContainer, legendRow);

        row.getChildren().add(engagementCard);
        HBox.setHgrow(engagementCard, Priority.ALWAYS);
        return row;
    }

    private HBox buildBottomRow() {
        HBox row = new HBox(16);

       
        VBox perfCard = new VBox(14);
        perfCard.setPadding(new Insets(18));
        perfCard.setPrefWidth(680);
        perfCard.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 14px; -fx-background-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0.1, 0, 2);");

        HBox pHeader = new HBox();
        pHeader.setAlignment(Pos.CENTER_LEFT);

        Text perfTitle = new Text("🎓 Student Performance & Grading Table");
        perfTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        perfTitle.setFill(Color.web("#0F172A"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        courseFilterCombo = new ComboBox<>();
        courseFilterCombo.getItems().add("All Courses");
        courseFilterCombo.setValue("All Courses");
        courseFilterCombo.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 6px; -fx-font-size: 11px;");
        courseFilterCombo.setOnAction(e -> renderPerformanceRows());

        pHeader.getChildren().addAll(perfTitle, sp, courseFilterCombo);

        performanceTableContainer = new VBox(10);
        performanceTableContainer.getChildren().add(new Label("🔄 Loading student performance records..."));

        perfCard.getChildren().addAll(pHeader, performanceTableContainer);

     
        VBox reportsCard = new VBox(14);
        reportsCard.setPadding(new Insets(18));
        reportsCard.setPrefWidth(320);
        reportsCard.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 14px; -fx-background-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 8, 0.1, 0, 2);");

        Text rTitle = new Text("📄 Recent Submissions & Reports");
        rTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        rTitle.setFill(Color.web("#0F172A"));

        submissionsListBox = new VBox(10);

        Button viewAllBtn = new Button("View All Submissions →");
        viewAllBtn.setMaxWidth(Double.MAX_VALUE);
        viewAllBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8px; -fx-background-radius: 8px; -fx-cursor: hand;");

        reportsCard.getChildren().addAll(rTitle, submissionsListBox, viewAllBtn);

        row.getChildren().addAll(perfCard, reportsCard);
        HBox.setHgrow(perfCard, Priority.ALWAYS);
        HBox.setHgrow(reportsCard, Priority.ALWAYS);
        return row;
    }

    private void loadDashboardData() {
        String trainerEmail = (UserSession.getCurrentUser() != null && UserSession.getCurrentUser().getEmail() != null && !UserSession.getCurrentUser().getEmail().isBlank())
                ? UserSession.getCurrentUser().getEmail().toLowerCase().trim()
                : "trainer@skillverse.com";

        FirebaseDAO.getInstance().getTrainingsForTrainer(trainerEmail).thenAccept(programs -> {
            this.cachedPrograms = programs != null ? programs : new ArrayList<>();

            FirebaseDAO.getInstance().getEnrollmentsForTrainerCourses(trainerEmail).thenAccept(enrollments -> {
                this.cachedEnrollments = enrollments != null ? enrollments : new ArrayList<>();

                FirebaseDAO.getInstance().getAssessmentsForEmployee(null).thenAccept(assessments -> {
                    this.cachedAssessments = assessments != null ? assessments : new ArrayList<>();

                    Platform.runLater(() -> {
                        updateMetricsUI();
                        renderEngagementChart();
                        populateCourseFilterOptions();
                        renderPerformanceRows();
                        renderSubmissionsUI();
                    });
                });
            });
        });
    }

    private void updateMetricsUI() {
        Set<String> distinctStudents = new HashSet<>();
        double totalAtt = 0.0;
        int completedCount = 0;

        for (TrainingEnrollment te : cachedEnrollments) {
            if (te.getEmployeeEmail() != null && !te.getEmployeeEmail().isBlank()) {
                distinctStudents.add(te.getEmployeeEmail().toLowerCase().trim());
            }
            totalAtt += te.getAttendancePercentage();
            if ("COMPLETED".equalsIgnoreCase(te.getCompletionStatus())) completedCount++;
        }

        int totalStud = distinctStudents.size() > 0 ? distinctStudents.size() : cachedEnrollments.size();
        int activeCourses = cachedPrograms.size();
        int totalAssessments = (cachedAssessments != null && !cachedAssessments.isEmpty()) ? cachedAssessments.size() : 2;
        double avgAtt = cachedEnrollments.size() > 0 ? (totalAtt / cachedEnrollments.size()) : 74.0;

        totalStudentsVal.setText(String.valueOf(totalStud));
        activeCoursesVal.setText(String.valueOf(activeCourses));
        assessmentsVal.setText(String.valueOf(totalAssessments));
        avgAttendanceVal.setText(String.format("%.0f%%", avgAtt));
        completedVal.setText(String.valueOf(completedCount));
    }

    private void renderTasksUI() {
        tasksListBox.getChildren().clear();
        for (String task : taskList) {
            HBox item = new HBox(10);
            item.setAlignment(Pos.CENTER_LEFT);
            item.setPadding(new Insets(8, 10, 8, 10));
            item.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

            CheckBox cb = new CheckBox();
            Text label = new Text(task);
            label.setFont(Font.font("Arial", 12));
            label.setFill(Color.web("#0F172A"));

            cb.setOnAction(e -> {
                if (cb.isSelected()) {
                    label.setStrikethrough(true);
                    label.setFill(Color.web("#94A3B8"));
                } else {
                    label.setStrikethrough(false);
                    label.setFill(Color.web("#0F172A"));
                }
            });

            item.getChildren().addAll(cb, label);
            tasksListBox.getChildren().add(item);
        }
    }

    private void showAddTaskModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Action Item");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 14px; -fx-background-radius: 14px;");

        ButtonType addType = new ButtonType("Add Task", ButtonData.OK_DONE);
        pane.getButtonTypes().addAll(addType, ButtonType.CANCEL);

        TextField input = new TextField();
        input.setPromptText("Task Title (e.g. Grade Spring Boot Assignment)...");
        input.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        VBox box = new VBox(10, new Label("Action Item / Task:"), input);
        box.setPadding(new Insets(16));
        pane.setContent(box);

        dialog.setResultConverter(btn -> {
            if (btn == addType && !input.getText().isBlank()) {
                taskList.add(input.getText().trim());
                renderTasksUI();
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void renderEngagementChart() {
        engagementChartContainer.getChildren().clear();

        double totalAtt = 0.0;
        for (TrainingEnrollment te : cachedEnrollments) {
            totalAtt += te.getAttendancePercentage();
        }
        double avgAtt = cachedEnrollments.size() > 0 ? (totalAtt / cachedEnrollments.size()) : 85.0;
        double absentPct = 100.0 - avgAtt;

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
            new PieChart.Data("Present", avgAtt),
            new PieChart.Data("Absent", absentPct)
        );

        PieChart chart = new PieChart(pieData);
        chart.setLabelsVisible(false);
        chart.setLegendVisible(false);
        chart.setPrefSize(160, 160);

        Circle innerCircle = new Circle(48, Color.web("#FFFFFF"));
        Label centerText = new Label(String.format("%.0f%%\nPresent", avgAtt));
        centerText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        centerText.setTextFill(Color.web("#0F172A"));
        centerText.setAlignment(Pos.CENTER);

        StackPane donutCenter = new StackPane(innerCircle, centerText);

        engagementChartContainer.getChildren().addAll(chart, donutCenter);
    }

    private void renderPlannerSessions(List<Map<String, String>> sessions) {
        upcomingEventsBox.getChildren().clear();
        if (sessions == null || sessions.isEmpty()) {
            Label empty = new Label("No live sessions scheduled for today.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 12px;");
            upcomingEventsBox.getChildren().add(empty);
            return;
        }

        for (Map<String, String> s : sessions) {
            HBox item = new HBox(10);
            item.setAlignment(Pos.CENTER_LEFT);
            item.setPadding(new Insets(8, 10, 8, 10));
            item.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

            Label icon = new Label("🎥");
            icon.setStyle("-fx-font-size: 14px;");

            VBox info = new VBox(2);
            Text title = new Text(s.getOrDefault("title", "Live Session"));
            title.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            title.setFill(Color.web("#0F172A"));

            Text sub = new Text(s.getOrDefault("time", "10:00 AM") + " • " + s.getOrDefault("location", "Virtual Room"));
            sub.setFont(Font.font("Arial", 10));
            sub.setFill(Color.web("#64748B"));

            info.getChildren().addAll(title, sub);
            item.getChildren().addAll(icon, info);
            upcomingEventsBox.getChildren().add(item);
        }
    }

    private void populateCourseFilterOptions() {
        courseFilterCombo.getItems().clear();
        courseFilterCombo.getItems().add("All Courses");
        Set<String> titles = new HashSet<>();
        for (TrainingProgram p : cachedPrograms) {
            if (p.getTitle() != null && !p.getTitle().isBlank()) titles.add(p.getTitle());
        }
        for (TrainingEnrollment te : cachedEnrollments) {
            if (te.getProgramTitle() != null && !te.getProgramTitle().isBlank()) titles.add(te.getProgramTitle());
        }
        courseFilterCombo.getItems().addAll(titles);
        courseFilterCombo.setValue("All Courses");
    }

    private void renderPerformanceRows() {
        performanceTableContainer.getChildren().clear();

        String selectedFilter = courseFilterCombo.getValue();
        List<TrainingEnrollment> filtered = new ArrayList<>();
        for (TrainingEnrollment te : cachedEnrollments) {
            if ("All Courses".equalsIgnoreCase(selectedFilter) || selectedFilter == null || selectedFilter.equalsIgnoreCase(te.getProgramTitle())) {
                filtered.add(te);
            }
        }

        if (filtered.isEmpty()) {
            Label empty = new Label("No student records found for selected course.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px; -fx-padding: 10px;");
            performanceTableContainer.getChildren().add(empty);
            return;
        }

        for (TrainingEnrollment te : filtered) {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(10, 14, 10, 14));
            row.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

            Circle avatar = new Circle(14, Color.web("#4F46E5"));
            String initStr = te.getEmployeeName() != null && !te.getEmployeeName().isBlank() ? te.getEmployeeName().substring(0, 1).toUpperCase() : "S";
            Text avText = new Text(initStr); avText.setFill(Color.WHITE); avText.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            StackPane avStack = new StackPane(avatar, avText);

            VBox studentInfo = new VBox(2);
            Text name = new Text(te.getEmployeeName() != null ? te.getEmployeeName() : "Student");
            name.setFont(Font.font("Arial", FontWeight.BOLD, 13)); name.setFill(Color.web("#0F172A"));
            Text sub = new Text(te.getEmployeeEmail() + " • " + te.getProgramTitle());
            sub.setFont(Font.font("Arial", 10)); sub.setFill(Color.web("#64748B"));
            studentInfo.getChildren().addAll(name, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label attTag = new Label("Att: " + (int) te.getAttendancePercentage() + "%");
            attTag.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E40AF; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 3px 8px; -fx-background-radius: 6px;");

            double score = te.getAssessmentScore();
            String scoreBg = score >= 80 ? "#DCFCE7" : (score >= 60 ? "#FEF3C7" : "#FEE2E2");
            String scoreFg = score >= 80 ? "#15803D" : (score >= 60 ? "#D97706" : "#B91C1C");
            Label scoreTag = new Label("Score: " + (int) score + "%");
            scoreTag.setStyle("-fx-background-color: " + scoreBg + "; -fx-text-fill: " + scoreFg + "; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 3px 8px; -fx-background-radius: 6px;");

            Label statusTag = new Label(te.getCompletionStatus() != null ? te.getCompletionStatus() : "IN_PROGRESS");
            statusTag.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 6px;");

            Button editBtn = new Button("✏️ Edit");
            editBtn.setStyle("-fx-background-color: #4F46E5; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-cursor: hand;");
            editBtn.setOnAction(e -> showEditGradeModal(te));

            row.getChildren().addAll(avStack, studentInfo, sp, attTag, scoreTag, statusTag, editBtn);
            performanceTableContainer.getChildren().add(row);
        }
    }

    private void showEditGradeModal(TrainingEnrollment te) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Student Score");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 14px; -fx-background-radius: 14px;");

        ButtonType saveBtnType = new ButtonType("Save Grade", ButtonData.OK_DONE);
        pane.getButtonTypes().addAll(saveBtnType, ButtonType.CANCEL);

        VBox content = new VBox(12);
        content.setPadding(new Insets(16));
        content.setPrefWidth(380);

        Text title = new Text("✏️ Edit Score for " + te.getEmployeeName());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        title.setFill(Color.web("#0F172A"));

        TextField attF = new TextField(String.valueOf((int) te.getAttendancePercentage()));
        TextField scoreF = new TextField(String.valueOf((int) te.getAssessmentScore()));
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("IN_PROGRESS", "COMPLETED", "DROPPED");
        statusCombo.setValue(te.getCompletionStatus() != null ? te.getCompletionStatus() : "IN_PROGRESS");
        statusCombo.setMaxWidth(Double.MAX_VALUE);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("Attendance %:"), 0, 0); grid.add(attF, 1, 0);
        grid.add(new Label("Score %:"), 0, 1); grid.add(scoreF, 1, 1);
        grid.add(new Label("Status:"), 0, 2); grid.add(statusCombo, 1, 2);

        content.getChildren().addAll(title, grid);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtnType) {
                double attVal = 0.0; double scoreVal = 0.0;
                try { attVal = Double.parseDouble(attF.getText().trim()); } catch (Exception ex) {}
                try { scoreVal = Double.parseDouble(scoreF.getText().trim()); } catch (Exception ex) {}

                FirebaseDAO.getInstance().updateTrainerGrading(te.getEnrollmentId(), attVal, scoreVal, statusCombo.getValue(), "").thenAccept(ok -> {
                    Platform.runLater(() -> {
                        showSuccessToast("Student grade updated successfully! 🚀");
                        loadDashboardData();
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void renderSubmissionsUI() {
        submissionsListBox.getChildren().clear();
        if (cachedEnrollments.isEmpty()) {
            Label empty = new Label("No recent assignment submissions.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 12px;");
            submissionsListBox.getChildren().add(empty);
            return;
        }

        int count = 0;
        for (TrainingEnrollment te : cachedEnrollments) {
            if (count++ >= 4) break;
            HBox item = new HBox(10);
            item.setAlignment(Pos.CENTER_LEFT);
            item.setPadding(new Insets(8, 10, 8, 10));
            item.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

            Label docIcon = new Label("📑");
            docIcon.setStyle("-fx-font-size: 14px;");

            VBox info = new VBox(2);
            Text title = new Text(te.getEmployeeName() + " — Assessment");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            title.setFill(Color.web("#0F172A"));

            Text sub = new Text(te.getProgramTitle() + " • Submitted");
            sub.setFont(Font.font("Arial", 10));
            sub.setFill(Color.web("#64748B"));

            info.getChildren().addAll(title, sub);
            item.getChildren().addAll(docIcon, info);
            submissionsListBox.getChildren().add(item);
        }
    }

    private void showSuccessToast(String message) {
        if (toastBannerBox == null) return;
        toastBannerBox.getChildren().clear();
        HBox toast = new HBox(10);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(10, 16, 10, 16));
        toast.setStyle("-fx-background-color: #DCFCE7; -fx-border-color: #86EFAC; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label icon = new Label("✅"); icon.setStyle("-fx-font-size: 14px;");
        Text msg = new Text(message); msg.setFont(Font.font("Arial", FontWeight.BOLD, 13)); msg.setFill(Color.web("#15803D"));
        toast.getChildren().addAll(icon, msg);
        toastBannerBox.getChildren().add(toast);
    }
}
