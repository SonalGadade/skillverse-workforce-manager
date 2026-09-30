package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MyDocumentsView {

    private static final String BLUE = "#1456D9";
    private static final String PURPLE = "#7436E8";
    private static final String DARK = "#111827";
    private static final String TEXT = "#374151";
    private static final String MUTED = "#6B7280";
    private static final String BG = "#F9F8FF";
    private static final String BORDER = "#E3E5EE";

    public VBox createDocumentsContent(
            Stage stage,
            String email
    ) {

        VBox page = new VBox(25);

        page.setPadding(
                new Insets(
                        38,
                        42,
                        50,
                        42
                )
        );

        page.setStyle(
                "-fx-background-color: " + BG + ";"
        );

        HBox top =
                createTopBar();

        HBox heading =
                createHeading(stage);

        GridPane cards =
                createDocumentCards();

        page.getChildren().addAll(
                top,
                heading,
                cards
        );

        return page;
    }

    private HBox createTopBar() {

        HBox bar = new HBox();

        bar.setAlignment(
                Pos.CENTER_LEFT
        );

        Label search = new Label(
                "⌕   Search documents..."
        );

        search.setPrefWidth(420);
        search.setPrefHeight(48);

        search.setPadding(
                new Insets(
                        0,
                        18,
                        0,
                        18
                )
        );

        search.setAlignment(
                Pos.CENTER_LEFT
        );

        search.setFont(
                Font.font(
                        "System",
                        14
                )
        );

        search.setStyle(
                "-fx-background-color: #F0F2FB;" +
                "-fx-background-radius: 25;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label bell = new Label("♧");

        bell.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        22
                )
        );

        bell.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        Circle profile = new Circle(20);

        profile.setFill(
                Color.web("#DDE6F8")
        );

        HBox right = new HBox(
                25,
                bell,
                profile
        );

        right.setAlignment(
                Pos.CENTER
        );

        bar.getChildren().addAll(
                search,
                spacer,
                right
        );

        return bar;
    }

    private HBox createHeading(
            Stage stage
    ) {

        HBox heading = new HBox();

        heading.setAlignment(
                Pos.CENTER_LEFT
        );

        VBox titleBox = new VBox(5);

        Label title = new Label(
                "MY DOCUMENTS"
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
                "Access your important employee documents."
        );

        subtitle.setFont(
                Font.font(
                        "System",
                        16
                )
        );

        subtitle.setStyle(
                "-fx-text-fill: " + TEXT + ";"
        );

        titleBox.getChildren().addAll(
                title,
                subtitle
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Button upload = new Button(
                "⇧  Upload Document"
        );

        upload.setPrefHeight(48);
        upload.setPrefWidth(190);

        upload.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        14
                )
        );

        upload.setStyle(
                "-fx-background-color: linear-gradient(" +
                "to right, #1456D9, #7436E8);" +
                "-fx-background-radius: 11;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        );

        upload.setOnAction(e ->
                chooseFile(stage)
        );

        heading.getChildren().addAll(
                titleBox,
                spacer,
                upload
        );

        return heading;
    }

    private GridPane createDocumentCards() {

        GridPane grid = new GridPane();

        grid.setHgap(25);
        grid.setVgap(25);

        grid.add(
                createDocumentCard(
                        "Offer Letter",
                        "15 Jun 2024",
                        "PDF",
                        "▤",
                        "#E4ECFF",
                        BLUE
                ),
                0,
                0
        );

        grid.add(
                createDocumentCard(
                        "Joining Document",
                        "15 Jun 2024",
                        "PDF",
                        "▣",
                        "#E1EBFF",
                        BLUE
                ),
                1,
                0
        );

        grid.add(
                createDocumentCard(
                        "Experience Letter",
                        "20 Aug 2026",
                        "PDF",
                        "▱",
                        "#F0E2FF",
                        PURPLE
                ),
                2,
                0
        );

        grid.add(
                createDocumentCard(
                        "Salary Slip - July 2026",
                        "01 Aug 2026",
                        "PDF",
                        "▤",
                        "#E5FAF0",
                        "#159A61"
                ),
                0,
                1
        );

        grid.add(
                createDocumentCard(
                        "Java Professional Certificate",
                        "12 Aug 2026",
                        "PDF",
                        "✪",
                        "#E4ECFF",
                        BLUE
                ),
                1,
                1
        );

        grid.add(
                createDocumentCard(
                        "Security Training Certificate",
                        "05 Aug 2026",
                        "PDF",
                        "♢",
                        "#F0E2FF",
                        PURPLE
                ),
                2,
                1
        );

        return grid;
    }

    private VBox createDocumentCard(
            String name,
            String date,
            String type,
            String icon,
            String iconBackground,
            String iconColor
    ) {

        VBox card = new VBox(13);

        card.setPrefWidth(330);
        card.setPrefHeight(220);

        card.setPadding(
                new Insets(
                        18,
                        18,
                        17,
                        18
                )
        );

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 15;"
        );

        HBox top = new HBox();

        top.setAlignment(
                Pos.CENTER_LEFT
        );

        Label iconLabel = new Label(icon);

        iconLabel.setPrefSize(
                50,
                50
        );

        iconLabel.setAlignment(
                Pos.CENTER
        );

        iconLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        20
                )
        );

        iconLabel.setStyle(
                "-fx-background-color: " +
                iconBackground +
                ";" +
                "-fx-background-radius: 25;" +
                "-fx-text-fill: " +
                iconColor +
                ";"
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label typeLabel = new Label(type);

        typeLabel.setPadding(
                new Insets(
                        6,
                        11,
                        6,
                        11
                )
        );

        typeLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        11
                )
        );

        typeLabel.setStyle(
                "-fx-background-color: #EEF1FF;" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: " + DARK + ";"
        );

        top.getChildren().addAll(
                iconLabel,
                spacer,
                typeLabel
        );

        Label nameLabel = new Label(name);

        nameLabel.setWrapText(true);

        nameLabel.setMaxWidth(285);

        nameLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        19
                )
        );

        nameLabel.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        Label dateLabel = new Label(
                "▣  " + date
        );

        dateLabel.setFont(
                Font.font(
                        "System",
                        13
                )
        );

        dateLabel.setStyle(
                "-fx-text-fill: " + TEXT + ";"
        );

        Region buttonSpacer = new Region();

        VBox.setVgrow(
                buttonSpacer,
                Priority.ALWAYS
        );

        HBox buttons = new HBox(9);

        Button view = new Button("View");

        view.setPrefHeight(40);

        HBox.setHgrow(
                view,
                Priority.ALWAYS
        );

        view.setMaxWidth(
                Double.MAX_VALUE
        );

        view.setFont(
                Font.font(
                        "System",
                        FontWeight.NORMAL,
                        13
                )
        );

        view.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #C9CEDA;" +
                "-fx-border-radius: 9;" +
                "-fx-background-radius: 9;" +
                "-fx-text-fill: " + DARK + ";" +
                "-fx-cursor: hand;"
        );

        Button download = new Button("⇩");

        download.setPrefWidth(52);
        download.setPrefHeight(40);

        download.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        16
                )
        );

        download.setStyle(
                "-fx-background-color: #F8F8FC;" +
                "-fx-border-color: #C9CEDA;" +
                "-fx-border-radius: 9;" +
                "-fx-background-radius: 9;" +
                "-fx-text-fill: " + DARK + ";" +
                "-fx-cursor: hand;"
        );

        buttons.getChildren().addAll(
                view,
                download
        );

        card.getChildren().addAll(
                top,
                nameLabel,
                dateLabel,
                buttonSpacer,
                buttons
        );

        return card;
    }

    private void chooseFile(
            Stage stage
    ) {

        FileChooser chooser =
                new FileChooser();

        chooser.setTitle(
                "Upload Document"
        );

        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "PDF Files",
                        "*.pdf"
                ),
                new FileChooser.ExtensionFilter(
                        "All Files",
                        "*.*"
                )
        );

        File file =
                chooser.showOpenDialog(stage);

        if (file != null) {

            System.out.println(
                    "Selected document: " +
                    file.getAbsolutePath()
            );
        }
    }
public void show(Stage stage, String email) {

    if (stage == null) {
        return;
    }

    if (stage.getScene() == null) {
        return;
    }

    VBox content = createDocumentsContent(stage, email);

    javafx.scene.control.ScrollPane scrollPane =
            new javafx.scene.control.ScrollPane(content);

    scrollPane.setFitToWidth(true);

    scrollPane.setHbarPolicy(
            javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER
    );

    scrollPane.setVbarPolicy(
            javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED
    );

    scrollPane.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;"
    );

    if (stage.getScene().getRoot()
            instanceof javafx.scene.layout.BorderPane) {

        javafx.scene.layout.BorderPane root =
                (javafx.scene.layout.BorderPane)
                        stage.getScene().getRoot();

        root.setCenter(scrollPane);
    }
}
    
}