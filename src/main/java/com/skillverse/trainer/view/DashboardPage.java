
package com.skillverse.trainer.view;

import com.skillverse.trainer.controller.DashboardController;
import com.skillverse.trainer.model.Announcement;
import com.skillverse.trainer.model.PerformanceSummary;
import com.skillverse.trainer.model.Trainer;

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

public class DashboardPage {

    public static void show(
            Stage stage,
            Trainer trainer) {

        DashboardController controller =
                new DashboardController(
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

        VBox top = new VBox();
        top.setPadding(new Insets(12, 25, 12, 25));
        top.setStyle("-fx-background-color: white; -fx-border-color: #E4E7EC; -fx-border-width: 0 0 1px 0;");

        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);

        VBox titleGroup = new VBox(2);
        Label crumb = new Label("TRAINER WORKSPACE");
        crumb.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        crumb.setTextFill(Color.web("#1E60FF"));

        Label pageLbl = new Label("Dashboard Overview");
        pageLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        pageLbl.setTextFill(Color.web("#0F172A"));

        titleGroup.getChildren().addAll(crumb, pageLbl);

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

       
        String tName = trainer != null ? trainer.getFullName() : "Alex Morgan";
        String tInit = (tName != null && !tName.isBlank()) ? (tName.contains(" ") ? (tName.split(" ")[0].substring(0, 1) + tName.split(" ")[1].substring(0, 1)).toUpperCase() : tName.substring(0, 1).toUpperCase()) : "AM";

        StackPane avatarBox = new StackPane();
        javafx.scene.shape.Circle avatarBg = new javafx.scene.shape.Circle(18, Color.web("#1E60FF"));
        Text avText = new Text(tInit);
        avText.setFill(Color.WHITE);
        avText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        avatarBox.getChildren().addAll(avatarBg, avText);
        avatarBox.setStyle("-fx-cursor: hand;");

        avatarBox.setOnMouseClicked(e -> ProfilePage.show(stage, trainer));

        HBox rightControls = new HBox(12, avatarBox);
        rightControls.setAlignment(Pos.CENTER_RIGHT);

        row.getChildren().addAll(titleGroup, spacer1, rightControls);
        top.getChildren().add(row);
        root.setTop(top);

        root.setCenter(new TrainerDashboardView());

        stage.setTitle("SkillVerse - Trainer Dashboard");

        stage.setScene(
                new Scene(
                        root,
                        1280,
                        800
                )
        );

        stage.show();
    }

    private static VBox welcome(
            Trainer trainer) {

        String firstName =
                trainer.getFullName();

        if (firstName != null &&
                firstName.contains(" ")) {

            firstName =
                    firstName.substring(
                            0,
                            firstName.indexOf(" ")
                    );
        }

        Label small =
                new Label(
                        "TRAINER WORKSPACE"
                );

        small.setStyle(
                "-fx-text-fill:#DDE7FF;" +
                "-fx-font-weight:bold;"
        );

        Label title =
                new Label(
                        "Welcome back, " +
                        firstName
                );

        title.setStyle(
                "-fx-text-fill:white;" +
                "-fx-font-size:25;" +
                "-fx-font-weight:bold;"
        );

        Label text =
                new Label(
                        "Create engaging learning experiences and track learner growth from one workspace."
                );

        text.setStyle(
                "-fx-text-fill:#DDE7FF;"
        );

        VBox box =
                new VBox(
                        8,
                        small,
                        title,
                        text
                );

        box.setPadding(
                new Insets(22)
        );

        box.setStyle(
                "-fx-background-color:" +
                "linear-gradient(to right,#172554,#2563EB);" +
                "-fx-background-radius:14;"
        );

        return box;
    }

    private static HBox stats(
            DashboardController controller) {

        HBox row =
                new HBox(14);

        row.getChildren().addAll(

                stat(
                        String.format(
                                "%02d",
                                controller
                                        .getActiveCourseCount()
                        ),
                        "Active Courses",
                        "📚"
                ),

                stat(
                        String.valueOf(
                                controller
                                        .getLearnerCount()
                        ),
                        "Learners",
                        "👥"
                ),

                stat(
                        String.valueOf(
                                controller
                                        .getAssessmentCount()
                        ),
                        "Assessments",
                        "✓"
                ),

                stat(
                        String.format(
                                "%.0f%%",
                                controller
                                        .getPerformanceSummary()
                                        .getAverageCourseCompletion()
                        ),
                        "Avg. Completion",
                        "↗"
                )
        );

        for (javafx.scene.Node node :
                row.getChildren()) {

            HBox.setHgrow(
                    node,
                    Priority.ALWAYS
            );
        }

        return row;
    }

