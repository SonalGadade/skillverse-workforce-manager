package com.skillverse.admin.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.MasterSkill;
import com.skillverse.CommonFeatures.User;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;
import java.util.stream.Collectors;

public class AdminSkillsView extends VBox {

    private final TextField searchField;
    private final ComboBox<String> categoryCombo;
    private final Label badgeSkillCount;
    private final FlowPane cardsGrid;
    private List<MasterSkill> allSkills = new ArrayList<>();
    private List<User> allUsers = new ArrayList<>();

    public AdminSkillsView() {
        this(null);
    }

    public AdminSkillsView(javafx.stage.Stage stage) {
        setSpacing(24);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("Skills Management 🎯");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Manage standardized organizational taxonomy, skill proficiencies, and verification matrix.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        badgeSkillCount = new Label("Loading...");
        badgeSkillCount.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1D4ED8; -fx-padding: 6 14; -fx-background-radius: 12; -fx-font-weight: bold; -fx-font-size: 13px;");

        Button addSkillBtn = new Button("+ Add Skill");
        addSkillBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;");
        addSkillBtn.setOnAction(e -> handleAddSkillDialog());

        headerBar.getChildren().addAll(titleBox, badgeSkillCount, addSkillBtn);

        HBox filterBar = new HBox(14);
        filterBar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("🔍 Search skills by name or keyword...");
        searchField.setPrefHeight(40);
        searchField.setPrefWidth(320);
        searchField.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 8; -fx-padding: 0 12;");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterAndRenderCards());

        categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll("All Categories", "Technical", "Programming", "Database", "Cloud & DevOps", "Soft Skills", "Management", "Testing");
        categoryCombo.setValue("All Categories");
        categoryCombo.setPrefHeight(40);
        categoryCombo.setPrefWidth(200);
        categoryCombo.setOnAction(e -> filterAndRenderCards());

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-text-fill: #334155; -fx-font-weight: bold; -fx-padding: 10 16; -fx-background-radius: 8; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadSkillsAndPersonnel());

        filterBar.getChildren().addAll(searchField, categoryCombo, refreshBtn);

        cardsGrid = new FlowPane();
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);
        cardsGrid.setPrefWrapLength(1080);

        ScrollPane scrollPane = new ScrollPane(cardsGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().addAll(headerBar, filterBar, scrollPane);

        loadSkillsAndPersonnel();
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }

