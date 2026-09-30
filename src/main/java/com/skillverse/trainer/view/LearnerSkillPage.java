
package com.skillverse.trainer.view;

import com.skillverse.trainer.controller.LearnerSkillController;
import com.skillverse.trainer.model.LearnerSkill;
import com.skillverse.trainer.model.Trainer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LearnerSkillPage {

    public static void show(
            Stage stage,
            Trainer trainer) {

        LearnerSkillController controller =
                new LearnerSkillController(
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

        root.setLeft(
                TrainerSidebar.create(
                        stage,
                        trainer
                )
        );

        VBox page =
                new VBox(18);

        page.setPadding(
                new Insets(28)
        );

        HBox header =
                new HBox(15);

        VBox heading =
                new VBox(
                        5,
                        AppTheme.title(
                                "Learner Skills"
                        ),
                        AppTheme.subtitle(
                                "Review learner skills and recommend development areas."
                        )
                );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Button back =
                AppTheme.secondary(
                        "← Dashboard"
                );

        back.setPrefWidth(150);

        back.setOnAction(
                e -> controller.goToDashboard()
        );

        header.getChildren().addAll(
                heading,
                spacer,
                back
        );

        Button action =
                AppTheme.primary(
                        "＋ Add Skill"
                );

        action.setPrefWidth(200);

        action.setOnAction(
                e -> showSkillDialog(
                        controller
                )
        );

        VBox list =
                new VBox(12);

        for (LearnerSkill skill :
                controller.getSkills()) {

            list.getChildren().add(
                    AppTheme.card(
                            new Label(
                                    skill.getSkillName()
                                            + " • "
                                            + skill.getProficiencyLevel()
                                            + " • "
                                            + skill.getLearnerCount()
                                            + " learners"
                            ),
                            new Label(
                                    "Trainer workspace • Manage and update this item."
                            )
                    )
            );
        }

        page.getChildren().addAll(
                header,
                action,
                list
        );

        root.setCenter(page);

        stage.setTitle(
                "SkillVerse - Learner Skills"
        );

        stage.setScene(
                new Scene(
                        root,
                        1280,
                        800
                )
        );

        stage.show();
    }

    private static void showSkillDialog(
            LearnerSkillController controller) {

        Dialog<ButtonType> dialog =
                new Dialog<>();

        dialog.setTitle(
                "Add Learner Skill"
        );

        TextField skill =
                AppTheme.field(
                        "Skill name"
                );

        TextField level =
                AppTheme.field(
                        "Proficiency level"
                );

        TextField count =
                AppTheme.field(
                        "Learner count"
                );

        TextArea description =
                new TextArea();

        description.setPromptText(
                "Description"
        );

        VBox box =
                new VBox(
                        10,
                        new Label("Skill"),
                        skill,
                        new Label("Proficiency"),
                        level,
                        new Label("Learner Count"),
                        count,
                        new Label("Description"),
                        description
                );

        box.setPadding(
                new Insets(10)
        );

        dialog.getDialogPane()
                .setContent(box);

        ButtonType add =
                new ButtonType(
                        "Add",
                        ButtonBar.ButtonData.OK_DONE
                );

        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        add,
                        ButtonType.CANCEL
                );

        dialog.showAndWait()
                .ifPresent(result -> {

                    if (result == add) {

                        int learnerCount = 0;

                        try {
                            learnerCount =
                                    Integer.parseInt(
                                            count.getText()
                                    );
                        } catch (NumberFormatException ignored) {
                        }

                        if (controller.addSkill(
                                skill.getText(),
                                level.getText(),
                                learnerCount,
                                description.getText()
                        )) {

                            new Alert(
                                    Alert.AlertType.INFORMATION,
                                    "Skill added successfully."
                            ).showAndWait();
                        }
                    }
                });
    }
}