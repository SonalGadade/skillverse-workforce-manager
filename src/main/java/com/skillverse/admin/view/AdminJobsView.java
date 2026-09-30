package com.skillverse.admin.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.JobPosting;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AdminJobsView extends VBox {

    private final Stage stage;
    private final Label totalJobsLbl;
    private final Label activeJobsLbl;
    private final Label totalApplicantsLbl;
    private final Label expiringJobsLbl;

    private final TextField searchField;
    private final ComboBox<String> statusFilterCombo;
    private final VBox jobsListContainer;
    private final Label positionsCountBadge;

    private final VBox mainJobsLayout;
    private final VBox createJobFormLayout;

    private List<JobPosting> masterJobsList = new ArrayList<>();

    public AdminJobsView() {
        this(null);
    }

    public AdminJobsView(Stage stage) {
        this.stage = stage;
        setSpacing(0);
        setStyle("-fx-background-color: #F8FAFC;");

        
        mainJobsLayout = new VBox(24);
        mainJobsLayout.setPadding(new Insets(24, 32, 24, 32));

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("Jobs & Roles 💼");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Manage organization job openings, skill profiles, and track candidate applications.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        Button addJobBtn = new Button("+ Add New Job");
        addJobBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        addJobBtn.setOnAction(e -> showCreateJobFormPage());

        headerBar.getChildren().addAll(titleBox, addJobBtn);

        HBox statsRow = new HBox(16);
        statsRow.setAlignment(Pos.CENTER_LEFT);

        VBox card1 = createStatCard("Total Jobs", totalJobsLbl = new Label("0"), "#2563EB");
        VBox card2 = createStatCard("Active Jobs", activeJobsLbl = new Label("0"), "#059669");
        VBox card3 = createStatCard("Total Applicants", totalApplicantsLbl = new Label("0"), "#D97706");
        VBox card4 = createStatCard("Expiring Soon", expiringJobsLbl = new Label("0"), "#DC2626");

        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);
        HBox.setHgrow(card3, Priority.ALWAYS);
        HBox.setHgrow(card4, Priority.ALWAYS);
        statsRow.getChildren().addAll(card1, card2, card3, card4);

        HBox filterBar = new HBox(14);
        filterBar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("🔍 Search jobs by title, department, or required skill...");
        searchField.setPrefHeight(40);
        searchField.setPrefWidth(380);
        searchField.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 8; -fx-padding: 0 12;");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterAndRenderJobs());

        statusFilterCombo = new ComboBox<>();
        statusFilterCombo.getItems().addAll("All Jobs", "ACTIVE", "CLOSED");
        statusFilterCombo.setValue("All Jobs");
        statusFilterCombo.setPrefHeight(40);
        statusFilterCombo.setPrefWidth(160);
        statusFilterCombo.setOnAction(e -> filterAndRenderJobs());

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-text-fill: #334155; -fx-font-weight: bold; -fx-padding: 10 16; -fx-background-radius: 8; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadJobsFromFirestore());

        filterBar.getChildren().addAll(searchField, statusFilterCombo, refreshBtn);

        VBox listSection = new VBox(14);
        HBox listHeader = new HBox(12);
        listHeader.setAlignment(Pos.CENTER_LEFT);

        Label listTitle = new Label("Available Jobs");
        listTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        HBox.setHgrow(listTitle, Priority.ALWAYS);

        positionsCountBadge = new Label("0 positions");
        positionsCountBadge.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B; -fx-font-weight: 500;");
        listHeader.getChildren().addAll(listTitle, positionsCountBadge);

        jobsListContainer = new VBox(14);
        ScrollPane scrollPane = new ScrollPane(jobsListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        listSection.getChildren().addAll(listHeader, scrollPane);
        mainJobsLayout.getChildren().addAll(headerBar, statsRow, filterBar, listSection);

        createJobFormLayout = buildCreateJobFormLayout();

        getChildren().add(mainJobsLayout);

        loadJobsFromFirestore();
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private VBox createStatCard(String title, Label valLbl, String accentColor) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        valLbl.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");

        card.getChildren().addAll(valLbl, titleLbl);
        return card;
    }

    public void loadJobsFromFirestore() {
        FirebaseDAO.getInstance().getAllJobPostings().thenAccept(jobs -> {
            Platform.runLater(() -> {
                this.masterJobsList = jobs != null ? jobs : new ArrayList<>();

                int total = masterJobsList.size();
                long active = masterJobsList.stream().filter(j -> "ACTIVE".equalsIgnoreCase(j.getStatus())).count();
                long totalApplicants = masterJobsList.stream().mapToLong(JobPosting::getApplicantsCount).sum();

                totalJobsLbl.setText(String.valueOf(total));
                activeJobsLbl.setText(String.valueOf(active));
                totalApplicantsLbl.setText(String.valueOf(totalApplicants));
                expiringJobsLbl.setText("0");

                filterAndRenderJobs();
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private void filterAndRenderJobs() {
        jobsListContainer.getChildren().clear();

        String query = searchField.getText() != null ? searchField.getText().toLowerCase().trim() : "";
        String status = statusFilterCombo.getValue();

        List<JobPosting> filtered = masterJobsList.stream().filter(job -> {
            boolean matchesQuery = query.isEmpty() ||
                    (job.getTitle() != null && job.getTitle().toLowerCase().contains(query)) ||
                    (job.getDepartment() != null && job.getDepartment().toLowerCase().contains(query)) ||
                    (job.getRequiredSkills() != null && job.getRequiredSkills().stream().anyMatch(s -> s.toLowerCase().contains(query)));

            boolean matchesStatus = status == null || "All Jobs".equalsIgnoreCase(status) ||
                    (job.getStatus() != null && job.getStatus().equalsIgnoreCase(status));

            return matchesQuery && matchesStatus;
        }).collect(Collectors.toList());

        positionsCountBadge.setText(filtered.size() + " positions");

        if (filtered.isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(60));
            Label emptyLbl = new Label(masterJobsList.isEmpty() 
                ? "No job openings found in Firestore. Click '+ Add New Job' to publish a role." 
                : "No job postings matched your search criteria.");
            emptyLbl.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 14px;");
            emptyBox.getChildren().add(emptyLbl);
            jobsListContainer.getChildren().add(emptyBox);
            return;
        }

        for (JobPosting job : filtered) {
            jobsListContainer.getChildren().add(createJobCard(job));
        }
    }

    private VBox createJobCard(JobPosting job) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox topRow = new HBox(14);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label iconLbl = new Label("💼");
        iconLbl.setStyle("-fx-font-size: 20px; -fx-background-color: #F1F5F9; -fx-padding: 8 12; -fx-background-radius: 8;");

        VBox titleBox = new VBox(2);
        Label titleLbl = new Label(job.getTitle() != null ? job.getTitle() : "Job Position");
        titleLbl.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label deptLbl = new Label(job.getDepartment() != null ? job.getDepartment() : "General Business");
        deptLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLbl, deptLbl);
        HBox.setHgrow(titleBox, Priority.ALWAYS);

        boolean isActive = "ACTIVE".equalsIgnoreCase(job.getStatus());
        Label statusBadge = new Label(isActive ? "ACTIVE" : "CLOSED");
        statusBadge.setStyle(isActive
                ? "-fx-background-color: #DCFCE7; -fx-text-fill: #166534; -fx-padding: 4 10; -fx-background-radius: 6; -fx-font-weight: bold; -fx-font-size: 11px;"
                : "-fx-background-color: #FEE2E2; -fx-text-fill: #991B1B; -fx-padding: 4 10; -fx-background-radius: 6; -fx-font-weight: bold; -fx-font-size: 11px;");

        topRow.getChildren().addAll(iconLbl, titleBox, statusBadge);

        HBox metaRow = new HBox(28);
        metaRow.setAlignment(Pos.CENTER_LEFT);

        metaRow.getChildren().addAll(
                createMetaItem("LOCATION", job.getLocation() != null ? job.getLocation() : "Pune / Hybrid"),
                createMetaItem("JOB TYPE", job.getJobType() != null ? job.getJobType() : "Full Time"),
                createMetaItem("EXPERIENCE", job.getExperience() != null ? job.getExperience() : "1 - 3 Years"),
                createMetaItem("SALARY", job.getSalaryRange() != null ? job.getSalaryRange() : "Competitive"),
                createMetaItem("APPLICANTS", String.valueOf(job.getApplicantsCount()))
        );

        HBox bottomRow = new HBox(12);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        HBox skillsBox = new HBox(6);
        skillsBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(skillsBox, Priority.ALWAYS);

        if (job.getRequiredSkills() != null) {
            for (String s : job.getRequiredSkills()) {
                Label skBadge = new Label(s);
                skBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-padding: 3 8; -fx-background-radius: 4; -fx-font-size: 11px; -fx-font-weight: bold;");
                skillsBox.getChildren().add(skBadge);
            }
        }

        Button deleteBtn = new Button("Delete");
        deleteBtn.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-size: 12px; -fx-cursor: hand; -fx-background-radius: 6; -fx-padding: 6 12;");
        deleteBtn.setOnAction(e -> {
            String targetId = job.getId() != null ? job.getId() : job.getJobId();
            FirebaseDAO.getInstance().deleteJobPosting(targetId).thenRun(() -> {
                Platform.runLater(this::loadJobsFromFirestore);
            });
        });

        bottomRow.getChildren().addAll(skillsBox, deleteBtn);
        card.getChildren().addAll(topRow, metaRow, bottomRow);
        return card;
    }

    private VBox createMetaItem(String key, String val) {
        VBox box = new VBox(2);
        Label k = new Label(key);
        k.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #94A3B8;");
        Label v = new Label(val);
        v.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #334155;");
        box.getChildren().addAll(k, v);
        return box;
    }

 

    private VBox buildCreateJobFormLayout() {
        VBox container = new VBox(24);
        container.setPadding(new Insets(24, 32, 24, 32));

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("← Back to Jobs");
        backBtn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #334155; -fx-border-color: #CBD5E1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold; -fx-padding: 8 16; -fx-cursor: hand;");
        backBtn.setOnAction(e -> showMainJobsView());

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("Create Job Requisition 💼");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Publish an enterprise position opening with skill profiles and compensation terms.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        headerBar.getChildren().addAll(backBtn, titleBox);

        VBox formCard = new VBox(20);
        formCard.setPadding(new Insets(28));
        formCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 12, 0, 0, 4);");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(18);

        TextField titleInput = new TextField();
        titleInput.setPromptText("e.g. Senior Java Backend Lead");
        titleInput.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #CBD5E1; -fx-padding: 10 12;");

        ComboBox<String> deptInput = new ComboBox<>();
        deptInput.getItems().addAll("Engineering", "HR", "Marketing", "Finance", "Design", "Quality & Testing");
        deptInput.setValue("Engineering");
        deptInput.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #CBD5E1; -fx-padding: 8 12;");
        deptInput.setPrefWidth(300);

        TextField locationInput = new TextField();
        locationInput.setPromptText("e.g. Pune / Hybrid / Remote");
        locationInput.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #CBD5E1; -fx-padding: 10 12;");

        ComboBox<String> typeInput = new ComboBox<>();
        typeInput.getItems().addAll("Full Time", "Part Time", "Contract", "Remote");
        typeInput.setValue("Full Time");
        typeInput.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #CBD5E1; -fx-padding: 8 12;");
        typeInput.setPrefWidth(300);

        TextField expInput = new TextField();
        expInput.setPromptText("e.g. 2 - 4 Years");
        expInput.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #CBD5E1; -fx-padding: 10 12;");

        TextField salaryInput = new TextField();
        salaryInput.setPromptText("e.g. ₹8 - 14 LPA");
        salaryInput.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #CBD5E1; -fx-padding: 10 12;");

        TextField skillsInput = new TextField();
        skillsInput.setPromptText("e.g. Java, Spring Boot, SQL, Microservices");
        skillsInput.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #CBD5E1; -fx-padding: 10 12;");

        TextArea descInput = new TextArea();
        descInput.setPromptText("Enter detailed job responsibilities and candidate qualifications...");
        descInput.setPrefRowCount(4);
        descInput.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #CBD5E1; -fx-padding: 8;");

        
        formGrid.add(createFormLabel("Job Title *"), 0, 0);
        formGrid.add(titleInput, 0, 1);
        formGrid.add(createFormLabel("Department *"), 1, 0);
        formGrid.add(deptInput, 1, 1);

        formGrid.add(createFormLabel("Location"), 0, 2);
        formGrid.add(locationInput, 0, 3);
        formGrid.add(createFormLabel("Job Type"), 1, 2);
        formGrid.add(typeInput, 1, 3);

        formGrid.add(createFormLabel("Required Experience"), 0, 4);
        formGrid.add(expInput, 0, 5);
        formGrid.add(createFormLabel("Salary Range"), 1, 4);
        formGrid.add(salaryInput, 1, 5);

        formGrid.add(createFormLabel("Required Skills (Comma Separated) *"), 0, 6, 2, 1);
        formGrid.add(skillsInput, 0, 7, 2, 1);

        formGrid.add(createFormLabel("Job Description"), 0, 8, 2, 1);
        formGrid.add(descInput, 0, 9, 2, 1);

        HBox actionsRow = new HBox(14);
        actionsRow.setAlignment(Pos.CENTER_RIGHT);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> showMainJobsView());

        Button publishBtn = new Button("🚀 Publish Job Requisition");
        publishBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");
        publishBtn.setOnAction(e -> {
            if (titleInput.getText().trim().isEmpty()) {
                showModernToast("Validation Error ⚠️", "Please enter a Job Title.", false);
                return;
            }

            JobPosting jp = new JobPosting();
            String newId = "job_" + System.currentTimeMillis();
            jp.setId(newId);
            jp.setJobId(newId);
            jp.setTitle(titleInput.getText().trim());
            jp.setDepartment(deptInput.getValue());
            jp.setLocation(locationInput.getText().trim().isEmpty() ? "Pune / Remote" : locationInput.getText().trim());
            jp.setJobType(typeInput.getValue());
            jp.setExperience(expInput.getText().trim().isEmpty() ? "1 - 3 Years" : expInput.getText().trim());
            jp.setSalaryRange(salaryInput.getText().trim().isEmpty() ? "Competitive LPA" : salaryInput.getText().trim());
            jp.setDescription(descInput.getText().trim());
            jp.setStatus("ACTIVE");
            jp.setApplicantsCount(0);
            jp.setCreatedAt(LocalDate.now().toString());

            List<String> skills = Arrays.stream(skillsInput.getText().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            if (skills.isEmpty()) {
                skills = Arrays.asList("Java", "Problem Solving");
            }
            jp.setRequiredSkills(skills);

            FirebaseDAO.getInstance().createJobPosting(jp).thenRun(() -> {
                Platform.runLater(() -> {
                    titleInput.clear();
                    locationInput.clear();
                    expInput.clear();
                    salaryInput.clear();
                    skillsInput.clear();
                    descInput.clear();

                    showMainJobsView();
                    loadJobsFromFirestore();
                    showModernToast("Job Requisition Published 💼", "Position '" + jp.getTitle() + "' published successfully to Firestore.", true);
                });
            });
        });

        actionsRow.getChildren().addAll(cancelBtn, publishBtn);

        formCard.getChildren().addAll(formGrid, new Separator(), actionsRow);
        container.getChildren().addAll(headerBar, formCard);
        return container;
    }

    private Label createFormLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #334155;");
        return lbl;
    }

    private void showCreateJobFormPage() {
        getChildren().setAll(createJobFormLayout);
    }

    private void showMainJobsView() {
        getChildren().setAll(mainJobsLayout);
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
