
package com.skillverse.trainer.view;

import com.skillverse.trainer.controller.CourseController;
import com.skillverse.trainer.model.Course;
import com.skillverse.trainer.model.Trainer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CoursePage {

    public static void show(
            Stage stage,
            Trainer trainer) {

        CourseController controller =
                new CourseController(
                        stage,
                        trainer
                );

        BorderPane root =
                new BorderPane();

        root.setStyle(
                "-fx-background-color:" +
                AppTheme.BG +
                ";"
        );

        root.setLeft(TrainerSidebar.create(stage, trainer, "My Courses"));
        root.setCenter(new TrainerTrainingsView());

        stage.setTitle("SkillVerse - My Courses & Training Programs");

        stage.setScene(
                new Scene(
                        root,
                        1280,
                        800
                )
        );

        stage.show();
    }

    private static void showCreateCourseDialog(
            CourseController controller) {

        Dialog<ButtonType> dialog =
                new Dialog<>();

        dialog.setTitle(
                "Create Course"
        );

        TextField title =
                AppTheme.field(
                        "Course title"
                );

        TextArea description =
                new TextArea();

        description.setPromptText(
                "Course description"
        );

        TextField level =
                AppTheme.field(
                        "Level"
                );

        TextField learners =
                AppTheme.field(
                        "Learner count"
                );

        TextField duration =
                AppTheme.field(
                        "Duration"
                );

        VBox box =
                new VBox(
                        10,
                        new Label("Course Title"),
                        title,
                        new Label("Description"),
                        description,
                        new Label("Level"),
                        level,
                        new Label("Learner Count"),
                        learners,
                        new Label("Duration"),
                        duration
                );

        box.setPadding(
                new Insets(10)
        );

        dialog.getDialogPane()
                .setContent(box);

        ButtonType create =
                new ButtonType(
                        "Create",
                        ButtonBar.ButtonData.OK_DONE
                );

        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        create,
                        ButtonType.CANCEL
                );

        dialog.showAndWait()
                .ifPresent(result -> {

                    if (result == create) {

                        int learnerCount = 0;

                        try {
                            learnerCount =
                                    Integer.parseInt(
                                            learners.getText()
                                    );
                        } catch (NumberFormatException ignored) {
                        }

                        if (controller.createCourse(
                                title.getText(),
                                description.getText(),
                                level.getText(),
                                learnerCount,
                                duration.getText()
                        )) {

                            new Alert(
                                    Alert.AlertType.INFORMATION,
                                    "Course created successfully."
                            ).showAndWait();
                        }
                    }
                });
    }
}