    public void loadSkillsAndPersonnel() {
        FirebaseDAO.getInstance().getAllMasterSkills().thenAccept(skills -> {
            FirebaseDAO.getInstance().getAllEmployees().thenAccept(users -> {
                Platform.runLater(() -> {
                    this.allSkills = skills != null ? skills : new ArrayList<>();
                    this.allUsers = users != null ? users : new ArrayList<>();
                    badgeSkillCount.setText(allSkills.size() + " Skills Available");
                    filterAndRenderCards();
                });
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private void filterAndRenderCards() {
        cardsGrid.getChildren().clear();

        String query = searchField.getText() != null ? searchField.getText().toLowerCase().trim() : "";
        String selectedCategory = categoryCombo.getValue();

        List<MasterSkill> filtered = allSkills.stream().filter(skill -> {
            boolean matchesQuery = query.isEmpty() ||
                    (skill.getSkillName() != null && skill.getSkillName().toLowerCase().contains(query)) ||
                    (skill.getDescription() != null && skill.getDescription().toLowerCase().contains(query));

            boolean matchesCategory = selectedCategory == null || "All Categories".equals(selectedCategory) ||
                    (skill.getCategory() != null && skill.getCategory().equalsIgnoreCase(selectedCategory));

            return matchesQuery && matchesCategory;
        }).collect(Collectors.toList());

        if (filtered.isEmpty()) {
            renderEmptyState(allSkills.isEmpty() 
                ? "No cataloged skills found. Click '+ Add Skill' to create standard enterprise taxonomy." 
                : "No skills matched your search criteria.");
            return;
        }

        for (MasterSkill s : filtered) {
            cardsGrid.getChildren().add(createSkillCard(s));
        }
    }

    private VBox createSkillCard(MasterSkill skill) {
        VBox card = new VBox(12);
        card.setPrefWidth(320);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label iconLbl = new Label(getCategoryIcon(skill.getCategory()));
        iconLbl.setStyle("-fx-font-size: 18px; -fx-background-color: #F1F5F9; -fx-padding: 8 12; -fx-background-radius: 8;");

        VBox titleBox = new VBox(2);
        Label titleLbl = new Label(skill.getSkillName() != null ? skill.getSkillName() : "Unnamed Skill");
        titleLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        Label catBadge = new Label(skill.getCategory() != null ? skill.getCategory() : "General");
        catBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-padding: 2 8; -fx-background-radius: 4; -fx-font-size: 10px; -fx-font-weight: bold;");
        titleBox.getChildren().addAll(titleLbl, catBadge);

        topRow.getChildren().addAll(iconLbl, titleBox);

        Label descLbl = new Label(skill.getDescription() != null ? skill.getDescription() : "Standard enterprise competency.");
        descLbl.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");
        descLbl.setWrapText(true);
        descLbl.setPrefHeight(36);

        HBox bottomRow = new HBox(12);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        long verifiedCount = allUsers.stream().filter(u -> 
            u.getSkills() != null && u.getSkills().stream().anyMatch(sk -> sk.equalsIgnoreCase(skill.getSkillName()))
        ).count();

        Label countLbl = new Label("👥 " + verifiedCount + " Verified");
        countLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #059669;");
        HBox.setHgrow(countLbl, Priority.ALWAYS);

        Button viewTeamBtn = new Button("View Talent →");
        viewTeamBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 12px;");
        viewTeamBtn.setOnAction(e -> {
            if (getScene() != null && getScene().getRoot() != null) {
                javafx.scene.Node contentArea = getScene().getRoot().lookup("#contentArea");
                if (contentArea instanceof Pane) {
                    AdminEmployeesView empView = new AdminEmployeesView();
                    empView.filterByDepartment(skill.getSkillName());
                    ((Pane) contentArea).getChildren().setAll(empView);
                    return;
                }
            }

            javafx.scene.Node p = getParent();
            while (p != null) {
                if (p instanceof BorderPane) {
                    AdminEmployeesView empView = new AdminEmployeesView();
                    empView.filterByDepartment(skill.getSkillName());
                    ((BorderPane) p).setCenter(empView);
                    break;
                } else if (p instanceof Pane && !(p instanceof ScrollPane) && !p.getClass().getName().contains("ScrollPaneSkin")) {
                    AdminEmployeesView empView = new AdminEmployeesView();
                    empView.filterByDepartment(skill.getSkillName());
                    ((Pane) p).getChildren().setAll(empView);
                    break;
                }
                p = p.getParent();
            }
        });

        bottomRow.getChildren().addAll(countLbl, viewTeamBtn);
        card.getChildren().addAll(topRow, descLbl, bottomRow);
        return card;
    }

    private String getCategoryIcon(String cat) {
        if (cat == null) return "🎯";
        String lower = cat.toLowerCase();
        if (lower.contains("program") || lower.contains("tech")) return "💻";
        if (lower.contains("data")) return "🗄️";
        if (lower.contains("cloud") || lower.contains("devops")) return "☁️";
        if (lower.contains("soft") || lower.contains("comm")) return "🗣️";
        if (lower.contains("lead") || lower.contains("manage")) return "👑";
        if (lower.contains("test")) return "✓";
        return "🎯";
    }

    private void renderEmptyState(String message) {
        VBox emptyBox = new VBox(10);
        emptyBox.setAlignment(Pos.CENTER);
        emptyBox.setPadding(new Insets(60));
        emptyBox.setPrefWidth(900);

        Label icon = new Label("🎯");
        icon.setStyle("-fx-font-size: 36px;");

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 14px;");

        emptyBox.getChildren().addAll(icon, label);
        cardsGrid.getChildren().add(emptyBox);
    }

    private void handleAddSkillDialog() {
        Dialog<MasterSkill> dialog = new Dialog<>();
        dialog.setTitle("Add Master Skill");
        dialog.setHeaderText("Create Standardized Enterprise Skill");

        ButtonType saveBtnType = new ButtonType("Save Skill", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtnType, ButtonType.CANCEL);

        VBox form = new VBox(12);
        form.setPadding(new Insets(20));

        TextField nameInput = new TextField();
        nameInput.setPromptText("Skill Name (e.g. Spring Boot, Cloud Security)");

        ComboBox<String> catInput = new ComboBox<>();
        catInput.getItems().addAll("Technical", "Programming", "Database", "Cloud & DevOps", "Soft Skills", "Management", "Testing");
        catInput.setValue("Technical");

        TextArea descInput = new TextArea();
        descInput.setPromptText("Brief description or scope of competency...");
        descInput.setPrefRowCount(3);

        form.getChildren().addAll(new Label("Skill Name:"), nameInput, new Label("Category:"), catInput, new Label("Description:"), descInput);
        dialog.getDialogPane().setContent(form);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveBtnType) {
                if (!nameInput.getText().trim().isEmpty()) {
                    MasterSkill s = new MasterSkill();
                    s.setSkillName(nameInput.getText().trim());
                    s.setCategory(catInput.getValue());
                    s.setDescription(descInput.getText().trim());
                    return s;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newSkill -> {
            FirebaseDAO.getInstance().createMasterSkill(newSkill).thenRun(() -> {
                Platform.runLater(this::loadSkillsAndPersonnel);
            });
        });
    }
}
