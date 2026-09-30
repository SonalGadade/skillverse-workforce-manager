package com.skillverse.admin.view;

import java.io.File;
import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutUsView extends VBox {

    private final Stage stage;

    public AboutUsView() {
        this(null);
    }

    public AboutUsView(Stage stage) {
        this.stage = stage;
        setSpacing(24);
        setPadding(new Insets(28, 36, 32, 36));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBox = new HBox(16);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        VBox titleContainer = new VBox(4);
        HBox.setHgrow(titleContainer, Priority.ALWAYS);

        Label pageTitle = new Label("About Us ✦");
        pageTitle.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        Label pageSubtitle = new Label("Honoring our mentorship, team foundation, and core journey behind SkillVerse.");
        pageSubtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");

        titleContainer.getChildren().addAll(pageTitle, pageSubtitle);
        headerBox.getChildren().add(titleContainer);

        VBox heroCard = createMentorHeroCard();

        HBox infoRow = createInfoCardsRow();

        getChildren().addAll(headerBox, heroCard, infoRow);
    }

    private VBox createMentorHeroCard() {
        VBox card = new VBox(20);
        card.setPadding(new Insets(28, 32, 28, 32));
        card.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: #E2E8F0;" +
            "-fx-border-radius: 16;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 10, 0, 0, 4);"
        );

        HBox badgeBox = new HBox();
        Label badge = new Label("★ SPECIAL MENTORSHIP & APPRECIATION");
        badge.setStyle(
            "-fx-background-color: #EEF2FF;" +
            "-fx-text-fill: #4F46E5;" +
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 6 14;" +
            "-fx-background-radius: 20;"
        );
        badgeBox.getChildren().add(badge);

        HBox contentBox = new HBox(36);
        contentBox.setAlignment(Pos.TOP_LEFT);

        VBox imageBox = new VBox(12);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setMinWidth(260);
        imageBox.setMaxWidth(260);

        ImageView sirImageView = loadSirImage();
        if (sirImageView != null) {
            sirImageView.setFitWidth(240);
            sirImageView.setFitHeight(270);
            sirImageView.setPreserveRatio(true);
            sirImageView.setSmooth(true);

            VBox imgWrapper = new VBox(sirImageView);
            imgWrapper.setAlignment(Pos.CENTER);
            imgWrapper.setPadding(new Insets(12));
            imgWrapper.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #EFF6FF, #DBEAFE);" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: #BFDBFE;" +
                "-fx-border-radius: 16;"
            );
            imageBox.getChildren().add(imgWrapper);
        } else {
            Label placeholder = new Label("👨‍🏫");
            placeholder.setStyle("-fx-font-size: 80px; -fx-padding: 30;");
            imageBox.getChildren().add(placeholder);
        }

        Label sirName = new Label("Shashi Bagal Sir");
        sirName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        Label mentorRole = new Label("Founder & Mentor\nCore2Web Technologies");
        mentorRole.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        mentorRole.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2563EB;");

        imageBox.getChildren().addAll(sirName, mentorRole);

        VBox textBox = new VBox(14);
        HBox.setHgrow(textBox, Priority.ALWAYS);

        Label sectionHeading = new Label("A Special Thank You");
        sectionHeading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0F172A;");

        Label p1 = createFormattedParagraph(
            "On behalf of the entire Pentatech team, we would like to express our heartfelt gratitude to Shashi Sir " +
            "and the entire Core2Web team for their invaluable guidance and support throughout our journey of building SkillVerse."
        );

        Label p2 = createFormattedParagraph(
            "The strong foundation we received in Core Java and its core concepts helped us develop the technical skills, " +
            "logical thinking, and confidence required to work on a project like SkillVerse. What we learned through Core2Web was not " +
            "limited to coding — it taught us how to approach problems, understand concepts deeply, and apply our knowledge to real-world projects."
        );

        Label p3 = createFormattedParagraph(
            "SkillVerse is not just a project we built; it is also a reflection of the concepts, knowledge, and confidence we gained through your guidance."
        );

        Label p4 = createFormattedParagraph(
            "Thank you, Shashi Sir and Core2Web, for being an important part of our learning journey and for helping us turn our ideas into reality."
        );

        VBox signOffBox = new VBox(4);
        signOffBox.setPadding(new Insets(10, 0, 0, 0));

        Label withGratitude = new Label("With gratitude,");
        withGratitude.setStyle("-fx-font-size: 14px; -fx-font-style: italic; -fx-text-fill: #475569;");

        Label teamName = new Label("Team Pentatech");
        teamName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #4F46E5;");

        signOffBox.getChildren().addAll(withGratitude, teamName);

        textBox.getChildren().addAll(sectionHeading, p1, p2, p3, p4, signOffBox);

        contentBox.getChildren().addAll(imageBox, textBox);
        card.getChildren().addAll(badgeBox, contentBox);

        return card;
    }

    private Label createFormattedParagraph(String text) {
        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #334155; -fx-line-spacing: 4px;");
        return lbl;
    }

    private HBox createInfoCardsRow() {
        HBox row = new HBox(16);

        VBox card1 = new VBox(8);
        HBox.setHgrow(card1, Priority.ALWAYS);
        card1.setPadding(new Insets(18, 20, 18, 20));
        card1.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");
        Label c1Title = new Label("🚀 Project SkillVerse");
        c1Title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label c1Desc = new Label("An intelligent corporate skill assessment, enterprise workforce planning, and AI talent matchmaking platform powered by Java and JavaFX.");
        c1Desc.setWrapText(true);
        c1Desc.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        card1.getChildren().addAll(c1Title, c1Desc);

        VBox card2 = new VBox(8);
        HBox.setHgrow(card2, Priority.ALWAYS);
        card2.setPadding(new Insets(18, 20, 18, 20));
        card2.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");
        Label c2Title = new Label("💡 Core2Web Ecosystem");
        c2Title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label c2Desc = new Label("Cultivating programming excellence, in-depth architectural mastery, and real-world software engineering fundamentals.");
        c2Desc.setWrapText(true);
        c2Desc.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        card2.getChildren().addAll(c2Title, c2Desc);

        row.getChildren().addAll(card1, card2);
        return row;
    }

    private ImageView loadSirImage() {
        try {
            InputStream is = getClass().getResourceAsStream("/assets/shashi_sir.png");
            if (is == null) {
                is = getClass().getResourceAsStream("/shashi_sir.png");
            }
            if (is != null) {
                return new ImageView(new Image(is));
            }

            File file = new File("src/main/resources/assets/shashi_sir.png");
            if (file.exists()) {
                return new ImageView(new Image(file.toURI().toString()));
            }

            File targetFile = new File("target/classes/assets/shashi_sir.png");
            if (targetFile.exists()) {
                return new ImageView(new Image(targetFile.toURI().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }
}
