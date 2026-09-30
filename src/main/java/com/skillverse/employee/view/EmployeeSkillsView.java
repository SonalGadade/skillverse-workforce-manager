package com.skillverse.employee.view;

import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.EmployeeSkill;
import com.skillverse.CommonFeatures.TrainingProgram;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EmployeeSkillsView {

    private static final String BLUE = "#2563EB";
    private static final String INDIGO = "#4F46E5";
    private static final String PURPLE = "#7C3AED";
    private static final String GREEN = "#10B981";
    private static final String AMBER = "#D97706";
    private static final String RED = "#DC2626";
    private static final String TEXT = "#0F172A";
    private static final String MUTED = "#64748B";
    private static final String BG = "#F8FAFC";
    private static final String BORDER = "#E2E8F0";

    private int activeTab = 1; // 1: My Skills, 2: Skill Gaps, 3: Recommended Training

    public VBox createSkillsContent(String email) {
        return createSkillsContent(email, null);
    }

    public VBox createSkillsContent(String email, Consumer<String> onNavigateToLearning) {
        String userEmail = (UserSession.getCurrentUser() != null && UserSession.getCurrentUser().getEmail() != null && !UserSession.getCurrentUser().getEmail().isBlank())
                ? UserSession.getCurrentUser().getEmail().toLowerCase().trim()
                : (email != null ? email.toLowerCase().trim() : "");

        VBox page = new VBox(24);
        page.setPadding(new Insets(32, 40, 40, 40));
        page.setStyle("-fx-background-color: " + BG + ";");

        // Header
        HBox headerRow = new HBox(20);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        Text title = new Text("My Skills & Capability Hub");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setFill(Color.web(TEXT));

        Text subtitle = new Text("Track verified competencies, analyze skill gaps, and explore targeted upskilling paths.");
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setFill(Color.web(MUTED));

        titleBox.getChildren().addAll(title, subtitle);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button syncBtn = new Button("🔄 Refresh Skills Matrix");
        syncBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 16px;");

        headerRow.getChildren().addAll(titleBox, sp, syncBtn);

        // Tab Selector Cards Bar
        Label verifiedBadge = new Label("0 Verified");
        Label gapBadge = new Label("0 Gaps");
        Label coursesBadge = new Label("0 Courses");

        VBox card1 = createTabCard(1, "🌟", "My Skills", "Verified Competencies", verifiedBadge);
        VBox card2 = createTabCard(2, "🎯", "Skill Gap Analysis", "Capability Target Shift", gapBadge);
        VBox card3 = createTabCard(3, "🚀", "Recommended Training", "Targeted Learning Paths", coursesBadge);

        HBox tabRow = new HBox(16);
        tabRow.getChildren().addAll(card1, card2, card3);
        for (javafx.scene.Node n : tabRow.getChildren()) {
            HBox.setHgrow(n, Priority.ALWAYS);
        }

        VBox contentContainer = new VBox(16);

        Runnable updateTabStyles = () -> {
            applyCardStyle(card1, activeTab == 1);
            applyCardStyle(card2, activeTab == 2);
            applyCardStyle(card3, activeTab == 3);
        };

        // Data holder lists
        final List<EmployeeSkill>[] currentSkillsHolder = new List[]{new ArrayList<>()};
        final List<TrainingProgram>[] currentProgramsHolder = new List[]{new ArrayList<>()};

        Runnable renderActiveTabContent = () -> {
            updateTabStyles.run();
            contentContainer.getChildren().clear();
            if (activeTab == 1) {
                renderVerifiedSkillsTab(contentContainer, currentSkillsHolder[0]);
            } else if (activeTab == 2) {
                renderSkillGapsTab(contentContainer, currentSkillsHolder[0], () -> {
                    activeTab = 3;
                    updateTabStyles.run();
                    contentContainer.getChildren().clear();
                    renderRecommendedTrainingTab(contentContainer, currentProgramsHolder[0], onNavigateToLearning);
                });
            } else {
                renderRecommendedTrainingTab(contentContainer, currentProgramsHolder[0], onNavigateToLearning);
            }
        };

        card1.setOnMouseClicked(e -> {
            activeTab = 1;
            renderActiveTabContent.run();
        });
        card2.setOnMouseClicked(e -> {
            activeTab = 2;
            renderActiveTabContent.run();
        });
        card3.setOnMouseClicked(e -> {
            activeTab = 3;
            renderActiveTabContent.run();
        });

        Runnable loadSkillsData = () -> {
            contentContainer.getChildren().setAll(new Label("🔄 Fetching competencies and training data..."));

            FirebaseDAO.getInstance().getSkillsAndGaps(userEmail).thenAccept(skillsList -> {
                Platform.runLater(() -> {
                    currentSkillsHolder[0] = skillsList != null ? skillsList : new ArrayList<>();

                    int verifiedCount = 0;
                    int gapCount = 0;
                    for (EmployeeSkill sk : currentSkillsHolder[0]) {
                        if (sk.getProficiencyPercentage() >= 50 || "Expert".equalsIgnoreCase(sk.getCurrentLevel()) || "Advanced".equalsIgnoreCase(sk.getCurrentLevel())) {
                            verifiedCount++;
                        }
                        if (sk.getGapPercentage() > 0 || "Beginner".equalsIgnoreCase(sk.getCurrentLevel())) {
                            gapCount++;
                        }
                    }
                    verifiedBadge.setText(verifiedCount + " Verified");
                    gapBadge.setText(gapCount + " Gaps");

                    renderActiveTabContent.run();
                });
            });

            FirebaseDAO.getInstance().getAllTrainingPrograms().thenAccept(programList -> {
                Platform.runLater(() -> {
                    currentProgramsHolder[0] = programList != null ? programList : new ArrayList<>();
                    coursesBadge.setText(currentProgramsHolder[0].size() + " Courses");
                    if (activeTab == 3) {
                        renderActiveTabContent.run();
                    }
                });
            });
        };

        syncBtn.setOnAction(e -> loadSkillsData.run());
        loadSkillsData.run();

        ScrollPane scrollPane = new ScrollPane(new VBox(24, headerRow, tabRow, contentContainer));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        page.getChildren().add(scrollPane);
        return page;
    }

    private static VBox createTabCard(int tabIndex, String icon, String title, String subtitle, Label badgeLabel) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18, 20, 18, 20));

        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER_LEFT);

        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font("Arial", 22));

        VBox textContainer = new VBox(2);
        Text t = new Text(title);
        t.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        t.setFill(Color.web(TEXT));

        Text sub = new Text(subtitle);
        sub.setFont(Font.font("Arial", 11));
        sub.setFill(Color.web(MUTED));

        textContainer.getChildren().addAll(t, sub);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        badgeLabel.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-text-fill: #4F46E5; -fx-font-weight: bold; -fx-padding: 3px 10px; -fx-background-radius: 12px; -fx-border-radius: 12px; -fx-font-size: 11px;");

        top.getChildren().addAll(iconLbl, textContainer, sp, badgeLabel);
        card.getChildren().add(top);
        return card;
    }

    private static void applyCardStyle(VBox card, boolean isActive) {
        if (isActive) {
            card.setStyle("-fx-background-color: #EEF2FF; -fx-border-color: #4F46E5; -fx-border-width: 2px; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(79,70,229,0.15), 10, 0, 0, 3); -fx-cursor: hand;");
        } else {
            card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-width: 1px; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 6, 0, 0, 2); -fx-cursor: hand;");
        }
    }

    // --- TAB 1: Verified Skills ---
    private static void renderVerifiedSkillsTab(VBox container, List<EmployeeSkill> skills) {
        container.getChildren().clear();
        int count = 0;

        if (skills != null) {
            for (EmployeeSkill sk : skills) {
                if (sk.getProficiencyPercentage() >= 50 || "Expert".equalsIgnoreCase(sk.getCurrentLevel()) || "Advanced".equalsIgnoreCase(sk.getCurrentLevel()) || "Intermediate".equalsIgnoreCase(sk.getCurrentLevel())) {
                    count++;
                    VBox card = new VBox(12);
                    card.setPadding(new Insets(18, 22, 18, 22));
                    card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 6, 0, 0, 2);");

                    HBox row = new HBox(16);
                    row.setAlignment(Pos.CENTER_LEFT);

                    VBox inf = new VBox(2);
                    Text name = new Text(sk.getSkillName() != null ? sk.getSkillName() : "Competency");
                    name.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                    name.setFill(Color.web(TEXT));

                    String domain = sk.getRecommendedCourseName() != null && !sk.getRecommendedCourseName().isBlank() ? sk.getRecommendedCourseName() : "Technical Capability";
                    Label domainTag = new Label("Domain: " + domain);
                    domainTag.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");

                    inf.getChildren().addAll(name, domainTag);

                    Region sp = new Region();
                    HBox.setHgrow(sp, Priority.ALWAYS);

                    String levelStr = sk.getCurrentLevel() != null ? sk.getCurrentLevel() : "Intermediate";
                    Label levelBadge = new Label(levelStr);
                    if ("Expert".equalsIgnoreCase(levelStr)) {
                        levelBadge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-weight: bold; -fx-padding: 4px 12px; -fx-background-radius: 6px; -fx-font-size: 11px;");
                    } else if ("Advanced".equalsIgnoreCase(levelStr)) {
                        levelBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-padding: 4px 12px; -fx-background-radius: 6px; -fx-font-size: 11px;");
                    } else {
                        levelBadge.setStyle("-fx-background-color: #EEF2FF; -fx-text-fill: #4F46E5; -fx-font-weight: bold; -fx-padding: 4px 12px; -fx-background-radius: 6px; -fx-font-size: 11px;");
                    }

                    VBox profBox = new VBox(4);
                    profBox.setAlignment(Pos.CENTER_RIGHT);
                    Text pText = new Text(sk.getProficiencyPercentage() + "% Verified");
                    pText.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                    pText.setFill(Color.web(INDIGO));

                    ProgressBar pb = new ProgressBar(sk.getProficiencyPercentage() / 100.0);
                    pb.setPrefWidth(200);
                    pb.setStyle("-fx-accent: #4F46E5;");

                    profBox.getChildren().addAll(pText, pb);

                    row.getChildren().addAll(inf, sp, levelBadge, profBox);
                    card.getChildren().add(row);
                    container.getChildren().add(card);
                }
            }
        }

        if (count == 0) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(40));
            Label emptyLbl = new Label("No verified skills recorded yet. Complete assessments or courses to verify competencies.");
            emptyLbl.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 14px; -fx-font-style: italic;");
            emptyBox.getChildren().add(emptyLbl);
            container.getChildren().add(emptyBox);
        }
    }

    // --- TAB 2: Skill Gap Analysis ---
    private static void renderSkillGapsTab(VBox container, List<EmployeeSkill> skills, Runnable onSwitchToTraining) {
        container.getChildren().clear();
        int count = 0;

        if (skills != null) {
            for (EmployeeSkill sk : skills) {
                if (sk.getGapPercentage() > 0 || "Beginner".equalsIgnoreCase(sk.getCurrentLevel())) {
                    count++;
                    VBox card = new VBox(14);
                    card.setPadding(new Insets(18, 22, 18, 22));
                    card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 6, 0, 0, 2);");

                    HBox row = new HBox(16);
                    row.setAlignment(Pos.CENTER_LEFT);

                    VBox inf = new VBox(4);
                    Text name = new Text(sk.getSkillName() != null ? sk.getSkillName() : "Capability Gap");
                    name.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                    name.setFill(Color.web(TEXT));

                    String currentLvl = sk.getCurrentLevel() != null ? sk.getCurrentLevel() : "Beginner";
                    String targetLvl = sk.getRequiredLevel() != null ? sk.getRequiredLevel() : "Advanced";
                    Text shift = new Text("Capability Shift: " + currentLvl + " (Current) ➔ " + targetLvl + " (Target)");
                    shift.setFont(Font.font("Arial", 12.5));
                    shift.setFill(Color.web(MUTED));
                    inf.getChildren().addAll(name, shift);

                    Region sp = new Region();
                    HBox.setHgrow(sp, Priority.ALWAYS);

                    int gap = sk.getGapPercentage() > 0 ? sk.getGapPercentage() : 35;
                    Label gapBadge = new Label("🔴 " + gap + "% Gap to Target");
                    if (gap >= 35) {
                        gapBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-padding: 5px 14px; -fx-background-radius: 6px; -fx-font-size: 12px;");
                    } else {
                        gapBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 5px 14px; -fx-background-radius: 6px; -fx-font-size: 12px;");
                    }

                    Button findTrainingBtn = new Button("Find Matched Training →");
                    findTrainingBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 6px; -fx-cursor: hand;");
                    findTrainingBtn.setOnAction(e -> {
                        if (onSwitchToTraining != null) {
                            onSwitchToTraining.run();
                        }
                    });

                    row.getChildren().addAll(inf, sp, gapBadge, findTrainingBtn);
                    card.getChildren().add(row);
                    container.getChildren().add(card);
                }
            }
        }

        if (count == 0) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(40));
            Label emptyLbl = new Label("Great job! No skill gaps identified for your current role profile.");
            emptyLbl.setStyle("-fx-text-fill: #9CA3AF; -fx-font-size: 14px; -fx-font-style: italic;");
            emptyBox.getChildren().add(emptyLbl);
            container.getChildren().add(emptyBox);
        }
    }

    private static void renderRecommendedTrainingTab(VBox container, List<TrainingProgram> programs, Consumer<String> onNavigateToLearning) {
        container.getChildren().clear();

        if (programs == null || programs.isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(40));
            Label emptyLbl = new Label("No recommended training programs available.");
            emptyLbl.setStyle("-fx-text-fill: #9CA3AF; -fx-font-style: italic; -fx-font-size: 14px;");
            emptyBox.getChildren().add(emptyLbl);
            container.getChildren().add(emptyBox);
            return;
        }

        for (TrainingProgram prog : programs) {
            VBox card = new VBox(14);
            card.setPadding(new Insets(18, 22, 18, 22));
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.03), 6, 0, 0, 2);");

            HBox row = new HBox(16);
            row.setAlignment(Pos.CENTER_LEFT);

            VBox inf = new VBox(4);
            Text courseTitle = new Text("🎓 " + (prog.getTitle() != null ? prog.getTitle() : "Training Program"));
            courseTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            courseTitle.setFill(Color.web(TEXT));

            String dept = prog.getDepartment() != null ? prog.getDepartment() : "Technical Skills";
            String duration = prog.getDurationWeeks() != null ? prog.getDurationWeeks() : "4";
            String trainer = prog.getAssignedTrainerName() != null ? prog.getAssignedTrainerName() : (prog.getAssignedTrainerEmail() != null ? prog.getAssignedTrainerEmail() : "Lead Instructor");
            Text meta = new Text("Department: " + dept + " • Duration: " + duration + " Weeks • Trainer: " + trainer);
            meta.setFont(Font.font("Arial", 12.5));
            meta.setFill(Color.web(MUTED));

            Label gapTag = new Label("Addresses Capability Gap: " + (prog.getDepartment() != null ? prog.getDepartment() : "Core Skill"));
            gapTag.setStyle("-fx-background-color: #EEF2FF; -fx-text-fill: #4F46E5; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 4px; -fx-font-size: 11px;");

            inf.getChildren().addAll(courseTitle, meta, gapTag);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Button startBtn = new Button("Start Learning →");
            startBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 18px; -fx-background-radius: 6px; -fx-cursor: hand;");
            startBtn.setOnAction(e -> {
                if (onNavigateToLearning != null) {
                    onNavigateToLearning.accept("Learning");
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Enrolled in '" + prog.getTitle() + "'! Navigating to Learning portal.").showAndWait();
                }
            });

            row.getChildren().addAll(inf, sp, startBtn);
            card.getChildren().add(row);
            container.getChildren().add(card);
        }
    }
}
