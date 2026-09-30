package com.skillverse.trainer.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.TrainerAssessment;
import com.skillverse.trainer.model.Trainer;
import com.skillverse.CommonFeatures.UserSession;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AssessmentPage {

    public static void show(Stage stage, Trainer trainer) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + AppTheme.BG + ";");
        root.setLeft(TrainerSidebar.create(stage, trainer, "Assessments"));

        StackPane contentStack = new StackPane();
        contentStack.setPadding(new Insets(24));

        VBox toastBox = new VBox();

      
        VBox listView = new VBox(20);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        VBox heading = new VBox(4,
                AppTheme.title("Assessments & Skill Tests"),
                AppTheme.subtitle("Design technical tests, quizzes, and evaluate employee skill competencies.")
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button createBtn = new Button("+ Create New Assessment");
        createBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand;");

        header.getChildren().addAll(heading, headerSpacer, createBtn);

        VBox assessmentsContainer = new VBox(14);
        ScrollPane scrollPane = new ScrollPane(assessmentsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        listView.getChildren().addAll(header, toastBox, scrollPane);

        VBox formView = new VBox(20);
        formView.setVisible(false);

        HBox formHeader = new HBox(15);
        formHeader.setAlignment(Pos.CENTER_LEFT);
        VBox formHeading = new VBox(4,
                AppTheme.title("Assessment Creator"),
                AppTheme.subtitle("Configure assessment details, passing scores, and publish to employee portals.")
        );

        Region formSpacer = new Region();
        HBox.setHgrow(formSpacer, Priority.ALWAYS);

        Button backToListBtn = new Button("← Cancel & Back to List");
        backToListBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 8px; -fx-cursor: hand;");

        formHeader.getChildren().addAll(formHeading, formSpacer, backToListBtn);

        VBox formCard = new VBox(16);
        formCard.setPadding(new Insets(24));
        formCard.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(16);

      
        Label titleLbl = createFormLabel("Assessment Title *");
        TextField titleField = AppTheme.field("e.g. Spring Boot & Microservices Mid-Term Test");
        grid.add(titleLbl, 0, 0);
        grid.add(titleField, 0, 1);

     
        Label courseLbl = createFormLabel("Associated Course / Program *");
        TextField courseField = AppTheme.field("e.g. Java Full Stack Development");
        grid.add(courseLbl, 1, 0);
        grid.add(courseField, 1, 1);

     
        Label marksLbl = createFormLabel("Total Marks *");
        TextField marksField = AppTheme.field("e.g. 100");
        grid.add(marksLbl, 0, 2);
        grid.add(marksField, 0, 3);

     
        Label timeLbl = createFormLabel("Time Limit (Minutes) *");
        TextField timeField = AppTheme.field("e.g. 60");
        grid.add(timeLbl, 1, 2);
        grid.add(timeField, 1, 3);

        
        Label deptLbl = createFormLabel("Target Department *");
        ComboBox<String> deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("ALL", "Engineering", "Product & Design", "Human Resources", "Sales & Marketing", "Finance");
        deptCombo.setValue("ALL");
        deptCombo.setPrefHeight(43);
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-radius: 8px;");
        grid.add(deptLbl, 0, 4);
        grid.add(deptCombo, 0, 5);

      
        Label deadlineLbl = createFormLabel("Submission Deadline *");
        DatePicker deadlinePicker = new DatePicker(LocalDate.now().plusDays(7));
        deadlinePicker.setPrefHeight(43);
        deadlinePicker.setMaxWidth(Double.MAX_VALUE);
        deadlinePicker.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-radius: 8px;");
        grid.add(deadlineLbl, 1, 4);
        grid.add(deadlinePicker, 1, 5);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

       
        Label instructionsLbl = createFormLabel("Test Instructions & Guidelines");
        TextArea instructionsArea = new TextArea();
        instructionsArea.setPromptText("Enter test format, allowed resources, passing threshold, and question guidelines...");
        instructionsArea.setPrefRowCount(4);
        instructionsArea.setWrapText(true);
        instructionsArea.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-radius: 8px; -fx-padding: 8px;");

        Button publishBtn = new Button("📝 Publish Assessment to Employees");
        publishBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12px 28px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-size: 14px;");

        formCard.getChildren().addAll(grid, instructionsLbl, instructionsArea, publishBtn);
        formView.getChildren().addAll(formHeader, formCard);

        contentStack.getChildren().addAll(listView, formView);
        root.setCenter(contentStack);

        Runnable loadAssessments = () -> {
            assessmentsContainer.getChildren().clear();
            ProgressIndicator spinner = new ProgressIndicator();
            assessmentsContainer.getChildren().add(new HBox(10, new Label("Loading active assessments..."), spinner));

            FirebaseDAO.getInstance().getAssessmentsForEmployee(null).thenAccept(assessments -> {
                Platform.runLater(() -> {
                    assessmentsContainer.getChildren().clear();
                    if (assessments == null || assessments.isEmpty()) {
                        VBox emptyBox = AppTheme.card(
                                new Label("📝 No Active Assessments Found"),
                                new Label("Click '+ Create New Assessment' above to publish an assessment for employees.")
                        );
                        emptyBox.setAlignment(Pos.CENTER);
                        assessmentsContainer.getChildren().add(emptyBox);
                    } else {
                        for (TrainerAssessment assessment : assessments) {
                            assessmentsContainer.getChildren().add(buildAssessmentCard(assessment));
                        }
                    }
                });
            });
        };

        createBtn.setOnAction(e -> {
            listView.setVisible(false);
            formView.setVisible(true);
        });

        backToListBtn.setOnAction(e -> {
            formView.setVisible(false);
            listView.setVisible(true);
        });

        publishBtn.setOnAction(e -> {
            String title = titleField.getText() != null ? titleField.getText().trim() : "";
            String course = courseField.getText() != null ? courseField.getText().trim() : "";
            String marksStr = marksField.getText() != null ? marksField.getText().trim() : "100";
            String duration = timeField.getText() != null ? timeField.getText().trim() : "60";
            String dept = deptCombo.getValue();
            String deadline = deadlinePicker.getValue() != null ? deadlinePicker.getValue().toString() : "";

            if (title.isEmpty() || course.isEmpty() || deadline.isEmpty()) {
                showToast(toastBox, "⚠️ Please enter required fields (Title, Course, Deadline).", "#DC2626", "#FEF2F2");
                return;
            }

            int totalMarks = 100;
            try {
                totalMarks = Integer.parseInt(marksStr);
            } catch (NumberFormatException ignored) {}

            TrainerAssessment ta = new TrainerAssessment();
            ta.setTitle(title);
            ta.setCourseName(course);
            ta.setTotalMarks(totalMarks);
            ta.setDurationMinutes(duration);
            ta.setTargetDepartment(dept);
            ta.setDeadlineDate(deadline);

            String tEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "trainer@skillverse.com";
            ta.setTrainerEmail(tEmail);
            ta.setCreatedAt(new Date());

            publishBtn.setDisable(true);
            publishBtn.setText("Publishing...");

            FirebaseDAO.getInstance().createAssessment(ta).thenAccept(success -> {
                Platform.runLater(() -> {
                    publishBtn.setDisable(false);
                    publishBtn.setText("📝 Publish Assessment to Employees");
                    if (success) {
                        showToast(toastBox, "✅ Assessment '" + title + "' published successfully!", "#16A34A", "#F0FDF4");
                        titleField.clear();
                        courseField.clear();
                        marksField.clear();
                        timeField.clear();
                        instructionsArea.clear();
                        formView.setVisible(false);
                        listView.setVisible(true);
                        loadAssessments.run();
                    } else {
                        showToast(toastBox, "❌ Error saving assessment to Firestore.", "#DC2626", "#FEF2F2");
                    }
                });
            });
        });

        loadAssessments.run();

        stage.setTitle("SkillVerse - Assessments");
        stage.setScene(new Scene(root, 1280, 800));
        stage.show();
    }

    private static Label createFormLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        lbl.setTextFill(Color.web("#334155"));
        return lbl;
    }

    private static VBox buildAssessmentCard(TrainerAssessment ta) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;");

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label marksBadge = new Label("💯 Marks: " + ta.getTotalMarks());
        marksBadge.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Label timeBadge = new Label("⏱️ Duration: " + (ta.getDurationMinutes() != null ? ta.getDurationMinutes() : "60") + " mins");
        timeBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Label deadlineBadge = new Label("⏳ Deadline: " + (ta.getDeadlineDate() != null ? ta.getDeadlineDate() : "No Deadline"));
        deadlineBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 6px; -fx-font-size: 12px;");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        topRow.getChildren().addAll(marksBadge, timeBadge, deadlineBadge, sp);

        Text titleText = new Text(ta.getTitle() != null ? ta.getTitle() : "Untitled Assessment");
        titleText.setFont(Font.font("System", FontWeight.BOLD, 17));
        titleText.setFill(Color.web("#0F172A"));

        Text courseText = new Text("Course: " + (ta.getCourseName() != null ? ta.getCourseName() : "General Training") + " • Target: " + (ta.getTargetDepartment() != null ? ta.getTargetDepartment() : "ALL"));
        courseText.setFont(Font.font("System", 13));
        courseText.setFill(Color.web("#64748B"));

        card.getChildren().addAll(topRow, titleText, courseText);
        return card;
    }

    private static void showToast(VBox toastContainer, String message, String textColor, String bgColor) {
        toastContainer.getChildren().clear();
        HBox toast = new HBox();
        toast.setPadding(new Insets(12, 16, 12, 16));
        toast.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: " + textColor + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        Label lbl = new Label(message);
        lbl.setStyle("-fx-text-fill: " + textColor + "; -fx-font-weight: bold; -fx-font-size: 13px;");
        toast.getChildren().add(lbl);
        toastContainer.getChildren().add(toast);
    }
}