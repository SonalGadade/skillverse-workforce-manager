package com.skillverse.admin.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.User;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;
import java.util.stream.Collectors;

public class AdminDepartmentsView extends VBox {

    private final FlowPane cardsGrid;
    private final Label badgeDeptCount;

    public AdminDepartmentsView() {
        setSpacing(24);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("Departments Management 🏢");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Manage and explore all enterprise functional business units and team headcounts.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        badgeDeptCount = new Label("Loading...");
        badgeDeptCount.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1D4ED8; -fx-padding: 6 14; -fx-background-radius: 12; -fx-font-weight: bold; -fx-font-size: 13px;");

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-text-fill: #334155; -fx-font-weight: bold; -fx-padding: 10 16; -fx-background-radius: 8; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadDepartmentsFromFirestore());

        Button addDeptBtn = new Button("+ Add Department");
        addDeptBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;");
        addDeptBtn.setOnAction(e -> handleAddDepartment());

        headerBar.getChildren().addAll(titleBox, badgeDeptCount, refreshBtn, addDeptBtn);

        cardsGrid = new FlowPane();
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);
        cardsGrid.setPrefWrapLength(1080);

        ScrollPane scrollPane = new ScrollPane(cardsGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().addAll(headerBar, scrollPane);

        loadDepartmentsFromFirestore();
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }

    public void loadDepartmentsFromFirestore() {
        FirebaseDAO.getInstance().getAllEmployees().thenAccept(users -> {
            Platform.runLater(() -> {
                cardsGrid.getChildren().clear();

                if (users == null || users.isEmpty()) {
                    badgeDeptCount.setText("0 Departments");
                    renderEmptyState("No departments or personnel registered in database.");
                    return;
                }

                Map<String, List<User>> deptMap = users.stream()
                        .filter(u -> u.getDepartment() != null && !u.getDepartment().trim().isEmpty())
                        .collect(Collectors.groupingBy(u -> u.getDepartment().trim()));

                badgeDeptCount.setText(deptMap.size() + " Departments");

                if (deptMap.isEmpty()) {
                    renderEmptyState("No departments found. Click '+ Add Department' to create one.");
                    return;
                }

                deptMap.forEach((deptName, members) -> {
                    cardsGrid.getChildren().add(createDepartmentCard(deptName, members));
                });
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private VBox createDepartmentCard(String deptName, List<User> members) {
        VBox card = new VBox(14);
        card.setPrefWidth(320);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label iconLbl = new Label(getDeptIcon(deptName));
        iconLbl.setStyle("-fx-font-size: 18px; -fx-background-color: #F1F5F9; -fx-padding: 8 12; -fx-background-radius: 8;");

        VBox nameBox = new VBox(2);
        Label nameLbl = new Label(deptName);
        nameLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        Label subLbl = new Label("Organizational Unit");
        subLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");
        nameBox.getChildren().addAll(nameLbl, subLbl);

        topRow.getChildren().addAll(iconLbl, nameBox);

        HBox bottomRow = new HBox(12);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        Label countLbl = new Label(members.size() + (members.size() == 1 ? " Personnel" : " Personnel"));
        countLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2563EB;");
        HBox.setHgrow(countLbl, Priority.ALWAYS);

        Button viewTeamBtn = new Button("View Team →");
        viewTeamBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 12px;");
        viewTeamBtn.setOnAction(e -> {
            if (getScene() != null && getScene().getRoot() != null) {
                javafx.scene.Node contentArea = getScene().getRoot().lookup("#contentArea");
                if (contentArea instanceof Pane) {
                    AdminEmployeesView empView = new AdminEmployeesView();
                    empView.filterByDepartment(deptName);
                    ((Pane) contentArea).getChildren().setAll(empView);
                    return;
                }
            }

            javafx.scene.Node p = getParent();
            while (p != null) {
                if (p instanceof BorderPane) {
                    AdminEmployeesView empView = new AdminEmployeesView();
                    empView.filterByDepartment(deptName);
                    ((BorderPane) p).setCenter(empView);
                    break;
                } else if (p instanceof Pane && !(p instanceof ScrollPane) && !p.getClass().getName().contains("ScrollPaneSkin")) {
                    AdminEmployeesView empView = new AdminEmployeesView();
                    empView.filterByDepartment(deptName);
                    ((Pane) p).getChildren().setAll(empView);
                    break;
                }
                p = p.getParent();
            }
        });

        bottomRow.getChildren().addAll(countLbl, viewTeamBtn);
        card.getChildren().addAll(topRow, bottomRow);
        return card;
    }

    private String getDeptIcon(String dept) {
        String lower = dept.toLowerCase();
        if (lower.contains("hr") || lower.contains("human")) return "👥";
        if (lower.contains("tech") || lower.contains("it") || lower.contains("dev") || lower.contains("eng") || lower.contains("software")) return "💻";
        if (lower.contains("finance") || lower.contains("acc")) return "₹";
        if (lower.contains("market")) return "📈";
        if (lower.contains("test") || lower.contains("qa")) return "✓";
        if (lower.contains("learning") || lower.contains("train")) return "📚";
        if (lower.contains("admin")) return "⚙️";
        return "🏢";
    }

    private void renderEmptyState(String msg) {
        VBox emptyBox = new VBox(10);
        emptyBox.setAlignment(Pos.CENTER);
        emptyBox.setPadding(new Insets(60));
        emptyBox.setPrefWidth(900);

        Label icon = new Label("🏢");
        icon.setStyle("-fx-font-size: 36px;");

        Label label = new Label(msg);
        label.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 14px;");

        emptyBox.getChildren().addAll(icon, label);
        cardsGrid.getChildren().add(emptyBox);
    }

    private void handleAddDepartment() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create New Department");
        dialog.setHeaderText("Add Functional Business Unit");
        dialog.setContentText("Department Name:");

        dialog.showAndWait().ifPresent(name -> {
            String trimmed = name.trim();
            if (!trimmed.isEmpty()) {
                FirebaseDAO.getInstance().createDepartment(trimmed).thenRun(() -> {
                    Platform.runLater(this::loadDepartmentsFromFirestore);
                });
            }
        });
    }
}
