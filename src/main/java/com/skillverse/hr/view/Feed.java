package com.skillverse.hr.view;

import java.io.File;
import java.util.List;

import com.skillverse.FirstScreen.SceneNavigator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class Feed {

    private Scene feedScene;
    private Button backButton;

    private final String background = "#F5F7FC";
    private final String white = "#FFFFFF";
    private final String primaryBlue = "#2563EB";
    private final String darkBlue = "#111C3D";
    private final String textDark = "#111827";
    private final String textLight = "#64748B";
    private final String mutedText = "#94A3B8";
    private final String border = "#E5EAF3";
    private final String lightBlue = "#EEF4FF";
    private final String hoverBlue = "#E8F0FF";

    private VBox postsContainer;
    private ScrollPane mainScrollPane;
    private String currentUserRole = "Employee";
    private String currentUserName = "Rohan Kulkarni";
    private String selectedPostAttachmentPath = null;

    public Feed() {
        this("Employee");
    }

    public Feed(String userRole) {
        if (userRole != null && !userRole.isBlank()) {
            this.currentUserRole = userRole;
        }

        if ("HR".equalsIgnoreCase(this.currentUserRole)) {
            this.currentUserName = "Alice Johnson";
        } else if ("Manager".equalsIgnoreCase(this.currentUserRole)) {
            this.currentUserName = "Aarav Sharma";
        } else if ("Trainer".equalsIgnoreCase(this.currentUserRole)) {
            this.currentUserName = "Alex Morgan";
        } else if ("Admin".equalsIgnoreCase(this.currentUserRole)) {
            this.currentUserName = "System Admin";
        } else {
            this.currentUserName = "Rohan Kulkarni";
        }

        VBox root = new VBox();
        root.setStyle("-fx-background-color: " + background + ";");

        HBox topBar = createTopBar();

        VBox feedContent = new VBox(20);
        feedContent.setPadding(new Insets(28, 20, 40, 20));
        feedContent.setMaxWidth(850);

        VBox heading = new VBox(4);
        Label title = new Label("Company Feed");
        title.setStyle("-fx-text-fill: " + textDark + "; -fx-font-size: 28px; -fx-font-weight: bold;");

        Label subtitle = new Label("Stay connected with announcements, team updates, and employee achievements across SkillVerse.");
        subtitle.setStyle("-fx-text-fill: " + textLight + "; -fx-font-size: 13px;");

        heading.getChildren().addAll(title, subtitle);

        VBox createBox = createPostCreationBox();
        postsContainer = new VBox(16);

        loadPosts();

        feedContent.getChildren().addAll(heading, createBox, postsContainer);

        com.skillverse.CommonFeatures.SocialFeedView socialFeed = new com.skillverse.CommonFeatures.SocialFeedView();
        VBox socialFeedBox = socialFeed.getView();

        mainScrollPane = new ScrollPane(socialFeedBox);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setStyle("-fx-background-color: " + background + "; -fx-background: " + background + ";");
        VBox.setVgrow(mainScrollPane, Priority.ALWAYS);

        root.getChildren().addAll(topBar, mainScrollPane);

        feedScene = new Scene(root, 1600, 800);
    }

    private VBox createPostCreationBox() {
        boolean isHR = "HR".equalsIgnoreCase(currentUserRole);
        boolean isManager = "Manager".equalsIgnoreCase(currentUserRole);

        VBox box = new VBox(14);
        box.setPadding(new Insets(22));
        box.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #E2E8F0;" +
            "-fx-border-radius: 16px;" +
            "-fx-background-radius: 16px;" +
            "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.05), 12, 0.1, 0, 3);"
        );

        String boxTitleText;
        String headlinePrompt;
        String descPrompt;
        String buttonText;
        String uploadBtnText;

        if (isHR) {
            boxTitleText = "🏢 Broadcast Official HR Notice & Hiring Announcement";
            headlinePrompt = "Headline Title (e.g. We are Hiring: Senior Java & Full-Stack Developers! 🚀)";
            descPrompt = "Role details, requirements, experience needed, and application instructions...";
            buttonText = "Broadcast HR Notice →";
            uploadBtnText = "📁 Upload Hiring Banner / Poster";
        } else if (isManager) {
            boxTitleText = "📢 Broadcast Team Announcement & Project Update";
            headlinePrompt = "Announcement Title (e.g. Sprint 4 Deliverables Completed Ahead of Schedule! 👏)";
            descPrompt = "Write team announcement, milestone details, or project update...";
            buttonText = "Post Team Announcement →";
            uploadBtnText = "📁 Upload Banner / Chart Snapshot";
        } else {
            boxTitleText = "🎓 Share Your Achievement or Milestone 🚀";
            headlinePrompt = "Title (e.g. Cleared AWS Certified Developer Exam / Shipped Sprint 4 Microservices)";
            descPrompt = "Write about what you achieved, skills gained, or project highlights...";
            buttonText = "Post Achievement →";
            uploadBtnText = "📁 Attach Certificate / Project Snapshot";
        }

        Text boxTitle = new Text(boxTitleText);
        boxTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        boxTitle.setFill(Color.web("#0F172A"));

        TextField headlineF = new TextField();
        headlineF.setPromptText(headlinePrompt);
        headlineF.setPrefHeight(38);
        headlineF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        TextArea descF = new TextArea();
        descF.setPromptText(descPrompt);
        descF.setPrefRowCount(3);
        descF.setWrapText(true);
        descF.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        ComboBox<String> tagBox = new ComboBox<>();
        if (isHR) {
            tagBox.getItems().addAll("Hiring", "Engineering", "Design", "Marketing", "Company Policy", "HR Notice");
            tagBox.setValue("Hiring");
        } else if (isManager) {
            tagBox.getItems().addAll("Team-Wide", "Department-Wide", "Project Release", "Leadership Notice");
            tagBox.setValue("Team-Wide");
        } else {
            tagBox.getItems().addAll("Certification", "Project Win", "Skill Badge", "Milestone");
            tagBox.setValue("Certification");
        }
        tagBox.setPrefHeight(36);
        tagBox.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        HBox actionRow = new HBox(12);
        actionRow.setAlignment(Pos.CENTER_LEFT);

        Button uploadBtn = new Button(uploadBtnText);
        uploadBtn.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-size: 11px; -fx-font-weight: bold; -fx-border-color: #BFDBFE; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-cursor: hand;");

        StackPane imagePreviewBox = new StackPane();
        imagePreviewBox.setPrefSize(140, 50);
        imagePreviewBox.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6px; -fx-background-radius: 6px;");
        Label previewStatusText = new Label("No image");
        previewStatusText.setFont(Font.font("Arial", 11));
        previewStatusText.setTextFill(Color.web("#94A3B8"));
        imagePreviewBox.getChildren().add(previewStatusText);

        uploadBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select Image Attachment");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
            File pf = chooser.showOpenDialog(SceneNavigator.getPrimaryStage());
            if (pf != null) {
                selectedPostAttachmentPath = pf.toURI().toString();
                previewStatusText.setText("✓ " + pf.getName());
                previewStatusText.setTextFill(Color.web("#16A34A"));
                try {
                    Image img = new Image(selectedPostAttachmentPath);
                    ImageView pv = new ImageView(img);
                    pv.setFitWidth(130); pv.setFitHeight(44); pv.setPreserveRatio(true);
                    imagePreviewBox.getChildren().clear();
                    imagePreviewBox.getChildren().add(pv);
                } catch (Exception ex) {}
            }
        });

        Region pSp = new Region();
        HBox.setHgrow(pSp, Priority.ALWAYS);

        Button postBtn = new Button(buttonText);
        postBtn.setStyle(
            "-fx-background-color: #1E60FF;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;" +
            "-fx-padding: 8px 22px;" +
            "-fx-background-radius: 8px;" +
            "-fx-cursor: hand;"
        );

        postBtn.setOnAction(e -> {
            String head = headlineF.getText().trim();
            String desc = descF.getText().trim();

            if (!head.isEmpty() || !desc.isEmpty()) {
                String tag = tagBox.getValue();
                String roleTag;
                if (isHR) {
                    roleTag = "HR Manager • HR BROADCAST";
                } else if (isManager) {
                    roleTag = "Software Manager • MANAGER ANNOUNCEMENT";
                } else {
                    roleTag = "Employee • EMPLOYEE ACHIEVEMENT";
                }

                Post newP = new Post(
                    currentUserName,
                    roleTag,
                    head.isEmpty() ? "Feed Update" : head,
                    tag,
                    desc,
                    selectedPostAttachmentPath,
                    "Just now",
                    0
                );

                PostRepository.getInstance().addPost(newP);
                headlineF.clear();
                descF.clear();
                selectedPostAttachmentPath = null;
                imagePreviewBox.getChildren().clear();
                previewStatusText.setText("No image");
                previewStatusText.setTextFill(Color.web("#94A3B8"));
                imagePreviewBox.getChildren().add(previewStatusText);

                loadPosts(); // Reload feed stream immediately!
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Incomplete Post");
                alert.setHeaderText("Please enter details");
                alert.setContentText("Please enter a title or description for your feed post.");
                alert.showAndWait();
            }
        });

        actionRow.getChildren().addAll(uploadBtn, imagePreviewBox, tagBox, pSp, postBtn);
        box.getChildren().addAll(boxTitle, headlineF, descF, actionRow);
        return box;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPrefHeight(64);
        topBar.setMinHeight(64);
        topBar.setMaxHeight(64);
        topBar.setPadding(new Insets(0, 25, 0, 25));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle(
            "-fx-background-color: " + white + ";" +
            "-fx-border-color: " + border + ";" +
            "-fx-border-width: 0 0 1px 0;"
        );



        return topBar;
    }

    private void loadPosts() {
        postsContainer.getChildren().clear();
        List<Post> posts = PostRepository.getInstance().getAllPosts();
        for (Post post : posts) {
            postsContainer.getChildren().add(createPostCard(post));
        }
    }

    private VBox createPostCard(Post post) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(22));
        card.setStyle(
            "-fx-background-color: " + white + ";" +
            "-fx-background-radius: 18px;" +
            "-fx-border-color: " + border + ";" +
            "-fx-border-radius: 18px;" +
            "-fx-effect: dropshadow(gaussian, rgba(17,24,39,0.05), 12, 0, 0, 3);"
        );

        Label avatar = new Label(getInitials(post.getUserName()));
        avatar.setAlignment(Pos.CENTER);
        avatar.setPrefSize(42, 42);
        avatar.setStyle("-fx-background-color: " + lightBlue + "; -fx-background-radius: 50%; -fx-text-fill: " + primaryBlue + "; -fx-font-size: 11px; -fx-font-weight: bold;");

        Label name = new Label(post.getUserName());
        name.setStyle("-fx-text-fill: " + textDark + "; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label role = new Label(post.getRole() + " • " + post.getTimestamp());
        role.setStyle("-fx-text-fill: " + mutedText + "; -fx-font-size: 11px;");

        VBox userText = new VBox(3, name, role);

        Region headerSp = new Region();
        HBox.setHgrow(headerSp, Priority.ALWAYS);

        // Explicit Role Badge Pill Rendering
        String roleStr = post.getRole() != null ? post.getRole() : "";
        Label roleBadgePill = new Label();
        if (roleStr.contains("HR")) {
            roleBadgePill.setText("HR BROADCAST");
            roleBadgePill.setStyle("-fx-background-color: #1E3A8A; -fx-text-fill: #FFFFFF; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 12px;");
        } else if (roleStr.contains("Manager")) {
            roleBadgePill.setText("MANAGER ANNOUNCEMENT");
            roleBadgePill.setStyle("-fx-background-color: #6B21A8; -fx-text-fill: #FFFFFF; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 12px;");
        } else {
            roleBadgePill.setText("EMPLOYEE ACHIEVEMENT");
            roleBadgePill.setStyle("-fx-background-color: #065F46; -fx-text-fill: #FFFFFF; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 12px;");
        }

        Label tagPill = new Label(post.getJobTag() != null ? post.getJobTag().toUpperCase() : "GENERAL");
        tagPill.setStyle("-fx-background-color: #EFF6FF; -fx-text-fill: #1E60FF; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 12px; -fx-border-color: #BFDBFE; -fx-border-radius: 12px;");

        HBox userSection = new HBox(11, avatar, userText, headerSp, roleBadgePill, tagPill);
        userSection.setAlignment(Pos.CENTER_LEFT);

        VBox bodyBox = new VBox(6);
        if (post.getHeadlineTitle() != null && !post.getHeadlineTitle().isBlank()) {
            Text hText = new Text(post.getHeadlineTitle());
            hText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            hText.setFill(Color.web("#0F172A"));
            bodyBox.getChildren().add(hText);
        }

        Label content = new Label(post.getContent());
        content.setWrapText(true);
        content.setStyle("-fx-text-fill: " + textDark + "; -fx-font-size: 14px;");
        bodyBox.getChildren().add(content);

        VBox imageContainer = new VBox();
        String imagePath = post.getImagePath();
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            try {
                Image image;
                if (imagePath.startsWith("file:") || imagePath.startsWith("http")) {
                    image = new Image(imagePath);
                } else {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        image = new Image(imageFile.toURI().toString());
                    } else {
                        image = new Image(getClass().getResourceAsStream(imagePath));
                    }
                }
                if (image != null) {
                    ImageView imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(720);
                    imageView.setSmooth(true);
                    Rectangle clipRect = new Rectangle(720, 360);
                    clipRect.setArcWidth(16);
                    clipRect.setArcHeight(16);
                    imageView.setClip(clipRect);
                    imageContainer.getChildren().add(imageView);
                }
            } catch (Exception ex) {}
        }

        Label likeCount = new Label(getLikeText(post));
        likeCount.setStyle("-fx-text-fill: " + mutedText + "; -fx-font-size: 11px;");

        Label separator = new Label();
        separator.setMaxWidth(Double.MAX_VALUE);
        separator.setPrefHeight(1);
        separator.setStyle("-fx-background-color: " + border + ";");

        Button likeButton = createActionButton(getLikeButtonText(post));
        Button commentButton = createActionButton("💬 Comments (" + post.getComments().size() + ")");

        likeButton.setOnAction(e -> {
            post.toggleLike();
            likeButton.setText(getLikeButtonText(post));
            likeCount.setText(getLikeText(post));
        });

        VBox commentsContainer = createCommentsSection(post);
        commentButton.setOnAction(e -> {
            boolean isVis = commentsContainer.isVisible();
            commentsContainer.setVisible(!isVis);
            commentsContainer.setManaged(!isVis);
        });

        HBox actions = new HBox(10, likeButton, commentButton);

        boolean isHiringPost = "Hiring".equalsIgnoreCase(post.getJobTag()) ||
                               (post.getRole() != null && post.getRole().contains("Hiring")) ||
                               (post.getHeadlineTitle() != null && post.getHeadlineTitle().contains("Hiring"));

        if (isHiringPost) {
            Button applyBtn = new Button("🚀 Apply Now");
            applyBtn.setStyle(
                "-fx-background-color: #10B981;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12px;" +
                "-fx-background-radius: 9px;" +
                "-fx-padding: 8px 16px;" +
                "-fx-cursor: hand;"
            );
            applyBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Job Application Submitted");
                alert.setHeaderText("Application Confirmation 🚀");
                alert.setContentText("Your candidate profile, verified skills summary, and official email address (" +
                    (SceneNavigator.getCurrentUserEmail() != null ? SceneNavigator.getCurrentUserEmail() : "rohan@skillverse.com") +
                    ") have been submitted to the HR Recruitment team for: " +
                    (post.getHeadlineTitle() != null ? post.getHeadlineTitle() : "Open Position") + "!");
                alert.showAndWait();
            });
            actions.getChildren().add(applyBtn);
        }

        actions.setAlignment(Pos.CENTER_LEFT);

        card.getChildren().addAll(userSection, bodyBox);
        if (!imageContainer.getChildren().isEmpty()) {
            card.getChildren().add(imageContainer);
        }
        card.getChildren().addAll(likeCount, separator, actions, commentsContainer);

        return card;
    }

    private VBox createCommentsSection(Post post) {
        VBox commentsBox = new VBox(8);
        commentsBox.setPadding(new Insets(10, 0, 0, 0));
        commentsBox.setVisible(false);
        commentsBox.setManaged(false);

        VBox existingComments = new VBox(7);
        refreshComments(post, existingComments);

        TextField commentField = new TextField();
        commentField.setPromptText("Write a comment...");
        commentField.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + border + "; -fx-border-radius: 9px; -fx-background-radius: 9px; -fx-padding: 9px 12px; -fx-font-size: 12px;");
        HBox.setHgrow(commentField, Priority.ALWAYS);

        Button commentButton = new Button("Comment");
        commentButton.setStyle("-fx-background-color: " + primaryBlue + "; -fx-text-fill: white; -fx-background-radius: 9px; -fx-padding: 9px 14px; -fx-font-size: 11px; -fx-font-weight: bold; -fx-cursor: hand;");

        HBox commentInput = new HBox(8, commentField, commentButton);
        commentInput.setAlignment(Pos.CENTER_LEFT);

        commentButton.setOnAction(e -> {
            String comment = commentField.getText().trim();
            if (!comment.isEmpty()) {
                post.addComment(currentUserName + ": " + comment);
                commentField.clear();
                refreshComments(post, existingComments);
            }
        });

        commentField.setOnAction(e -> commentButton.fire());
        commentsBox.getChildren().addAll(existingComments, commentInput);

        return commentsBox;
    }

    private void refreshComments(Post post, VBox container) {
        container.getChildren().clear();
        for (String comment : post.getComments()) {
            HBox cRow = new HBox(8);
            cRow.setAlignment(Pos.CENTER_LEFT);
            cRow.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 8px 12px;");

            Label cAv = new Label("💬");
            cAv.setStyle("-fx-font-size: 13px;");

            Label cText = new Label(comment);
            cText.setWrapText(true);
            cText.setStyle("-fx-text-fill: #1E293B; -fx-font-size: 12px;");

            cRow.getChildren().addAll(cAv, cText);
            container.getChildren().add(cRow);
        }
    }

    private Button createActionButton(String text) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: " + lightBlue + ";" +
            "-fx-text-fill: " + primaryBlue + ";" +
            "-fx-background-radius: 9px;" +
            "-fx-padding: 8px 16px;" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: " + hoverBlue + "; -fx-text-fill: " + primaryBlue + "; -fx-background-radius: 9px; -fx-padding: 8px 16px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-cursor: hand;");
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: " + lightBlue + "; -fx-text-fill: " + primaryBlue + "; -fx-background-radius: 9px; -fx-padding: 8px 16px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-cursor: hand;");
        });

        return button;
    }

    private String getLikeText(Post post) {
        int likes = post.getLikes();
        if (likes == 0) return "Be the first to like this";
        if (likes == 1) return "1 like";
        return likes + " likes";
    }

    private String getLikeButtonText(Post post) {
        return post.isLiked() ? "❤️ Liked" : "❤️ Like";
    }

    private String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) return "SV";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) return parts[0].substring(0, 1).toUpperCase();
        return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
    }

    public Button getBackButton() { return backButton; }
    public Scene getScene() { return feedScene; }
    public ScrollPane getFeedContentBox() { return mainScrollPane; }
}