package com.skillverse.CommonFeatures;

import com.skillverse.Config.CloudinaryService;
import com.skillverse.Dao.FirebaseDAO;
import com.skillverse.CommonFeatures.User;
import com.skillverse.CommonFeatures.UserSession;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class ProfileView extends VBox {

    private final Stage stage;
    private final TextField nameField;
    private final TextField emailField;
    private final TextField deptField;
    private final TextField roleField;
    private final TextField phoneField;
    private final Button editSaveBtn;
    private final Button cancelBtn;
    private final Label nameDisplayLbl;
    private final Label roleDisplayLbl;
    private final StackPane avatarContainer;
    private final Button removePhotoBtn;

    private boolean isEditing = false;

    public ProfileView(Stage stage) {
        this();
    }

    public ProfileView() {
        this.stage = null;
        setSpacing(20);
        setPadding(new Insets(24, 32, 24, 32));
        setStyle("-fx-background-color: #F8FAFC;");

        User current = UserSession.getCurrentUser();
        String currentName = (current != null && current.getName() != null && !current.getName().trim().isEmpty()) 
                ? current.getName() : "User Profile";
        String currentEmail = (current != null && current.getEmail() != null) ? current.getEmail() : "user@skillverse.ai";
        String currentRole = (current != null && current.getRole() != null) ? current.getRole().toUpperCase() : "EMPLOYEE";
        String currentDept = (current != null && current.getDepartment() != null) ? current.getDepartment() : "General";
        String currentPhone = (current != null && current.getPhone() != null) ? current.getPhone() : "";
        String photoUrl = current != null ? current.getProfilePicUrl() : null;

        HBox headerBar = new HBox(14);
        headerBar.setAlignment(Pos.CENTER_LEFT);



        VBox titleBox = new VBox(4);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        Label titleLabel = new Label(currentRole + " Profile");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label subtitleLabel = new Label("Manage your account details, contact info, and profile image.");
        subtitleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        Label badgeRole = new Label(currentRole + " WORKSPACE");
        badgeRole.setStyle("-fx-background-color: #EEF2FF; -fx-text-fill: #4F46E5; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 6 14; -fx-background-radius: 20;");

        headerBar.getChildren().addAll(titleBox, badgeRole);

        VBox contentBox = new VBox(20);
        contentBox.setMaxWidth(780);

        VBox heroCard = new VBox(18);
        heroCard.setPadding(new Insets(24));
        heroCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox heroRow = new HBox(20);
        heroRow.setAlignment(Pos.CENTER_LEFT);

        avatarContainer = new StackPane();

        VBox avatarWrapper = new VBox(8);
        avatarWrapper.setAlignment(Pos.CENTER);

        HBox photoActions = new HBox(6);
        photoActions.setAlignment(Pos.CENTER);

        Button uploadPhotoBtn = new Button("📷 Change");
        uploadPhotoBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #334155; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 6; -fx-cursor: hand;");
        uploadPhotoBtn.setOnAction(e -> handleUploadProfilePhoto());

        removePhotoBtn = new Button("🗑️ Remove");
        removePhotoBtn.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 6; -fx-cursor: hand;");
        removePhotoBtn.setOnAction(e -> handleRemoveProfilePhoto());

        photoActions.getChildren().addAll(uploadPhotoBtn, removePhotoBtn);
        avatarWrapper.getChildren().addAll(avatarContainer, photoActions);

        renderAvatar(currentName, photoUrl);

        VBox heroInfo = new VBox(6);
        HBox.setHgrow(heroInfo, Priority.ALWAYS);

        HBox nameVerifiedRow = new HBox(8);
        nameVerifiedRow.setAlignment(Pos.CENTER_LEFT);
        nameDisplayLbl = new Label(currentName);
        nameDisplayLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");
        Label verifiedBadge = new Label("✓ " + currentRole);
        verifiedBadge.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #166534; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 3 8; -fx-background-radius: 6;");
        nameVerifiedRow.getChildren().addAll(nameDisplayLbl, verifiedBadge);

        roleDisplayLbl = new Label(currentRole + " • " + currentDept + " • SkillVerse AI Enterprise");
        roleDisplayLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");

        Label emailPill = new Label("✉ " + currentEmail);
        emailPill.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-size: 12px; -fx-padding: 4 10; -fx-background-radius: 6;");

        heroInfo.getChildren().addAll(nameVerifiedRow, roleDisplayLbl, emailPill);
        heroRow.getChildren().addAll(avatarWrapper, heroInfo);
        heroCard.getChildren().add(heroRow);

        VBox accountCard = new VBox(16);
        accountCard.setPadding(new Insets(24));
        accountCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");

        Label accHeader = new Label("👤 Account Details");
        accHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1E293B;");

        nameField = createFormField(currentName);
        emailField = createFormField(currentEmail);
        emailField.setDisable(true);

        phoneField = createFormField(currentPhone);
        deptField = createFormField(currentDept);
        roleField = createFormField(currentRole);
        roleField.setDisable(true);

        setFieldsEditable(false);

        HBox actionButtonsRow = new HBox(12);
        actionButtonsRow.setAlignment(Pos.CENTER_LEFT);

        editSaveBtn = new Button("Edit Profile");
        editSaveBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");
        editSaveBtn.setOnAction(e -> handleEditOrSave());

        cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setVisible(false);
        cancelBtn.setOnAction(e -> {
            isEditing = false;
            setFieldsEditable(false);
            editSaveBtn.setText("Edit Profile");
            cancelBtn.setVisible(false);
        });

        actionButtonsRow.getChildren().addAll(editSaveBtn, cancelBtn);

        accountCard.getChildren().addAll(
                accHeader,
                createFieldContainer("FULL NAME", nameField),
                createFieldContainer("EMAIL ADDRESS", emailField),
                createFieldContainer("PHONE NUMBER", phoneField),
                createFieldContainer("DEPARTMENT", deptField),
                createFieldContainer("ASSIGNED ROLE", roleField),
                actionButtonsRow
        );

        contentBox.getChildren().addAll(heroCard, accountCard);

        ScrollPane mainScroll = new ScrollPane(new VBox(20, headerBar, contentBox));
        mainScroll.setFitToWidth(true);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        getChildren().add(mainScroll);
    }

    public ScrollPane createScrollPane() {
        ScrollPane sp = new ScrollPane(this);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private void renderAvatar(String name, String photoUrl) {
        avatarContainer.getChildren().clear();
        boolean hasPhoto = photoUrl != null && !photoUrl.trim().isEmpty();
        removePhotoBtn.setVisible(hasPhoto);
        removePhotoBtn.setManaged(hasPhoto);

        if (hasPhoto) {
            try {
                ImageView imageView = new ImageView(new Image(photoUrl, 80, 80, true, true, true));
                Circle clip = new Circle(40, 40, 40);
                imageView.setClip(clip);
                avatarContainer.getChildren().add(imageView);
                return;
            } catch (Exception ignored) {}
        }

        String initials = name != null && name.length() >= 2 ? name.substring(0, 2).toUpperCase() : "US";
        Label fallbackLbl = new Label(initials);
        fallbackLbl.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-size: 26px; -fx-font-weight: bold; -fx-padding: 22 26; -fx-background-radius: 50;");
        avatarContainer.getChildren().add(fallbackLbl);
    }

    private void handleUploadProfilePhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(getScene() != null ? getScene().getWindow() : null);
        if (selectedFile != null) {
            User current = UserSession.getCurrentUser();
            if (current == null) return;

            CloudinaryService.uploadImage(selectedFile, "profiles").thenAccept(uploadedUrl -> {
                if (uploadedUrl != null) {
                    current.setProfilePicUrl(uploadedUrl);
                    FirebaseDAO.getInstance().updateUserProfilePicture(current.getEmail(), uploadedUrl).thenRun(() -> {
                        Platform.runLater(() -> {
                            renderAvatar(current.getName(), uploadedUrl);
                            showModernToast("Photo Uploaded 🎉", "Your profile photo has been updated.", true);
                        });
                    });
                }
            }).exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });
        }
    }

    private void handleRemoveProfilePhoto() {
        User current = UserSession.getCurrentUser();
        if (current == null) return;

        current.setProfilePicUrl(null);

        FirebaseDAO.getInstance().updateUserProfilePicture(current.getEmail(), null).thenRun(() -> {
            Platform.runLater(() -> {
                renderAvatar(current.getName(), null);
                showModernToast("Photo Removed 🗑️", "Profile photo permanently deleted from database.", true);
                
                refreshGlobalAvatars(current.getName());
            });
        });
    }

    private void refreshGlobalAvatars(String name) {
        try {
            javafx.scene.Parent root = getScene().getRoot();
            if (root != null) {
                String initials = (name != null && name.length() >= 2) ? name.substring(0, 2).toUpperCase() : "US";
                
                javafx.scene.Node topAvatar = root.lookup("#headerAvatar");
                if (topAvatar instanceof Label) {
                    ((Label) topAvatar).setText(initials);
                    ((Label) topAvatar).setGraphic(null);
                }
                
                javafx.scene.Node sideAvatar = root.lookup("#sidebarAvatar");
                if (sideAvatar instanceof Label) {
                    ((Label) sideAvatar).setText(initials);
                    ((Label) sideAvatar).setGraphic(null);
                }
            }
        } catch (Exception ignored) {}
    }

    private TextField createFormField(String value) {
        TextField tf = new TextField(value);
        tf.setPrefHeight(38);
        tf.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6; -fx-padding: 0 10;");
        return tf;
    }

    private VBox createFieldContainer(String labelText, TextField tf) {
        VBox box = new VBox(4);
        Label lbl = new Label(labelText);
        lbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748B;");
        box.getChildren().addAll(lbl, tf);
        return box;
    }

    private void setFieldsEditable(boolean editable) {
        nameField.setEditable(editable);
        phoneField.setEditable(editable);
        deptField.setEditable(editable);
        String style = editable
                ? "-fx-background-color: #FFFFFF; -fx-border-color: #2563EB; -fx-border-radius: 6; -fx-padding: 0 10;"
                : "-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6; -fx-padding: 0 10;";
        nameField.setStyle(style);
        phoneField.setStyle(style);
        deptField.setStyle(style);
    }

    private void handleEditOrSave() {
        if (!isEditing) {
            isEditing = true;
            setFieldsEditable(true);
            editSaveBtn.setText("Save Changes →");
            cancelBtn.setVisible(true);
        } else {
            String newName = nameField.getText().trim();
            String newPhone = phoneField.getText().trim();
            String newDept = deptField.getText().trim();
            User current = UserSession.getCurrentUser();

            if (current != null && !newName.isEmpty()) {
                current.setName(newName);
                current.setPhone(newPhone);
                current.setDepartment(newDept);

                FirebaseDAO.getInstance().updateUserProfileFull(current.getEmail(), newName, newPhone, newDept).thenRun(() -> {
                    Platform.runLater(() -> {
                        isEditing = false;
                        setFieldsEditable(false);
                        editSaveBtn.setText("Edit Profile");
                        cancelBtn.setVisible(false);

                        nameDisplayLbl.setText(newName);
                        roleDisplayLbl.setText((current.getRole() != null ? current.getRole().toUpperCase() : "EMPLOYEE") + " • " + newDept + " • SkillVerse AI Enterprise");
                        renderAvatar(newName, current.getProfilePicUrl());
                        showModernToast("Profile Saved 🎉", "Profile details stored permanently in Firestore.", true);
                    });
                });
            }
        }
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

    private void navigateBackToDashboard() {
        if (getScene() != null && getScene().getRoot() != null) {
            javafx.scene.Node contentArea = getScene().getRoot().lookup("#contentArea");
            if (contentArea instanceof Pane) {
                User current = UserSession.getCurrentUser();
                String role = (current != null && current.getRole() != null) ? current.getRole().toUpperCase() : "EMPLOYEE";

                if ("ADMIN".equalsIgnoreCase(role)) {
                    ((Pane) contentArea).getChildren().setAll(new com.skillverse.admin.view.AdminDashboardView());
                    return;
                } else if ("TRAINER".equalsIgnoreCase(role)) {
                    ((Pane) contentArea).getChildren().setAll(new com.skillverse.trainer.view.TrainerDashboardView());
                    return;
                } else if ("MANAGER".equalsIgnoreCase(role)) {
                    ((Pane) contentArea).getChildren().setAll(new com.skillverse.manager.view.ManagerDashboardView());
                    return;
                }
            }
        }

        if (getParent() instanceof Pane) {
            Pane parent = (Pane) getParent();
            User currentU = UserSession.getCurrentUser();
            String role = currentU != null && currentU.getRole() != null ? currentU.getRole() : "EMPLOYEE";
            if ("TRAINER".equalsIgnoreCase(role)) {
                parent.getChildren().setAll(new com.skillverse.trainer.view.TrainerDashboardView());
            } else if ("ADMIN".equalsIgnoreCase(role)) {
                parent.getChildren().setAll(new com.skillverse.admin.view.AdminDashboardView());
            } else if ("MANAGER".equalsIgnoreCase(role)) {
                parent.getChildren().setAll(new com.skillverse.manager.view.ManagerDashboardView());
            } else {
                com.skillverse.FirstScreen.SceneNavigator.navigateToDashboard(role);
            }
        } else {
            User currentU = UserSession.getCurrentUser();
            String role = currentU != null && currentU.getRole() != null ? currentU.getRole() : "EMPLOYEE";
            com.skillverse.FirstScreen.SceneNavigator.navigateToDashboard(role);
        }
    }
}
