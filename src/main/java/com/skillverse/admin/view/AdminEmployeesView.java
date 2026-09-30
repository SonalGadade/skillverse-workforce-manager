package com.skillverse.admin.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.User;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class AdminEmployeesView extends VBox {

    private final Stage stage;
    private final Label totalEmployeesLabel = new Label("0");
    private final Label activeEmployeesLabel = new Label("0");
    private final Label departmentsLabel = new Label("0");

    private final TextField searchField = new TextField();
    private final TableView<User> employeeTable = new TableView<>();

    private final ObservableList<User> employeeMasterList = FXCollections.observableArrayList();
    private final FilteredList<User> filteredEmployeeList = new FilteredList<>(employeeMasterList, p -> true);

    public AdminEmployeesView() {
        this(null);
    }

    public AdminEmployeesView(Stage stage) {
        this.stage = stage;
        setSpacing(24);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLbl = new Label("Employee Management Directory 👥");
        titleLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLbl = new Label("Real-time enterprise workforce directory synchronized with Cloud Firestore.");
        subtitleLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLbl, subtitleLbl);

        Button refreshBtn = new Button("🔄 Refresh Directory");
        refreshBtn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2563EB; -fx-border-color: #CBD5E1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold; -fx-padding: 8 16; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadEmployeesFromFirestore());

        headerBar.getChildren().addAll(titleBox, refreshBtn);

        GridPane metricsGrid = new GridPane();
        metricsGrid.setHgap(16);
        metricsGrid.setVgap(16);

        VBox card1 = createStatCard("👥 Total Employees", totalEmployeesLabel, "Total Registered Users", "#2563EB", "#EFF6FF");
        VBox card2 = createStatCard("✅ Active Personnel", activeEmployeesLabel, "Currently Active Staff", "#059669", "#ECFDF5");
        VBox card3 = createStatCard("🏢 Business Units", departmentsLabel, "Functional Departments", "#7C3AED", "#F5F3FF");

        metricsGrid.add(card1, 0, 0);
        metricsGrid.add(card2, 1, 0);
        metricsGrid.add(card3, 2, 0);

        HBox filterBar = new HBox(12);
        filterBar.setAlignment(Pos.CENTER_LEFT);
        filterBar.setPadding(new Insets(16));
        filterBar.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label searchIcon = new Label("🔍");
        searchIcon.setStyle("-fx-font-size: 14px;");

        searchField.setPromptText("Search employees by name, email, role, or department...");
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #CBD5E1; -fx-padding: 8 12;");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterEmployees(newValue));

        filterBar.getChildren().addAll(searchIcon, searchField);

        VBox tableCard = new VBox(12);
        tableCard.setPadding(new Insets(20));
        tableCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        setupTableColumns();
        employeeTable.setItems(filteredEmployeeList);
        employeeTable.setPrefHeight(420);
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableCard.getChildren().add(employeeTable);

        ScrollPane mainScroll = new ScrollPane(new VBox(20, headerBar, metricsGrid, filterBar, tableCard));
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScroll);

        loadEmployeesFromFirestore();
    }

    private VBox createStatCard(String title, Label valLbl, String subTitle, String accentHex, String bgHex) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle("-fx-background-color: " + bgHex + "; -fx-background-radius: 12; -fx-border-color: " + accentHex + "33; -fx-border-radius: 12;");

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");

        valLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + accentHex + ";");

        Label subLbl = new Label(subTitle);
        subLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748B;");

        card.getChildren().addAll(titleLbl, valLbl, subLbl);
        return card;
    }

    private void setupTableColumns() {
        employeeTable.getColumns().clear();

        TableColumn<User, String> colName = new TableColumn<>("Full Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<User, String> colEmail = new TableColumn<>("Email Address");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> colRole = new TableColumn<>("Role");
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<User, String> colDept = new TableColumn<>("Department");
        colDept.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn<User, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData -> {
            String st = cellData.getValue().getStatus();
            return new javafx.beans.property.SimpleStringProperty(st != null ? st : "ACTIVE");
        });

        TableColumn<User, String> colJoining = new TableColumn<>("Joining Date");
        colJoining.setCellValueFactory(cellData -> {
            String dt = cellData.getValue().getJoiningDate();
            return new javafx.beans.property.SimpleStringProperty(dt != null ? dt : "2026-01-15");
        });

        employeeTable.getColumns().addAll(colName, colEmail, colRole, colDept, colStatus, colJoining);
    }

    public void filterByDepartment(String deptName) {
        if (deptName != null && !deptName.isBlank()) {
            searchField.setText(deptName);
        }
    }

    private void filterEmployees(String query) {
        if (query == null || query.isBlank()) {
            filteredEmployeeList.setPredicate(user -> true);
        } else {
            String lower = query.toLowerCase().trim();
            filteredEmployeeList.setPredicate(u ->
                (u.getFullName() != null && u.getFullName().toLowerCase().contains(lower)) ||
                (u.getEmail() != null && u.getEmail().toLowerCase().contains(lower)) ||
                (u.getRole() != null && u.getRole().toLowerCase().contains(lower)) ||
                (u.getDepartment() != null && u.getDepartment().toLowerCase().contains(lower))
            );
        }
    }

    public void loadEmployeesFromFirestore() {
        FirebaseDAO.getInstance().getAllEmployees().thenAccept(users -> {
            Platform.runLater(() -> {
                employeeMasterList.clear();
                if (users != null && !users.isEmpty()) {
                    employeeMasterList.addAll(users);

                    int total = users.size();
                    long activeCount = users.stream()
                            .filter(u -> u.getStatus() == null || !"INACTIVE".equalsIgnoreCase(u.getStatus()))
                            .count();
                    long deptCount = users.stream()
                            .map(User::getDepartment)
                            .filter(d -> d != null && !d.trim().isEmpty())
                            .distinct()
                            .count();

                    totalEmployeesLabel.setText(String.valueOf(total));
                    activeEmployeesLabel.setText(String.valueOf(activeCount));
                    departmentsLabel.setText(String.valueOf(deptCount));

                    showModernToast("Directory Synchronized 👥", "Loaded " + total + " employees directly from Firestore.", true);
                } else {
                    totalEmployeesLabel.setText("0");
                    activeEmployeesLabel.setText("0");
                    departmentsLabel.setText("0");
                }
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
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
}
