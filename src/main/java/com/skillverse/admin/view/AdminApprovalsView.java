package com.skillverse.admin.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.skillverse.CommonFeatures.SystemApproval;
import com.skillverse.Dao.FirebaseDAO;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminApprovalsView extends VBox {

    private final Label pendingCountLbl;
    private final Label approvedCountLbl;
    private final Label rejectedCountLbl;
    private final Label totalCountLbl;

    private final TextField searchField;
    private final ComboBox<String> statusFilterCombo;
    private final VBox requestsContainer;
    private List<SystemApproval> masterApprovalsList = new ArrayList<>();

    public AdminApprovalsView(Stage stage) {
        this();
    }

    public AdminApprovalsView() {
        setSpacing(24);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        HBox headerBar = new HBox(16);
        headerBar.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label("Approvals & System Requests ✓");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Review, authenticate, and manage organizational requests and verified status transitions.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-text-fill: #334155; -fx-font-weight: bold; -fx-padding: 10 16; -fx-background-radius: 8; -fx-cursor: hand;");
        refreshBtn.setOnAction(e -> loadApprovalsFromFirestore());

        headerBar.getChildren().addAll(titleBox, refreshBtn);

        HBox statsRow = new HBox(16);
        statsRow.setAlignment(Pos.CENTER_LEFT);

        VBox card1 = createStatCard("Pending Approvals", pendingCountLbl = new Label("0"), "Requires your attention", "#D97706", "#FFFBEB");
        VBox card2 = createStatCard("Approved", approvedCountLbl = new Label("0"), "Successfully processed", "#059669", "#ECFDF5");
        VBox card3 = createStatCard("Rejected", rejectedCountLbl = new Label("0"), "Requests declined", "#DC2626", "#FEF2F2");
        VBox card4 = createStatCard("Total Requests", totalCountLbl = new Label("0"), "All approval requests", "#2563EB", "#EFF6FF");

        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);
        HBox.setHgrow(card3, Priority.ALWAYS);
        HBox.setHgrow(card4, Priority.ALWAYS);
        statsRow.getChildren().addAll(card1, card2, card3, card4);

        HBox filterBar = new HBox(14);
        filterBar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("🔍 Search approvals by applicant name, email, or request type...");
        searchField.setPrefHeight(40);
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchField.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E2E8F0; -fx-border-radius: 8; -fx-padding: 0 12;");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterAndRenderRequests());

        statusFilterCombo = new ComboBox<>();
        statusFilterCombo.getItems().addAll("All Requests", "PENDING", "APPROVED", "REJECTED");
        statusFilterCombo.setValue("All Requests");
        statusFilterCombo.setPrefHeight(40);
        statusFilterCombo.setPrefWidth(180);
        statusFilterCombo.setOnAction(e -> filterAndRenderRequests());

        filterBar.getChildren().addAll(searchField, statusFilterCombo);

        VBox listSection = new VBox(14);
        Label sectionTitle = new Label("Approval Requests");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        requestsContainer = new VBox(14);
        ScrollPane scrollPane = new ScrollPane(requestsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        listSection.getChildren().addAll(sectionTitle, scrollPane);

        ScrollPane mainScroll = new ScrollPane(new VBox(20, headerBar, statsRow, filterBar, listSection));
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScroll);

        loadApprovalsFromFirestore();
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private VBox createStatCard(String title, Label valLbl, String sub, String accentColor, String bgLight) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox top = new HBox(8);
        top.setAlignment(Pos.CENTER_LEFT);
        Label dot = new Label("●");
        dot.setStyle("-fx-font-size: 10px; -fx-text-fill: " + accentColor + ";");
        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        top.getChildren().addAll(dot, titleLbl);

        valLbl.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subLbl = new Label(sub);
        subLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #94A3B8;");

        card.getChildren().addAll(top, valLbl, subLbl);
        return card;
    }

    public void loadApprovalsFromFirestore() {
        FirebaseDAO.getInstance().getAllSystemApprovals().thenAccept(list -> {
            Platform.runLater(() -> {
                this.masterApprovalsList = list != null ? list : new ArrayList<>();

                int total = masterApprovalsList.size();
                long pending = masterApprovalsList.stream().filter(a -> "PENDING".equalsIgnoreCase(a.getStatus())).count();
                long approved = masterApprovalsList.stream().filter(a -> "APPROVED".equalsIgnoreCase(a.getStatus())).count();
                long rejected = masterApprovalsList.stream().filter(a -> "REJECTED".equalsIgnoreCase(a.getStatus())).count();

                totalCountLbl.setText(String.valueOf(total));
                pendingCountLbl.setText(String.valueOf(pending));
                approvedCountLbl.setText(String.valueOf(approved));
                rejectedCountLbl.setText(String.valueOf(rejected));

                filterAndRenderRequests();
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private void filterAndRenderRequests() {
        requestsContainer.getChildren().clear();

        String query = searchField.getText() != null ? searchField.getText().toLowerCase().trim() : "";
        String status = statusFilterCombo.getValue();

        List<SystemApproval> filtered = masterApprovalsList.stream().filter(req -> {
            boolean matchesQuery = query.isEmpty() ||
                    (req.getApplicantName() != null && req.getApplicantName().toLowerCase().contains(query)) ||
                    (req.getApplicantEmail() != null && req.getApplicantEmail().toLowerCase().contains(query)) ||
                    (req.getRequestType() != null && req.getRequestType().toLowerCase().contains(query)) ||
                    (req.getDepartment() != null && req.getDepartment().toLowerCase().contains(query));

            boolean matchesStatus = status == null || "All Requests".equalsIgnoreCase(status) ||
                    (req.getStatus() != null && req.getStatus().equalsIgnoreCase(status));

            return matchesQuery && matchesStatus;
        }).collect(Collectors.toList());

        if (filtered.isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(60));
            Label emptyLbl = new Label(masterApprovalsList.isEmpty()
                    ? "No pending approvals or requests found in Firestore."
                    : "No requests matched your filter criteria.");
            emptyLbl.setStyle("-fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-font-size: 14px;");
            emptyBox.getChildren().add(emptyLbl);
            requestsContainer.getChildren().add(emptyBox);
            return;
        }

        for (SystemApproval req : filtered) {
            requestsContainer.getChildren().add(createApprovalCard(req));
        }
    }

    private VBox createApprovalCard(SystemApproval req) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        String initials = req.getApplicantName() != null && req.getApplicantName().length() >= 2
                ? req.getApplicantName().substring(0, 2).toUpperCase() : "AP";
        Label avatar = new Label(initials);
        avatar.setStyle("-fx-background-color: #EEF2FF; -fx-text-fill: #4F46E5; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 10; -fx-background-radius: 8;");

        VBox infoBox = new VBox(2);
        Label nameLbl = new Label(req.getApplicantName() != null ? req.getApplicantName() : req.getApplicantEmail());
        nameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label typeLbl = new Label((req.getRequestType() != null ? req.getRequestType() : "General Request") + " • " + (req.getDepartment() != null ? req.getDepartment() : "General"));
        typeLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");
        infoBox.getChildren().addAll(nameLbl, typeLbl);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        Label statusBadge = new Label(req.getStatus() != null ? req.getStatus() : "PENDING");
        if ("APPROVED".equalsIgnoreCase(req.getStatus())) {
            statusBadge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #166534; -fx-padding: 4 10; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold;");
        } else if ("REJECTED".equalsIgnoreCase(req.getStatus())) {
            statusBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #991B1B; -fx-padding: 4 10; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold;");
        } else {
            statusBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #92400E; -fx-padding: 4 10; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold;");
        }

        topRow.getChildren().addAll(avatar, infoBox, statusBadge);

        Label descLbl = new Label(req.getDescription() != null ? req.getDescription() : "Standard approval request submitted.");
        descLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #334155;");
        descLbl.setWrapText(true);

        HBox bottomRow = new HBox(12);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        Label dateLbl = new Label("Submitted: " + (req.getSubmittedAt() != null ? req.getSubmittedAt().toString() : "Recent"));
        dateLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #94A3B8;");
        HBox.setHgrow(dateLbl, Priority.ALWAYS);

        HBox actions = new HBox(8);
        actions.setAlignment(Pos.CENTER_RIGHT);

        if ("PENDING".equalsIgnoreCase(req.getStatus())) {
            Button approveBtn = new Button("Approve");
            approveBtn.setStyle("-fx-background-color: #DCFCE7; -fx-border-color: #86EFAC; -fx-text-fill: #166534; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 6 14; -fx-background-radius: 6; -fx-cursor: hand;");
            approveBtn.setOnAction(e -> handleUpdateStatus(req, "APPROVED"));

            Button rejectBtn = new Button("Reject");
            rejectBtn.setStyle("-fx-background-color: #FEE2E2; -fx-border-color: #FECACA; -fx-text-fill: #991B1B; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 6 14; -fx-background-radius: 6; -fx-cursor: hand;");
            rejectBtn.setOnAction(e -> handleUpdateStatus(req, "REJECTED"));

            actions.getChildren().addAll(approveBtn, rejectBtn);
        } else {
            Label processedLbl = new Label("Processed");
            processedLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B; -fx-font-style: italic;");
            actions.getChildren().add(processedLbl);
        }

        bottomRow.getChildren().addAll(dateLbl, actions);
        card.getChildren().addAll(topRow, descLbl, bottomRow);
        return card;
    }

    private void handleUpdateStatus(SystemApproval req, String newStatus) {
        FirebaseDAO.getInstance().updateApprovalStatus(req.getId(), newStatus).thenRun(() -> {
            Platform.runLater(() -> {
                FirebaseDAO.getInstance().logActivity(newStatus + " request for " + req.getApplicantName(), "Approvals", "SUCCESS");
                showModernToast("Request " + newStatus.toLowerCase() + " 🎉",
                        "The request for " + req.getApplicantName() + " was set to " + newStatus,
                        "APPROVED".equalsIgnoreCase(newStatus));
                loadApprovalsFromFirestore();
            });
        });
    }

    private void showModernToast(String title, String message, boolean isSuccess) {
        Platform.runLater(() -> {
            HBox toast = new HBox(12);
            toast.setAlignment(Pos.CENTER_LEFT);
            toast.setPadding(new Insets(14, 20, 14, 20));
            toast.setMaxWidth(480);

            Label iconLbl = new Label(isSuccess ? "✓" : "⚠️");
            iconLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + (isSuccess ? "#059669;" : "#D97706;"));

            VBox textContainer = new VBox(2);
            Label titleLbl = new Label(title);
            titleLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

            Label descLbl = new Label(message);
            descLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #475569;");
            descLbl.setWrapText(true);

            textContainer.getChildren().addAll(titleLbl, descLbl);
            HBox.setHgrow(textContainer, Priority.ALWAYS);

            toast.getChildren().addAll(iconLbl, textContainer);

            toast.setStyle(
                "-fx-background-color: " + (isSuccess ? "#ECFDF5;" : "#FFFBEB;") +
                "-fx-border-color: " + (isSuccess ? "#A7F3D0;" : "#FDE68A;") +
                "-fx-border-width: 1.5; -fx-background-radius: 12; -fx-border-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 15, 0, 0, 6);"
            );

            toast.setOpacity(0);
            getChildren().add(0, toast);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toast);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), toast);
            slideIn.setFromY(-20);
            slideIn.setToY(0);

            ParallelTransition showAnim = new ParallelTransition(fadeIn, slideIn);

            PauseTransition delay = new PauseTransition(Duration.seconds(3.5));
            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), toast);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> getChildren().remove(toast));

            new SequentialTransition(showAnim, delay, fadeOut).play();
        });
    }
}
