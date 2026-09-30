package com.skillverse.admin.view;

import com.skillverse.CommonFeatures.AuditLog;
import com.skillverse.Dao.FirebaseDAO;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminActivityLogView extends VBox {

    private final Label totalActivitiesLbl;
    private final Label todayActivitiesLbl;
    private final Label successfulLbl;
    private final Label warningsLbl;

    private final TextField searchField;
    private final ComboBox<String> userRoleFilter;
    private final ComboBox<String> moduleFilter;
    private final ComboBox<String> statusFilter;

    private final TableView<AuditLog> activityTable;
    private final ObservableList<AuditLog> masterAuditList = FXCollections.observableArrayList();
    private final FilteredList<AuditLog> filteredAuditList = new FilteredList<>(masterAuditList, p -> true);

    public AdminActivityLogView(Stage stage) {
        this();
    }

    public AdminActivityLogView() {
        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("System Activity & Audit Log 📋");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Track real-time system actions, role transitions, authentication logs, and error telemetry.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        Button refreshBtn = new Button("🔄 Refresh Log");
        refreshBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadActivityLogsFromFirestore());

        headerBar.getChildren().addAll(titleBox, refreshBtn);

        HBox statsRow = new HBox(16);
        statsRow.setAlignment(Pos.CENTER_LEFT);

        VBox card1 = createStatCard("Total Activities", totalActivitiesLbl = new Label("0"), "#2563EB");
        VBox card2 = createStatCard("Today's Events", todayActivitiesLbl = new Label("0"), "#059669");
        VBox card3 = createStatCard("Successful", successfulLbl = new Label("0"), "#16A34A");
        VBox card4 = createStatCard("Warnings / Errors", warningsLbl = new Label("0"), "#DC2626");

        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);
        HBox.setHgrow(card3, Priority.ALWAYS);
        HBox.setHgrow(card4, Priority.ALWAYS);
        statsRow.getChildren().addAll(card1, card2, card3, card4);

        VBox filterCard = new VBox(12);
        filterCard.setPadding(new Insets(16, 20, 16, 20));
        filterCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label filterTitle = new Label("Filter Activities");
        filterTitle.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        HBox filterInputs = new HBox(12);
        filterInputs.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("🔍 Search by user, action, or keyword...");
        searchField.setPrefHeight(38);
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchField.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 8; -fx-padding: 0 12;");

        userRoleFilter = new ComboBox<>();
        userRoleFilter.getItems().addAll("All Users", "ADMIN", "MANAGER", "EMPLOYEE", "HR");
        userRoleFilter.setValue("All Users");
        userRoleFilter.setPrefHeight(38);

        moduleFilter = new ComboBox<>();
        moduleFilter.getItems().addAll("All Modules", "Authentication", "Employees", "Jobs", "Skills", "Approvals", "System");
        moduleFilter.setValue("All Modules");
        moduleFilter.setPrefHeight(38);

        statusFilter = new ComboBox<>();
        statusFilter.getItems().addAll("All Status", "SUCCESS", "WARNING", "FAILED");
        statusFilter.setValue("All Status");
        statusFilter.setPrefHeight(38);

        Button clearBtn = new Button("Clear Filters");
        clearBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
        clearBtn.setOnAction(e -> {
            searchField.clear();
            userRoleFilter.setValue("All Users");
            moduleFilter.setValue("All Modules");
            statusFilter.setValue("All Status");
        });

        filterInputs.getChildren().addAll(searchField, userRoleFilter, moduleFilter, statusFilter, clearBtn);
        filterCard.getChildren().addAll(filterTitle, filterInputs);

        VBox tableCard = new VBox(12);
        tableCard.setPadding(new Insets(20));
        tableCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label tableTitle = new Label("Recent Activity Stream");
        tableTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        activityTable = new TableView<>();
        activityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        activityTable.setPrefHeight(440);
        activityTable.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 8;");

        setupTableColumns();
        setupFilterListeners();

        tableCard.getChildren().addAll(tableTitle, activityTable);

        ScrollPane mainScroll = new ScrollPane(new VBox(20, headerBar, statsRow, filterCard, tableCard));
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScroll);

        loadActivityLogsFromFirestore();
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private VBox createStatCard(String title, Label valLbl, String color) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");

        valLbl.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");
        card.getChildren().addAll(valLbl, titleLbl);
        return card;
    }

    private void setupTableColumns() {
        TableColumn<AuditLog, String> colTime = new TableColumn<>("Time");
        colTime.setPrefWidth(140);
        colTime.setCellValueFactory(cellData -> {
            Object ts = cellData.getValue().getTimestamp();
            if (ts != null) {
                return new ReadOnlyObjectWrapper<>(ts.toString());
            }
            return new ReadOnlyObjectWrapper<>("Just now");
        });

        TableColumn<AuditLog, String> colUser = new TableColumn<>("User");
        colUser.setPrefWidth(160);
        colUser.setCellValueFactory(cellData -> {
            AuditLog log = cellData.getValue();
            String name = log.getUserName() != null ? log.getUserName() : log.getUserEmail();
            return new ReadOnlyObjectWrapper<>(name != null ? name : "System Service");
        });

        TableColumn<AuditLog, String> colAction = new TableColumn<>("Activity / Action");
        colAction.setPrefWidth(220);
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        TableColumn<AuditLog, String> colModule = new TableColumn<>("Module");
        colModule.setPrefWidth(120);
        colModule.setCellValueFactory(new PropertyValueFactory<>("module"));

        TableColumn<AuditLog, String> colStatus = new TableColumn<>("Status");
        colStatus.setPrefWidth(100);
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(col -> new TableCell<AuditLog, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label badge = new Label(status);
                    if ("SUCCESS".equalsIgnoreCase(status)) {
                        badge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #166534; -fx-padding: 4 10; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold;");
                    } else if ("WARNING".equalsIgnoreCase(status)) {
                        badge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #92400E; -fx-padding: 4 10; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold;");
                    } else {
                        badge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #991B1B; -fx-padding: 4 10; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold;");
                    }
                    setGraphic(badge);
                    setText(null);
                }
            }
        });

        activityTable.getColumns().addAll(colTime, colUser, colAction, colModule, colStatus);
    }

    private void setupFilterListeners() {
        searchField.textProperty().addListener((obs, old, val) -> applyFilters());
        userRoleFilter.setOnAction(e -> applyFilters());
        moduleFilter.setOnAction(e -> applyFilters());
        statusFilter.setOnAction(e -> applyFilters());
        activityTable.setItems(filteredAuditList);
    }

    private void applyFilters() {
        filteredAuditList.setPredicate(log -> {
            String query = searchField.getText() != null ? searchField.getText().toLowerCase().trim() : "";
            String role = userRoleFilter.getValue();
            String mod = moduleFilter.getValue();
            String st = statusFilter.getValue();

            boolean matchQ = query.isEmpty() ||
                    (log.getUserName() != null && log.getUserName().toLowerCase().contains(query)) ||
                    (log.getUserEmail() != null && log.getUserEmail().toLowerCase().contains(query)) ||
                    (log.getAction() != null && log.getAction().toLowerCase().contains(query));

            boolean matchRole = role == null || "All Users".equalsIgnoreCase(role) ||
                    (log.getUserRole() != null && log.getUserRole().equalsIgnoreCase(role));

            boolean matchMod = mod == null || "All Modules".equalsIgnoreCase(mod) ||
                    (log.getModule() != null && log.getModule().equalsIgnoreCase(mod));

            boolean matchSt = st == null || "All Status".equalsIgnoreCase(st) ||
                    (log.getStatus() != null && log.getStatus().equalsIgnoreCase(st));

            return matchQ && matchRole && matchMod && matchSt;
        });
    }

    public void loadActivityLogsFromFirestore() {
        FirebaseDAO.getInstance().getAllAuditLogs().thenAccept(logs -> {
            Platform.runLater(() -> {
                masterAuditList.clear();
                if (logs != null && !logs.isEmpty()) {
                    masterAuditList.addAll(logs);

                    int total = masterAuditList.size();
                    long successful = masterAuditList.stream().filter(l -> "SUCCESS".equalsIgnoreCase(l.getStatus())).count();
                    long warnings = masterAuditList.stream().filter(l -> "WARNING".equalsIgnoreCase(l.getStatus()) || "FAILED".equalsIgnoreCase(l.getStatus())).count();

                    totalActivitiesLbl.setText(String.valueOf(total));
                    todayActivitiesLbl.setText(String.valueOf(total));
                    successfulLbl.setText(String.valueOf(successful));
                    warningsLbl.setText(String.valueOf(warnings));
                } else {
                    totalActivitiesLbl.setText("0");
                    todayActivitiesLbl.setText("0");
                    successfulLbl.setText("0");
                    warningsLbl.setText("0");
                }
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }
}
