package com.skillverse.admin.view;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.skillverse.CommonFeatures.JobPosting;
import com.skillverse.CommonFeatures.User;
import com.skillverse.Dao.FirebaseDAO;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AdminAnalyticsView extends VBox {

    private final Stage stage;
    private final Label totalEmpVal;
    private final Label growthVal;
    private final Label activeDeptsVal;
    private final Label openJobsVal;

    private final BarChart<String, Number> empGrowthChart;
    private final PieChart deptPieChart;
    private final FlowPane pieLegendContainer;

    private static final String[] SLICE_COLORS = {
        "#2563EB", "#059669", "#D97706", "#7C3AED", "#DB2777",
        "#0891B2", "#DC2626", "#4F46E5", "#0D9488", "#CA8A04"
    };

    public AdminAnalyticsView() {
        this(null);
    }

    public AdminAnalyticsView(Stage stage) {
        this.stage = stage;
        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("Enterprise Analytics & Insights ↗");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Comprehensive organizational velocity, talent acquisition, and workforce distribution.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        Button refreshBtn = new Button("🔄 Refresh Data");
        refreshBtn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-text-fill: #334155; -fx-font-weight: bold; -fx-padding: 10 16; -fx-background-radius: 8; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadLiveAnalytics());

        headerBar.getChildren().addAll(titleBox, refreshBtn);

        HBox statsRow = new HBox(14);
        statsRow.setAlignment(Pos.CENTER_LEFT);

        VBox card1 = createStatCard("Total Employees", totalEmpVal = new Label("0"), "#2563EB");
        VBox card2 = createStatCard("Workforce Velocity", growthVal = new Label("100%"), "#059669");
        VBox card3 = createStatCard("Active Departments", activeDeptsVal = new Label("0"), "#7C3AED");
        VBox card4 = createStatCard("Open Jobs", openJobsVal = new Label("0"), "#D97706");
        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);
        HBox.setHgrow(card3, Priority.ALWAYS);
        HBox.setHgrow(card4, Priority.ALWAYS);
        statsRow.getChildren().addAll(card1, card2, card3, card4);

        HBox middleChartsRow = new HBox(20);

        VBox growthCard = new VBox(10);
        growthCard.setPrefWidth(640);
        growthCard.setPadding(new Insets(20, 20, 16, 20));
        growthCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label growthTitle = new Label("\uD83D\uDCCA Workforce Distribution by Role");
        growthTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label growthSub = new Label("Live employee count grouped by assigned organizational role");
        growthSub.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Employee Role");
        xAxis.setTickLabelRotation(-30);
        xAxis.setStyle("-fx-tick-label-fill: #1E293B; -fx-font-weight: bold; -fx-font-size: 12px;");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Employees");
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);
        yAxis.setStyle("-fx-tick-label-fill: #1E293B; -fx-font-weight: bold; -fx-font-size: 12px;");

        empGrowthChart = new BarChart<>(xAxis, yAxis);
        empGrowthChart.setTitle(null);
        empGrowthChart.setLegendVisible(false);
        empGrowthChart.setAnimated(false);
        empGrowthChart.setBarGap(4);
        empGrowthChart.setCategoryGap(20);
        empGrowthChart.setPrefHeight(300);
        empGrowthChart.setStyle("-fx-background-color: transparent;");

        growthCard.getChildren().addAll(growthTitle, growthSub, empGrowthChart);

        VBox deptCard = new VBox(10);
        HBox.setHgrow(deptCard, Priority.ALWAYS);
        deptCard.setPadding(new Insets(20, 20, 16, 20));
        deptCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label deptTitle = new Label("\uD83C\uDFE2 Department Headcount Distribution");
        deptTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label deptSub = new Label("Live employee allocation across all organizational units");
        deptSub.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");

        deptPieChart = new PieChart();
        deptPieChart.setLegendVisible(false);
        deptPieChart.setLabelsVisible(false);
        deptPieChart.setAnimated(false);
        deptPieChart.setPrefHeight(240);
        deptPieChart.setStyle("-fx-background-color: transparent;");

        pieLegendContainer = new FlowPane();
        pieLegendContainer.setHgap(10);
        pieLegendContainer.setVgap(8);
        pieLegendContainer.setAlignment(Pos.CENTER);
        pieLegendContainer.setPadding(new Insets(8, 4, 4, 4));

        ScrollPane legendScroll = new ScrollPane(pieLegendContainer);
        legendScroll.setFitToWidth(true);
        legendScroll.setPrefHeight(90);
        legendScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        deptCard.getChildren().addAll(deptTitle, deptSub, deptPieChart, legendScroll);

        middleChartsRow.getChildren().addAll(growthCard, deptCard);

        getChildren().addAll(headerBar, statsRow, middleChartsRow);

        loadLiveAnalytics();
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private VBox createStatCard(String title, Label valLbl, String accentColor) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 18, 16, 18));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        valLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + accentColor + ";");
        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");

        card.getChildren().addAll(valLbl, titleLbl);
        return card;
    }

    public void loadLiveAnalytics() {
        FirebaseDAO.getInstance().getAllEmployees().thenAccept(users -> {
            FirebaseDAO.getInstance().getAllJobPostings().thenAccept(jobs -> {
                FirebaseDAO.getInstance().getPendingSystemApprovals().thenAccept(approvals -> {
                    Platform.runLater(() -> {
                        List<User> employeeList = users != null ? users : Collections.emptyList();
                        List<JobPosting> jobList = jobs != null ? jobs : Collections.emptyList();

                        // 1. Update Top Metric Cards
                        totalEmpVal.setText(String.valueOf(employeeList.size()));
                        growthVal.setText("+100% Active");

                        Set<String> depts = employeeList.stream()
                                .map(User::getDepartment)
                                .filter(d -> d != null && !d.trim().isEmpty())
                                .collect(Collectors.toSet());
                        activeDeptsVal.setText(String.valueOf(depts.size()));

                        long activeJobs = jobList.stream().filter(j -> j.getStatus() == null || "ACTIVE".equalsIgnoreCase(j.getStatus()) || "OPEN".equalsIgnoreCase(j.getStatus())).count();
                        openJobsVal.setText(String.valueOf(activeJobs));


                        // 2. Render Role Bar Chart
                        empGrowthChart.getData().clear();
                        XYChart.Series<String, Number> series = new XYChart.Series<>();
                        Map<String, Long> roleCounts = employeeList.stream()
                                .filter(u -> u.getRole() != null)
                                .collect(Collectors.groupingBy(User::getRole, Collectors.counting()));
                        roleCounts.forEach((role, cnt) -> series.getData().add(new XYChart.Data<>(role, cnt)));
                        empGrowthChart.getData().add(series);

                        // 3. Render Department Pie Chart with named color legend
                        deptPieChart.getData().clear();
                        pieLegendContainer.getChildren().clear();

                        Map<String, Long> deptCounts = new LinkedHashMap<>();
                        for (User u : employeeList) {
                            if (u.getDepartment() != null && !u.getDepartment().isBlank()) {
                                String d = u.getDepartment().trim();
                                deptCounts.put(d, deptCounts.getOrDefault(d, 0L) + 1);
                            }
                        }
                        if (deptCounts.isEmpty()) deptCounts.put("General", 1L);

                        int cIdx = 0;
                        for (Map.Entry<String, Long> entry : deptCounts.entrySet()) {
                            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
                            deptPieChart.getData().add(slice);

                            String hex = SLICE_COLORS[cIdx % SLICE_COLORS.length];
                            HBox chip = new HBox(6);
                            chip.setAlignment(Pos.CENTER_LEFT);
                            chip.setStyle("-fx-background-color: #F8FAFC; -fx-padding: 5 10; -fx-border-color: #E2E8F0; -fx-border-radius: 6; -fx-background-radius: 6;");
                            Circle dot = new Circle(5, Color.web(hex));
                            Label nameLbl = new Label(entry.getKey() + " (" + entry.getValue() + ")");
                            nameLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
                            chip.getChildren().addAll(dot, nameLbl);
                            pieLegendContainer.getChildren().add(chip);
                            cIdx++;
                        }

                        Platform.runLater(() -> {
                            int i = 0;
                            for (PieChart.Data slice : deptPieChart.getData()) {
                                if (slice.getNode() != null) {
                                    slice.getNode().setStyle("-fx-pie-color: " + SLICE_COLORS[i % SLICE_COLORS.length] + ";");
                                }
                                i++;
                            }
                        });

                    });
                });
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }
}
