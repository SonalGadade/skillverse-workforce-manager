package com.skillverse.admin.view;

import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.AppNotification;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminSendBroadcastView extends VBox {

    private final Stage stage;

    public AdminSendBroadcastView(Stage stage) {
        this();
    }

    public AdminSendBroadcastView() {
        this.stage = null;
        setSpacing(24);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(12);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("← Back to Notifications");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563EB; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 13px;");
        backBtn.setOnAction(e -> navigateBack());

        VBox titleBox = new VBox(4);
        Label titleLabel = new Label("Broadcast Organization Announcement 📢");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Publish company-wide announcements, critical system alerts, and training circulars.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        headerBar.getChildren().addAll(backBtn, titleBox);

        VBox formCard = new VBox(18);
        formCard.setMaxWidth(720);
        formCard.setPadding(new Insets(28));
        formCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label formTitle = new Label("Announcement Details");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        VBox titleInputBox = new VBox(6);
        Label titleLbl = new Label("Announcement Title:");
        titleLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569;");
        TextField titleField = new TextField();
        titleField.setPromptText("e.g. Q3 Performance Review Cycle Is Now Live");
        titleInputBox.getChildren().addAll(titleLbl, titleField);

        VBox catBox = new VBox(6);
        Label catLbl = new Label("Category / Audience:");
        catLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569;");
        ComboBox<String> categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll("System", "Employee", "Training", "Alert");
        categoryCombo.setValue("System");
        categoryCombo.setMaxWidth(Double.MAX_VALUE);
        catBox.getChildren().addAll(catLbl, categoryCombo);

        VBox msgBox = new VBox(6);
        Label msgLbl = new Label("Detailed Message:");
        msgLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569;");
        TextArea msgArea = new TextArea();
        msgArea.setPromptText("Type announcement message body here...");
        msgArea.setPrefRowCount(6);
        msgArea.setWrapText(true);
        msgBox.getChildren().addAll(msgLbl, msgArea);

        HBox actionsRow = new HBox(12);
        actionsRow.setAlignment(Pos.CENTER_RIGHT);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> navigateBack());

        Button publishBtn = new Button("Publish Announcement →");
        publishBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");
        publishBtn.setOnAction(e -> {
            if (titleField.getText().trim().isEmpty() || msgArea.getText().trim().isEmpty()) {
                return;
            }
            AppNotification notif = new AppNotification(titleField.getText().trim(), msgArea.getText().trim(), categoryCombo.getValue());
            FirebaseDAO.getInstance().createNotification(notif).thenRun(() -> {
                Platform.runLater(this::navigateBack);
            });
        });

        actionsRow.getChildren().addAll(cancelBtn, publishBtn);
        formCard.getChildren().addAll(formTitle, titleInputBox, catBox, msgBox, actionsRow);

        getChildren().addAll(headerBar, formCard);
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private void navigateBack() {
        if (getScene() != null && getScene().getRoot() != null) {
            javafx.scene.Node contentArea = getScene().getRoot().lookup("#contentArea");
            if (contentArea instanceof Pane) {
                ((Pane) contentArea).getChildren().setAll(new AdminNotificationsView());
                return;
            }
        }
        javafx.scene.Node current = this;
        while (current != null && !(current instanceof StackPane || current instanceof VBox || current instanceof BorderPane)) {
            current = current.getParent();
        }
        if (current instanceof Pane) {
            ((Pane) current).getChildren().setAll(new AdminNotificationsView());
        }
    }
}
