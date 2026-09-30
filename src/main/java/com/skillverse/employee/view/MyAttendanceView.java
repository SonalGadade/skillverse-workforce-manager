package com.skillverse.employee.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyAttendanceView {

    private static final String BLUE = "#1648C8";
    private static final String PURPLE = "#7C3AED";
    private static final String GREEN = "#15803D";
    private static final String RED = "#DC2626";
    private static final String ORANGE = "#D97706";
    private static final String DARK = "#111827";
    private static final String TEXT = "#374151";
    private static final String MUTED = "#6B7280";
    private static final String BG = "#F8F8FD";
    private static final String BORDER = "#E5E7EB";

    public VBox createContent(String email) {

        VBox main = new VBox(26);

        main.setPadding(
                new Insets(32, 38, 45, 38)
        );

        main.setFillWidth(true);

        main.setStyle(
                "-fx-background-color: " + BG + ";"
        );

        HBox heading = createHeading(email);

        HBox stats = createStats();

        HBox middleSection = new HBox(22);

        VBox calendar = createCalendar();

        VBox summary = createSummary();

        HBox.setHgrow(
                calendar,
                Priority.ALWAYS
        );

        middleSection.getChildren().addAll(
                calendar,
                summary
        );

        VBox recentActivity =
                createRecentActivity();

        main.getChildren().addAll(
                heading,
                stats,
                middleSection,
                recentActivity
        );

        return main;
    }

    private HBox createHeading(String email) {

        HBox heading = new HBox();

        heading.setAlignment(
                Pos.CENTER_LEFT
        );

        VBox titleBox = new VBox(6);

        Label title =
                new Label("MY ATTENDANCE");

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

        Label subtitle =
                new Label(
                        "Track your attendance and working days."
                );

        subtitle.setFont(
                Font.font(
                        "System",
                        15
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

        Button export =
                new Button("⇩  Export Report");

        export.setPrefHeight(42);

        export.setPadding(
                new Insets(0, 18, 0, 18)
        );

        export.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        12
                )
        );

        export.setStyle(
                "-fx-background-color: #EEF0FF;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #DDE1F5;" +
                "-fx-border-radius: 12;" +
                "-fx-text-fill: " + DARK + ";" +
                "-fx-cursor: hand;"
        );

        heading.getChildren().addAll(
                titleBox,
                spacer,
                export
        );

        return heading;
    }

    private HBox createStats() {

        HBox stats = new HBox(20);

        VBox present =
                createStatCard(
                        "PRESENT",
                        "22",
                        "Days",
                        "✓",
                        GREEN,
                        "#DDF4E5"
                );

        VBox absent =
                createStatCard(
                        "ABSENT",
                        "1",
                        "Day",
                        "↗",
                        RED,
                        "#FDE1E1"
                );

        VBox leave =
                createStatCard(
                        "LEAVE",
                        "2",
                        "Days",
                        "□",
                        ORANGE,
                        "#FFF0C7"
                );

        VBox overall =
                createOverallCard();

        stats.getChildren().addAll(
                present,
                absent,
                leave,
                overall
        );

        HBox.setHgrow(
                present,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                absent,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                leave,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                overall,
                Priority.ALWAYS
        );

        return stats;
    }

    private VBox createStatCard(
            String title,
            String value,
            String unit,
            String icon,
            String accent,
            String iconBackground
    ) {

        VBox card = new VBox(10);

        card.setPadding(
                new Insets(22)
        );

        card.setPrefHeight(125);

        card.setMinHeight(125);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 20;"
        );

        HBox top = new HBox();

        top.setAlignment(
                Pos.CENTER_LEFT
        );

        Label titleLabel =
                new Label(title);

        titleLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        11
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

        Label iconLabel =
                new Label(icon);

        iconLabel.setPrefSize(
                46,
                46
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
                "-fx-background-radius: 14;" +
                "-fx-text-fill: " +
                accent +
                ";"
        );

        top.getChildren().addAll(
                titleLabel,
                spacer,
                iconLabel
        );

        HBox valueBox =
                new HBox(8);

        valueBox.setAlignment(
                Pos.BASELINE_LEFT
        );

        Label valueLabel =
                new Label(value);

        valueLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        29
                )
        );

        valueLabel.setStyle(
                "-fx-text-fill: " +
                accent +
                ";"
        );

        Label unitLabel =
                new Label(unit);

        unitLabel.setFont(
                Font.font(
                        "System",
                        13
                )
        );

        unitLabel.setStyle(
                "-fx-text-fill: " +
                MUTED +
                ";"
        );

        valueBox.getChildren().addAll(
                valueLabel,
                unitLabel
        );

        card.getChildren().addAll(
                top,
                valueBox
        );

        return card;
    }

    private VBox createOverallCard() {

        VBox card = new VBox(8);

        card.setPadding(
                new Insets(22)
        );

        card.setPrefHeight(125);

        card.setMinHeight(125);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 20;"
        );

        HBox top = new HBox();

        top.setAlignment(
                Pos.CENTER_LEFT
        );

        Label title =
                new Label("OVERALL");

        title.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        11
                )
        );

        title.setStyle(
                "-fx-text-fill: " + TEXT + ";"
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label circle =
                new Label("↗");

        circle.setPrefSize(
                48,
                48
        );

        circle.setAlignment(
                Pos.CENTER
        );

        circle.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        22
                )
        );

        circle.setStyle(
                "-fx-background-color: #F0E9FF;" +
                "-fx-background-radius: 30;" +
                "-fx-border-color: " + PURPLE + ";" +
                "-fx-border-width: 3;" +
                "-fx-border-radius: 30;" +
                "-fx-text-fill: " + PURPLE + ";"
        );

        top.getChildren().addAll(
                title,
                spacer,
                circle
        );

        HBox valueBox =
                new HBox(8);

        valueBox.setAlignment(
                Pos.BASELINE_LEFT
        );

        Label value =
                new Label("96%");

        value.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        30
                )
        );

        value.setStyle(
                "-fx-text-fill: " + PURPLE + ";"
        );

        Label text =
                new Label("Attendance");

        text.setFont(
                Font.font(
                        "System",
                        13
                )
        );

        text.setStyle(
                "-fx-text-fill: " + MUTED + ";"
        );

        valueBox.getChildren().addAll(
                value,
                text
        );

        card.getChildren().addAll(
                top,
                valueBox
        );

        return card;
    }

    private VBox createCalendar() {

        VBox card = new VBox(20);

        card.setPadding(
                new Insets(25)
        );

        card.setPrefWidth(650);

        card.setMinHeight(515);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 22;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 22;"
        );

        HBox monthHeader =
                new HBox();

        monthHeader.setAlignment(
                Pos.CENTER_LEFT
        );

        Label month =
                new Label("August 2026");

        month.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        21
                )
        );

        month.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        Label current =
                new Label("CURRENT");

        current.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        9
                )
        );

        current.setStyle(
                "-fx-background-color: #E9EFFF;" +
                "-fx-background-radius: 12;" +
                "-fx-padding: 5 10 5 10;" +
                "-fx-text-fill: " + BLUE + ";"
        );

        HBox monthText =
                new HBox(
                        10,
                        month,
                        current
                );

        monthText.setAlignment(
                Pos.CENTER_LEFT
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Button previous =
                new Button("‹");

        Button next =
                new Button("›");

        styleCalendarButton(previous);

        styleCalendarButton(next);

        monthHeader.getChildren().addAll(
                monthText,
                spacer,
                previous,
                next
        );

        GridPane calendarGrid =
                new GridPane();

        calendarGrid.setHgap(10);

        calendarGrid.setVgap(12);

        calendarGrid.setAlignment(
                Pos.CENTER
        );

        String[] days = {
                "SUN",
                "MON",
                "TUE",
                "WED",
                "THU",
                "FRI",
                "SAT"
        };

        for (int i = 0; i < days.length; i++) {

            Label day =
                    new Label(days[i]);

            day.setPrefSize(
                    64,
                    28
            );

            day.setAlignment(
                    Pos.CENTER
            );

            day.setFont(
                    Font.font(
                            "System",
                            FontWeight.BOLD,
                            10
                    )
            );

            day.setStyle(
                    "-fx-text-fill: " +
                    MUTED +
                    ";"
            );

            calendarGrid.add(
                    day,
                    i,
                    0
            );
        }

        String[][] dates = {
                {"26","27","28","29","30","31","1"},
                {"2","3","4","5","6","7","8"},
                {"9","10","11","12","13","14","15"},
                {"16","17","18","19","20","21","22"},
                {"23","24","25","26","27","28","29"},
                {"30","31","","","","",""}
        };

        for (int row = 0; row < dates.length; row++) {

            for (int col = 0; col < 7; col++) {

                String value =
                        dates[row][col];

                if (value.isEmpty()) {
                    continue;
                }

                Label date =
                        createDateCell(
                                value,
                                row,
                                col
                        );

                calendarGrid.add(
                        date,
                        col,
                        row + 1
                );
            }
        }

        HBox legend =
                new HBox(25);

        legend.setPadding(
                new Insets(
                        15,
                        0,
                        0,
                        0
                )
        );

        legend.getChildren().addAll(
                createLegend(
                        "Present",
                        GREEN
                ),
                createLegend(
                        "Absent",
                        RED
                ),
                createLegend(
                        "Leave",
                        ORANGE
                ),
                createLegend(
                        "Holiday",
                        BLUE
                )
        );

        card.getChildren().addAll(
                monthHeader,
                calendarGrid,
                legend
        );

        return card;
    }

    private Label createDateCell(
            String value,
            int row,
            int col
    ) {

        Label date =
                new Label(value);

        date.setPrefSize(
                64,
                50
        );

        date.setAlignment(
                Pos.CENTER
        );

        date.setFont(
                Font.font(
                        "System",
                        FontWeight.NORMAL,
                        13
                )
        );

        String style;

        if (row == 0) {

            style =
                    "-fx-background-color: #FAFAFC;" +
                    "-fx-background-radius: 12;" +
                    "-fx-border-color: #EEEEF3;" +
                    "-fx-border-radius: 12;" +
                    "-fx-text-fill: #C8CAD2;";

        } else if (value.equals("10") ||
                   value.equals("23")) {

            style =
                    "-fx-background-color: #FFF9E9;" +
                    "-fx-background-radius: 12;" +
                    "-fx-border-color: #F7D978;" +
                    "-fx-border-radius: 12;" +
                    "-fx-text-fill: " + ORANGE + ";";

        } else if (value.equals("12")) {

            style =
                    "-fx-background-color: #FEF0F0;" +
                    "-fx-background-radius: 12;" +
                    "-fx-border-color: #F4CACA;" +
                    "-fx-border-radius: 12;" +
                    "-fx-text-fill: " + RED + ";";

        } else if (value.equals("17")) {

            style =
                    "-fx-background-color: #DDEBFF;" +
                    "-fx-background-radius: 12;" +
                    "-border-color: #A8C9FF;" +
                    "-fx-border-color: #A8C9FF;" +
                    "-fx-border-radius: 12;" +
                    "-fx-text-fill: " + BLUE + ";";

        } else if (value.equals("24")) {

            style =
                    "-fx-background-color: " + BLUE + ";" +
                    "-fx-background-radius: 12;" +
                    "-fx-border-color: " + BLUE + ";" +
                    "-fx-border-width: 3;" +
                    "-fx-border-radius: 12;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-weight: bold;";

        } else if (
                value.equals("2") ||
                value.equals("3") ||
                value.equals("4") ||
                value.equals("5") ||
                value.equals("6") ||
                value.equals("9") ||
                value.equals("11") ||
                value.equals("13") ||
                value.equals("16") ||
                value.equals("18") ||
                value.equals("19") ||
                value.equals("20")
        ) {

            style =
                    "-fx-background-color: #F2F9F4;" +
                    "-fx-background-radius: 12;" +
                    "-fx-border-color: #D4EBD9;" +
                    "-fx-border-radius: 12;" +
                    "-fx-text-fill: " + GREEN + ";";

        } else {

            style =
                    "-fx-background-color: #FAFAFC;" +
                    "-fx-background-radius: 12;" +
                    "-fx-border-color: #E6E7EE;" +
                    "-fx-border-radius: 12;" +
                    "-fx-text-fill: " + TEXT + ";";
        }

        date.setStyle(style);

        return date;
    }

    private void styleCalendarButton(
            Button button
    ) {

        button.setPrefSize(
                36,
                36
        );

        button.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        20
                )
        );

        button.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 25;" +
                "-fx-border-color: #D9DCE5;" +
                "-fx-border-radius: 25;" +
                "-fx-text-fill: " + DARK + ";" +
                "-fx-cursor: hand;"
        );
    }

    private HBox createLegend(
            String text,
            String color
    ) {

        HBox box =
                new HBox(7);

        box.setAlignment(
                Pos.CENTER_LEFT
        );

        Label dot =
                new Label("●");

        dot.setStyle(
                "-fx-text-fill: " +
                color +
                ";" +
                "-fx-font-size: 13px;"
        );

        Label label =
                new Label(text);

        label.setFont(
                Font.font(
                        "System",
                        11
                )
        );

        label.setStyle(
                "-fx-text-fill: " +
                TEXT +
                ";"
        );

        box.getChildren().addAll(
                dot,
                label
        );

        return box;
    }

    private VBox createSummary() {

        VBox summary =
                new VBox(20);

        summary.setPadding(
                new Insets(25)
        );

        summary.setPrefWidth(285);

        summary.setMinWidth(285);

        summary.setMinHeight(515);

        summary.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 22;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 22;"
        );

        HBox titleBox =
                new HBox(12);

        titleBox.setAlignment(
                Pos.CENTER_LEFT
        );

        Label icon =
                new Label("▣");

        icon.setPrefSize(
                36,
                36
        );

        icon.setAlignment(
                Pos.CENTER
        );

        icon.setStyle(
                "-fx-background-color: #EEF0FF;" +
                "-fx-background-radius: 9;" +
                "-fx-text-fill: " + DARK + ";" +
                "-fx-font-weight: bold;"
        );

        Label title =
                new Label("Summary");

        title.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        18
                )
        );

        title.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        titleBox.getChildren().addAll(
                icon,
                title
        );

        VBox total =
                createSummaryRow(
                        "Total Working Days",
                        "25",
                        1.0,
                        "#6B7280"
                );

        VBox present =
                createSummaryRow(
                        "Present",
                        "22",
                        0.88,
                        GREEN
                );

        VBox leave =
                createSummaryRow(
                        "Leave",
                        "2",
                        0.12,
                        ORANGE
                );

        VBox absent =
                createSummaryRow(
                        "Absent",
                        "1",
                        0.06,
                        RED
                );

        Region spacer = new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );

        Button apply =
                new Button(
                        "Apply for Leave   →"
                );

        apply.setPrefHeight(44);

        apply.setMaxWidth(
                Double.MAX_VALUE
        );

        apply.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        12
                )
        );

        apply.setStyle(
                "-fx-background-color: " + BLUE + ";" +
                "-fx-background-radius: 10;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        );

        summary.getChildren().addAll(
                titleBox,
                total,
                present,
                leave,
                absent,
                spacer,
                apply
        );

        return summary;
    }

    private VBox createSummaryRow(
            String title,
            String value,
            double percentage,
            String accent
    ) {

        VBox box =
                new VBox(8);

        HBox top =
                new HBox();

        top.setAlignment(
                Pos.CENTER_LEFT
        );

        Label titleLabel =
                new Label(title);

        titleLabel.setFont(
                Font.font(
                        "System",
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

        Label valueLabel =
                new Label(value);

        valueLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        17
                )
        );

        valueLabel.setStyle(
                "-fx-text-fill: " + accent + ";"
        );

        top.getChildren().addAll(
                titleLabel,
                spacer,
                valueLabel
        );

        HBox progressBox =
                new HBox();

        progressBox.setPrefHeight(7);

        progressBox.setMaxWidth(
                Double.MAX_VALUE
        );

        progressBox.setStyle(
                "-fx-background-color: #DEE4F5;" +
                "-fx-background-radius: 8;"
        );

        Region progress =
                new Region();

        progress.setPrefHeight(7);

        progress.prefWidthProperty().bind(
                progressBox.widthProperty()
                        .multiply(percentage)
        );

        progress.setStyle(
                "-fx-background-color: " +
                accent +
                ";" +
                "-fx-background-radius: 8;"
        );

        progressBox.getChildren().add(
                progress
        );

        box.getChildren().addAll(
                top,
                progressBox
        );

        return box;
    }

    private VBox createRecentActivity() {

        VBox card =
                new VBox();

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 22;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 22;"
        );

        HBox header =
                new HBox();

        header.setPadding(
                new Insets(22, 25, 18, 25)
        );

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        Label icon =
                new Label("↶");

        icon.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        20
                )
        );

        icon.setStyle(
                "-fx-text-fill: " + BLUE + ";"
        );

        Label title =
                new Label("Recent Activity");

        title.setFont(
                Font.font(
                        "System",
                        FontWeight.EXTRA_BOLD,
                        18
                )
        );

        title.setStyle(
                "-fx-text-fill: " + DARK + ";"
        );

        HBox titleBox =
                new HBox(
                        10,
                        icon,
                        title
                );

        titleBox.setAlignment(
                Pos.CENTER_LEFT
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label viewAll =
                new Label("View All  ›");

        viewAll.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        11
                )
        );

        viewAll.setStyle(
                "-fx-text-fill: " + BLUE + ";"
        );

        header.getChildren().addAll(
                titleBox,
                spacer,
                viewAll
        );

        HBox columns =
                new HBox();

        columns.setPadding(
                new Insets(
                        12,
                        25,
                        12,
                        25
                )
        );

        columns.setStyle(
                "-fx-border-color: #EEF0F4 transparent #EEF0F4 transparent;"
        );

        Label date =
                createColumn("DATE");

        Label checkIn =
                createColumn("CHECK IN");

        Label checkOut =
                createColumn("CHECK OUT");

        Label status =
                createColumn("STATUS");

        Label action =
                createColumn("ACTION");

        HBox.setHgrow(
                date,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                checkIn,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                checkOut,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                status,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                action,
                Priority.ALWAYS
        );

        columns.getChildren().addAll(
                date,
                checkIn,
                checkOut,
                status,
                action
        );

        VBox rows =
                new VBox();

        rows.getChildren().addAll(
                createActivityRow(
                        "AUG\n24",
                        "Monday",
                        "09:00 AM",
                        "06:00 PM",
                        "Present",
                        GREEN
                ),
                createActivityRow(
                        "AUG\n23",
                        "Sunday",
                        "09:15 AM",
                        "06:10 PM",
                        "Present",
                        GREEN
                ),
                createActivityRow(
                        "AUG\n22",
                        "Saturday",
                        "-",
                        "-",
                        "Leave",
                        ORANGE
                ),
                createActivityRow(
                        "AUG\n21",
                        "Friday",
                        "08:55 AM",
                        "05:55 PM",
                        "Present",
                        GREEN
                )
        );

        card.getChildren().addAll(
                header,
                columns,
                rows
        );

        return card;
    }

    private Label createColumn(
            String text
    ) {

        Label label =
                new Label(text);

        label.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        10
                )
        );

        label.setStyle(
                "-fx-text-fill: " + MUTED + ";"
        );

        return label;
    }

    private HBox createActivityRow(
            String date,
            String day,
            String checkIn,
            String checkOut,
            String status,
            String accent
    ) {

        HBox row =
                new HBox();

        row.setPadding(
                new Insets(
                        15,
                        25,
                        15,
                        25
                )
        );

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        row.setStyle(
                "-fx-border-color: transparent transparent #EEF0F4 transparent;"
        );

        VBox dateBox =
                new VBox(1);

        dateBox.setPrefWidth(190);

        Label dateLabel =
                new Label(date);

        dateLabel.setPrefSize(
                35,
                38
        );

        dateLabel.setAlignment(
                Pos.CENTER
        );

        dateLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        9
                )
        );

        dateLabel.setStyle(
                "-fx-background-color: #EEF0FF;" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: " + BLUE + ";"
        );

        Label dayLabel =
                new Label(day);

        dayLabel.setStyle(
                "-fx-text-fill: " + DARK + ";" +
                "-fx-font-size: 12px;"
        );

        HBox dateContent =
                new HBox(
                        10,
                        dateLabel,
                        dayLabel
                );

        dateContent.setAlignment(
                Pos.CENTER_LEFT
        );

        dateBox.getChildren().add(
                dateContent
        );

        Label in =
                new Label(checkIn);

        in.setPrefWidth(135);

        in.setStyle(
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-font-size: 12px;"
        );

        Label out =
                new Label(checkOut);

        out.setPrefWidth(145);

        out.setStyle(
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-font-size: 12px;"
        );

        Label statusLabel =
                new Label("●  " + status);

        statusLabel.setPrefWidth(190);

        statusLabel.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        10
                )
        );

        statusLabel.setPadding(
                new Insets(
                        7,
                        12,
                        7,
                        12
                )
        );

        statusLabel.setStyle(
                "-fx-background-color: " +
                (
                        accent.equals(GREEN)
                                ? "#E4F5E9"
                                : "#FFF3D6"
                ) +
                ";" +
                "-fx-background-radius: 15;" +
                "-fx-text-fill: " +
                accent +
                ";"
        );

        Label action =
                new Label("—");

        action.setStyle(
                "-fx-text-fill: " + MUTED + ";"
        );

        row.getChildren().addAll(
                dateBox,
                in,
                out,
                statusLabel,
                action
        );

        return row;
    }

    public void show(
            Stage stage,
            String email
    ) {

        if (stage == null) {
            return;
        }

        if (stage.getScene() == null) {
            return;
        }

        if (!(stage.getScene().getRoot()
                instanceof BorderPane)) {
            return;
        }

        BorderPane root =
                (BorderPane)
                stage.getScene().getRoot();

        VBox content =
                createContent(email);

        ScrollPane scrollPane =
                new ScrollPane(content);

        scrollPane.setFitToWidth(true);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );

        root.setCenter(
                scrollPane
        );
    }
}