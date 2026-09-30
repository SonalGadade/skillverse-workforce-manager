package com.skillverse.admin.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.JobPosting;
import com.skillverse.CommonFeatures.User;
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

import java.util.*;
import java.util.stream.Collectors;

public class AdminTalentMatchingView extends VBox {

    private final Stage stage;
    private final Label totalEmployeesLbl;
    private final Label openJobsLbl;
    private final Label matchesFoundLbl;
    private final Label highMatchesLbl;

    private final ComboBox<JobItem> jobComboBox;
    private final FlowPane requiredSkillsPane;
    private final ComboBox<String> expFilterCombo;
    private final VBox candidatesContainer;

    private List<User> allEmployees = new ArrayList<>();
    private List<JobPosting> allJobs = new ArrayList<>();

    public static class JobItem {
        private final JobPosting job;

        public JobItem(JobPosting job) {
            this.job = job;
        }

        public JobPosting getJob() { return job; }

        @Override
        public String toString() {
            return (job.getTitle() != null ? job.getTitle() : "Untitled Position") + " (" + (job.getDepartment() != null ? job.getDepartment() : "General") + ")";
        }
    }

    public static class CandidateMatch {
        public User user;
        public int matchPercentage;
        public int matchedSkillsCount;
        public int totalRequiredSkills;

        public CandidateMatch(User user, int matchPercentage, int matchedSkillsCount, int totalRequiredSkills) {
            this.user = user;
            this.matchPercentage = matchPercentage;
            this.matchedSkillsCount = matchedSkillsCount;
            this.totalRequiredSkills = totalRequiredSkills;
        }
    }

    public AdminTalentMatchingView() {
        this(null);
    }

    public AdminTalentMatchingView(Stage stage) {
        this.stage = stage;
        setSpacing(24);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("AI Talent Matching 🔮");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Find the optimal internal employees for open requisitions using semantic skill matching.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        Button matchBtn = new Button("⚡ Match All Candidates");
        matchBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        matchBtn.setOnAction(e -> runMatchingAlgorithm());

        headerBar.getChildren().addAll(titleBox, matchBtn);

        HBox statsRow = new HBox(16);
        statsRow.setAlignment(Pos.CENTER_LEFT);

        VBox card1 = createStatCard("Total Employees", totalEmployeesLbl = new Label("0"), "#2563EB");
        VBox card2 = createStatCard("Open Jobs", openJobsLbl = new Label("0"), "#7C3AED");
        VBox card3 = createStatCard("Matches Found", matchesFoundLbl = new Label("0"), "#059669");
        VBox card4 = createStatCard("High Matches (>75%)", highMatchesLbl = new Label("0"), "#D97706");

        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);
        HBox.setHgrow(card3, Priority.ALWAYS);
        HBox.setHgrow(card4, Priority.ALWAYS);
        statsRow.getChildren().addAll(card1, card2, card3, card4);

        VBox matchParamsCard = new VBox(16);
        matchParamsCard.setPadding(new Insets(20));
        matchParamsCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label cardHeader = new Label("Match Candidates for Requisition");
        cardHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        VBox jobRoleBox = new VBox(6);
        Label jobLbl = new Label("TARGET JOB ROLE");
        jobLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");
        jobComboBox = new ComboBox<>();
        jobComboBox.setPromptText("Select open job requisition");
        jobComboBox.setMaxWidth(Double.MAX_VALUE);
        jobComboBox.setOnAction(e -> updateRequiredSkillsDisplay());
        jobRoleBox.getChildren().addAll(jobLbl, jobComboBox);

        VBox skillsBox = new VBox(6);
        Label skillsLbl = new Label("REQUIRED SKILLS PROFILE");
        skillsLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");
        requiredSkillsPane = new FlowPane();
        requiredSkillsPane.setHgap(8);
        requiredSkillsPane.setVgap(8);
        skillsBox.getChildren().addAll(skillsLbl, requiredSkillsPane);

        HBox rowExp = new HBox(16);
        rowExp.setAlignment(Pos.BOTTOM_LEFT);

        VBox expBox = new VBox(6);
        Label expLbl = new Label("EXPERIENCE CRITERIA");
        expLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");
        expFilterCombo = new ComboBox<>();
        expFilterCombo.getItems().addAll("Any Experience", "1-3 Years", "2-5 Years", "5+ Years");
        expFilterCombo.setValue("Any Experience");
        expFilterCombo.setPrefWidth(220);
        expBox.getChildren().addAll(expLbl, expFilterCombo);