    private static VBox stat(
            String number,
            String label,
            String icon) {

        Label i =
                new Label(icon);

        i.setStyle(
                "-fx-background-color:#EEF4FF;" +
                "-fx-padding:10;" +
                "-fx-background-radius:9;"
        );

        Label n =
                new Label(number);

        n.setStyle(
                "-fx-font-size:24;" +
                "-fx-font-weight:bold;"
        );

        Label l =
                new Label(label);

        l.setStyle(
                "-fx-text-fill:" +
                AppTheme.MUTED +
                ";"
        );

        VBox text =
                new VBox(
                        3,
                        n,
                        l
                );

        HBox row =
                new HBox(
                        12,
                        i,
                        text
                );

        row.setPadding(
                new Insets(17)
        );

        VBox box =
                new VBox(row);

        box.setStyle(
                "-fx-background-color:white;" +
                "-fx-background-radius:14;" +
                "-fx-border-color:" +
                AppTheme.BORDER +
                ";-fx-border-radius:14;"
        );

        return box;
    }

    private static VBox quickAccess(
            DashboardController controller) {

        VBox section =
                new VBox(12);

        Label heading =
                new Label("Quick Access");

        heading.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        18
                )
        );

        HBox cards =
                new HBox(12);

        addQuick(
                cards,
                "📚",
                "Create Course",
                "Design a learning program",
                controller::openCourses
        );

        addQuick(
                cards,
                "✓",
                "New Assessment",
                "Assess learner skills",
                controller::openAssessments
        );

        addQuick(
                cards,
                "◇",
                "Schedule Seminar",
                "Plan workshops and events",
                controller::openSeminars
        );

        addQuick(
                cards,
                "↗",
                "Performance",
                "Track learner progress",
                controller::openPerformance
        );

        for (javafx.scene.Node node :
                cards.getChildren()) {

            HBox.setHgrow(
                    node,
                    Priority.ALWAYS
            );
        }

        section.getChildren().addAll(
                heading,
                cards
        );

        return section;
    }

    private static void addQuick(
            HBox parent,
            String icon,
            String title,
            String description,
            Runnable action) {

        VBox card =
                new VBox(7);

        card.setPadding(
                new Insets(16)
        );

        card.setPrefHeight(105);

        card.setStyle(
                "-fx-background-color:white;" +
                "-fx-background-radius:12;" +
                "-fx-border-color:" +
                AppTheme.BORDER +
                ";" +
                "-fx-border-radius:12;" +
                "-fx-cursor:hand;"
        );

        Label i =
                new Label(icon);

        i.setStyle(
                "-fx-background-color:#EEF4FF;" +
                "-fx-padding:8;" +
                "-fx-background-radius:8;"
        );

        Label t =
                new Label(title);

        t.setStyle(
                "-fx-font-weight:bold;"
        );

        Label d =
                new Label(description);

        d.setStyle(
                "-fx-text-fill:" +
                AppTheme.MUTED +
                ";"
        );

        card.getChildren().addAll(
                i,
                t,
                d
        );

        card.setOnMouseClicked(
                e -> action.run()
        );

        parent.getChildren().add(card);
    }

    private static VBox upcoming() {

        VBox box =
                AppTheme.card(
                        new Label(
                                "Upcoming Training"
                        ),
                        new Label(
                                "Java Full Stack • 24 learners • Tomorrow 10:00 AM"
                        ),
                        new Label(
                                "Advanced Java • 18 learners • Friday 2:00 PM"
                        ),
                        new Label(
                                "Soft Skills Workshop • 30 learners • Saturday 11:00 AM"
                        )
                );

        ((Label) box.getChildren().get(0))
                .setStyle(
                        "-fx-font-size:17;" +
                        "-fx-font-weight:bold;"
                );

        return box;
    }
}