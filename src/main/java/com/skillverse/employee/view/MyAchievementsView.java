package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MyAchievementsView {

    private static final String BLUE = "#1648C8";
    private static final String PURPLE = "#7C3AED";
    private static final String DARK = "#111827";
    private static final String TEXT = "#374151";
    private static final String MUTED = "#6B7280";
    private static final String BORDER = "#E1E4EE";

    public VBox createContent() {

        VBox main = new VBox(28);

        main.setFillWidth(true);

        main.setPadding(
                new Insets(30, 42, 50, 42)
        );

        main.setStyle(
                "-fx-background-color: #F8F8FD;"
        );

        Label title = new Label(
                "MY ACHIEVEMENTS"
        );

        title.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        38
                )
        );

        title.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        Label subtitle = new Label(
                "Celebrate your learning, performance and professional milestones."
        );

        subtitle.setFont(
                Font.font("System", 16)
        );

        subtitle.setStyle(
                "-fx-text-fill: " + TEXT + ";"
        );

        VBox heading = new VBox(
                7,
                title,
                subtitle
        );

        HBox stats = createStats();

        Label latestTitle = new Label(
                "Latest Achievement"
        );

        latestTitle.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        22
                )
        );

        latestTitle.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        VBox latestCard = createLatestAchievement();

        Label galleryTitle = new Label(
                "Achievement Gallery"
        );

        galleryTitle.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        22
                )
        );

        galleryTitle.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        HBox gallery = createGallery();

        main.getChildren().addAll(
                heading,
                stats,
                latestTitle,
                latestCard,
                galleryTitle,
                gallery
        );

        return main;
    }

    private HBox createStats() {

        HBox stats = new HBox(22);

        VBox total = createStatCard(
                "TOTAL ACHIEVEMENTS",
                "12",
                "🏆",
                BLUE
        );

        VBox certificates = createStatCard(
                "CERTIFICATES",
                "6",
                "▣",
                PURPLE
        );

        VBox badges = createStatCard(
                "BADGES",
                "4",
                "♕",
                BLUE
        );

        VBox points = createStatCard(
                "POINTS",
                "1,250",
                "☆",
                PURPLE
        );

        stats.getChildren().addAll(
                total,
                certificates,
                badges,
                points
        );

        HBox.setHgrow(total, Priority.ALWAYS);
        HBox.setHgrow(certificates, Priority.ALWAYS);
        HBox.setHgrow(badges, Priority.ALWAYS);
        HBox.setHgrow(points, Priority.ALWAYS);

        return stats;
    }

    private VBox createStatCard(
            String title,
            String value,
            String icon,
            String accent
    ) {

        VBox card = new VBox(16);

        card.setPadding(
                new Insets(24)
        );

        card.setPrefHeight(138);
        card.setMinHeight(138);

        card.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 20;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 20;"
        );

        HBox top = new HBox();

        top.setAlignment(
                Pos.CENTER_LEFT
        );

        Label titleLabel = new Label(
                title
        );

        titleLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        12
                )
        );

        titleLabel.setStyle(
                "-fx-text-fill: " + TEXT + ";"
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label iconLabel = new Label(
                icon
        );

        iconLabel.setPrefSize(
                38,
                38
        );

        iconLabel.setAlignment(
                Pos.CENTER
        );

        iconLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        18
                )
        );

        iconLabel.setStyle(
                "-fx-background-color: #EEF0FF;"
                + "-fx-background-radius: 20;"
                + "-fx-text-fill: " + accent + ";"
        );

        top.getChildren().addAll(
                titleLabel,
                spacer,
                iconLabel
        );

        Label valueLabel = new Label(
                value
        );

        valueLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        30
                )
        );

        valueLabel.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        card.getChildren().addAll(
                top,
                valueLabel
        );

        return card;
    }

    private VBox createLatestAchievement() {

        VBox card = new VBox();

        card.setPadding(
                new Insets(30)
        );

        card.setMinHeight(285);

        card.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 22;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 22;"
        );

        HBox content = new HBox(30);

        content.setAlignment(
                Pos.CENTER_LEFT
        );

        // Medal
        VBox iconBox = new VBox();

        iconBox.setPrefSize(
                170,
                170
        );

        iconBox.setMinSize(
                170,
                170
        );

        iconBox.setAlignment(
                Pos.CENTER
        );

        iconBox.setStyle(
                "-fx-background-color: #E9E7FF;"
                + "-fx-background-radius: 90;"
        );

        Label medal = new Label(
                "🏅"
        );

        medal.setFont(
                Font.font(
                        "System",
                        54
                )
        );

        iconBox.getChildren().add(
                medal
        );

        // Details
        VBox details = new VBox(12);

        HBox meta = new HBox(10);

        meta.setAlignment(
                Pos.CENTER_LEFT
        );

        Label type = new Label(
                "CERTIFICATE"
        );

        type.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        11
                )
        );

        type.setStyle(
                "-fx-background-color: #E4E8FA;"
                + "-fx-background-radius: 14;"
                + "-fx-padding: 6 12 6 12;"
                + "-fx-text-fill: #374151;"
        );

        Label date = new Label(
                "12 Aug 2026"
        );

        date.setFont(
                Font.font(
                        "System",
                        14
                )
        );

        date.setStyle(
                "-fx-text-fill: " + MUTED + ";"
        );

        meta.getChildren().addAll(
                type,
                date
        );

        Label title = new Label(
                "Java Professional Certification"
        );

        title.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        28
                )
        );

        title.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        title.setWrapText(true);

        Label description = new Label(
                "Achieved expert-level proficiency in Java SE 17 Developer "
                + "examination, demonstrating advanced skills in object-oriented "
                + "programming, modularity, and secure coding practices."
        );

        description.setWrapText(true);

        description.setFont(
                Font.font(
                        "System",
                        15
                )
        );

        description.setStyle(
                "-fx-text-fill: " + TEXT + ";"
        );

        // Buttons
        HBox buttons = new HBox(15);

        Button view = new Button(
                "◉   View Certificate"
        );

        view.setPrefHeight(48);

        view.setPadding(
                new Insets(
                        0,
                        24,
                        0,
                        24
                )
        );

        view.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        14
                )
        );

        view.setStyle(
                "-fx-background-color: linear-gradient("
                + "to right, #1648C8, #7C3AED);"
                + "-fx-background-radius: 24;"
                + "-fx-text-fill: white;"
                + "-fx-cursor: hand;"
        );

        Button share = new Button(
                "♧   Share"
        );

        share.setPrefHeight(48);

        share.setPadding(
                new Insets(
                        0,
                        24,
                        0,
                        24
                )
        );

        share.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        14
                )
        );

        share.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 24;"
                + "-fx-border-color: #D9DDE8;"
                + "-fx-border-radius: 24;"
                + "-fx-text-fill: " + DARK + ";"
                + "-fx-cursor: hand;"
        );

        buttons.getChildren().addAll(
                view,
                share
        );

        details.getChildren().addAll(
                meta,
                title,
                description,
                buttons
        );

        HBox.setHgrow(
                details,
                Priority.ALWAYS
        );

        content.getChildren().addAll(
                iconBox,
                details
        );

        card.getChildren().add(
                content
        );

        return card;
    }

    private HBox createGallery() {

        HBox gallery = new HBox(22);

        VBox java = createGalleryCard(
                "🏅",
                "CERTIFICATE",
                "Java Certification",
                "12 Aug 2026",
                BLUE
        );

        VBox performer = createGalleryCard(
                "🏆",
                "PERFORMANCE",
                "Top Performer",
                "2026",
                PURPLE
        );

        VBox learning = createGalleryCard(
                "🎖",
                "LEARNING",
                "Learning Champion",
                "2026",
                BLUE
        );

        gallery.getChildren().addAll(
                java,
                performer,
                learning
        );

        HBox.setHgrow(
                java,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                performer,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                learning,
                Priority.ALWAYS
        );

        return gallery;
    }

    private VBox createGalleryCard(
            String icon,
            String category,
            String title,
            String date,
            String accent
    ) {

        VBox card = new VBox(15);

        card.setPadding(
                new Insets(22)
        );

        card.setPrefHeight(205);
        card.setMinHeight(205);

        card.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 20;"
                + "-fx-border-color: " + BORDER + ";"
                + "-fx-border-radius: 20;"
        );

        HBox top = new HBox();

        top.setAlignment(
                Pos.CENTER_LEFT
        );

        Label iconLabel = new Label(
                icon
        );

        iconLabel.setPrefSize(
                55,
                55
        );

        iconLabel.setAlignment(
                Pos.CENTER
        );

        iconLabel.setFont(
                Font.font(
                        "System",
                        25
                )
        );

        iconLabel.setStyle(
                "-fx-background-color: #ECEAFF;"
                + "-fx-background-radius: 28;"
                + "-fx-text-fill: " + accent + ";"
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label categoryLabel = new Label(
                category
        );

        categoryLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        10
                )
        );

        categoryLabel.setStyle(
                "-fx-background-color: #E9EBFA;"
                + "-fx-background-radius: 12;"
                + "-fx-padding: 6 11 6 11;"
                + "-fx-text-fill: " + TEXT + ";"
        );

        top.getChildren().addAll(
                iconLabel,
                spacer,
                categoryLabel
        );

        Label titleLabel = new Label(
                title
        );

        titleLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        18
                )
        );

        titleLabel.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        Label dateLabel = new Label(
                date
        );

        dateLabel.setFont(
                Font.font(
                        "System",
                        13
                )
        );

        dateLabel.setStyle(
                "-fx-text-fill: " + MUTED + ";"
        );

        card.getChildren().addAll(
                top,
                titleLabel,
                dateLabel
        );

        return card;
    }
}