        Button findMatchesBtn = new Button("✦ Find Best Matches");
        findMatchesBtn.setStyle("-fx-background-color: #4F46E5; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");
        findMatchesBtn.setOnAction(e -> runMatchingAlgorithm());

        rowExp.getChildren().addAll(expBox, findMatchesBtn);
        matchParamsCard.getChildren().addAll(cardHeader, jobRoleBox, skillsBox, rowExp);

        VBox resultsSection = new VBox(14);
        Label resultsTitle = new Label("Best Matching Internal Candidates");
        resultsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        candidatesContainer = new VBox(14);
        ScrollPane scrollPane = new ScrollPane(candidatesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        resultsSection.getChildren().addAll(resultsTitle, scrollPane);

        getChildren().addAll(headerBar, statsRow, matchParamsCard, resultsSection);

        loadInitialData();
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

    public void loadInitialData() {
        FirebaseDAO.getInstance().getAllEmployees().thenAccept(users -> {
            FirebaseDAO.getInstance().getAllJobPostings().thenAccept(jobs -> {
                Platform.runLater(() -> {
                    this.allEmployees = users != null ? users : new ArrayList<>();
                    this.allJobs = jobs != null ? jobs : new ArrayList<>();

                    totalEmployeesLbl.setText(String.valueOf(allEmployees.size()));
                    long activeJobsCount = allJobs.stream().filter(j -> "ACTIVE".equalsIgnoreCase(j.getStatus())).count();
                    openJobsLbl.setText(String.valueOf(activeJobsCount > 0 ? activeJobsCount : allJobs.size()));

                    jobComboBox.getItems().clear();
                    for (JobPosting jp : allJobs) {
                        jobComboBox.getItems().add(new JobItem(jp));
                    }
                    if (!jobComboBox.getItems().isEmpty()) {
                        jobComboBox.getSelectionModel().select(0);
                        updateRequiredSkillsDisplay();
                        runMatchingAlgorithm();
                    }
                });
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private void updateRequiredSkillsDisplay() {
        requiredSkillsPane.getChildren().clear();
        JobItem selected = jobComboBox.getValue();
        if (selected == null || selected.getJob() == null) return;

        List<String> reqSkills = selected.getJob().getRequiredSkills();
        if (reqSkills == null || reqSkills.isEmpty()) {
            if (selected.getJob().getSkillsRequired() != null && !selected.getJob().getSkillsRequired().isBlank()) {
                reqSkills = Arrays.asList(selected.getJob().getSkillsRequired().split(","));
            } else {
                reqSkills = Arrays.asList("Java", "Spring Boot", "SQL");
            }
        }

        for (String skill : reqSkills) {
            Label badge = new Label(skill.trim());
            badge.setStyle("-fx-background-color: #EEF2FF; -fx-text-fill: #4F46E5; -fx-padding: 4 10; -fx-background-radius: 6; -fx-font-weight: bold; -fx-font-size: 12px;");
            requiredSkillsPane.getChildren().add(badge);
        }
    }

    private void runMatchingAlgorithm() {
        candidatesContainer.getChildren().clear();
        JobItem selectedJobItem = jobComboBox.getValue();

        if (selectedJobItem == null || selectedJobItem.getJob() == null) {
            renderEmptyCandidates("Please select a target job opening to compute candidate matches.");
            return;
        }

        JobPosting job = selectedJobItem.getJob();
        List<String> reqSkills = job.getRequiredSkills();
        if (reqSkills == null || reqSkills.isEmpty()) {
            if (job.getSkillsRequired() != null && !job.getSkillsRequired().isBlank()) {
                reqSkills = Arrays.asList(job.getSkillsRequired().split(","));
            } else {
                reqSkills = Collections.emptyList();
            }
        }

        List<CandidateMatch> matches = new ArrayList<>();

        for (User u : allEmployees) {
            List<String> empSkills = u.getSkills() != null ? u.getSkills() : Collections.emptyList();
            int matchedCount = 0;
            for (String req : reqSkills) {
                if (empSkills.stream().anyMatch(es -> es.trim().equalsIgnoreCase(req.trim()))) {
                    matchedCount++;
                }
            }

            int score = 40;
            if (!reqSkills.isEmpty()) {
                double skillRatio = (double) matchedCount / reqSkills.size();
                score = (int) Math.round((skillRatio * 60.0) + 40.0);
            }
            if (u.getDepartment() != null && job.getDepartment() != null && u.getDepartment().equalsIgnoreCase(job.getDepartment())) {
                score = Math.min(100, score + 10);
            }

            matches.add(new CandidateMatch(u, score, matchedCount, reqSkills.size()));
        }

        matches.sort((a, b) -> Integer.compare(b.matchPercentage, a.matchPercentage));

        matchesFoundLbl.setText(String.valueOf(matches.size()));
        long highCount = matches.stream().filter(m -> m.matchPercentage >= 75).count();
        highMatchesLbl.setText(String.valueOf(highCount));

        if (matches.isEmpty()) {
            renderEmptyCandidates("No candidate profiles available in the organization.");
            return;
        }

        for (CandidateMatch cm : matches) {
            candidatesContainer.getChildren().add(createCandidateCard(cm, job));
        }
    }

    private VBox createCandidateCard(CandidateMatch cm, JobPosting job) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label avatar = new Label(cm.user.getName() != null && cm.user.getName().length() >= 2 ? cm.user.getName().substring(0, 2).toUpperCase() : "EM");
        avatar.setStyle("-fx-background-color: #EEF2FF; -fx-text-fill: #4F46E5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 10; -fx-background-radius: 8;");

        VBox nameBox = new VBox(2);
        Label nameLbl = new Label(cm.user.getName() != null ? cm.user.getName() : cm.user.getEmail());
        nameLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label roleLbl = new Label((cm.user.getRole() != null ? cm.user.getRole() : "Member") + " • " + (cm.user.getDepartment() != null ? cm.user.getDepartment() : "General"));
        roleLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");
        nameBox.getChildren().addAll(nameLbl, roleLbl);
        HBox.setHgrow(nameBox, Priority.ALWAYS);

        VBox scoreBox = new VBox(2);
        scoreBox.setAlignment(Pos.CENTER_RIGHT);
        Label scoreTitle = new Label("AI Match Score");
        scoreTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #94A3B8;");
        Label scoreVal = new Label(cm.matchPercentage + "%");
        scoreVal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + (cm.matchPercentage >= 75 ? "#059669;" : "#D97706;"));
        scoreBox.getChildren().addAll(scoreTitle, scoreVal);

        topRow.getChildren().addAll(avatar, nameBox, scoreBox);

        ProgressBar pBar = new ProgressBar(cm.matchPercentage / 100.0);
        pBar.setMaxWidth(Double.MAX_VALUE);
        pBar.setStyle("-fx-accent: " + (cm.matchPercentage >= 75 ? "#10B981;" : "#6366F1;"));

        HBox bottomRow = new HBox(14);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        Label breakdownLbl = new Label("Skills Matched: " + cm.matchedSkillsCount + " / " + cm.totalRequiredSkills + " Required");
        breakdownLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B; -fx-font-weight: 500;");
        HBox.setHgrow(breakdownLbl, Priority.ALWAYS);

        Button shortlistBtn = new Button("Shortlist Candidate");
        shortlistBtn.setStyle("-fx-background-color: #ECFDF5; -fx-border-color: #A7F3D0; -fx-text-fill: #065F46; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 6 16; -fx-background-radius: 6; -fx-cursor: hand;");
        shortlistBtn.setOnAction(e -> {
            String jobId = job.getId() != null ? job.getId() : job.getJobId();
            FirebaseDAO.getInstance().saveCandidateShortlist(cm.user.getEmail(), jobId).thenRun(() -> {
                showModernToast("Candidate Shortlisted! 🎉", cm.user.getName() + " was successfully matched and shortlisted for " + job.getTitle(), true);
            });
        });

        bottomRow.getChildren().addAll(breakdownLbl, shortlistBtn);
        card.getChildren().addAll(topRow, pBar, bottomRow);
        return card;
    }

    private void renderEmptyCandidates(String msg) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(60));
        Label l = new Label(msg);
        l.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 14px;");
        box.getChildren().add(l);
        candidatesContainer.getChildren().add(box);
    }

    private void showModernToast(String title, String message, boolean isSuccess) {
        Platform.runLater(() -> {
            HBox toast = new HBox(12);
            toast.setAlignment(Pos.CENTER_LEFT);
            toast.setPadding(new Insets(14, 20, 14, 20));
            toast.setMaxWidth(480);

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

            new SequentialTransition(showAnim, delay, fadeOut).play();
        });
    }
}
