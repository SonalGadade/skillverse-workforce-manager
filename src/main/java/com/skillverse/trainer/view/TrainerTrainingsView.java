package com.skillverse.trainer.view;

import java.util.List;

import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.TrainingEnrollment;
import com.skillverse.CommonFeatures.TrainingProgram;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TrainerTrainingsView extends VBox {

    private VBox toastBannerBox;
    private VBox programsContainer;
    private VBox gradingContainerBox;

    public TrainerTrainingsView() {
        setSpacing(20);
        setPadding(new Insets(24));
        setStyle("-fx-background-color: #F8FAFC;");

        // Header Bar
        HBox headerBar = new HBox();
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(2);
        Text titleText = new Text("👨‍🏫 Training Programs & Batches");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleText.setFill(Color.web("#0F172A"));

        Text subtitleText = new Text("Create new courses, manage assigned training programs, and grade employee assessments.");
        subtitleText.setFont(Font.font("Arial", 13));
        subtitleText.setFill(Color.web("#64748B"));
        titleBox.getChildren().addAll(titleText, subtitleText);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button addProgBtn = new Button("+ Create Training Program");
        addProgBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-size: 13px;");
        addProgBtn.setOnAction(e -> showCreateTrainingProgramModal());

        headerBar.getChildren().addAll(titleBox, headerSpacer, addProgBtn);

        toastBannerBox = new VBox();

     
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.04), 10, 0.1, 0, 3);");

        Text listTitle = new Text("Assigned Training Programs");
        listTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        listTitle.setFill(Color.web("#0F172A"));

        programsContainer = new VBox(12);
        programsContainer.getChildren().add(new Label("🔄 Loading training programs"));
        card.getChildren().addAll(listTitle, programsContainer);

        gradingContainerBox = new VBox(16);

        getChildren().addAll(headerBar, toastBannerBox, card, gradingContainerBox);

        loadPrograms();
    }

    public void loadPrograms() {
        String trainerEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail() : "";
        FirebaseDAO.getInstance().getTrainingsForTrainer(trainerEmail).thenAccept(programs -> {
            Platform.runLater(() -> renderProgramCards(programs));
        });
    }

    private void renderProgramCards(List<TrainingProgram> programs) {
        programsContainer.getChildren().clear();
        if (programs == null || programs.isEmpty()) {
            Label empty = new Label("No training programs currently assigned or created. Click '+ Create Training Program' to start!");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px; -fx-padding: 10px;");
            programsContainer.getChildren().add(empty);
            return;
        }

        for (TrainingProgram tp : programs) {
            programsContainer.getChildren().add(createTrainerProgramCard(tp));
        }
    }

    private HBox createTrainerProgramCard(TrainingProgram tp) {
        HBox r = new HBox(16);
        r.setAlignment(Pos.CENTER_LEFT);
        r.setPadding(new Insets(14, 16, 14, 16));
        r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        VBox inf = new VBox(2);
        Text n = new Text(tp.getTitle() != null ? tp.getTitle() : "Untitled Program");
        n.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        n.setFill(Color.web("#0F172A"));

        String trainerName = tp.getAssignedTrainerName() != null ? tp.getAssignedTrainerName() : "Trainer";
        String dept = tp.getDepartment() != null ? tp.getDepartment() : "General";
        String duration = tp.getDurationWeeks() != null ? tp.getDurationWeeks() : "4 Weeks";
        String startDate = tp.getStartDate() != null ? tp.getStartDate() : "TBD";

        Text sub = new Text("Trainer: " + trainerName + " • Dept: " + dept + " • Duration: " + duration + " • Start: " + startDate);
        sub.setFont(Font.font("Arial", 11));
        sub.setFill(Color.web("#64748B"));
        inf.getChildren().addAll(n, sub);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        boolean isCompleted = "COMPLETED".equalsIgnoreCase(tp.getStatus());

        Label statusBadge = new Label(isCompleted ? "COMPLETED" : "ACTIVE");
        statusBadge.setStyle(isCompleted ? "-fx-background-color: #E2E8F0; -fx-text-fill: #475569; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 10px;"
                                         : "-fx-background-color: #DCFCE7; -fx-text-fill: #15803D; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 10px;");

        Button viewGradingBtn = new Button("👥 View Students & Grade");
        viewGradingBtn.setStyle("-fx-background-color: #1E60FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 14px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-size: 11px;");
        viewGradingBtn.setOnAction(e -> renderStudentGradingView(tp));

        Button completeBtn = new Button(isCompleted ? "✓ Completed" : "Mark as Completed");
        completeBtn.setDisable(isCompleted);
        completeBtn.setStyle(isCompleted ? "-fx-background-color: #F1F5F9; -fx-text-fill: #94A3B8; -fx-font-weight: bold; -fx-padding: 6px 12px; -fx-background-radius: 8px; -fx-font-size: 11px;"
                                          : "-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-padding: 6px 12px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-size: 11px;");
        completeBtn.setOnAction(e -> {
            FirebaseDAO.getInstance().updateTrainingProgramStatusAsync(tp.getProgramId(), "COMPLETED").thenAccept(ok -> {
                Platform.runLater(() -> {
                    loadPrograms();
                    showSuccessToast("Training program '" + tp.getTitle() + "' marked as COMPLETED! 🎉");
                });
            });
        });

        r.getChildren().addAll(inf, sp, statusBadge, viewGradingBtn, completeBtn);
        return r;
    }

    private void renderStudentGradingView(TrainingProgram program) {
        gradingContainerBox.getChildren().clear();

        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px; -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.04), 10, 0.1, 0, 3);");

        Text title = new Text("👥 Student Roster & Grading: " + program.getTitle());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        title.setFill(Color.web("#0F172A"));

        VBox studentList = new VBox(12);
        studentList.getChildren().add(new Label("🔄 Loading enrolled employees"));
        card.getChildren().addAll(title, studentList);
        gradingContainerBox.getChildren().add(card);

        FirebaseDAO.getInstance().getEnrollmentsForProgram(program.getProgramId()).thenAccept(enrollments -> {
            Platform.runLater(() -> renderStudentRows(studentList, program, enrollments));
        });
    }

    private void renderStudentRows(VBox container, TrainingProgram program, List<TrainingEnrollment> enrollments) {
        container.getChildren().clear();
        if (enrollments == null || enrollments.isEmpty()) {
            Label empty = new Label("No employees have enrolled in this training program yet.");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 13px; -fx-padding: 10px;");
            container.getChildren().add(empty);
            return;
        }

        for (TrainingEnrollment te : enrollments) {
            HBox r = new HBox(16);
            r.setAlignment(Pos.CENTER_LEFT);
            r.setPadding(new Insets(12, 16, 12, 16));
            r.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            VBox inf = new VBox(2);
            Text n = new Text(te.getEmployeeName() + " (" + te.getEmployeeDepartment() + ")");
            n.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            n.setFill(Color.web("#0F172A"));

            Text sub = new Text("Email: " + te.getEmployeeEmail() + " • Status: " + te.getCompletionStatus());
            sub.setFont(Font.font("Arial", 11));
            sub.setFill(Color.web("#64748B"));
            inf.getChildren().addAll(n, sub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label attTag = new Label("Attendance: " + (int) te.getAttendancePercentage() + "%");
            attTag.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E40AF; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4px 10px; -fx-background-radius: 10px;");

            Label scoreTag = new Label("Score: " + (int) te.getAssessmentScore() + "%");
            scoreTag.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4px 10px; -fx-background-radius: 10px;");

            Button gradeBtn = new Button("✍️ Grade / Update Scores");
            gradeBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 12px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-size: 11px;");
            gradeBtn.setOnAction(e -> showGradeModal(te, program));

            r.getChildren().addAll(inf, sp, attTag, scoreTag, gradeBtn);
            container.getChildren().add(r);
        }
    }

    private void showGradeModal(TrainingEnrollment te, TrainingProgram program) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Student Scores & Attendance");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType saveBtnType = new ButtonType("Save Marks & Attendance", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(saveBtnType, cancelBtnType);

        Button saveBtn = (Button) pane.lookupButton(saveBtnType);
        saveBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");
        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(16));
        content.setPrefWidth(420);

        Text title = new Text("✍️ Update Scores for " + te.getEmployeeName());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Color.web("#0F172A"));

        TextField attF = new TextField(String.valueOf((int) te.getAttendancePercentage()));
        attF.setPromptText("Attendance % (0-100)");
        attF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        TextField scoreF = new TextField(String.valueOf((int) te.getAssessmentScore()));
        scoreF.setPromptText("Assessment Score % (0-100)");
        scoreF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("IN_PROGRESS", "COMPLETED", "DROPPED");
        statusCombo.setValue(te.getCompletionStatus() != null ? te.getCompletionStatus() : "IN_PROGRESS");
        statusCombo.setMaxWidth(Double.MAX_VALUE);
        statusCombo.setStyle("-fx-padding: 4px; -fx-background-color: white; -fx-border-color: #CBD5E1;");

        TextField dateF = new TextField("Aug 29, 2026");
        dateF.setPromptText("Completion Date");
        dateF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Attendance %:"), 0, 0); grid.add(attF, 1, 0);
        grid.add(new Label("Assessment Score %:"), 0, 1); grid.add(scoreF, 1, 1);
        grid.add(new Label("Completion Status:"), 0, 2); grid.add(statusCombo, 1, 2);
        grid.add(new Label("Completion Date:"), 0, 3); grid.add(dateF, 1, 3);

        content.getChildren().addAll(title, grid);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtnType) {
                double attVal = 0.0;
                double scoreVal = 0.0;
                try { attVal = Double.parseDouble(attF.getText().trim()); } catch (Exception ex) {}
                try { scoreVal = Double.parseDouble(scoreF.getText().trim()); } catch (Exception ex) {}

                String newStatus = statusCombo.getValue();
                String dateVal = dateF.getText().trim();

                FirebaseDAO.getInstance().updateTrainerGrading(te.getEnrollmentId(), attVal, scoreVal, newStatus, dateVal).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        showSuccessToast("Employee training records updated successfully for " + te.getEmployeeName() + "! 🚀");
                        renderStudentGradingView(program);
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void showCreateTrainingProgramModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create Training Program");

        DialogPane pane = dialog.getDialogPane();
        pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-background-radius: 16px;");

        ButtonType publishBtnType = new ButtonType("Publish / Create Course", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(publishBtnType, cancelBtnType);

        Button pubBtn = (Button) pane.lookupButton(publishBtnType);
        pubBtn.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");
        Button cancelBtn = (Button) pane.lookupButton(cancelBtnType);
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand; -fx-padding: 8px 18px;");

        VBox content = new VBox(14);
        content.setPadding(new Insets(16));
        content.setPrefWidth(460);

        Text title = new Text("🎓 Add New Training Course");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Color.web("#0F172A"));

        TextField titleF = new TextField();
        titleF.setPromptText("Course Title (e.g. React & TypeScript Engineering)");
        titleF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        ComboBox<String> deptCombo = new ComboBox<>();
        deptCombo.getItems().addAll("Engineering", "HR", "Marketing", "Sales", "All");
        deptCombo.setValue("Engineering");
        deptCombo.setMaxWidth(Double.MAX_VALUE);
        deptCombo.setStyle("-fx-padding: 4px; -fx-background-color: white; -fx-border-color: #CBD5E1;");

        TextField durationF = new TextField("4 Weeks");
        durationF.setPromptText("Duration (Weeks)");
        durationF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        TextField startF = new TextField("Aug 30, 2026");
        startF.setPromptText("Start Date");
        startF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        TextField endF = new TextField("Sep 30, 2026");
        endF.setPromptText("End Date");
        endF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        TextArea descF = new TextArea();
        descF.setPromptText("Course Description / Curriculum Outline...");
        descF.setPrefRowCount(3);
        descF.setWrapText(true);
        descF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: #CBD5E1;");

        VBox form = new VBox(10,
            new VBox(4, new Label("Program Title:"), titleF),
            new VBox(4, new Label("Target Department:"), deptCombo),
            new HBox(10,
                new VBox(4, new Label("Duration:"), durationF),
                new VBox(4, new Label("Start Date:"), startF),
                new VBox(4, new Label("End Date:"), endF)
            ),
            new VBox(4, new Label("Course Description / Outline:"), descF)
        );

        content.getChildren().addAll(title, form);
        pane.setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == publishBtnType && !titleF.getText().isBlank()) {
                String tEmail = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getEmail().toLowerCase().trim() : "trainer@skillverse.com";
                String tName = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getFullName() : "Trainer";

                TrainingProgram tp = new TrainingProgram();
                tp.setTitle(titleF.getText().trim());
                tp.setDescription(descF.getText().trim());
                tp.setAssignedTrainerEmail(tEmail);
                tp.setAssignedTrainerName(tName);
                tp.setDepartment(deptCombo.getValue());
                tp.setDurationWeeks(durationF.getText().trim());
                tp.setStartDate(startF.getText().trim());
                tp.setEndDate(endF.getText().trim());
                tp.setStatus("ACTIVE");

                FirebaseDAO.getInstance().createTrainingProgram(tp).thenAccept(ok -> {
                    Platform.runLater(() -> {
                        loadPrograms();
                        showSuccessToast("Training program created successfully!");
                    });
                });
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void showSuccessToast(String message) {
        if (toastBannerBox == null) return;
        toastBannerBox.getChildren().clear();
        HBox toast = new HBox(10);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(10, 16, 10, 16));
        toast.setStyle("-fx-background-color: #DCFCE7; -fx-border-color: #86EFAC; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 14px;");

        Text msg = new Text(message);
        msg.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        msg.setFill(Color.web("#15803D"));

        toast.getChildren().addAll(icon, msg);
        toastBannerBox.getChildren().add(toast);
    }
}
