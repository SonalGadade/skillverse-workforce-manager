package com.skillverse.FirstScreen;

import java.io.InputStream;
import java.util.function.Consumer;
import com.skillverse.CommonFeatures.UserSession;
import com.skillverse.Dao.FirebaseDAO;
import javafx.application.Platform;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UnifiedLoginView {

    public static Scene createLoginScene(
            String roleName,
            String platformTitle,
            String taglineDescription,
            String roleIconSymbol,
            String accentHexColor,
            String[] featurePillTexts,
            Consumer<String> onLoginSuccess
    ) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #F8FAFF, #EEF4FF);");

        HBox mainContainer = new HBox(40);
        mainContainer.setPadding(new Insets(30, 50, 30, 50));
        mainContainer.setAlignment(Pos.CENTER);

        VBox leftSection = new VBox(20);
        leftSection.setPrefWidth(620);
        leftSection.setMaxWidth(650);
        leftSection.setAlignment(Pos.TOP_LEFT);

        Button backBtn = new Button("← Back to Portal / Switch Role");
        backBtn.setPrefHeight(36);
        backBtn.setStyle(
                "-fx-background-color: white;"
                + "-fx-text-fill: " + accentHexColor + ";"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 6px 16px;"
                + "-fx-background-radius: 18px;"
                + "-fx-border-color: #CBD5E1;"
                + "-fx-border-radius: 18px;"
                + "-fx-cursor: hand;"
        );
        backBtn.setOnAction(e -> SceneNavigator.showMainPortal());

        HBox brandRow = new HBox(12);
        brandRow.setAlignment(Pos.CENTER_LEFT);

        ImageView logoImageView = null;
        try {
            InputStream is = UnifiedLoginView.class.getResourceAsStream("/assets/Main Logo.jpeg");
            if (is == null) {
                is = UnifiedLoginView.class.getResourceAsStream("/Main Logo.jpeg");
            }
            if (is != null) {
                Image img = new Image(is);
                logoImageView = new ImageView(img);
                logoImageView.setFitHeight(42);
                logoImageView.setPreserveRatio(true);
                logoImageView.setSmooth(true);
            }
        } catch (Exception ex) {
            logoImageView = null;
        }

        StackPane logoBox = new StackPane();
        logoBox.setPrefSize(44, 44);
        logoBox.setStyle("-fx-background-color: linear-gradient(to right, " + accentHexColor + ", #8B5CF6); -fx-background-radius: 12px;");
        Text logoText = new Text("SV");
        logoText.setFill(Color.WHITE);
        logoText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        logoBox.getChildren().add(logoText);

        VBox brandTextBox = new VBox(2);
        Text brandTitle = new Text("SkillVerse");
        brandTitle.setFill(Color.web("#0F172A"));
        brandTitle.setFont(Font.font("Arial", FontWeight.BOLD, 26));

        Text brandSubtitle = new Text("AI Powered Internal Talent Ecosystem");
        brandSubtitle.setFill(Color.web("#64748B"));
        brandSubtitle.setFont(Font.font("Arial", 11));

        brandTextBox.getChildren().addAll(brandTitle, brandSubtitle);

        if (logoImageView != null) {
            brandRow.getChildren().addAll(logoImageView, brandTextBox);
        } else {
            brandRow.getChildren().addAll(logoBox, brandTextBox);
        }

        VBox headingBox = new VBox(4);
        Text welcomeSmall = new Text("Welcome to");
        welcomeSmall.setFill(Color.web("#0F172A"));
        welcomeSmall.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        HBox titleLine = new HBox(8);
        Text skillText = new Text("SkillVerse ");
        skillText.setFill(Color.web(accentHexColor));
        skillText.setFont(Font.font("Arial", FontWeight.BOLD, 32));

        Text rolePlatformText = new Text(platformTitle);
        rolePlatformText.setFill(Color.web("#0F172A"));
        rolePlatformText.setFont(Font.font("Arial", FontWeight.BOLD, 32));

        titleLine.getChildren().addAll(skillText, rolePlatformText);

        Text descText = new Text(taglineDescription);
        descText.setFill(Color.web("#475569"));
        descText.setFont(Font.font("Arial", 14));

        headingBox.getChildren().addAll(welcomeSmall, titleLine, descText);

        // 4. Infographic / Preview Card
        VBox previewCard = createPreviewCard(roleName, accentHexColor);

        // 5. Feature Badges Row
        HBox featureRow = new HBox(12);
        featureRow.setAlignment(Pos.CENTER_LEFT);

        if (featurePillTexts != null) {
            for (String feature : featurePillTexts) {
                HBox pill = new HBox(6);
                pill.setPadding(new Insets(8, 12, 8, 12));
                pill.setAlignment(Pos.CENTER);
                pill.setStyle(
                        "-fx-background-color: white;"
                        + "-fx-background-radius: 12px;"
                        + "-fx-border-color: #E2E8F0;"
                        + "-fx-border-radius: 12px;"
                );
                Text checkMark = new Text("✓");
                checkMark.setFill(Color.web(accentHexColor));
                checkMark.setFont(Font.font("Arial", FontWeight.BOLD, 12));

                Text pillLabel = new Text(feature);
                pillLabel.setFill(Color.web("#334155"));
                pillLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));

                pill.getChildren().addAll(checkMark, pillLabel);
                featureRow.getChildren().add(pill);
            }
        }

        leftSection.getChildren().addAll(
                backBtn,
                brandRow,
                headingBox,
                previewCard,
                featureRow
        );

        VBox loginCard = new VBox(16);
        loginCard.setPrefWidth(480);
        loginCard.setMaxWidth(500);
        loginCard.setPadding(new Insets(35, 45, 30, 45));
        loginCard.setAlignment(Pos.TOP_CENTER);
        loginCard.setStyle(
                "-fx-background-color: rgba(255,255,255,0.98);"
                + "-fx-background-radius: 24px;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-width: 1px;"
                + "-fx-border-radius: 24px;"
        );

        DropShadow cardShadow = new DropShadow();
        cardShadow.setRadius(25);
        cardShadow.setOffsetY(8);
        cardShadow.setColor(Color.rgb(15, 23, 42, 0.12));
        loginCard.setEffect(cardShadow);

        // Role Circle Icon
        Label iconLabel = new Label(roleIconSymbol);
        iconLabel.setPrefSize(64, 64);
        iconLabel.setAlignment(Pos.CENTER);
        iconLabel.setStyle(
                "-fx-background-color: " + accentHexColor + "1E;"
                + "-fx-text-fill: " + accentHexColor + ";"
                + "-fx-font-size: 28px;"
                + "-fx-background-radius: 32px;"
        );

        // Title & Subtitle
        VBox cardTitleBox = new VBox(4);
        cardTitleBox.setAlignment(Pos.CENTER);

        Text cardTitle = new Text(roleName + " Login");
        cardTitle.setFill(Color.web("#0F172A"));
        cardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 26));

        Text cardSub = new Text("Access your " + roleName.toLowerCase() + " workspace");
        cardSub.setFill(Color.web("#64748B"));
        cardSub.setFont(Font.font("Arial", 13));

        cardTitleBox.getChildren().addAll(cardTitle, cardSub);

        // Inputs
        VBox emailBox = new VBox(6);
        emailBox.setMaxWidth(Double.MAX_VALUE);

        Text emailLbl = new Text("Email Address");
        emailLbl.setFill(Color.web("#1E293B"));
        emailLbl.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email (e.g. " + roleName.toLowerCase() + "@skillverse.com)");
        emailField.setPrefHeight(44);
        emailField.setStyle(
                "-fx-background-color: #FFFFFF;"
                + "-fx-border-color: #CBD5E1;"
                + "-fx-border-radius: 9px;"
                + "-fx-background-radius: 9px;"
                + "-fx-padding: 10px 14px;"
                + "-fx-font-size: 13px;"
        );

        String rememberedEmail = AuthModalService.loadRememberedEmail(roleName);
        if (rememberedEmail != null && !rememberedEmail.isEmpty()) {
            emailField.setText(rememberedEmail);
        }

        emailBox.getChildren().addAll(emailLbl, emailField);

        VBox passBox = new VBox(6);
        passBox.setMaxWidth(Double.MAX_VALUE);

        Text passLbl = new Text("Password");
        passLbl.setFill(Color.web("#1E293B"));
        passLbl.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");
        passField.setPrefHeight(44);
        passField.setStyle(
                "-fx-background-color: #FFFFFF;"
                + "-fx-border-color: #CBD5E1;"
                + "-fx-border-radius: 9px;"
                + "-fx-background-radius: 9px;"
                + "-fx-padding: 10px 40px 10px 14px;"
                + "-fx-font-size: 13px;"
        );

        TextField visiblePassField = new TextField();
        visiblePassField.setPromptText("Enter your password");
        visiblePassField.setPrefHeight(44);
        visiblePassField.setStyle(
                "-fx-background-color: #FFFFFF;"
                + "-fx-border-color: #CBD5E1;"
                + "-fx-border-radius: 9px;"
                + "-fx-background-radius: 9px;"
                + "-fx-padding: 10px 40px 10px 14px;"
                + "-fx-font-size: 13px;"
        );
        visiblePassField.setManaged(false);
        visiblePassField.setVisible(false);

        visiblePassField.textProperty().bindBidirectional(passField.textProperty());

        Button togglePassBtn = new Button("👁");
        togglePassBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-size: 15px; -fx-cursor: hand;");

        togglePassBtn.setOnAction(e -> {
            boolean show = !visiblePassField.isVisible();
            visiblePassField.setVisible(show);
            visiblePassField.setManaged(show);
            passField.setVisible(!show);
            passField.setManaged(!show);
            togglePassBtn.setText(show ? "🙈" : "👁");
        });

        StackPane passStack = new StackPane(passField, visiblePassField, togglePassBtn);
        StackPane.setAlignment(togglePassBtn, Pos.CENTER_RIGHT);
        StackPane.setMargin(togglePassBtn, new Insets(0, 10, 0, 0));

        passBox.getChildren().addAll(passLbl, passStack);

        HBox optionsRow = new HBox(8);
        optionsRow.setAlignment(Pos.CENTER_LEFT);

        CheckBox rememberMe = new CheckBox("Remember me");
        rememberMe.setSelected(rememberedEmail != null || AuthModalService.isRemembered(roleName));
        rememberMe.setContentDisplay(javafx.scene.control.ContentDisplay.RIGHT);
        rememberMe.setGraphicTextGap(8);
        rememberMe.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-text-fill: #0F172A;"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: 600;"
                + "-fx-cursor: hand;"
        );

        Region optSpacer = new Region();
        HBox.setHgrow(optSpacer, Priority.ALWAYS);

        Hyperlink forgotLink = new Hyperlink("Forgot Password?");
        forgotLink.setStyle("-fx-text-fill: " + accentHexColor + "; -fx-font-size: 12px; -fx-border-color: transparent; -fx-font-weight: bold;");
        forgotLink.setOnAction(e -> AuthModalService.showForgotPasswordModal(emailField.getText().trim()));

        optionsRow.getChildren().addAll(rememberMe, optSpacer, forgotLink);

        Label loginErrorLabel = new Label();
        loginErrorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        loginErrorLabel.setTextFill(Color.web("#DC2626"));
        loginErrorLabel.setWrapText(true);
        loginErrorLabel.setMaxWidth(Double.MAX_VALUE);
        loginErrorLabel.setAlignment(Pos.CENTER);
        loginErrorLabel.setVisible(false);
        loginErrorLabel.setManaged(false);

        Button loginBtn = new Button("Login to " + roleName + " Dashboard  →");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPrefHeight(46);
        loginBtn.setStyle(
                "-fx-background-color: " + accentHexColor + ";"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 10px;"
                + "-fx-cursor: hand;"
        );
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(
                "-fx-background-color: derive(" + accentHexColor + ", -10%);"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 10px;"
                + "-fx-cursor: hand;"
        ));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
                "-fx-background-color: " + accentHexColor + ";"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 10px;"
                + "-fx-cursor: hand;"
        ));

        loginBtn.setOnAction(e -> {
            String email = emailField.getText() != null ? emailField.getText().trim() : "";
            String pass = passField.getText();

            loginErrorLabel.setVisible(false);
            loginErrorLabel.setManaged(false);

            if (ValidationUtil.validateLoginInput(email, pass)) {
                loginBtn.setDisable(true);
                loginBtn.setText("Authenticating 🔒...");

                FirebaseDAO.getInstance().authenticateUser(email, pass, roleName).thenAccept(user -> {
                    Platform.runLater(() -> {
                        loginBtn.setDisable(false);
                        loginBtn.setText("Login to " + roleName + " Dashboard  →");

                        if (user != null) {
                            UserSession.setCurrentUser(user);
                            AuthModalService.saveRememberMePreference(roleName, email, rememberMe.isSelected());
                            SceneNavigator.setCurrentUserEmail(user.getEmail());
                            FirebaseDAO.getInstance().logActivity("User logged in successfully as " + roleName, "Authentication", "SUCCESS");
                            onLoginSuccess.accept(user.getEmail());
                        } else {
                            FirebaseDAO.getInstance().logActivity("Failed login attempt for " + email + " (" + roleName + ")", "Authentication", "FAILED");
                            loginErrorLabel.setText("❌ Invalid Email, Password, or Role! Please check your credentials or create an account.");
                            loginErrorLabel.setVisible(true);
                            loginErrorLabel.setManaged(true);
                        }
                    });
                });
            }
        });

        VBox footerBox = new VBox(4);
        footerBox.setAlignment(Pos.CENTER);

        HBox createAccBox = new HBox(4);
        createAccBox.setAlignment(Pos.CENTER);
        Text noAccText = new Text("Don't have an account?");
        noAccText.setFill(Color.web("#64748B"));
        noAccText.setFont(Font.font("Arial", 12));
        Hyperlink createAccLink = new Hyperlink("Create Account");
        createAccLink.setStyle("-fx-text-fill: " + accentHexColor + "; -fx-font-weight: bold; -fx-font-size: 12px;");
        createAccLink.setOnAction(e -> AuthModalService.showCreateAccountModal(roleName, emailField));
        createAccBox.getChildren().addAll(noAccText, createAccLink);

        HBox contactBox = new HBox(4);
        contactBox.setAlignment(Pos.CENTER);
        Text needAccessText = new Text("Need access?");
        needAccessText.setFill(Color.web("#64748B"));
        needAccessText.setFont(Font.font("Arial", 12));
        Hyperlink contactAdmin = new Hyperlink("Contact Administrator");
        contactAdmin.setStyle("-fx-text-fill: " + accentHexColor + "; -fx-font-size: 12px;");
        contactAdmin.setOnAction(e -> ValidationUtil.showAlert(
                javafx.scene.control.Alert.AlertType.INFORMATION,
                "Administrator Support",
                "Help & Access Support",
                "Contact administrator support dispatched for " + roleName + " workspace."
        ));
        contactBox.getChildren().addAll(needAccessText, contactAdmin);

        footerBox.getChildren().addAll(createAccBox, contactBox);

        loginCard.getChildren().addAll(
                iconLabel,
                cardTitleBox,
                emailBox,
                passBox,
                optionsRow,
                loginErrorLabel,
                loginBtn,
                footerBox
        );

        mainContainer.getChildren().addAll(leftSection, loginCard);

        ScrollPane scroll = new ScrollPane(mainContainer);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        root.setCenter(scroll);

        return new Scene(root, 1280, 780);
    }

    private static VBox createPreviewCard(String roleName, String accentColor) {
        VBox card = new VBox(12);
        card.setPrefSize(580, 240);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.95);"
                + "-fx-background-radius: 18px;"
                + "-fx-border-color: #E2E8F0;"
                + "-fx-border-radius: 18px;"
        );

        HBox topBar = new HBox(8);
        topBar.setAlignment(Pos.CENTER_LEFT);
        Circle c1 = new Circle(5, Color.web("#EF4444"));
        Circle c2 = new Circle(5, Color.web("#F59E0B"));
        Circle c3 = new Circle(5, Color.web("#10B981"));
        Text title = new Text("SkillVerse " + roleName + " Dashboard Preview");
        title.setFill(Color.web("#64748B"));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        topBar.getChildren().addAll(c1, c2, c3, title);

        HBox body = new HBox(20);
        body.setAlignment(Pos.CENTER_LEFT);

        VBox miniStats = new VBox(10);
        miniStats.setPrefWidth(160);

        VBox s1 = new VBox(2);
        s1.setPadding(new Insets(8, 12, 8, 12));
        s1.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px;");
        Text t1 = new Text("ACTIVE USERS");
        t1.setFill(Color.web("#94A3B8"));
        t1.setFont(Font.font("Arial", 9));
        Text v1 = new Text("1,420");
        v1.setFill(Color.web("#0F172A"));
        v1.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        s1.getChildren().addAll(t1, v1);

        VBox s2 = new VBox(2);
        s2.setPadding(new Insets(8, 12, 8, 12));
        s2.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px;");
        Text t2 = new Text("SYSTEM STATUS");
        t2.setFill(Color.web("#94A3B8"));
        t2.setFont(Font.font("Arial", 9));
        Text v2 = new Text("Operational 100%");
        v2.setFill(Color.web("#10B981"));
        v2.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        s2.getChildren().addAll(t2, v2);

        miniStats.getChildren().addAll(s1, s2);

        HBox graphBars = new HBox(12);
        graphBars.setAlignment(Pos.BOTTOM_LEFT);
        graphBars.setPadding(new Insets(10, 15, 10, 15));
        graphBars.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 12px;");
        graphBars.setPrefSize(340, 140);

        int[] heights = {40, 65, 85, 110, 95, 125, 140};
        for (int h : heights) {
            Region bar = new Region();
            bar.setPrefWidth(24);
            bar.setPrefHeight(h);
            bar.setStyle(
                    "-fx-background-color: linear-gradient(to top, " + accentColor + ", #8B5CF6);"
                    + "-fx-background-radius: 6px 6px 0 0;"
            );
            graphBars.getChildren().add(bar);
        }

        body.getChildren().addAll(miniStats, graphBars);
        card.getChildren().addAll(topBar, body);

        return card;
    }
}
